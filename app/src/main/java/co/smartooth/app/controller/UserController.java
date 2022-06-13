package co.smartooth.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import co.smartooth.app.service.DeviceService;
import co.smartooth.app.service.MailAuthService;
import co.smartooth.app.service.UserService;
import co.smartooth.app.utils.AES256Util;
import co.smartooth.app.utils.JwtTokenUtil;
import co.smartooth.app.vo.AuthVO;
import co.smartooth.app.vo.CalibrationVO;
import co.smartooth.app.vo.DeviceVO;
import co.smartooth.app.vo.TeethInfoVO;
import co.smartooth.app.vo.TeethMeasureVO;
import co.smartooth.app.vo.ToothMeasureVO;
import co.smartooth.app.vo.UserVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 4. 28 ~
 * @RestController를 쓰지 않는 이유는 몇 안되는 mapping에 jsp를 반환해줘야하는게 있으므로 @Controller를 사용한다.
 * @RestAPI로 반환해야할 경우 @ResponseBody를 사용하여 HashMap으로 return 해준다.
 */
@Controller
public class UserController {
	
	@Autowired(required = false)
	private UserService userService;
	
	@Autowired(required = false)
	private DeviceService deviceService;
	
	@Autowired(required = false)
	private MailAuthService mailAuthService;
	
	
	/**
	 * 기능   : 회원 ID 중복 확인
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 10
	 */
	@PostMapping(value = {"/app/user/duplicateChkId.do"})
	@ResponseBody
	public HashMap<String,Object> duplicateChkId(@RequestBody HashMap<String, String> paramMap){

		HashMap<String,Object> hm = new HashMap<String,Object>();
		int isExistRow = 0;

		String userId = (String)paramMap.get("userId");
		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		if(userId == null || userId.equals("")) {
			hm.put("code", "401");
			hm.put("msg", "This ID does not exist.");
			return hm;
		}

		try {
			// 회원 아이디 중복 체크 :: 회원 테이블에 값이 존재하는지 여부 확인
			isExistRow =  userService.duplicateChkId(userId);
			if(isExistRow == 0) { // 아이디가 없을 경우 0
				hm.put("code", "000");
				hm.put("msg", "This ID is an available.");
			}else { // 등록 되어 있는 아이디가 있을 경우 1
				hm.put("code", "402");
				hm.put("msg", "This ID is already registered.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("code", "500");
			hm.put("msg", "Server Error");
		}
		
		return hm;
	}
	
	
	
	/**
	 * 기능   : 회원가입 API 
	 * 			 JSON 형식의 값을 받아 Database 에 UPDATE
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 10
	 */
	@PostMapping(value = {"/app/user/register.do"})
	@ResponseBody
	public HashMap<String,Object> registerUser(@RequestBody HashMap<String, Object> paramMap) throws Exception{
		
		// 회원 번호
		String userSeqNo = null;
		//회원 ID
		String userId = null;
		// 회원 비밀번호
		String userPwd = null;
		// 회원 이름
		String userType = null;
		// 회원 생일
		String userNm = null;
		// 회원 닉네임
		String userNickname = null;
		// 회원 타입 (개인, 단체, 그룹)
		String userBirthday = null;
		// 회원 거주 - 국
		String userCountry = null;
		// 회원 거주 - 주
		String userState = null;
		// 회원 상세 주소
		String userAddress = null;
		// 회원 전화번호
		String userTelNo = null;
		// 회원 성별
		String userSex = null;
		// 푸쉬토큰
		String pushToken = null;
		// 회원 치아 상태
		String teethStatus = null;
		
		
		int seqNo = 0;
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		TeethInfoVO teethInfoVO = new TeethInfoVO();
		UserVO userVO = new UserVO();
		
		try {
			
			userPwd = (String)paramMap.get("userPwd");
			// 비밀번호 암호화
			AES256Util aes256Util = new AES256Util();
			userPwd = aes256Util.aesEncode(userPwd);
			// 회원 구분
			userType = (String)paramMap.get("userType");
			// 회원 거주 국
			userCountry = (String)paramMap.get("userCountry");
			// 회원 거주 주
			userState = (String)paramMap.get("userState");
			
			
			userVO.setUserType(userType);
			// 회원 번호 (회원 번호 생성 전 SEQ_NO) 조회 후 생성
			// 회원번호 생성 시 +1 증감하여 별도의 테이블에 저장 (ST_SEQUENCE)
			seqNo = userService.selectUserSeqNo(userVO);
			// 회원 번호 +1 증가
			seqNo++;
			
			userVO.setSeqNo(seqNo);
			// 회원 회원번호 생성 및 등록
			// I : 개인, G : 그룹, O : 단체
			// 예) KR-I-0001
			
			if(userState == null || userState.equals("")) { // 미국이 아닐 경우 주를 붙이지 않는다
				if(userCountry.equals("US")) {  // 미국일 경우 주를 붙인다 --------------------------------------> 국가 추가해줘야함
					userSeqNo = userCountry+"-"+userState+"-"+userType+"-"+String.format("%04d", seqNo);
				}else {
					userSeqNo = userCountry+"-"+userType+"-"+String.format("%04d", seqNo);
				}
			}
			
			// 회원 ID
			userId = (String)paramMap.get("userId");
			// 회원 이름
			userNm = (String)paramMap.get("userNm");
			// 회원 닉네임
			userNickname = (String)paramMap.get("userNickname");
			// 회원 생일
			userBirthday = (String)paramMap.get("userBirthday");
			// 회원 상세주소
			userAddress = (String)paramMap.get("userAddress");
			// 회원 전화번호
			userTelNo = (String)paramMap.get("userTelNo");
			// 회원 성별
			userSex = (String)paramMap.get("userSex");
			// 푸쉬토큰
			pushToken = (String)paramMap.get("pushToken");
			
			userVO.setUserId(userId);
			userVO.setUserNo(userSeqNo);
			userVO.setUserPwd(userPwd);
			userVO.setUserNm(userNm);
			userVO.setUserNickname(userNickname);
			userVO.setUserType(userType);
			userVO.setUserBirthday(userBirthday);
			userVO.setUserCountry(userCountry);
			userVO.setUserState(userState);
			userVO.setUserAddress(userAddress);
			userVO.setUserTelNo(userTelNo);
			userVO.setUserSex(userSex);
			userVO.setPushToken(pushToken);
			
			teethInfoVO.setUserId(userId);
			teethInfoVO.setUserNo(userSeqNo);
			teethInfoVO.setTeethStatus(teethStatus);
			
			//	회원 등록 (회원가입)
			userService.insertUserInfo(userVO);
			// 회원 치아 상태 INSERT
			userService.insertUserTeethInfo(teethInfoVO);
			// 회원 번호 (회원 번호 생성 후 SEQ_NO) 업데이트 
			userService.updateUserSeqNo(userVO);
			
			hm.put("code", "000");
			hm.put("msg", "Success");
		} catch (Exception e) {
			hm.put("code", "500");
			hm.put("msg", "Server Error");
			e.printStackTrace();
		}
		return hm;
	}
	
	
	
	/**
	 * 기능   : 회원의 치아 상태 값 등록
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 20
	 */
	@PostMapping(value = {"/app/user/insertUserTeethInfo.do"})
	@ResponseBody
	public HashMap<String,Object> insertUserTeethInfo(@RequestBody HashMap<String, Object> paramMap) {
		
		String userId = null;
		String userNo = null;
		String teethStatus = null;
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		TeethInfoVO teethInfoVO = new TeethInfoVO();

		userId = (String)paramMap.get("userId");
		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		if(userId == null || userId.equals("") || userId.equals(" ")) {
			hm.put("code", "401");
			hm.put("msg", "There is no ID.");
			return hm;
		}
		userNo = (String)paramMap.get("userNo");
		teethStatus = (String)paramMap.get("teethStatus");
		
		try {
			teethInfoVO.setUserId(userId);
			teethInfoVO.setUserNo(userNo);
			teethInfoVO.setTeethStatus(teethStatus);
			// 회원 치자 상태 INSERT
			userService.insertUserTeethInfo(teethInfoVO);
			hm.put("code", "000");
			hm.put("msg", "Success");
		} catch (Exception e) {
			hm.put("code", "500");
			hm.put("msg", "Server Error");
			e.printStackTrace();
		}
		return hm;
	}
	
	
	
	/**
	 * 기능   : 회원의 치아 측정 값을 등록 또는 업데이트
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 25
	 */
	@PostMapping(value = {"/app/user/insertUserMeasureValue.do"})
	@ResponseBody
	public HashMap<String,Object> insertUserMeasureValue(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) {
		
		boolean tokenValidation = false;
		
		String userId = null;
		String userNo = null;
		String userAuthToken = null;
		String t01,t02,t03,t04,t05,t06,t07,t08,t09,t10,t11 = null;
		String t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22 = null;
		String t23,t24,t25,t26,t27,t28,t29,t30,t31,t32 = null;
		
		int isExistRow = 0;
		
		HashMap<String,Object> hm = new HashMap<String,Object>();

		TeethMeasureVO teethMeasureVO = new TeethMeasureVO();

		// 오늘 날짜 구하기 (SYSDATE)
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String sysDate = now.format(formatter);
		
		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		userId= (String)paramMap.get("userId");
		if(userId == null || userId.equals("") || userId.equals(" ")) {
			hm.put("code", "401");
			hm.put("msg", "This ID does not exist.");
			return hm;
		}
		
		t01 = (String)paramMap.get("t01");
		t02 = (String)paramMap.get("t02");
		t03 = (String)paramMap.get("t03");
		t04 = (String)paramMap.get("t04");
		t05 = (String)paramMap.get("t05");
		t06 = (String)paramMap.get("t06");
		t07 = (String)paramMap.get("t07");
		t08 = (String)paramMap.get("t08");
		t09 = (String)paramMap.get("t09");
		t10 = (String)paramMap.get("t10");
		t11 = (String)paramMap.get("t11");
		t12 = (String)paramMap.get("t12");
		t13 = (String)paramMap.get("t13");
		t14 = (String)paramMap.get("t14");
		t15 = (String)paramMap.get("t15");
		t16 = (String)paramMap.get("t16");
		t17 = (String)paramMap.get("t17");
		t18 = (String)paramMap.get("t18");
		t19 = (String)paramMap.get("t19");
		t20 = (String)paramMap.get("t20");
		t21 = (String)paramMap.get("t21");
		t22 = (String)paramMap.get("t22");
		t23 = (String)paramMap.get("t23");
		t24 = (String)paramMap.get("t24");
		t25 = (String)paramMap.get("t25");
		t26 = (String)paramMap.get("t26");
		t27 = (String)paramMap.get("t27");
		t28 = (String)paramMap.get("t28");
		t29 = (String)paramMap.get("t29");
		t30 = (String)paramMap.get("t30");
		t31 = (String)paramMap.get("t31");
		t32 = (String)paramMap.get("t32");
		
		userNo = (String)paramMap.get("userNo");
		
		userAuthToken = request.getHeader("Authorization");
		// TOKEN 검증
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
		
		if(tokenValidation) {
			try {
				
				teethMeasureVO.setT01(t01); teethMeasureVO.setT02(t02); teethMeasureVO.setT03(t03);
				teethMeasureVO.setT04(t04); teethMeasureVO.setT05(t05); teethMeasureVO.setT06(t06);
				teethMeasureVO.setT07(t07); teethMeasureVO.setT08(t08); teethMeasureVO.setT09(t09);
				teethMeasureVO.setT10(t10); teethMeasureVO.setT11(t11); teethMeasureVO.setT12(t12);
				teethMeasureVO.setT13(t13); teethMeasureVO.setT14(t14); teethMeasureVO.setT15(t15);
				teethMeasureVO.setT16(t16); teethMeasureVO.setT17(t17); teethMeasureVO.setT18(t18);
				teethMeasureVO.setT19(t19); teethMeasureVO.setT20(t20); teethMeasureVO.setT21(t21);
				teethMeasureVO.setT22(t22); teethMeasureVO.setT23(t23); teethMeasureVO.setT24(t24);
				teethMeasureVO.setT25(t25); teethMeasureVO.setT26(t26); teethMeasureVO.setT27(t27);
				teethMeasureVO.setT28(t28); teethMeasureVO.setT29(t29); teethMeasureVO.setT30(t30);
				teethMeasureVO.setT31(t31); teethMeasureVO.setT32(t32);
				
				teethMeasureVO.setUserId(userId);
				teethMeasureVO.setUserNo(userNo);
				teethMeasureVO.setMeasureDt(sysDate);
				
				// 회원 치아 측정 값을 저장하기 위해 현재 회원이 측정한 측정 값이 오늘 데이터인지 확인 후 값 반환(0 : 오늘X / 1: 오늘)
				isExistRow = userService.selectUserTeethMeasureValueByDate(teethMeasureVO);
				if(isExistRow == 0){ // 0일 경우는 Database에 값이 없는 경우 ::: INSERT
					userService.insertUserTeethMeasureValue(teethMeasureVO);
				}else { // 1이상일 경우 Database에 값이 있는 경우 ::: UPDATE
					userService.updateUserTeethMeasureValue(teethMeasureVO);
				}
				hm.put("code", "000");
				hm.put("msg", "Success");
				
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "Server Error");
				e.printStackTrace();
			}
		}else {
			hm.put("code", "400");
			hm.put("msg", "The token is not valid.");
		}
		return hm;
	}
	
	
	
	/**
	 * 기능   : 회원의 치아 상태 값 조회
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 20
	 */
	@PostMapping(value = {"/app/user/selectUserTeethInfo.do"})
	@ResponseBody
	public HashMap<String,Object> selectUserTeethInfo(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) {
		
		
		boolean tokenValidation = false; 
		
		String userId = null;
		String userAuthToken = null;
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<TeethInfoVO> userTeethInfo = new ArrayList<TeethInfoVO>();
		
		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		userId= (String)paramMap.get("userId");
		if(userId == null || userId.equals("") || userId.equals(" ")) {
			hm.put("code", "401");
			hm.put("msg", "This ID does not exist.");
			return hm;
		}
		
		userAuthToken = request.getHeader("Authorization");
		// JWT TOKEN 검증
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
		
		if(tokenValidation) {

			try {
				UserVO userVO = new UserVO();
				userVO.setUserId((String)paramMap.get("userId"));
				userVO.setUserNo((String)paramMap.get("userNo"));
				// 회원의 치아 상태 값 조회
				userTeethInfo = userService.selectUserTeethInfo(userVO);
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "Server Error");
				e.printStackTrace();
			}
			hm.put("userTeethInfo", userTeethInfo);
			hm.put("code", "000");
			hm.put("msg", "Success");
		}else {
			hm.put("code", "400");
			hm.put("msg", "The token is not valid.");
		}
		return hm;
	}
	
	
	
	/**
	 * 기능   : 회원의 치아 측정 값 조회
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 16
	 *       	 기간  조회 (startDt, endDt)
	 */
	@PostMapping(value = {"/app/user/selectUserTeethMeasureValue.do"})
	@ResponseBody
	public HashMap<String,Object> selectUserMeasureValue(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		
		boolean tokenValidation = false; 
		
		String userId = null;
		String userAuthToken = null;
		String startDt = null;
		String endDt = null;
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<TeethMeasureVO> userTeethValues = new ArrayList<TeethMeasureVO>();
		TeethMeasureVO teethInfoVO = new TeethMeasureVO();

		userId = (String)paramMap.get("userId");
		startDt = (String)paramMap.get("startDt");
		endDt = (String)paramMap.get("endDt");

		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		userId= (String)paramMap.get("userId");
		if(userId == null || userId.equals("") || userId.equals(" ")) {
			hm.put("code", "401");
			hm.put("msg", "This ID does not exist.");
			return hm;
		}
		
		
		userAuthToken = request.getHeader("Authorization");
		// TOKEN 검증
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
		
		if(tokenValidation) {
			try {
				teethInfoVO.setUserId(userId);
				teethInfoVO.setStartDt(startDt);
				teethInfoVO.setEndDt(endDt);
				// 회원의 치아 측정 값 조회 (기간)
				userTeethValues = userService.selectUserTeethMeasureValue(teethInfoVO);
				hm.put("userTeethValues", userTeethValues);
				hm.put("code", "000");
				hm.put("msg", "Success");
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "Server Error");
				e.printStackTrace();
			}
		}else {
			hm.put("code", "400");
			hm.put("msg", "The token is not valid.");
		}
		return hm;
	}
	
	
	
	/**
	 * 기능   : 회원 치아 개별 측정 값 조회 (기간)
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 26
 	 *		      기간  조회 (startDt, endDt)
	 */
	@PostMapping(value = {"/app/user/selectUserToothMeasureValue.do"})
	@ResponseBody
	public HashMap<String,Object> selectUserMeasureToothValue(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		boolean tokenValidation = false; 
		
		int isExistRow = 0;
		String userId = null;
		String userNo = null;
		String userAuthToken = null;
		String toothNo = null;
		String toothValue = null;
		String startDt = null;
		String endDt = null;
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<ToothMeasureVO> userToothValues = new ArrayList<ToothMeasureVO>();
		List<TeethMeasureVO> userTeethValues = new ArrayList<TeethMeasureVO>();
		ToothMeasureVO toothMeasureVO = new ToothMeasureVO();
		TeethMeasureVO teethMeasureVO = new TeethMeasureVO();
		
		// 오늘 날짜 구하기 (SYSDATE)
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String sysDate = now.format(formatter);
		
		userId = (String)paramMap.get("userId");

		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		userId= (String)paramMap.get("userId");
		if(userId == null || userId.equals("") || userId.equals(" ")) {
			hm.put("code", "401");
			hm.put("msg", "This ID does not exist.");
			return hm;
		}
		
		userAuthToken = request.getHeader("Authorization");
		
		// TOKEN 검증
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
		
		if(tokenValidation) {
			
			try {
				userNo = (String)paramMap.get("userNo");
				// 치아 번호
				toothNo = (String)paramMap.get("toothNo");
				// 치아 측정 값
				toothValue = (String)paramMap.get("toothValue");
				// 검색 기간
				startDt = (String)paramMap.get("startDt");
				endDt = (String)paramMap.get("endDt");
				
				toothMeasureVO.setUserId(userId);
				toothMeasureVO.setUserNo(userNo);
				toothMeasureVO.setStartDt(startDt);
				toothMeasureVO.setEndDt(endDt);
				toothMeasureVO.setToothNo(toothNo);
				toothMeasureVO.setMeasureDt(sysDate);
			
				if(toothValue != null && !toothValue.equals("") && !toothValue.equals(" ")) { // 값이 있으면 업데이트 
					// 측정값을 입력할 당시, 오늘 날짜와 비교한 후 오늘 일 경우 업데이트, 아닐 경우 INSERT해줘야함
					toothMeasureVO.setToothValue(Integer.parseInt(toothValue));
					isExistRow = userService.selectUserToothMeasureValueByDate(toothMeasureVO);
					if(isExistRow == 0) {
						userService.insertUserToothMeasureValue(toothMeasureVO);
					}else {
						userService.updateUserToothMeasureValue(toothMeasureVO);
					}
				}
					
				// 회원 치아 개별 측정 값 조회 (기간)
				userToothValues = userService.selectUserToothMeasureValue(toothMeasureVO);

				teethMeasureVO.setUserId(userId);
				teethMeasureVO.setStartDt(sysDate);
				teethMeasureVO.setEndDt(sysDate);
				
				// 회원의 32개 치아 즉정 데이터 조회(기간 : 오늘)
				userTeethValues = userService.selectUserTeethMeasureValue(teethMeasureVO);
				
				hm.put("userToothValues", userToothValues);
				hm.put("userTeethValues", userTeethValues);
				hm.put("code", "000");
				hm.put("msg", "Success");
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "Server Error");
				e.printStackTrace();
			}
		}else {
			hm.put("code", "400");
			hm.put("msg", "The token is not valid.");
		}
		return hm;
	}
	
	
	
	/**
	 * 기능   : 회원 정보(개인정보) 조회
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 25
	 */
	@PostMapping(value = {"/app/user/selectUserInfo.do"})
	@ResponseBody
	public HashMap<String,Object> selectUserInfo(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		boolean tokenValidation = false; 
		
		String userId = null;
		String userNo = null;
		String userAuthToken = null;
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<UserVO> userInfo = new ArrayList<UserVO>();
		UserVO userVO = new UserVO();
		
		userId = (String)paramMap.get("userId");
		
		
		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		userId= (String)paramMap.get("userId");
		if(userId == null || userId.equals("") || userId.equals(" ")) {
			hm.put("code", "401");
			hm.put("msg", "This ID does not exist.");
			return hm;
		}
		userNo = (String)paramMap.get("userNo");
		userAuthToken = request.getHeader("Authorization");
		// TOKEN 검증
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
		
		if(tokenValidation) {
			try {
				userVO.setUserId(userId);
				userVO.setUserNo(userNo);
				// 회원 정보 조회
				userInfo = userService.selectUserInfo(userVO);
				hm.put("userInfo", userInfo);
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "Server Error");
				e.printStackTrace();
			}
		}else {
			hm.put("code", "400");
			hm.put("msg", "The token is not valid.");
		}
		return hm;
	}
	
	
	
	/**
	 * 기능   : 회원 정보(개인정보) 수정
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 30
	 */
	@PostMapping(value = {"/app/user/updateUserInfo.do"})
	@ResponseBody
	public HashMap<String,Object> updateUserInfo(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		boolean tokenValidation = false; 
		String userId = null;
		String userNo = null;
		String userAuthToken = null;
		String userNm = null;
		String userNickname = null;
		String userBirthday = null;
		String userCountry = null;
		String userState = null;
		String userAddress = null;
		String userTelNo = null;
		String userSex = null;

		HashMap<String,Object> hm = new HashMap<String,Object>();
		UserVO userVO = new UserVO();
		
		userId = (String)paramMap.get("userId");
		
		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		userId= (String)paramMap.get("userId");
		if(userId == null || userId.equals("") || userId.equals(" ")) {
			hm.put("code", "401");
			hm.put("msg", "This ID does not exist.");
			return hm;
		}
		
		userAuthToken = request.getHeader("Authorization");
		// TOKEN 검증
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
		
		if (tokenValidation) {
			
			try {
				userNm = (String)paramMap.get("userNm");
				userNickname = (String)paramMap.get("userNickname");
				userBirthday = (String)paramMap.get("userBirthday");
				userCountry = (String)paramMap.get("userCountry");
				userState = (String)paramMap.get("userState");
				userAddress = (String)paramMap.get("userAddress");
				userTelNo = (String)paramMap.get("userTelNo");
				userSex = (String)paramMap.get("userSex");
				
				userVO.setUserId(userId);
				userVO.setUserNo(userNo);
				userVO.setUserNm(userNm);
				userVO.setUserNickname(userNickname);
				userVO.setUserBirthday(userBirthday);
				userVO.setUserCountry(userCountry);
				userVO.setUserState(userState);
				userVO.setUserAddress(userAddress);
				userVO.setUserTelNo(userTelNo);
				userVO.setUserSex(userSex);
				
				userService.updateUserInfo(userVO);
				
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "Server Error");
				e.printStackTrace();
			}
		}else {
			hm.put("code", "400");
			hm.put("msg", "The token is not valid.");
		}
		return hm;
	}
	
	
	
	/**
	 * 기능   : 회원의 사용하는 장비 정보 INSERT
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 19
	 */
	@PostMapping(value = {"/app/user/insertDeviceInfo.do"})
	@ResponseBody
	public HashMap<String,Object> insertDeviceInfo(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		boolean tokenValidation = false;
		String userAuthToken = null;
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		DeviceVO deviceVO = new DeviceVO();
		deviceVO.setUserId((String)paramMap.get("userId"));
		deviceVO.setUserNo((String)paramMap.get("userNo"));
		deviceVO.setDeviceNm((String)paramMap.get("deviceNm"));
		deviceVO.setDeviceCode((String)paramMap.get("deviceCode"));
		deviceVO.setSerialNo((String)paramMap.get("serialNo"));
		deviceVO.setMacAddress((String)paramMap.get("macAddress"));
		
		userAuthToken = request.getHeader("Authorization");
		// TOKEN 검증
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
		
		if (tokenValidation) {
			try {
				// 회원이 사용하는 장비 정보 INSERT
				deviceService.insertDeviceInfo(deviceVO);
				hm.put("code", "000");
				hm.put("msg", "Registration Successful");
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "Server Error");
				e.printStackTrace();
			}
		}else {
			hm.put("code", "400");
			hm.put("msg", "The token is not valid.");
		}
		return hm;
	}
	
	
	
	
	/**
	 * 기능   : 회원의 장비 - 캘리브레이션 값 INSERT 또는 UPDATE
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 19
	 */												
	@PostMapping(value = {"/app/user/insertCalibrationInfoValue.do"})
	@ResponseBody
	public HashMap<String,Object> insertCalibrationInfoValue(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		boolean tokenValidation = false;
		String userId = null;
		String userNo = null;
		String calibrationAir = null;
		String calibrationKit = null;
		String calibrationRef = null;
		String macAddress = null;
		String userAuthToken = null;
		
		int isExistRow = 0;
		
		// 오늘 날짜 구하기 (SYSDATE)
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String sysDate = now.format(formatter);
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		CalibrationVO calibrationVO = new CalibrationVO();
		
		// Parameter = userId 값 검증 (Null 체크 및 공백 체크)
		userId= (String)paramMap.get("userId");
		if(userId == null || userId.equals("") || userId.equals(" ")) {
			hm.put("code", "401");
			hm.put("msg", "This ID does not exist.");
			return hm;
		}
		
		userId= (String)paramMap.get("userId");
		userNo= (String)paramMap.get("userNo");
		calibrationAir= (String)paramMap.get("calibrationAir");
		calibrationKit= (String)paramMap.get("calibrationKit");
		calibrationRef= (String)paramMap.get("calibrationRef");
		macAddress = ((String)paramMap.get("macAddress"));
		
		userAuthToken = request.getHeader("Authorization");
		// TOKEN 검증
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		tokenValidation = jwtTokenUtil.validateToken(userAuthToken);
		
		if (tokenValidation) {
			calibrationVO.setUserId(userId);
			calibrationVO.setUserNo(userNo);
			calibrationVO.setCalibrationAir(calibrationAir);
			calibrationVO.setCalibrationKit(calibrationKit);
			calibrationVO.setCalibrationRef(calibrationRef);
			calibrationVO.setCalibrationDt(sysDate);
			calibrationVO.setMacAddress(macAddress);
			
			try {
				// 맥주소와 날짜로 기존에 캘리브레이션 값이 존재하는 지 여부 확인
				isExistRow = userService.isExistCalibrationValueByMacAndDate(calibrationVO);
				if(isExistRow == 0) {
					userService.insertCalibrationInfoValue(calibrationVO);
				}else{
					userService.updateCalibrationInfoValue(calibrationVO);
				}
				
				hm.put("code", "000");
				hm.put("msg", "Registration Successful");
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "Server Error");
				e.printStackTrace();
			}
		}else {
			hm.put("code", "400");
			hm.put("msg", "The token is not valid.");
		}
		return hm;
	}
	
	
	
	
	/**
	 * 기능   : 비밀번호 찾기 (이메일 발송)
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 6. 9
	 */
	@PostMapping(value = {"/app/user/findUserPwd.do"})
	@ResponseBody
		public HashMap<String,Object> resetPassword(@RequestBody HashMap<String, String> paramMap) {

		int isExistId = 0;
		String userId = null;
		String emailAuthKey = null;
		HashMap<String,Object> hm = new HashMap<String,Object>();
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		
		
		// Parameter :: userId 값 검증
		userId = (String)paramMap.get("userId");
		// (Null 체크 및 공백 체크)
		if(userId == null || userId.equals("")) {
			hm.put("code", "401");
			hm.put("msg", "There is no ID.");
			return hm;
		}
		
		emailAuthKey = jwtTokenUtil.createToken(userId);
		
		try {
			// 아이디 중복 체크 :: ID가 없을 경우 0, ID가 있을 경우 1
			isExistId = userService.duplicateChkId(userId);
		} catch (Exception e) {
			hm.put("code", "500");
			hm.put("msg", "Server Error");
			e.printStackTrace();
		}

		if (isExistId == 1) { // 아이디가 있는 경우 메일 발송
			try {
				// 이메일 안에 비밀번호 변경 url을 전송하도록 함
				mailAuthService.sendResetPasswordMail(userId, emailAuthKey);
			} catch (Exception e) {
				hm.put("code", "500");
				hm.put("msg", "Server Error");
				e.printStackTrace();
				return hm;
			}
			hm.put("code", "000");
			hm.put("msg", "Mail Sent Completed.");
		} else { // 아이디가 없을 경우 JSON code 및 msg RETURN
			hm.put("code", "405");
			hm.put("msg", "This ID does not exist.");
		}
		return hm;
	}	
	
	
	
	/**
	 * 기능   : 회원 비밀번호 UPDATE
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 6. 9
	 */
	@PostMapping(value = {"/app/user/updateUserPwd.do"})
	@ResponseBody
	public HashMap<String,Object> updateUserPwd(@RequestBody HashMap<String, String> paramMap) {
		
		
		String userId = null;
		String userPwd = null;
		HashMap<String,Object> hm = new HashMap<String,Object>();
		UserVO userVO = new UserVO();
		
		// Parameter :: userId 값 검증
		userId = (String)paramMap.get("userId");
		// (Null 체크 및 공백 체크)
		if(userId == null || userId.equals("")) {
			hm.put("code", "401");
			hm.put("msg", "There is no ID.");
			return hm;
		}
		
		// 비밀번호 암호화
		userPwd = (String)paramMap.get("userPwd");
		AES256Util aes256Util = new AES256Util();
		userPwd = aes256Util.aesEncode(userPwd);
		
		userVO.setUserId(userId);
		userVO.setUserPwd(userPwd);
		
		try {
			userService.updateUserPwd(userVO);
			hm.put("code", "000");
			hm.put("msg", "Password change complete.");
		} catch (Exception e) {
			hm.put("code", "500");
			hm.put("msg", "Server Error");
			e.printStackTrace();
		}
		
		return hm;
	}	

	
	
	
	
	
	/**
	 * 기능   : 회원 삭제
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 5. 24
	 */
	@PostMapping(value = {"/app/user/deleteUser.do"})
	@ResponseBody
	public HashMap<String,Object> deleteUser(@RequestBody HashMap<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		String userId = (String)paramMap.get("userId");
		String userAuthToken = request.getHeader("Authorization");
		String userPwd = (String)paramMap.get("userPwd");
		
		// 회원 인증 필요 - token이나 비밀번호 사용
		// 비밀번호 암호화 
		AES256Util aes256Util = new AES256Util();
		userPwd = aes256Util.aesEncode(userPwd);
		
		AuthVO authVO = new AuthVO();
		
		authVO.setUserId(userId);
		authVO.setUserPwd(userPwd);
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		boolean tmp = jwtTokenUtil.validateToken(userAuthToken);
		
//		try {
//			// 로그인 확인
//			loginChkByIdPwd = authService.loginChkByIdPwd(authVO);
//			if (loginChkByIdPwd == 0) { // 0일 경우는 Database에 ID와 비밀번호가 틀린 것
//				// 아이디와 비밀번호 어떤게 틀린 것인지 확인하고자함
//				isIdExist = authService.isIdExist(authVO.getUserId());
//				if (isIdExist == 0) {
//					hm.put("code", "405");
//					hm.put("msg", "This ID does not exist.");
//				} else {
//					hm.put("code", "406");
//					hm.put("msg", "The password is wrong.");
//				}
//			} else { // 로그인이 정상적으로 완료된 경우 회원의 정보를 제공하고 LOG를 INSERT
//				// 회원 삭제
//				userService.deleteUser(userId);
//				hm.put("code", "000");
//				hm.put("msg", "deleted");
//			}
//		} catch (Exception e) {
//			hm.put("code", "500");
//			hm.put("msg", "Server error");
//			e.printStackTrace();
//		}
		return hm;
	}
	
	
	
	
	
	
/*************************
	 RequestMapping 설정 시 
	 앱에서의 요청이 있을 경우 /app/*
	 웹에서의 요청이 있을 경우 /web/*
	 
*************************/
	
	
	/**
	 * WEB 용
	 * 기능   : 웹에서 요청한 유저에 대한 ...
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 4. 27
	 * return : HashMap<String,Object>
	 * @throws UnsupportedEncodingException 
	 */
//	@GetMapping(value = {"/user.do"})
//	  public String userList(HttpServletRequest request, Model model) throws Exception {
//		model.addAttribute("headerCategoriesName","회원관리");
//		model.addAttribute("kategorieName", "회원관리 > 개인");
//		System.out.println("user.do");
//	    return "/user/userManageIndividual";
//	  }
	
	
	/**
	 * WEB 용
	 * 기능   : 웹에서 요청한 그룹에 대한 ...
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 4. 27
	 * return : HashMap<String,Object>
	 * @throws UnsupportedEncodingException 
	 */
//	@GetMapping(value = {"/group.do"})
//	  public String groupList(HttpServletRequest request, Model model) throws Exception {
//		String gubn = request.getParameter("gubn");
//		model.addAttribute("headerCategoriesName","회원관리");
//		model.addAttribute("title","usermanage");
//		System.out.println("group.do");
//		
//		if(gubn.equals("A")) {
//			model.addAttribute("headerCategoriesName", "회원관리 > 그룹 > A유치원");
//			return "/user/userManageGroupA";
//		}else if(gubn.equals("B")) {
//			model.addAttribute("headerCategoriesName", "회원관리 > 그룹 > B유치원");
//			return "/user/userManageGroupB";
//		}else {
//			model.addAttribute("headerCategoriesName", "회원관리 > 그룹 > C유치원");
//			return "/user/userManageGroupC";
//		}
//	  }
	
	
}
