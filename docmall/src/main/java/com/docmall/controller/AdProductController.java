package com.docmall.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;
import com.docmall.dto.PageDTO;
import com.docmall.mapper.ProductMapper;
import com.docmall.service.AdProductService;
import com.docmall.util.UploadFileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//관리자 - 상품관리기능

@Log4j
@RequestMapping("/admin/product/*")
@Controller
public class AdProductController {

	@Setter(onMethod_ = {@Autowired})
	private AdProductService proService;
	
	@Resource(name = "uploadPath") // Bean중에 uploadPath bean객체를 찾아, 아래 변수에 주입한다.
	private String uploadPath;  // "D:/dev/upload"

	//상품등록 폼 : 1차카테고리정보
	@GetMapping("/productInsert")
	public void productInsert(Model model) {
		
		model.addAttribute("cateList", proService.getCateList());
	}
	
	
	//2차카테고리 정보 불어오기. @PathVariable : 경로주소의 일부분을 파라미터로 참조시 사용
	@ResponseBody
	@GetMapping("/subCategoryList/{categoryCode}")  //  subCategoryList/1차카테고리코드(REST). /subCategoryList?category=1
	public ResponseEntity<List<CategoryVO>> subCategoryList(@PathVariable("categoryCode") Integer categoryCode) {
		
		ResponseEntity<List<CategoryVO>> entity = null;
		
		entity = new ResponseEntity<List<CategoryVO>>(proService.getSubCateList(categoryCode), HttpStatus.OK);
			
		return entity;
	}
	
	//CKEditor 웹에디터를 통한 이미지 업로드 작업(상세설명에서 사용하는 설명이미지파일)
	@PostMapping("/imageUpload")  // CKEditor: 업로드 <input type="file" name="upload">
	public void imageUpload(HttpServletRequest req, HttpServletResponse res, MultipartFile upload) {
		
		//입출력스트림방식으로 파일업로드 구현. 
		/*
		 - request : 클라이언트가 서버에 접속하는 작업형태
		 - response : 서버에서 클라이언트에게 결과를 보낼때 담당하는 의미.
		 * 
		 */
		
		
		
		OutputStream out = null;
		PrintWriter printWriter = null; // 개체의 형식화된 표현을 텍스트 출력스트림에 출력하는 기능을 제공.
		
		//클라이언의 브라우저에게 보내는 정보.
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=utf-8");
		
		try {
			String fileName = upload.getOriginalFilename(); // 클라이언트에서 업로드한 원본파일명.
			byte[] bytes = upload.getBytes(); // 업로드 파일
			
			//서버측의 업로드폴더경로 작업. 1)프로젝트 내부  2)외부
			//1)프로젝트 내부 : 톰캣이 war 파일로 리눅스서버에 배포를 할 경우, 톰캣이 재시작하면, 기존 upload폴더를 삭제해버린다. 
			// 톰캣이 실제관리하는 물리적인 경로
			
			
			String uploadTomcatTempPath = req.getSession().getServletContext().getRealPath("/") + "resources\\upload\\";
			log.info("톰캣 물리적 경로: " + uploadTomcatTempPath);
			
			//2)외부폴더(프로젝트 관리하는 폴더가 아님)
			//작업시 톰캣의 server.xml의 <Context docBase="C:\\Dev\\upload\\ckeditor" path="/upload/" reloadable="true"/>설정 할것.
			String uploadPath = "C:\\Dev\\upload\\ckeditor\\"; // 톰캣의 server.xml에 설정정보 참고
			
			log.info("외부 물리적 경로: " + uploadPath);
			
			uploadPath = uploadPath + fileName;
			
			out = new FileOutputStream(new File(uploadPath)); // 파일입출력스트림 객체생성(실제폴더에 파일생성됨). 0byte
			out.write(bytes); // 출력스트림에 업로드된 파일을 가리키는 바이트배열을 쓴다.  업로드된 파일크기.
			
			// CKEditor에게 보낼 파일정보작업
			
			printWriter = res.getWriter();
			
			//클라이언트에서 요청할 이미지 주소정보
			String fileUrl = "/upload/" + fileName; // // 톰캣의 server.xml에 설정정보 참고
			
			// {"filename":"abc.gif", "uploaded":1, "url":"/upload/abc.gif"} CKEditor 4.x version에서 요구하는 json포맷
			printWriter.println("{\"filename\":\"" + fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}");
			printWriter.flush(); // 전송 (return과 같은 역할: 클라이언트로 보냄)
			
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(out != null) {
				try {
					out.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			if(printWriter != null) {
				printWriter.close();
			}
		}
		
	}
	
	
	
	//상품저장
	@PostMapping("/productInsert")
	public String productInsert(ProductVO vo, RedirectAttributes rttr) {
		
		log.info("상품등록정보: " + vo);
		
		//파일업로드작업
		// vo.getPdt_img() Null 상태임.
		// 이미지 파일명이 저장될 pdt_img필드에 업로드한  후 실제파일명을 저장.
		String uploadDateFolderPath = UploadFileUtils.getFolder();
		vo.setPdt_img_folder(uploadDateFolderPath); // 날짜폴더명
		vo.setPdt_img(UploadFileUtils.uploadFile(uploadPath, uploadDateFolderPath, vo.getUploadFile())); // 실제 업로드한 파일명
		
		//상품정보저장
		proService.productInsert(vo);
		
		return "redirect:/admin/product/productList";
	}
	
	//상품목록:페이징,검색추가
	@GetMapping("/productList") // Criteria cri = new Criteria();
	public void productList(@ModelAttribute("cri") Criteria cri, Model model) {
		
		
		log.info("검색및페이징정보: " + cri);
		
		List<ProductVO> productList = proService.getProductList(cri);
		
		// 윈도우 운영체제 경로구분자 \ (역슬래쉬).
		// 날짜폴더명의 \를 /로 변환하는 작업. \가 클라이언트에서 서버로 보내지는 데이터로 사용이 안된다.
		for(int i=0; i<productList.size(); i++) {
			String pdt_img_folder = productList.get(i).getPdt_img_folder().replace("\\", "/"); // File.serparator 운영체제 경로구분자
			productList.get(i).setPdt_img_folder(pdt_img_folder);
		}
		
		
		// 페이징쿼리에 의한 상품목록
		model.addAttribute("productList", productList);
		
		// [prev] 1	 2	3	4	5  [next]
		int totalCount = proService.getProductTotalCount(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		
		
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
	
	//상품수정 폼
	@GetMapping("/productModify")
	public void modify(@RequestParam("pdt_num") Integer pdt_num, @ModelAttribute("cri") Criteria cri, Model model) {
		
		
		log.info("상품코드: " + pdt_num);
		log.info("페이징및검색정보: " + cri);
		
		
		
		//1차카테고리 작업
		model.addAttribute("cateList", proService.getCateList());
		
		//상품정보
		ProductVO vo = proService.getProductByNum(pdt_num);
		vo.setPdt_img_folder(vo.getPdt_img_folder().replace("\\", "/"));
		
		model.addAttribute("productVO", vo);
		
		//상품정보에서 1차카테고리 코드를 참조.
		Integer cate_code_prt = vo.getCate_code_prt();
		//1차를 부모로 하는 2차카테고리 정보
		model.addAttribute("subCateList", proService.getSubCateList(cate_code_prt));
	
	}
	
	//상품수정하기.
	@PostMapping("/productModify")
	public String productModify(ProductVO vo, Criteria cri, RedirectAttributes rttr) {
		
		log.info("상품수정정보: " + vo);
		
		
		// 상품이미지 변경한 경우 - 파일업로드 
		if(!vo.getUploadFile().isEmpty()) {
			
			
			log.info("날짜폴더명: " + vo.getPdt_img_folder());
			log.info("파일명: " + vo.getPdt_img());
			
			
			//1)상품 수정전 이미지 파일삭제. (날짜폴더명, 구 이미지파일명)
			UploadFileUtils.deleteFile(uploadPath, vo.getPdt_img_folder() + "\\s_" + vo.getPdt_img());
			
			//상품수정이미지 파일 업로드구문.
			String uploadDateFolderPath = UploadFileUtils.getFolder();
			vo.setPdt_img_folder(uploadDateFolderPath); // 날짜폴더명
			vo.setPdt_img(UploadFileUtils.uploadFile(uploadPath, uploadDateFolderPath, vo.getUploadFile())); // 실제 업로드한 파일명
	
		}

		//2)상품정보수정
		proService.productModify(vo);
		
		return "redirect:/admin/product/productList" + cri.getListLink();
	}
	
	//상품삭제
	@GetMapping("/productDelete")  //상품코드, 페이지정보및검색파라미터, 날짜폴더, 파일이름
	public String delete(@RequestParam("pdt_num") Integer pdt_num, @ModelAttribute("cri") Criteria cri, String pdt_img_folder, String pdt_img) {
		
		//이미지파일삭제
		UploadFileUtils.deleteFile(uploadPath, pdt_img_folder + "\\s_" + pdt_img);
		
		proService.productDelete(pdt_num);
		
		
		return "redirect:/admin/product/productList" + cri.getListLink();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
