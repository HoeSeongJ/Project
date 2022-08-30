package com.docmall.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CartVOList {

	// C.CART_CODE, C.PDT_NUM, C.MEM_ID, C.CART_AMOUNT, P.PDT_IMG_FOLDER, P.PDT_IMG, P.PDT_NAME, P.PDT_PRICE, M.MEM_POINT
	// CartVO클래스 참조
	private Long cart_code;
	private Integer pdt_num;
	private String mem_id;
	private int cart_amount;
	
	// ProductVO 클래스 참조
	private String pdt_img_folder;
	private String pdt_img;
	private String pdt_name;
	private int pdt_price;
	
	// MemberVO 클래스 참조
	private int mem_point;
	
	
	
	
	
}
