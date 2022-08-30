package com.docmall.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;
import com.docmall.dto.PageDTO;
import com.docmall.service.UserProductService;
import com.docmall.util.UploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/user/product/*")
@Controller
public class UserProductController {

	
	@Setter(onMethod_ = {@Autowired})
	private UserProductService proService;
	
	@Resource(name = "uploadPath") // Bean중에 uploadPath bean객체를 찾아, 아래 변수에 주입한다.
	private String uploadPath;  // "D:/dev/upload"
	
	//1차카테고리 정보를 불러오는 작업은 생략한다. 그 부분을 @ControllerAdvice 어노테이션을 적용된 클래스가 카테고리 Model작업을 함.
	
	
	//2차카테고리 정보. Ajax사용
	@ResponseBody
	@GetMapping("/subCategoryList/{cate_code}")
	public ResponseEntity<List<CategoryVO>> subCategoryList(@PathVariable("cate_code") Integer cate_code) {
		
		ResponseEntity<List<CategoryVO>> entity = null;
		
		entity = new ResponseEntity<List<CategoryVO>>(proService.getSubCategoryList(cate_code), HttpStatus.OK);
		
		return entity;
	}
	
	//상품리스트. 페이징기능추가.(검색기능 제외)
	@GetMapping("/productList/{cate_code}/{cate_name}") // REST API 개발에서 사용하는 주소형태.
	public String productList(@PathVariable("cate_code") Integer cate_code, @PathVariable("cate_name") String cate_name, @ModelAttribute("cri") Criteria cri, Model model) {
		
		log.info("2차카테고리 코드: " + cate_code);
		
		cri.setAmount(9); // 10 -> 9변경
		
		List<ProductVO> productList = proService.getProductListbysubCategory(cate_code, cri);
		
		// 윈도우 운영체제 경로구분자 \ (역슬래쉬).
		// 날짜폴더명의 \를 /로 변환하는 작업. \가 클라이언트에서 서버로 보내지는 데이터로 사용이 안된다.
		for(int i=0; i<productList.size(); i++) {
			String pdt_img_folder = productList.get(i).getPdt_img_folder().replace("\\", "/"); // File.serparator 운영체제 경로구분자
			productList.get(i).setPdt_img_folder(pdt_img_folder);
		}
		
		
		// 1)페이징쿼리에 의한 상품목록
		model.addAttribute("productList", productList);
		
		// [prev] 1	 2	3	4	5  [next]
		int totalCount = proService.getProductCountbysubCategory(cate_code, cri);
		// 2) 페이지 표시
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		
		return "/user/product/productList";
	}
	
	//상품목록에서 이미지 보여주기
	@ResponseBody
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String folderName, String fileName){
		
		log.info("폴더이름: " + folderName);
		log.info("파일이름: " + fileName);
		
		
		
		//이미지를 바이트 배열로 읽어오는 작업
		return UploadFileUtils.getFile(uploadPath, folderName + "\\" + fileName);
	}
	
	//모달대화상자에서 사용할 상품상세정보
	@ResponseBody
	@GetMapping("/productDetail/{pdt_num}")
	public ResponseEntity<ProductVO> productDetail(@PathVariable("pdt_num") Integer pdt_num) {
		
		ResponseEntity<ProductVO> entity = null;

		ProductVO vo = proService.getProductByNum(pdt_num);
		vo.setPdt_img_folder(vo.getPdt_img_folder().replace("\\", "/"));
		
		entity = new ResponseEntity<ProductVO>(vo, HttpStatus.OK);
				
		return entity;
	}
	
	//상품상세주소.  클라이언트에서 actionForm으로 전송되어 옴.
	@GetMapping("/productDetail")
	public String productDetail(@RequestParam("pdt_num") Integer pdt_num, @ModelAttribute("cate_code") Integer cate_code, @ModelAttribute("cate_name") String cate_name, @ModelAttribute("cri") Criteria cri, Model model) {
		
		ProductVO vo = proService.getProductByNum(pdt_num);
		vo.setPdt_img_folder(vo.getPdt_img_folder().replace("\\", "/"));
		
		
		log.info("상품코드: " + pdt_num);
		log.info("검색및페이지정보: " + cri);
		
		log.info("카테고리 코드: " + cate_code);
		log.info("카테고리 이름: " + cate_name);
		
		model.addAttribute("productVO", vo);
				
		return "/user/product/productDetail";
	}
	
}
