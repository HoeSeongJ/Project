package com.docmall.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.domain.CartVO;
import com.docmall.domain.CartVOList;
import com.docmall.domain.MemberVO;
import com.docmall.service.CartService;
import com.docmall.util.UploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/user/cart/*")
@Controller
public class CartController {

	@Setter(onMethod_ = {@Autowired})
	private CartService cartService;
	
	@Resource(name = "uploadPath") // Bean중에 uploadPath bean객체를 찾아, 아래 변수에 주입한다.
	private String uploadPath;  // "D:/dev/upload"
	
	//장바구니 담기.
	@ResponseBody
	@GetMapping("/cart_add")
	public ResponseEntity<String> card_add(CartVO vo, HttpSession session) {
		
		ResponseEntity<String> entity = null;
		
		log.info("장바구니: " + vo );
		
		// 세션에서 로그인시 사용한 정보를 사용.
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		vo.setMem_id(mem_id);
		
		cartService.cart_add(vo);
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 로그인한 사용자가 사용하는 매핑주소의 메서드는 파라미터를 HttpSession session 반드시 사용해야 한다.
	// 이유는 ? 세션을 통하여 로그인한 정보가 저장되어 있기때문에.
	
	// Model model : JSP에 데이터 전달을 하여, 정보를 보여주고자 할때 사용
	
	@GetMapping("/cart_list")
	public String cart_list(HttpSession session, Model model) {
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		List<CartVOList> cartList = cartService.cart_list(mem_id);
		
		for(int i=0; i<cartList.size(); i++) {
			String pdt_img_folder = cartList.get(i).getPdt_img_folder().replace("\\", "/"); // File.serparator 운영체제 경로구분자
			cartList.get(i).setPdt_img_folder(pdt_img_folder);
		}
		
		model.addAttribute("cartList", cartList);
		
		return "/user/cart/cartList";
	}
	
	
	// Ajax요청시 스프링의 메서드는 @ResponseBody, ResponseEntity<결과데이타타입>
	@ResponseBody
	@GetMapping("/cart_amount_update1")
	public ResponseEntity<String> cart_amount_update1(@RequestParam("cart_code") Long cart_code, @RequestParam("cart_amount") int cart_amount){
		
		ResponseEntity<String> entity = null;
		
		cartService.cart_amount_update(cart_code, cart_amount);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	@GetMapping("/cart_amount_update2")
	public String cart_amount_update2(@RequestParam("cart_code") Long cart_code, @RequestParam("cart_amount") int cart_amount){
		
		cartService.cart_amount_update(cart_code, cart_amount);
		
		return "redirect:/user/cart/cart_list";
	}
	
	
	// 등록,수정,삭제 한후 다른주소로 이동해야 하는 경우는 메서드의 리턴타입을 String 이어야 한다. "redirect:/이동주소"
	@GetMapping("/cart_delete")
	public String cart_delete(@RequestParam("cart_code") Long cart_code) {
		
		cartService.cart_delete(cart_code);
		
		return "redirect:/user/cart/cart_list";
	}
	
	@GetMapping("/cart_empty")
	public String cart_empty(HttpSession session) {
		
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		cartService.cart_empty(mem_id);
		
		return "redirect:/user/cart/cart_list";
	}
	
	
	//상품목록에서 이미지 보여주기
	@ResponseBody
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String folderName, String fileName){
		
		log.info("폴더이름: " + folderName);
		log.info("파일이름: " + fileName);
		
		
		
		//이미지를 바이트 배열로 읽어오는 작업
		return UploadFileUtils.getFile(uploadPath, folderName + "\\" + fileName);
	}
	
	
}
