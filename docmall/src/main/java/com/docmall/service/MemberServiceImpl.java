package com.docmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.MemberVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.LoginDTO;
import com.docmall.mapper.MemberMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class MemberServiceImpl implements MemberService {
	
	@Setter(onMethod_ = {@Autowired})
	private MemberMapper mapper;
	
	@Override
	public void join(MemberVO vo) {
		
		mapper.join(vo);
		
	}

	@Override
	public String idCheck(String mem_id) {
		// TODO Auto-generated method stub
		return mapper.idCheck(mem_id);
	}

	@Override
	public MemberVO login_ok(LoginDTO dto) {
		// TODO Auto-generated method stub
		return mapper.login_ok(dto);
	}

	@Override
	public String searchID(String mem_name, String mem_email) {
		// TODO Auto-generated method stub
		return mapper.searchID(mem_name, mem_email);
	}

	@Override
	public String getIDEmailExists(String mem_id, String mem_email) {
		// TODO Auto-generated method stub
		return mapper.getIDEmailExists(mem_id, mem_email);
	}

	@Override
	public void changePW(String mem_id, String enc_mem_pw) {
		// TODO Auto-generated method stub
		mapper.changePW(mem_id, enc_mem_pw);
	}

	@Override
	public void modify(MemberVO vo) {
		// TODO Auto-generated method stub
		mapper.modify(vo);
	}


}
