package com.docmall.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.domain.CartOrderInfo;
import com.docmall.domain.CartVO;
import com.docmall.domain.CartVOList;
import com.docmall.domain.MemberVO;
import com.docmall.domain.OrderVO;
import com.docmall.domain.PaymentVO;
import com.docmall.kakaopay.ApproveResponse;
import com.docmall.kakaopay.ReadyResponse;
import com.docmall.service.CartService;
import com.docmall.service.KakaoPayServiceImpl;
import com.docmall.service.OrderService;
import com.docmall.util.UploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/user/order/*")
@Controller
public class OrderController {

	@Setter(onMethod_ = {@Autowired})
	private OrderService orderService;
	
	@Setter(onMethod_ = {@Autowired})
	private CartService cartService;
	
	@Setter(onMethod_ = {@Autowired})
	private KakaoPayServiceImpl kakaoPayServiceImpl;
	
	@Resource(name = "uploadPath") // Bean중에 uploadPath bean객체를 찾아, 아래 변수에 주입한다.
	private String uploadPath;  // "D:/dev/upload"
	
	// 주문내역
	// @RequestParam(value = "pdt_num", required = false, defaultValue = ) 
	@GetMapping("/orderListInfo")
	public void orderListInfo(@RequestParam("type") String type, Integer pdt_num, Integer odr_amount, HttpSession session, Model model) {
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		List<CartOrderInfo> orderInfoList = null;
		
		if(type.equals("cartOrder")) { //장바구니 구매.   Integer pdt_num, int odr_amount 파라미터 사용 안됨.
			orderInfoList = orderService.cartOrderList(mem_id);
			
		}else if(type.equals("direct")) { //직접구매. Integer pdt_num, int odr_amount 파라미터 사용.
			orderInfoList = orderService.directOrderList(pdt_num, odr_amount);
			
			//직접구매시 장바구니에 데이타를 저장한다.			
			CartVO vo = new CartVO(); 
			vo.setMem_id(mem_id);
			vo.setPdt_num(pdt_num);
			vo.setCart_amount(odr_amount);
			
			cartService.cart_add(vo);
			
		}
		
		// 폴더경로 역슬래쉬를 슬래쉬로 변환.
		for(int i=0; i<orderInfoList.size(); i++) {
			String pdt_img_folder = orderInfoList.get(i).getPdt_img_folder().replace("\\", "/"); // File.serparator 운영체제 경로구분자
			orderInfoList.get(i).setPdt_img_folder(pdt_img_folder);
		}
		
		model.addAttribute("cartOrderList", orderInfoList);
	}
	
	//주문저장하기(장바구니 주문하기 또는 직접구매)
	@PostMapping("/orderSave")
	public String orderSave(OrderVO o_vo, PaymentVO p_vo, String type, HttpSession session) {
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		o_vo.setMem_id(mem_id);
		
		log.info("주문정보: " + o_vo);
		log.info("결제정보: " + p_vo);
		
		// 1)무통장 입금일 경우.
		if(type.equals("무통장")) {
			
			o_vo.setPayment_status("입금전"); 
			
			p_vo.setPay_tot_price(o_vo.getOdr_total_price()); // 총 실 결제금액
			p_vo.setPay_rest_price(0); // 추가 입금금액
		}
		//2)카카오 페이 결제일 경우.
//		if(type.equals("카카오페이")) {
//			
//			o_vo.setPayment_status("입금전"); 
//			
//			p_vo.setPay_tot_price(o_vo.getOdr_total_price()); // 총 실 결제금액
//			p_vo.setPay_rest_price(0); // 추가 입금금액
//		}
		
		
		orderService.orderbuy(o_vo, p_vo);
		
		return "redirect:/user/order/orderComplete"; //주문내역 안내문구.
	}
	
	
	//카카오페이 결제요청. 바로구매는 에러발생된다.
	@GetMapping("/orderPay")
	public @ResponseBody ReadyResponse payReady(OrderVO o_vo,  PaymentVO p_vo, int totalAmount, HttpSession session, Model model) {
		
		//장바구니테이블에서 상품정보(상품명, 상품코드, 수량, 상품가격*수량=단위별 금액)
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		//장바구니에서 주문이 진행될 때
		List<CartVOList> cartList = cartService.cart_list(mem_id);
		String itemName = cartList.get(0).getPdt_name() + "외 " + String.valueOf(cartList.size() - 1) + " 개";
		int quantity = cartList.size() - 1;
		
		
		// 카카오페이서버에서 보낸온 정보. (1차 준비요청) -> QR코드 주소 리턴
		ReadyResponse readyResponse = kakaoPayServiceImpl.payReady(itemName, quantity, mem_id, totalAmount);
		
		//model.addAttribute("tid", readyResponse.getTid());
		
		session.setAttribute("tid", readyResponse.getTid());
		o_vo.setMem_id(mem_id);
		session.setAttribute("order", o_vo);
		session.setAttribute("payment", p_vo);
		
		
		
		//log.info("결제고유번호1: " + readyResponse.getTid());
			
		return readyResponse;
	}
	
	
	//결제승인요청 : 큐알코드를 찍고(결제요청) 카카오페이 서버에서 결제가 성공적으로 끝나면, 카카오페이 서버에서 호출하는 주소
	@GetMapping("/orderApproval")
	public String orderApproval(@RequestParam("pg_token") String pgToken, HttpSession session ) {
		
		log.info("결제 승인요청 인증토큰: " + pgToken);
		//log.info("주문정보: " + o_vo);
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		String tid = (String) session.getAttribute("tid");
		OrderVO o_vo = (OrderVO) session.getAttribute("order"); 
		PaymentVO p_vo = (PaymentVO )session.getAttribute("payment");
		
		log.info("결제정보: " + p_vo);
		
		session.removeAttribute("tid"); //세션제거. 반드시 처리할 것. 로그인 상태에서 세션정보가 필요하지 않게되면, 불필요하게 서버측의 메모리를 사용하고 있게된다.
		session.removeAttribute("order");
		session.removeAttribute("payment");
		
		log.info("결제고유번호2: " + tid);
		
		//카카오페이 결제하기.  2번째 요청
		ApproveResponse approveResponse =kakaoPayServiceImpl.payApprove(tid, pgToken, mem_id);
		log.info("approveResponse: " + approveResponse);
		
		//카카오페이 결제정보 db저장 : approveResponse 제외.
		
		orderService.orderbuy(o_vo, p_vo);

		return "redirect:/user/order/orderComplete";
	}
		
	
	@GetMapping("/orderComplete")
	public void orderComplete() {
		
	}
	
	@ResponseBody
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String folderName, String fileName){
		
		log.info("폴더이름: " + folderName);
		log.info("파일이름: " + fileName);
		
		
		
		//이미지를 바이트 배열로 읽어오는 작업
		return UploadFileUtils.getFile(uploadPath, folderName + "\\" + fileName);
	}
}
