package com.docmall.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.domain.MemberVO;
import com.docmall.domain.ReviewVO;
import com.docmall.dto.Criteria;
import com.docmall.dto.PageDTO;
import com.docmall.service.ReviewService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/user/review/*")
@RestController //@Controller + @ResponseBody
public class ReviewController {

	
	@Setter(onMethod_ = {@Autowired})
	private ReviewService reviewService;
	
	
	//상품후기 쓰기
	// consumes : 클라이언트에서 보내는 데이타 타입지정.
	// produces : 서버에서 클라이언트로 보내는 데이타 타입지정.
	// @RequestBody : json데이타를 ReviewVO vo 객체로 변환하는 작업
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReviewVO vo, HttpSession session) {
		
		ResponseEntity<String> entity = null;

		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();	
		vo.setMem_id(mem_id);
		
		log.info("상품후기: " + vo);
		
		reviewService.create(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	
	//상품후기 수정.  resp api개발
	@PatchMapping(value = "/modify", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReviewVO vo, HttpSession session) {
		
		ResponseEntity<String> entity = null;

		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();	
		vo.setMem_id(mem_id);
		
		log.info("상품후기: " + vo);
		
		reviewService.modify(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	//상품후기 삭제
	@DeleteMapping(value = "/delete/{rv_num}")
	public ResponseEntity<String> delete(@PathVariable("rv_num") Integer rv_num) {
		
		log.info("상품후기코드: " + rv_num);
		
		ResponseEntity<String> entity = null;
	
		reviewService.delete(rv_num);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}	
	
	//상품후기 목록 : 1)목록 + 2)페이징정보(2개의 데이타) -> JSON포맷으로 클라이언트에게 보내는 작업
	@GetMapping("/list/{pdt_num}/{page}")
	public ResponseEntity<Map<String, Object>> reviewList(@PathVariable("pdt_num") Integer pdt_num, @PathVariable("page") Integer page) {
		
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		Criteria cri = new Criteria(); //검색필드가 필요없으므로, 메서드의 파라미터로 사용하지않고, 객체를 직접생성한다.
		cri.setPageNum(page);
		
		//1)댓글목록
		List<ReviewVO> list = reviewService.list(pdt_num, cri);
		map.put("list", list);	
		//2)페이지정보
		PageDTO pageMaker = new PageDTO(cri, reviewService.listCount(pdt_num));
		map.put("pageMaker", pageMaker);
		
		
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		
		return entity;
	}
	
	
	
}
