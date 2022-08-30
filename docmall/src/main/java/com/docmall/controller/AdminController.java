package com.docmall.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.domain.AdminVO;
import com.docmall.service.AdminService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/admin/*")
@Controller
public class AdminController {

	@Setter(onMethod_ = {@Autowired})
	private AdminService service;
	
	//스프링시큐리티 암호화 클래스주입
	@Setter(onMethod_ = {@Autowired})
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("")
	public String adLogin() {
		
		return "/admin/adLogin";
	}
	
	@PostMapping("/adminOk")
	public String adminOk(AdminVO vo, HttpSession session) throws Exception {
		
		String url = "";
		
		AdminVO dbAdminVO = service.adminOk(vo);
		
		if(dbAdminVO != null) { // 아이디 존재할 경우
		
			if(bCryptPasswordEncoder.matches(vo.getAdmin_pw(), dbAdminVO.getAdmin_pw())) { // 비번이 일치할 경우
				//로그인 인증성공
				session.setAttribute("adminStatus", dbAdminVO);
				url = "admin/adMain";
			}else {	//비번이 일치하지 않을 경우
				url = "admin/";
			}
		}else {	// 아이디가 존재하지 않을 경우
			url = "admin/";
		}
		
		return "redirect:/" + url;
	}
	
	@GetMapping("/adMain")
	public String adMain() {
		
		return "/admin/main";
	}
	
	//로그아웃
	@GetMapping("/adLogout")
	public String adLogout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/admin/"; //관리자 로그인페이지 주소
	}
	
}
// admin