package com.docmall.dto;

import lombok.Getter;
import lombok.ToString;

// jsp에서  [이전]  1	 2	3	4	5	[다음] 형태를 만들기위한 정보를 구성하는 목적
/*
         1	 2	3	4	5	[다음]   - 첫번째블럭
  [이전]  6   7  8   9   10          - 두번째블럭
 */
@Getter
@ToString
public class PageDTO {
	
	private int startPage;  // 각 블럭의 시작페이지 번호저장
	private int endPage;    // 각 블럭의 마지막페이지 번호저장
	
	//이전, 다음 표시여부
	private boolean prev, next;
	
	//총 데이타 개수
	private int total; 
	
	// 페이징과검색 필드 : page, amount, type, keyword
	private Criteria cri;

	//매개변수가 있는 생성자 정의
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		//데이터수 42
		int pageSize = 20; //블록에 표시될 페이지 개수
		int endPageInfo = pageSize - 1; // 5 - 1 = 4
		
		// pageNum 이 1~5범위에 해당되면, endPage변수의 값이 동일하게된다.  1   2	3	4	5
		// pageNum이 6이면, endPage변수의 값이 다르게 된다. 6	7	8	9  10
		// (int) (ceil( 1 / 5.0)) * 5,  (int) (ceil( 2 / 5.0)) * 5, (int) (ceil( 3 / 5.0)) * 5
		this.endPage = (int) (Math.ceil(cri.getPageNum() / (double) pageSize)) * pageSize;
		this.startPage = this.endPage - endPageInfo;  // 10 - 4 = 6
		
		//실제 데이터수를 사용한 전체페이지수를 구한다.
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		
		if(realEnd <= this.endPage) {
			this.endPage = realEnd;
		}
		
		//이전, 다음 표시여부
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
		
		
		
		
	}
	
}
