package co.smartooth.app.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.smartooth.app.service.AuthService;
import co.smartooth.app.service.DeviceService;
import co.smartooth.app.service.LogService;
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
@RestController
public class tempController {
	
	@Autowired(required = false)
	private UserService userService;
	
	@Autowired(required = false)
    private AuthService authService;
	
	@Autowired(required = false)
	private LogService logService;
	
	
	@CrossOrigin(origins = "http://13.124.37.209:3000")
//	@CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = {"/web/login.do"})
    @ResponseBody
    public HashMap<String,Object> webLogin(@RequestBody HashMap<String, Object> paramMap) {
       
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.debug("LoginController >>> Web >>> login.do");
        
        String userPwd = null;
        String userId = null;
        String loginIp = null;
        String userAuthToken = null;
        
        int loginChkByIdPwd = 0;
        int isIdExist = 0;
        
        HashMap<String,Object> hm = new HashMap<String,Object>();
        
        List<UserVO> userInfo = new ArrayList<UserVO>();
        List<TeethInfoVO> userTeethInfo = new ArrayList<TeethInfoVO>();
        List<TeethMeasureVO> userMeasureValueList = new ArrayList<TeethMeasureVO>();
        
        AuthVO authVO = new AuthVO();
        UserVO userVO = new UserVO();
        
        
        // 오늘 일자 계산
        Date tmpDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        String sysDate = sdf.format(tmpDate);
        
        // 파라미터 >> 값 setting
        userId= (String)paramMap.get("userId");
        loginIp = (String)paramMap.get("loginIp"); 
        
        // 비밀번호 암호화 
        AES256Util aes256Util = new AES256Util();
        userPwd = aes256Util.aesEncode((String)paramMap.get("userPwd"));
        
        if(userPwd.equals("false")) { // 암호화에 실패할 경우
            hm.put("code", "500");
            hm.put("msg", "Server Error");
            return hm;
        }
        
        // 로그인 VO
        authVO.setUserId(userId);
        authVO.setUserPwd(userPwd);
        authVO.setLoginDt(sysDate);
        // 회원 VO
        userVO.setUserId(userId);
        
        try {
            // 로그인 확인
            loginChkByIdPwd = authService.loginChkByIdPwd(authVO);
            if(loginChkByIdPwd == 0){ // 0일 경우는 Database에 ID와 비밀번호가 틀린 것

                isIdExist = authService.isIdExist(authVO.getUserId());
                if(isIdExist == 0) { // ID가 존재하지 않을 경우
                    hm.put("code", "405");
                    hm.put("msg", "This ID does not exist.");
                }else { // PWD가 틀렸을 경우
                    hm.put("code", "406");
                    hm.put("msg", "The password is wrong.");
                }
            }else { // ID와 PWD가 검증된 이후 JWT 토큰과 회원의 정보를 제공하고 LOG를 INSERT

                // JWT TOKEN 발행
                JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
                userAuthToken = jwtTokenUtil.createToken(authVO);
                
                // 로그인 일자 업데이트
                logService.updateLoginDt(authVO);
                
                // 로그인 시 회원의 정보를 가져옴 :: List<UserVO>
                userInfo = userService.selectUserInfo(userVO);
                userTeethInfo = userService.selectUserTeethInfo(userVO);
                
                // 회원의 정보를 authVO에 담고 LOG 테이블 INSERT 파라미터로 전달
                authVO.setUserNo(userInfo.get(0).getUserNo());
                authVO.setUserType(userInfo.get(0).getUserType());
                authVO.setLoginDt(userInfo.get(0).getLoginDt());
                authVO.setLoginResultCode("000");
                authVO.setLoginIp(loginIp);
                
                logService.insertUserLoginHistory(authVO);
                hm.put("userAuthToken", userAuthToken);
                //hm.put("userInfo", userInfo);
                //hm.put("userTeethInfo", userTeethInfo);
                //hm.put("userNo", userInfo.get(0).getUserNo());
                
                // 회원들의 치아 조회 값을 가져와야함
//                userMeasureValueList =  userService.selectUserMeasureValueList();
                
                hm.put("userMeasureValueList", userMeasureValueList);
                
                
                hm.put("code", "000");
                hm.put("msg", "Login Success");
            }
        } catch (Exception e) {
            hm.put("code", "500");
            hm.put("msg", "Server Error");
            e.printStackTrace();
        }
        return hm;
    }
    
	
	/**
	 * WEB 용
	 * 기능   : 회원 리스트 반환
	 * 작성자 : 정주현 
	 * 작성일 : 2022. 4. 27
	 * return : HashMap<String,Object>
	 * @throws UnsupportedEncodingException 
	 */
     @CrossOrigin(origins = "http://13.124.37.209:3000")
//	@CrossOrigin(origins = "http://localhost:3000")
     @RequestMapping(value = {"/web/main.do"})
     @ResponseBody
     public List<UserVO> selectUserList() throws Exception {
         
         HashMap<String, Object> hm = new HashMap<String, Object>();
         List<UserVO> userList = null;
         UserVO userVO = new UserVO();

         try {
             userList = userService.selectMdUserList(userVO);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return userList;
     }
     
     
     @CrossOrigin(origins = "http://13.124.37.209:3000")
//	@CrossOrigin(origins = "http://localhost:3000")
     @RequestMapping(value = {"/web/userInfo.do"})
     @ResponseBody
     public List<TeethMeasureVO> selectUserInfo(HttpServletRequest request) throws Exception {
         
         String userNm = null;
         // 충치 수치 정상 갯수
         int cavityNormal = 0;
         // 충치 수치 경고 갯수
         int cavityWarning = 0;
         // 충치 갯수
         int cavityCnt = 0;
         
         int[] teethStatus = new int[32];
         
         String userId = request.getParameter("userId");
         
         List<TeethMeasureVO> userInfo = new ArrayList<TeethMeasureVO>();
         UserVO userVO = new UserVO();
         userVO.setUserId(userId);

         try {
             userInfo = userService.selectUserMeasureValueList(userVO);
             userNm = userService.selectUserNm(userId);
             teethStatus[0] =  Integer.parseInt(userInfo.get(0).getT01());
             teethStatus[1] =  Integer.parseInt(userInfo.get(0).getT02());
             teethStatus[2] =  Integer.parseInt(userInfo.get(0).getT03());
             teethStatus[3] =  Integer.parseInt(userInfo.get(0).getT04());
             teethStatus[4] =  Integer.parseInt(userInfo.get(0).getT05());
             teethStatus[5] =  Integer.parseInt(userInfo.get(0).getT06());
             teethStatus[6] =  Integer.parseInt(userInfo.get(0).getT07());
             teethStatus[7] =  Integer.parseInt(userInfo.get(0).getT08());
             teethStatus[8] =  Integer.parseInt(userInfo.get(0).getT09());
             teethStatus[9] =  Integer.parseInt(userInfo.get(0).getT10());
             teethStatus[10] =  Integer.parseInt(userInfo.get(0).getT11());
             teethStatus[11] =  Integer.parseInt(userInfo.get(0).getT12());
             teethStatus[12] =  Integer.parseInt(userInfo.get(0).getT13());
             teethStatus[13] =  Integer.parseInt(userInfo.get(0).getT14());
             teethStatus[14] =  Integer.parseInt(userInfo.get(0).getT15());
             teethStatus[15] =  Integer.parseInt(userInfo.get(0).getT16());
             teethStatus[16] =  Integer.parseInt(userInfo.get(0).getT17());
             teethStatus[17] =  Integer.parseInt(userInfo.get(0).getT18());
             teethStatus[18] =  Integer.parseInt(userInfo.get(0).getT19());
             teethStatus[19] =  Integer.parseInt(userInfo.get(0).getT20());
             teethStatus[20] =  Integer.parseInt(userInfo.get(0).getT21());
             teethStatus[21] =  Integer.parseInt(userInfo.get(0).getT22());
             teethStatus[22] =  Integer.parseInt(userInfo.get(0).getT23());
             teethStatus[23] =  Integer.parseInt(userInfo.get(0).getT24());
             teethStatus[24] =  Integer.parseInt(userInfo.get(0).getT25());
             teethStatus[25] =  Integer.parseInt(userInfo.get(0).getT26());
             teethStatus[26] =  Integer.parseInt(userInfo.get(0).getT27());
             teethStatus[27] =  Integer.parseInt(userInfo.get(0).getT28());
             teethStatus[28] =  Integer.parseInt(userInfo.get(0).getT29());
             teethStatus[29] =  Integer.parseInt(userInfo.get(0).getT30());
             teethStatus[30] =  Integer.parseInt(userInfo.get(0).getT31());
             teethStatus[31] =  Integer.parseInt(userInfo.get(0).getT32());
             
             for(int i=0; i<teethStatus.length; i++) {
                 if(teethStatus[i]<13) {
                     cavityNormal++;
                 }else if(teethStatus[i]<13 || teethStatus[i]<19){
                     cavityWarning++;
                 }else {
                     cavityCnt++;
                 }
             }
             userInfo.get(0).setCavityNormal(Integer.toString(cavityNormal));
             userInfo.get(0).setCavityWarning(Integer.toString(cavityWarning));
             userInfo.get(0).setCavityCnt(Integer.toString(cavityCnt));
             userInfo.get(0).setUserNm(userNm);
             
         } catch (Exception e) {
             e.printStackTrace();
         }
         
         return userInfo;

     }
    
	
	
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
