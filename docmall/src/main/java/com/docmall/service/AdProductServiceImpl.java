package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;
import com.docmall.mapper.ProductMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class AdProductServiceImpl implements AdProductService {
	
	@Setter(onMethod_ = {@Autowired})
	private ProductMapper proMapper;
	
	@Override
	public List<CategoryVO> getCateList() {
		// TODO Auto-generated method stub
		return proMapper.getCateList();
	}

	@Override
	public List<CategoryVO> getSubCateList(Integer categoryCode) {
		// TODO Auto-generated method stub
		return proMapper.getSubCateList(categoryCode);
	}

	@Override
	public void productInsert(ProductVO vo) {
		// TODO Auto-generated method stub
		proMapper.productInsert(vo);
	}

	@Override
	public List<ProductVO> getProductList(Criteria cri) {
		// TODO Auto-generated method stub
		return proMapper.getProductList(cri);
	}

	@Override
	public int getProductTotalCount(Criteria cri) {
		// TODO Auto-generated method stub
		return proMapper.getProductTotalCount(cri);
	}

	@Override
	public ProductVO getProductByNum(Integer pdt_num) {
		// TODO Auto-generated method stub
		return proMapper.getProductByNum(pdt_num);
	}

	@Override
	public void productModify(ProductVO vo) {
		// TODO Auto-generated method stub
		proMapper.productModify(vo);
	}

	@Override
	public void productDelete(Integer pdt_num) {
		// TODO Auto-generated method stub
		proMapper.productDelete(pdt_num);
		
	}

}
