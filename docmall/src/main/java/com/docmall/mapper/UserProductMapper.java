package com.docmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;

public interface UserProductMapper {

	//1차카테고리
	List<CategoryVO> getCategoryList();
	
	//2차카테고리
	List<CategoryVO> getSubCategoryList(Integer cate_code);
	
	//2차카테고리별 상품목록
	List<ProductVO> getProductListbysubCategory(@Param("cate_code") Integer cate_code, @Param("cri") Criteria cri);
	
	//2차카테고리별 상품개수
	int getProductCountbysubCategory(@Param("cate_code") Integer cate_code, @Param("cri") Criteria cri);
	
	//상품상세정보
	ProductVO getProductByNum(Integer pdt_num);
}
