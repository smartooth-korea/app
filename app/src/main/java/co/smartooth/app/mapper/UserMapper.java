package co.smartooth.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import co.smartooth.app.vo.CalibrationVO;
import co.smartooth.app.vo.TeethInfoVO;
import co.smartooth.app.vo.TeethMeasureVO;
import co.smartooth.app.vo.ToothMeasureVO;
import co.smartooth.app.vo.UserVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 4. 28 ~
 */
@Mapper
public interface UserMapper {
	

	// 회원 아이디 중복 체크
	public int duplicateChkId(String userId) throws Exception;
	
	
	// 회원 등록 (회원가입)
	public void insertUserInfo(UserVO userVO) throws Exception;
	
	
	// 회원 정보 업데이트
	public void updateUserInfo(UserVO userVO) throws Exception;

	
	// 회원 번호 (회원 번호 생성 전 SEQ_NO) 조회
	public Integer selectUserSeqNo(UserVO userVO) throws Exception;
	
	
	// 회원 번호 (회원 번호 생성 후 SEQ_NO) 업데이트 
	public void updateUserSeqNo(UserVO userVO) throws Exception;

	
	// 회원 정보 조회
	public List<UserVO> selectUserInfo(UserVO userVO) throws Exception;

	
	// 회원 치아 측정 값 INSERT
	public void insertUserTeethMeasureValue(TeethMeasureVO teethMeasureVO) throws Exception;

	
	// 회원 개별 치아 측정 값 INSERT
	public void insertUserToothMeasureValue(ToothMeasureVO toothMeasureVO) throws Exception;

	
	// 회원 치아 측정 값 UPDATE
	public void updateUserTeethMeasureValue(TeethMeasureVO teethMeasureVO) throws Exception;

	
	// 회원 개별 치아 측정 값 UPDATE
	public void updateUserToothMeasureValue(ToothMeasureVO toothMeasureVO) throws Exception;
	
	
	// 회원 치아 측정 값을 저장하기 위해 현재 회원이 측정한 측정 값이 오늘 데이터인지 확인 후 값 반환(0 : 오늘X, 1: 오늘)
	public Integer selectUserTeethMeasureValueByDate(TeethMeasureVO teethMeasureVO) throws Exception;

	
	// 회원 개별 치아 측정 값을 저장하기 위해 현재 회원이 측정한 측정 값이 오늘 데이터인지 확인 후 값 반환(0 : 오늘X, 1: 오늘)
	public Integer selectUserToothMeasureValueByDate(ToothMeasureVO toothMeasureVO) throws Exception;
	
	
	// 회원 치아 상태 값 INSERT
	public void insertUserTeethInfo(TeethInfoVO teethInfoVO) throws Exception;
	
	
	// 회원의 치아 상태 값 조회
	public List<TeethInfoVO> selectUserTeethInfo(UserVO userVO) throws Exception; 
	
	
	// 회원의 치아 측정 값 조회 (기간)
	public List<TeethMeasureVO> selectUserTeethMeasureValue(TeethMeasureVO teethMeasureVO) throws Exception;
	
	
	// 회원 치아 개별 측정 값 조회 (기간)
	public List<ToothMeasureVO> selectUserToothMeasureValue(ToothMeasureVO toothMeasureVO) throws Exception;
	
	
	// 기준의 오늘날짜와 맥주소
	// 회원 장비의 캘리브레이션 측정 값을 저장하기 위해 현재 회원이 측정한 캘리브레이션 값이 오늘 데이터인지 확인 후 값 반환(0 : 오늘X, 1: 오늘)
	public Integer isExistCalibrationValueByMacAndDate(CalibrationVO calibrationVO) throws Exception;
	
	
	// 회원의 장비 - 캘리브레이션 값 INSERT
	public void insertCalibrationInfoValue(CalibrationVO calibrationVO) throws Exception;
	
	
	// 회원의 장비 - 캘리브레이션 값 INSERT
	public void updateCalibrationInfoValue(CalibrationVO calibrationVO) throws Exception;
	
	
	// 회원 비밀번호 변경(찾기)
	public void updateUserPwd(UserVO userVO) throws Exception;
	
	
	// 회원 삭제
	public void deleteUser(String userId) throws Exception;
	
	// 회원 이름 조회
    public String selectUserNm(String userId) throws Exception;
}