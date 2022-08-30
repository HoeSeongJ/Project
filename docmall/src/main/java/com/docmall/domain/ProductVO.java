package com.docmall.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductVO {

	// pdt_num, cate_code, cate_code_prt, pdt_name, pdt_price, pdt_discount, pdt_company, 
	// pdt_detail, pdt_img, pdt_amount, pdt_buy, pdt_date_sub, pdt_date_up
	
	private Integer pdt_num; // 상품코드 시퀀스 사용
	private Integer cate_code; // 2차
	private Integer cate_code_prt;  // 1차
	private String pdt_name;
	private int pdt_price;
	private int pdt_discount;
	private String pdt_company;
	private String pdt_detail;
	private String pdt_img_folder;
	private String pdt_img; // 상품이미지 파일이름.  예> "test.jpg"
	private int pdt_amount;
	private String pdt_buy;
	private Date pdt_date_sub;
	private Date pdt_date_up;
	
	
	// 상품이미지 파일(업로드)
	// <form id="productForm" action="productInsert" method="post" enctype="multipart/form-data">
	// <input type="file" class="form-control" id="uploadFile" name="uploadFile">
	// servlet-context.xml : <beans:bean id="multipartResolver" class="org.springframework.web.multipart.support.StandardServletMultipartResolver"></beans:bean>
	private MultipartFile uploadFile;
	
}
