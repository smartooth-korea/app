package co.smartooth.app.service;

import org.springframework.stereotype.Service;

import co.smartooth.app.vo.DeviceVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 4. 28 ~
 */
@Service
public interface DeviceService {

	
	// 회원이 사용하는 장비 정보 INSERT
	public void insertDeviceInfo(DeviceVO deviceVO) throws Exception;
	
	
	
	
	
	
	//	회원 아이디와 비밀번호로 존재 여부 확인 :: true = 1, false = 0
	//	public int loginChkByIdPwd(DeviceVO authVO) throws Exception;
		
	//	회원 아이디가 존재하는지 여부 확인 :: true = 1, false = 0
	//	public int isIdExist(String userId) throws Exception;
		
	//	회원 로그인 처리가 정상적으로 완료될 경우, USER_AUTH_TOKEN을 UPDATE
	//	public void updateUserAuthToken(DeviceVO authVO) throws Exception;
		
	//	회원 로그인 기록 INSERT
	//	public void insertUserLoginHistory(DeviceVO authVO) throws Exception;
		
	//	회원 접속일 UPDATE
	//	public void updateLoginDt(DeviceVO authVO) throws Exception;

}