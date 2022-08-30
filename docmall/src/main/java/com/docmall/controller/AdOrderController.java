package com.docmall.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.domain.OrderVO;
import com.docmall.dto.Criteria;
import com.docmall.dto.PageDTO;
import com.docmall.service.AdOrderService;
import com.docmall.util.UploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/admin/order/*")
@Controller
public class AdOrderController {

	
	@Setter(onMethod_ = @Autowired)
	private AdOrderService adOrderService;
	
	@Resource(name = "uploadPath") // Bean중에 uploadPath bean객체를 찾아, 아래 변수에 주입한다.
	private String uploadPath;  // "D:/dev/upload"
	
	@GetMapping("/orderList")
	public void orderList(
			Criteria cri, 
			@RequestParam(value = "startDate", required = false) String startDate, 
			@RequestParam(value = "endDate", required = false) String endDate, 
			Model model) {
		
		
		log.info("시작날짜: " + startDate);
		log.info("종료날짜: " + endDate);
		
		cri.setAmount(2);
		
		List<OrderVO> orderList = adOrderService.getOrderList(cri, startDate, endDate);
		model.addAttribute("orderList", orderList);
		
		// [prev] 1	 2	3	4	5  [next]
		int totalCount = adOrderService.getOrderTotalCount(cri, startDate, endDate);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		log.info("orderList: " + orderList);
	}
	
	@ResponseBody
	@GetMapping("/orderStatusChange")
	public ResponseEntity<String> orderStatusChange(Long odr_code, String odr_status) {
		ResponseEntity<String> entity = null;
		
		adOrderService.orderStatusChange(odr_code, odr_status);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	//선택된 주문정보 삭제. ajax구문으로 배열값이 파라미터로 전송될 경우, 스프링 메서드에서는 파라미터 작업? @RequestParam("ordCodeArr[]")
	@ResponseBody
	@PostMapping("/orderCheckedDelete")
	public ResponseEntity<String> orderCheckedDelete(@RequestParam("ordCodeArr[]") List<Long> ordCodeArr) {
		ResponseEntity<String> entity = null;
		
		//방법1.  선택한 개수만큼 반복
		for(int i=0; i<ordCodeArr.size(); i++) {
			
			//주문번호를 이용하여 삭제구문진행.
			//adOrderService.orderDelete(ordCodeArr.get(i));
		}
		
		//방법2
		// mybatis에서 이구문 작업을 해야 한다.  delete 주문테이블 where 주문번호 in (1, 2, 3, 4, 5)
		adOrderService.orderListDelete(ordCodeArr);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	//주문상세
	@GetMapping("/orderDetail")
	public void orderDetail(Long odr_code, Model model) {
		
		log.info("주문번호: " + odr_code);
		
		//주문정보
		model.addAttribute("orderVO", adOrderService.getOrderInfo(odr_code));
		//결제정보
		model.addAttribute("paymentVO", adOrderService.getPaymentInfo(odr_code));
		
		
		//주문상품정보
		List<Map<String, Object>>  orderProductListMap = adOrderService.getOrderProductInfo(odr_code);
		
		for(int i=0; i < orderProductListMap.size(); i++) {
			Map<String, Object> oderProudct = orderProductListMap.get(i);
			
			String img_folder = String.valueOf(oderProudct.get("Pdt_Img_Folder")).replace("\\", "/");
			oderProudct.put("Pdt_Img_Folder", img_folder);
		}
		
		
		model.addAttribute("orderProductMap", orderProductListMap);
		
	}
	
	//주문상품 개별취소 기능. 페이징, 검색정보 제외.
	@GetMapping("/orderUnitProductCancel")
	public String orderUnitProductCancel(Long odr_code, Integer pdt_num, int unit_price, RedirectAttributes rttr) {
		
		
		adOrderService.orderUnitProductCancel(odr_code, pdt_num, unit_price);
		
		rttr.addAttribute("odr_code", odr_code);
		
		return "redirect:/admin/order/orderDetail";
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
