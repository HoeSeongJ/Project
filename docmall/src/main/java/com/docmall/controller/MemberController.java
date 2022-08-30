package com.docmall.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.domain.MemberVO;
import com.docmall.dto.EmailDTO;
import com.docmall.dto.LoginDTO;
import com.docmall.mapper.MemberMapper;
import com.docmall.service.EmailService;
import com.docmall.service.MemberService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/member/*")
@Controller
public class MemberController {

	//스프링시큐리티 암호화 클래스주입
	@Setter(onMethod_ = {@Autowired})
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Setter(onMethod_ = {@Autowired})
	private MemberService memService;
	
	@Setter(onMethod_ = {@Autowired})
	private EmailService mailservice;
	
	//회원가입 폼
	@GetMapping("/join")
	public void join() {
		
	}
	
	//회원정보 저장
	@PostMapping("/join") // MemberVO vo = new MemberVO(); 스프링이 객체생성을 자동으로 해줌
	public String join(MemberVO vo, RedirectAttributes rttr) throws Exception {
		
		// vo.getMem_pw() : 평문텍스트 비밀번호
		
		String cryptEncoderPW = bCryptPasswordEncoder.encode(vo.getMem_pw());
		
//		log.info("평문텍스트 비밀번호 : " + vo.getMem_pw());
//		log.info("암호화된 비밀번호 : " + cryptEncoderPW);
//		log.info("암호화된 비밀번호 길이 : " + cryptEncoderPW.length());
		
		vo.setMem_pw(cryptEncoderPW); //평문텍스트 비밀번호 -> 암호화된 비밀번호 변경
		
		
		if(vo.getMem_accept_e().equals("on")) {
			vo.setMem_accept_e("Y");
		}
		
		log.info(vo); // vo.toString()
		
		memService.join(vo);
		
		
		return "/member/login"; // 회원가입 후 로그인주소로 이동.
	}
	
	//아이디중복체크
	@ResponseBody
	@GetMapping("/idCheck")
	public ResponseEntity<String> idCheck(@RequestParam("mem_id") String mem_id) {
		
		ResponseEntity<String> entity = null;
		
		//아이디 존재여부작업
		String isUseID = "";
		
		if(memService.idCheck(mem_id) != null) {
			isUseID = "no"; // 아이디가 존재하여, 사용 불가능
		}else {
			isUseID = "yes"; // 아이디가 존재하지 않아서, 사용 가능
		}
		
		entity = new ResponseEntity<String>(isUseID, HttpStatus.OK);
		
		return entity;
	}
	
	//닉네임중복체크
		@ResponseBody
		@GetMapping("/NickCheck")
		public ResponseEntity<String> NickCheck(@RequestParam("mem_nick") String mem_nick) {
			
			ResponseEntity<String> entity = null;
			
			//아이디 존재여부작업
			String isUseNick = "";
			
			if(memService.idCheck(mem_nick) != null) {
				isUseNick = "no"; // 아이디가 존재하여, 사용 불가능
			}else {
				isUseNick = "yes"; // 아이디가 존재하지 않아서, 사용 가능
			}
			
			entity = new ResponseEntity<String>(isUseNick, HttpStatus.OK);
			
			return entity;
		}
	
	
	//메일인증확인작업
	@PostMapping("/confirmAuthCode")
	@ResponseBody
	public ResponseEntity<String> confirmAuthCode(String uAuthCode, HttpSession session) {
		
		ResponseEntity<String> entify = null;
		
		String authCode = (String)session.getAttribute("authCode");
		if(authCode.equals(authCode)) {
			entify = new ResponseEntity<String>("success", HttpStatus.OK);
			
			// 세션을 사용하고 난 후 즉시제거.
			session.removeAttribute("authCode");
		}else {
			entify = new ResponseEntity<String>("fail", HttpStatus.OK);
		}
			
		return entify;
	}
	
	
	//로그인폼
	@GetMapping("/login")
	public void login() {
		
		log.info("로그인 폼");
	}
	
	//로그인인증
	@PostMapping("/loginPost")
	public String login_ok(LoginDTO dto, RedirectAttributes rttr, HttpSession session) throws Exception{
		
		// RedirectAttributes인터페이스 주요메서드 설명.
		//1)rttr.addFlashAttribute(attributeName, attributeValue) : 페이지 이동주소의 jsp에서 참조할 경우
		
		
		//2)rttr.addAttribute(attributeName, attributeValue) : 리다이렉트 주소에 파라미터로 사용. /member/login?pageNum=값&amount=값
		/*
		  rttr.addAttribute("pageNum", pageNum);
		  rttr.addAttribute("amount", amount);
		 */
		
		log.info("로그인정보: " + dto);
		
		//로그인정보 인증작업
		MemberVO vo = memService.login_ok(dto);
		
		String url = "";
		String msg = "";
		
		if(vo != null) { // 아이디가 존재하는 경우
			
			// 사용자가 입력한 평문텍스트(일반비번)과 DB에 저장된 암호화된 비밀번호를 비교작업
			
			//1)비번일치되는 경우
			
			String passwd = dto.getMem_pw(); // 사용자가 입력한 비밀번호
			String db_passwd = vo.getMem_pw(); // DB에서 가져온 비밀번호(암호환 된 비밀번호)
			
			if(bCryptPasswordEncoder.matches(passwd, db_passwd)) {
				url = "/"; // 메인페이지 주소
				session.setAttribute("loginStatus", vo); // 인증성공시 서버측에 세션을 통한 정보를 저장.
				
				// preHandle()메서드에서 세션형태로 저장한 것을 사용
				String dest =  (String) session.getAttribute("dest");
				url = (dest != null) ? dest : "/";
				
				msg = "loginSuccess";
				
			}else {
				//2)비번일치되지 않는 경우
				url = "/member/login"; // 로그인폼 주소
				msg = "passwdFailure";
			}
			
			
		}else { // 아이디가 존재하지 않는 경우
			url = "/member/login";  // 로그인폼 주소
			msg = "idFailure";
		}
		
		rttr.addFlashAttribute("msg", msg); //이동하는 주소의 jsp에서 참조함.
		
		return "redirect:" + url;
	}
	
	
	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes rttr) {
		
		//세션소멸
		session.invalidate();
		
		rttr.addFlashAttribute("msg", "logout"); // jsp참조.
		
		return "redirect:/";
	}
	
	//아이디및비밀번호 찾기 폼
	@GetMapping("/lostpass")
	public void lostpass() {
		
	}
	
	//아이디찾기
	@PostMapping("/searchID")
	public String searchID(@RequestParam("mem_name") String mem_name, @RequestParam("mem_email") String mem_email, 
							Model model, RedirectAttributes rttr) {
		
		log.info("이름: " + mem_name);
		log.info("전자우편: " + mem_email);
		
		String mem_id = memService.searchID(mem_name, mem_email);
		
		String url = "";
		
		if(mem_id != null) {
			model.addAttribute("mem_id", mem_id); // key, value
			url = "/member/searchID"; // jsp파일이름
		}else {
			url = "redirect:/member/lostpass"; // 이동주소(get방식요청)
			rttr.addFlashAttribute("msg", "noID");
		}
		
		return url;		
	}
	
	//임시비밀번호 발급
	@PostMapping("/searchImsiPW")
	public String searchImsiPW(@RequestParam("mem_id") String mem_id, @RequestParam("mem_email") String mem_email, Model model, RedirectAttributes rttr) {
		
		//db에서 아이디와 메일존재여부를 확인
		String db_mem_id = memService.getIDEmailExists(mem_id, mem_email);
		String temp_mem_pw = "";
		
		String url = "";
		
		if(db_mem_id != null) {
			//메일보내기 작업
			
			//1)임시비밀번호 생성
			UUID uid = UUID.randomUUID();
			temp_mem_pw = uid.toString().substring(0, 6); //인덱스 6은 제외한 범위의 6자리문자열
			
			log.info("임시비밀번호: " + temp_mem_pw);
			
			//2)temp_mem_pw 임시비밀번호를 암호화하여, db에 저장
			memService.changePW(mem_id, bCryptPasswordEncoder.encode(temp_mem_pw));
			
			//3)메일보내기
			EmailDTO dto = new EmailDTO("DocMall", "DocMall", mem_email, "DocMall 임시비밀번호입니다.", "");
									
			try {
				mailservice.sendMail(dto, temp_mem_pw);
				model.addAttribute("mail", "mail");
				url = "/member/searchID";  //jsp파일이름
			}catch(Exception ex) {
				ex.printStackTrace();
			}
	
		}else {
			url = "redirect:/member/lostpass";
			rttr.addFlashAttribute("msg", "noID");
		}
		
		return url;
	}
	
	
	// 회원정보수정을 위한 비밀번호 재확인 폼.
	@GetMapping("/confirmPW")
	public void confirmPW() {
		
	}
	
	
	// 회원정보수정을 위한 비밀번호 재확인.
	@PostMapping("/confirmPW")
	public String confirmPW(@RequestParam("mem_pw") String mem_pw, HttpSession session, RedirectAttributes rttr) {
		
		String url = "";
		// 세션에서 로그인시 사용한 정보를 사용.
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();
		
		//로그인시 사용한 코드를 재사용.
		LoginDTO dto = new LoginDTO(mem_id, mem_pw);
		
		MemberVO vo = memService.login_ok(dto); // 아이디만 비교함(비밀번호는 뺘져있음)
		
		if(vo != null) {  // 아이디만 일치된 경우
			
			String db_passwd = vo.getMem_pw(); // DB에서 가져온 비밀번호(암호환 된 비밀번호)
			
			if(bCryptPasswordEncoder.matches(mem_pw, db_passwd)) { 			//비밀번호 재확인이 되었을 경우
				url = "member/modify";
			}else { // 비밀번호가 일치 안된 경우
				url = "member/confirmPW";
				rttr.addFlashAttribute("msg", "noPW");
			}
		}else {		// 비밀번호가 틀린경우 원래 재확인 주소로 이동
			url = "member/confirmPW";
			rttr.addFlashAttribute("msg", "noID");
		}
		
		return "redirect:/" + url;
	}
	
	// HttpSession sesion 메서드 파라미터로 사용할 경우? 
	// 로그인한 상태에서 세션에서 로그인정보를 참조하고자 할경우. (로그아웃,회원정보수정 등)
	
	//회원정보수정 폼
	@GetMapping("/modify")
	public void modify(HttpSession session, Model model) {
		
		// 세션에서 로그인시 사용한 정보를 사용.
		String mem_id = ((MemberVO) session.getAttribute("loginStatus")).getMem_id();	
		LoginDTO dto = new LoginDTO(mem_id, ""); // 비밀번호는 쿼리에서 사용이 안되고 있음. 그래서 공백처리 함.
		
		//로그인 쿼리가 회원정보수정폼 쿼리와 동일하여 사용함.
		MemberVO vo = memService.login_ok(dto);
		model.addAttribute("memberVO", vo);
		
	}
		
	//회원정보수정 저장하기
	@PostMapping("/modify")
	public String modify(MemberVO vo, RedirectAttributes rttr) {
		
		log.info("회원수정정보: " + vo);
		
		// 클라이언트에서 입력한 정보에 파라미터명이 MemberVO클래스와 일치하지 않으면, 필드가 참조타입일 경우에는 Null이된다.
		// 파라미터가 일치하지 않는 경우. 예>비밀번호.    클라이언트 mem_pw100  ----------->  서버(스프링) MemberVO클래스의 필드 mem_pw. Null이 됨.
		// 파라미터가 일치하는 경우   값을 입력 안하면           클라이언트 mem_pw  -------------> 서버(스프링) MemberVO클래스의 필드 mem_pw. 공백("")이 됨.
		
		if(vo.getMem_pw().equals("")) log.info("공백문자열 ");
		
		if(vo.getMem_pw() != null && !vo.getMem_pw().equals("")) {
			log.info("변경비밀번호:  " + vo.getMem_pw());
			String cryptEncoderPW = bCryptPasswordEncoder.encode(vo.getMem_pw());
			vo.setMem_pw(cryptEncoderPW); //평문텍스트 비밀번호 -> 암호화된 비밀번호 변경		
		}
		//메일수신여부.
		/*
		 <input type="checkbox" value="Y">   vo.getMem_accept_e().equals("Y")
		 선택안 하고 전송클릭시 <input type="checkbox">  Null로 스프링에서 인식되므로 코드를  vo.getMem_accept_e() != null
		 */
		if(vo.getMem_accept_e().equals("on")) {
			vo.setMem_accept_e("Y");
		}else {
			vo.setMem_accept_e("N");
		}
		
		memService.modify(vo);
		
		return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
