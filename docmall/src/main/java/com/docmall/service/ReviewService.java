package com.docmall.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.ReviewVO;
import com.docmall.dto.Criteria;

public interface ReviewService {

	void create(ReviewVO vo);
	
	List<ReviewVO> list(Integer pdt_num, Criteria cri);
	
	int listCount(Integer pdt_num); // 상품후기 목록개수.
	
	void modify(ReviewVO vo);
	
	void delete(Integer rv_num);
}
