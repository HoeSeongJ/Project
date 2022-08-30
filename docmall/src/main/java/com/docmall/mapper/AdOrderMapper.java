package com.docmall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.OrderVO;
import com.docmall.domain.PaymentVO;
import com.docmall.dto.Criteria;

public interface AdOrderMapper {

	// 목록과 카운트작업은 조건을 동일하게 해야한다.(중요)
	List<OrderVO> getOrderList(@Param("cri") Criteria cri, @Param("startDate") String startDate, @Param("endDate") String endDate);
	int getOrderTotalCount(@Param("cri") Criteria cri, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	void orderStatusChange(@Param("odr_code") Long odr_code, @Param("odr_status") String odr_status);
	
	void orderDelete(Long odr_code);
	
	// 파라미터로 mapper파일에 컬렉션을 사용할 때.  DELETE FROM ORDER_TBL WHERE ODR_CODE IN (주문번호1, 주문번호2, 주문번호3)
	// 참고> 파라미터가 Map컬렉션일 경우는 어떻게 구문을 사용해야 하냐?  HashMap<String, Object> map = new HashMap(); map.put("checkList", chkList)
	// <foreach collection="checkList">
	void orderListDelete(List<Long> ordCodeArr);
	
	//주문정보
	OrderVO getOrderInfo(Long odr_code);
	//결제정보
	PaymentVO getPaymentInfo(Long odr_code);
	//주문상품정보
	List<Map<String, Object>> getOrderProductInfo(Long odr_code);
	
	//주문상품 개별취소기능
	//1)주문상세테이블 주문개별상품 데이타 삭제
	void orderDetailProductDelete(@Param("odr_code") Long odr_code, @Param("pdt_num") Integer pdt_num);
	//2)주문테이블 : 총주문가격 수정
	void orderTotalPriceChange(@Param("odr_code") Long odr_code, @Param("unit_price") int unit_price);
	//3)결제테이블 : 결제 총금액 수정
	void paymentTotalPriceChange(@Param("odr_code") Long odr_code, @Param("unit_price") int unit_price);
	
	
	//주문상세테이블의 데이타 1개 확인
	int getorderDetailProductCount(Long odr_code);
	
	//결제테이블 삭제
	void paymentDelete(Long odr_code);
}
