package com.docmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j
// @RequiredArgsConstructor : final or @NonNull 필드를 대상으로 생성자메서드가 생성.
@Data // @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode
@AllArgsConstructor // 클래스의 모든 필드를 이용한 생성자메서드 생성
public class LoginDTO {
	
	private String mem_id;
	private String mem_pw;
	
}
