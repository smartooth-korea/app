package co.smartooth.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.smartooth.app.mapper.DeviceMapper;
import co.smartooth.app.service.DeviceService;
import co.smartooth.app.vo.DeviceVO;

/**
 * 작성자 : 정주현 
 * 작성일 : 2022. 4. 28 ~
 */
@Service
public class DeviceServiceImpl implements DeviceService{

	
	@Autowired(required = false)
	DeviceMapper deviceMapper;
	
	
	// 장비 정보 INSERT
	@Override
	public void insertDeviceInfo(DeviceVO deviceVO) throws Exception {
		deviceMapper.insertDeviceInfo(deviceVO);
	}
	
	
}
