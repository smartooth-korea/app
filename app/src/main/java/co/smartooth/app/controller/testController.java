package co.smartooth.app.controller;

import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 4. 28 ~
 * @RestController를 쓰지 않는 이유는 몇 안되는 mapping에 jsp를 반환해줘야하는게 있으므로 @Controller를 사용한다.
 * @RestAPI로 반환해야할 경우 @ResponseBody를 사용하여 HashMap으로 return 해준다.
 */
@Controller
public class testController {
	
	@RequestMapping(value = {"/test/register.do"})
	public String regist() {
		return "/test/register";
	}

	@RequestMapping(value = {"/test/emailAuth.do"})
	public String emailAuth() {
		return "/test/emailAuth";
	}
	
	
	@RequestMapping(value = {"/test/login.do"})
	public String login() {
		return "/test/login";
	}
	
	
	@RequestMapping(value = {"/test/deviceInfo.do"})
	public String device() {
		return "/test/deviceInfo";
	}
	
	@RequestMapping(value = {"/test/selectUserInfo.do"})
	public String selectUserInfo() {
		return "/test/selectUserInfo";
	}

	@RequestMapping(value = {"/test/selectUserTeethInfo.do"})
	public String teethInfo() {
		return "/test/selectUserTeethInfo";
	}
	
	@RequestMapping(value = {"/test/selectUserTeethMeasureValue.do"})
	public String teethMeasureValue() {
		return "/test/selectUserTeethMeasureValue";
	}

	@RequestMapping(value = {"/test/selectUserToothMeasureValue.do"})
	public String selectUserToothMeasureValue() {
		return "/test/selectUserToothMeasureValue";
	}
 	
	@RequestMapping(value = {"/test/deleteUser.do"})
	public String deleteUser() {
		return "/test/deleteUser";
	}
	
	@RequestMapping(value = {"/test/insertCalibrationInfoValue.do"})
	public String insertCalibrationInfo() {
		return "/test/insertCalibrationInfoValue";
	}
	
	@RequestMapping(value = {"/test/findUserPwd.do"})
	public String findUserPwd() {
		return "/test/findUserPwd";
	}
	
	@GetMapping(value = {"/test/react.do"})
	@ResponseBody
	public String reactDemo(){
		return "Hello React";
	}

	
	
}
