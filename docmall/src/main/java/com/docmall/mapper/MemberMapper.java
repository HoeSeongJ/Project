package com.docmall.mapper;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.MemberVO;
import com.docmall.dto.LoginDTO;

public interface MemberMapper {

	void join(MemberVO vo);
	
	String idCheck(String mem_id);
	
	MemberVO login_ok(LoginDTO dto); // 아이디만 사용(비밀번호 사용이 안되어 있음)
	
	//mapper인터페이스의 메서드가 파라미터 2개이상일 경우에는 @Param 어노테이션 반드시 사용해야 한다.
	String searchID(@Param("mem_name") String mem_name, @Param("mem_email") String mem_email);
	
	//임시비밀번호 발급을 위한 확인작업
	String getIDEmailExists(@Param("mem_id") String mem_id, @Param("mem_email") String mem_email);
	
	//임시비밀번호를 암호화하여 변경.
	void changePW(@Param("mem_id") String mem_id, @Param("mem_pw") String enc_mem_pw);
	
	//회원정보 수정하기
	void modify(MemberVO vo);
	
}
