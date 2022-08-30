package com.docmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.domain.ChartVO;
import com.docmall.service.ChartService;

import lombok.Setter;
import lombok.extern.java.Log;

@Log
@RequestMapping("/admin/chart/*")
@Controller
public class ChartController {

	@Setter(onMethod_ = @Autowired)
	private ChartService chartService;
	
	//전체(1차) 카테고리 별 통계 차트
	@GetMapping("/overall")
	public void overall(Model model) {
		
		
		/* 통계차트 데이타 : 파이차크
		 * 2차원 배열구조
		 [ -- 시작
          ['Task', 'Hours per Day'],   -- 제목
          ['Work',     11],
          ['Eat',      2],
          ['Commute',  2],
          ['Watch TV', 2],
          ['Sleep',    7]
        ]  -- 끝
		 */
		
	List<ChartVO> primary_list = chartService.primaryChart();
	
	String primaryData = "[";
	primaryData += "['1차 카테고리', '매출'],";
			
	int i=0;
	for(ChartVO vo : primary_list) {
		primaryData += "['" + vo.getPrimary_cd() +"'," + vo.getSales_p() + "]";
		i++;
		
		//마지막 데이타 처리시 콤마(,)는 추가안함.
		if(i < primary_list.size()) primaryData += ",";
	}
	
	primaryData += "]";
	
	model.addAttribute("primaryData", primaryData);
	
	}
}
