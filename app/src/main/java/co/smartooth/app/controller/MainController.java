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
public class MainController {
	
	@GetMapping("/")
	public String main() {
		
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("test", "hello log4");
		logger.toString();
		return "test";
	}
}
