package com.docmall.util;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.xml.ws.spi.http.HttpHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

public class UploadFileUtils {

	
	// 업로드 작업
	// 업로드시 날짜폴더생성해서 파일관리.
	// Thumnail이미지 생성 - 업로드한 파일이 이미지 또는 일반파일인지 체크
	// byte[]배열로 다운로드.
	
	
	//1)파일업로드 작업. 리턴값: 업로드한 실제파일명(DB저장). 
	public static String uploadFile(String uploadFolder,String uploadDateFolderPath, MultipartFile uploadFile) {
		
		String uploadFileName = ""; //실제 업로드한 파일명
		
		//1)업로드 날짜폴더 생성
		//String uploadDateFolderPath = getFolder();// "2022\07\19"
		File uploadPath = new File(uploadFolder,  uploadDateFolderPath); // C:\\Dev\\upload\\2022\\07\\19
		
		//폴더 존재여부
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs(); // 하위디렉토리까지 생성
		}
		
		// 클라이언트에서 업로드한 파일명.
		String uploadClientFileName = uploadFile.getOriginalFilename();
		
		//중복되지 않는 고유의 문자열 생성
		UUID uuid = UUID.randomUUID();
		
		// 업로드시 중복되지 않는 파일이름을 생성.
		uploadFileName = uuid.toString() + "_" + uploadClientFileName;
		
		try {
			
			//유일한 파일이름으로 객체생성
			File saveFile = new File(uploadPath, uploadFileName);
			uploadFile.transferTo(saveFile); // 파일업로드 됨(파일복사)
			
			if(checkImageType(saveFile)) {
				
				//섬네일 작업 : 원본이미지를 대상으로 사본이미지를 해상도의 손실을 줄이고, 크기를 작게 복사한다. 출력스트림 객체생성하면, 파일이 생성됨.
				FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));	
				Thumbnailator.createThumbnail(uploadFile.getInputStream(), thumbnail, 100, 100);
				thumbnail.close();
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		return uploadFileName; // 실제 업로드한 파일명(날짜폴더명 제외)
	}
	
	//날짜를 이용한 업로드 폴더생성및 폴더이름 반환
	public static String getFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);  // "2022-07-19"
		
		// File.separator : 운영체제에 따라서 파일경로 구분자를 반환. 예>윈도우 c:\temp\...  리눅스 /home/etc/..
		
		return str.replace("-", File.separator); // "2022-07-19" -> "2022\07\19"
	}
	
	//이미지 파일여부를 체크.
	private static boolean checkImageType(File saveFile) {
		
		boolean isImage = false;
		
		try {
			String contentType = Files.probeContentType(saveFile.toPath()); // text/html, text/plain, image/jpeg
		
			isImage = contentType.startsWith("image");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return isImage;
	}
	
	//이미지를 바이트배열로 읽어오는 작업
	public static ResponseEntity<byte[]> getFile(String uploadPath, String fileName){

		File file = new File(uploadPath, fileName); // 이미지파일 정보를 이용하여 File객체
		
		// 이미지 파일명이 존재하지 않을 경우
		if(!file.exists()) {
			uploadPath = "C:\\Dev\\upload\\imsi\\";
			fileName = "s_no_images.png";
			
			file = new File(uploadPath, fileName);
		}
		
		ResponseEntity<byte[]> entity = null;
		
		//브라우저에게 서버에서 보내는 데이타에 대한 설명
		HttpHeaders headers = new HttpHeaders();
		
		try {
			// 브라우저에게 보낼 데이타의 MIME정보(image/png, image/jpeg, image/gif 등) 패킷의 헤더부분에 추가
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return entity;
	}
	
	//파일삭제
	public static void deleteFile(String uploadPath, String fileName) {
		
		//윈도우 운영체제 : 경로구분자. \(역슬래쉬)
		//원본이미지, 썸네일이미지 : 파일 2개
		
		/*
		 uploadPath : c:\\dev\\upload
		 fileName : 2022/07/21/s_15a976a9-7cff-4ef9-b720-3fc9c7ddfa5f_1.jfif
		 */
		
		String front = fileName.substring(0, 11); // 2022/07/21/
		String end = fileName.substring(13); // 15a976a9-7cff-4ef9-b720-3fc9c7ddfa5f_1.jfif
		String origin = front + end; // 2022/07/21/15a976a9-7cff-4ef9-b720-3fc9c7ddfa5f_1.jfif
		
		// 파일명에 s_ 있고, 없고로 구분한다.
		//원본이미지 삭제.  2022/07/21/15a976a9-7cff-4ef9-b720-3fc9c7ddfa5f_1.jfif
		new File(uploadPath+origin.replace('/', File.separatorChar)).delete();
		
		//썸네일파일삭제.  2022/07/21/s_15a976a9-7cff-4ef9-b720-3fc9c7ddfa5f_1.jfif
		new File(uploadPath+fileName.replace('/', File.separatorChar)).delete();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
