package com.docmall.service;

import java.util.List;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;

public interface AdProductService {

	List<CategoryVO> getCateList();
	
	List<CategoryVO> getSubCateList(Integer categoryCode);
	
	void productInsert(ProductVO vo);
	
	List<ProductVO> getProductList(Criteria cri);
	
	int getProductTotalCount(Criteria cri);
	
	//상품수정 폼(상품정보)
	ProductVO getProductByNum(Integer pdt_num);
	
	//상품수정하기
	void productModify(ProductVO vo);
	
	//상품삭제하기
	void productDelete(Integer pdt_num);
}
