package co.smartooth.app.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.smartooth.app.mapper.UserMapper;
import co.smartooth.app.service.UserService;
import co.smartooth.app.vo.CalibrationVO;
import co.smartooth.app.vo.TeethInfoVO;
import co.smartooth.app.vo.TeethMeasureVO;
import co.smartooth.app.vo.ToothMeasureVO;
import co.smartooth.app.vo.UserVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 4. 28 ~
 */
@Service
public class UserServiceImpl implements UserService{
	
	
	@Autowired(required = false)
	UserMapper userMapper;

	
	// 회원 아이디 중복 체크
	@Override
	public int duplicateChkId(String userId) throws Exception {
		return userMapper.duplicateChkId(userId);
	}

	//	회원 등록 (회원가입)
	@Override
	public void insertUserInfo(UserVO userVO) throws Exception {
		userMapper.insertUser(userVO);
	}
	
	// 회원 정보 업데이트
	@Override
	public void updateUserInfo(UserVO userVo) throws Exception {
		userMapper.updateUserInfo(userVo);
	}
	
	// 회원 번호 (회원 번호 생성 전 SEQ_NO) 조회
	@Override
	public Integer selectUserSeqNo(UserVO userVO) throws Exception {
		return userMapper.selectUserSeqNo(userVO);
	}
	
	// 회원 번호 (회원 번호 생성 후 SEQ_NO) 업데이트 
	@Override
	public void updateUserSeqNo(UserVO userVO) throws Exception {
		userMapper.updateUserSeqNo(userVO);
	}

	// 회원 정보 조회
	@Override
	public List<UserVO> selectUserInfo(UserVO userVO) throws Exception {
		return userMapper.selectUserInfo(userVO);
	}
	
	// 회원 치아 측정 값 INSERT
	@Override
	//public void insertTeethMeasureValue(HashMap<String, Object> map) throws Exception { 
	public void insertUserTeethMeasureValue(TeethMeasureVO teethMeasureVO) throws Exception {
		userMapper.insertUserTeethMeasureValue(teethMeasureVO);
	}

	// 회원 개별 치아 측정 값 INSERT
	@Override
	public void insertUserToothMeasureValue(ToothMeasureVO toothMeasureVO) throws Exception {
		userMapper.insertUserToothMeasureValue(toothMeasureVO);
	}
	
	// 회원 치아 측정 값 입력 UPDATE
	@Override
	public void updateUserTeethMeasureValue(TeethMeasureVO teethMeasureVO) throws Exception {
		userMapper.updateUserTeethMeasureValue(teethMeasureVO);
	}
	
	// 회원 개별 치아 측정 값 UPDATE
	@Override
	public void updateUserToothMeasureValue(ToothMeasureVO toothMeasureVO) throws Exception {
		userMapper.updateUserToothMeasureValue(toothMeasureVO);
		
	}
	
	// 회원 치아 측정 값을 저장하기 위해 현재 회원이 측정한 측정 값이 오늘 데이터인지 확인 후 값 반환(0 : 오늘X, 1: 오늘)
	@Override
	public Integer selectUserTeethMeasureValueByDate(TeethMeasureVO teethMeasureVO) throws Exception {
		return userMapper.selectUserTeethMeasureValueByDate(teethMeasureVO);
	}
	
	// 회원 치아 측정 값을 저장하기 위해 현재 회원이 측정한 측정 값이 오늘 데이터인지 확인 후 값 반환(0 : 오늘X, 1: 오늘)
	@Override
	public Integer selectUserToothMeasureValueByDate(ToothMeasureVO toothMeasureVO) throws Exception {
		return userMapper.selectUserToothMeasureValueByDate(toothMeasureVO);
	}

	// 회원 치아 상태 값 INSERT
	@Override
	public void insertUserTeethInfo(TeethInfoVO teethInfoVO) throws Exception {
		userMapper.insertUserTeethInfo(teethInfoVO);
	}
	
	// 회원의 치아 상태 값 조회
	@Override
	public List<TeethInfoVO> selectUserTeethInfo(UserVO userVO) throws Exception {
		return userMapper.selectUserTeethInfo(userVO);
	}
	

	// 회원의 치아 측정 값 조회 (기간)
	@Override
	public List<TeethMeasureVO> selectUserTeethMeasureValue(TeethMeasureVO teethMeasureVO) throws Exception {
		return userMapper.selectUserTeethMeasureValue(teethMeasureVO);
	}
	
	
	// 회원 치아 개별 측정 값 조회 (기간)
	@Override
	public List<ToothMeasureVO> selectUserToothMeasureValue(ToothMeasureVO toothMeasureVO) throws Exception {
		return userMapper.selectUserToothMeasureValue(toothMeasureVO);
	}

	// 회원의 장비 - 캘리브레이션 측정 값을 저장하기 위해 현재 회원이 측정한 캘리브레이션 값이 오늘 데이터인지 확인 후 값 반환(0 : 오늘X, 1: 오늘)
	@Override
	public Integer isExistCalibrationValueByMacAndDate(CalibrationVO calibrationVO) throws Exception {
		return userMapper.isExistCalibrationValueByMacAndDate(calibrationVO);
	}
	
	// 회원 장비의 캘리브레이션 값 INSERT
	@Override
	public void insertCalibrationInfoValue(CalibrationVO calibrationVO) throws Exception {
		userMapper.insertCalibrationInfoValue(calibrationVO);
	}
	
	// 회원의 장비 - 캘리브레이션 측정 값을 저장하기 위해 현재 회원이 측정한 캘리브레이션 값이 오늘 데이터인지 확인 후 값 반환(0 : 오늘X, 1: 오늘)
	//	@Override
	//	public Integer selectUserCalibrationValueByDate(CalibrationVO calibrationVO) throws Exception {
	//		return userMapper.selectUserCalibrationValueByDate(calibrationVO);
	//	}
	
	// 회원 장비의 캘리브레이션 값 UPDATE
	@Override
	public void updateCalibrationInfoValue(CalibrationVO calibrationVO) throws Exception {
		userMapper.updateCalibrationInfoValue(calibrationVO);
	}
	
	// 회원 비밀번호 변경(찾기)
	@Override
	public void updateUserPwd(UserVO userVO) throws Exception {
		userMapper.updateUserPwd(userVO);
	}
	
	// 회원 목록 조회
	@Override
    public List<UserVO> selectUserList(UserVO userVO) throws Exception {
	    return userMapper.selectUserList(userVO);
    }
	
	
	
	
	// 회원의 삭제
	@Override
	public void deleteUser(String userId) throws Exception {
		userMapper.deleteUser(userId);
	}
}