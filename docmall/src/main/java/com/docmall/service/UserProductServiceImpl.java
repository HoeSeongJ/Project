package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;
import com.docmall.mapper.UserProductMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class UserProductServiceImpl implements UserProductService {
	
	@Setter(onMethod_ = {@Autowired})
	private UserProductMapper proMapper;
	
	@Override
	public List<CategoryVO> getCategoryList() {
		// TODO Auto-generated method stub
		return proMapper.getCategoryList();
	}

	@Override
	public List<CategoryVO> getSubCategoryList(Integer categoryCode) {
		// TODO Auto-generated method stub
		return proMapper.getSubCategoryList(categoryCode);
	}

	@Override
	public List<ProductVO> getProductListbysubCategory(Integer cate_code, Criteria cri) {
		// TODO Auto-generated method stub
		return proMapper.getProductListbysubCategory(cate_code, cri);
	}

	@Override
	public int getProductCountbysubCategory(Integer cate_code, Criteria cri) {
		// TODO Auto-generated method stub
		return proMapper.getProductCountbysubCategory(cate_code, cri);
	}

	@Override
	public ProductVO getProductByNum(Integer pdt_num) {
		// TODO Auto-generated method stub
		return proMapper.getProductByNum(pdt_num);
	}

}
