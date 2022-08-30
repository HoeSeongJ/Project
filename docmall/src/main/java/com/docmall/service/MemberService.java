package com.docmall.service;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.MemberVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.LoginDTO;

// 어노테이션 사용 안한다.
public interface MemberService {

	void join(MemberVO vo);
	
	String idCheck(String mem_id);
	
	MemberVO login_ok(LoginDTO dto);
	
	String searchID(String mem_name, String mem_email);
	
	//임시비밀번호 발급을 위한 확인작업
	String getIDEmailExists(String mem_id, String mem_email);
	
	//임시비밀번호를 암호화하여 변경.
	void changePW(String mem_id, String enc_mem_pw);
	
	//회원정보 수정하기
	void modify(MemberVO vo);
	

}
