package co.smartooth.app.controller;

//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 4. 28 ~
 */
@RestController
//@Controller
public class testController {
	
	@RequestMapping(value = {"/register"})
	public String regist() {
		return "register";
	}

	@RequestMapping(value = {"/emailAuth"})
	public String emailAuth() {
		return "emailAuth";
	}
	
	
	@RequestMapping(value = {"/login"})
	public String login() {
		return "login";
	}
	
	
	@RequestMapping(value = {"/device"})
	public String device() {
		return "deviceInfo";
	}
	
	@RequestMapping(value = {"/selectUserInfo"})
	public String selectUserInfo() {
		return "selectUserInfo";
	}

	@RequestMapping(value = {"/selectUserTeethInfo"})
	public String teethInfo() {
		return "selectUserTeethInfo";
	}
	
	@RequestMapping(value = {"/teethMeasureValue"})
	public String teethMeasureValue() {
		return "teethMeasureValue";
	}

	@RequestMapping(value = {"/selectUserToothMeasureValue"})
	public String selectUserToothMeasureValue() {
		return "selectUserToothMeasureValue";
	}
 	
	@RequestMapping(value = {"/deleteUser"})
	public String deleteUser() {
		return "deleteUser";
	}
	
	@RequestMapping(value = {"/insertCalibrationInfoValue"})
	public String insertCalibrationInfo() {
		return "insertCalibrationInfoValue";
	}
	
	@RequestMapping(value = {"/findUserPwd"})
	public String findUserPwd() {
		return "findUserPwd";
	}
	
	@GetMapping(value = {"/test"})
	public String reactDemo(){
		return "Hello React";
	}

	
	
}
