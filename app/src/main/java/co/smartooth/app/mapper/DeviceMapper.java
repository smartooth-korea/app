package co.smartooth.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import co.smartooth.app.vo.DeviceVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 4. 28 ~
 */
@Mapper
public interface DeviceMapper {

	// 장비 정보 입력
	public void insertDeviceInfo(DeviceVO deviceVO) throws Exception;
	
}
