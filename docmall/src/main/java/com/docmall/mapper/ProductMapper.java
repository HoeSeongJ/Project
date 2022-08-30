package com.docmall.mapper;

import java.util.List;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;

public interface ProductMapper {

	List<CategoryVO> getCateList(); // 1차카테고리
	
	List<CategoryVO> getSubCateList(Integer categoryCode);  //2차카테고리
	
	void productInsert(ProductVO vo);
	
	List<ProductVO> getProductList(Criteria cri);
	
	int getProductTotalCount(Criteria cri);
	
	ProductVO getProductByNum(Integer pdt_num);
	
	void productModify(ProductVO vo);
	
	void productDelete(Integer pdt_num);
}
