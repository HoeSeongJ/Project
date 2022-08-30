package com.docmall.domain;

import lombok.Data;

@Data
public class CategoryVO {

	// cate_code, cate_code_prt, cate_name
	
	/*
	  cate_code_prt 필드가 Null이면, cate_code 필드는 1차카테고리 의미
	  cate_code_prt 필드가 Not Null이면, cate_code 필드는 2차카테고리 의미
	 */
	
	private Integer cate_code;
	private Integer cate_code_prt;
	private String cate_name;
}
