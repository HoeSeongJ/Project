package com.docmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.ReviewVO;
import com.docmall.dto.Criteria;

public interface ReviewMapper {

	void create(ReviewVO vo);
	
	List<ReviewVO> list(@Param("pdt_num") Integer pdt_num, @Param("cri") Criteria cri); // 상품후기목록 : 검색제외.
	
	int listCount(Integer pdt_num); // 상품후기 목록개수.
	
	void modify(ReviewVO vo);
	
	void delete(Integer rv_num);
	
}
