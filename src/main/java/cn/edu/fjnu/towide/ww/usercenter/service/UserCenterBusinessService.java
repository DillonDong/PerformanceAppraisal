package cn.edu.fjnu.towide.ww.usercenter.service;

import cn.edu.fjnu.towide.clw.usermodule.constant.UserAuthorityConstant;
import cn.edu.fjnu.towide.clw.usermodule.constant.UserStatusConstant;
import cn.edu.fjnu.towide.dao.AuthoritiesDao;
import cn.edu.fjnu.towide.dao.KeyVerificationCodeDao;
import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.dao.UserDetailDao;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.entity.User;
import cn.edu.fjnu.towide.entity.UserDetails;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.*;
import cn.edu.fjnu.towide.vo.UserInfoVo;
import cn.edu.fjnu.towide.ww.usercenter.constant.WeChatReasonOfFailureConstant;
import com.alibaba.fastjson.JSONObject;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayOutputStream;
import java.util.*;

import static cn.edu.fjnu.towide.clw.usermodule.constant.UserStatusConstant.ENABLE;
import static cn.edu.fjnu.towide.constant.EnrollStateConstant.ENROL_STATE_UNSUBMITTED;
import static cn.edu.fjnu.towide.constant.EnrollStateConstant.PAYMENT_COMMENT_UNPAY;
import static cn.edu.fjnu.towide.constant.EnrollStateConstant.PAYMENT_STATE_UNPAY;

@Service
public class UserCenterBusinessService {
    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDetailDao userDetailDao;
    @Autowired
    KeyVerificationCodeDao keyVerificationCodeDao;
    @Autowired
    AuthoritiesDao authoritiesDao;
    @Autowired
    PushMailUtil pushMailUtil;

    @Value("${wechat.app_id}")
    private String appId; // 微信app id
    @Value("${wechat.app_secret}")
    private String appsecret; // 微信app secret
    @Value("${wechat.get_session_key_api}")
    private String getSessionKeyAPI;

    /**
     * @Description: 返回数据封装
     */
    public void responseUtil(String dataKey, Object dataValue) {
        ResponseData responseData = dataCenterService.getData("responseData");
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        if (dataKey != null) {
            JSONObject data = new JSONObject();
            data.put(dataKey, dataValue);
            responseData.setData(data);
        }
    }

    /**
     * @description: 微信登录
     */
    public void weChatLoginVerificationRequestProcess() {
        String weChatCode = dataCenterService.getData("weChatCode");
        System.out.println("------weChatCode------" + weChatCode);

        // 获取sessionKeyAndOpenId的api
        String getSesionKeyURL = getSessionKeyAPI + "?appid=" + appId + "&secret=" + appsecret + "&js_code="
                + weChatCode + "&grant_type=authorization_code";
        System.out.println("------getSesionKeyURL---------" + getSesionKeyURL);

        // 获取到sessionKeyAndOpenId的json数据
        String returnSesionKeyAndOpendIdJson = CommonUtil.sendGetRequest(getSesionKeyURL);

        // 连接超时
        if (returnSesionKeyAndOpendIdJson == null) {
            ExceptionUtil.setFailureMsgAndThrow(cn.edu.fjnu.towide.ww.usercenter.enums.ReasonOfFailure.REQUEST_TIMED_OUT_CODE);
        }
        System.out.println("---------returnSesionKeyAndOpendIdJson--------" + returnSesionKeyAndOpendIdJson);

        JSONObject jsonObject = JSONObject.parseObject(returnSesionKeyAndOpendIdJson);
        String errcode = jsonObject.getString("errcode");
        if (WeChatReasonOfFailureConstant.ERRMSG_CODE.equals(errcode)) {
            ExceptionUtil.setFailureMsgAndThrow(cn.edu.fjnu.towide.ww.usercenter.enums.ReasonOfFailure.INVALID_CODE_CODE);
        } else if (WeChatReasonOfFailureConstant.ERRMSG_CODE_BEEN_USED_CODE.equals(errcode)) {
            ExceptionUtil.setFailureMsgAndThrow(cn.edu.fjnu.towide.ww.usercenter.enums.ReasonOfFailure.INVALID_CODE_CODE);
            return;
        }

        String openId = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");
//		String expiresIn = jsonObject.getString("expires_in");//暂时未用到
        System.out.println("-----openId--------" + openId);

        ResponseData responseData = dataCenterService.getData("responseData");


        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        ResponseDataUtil.putValueToData(responseData, "openId", openId);
    }

    public void getPhoneVerificationCodeRequestProcess() {
        String usernameOrPhone = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("usernameOrPhone");
        User user = userDao.getUserByUserNameOrPhone(usernameOrPhone);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();

        if (user == null) {
            ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.USER_DOES_NOT_EXIST);
            return;
        }

        String phoneVerificationCode = "" + Math.round(((1 + new Random().nextInt(9) + Math.random()) * 100000));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String verificationCodeAfterEncrypt = bCryptPasswordEncoder.encode(phoneVerificationCode);
        String username = user.getUsername();

        boolean result = userDao.updateVerificationCodeByUserName(username, verificationCodeAfterEncrypt);
        if (!result) {
            ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FAILED_TO_GET_VERIFICATION_CODE);
            return;
        }

        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "phoneVerificationCode", phoneVerificationCode);

        //短信接口还未开发，暂时注释， 此处添加短信发送代码，如果发送成功，才将验证码写入数据库，否则，显示验证码发送失败信息
/*		String phoneNumber=user.getPhone();
		String templateCode=SmsSendService.GET_VERIFICATION_CODE_TEMPLATE_CODE;
		JSONObject templateParamJson=new JSONObject();
		templateParamJson.put("verificationCode", verificationCode);
		String templateParam=templateParamJson.toJSONString();
		smsSendService.sendSms(phoneNumber, templateCode, templateParam);
*/

    }

    public void getLoginVerificationCodeRequestProcess() {

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();

        String key = IdGenerator.getId();
//		String verificationCode = "" + Math.round(((1 + new Random().nextInt(9) + Math.random()) * 1000));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int width = 90;
        int height = 40;
        String imgType = "jpeg";
        String verificationCode = GraphicHelper.create(width, height, imgType, output);
        byte[] verificationCodeByteArray = output.toByteArray();

        verificationCodeByteArray = Base64Utils.encode(verificationCodeByteArray);
        String verificationCodeImgBase64Encode = new String(verificationCodeByteArray);

        boolean result = keyVerificationCodeDao.addKeyVerificationCode(key, verificationCode);
        if (!result) {
            ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FAILED_TO_GET_LOGIN_VERIFICATION_CODE);
            return;
        }

        // 验证码将以图片的方式返回

        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "key", key);
        ResponseDataUtil.putValueToData(responseData, "verificationCodeImgBase64Encode", verificationCodeImgBase64Encode);
    }


    public void getCurrentLoginUserInfoRequestProcess() {

        User currentLoginUser = dataCenterService.getCurrentLoginUserFromDataLocal();

        ResponseData responseData = dataCenterService.getData("responseData");

        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        ResponseDataUtil.putValueToData(responseData, "currentLoginUser", currentLoginUser);

        //短信接口还未开发，暂时注释， 此处添加短信发送代码，如果发送成功，才将验证码写入数据库，否则，显示验证码发送失败信息
/*		String phoneNumber=user.getPhone();
		String templateCode=SmsSendService.GET_VERIFICATION_CODE_TEMPLATE_CODE;
		JSONObject templateParamJson=new JSONObject();
		templateParamJson.put("verificationCode", verificationCode);
		String templateParam=templateParamJson.toJSONString();
		smsSendService.sendSms(phoneNumber, templateCode, templateParam);
*/

    }


    /**
     * 设置密码
     */
    @Transactional
    public void setPasswordRequestProcess() {

        String password = dataCenterService.getData("password");
        String userName = dataCenterService.getData("userName");

//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodePassword = passwordEncoder.encode(password);

        // 密码加密
        String encodePassword = CommonUtil.passwordEncodeByBCrypt(password);

        //设置密码
        boolean setPasswordResult = userDao.setPassword(userName, encodePassword);

        //启用用户
        boolean modifyUserStatusResult = userDao.modifyUserStatus(userName, ENABLE);

        //设置默认权限 预约报道
        List<String> initialAuthority = UserAuthorityConstant.INITIAL_AUTHORITY;
        boolean initialAuthorityResult = authoritiesDao.addPersonnelToAuthorities(userName, initialAuthority);

        if (!(setPasswordResult && modifyUserStatusResult && initialAuthorityResult)) {
            ExceptionUtil.throwRequestFailureException();
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();

        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }


    /**
     * 用户注册
     */
    @Transactional
    public void getUserRegistrationRequestProcess() {
        UserInfoVo userInfoVo = dataCenterService.getData("userInfoVo");

        User user = new User();
        user.setUsername(userInfoVo.getUsername());
        String newPassword = CommonUtil.passwordEncodeByBCrypt(user.getDefaultPassword());
        user.setPassword(newPassword);
        user.setEnabled(ENABLE);
        boolean addResult1 = userDao.addUsers(user);
        //将注册的用户信息写到数据库中
        UserDetails userDetails = new UserDetails();
        userDetails.setUsername(userInfoVo.getUsername());
        userDetails.setNickname(userInfoVo.getNickname());
        userDetails.setHead(userInfoVo.getHead());
        userDetails.setProvince(userInfoVo.getProvince());
        userDetails.setCity(userInfoVo.getCity());

        userDetails.setCreateTime(new Date());
        userDetails.setUpdateTime(new Date());

        boolean addResult2 = userDao.addUserDetails(userDetails);

        if (!addResult1 || !addResult2) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.USER_REGISTRATION_ERROR);
        }

        responseUtil(null, null);

    }


    /**
     * 忘记密码业务逻辑
     */
    @Transactional
    public void forgetPasswordRequestProcess() {

        String idCardNum = dataCenterService.getData("idCardNum");
        String email = dataCenterService.getData("email");

        String username = userDao.getUsernameFromIdCardNum(idCardNum);
        Date updateTime = userDao.getUpdateTimeFromUsername(username);
        Date now = new Date();
        //连续操作时间间隔不能小于1分钟
        if ((now.getTime() - updateTime.getTime()) / (1000 * 60) < 1) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.OPERATION_TIME_INTERVAL_ERROR);
        }
        //生成新密码推送给用户
        String password = IdGenerator.getId();
        String newPassword = CommonUtil.passwordEncodeByBCrypt(password);
        boolean isUpdatePasswordSuccess = userDao.updatePasswordForUsers(newPassword, username);
        boolean isUpdateTimeSuccess = userDao.updateTime(new Date(), username);
        if (!isUpdatePasswordSuccess || !isUpdateTimeSuccess) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.PASSWORD_UPDATE_ERROR);
        }

        isPushMailSuccess(username, password, email);

        responseUtil(null, null);
    }

    //把账号密码推送给用户
    public void isPushMailSuccess(String username, String password, String email) {
        String content = "账号：" + username + "   " + "密码：" + password;

        pushMailUtil.SampleMail(content, email);
    }
}
