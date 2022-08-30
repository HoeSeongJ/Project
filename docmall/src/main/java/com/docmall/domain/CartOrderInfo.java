package com.docmall.domain;

import lombok.Data;

@Data
public class CartOrderInfo {

	// c.pdt_num, c.mem_id, c.cart_amount, p.pdt_name, p.pdt_price, p.pdt_discount, p.pdt_company, p.pdt_img_folder, p.pdt_img
	private Integer pdt_num;
	private String mem_id;
	private int cart_amount;
	private String pdt_name;
	private int pdt_price;
	private int pdt_discount;
	private String pdt_company;
	private String pdt_img_folder;
	private String pdt_img;
}
