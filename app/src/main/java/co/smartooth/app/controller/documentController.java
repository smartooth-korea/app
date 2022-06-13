package co.smartooth.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 4. 28 ~
 */
@Controller
public class documentController {
	
	// 이용약관 (한글)
	@GetMapping(value = {"app/document/termsOfServiceKo.do"})
	public String termsOfServiceKo() {
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("Call document 'Terms Of Service (KOR)'", "terms of service");
		logger.toString();
		return "/document/termsOfService_ko";
	}

	// 이용약관 (영문)
	@GetMapping(value = {"/app/document/termsOfServiceEn.do"})
	public String termsOfServiceEn() {
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("Call document 'Terms Of Service (ENG)'", "terms of service");
		logger.toString();
		return "/document/termsOfService_en";
	}
	
}