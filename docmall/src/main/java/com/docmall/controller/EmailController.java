package com.docmall.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.dto.EmailDTO;
import com.docmall.service.EmailService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/email/*")
@RestController
public class EmailController {

	@Setter(onMethod_ = {@Autowired})
	private EmailService service;
	
	@GetMapping("/send") // EmailDTO dto = new EmailDTO(); 스프링에서 자동객체생성
	public ResponseEntity<String> send(EmailDTO dto, HttpSession session) {
		
		ResponseEntity<String> entity = null;
		
		//인증코드 생성.   6자리
		String authCode = "";
		for(int i=0; i<6; i++) {
			authCode += String.valueOf((int)(Math.random()*10)); // 0~9범위의 값
		}
		
		session.setAttribute("authCode", authCode); // 세션으로 인증코드 정보를 서버측 메모리에 생성.
		
		log.info("인증코드: " + authCode);
		
		log.info("메일정보: " + dto);
		
		//메일보내기 기능(인증코드)
		try {
			service.sendMail(dto, authCode); // 메일기본정보(받는사람, 보내는 사람 등등), 인증코드
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}
