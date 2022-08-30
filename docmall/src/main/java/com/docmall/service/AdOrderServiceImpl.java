package com.docmall.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docmall.domain.OrderVO;
import com.docmall.domain.PaymentVO;
import com.docmall.dto.Criteria;
import com.docmall.mapper.AdOrderMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class AdOrderServiceImpl implements AdOrderService {

	@Setter(onMethod_ = @Autowired)
	private AdOrderMapper adOrderMapper;

	@Override
	public List<OrderVO> getOrderList(Criteria cri, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return adOrderMapper.getOrderList(cri, startDate, endDate);
	}

	@Override
	public int getOrderTotalCount(Criteria cri, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return adOrderMapper.getOrderTotalCount(cri, startDate, endDate);
	}

	@Override
	public void orderStatusChange(Long odr_code, String odr_status) {
		// TODO Auto-generated method stub
		adOrderMapper.orderStatusChange(odr_code, odr_status);
	}

	@Override
	public void orderDelete(Long odr_code) {
		// TODO Auto-generated method stub
		adOrderMapper.orderDelete(odr_code);
		
	}

	@Transactional
	@Override
	public void orderListDelete(List<Long> ordCodeArr) {
		// TODO Auto-generated method stub
		
		//주문삭제기능 : 관련된 작업을 모두 삭제. 트랜잭션 설정.
		//주문테이블삭제(처리)
		
		// 처리 할것.
		//주문상세테이블삭제
		//결제테이블삭제
		
		
		adOrderMapper.orderListDelete(ordCodeArr);
		
	}

	@Override
	public OrderVO getOrderInfo(Long odr_code) {
		// TODO Auto-generated method stub
		return adOrderMapper.getOrderInfo(odr_code);
	}

	@Override
	public PaymentVO getPaymentInfo(Long odr_code) {
		// TODO Auto-generated method stub
		return adOrderMapper.getPaymentInfo(odr_code);
	}

	@Override
	public List<Map<String, Object>> getOrderProductInfo(Long odr_code) {
		// TODO Auto-generated method stub
		return adOrderMapper.getOrderProductInfo(odr_code);
	}

	//주문상품 개별취소기능
	@Transactional
	@Override
	public void orderUnitProductCancel(Long odr_code, Integer pdt_num, int unit_price) {
		
		
		// 주문상세테이블에 데이타가 1개이면,  주문테이블, 결제테이블 주문정보삭제
		if(adOrderMapper.getorderDetailProductCount(odr_code) == 1) {
			//주문테이블삭제
			adOrderMapper.orderDelete(odr_code);
			//결제테이블삭제
			adOrderMapper.paymentDelete(odr_code);
		}
		
		// 테이블에 조건식에 일치하는 데이타가 존재안해도, 정상 실행된다.(Delete문)
		 
		adOrderMapper.orderDetailProductDelete(odr_code, pdt_num);
		
		// 테이블에 조건식에 일치하는 데이타가 존재안해도, 정상 실행된다.(UPdate문)
		// 주문테이블의 총주문금액 변경(빼기)
		adOrderMapper.orderTotalPriceChange(odr_code, unit_price);
		// 결제테이블의 결제금액 변경(빼기)
		adOrderMapper.paymentTotalPriceChange(odr_code, unit_price);
		
	}
}
