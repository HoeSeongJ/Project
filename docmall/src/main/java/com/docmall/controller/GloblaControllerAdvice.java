package com.docmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.docmall.service.UserProductService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

/*
com.docmall.controller 패키지안에 존재하는 컨트롤러에서 주소요청을 받으면,  categoryList메서드가 자동으로 호출되어, 공통모델을 사용할수가 있게 된다.
*/
@ControllerAdvice(basePackages = {"com.docmall.controller"})
@Log4j
public class GloblaControllerAdvice {

	@Setter(onMethod_ = {@Autowired})
	private UserProductService userProService;
	
	
	// 1차 카테고리를 Model작업
	@ModelAttribute
	public void categoryList(Model model) {
		
		log.info("1차카테고리 모델작업");
		
		model.addAttribute("mainCategoryList", userProService.getCategoryList());
	}
	
}
