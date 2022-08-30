package com.docmall.service;

import java.util.List;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;

public interface UserProductService {

	//1차카테고리
	List<CategoryVO> getCategoryList();
	
	//2차카테고리
	List<CategoryVO> getSubCategoryList(Integer categoryCode);
	
	//2차카테고리별 상품목록
	
	List<ProductVO> getProductListbysubCategory(Integer cate_code, Criteria cri);
		
	//2차카테고리별 상품개수
	int getProductCountbysubCategory(Integer cate_code, Criteria cri);
	
	//상품상세정보
	ProductVO getProductByNum(Integer pdt_num);
}
