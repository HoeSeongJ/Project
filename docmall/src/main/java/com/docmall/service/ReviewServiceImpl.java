package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.ReviewVO;
import com.docmall.dto.Criteria;
import com.docmall.mapper.ReviewMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j
@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Setter(onMethod_ = {@Autowired})
	private ReviewMapper reviewMapper;
	
	@Override
	public void create(ReviewVO vo) {
		// TODO Auto-generated method stub
		reviewMapper.create(vo);
		
	}

	@Override
	public List<ReviewVO> list(Integer pdt_num, Criteria cri) {
		// TODO Auto-generated method stub
		return reviewMapper.list(pdt_num, cri);
	}

	@Override
	public int listCount(Integer pdt_num) {
		// TODO Auto-generated method stub
		return reviewMapper.listCount(pdt_num);
	}

	@Override
	public void modify(ReviewVO vo) {
		// TODO Auto-generated method stub
		reviewMapper.modify(vo);
	}

	@Override
	public void delete(Integer rv_num) {
		// TODO Auto-generated method stub
		reviewMapper.delete(rv_num);
	}

}
