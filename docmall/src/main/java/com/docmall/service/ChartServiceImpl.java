package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.ChartVO;
import com.docmall.mapper.ChartMapper;

import lombok.Setter;

@Service
public class ChartServiceImpl implements ChartService {

	@Setter(onMethod_ = @Autowired)
	private ChartMapper chartMapper;
	
	@Override
	public List<ChartVO> primaryChart() {
		// TODO Auto-generated method stub
		return null;
	}

}
