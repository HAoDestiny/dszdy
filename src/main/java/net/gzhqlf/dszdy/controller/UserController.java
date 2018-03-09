package net.gzhqlf.dszdy.controller;

import net.gzhqlf.dszdy.entity.*;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.*;
import net.gzhqlf.dszdy.service.*;
import net.gzhqlf.dszdy.service.admin.AddressService;
import net.gzhqlf.dszdy.util.Tool;
import net.gzhqlf.dszdy.vo.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DESTINY on 2017/10/20.
 */

@RestController
@RequestMapping(value = "/api/user", method = RequestMethod.POST)
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private FileService fileService;

    @Resource
    private NotifyService notifyService;

    @Resource
    private AddressService addressService;

    @Resource
    private ConfigurationService configurationService;


    @RequestMapping(value = "/login")
    public ResultPo login(@RequestBody @Valid UserVo userVo, BindingResult bindingResult, HttpSession httpSession,
                          HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        userVo.setIP(Tool.getIpAddress(httpServletRequest));
        ResultPo resultPo = userService.login(userVo);
        LoginServicePo loginServicePo = (LoginServicePo) resultPo.getData();
        httpSession.setAttribute("User", loginServicePo.getUserEntity());
//        TokenUtils tokenUtils = new TokenUtils(httpSession.getId(), loginServicePo.getUserEntity().getId());

//        String dstoken = "";
//        try {
//            dstoken = URLEncoder.encode(tokenUtils.getTokenCode(), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            throw new ControllerException("令牌错误");
//        }

        Cookie cookie = new Cookie("__dstoken", "");
        cookie.setMaxAge(0);
//        cookie.setMaxAge(3600 * 24 * 15);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        if (resultPo.getStatus().equals("ACTION")) {
            resultPo.setData(loginServicePo.getUri());
        }

        if (resultPo.getStatus().equals("SUCCESS")) {
            resultPo.setData(null);
        }

        return resultPo;
    }

    @RequestMapping(value = "/logout")
    public ResultPo logout(HttpSession httpSession) {
        httpSession.removeAttribute("User");
        httpSession.invalidate();

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("ACTION");
        resultPo.setData("/m/");
        resultPo.setMessage("退出成功");

        return resultPo;
    }

    @RequestMapping(value = "/register")
    public ResultPo register(@RequestBody @Valid RegisterVo registerVo, BindingResult bindingResult,
                             HttpSession httpSession, HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        //判断短信验证码是否正确
        String smsCode = (String) httpSession.getAttribute("smsCode");

        if (smsCode == null) {
            throw new ControllerException("尚未获取短信验证码");
        }

        if (registerVo.getCode().trim().equals("")) {
            throw new ControllerException("未填写短信验证码");
        }

        if (!registerVo.getCode().trim().equals(smsCode)) {
            throw new ControllerException("短信验证码不正确");
        }

        httpSession.removeAttribute("smsCode");

        registerVo.setIp(Tool.getIpAddress(httpServletRequest));
        ResultPo resultPo = userService.register(registerVo);

        httpSession.setAttribute("User", resultPo.getData());

        Cookie cookie = new Cookie("__dstoken","");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        if (resultPo.getStatus().equals("SUCCESS")) {

            resultPo.setData(new TokenRefreshPo(true));
        }

        return resultPo;
    }

    @RequestMapping(value = "/editProfile")
    public ResultPo addProfile(@RequestBody @Valid UserInfoMsgVo userInfoMsgVo, BindingResult bindingResult,
                               HttpSession session) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        return userService.addProfile(userInfoMsgVo, (UserEntity) session.getAttribute("User"));
    }

    @RequestMapping(value = "/getMobileVerifyCode")
    public ResultPo getMobileVerifyCode(@RequestBody @Valid MobileMsgVo mobileMsgVo,
                                        BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        String smsCode = Tool.getRandomCodeString(6);
        httpSession.setAttribute("smsCode", smsCode);

        return userService.getMobileVerifyCode(mobileMsgVo ,smsCode);
    }

    @RequestMapping(value = "/getUserProfile")
    public ResultPo getUserProfile(@RequestBody @Valid UserIdVo userIdVo) throws ControllerException {

        if (userIdVo.getUserId() == 0) {
            throw new ControllerException("用户id错误");
        }

        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserId(userIdVo.getUserId());

        if (userInfoEntity.getId() == 0) {
            throw new ControllerException("还未填写用户基础信息");
        }

        FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

        UserInfoPo userInfoPo = new UserInfoPo();
        userInfoPo.setAvatar(filePo);
        userInfoPo.setBirth(userInfoEntity.getBirth());
        userInfoPo.setProvince(addressService.getAddressByCode(userInfoEntity.getOriginProvinceId(), 1));
        userInfoPo.setCity(addressService.getAddressByCode(userInfoEntity.getOriginCityId(), 2));
        userInfoPo.setTrueName(userInfoEntity.getNickname());
        userInfoPo.setSex(userInfoEntity.getSex());
        userInfoPo.setHeight(String.valueOf(userInfoEntity.getHeight()));
        userInfoPo.setEducation(configurationService.getEducationByCode(userInfoEntity.getEducation(), 5));

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(userInfoPo);

        return resultPo;
    }

    @RequestMapping(value = "/getUserInfo")
    public ResultPo getUserInfo(HttpSession httpSession) throws ControllerException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");
        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());

        if (userInfoEntity.getId() == 0) {
            throw new ControllerException("还未填写用户基础信息");
        }

        FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

        UserInfoPo userInfoPo = new UserInfoPo();
        userInfoPo.setUserId(userInfoEntity.getId());
        userInfoPo.setAvatar(filePo);
        userInfoPo.setBirth(userInfoEntity.getBirth());
        userInfoPo.setProvince(addressService.getAddressByCode(userInfoEntity.getOriginProvinceId(), 1));
        userInfoPo.setCity(addressService.getAddressByCode(userInfoEntity.getOriginCityId(), 2));
        userInfoPo.setTrueName(userInfoEntity.getNickname());
        userInfoPo.setSex(userInfoEntity.getSex());
        userInfoPo.setHeight(String.valueOf(userInfoEntity.getHeight()));
        userInfoPo.setEducation(configurationService.getEducationByCode(userInfoEntity.getEducation(), 5));
        userInfoPo.setProveStatus(userService.getUserEntityByUserId(userEntity.getId()).getIsVerify());
        userInfoPo.setFlowerTotal(userService.getFlowerTotal(userEntity.getId()));
        if ("".equals(userEntity.getVipType()))
            userEntity.setVipType("普通会员");

        userInfoPo.setVipType(userEntity.getVipType());

        UserVerifyInfoPo userVerifyInfoPo = new UserVerifyInfoPo();
        UserProveEntity userProveEntity = userService.getUserProveEntityByUserId(userEntity.getId());
        userVerifyInfoPo.setCar(userProveEntity.getCarStatus());
        userVerifyInfoPo.setEducation(userProveEntity.getEducationStatus());
        userVerifyInfoPo.setHouse(userProveEntity.getCarStatus());
        userVerifyInfoPo.setIdentity(userProveEntity.getIdentityStatus());
        userVerifyInfoPo.setPeople(userProveEntity.getPeopleStatus());

        userInfoPo.setVerify(userVerifyInfoPo);

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(userInfoPo);

        return resultPo;
    }

    //修改用户其他信息
    @RequestMapping(value = "/updateOtherProfile")
    public ResultPo updateOtherProfile(@RequestBody @Valid UserInfoOtherVo userInfoOtherVo, BindingResult bindingResult,
                                       HttpSession session) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        return userService.updateOtherProfile(userInfoOtherVo, (UserEntity) session.getAttribute("User"));
    }

    //更新用户择偶信息
    @RequestMapping(value = "/updateMateInfoProfile")
    public ResultPo updateMateInfoProfile(@RequestBody UserMetaInfoVo userMetaInfoVo,
                                          HttpSession httpSession) throws ControllerException {
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");
        return userService.updateMateInfoProfile(userMetaInfoVo, userEntity);
    }

    //获取用户择偶信息
    @RequestMapping(value = "/getMateInfoProfile")
    public ResultPo getMateInfoProfile(HttpSession httpSession) throws ControllerException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");
        UserMateEntity userMateEntity = userService.getUserMateEntityByUserMateId(userEntity.getUserMateId());

        UserMetaInfoPo userMetaInfoPo = new UserMetaInfoPo();
        ResultPo resultPo = new ResultPo();
        userMetaInfoPo.setEducationValue(configurationService.getEducationByCode(userMateEntity.getEducation(), 5));
        userMetaInfoPo.setMaritalStatusValue(configurationService.getConfigurationByCodeAndType(userMateEntity.getMarriageStatus(), 4));
        userMetaInfoPo.setMateInfo(userMateEntity);

        resultPo.setStatus("SUCCESS");
        resultPo.setData(userMetaInfoPo);
        return resultPo;
    }

    //获取用户其他信息
    @RequestMapping(value = "/getUserInfoOther")
    public ResultPo getUserInfoOther(HttpSession httpSession) throws ControllerException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        UserInfoOtherPo userInfoOtherPo = userService.getUserInfoOther(userEntity.getUserInfoOtherId());

        ResultPo resultPo = new ResultPo();

        resultPo.setStatus("SUCCESS");
        resultPo.setData(userInfoOtherPo);

        return resultPo;
    }

    //获取用户全部信息
    @RequestMapping(value = "/getUserInfoAll")
    public ResultPo getUserInfoAll(@RequestBody UserIdVo userIdVo, HttpSession httpSession) throws ControllerException {

//        if (userIdVo.getUserId() == 0) {
//            throw new ControllerException("用户id错误");
//        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (userIdVo.getUserId() != 0) {
            userEntity = userService.getUserEntityByUserId(userIdVo.getUserId());
            if (userEntity == null) {
                throw new ControllerException("用户不存在");
            }
        }

        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());
        UserInfoOtherEntity userInfoOtherEntity = userService.getUserInfoOtherEntityByUserInfoOtherId(userEntity.getUserInfoOtherId());

        if (userInfoOtherEntity.getId() == 0 || userInfoEntity.getId() == 0) {
            throw new ControllerException("还未填写用户基础信息");
        }

        List<FilePo> filePoList = new ArrayList<>();

        if (userInfoEntity.getAvatarProB() != 0) {
            FilePo avatarProBFilePo = fileService.getFileByFileId(userInfoEntity.getAvatarProB());
            filePoList.add(avatarProBFilePo);
        }

        if (userInfoEntity.getAvatarProC() != 0) {
            FilePo avatarProCFilePo = fileService.getFileByFileId(userInfoEntity.getAvatarProC());
            filePoList.add(avatarProCFilePo);
        }

        if (userInfoEntity.getAvatarProD() != 0) {
            FilePo avatarProDFilePo = fileService.getFileByFileId(userInfoEntity.getAvatarProD());
            filePoList.add(avatarProDFilePo);
        }

        FilePo avatarNormalFilePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

        filePoList.add(avatarNormalFilePo);

        ResultPo resultPo = new ResultPo();
        UserInfoPo userInfoPo = new UserInfoPo();
        UserVerifyInfoPo userVerifyInfoPo = new UserVerifyInfoPo();
        UserInfoOtherPo userInfoOtherPo = userService.getUserInfoOther(userEntity.getUserInfoOtherId());
        UserInfoAllPo userInfoAllPo = new UserInfoAllPo();

        userInfoPo.setAvatar(filePoList);
        userInfoPo.setBirth(userInfoEntity.getBirth());
        userInfoPo.setProvince(addressService.getAddressByCode(userInfoEntity.getOriginProvinceId(), 1));
        userInfoPo.setCity(addressService.getAddressByCode(userInfoEntity.getOriginCityId(), 2));
        userInfoPo.setTrueName(userInfoEntity.getNickname());
        userInfoPo.setHeight(String.valueOf(userInfoEntity.getHeight()));
        userInfoPo.setEducation(configurationService.getConfigurationByCodeAndType(userInfoEntity.getEducation(), 5));

        UserProveEntity userProveEntity = userService.getUserProveEntityByUserId(userEntity.getId());
        userVerifyInfoPo.setCar(userProveEntity.getCarStatus());
        userVerifyInfoPo.setEducation(userProveEntity.getEducationStatus());
        userVerifyInfoPo.setHouse(userProveEntity.getCarStatus());
        userVerifyInfoPo.setIdentity(userProveEntity.getIdentityStatus());
        userVerifyInfoPo.setPeople(userProveEntity.getPeopleStatus());

        int verifyCounts = 0;

        if(userVerifyInfoPo.getCar() == 1){
            verifyCounts += 1;
        }
        if(userVerifyInfoPo.getEducation() == 1){
            verifyCounts += 1;
        }
        if(userVerifyInfoPo.getHouse() == 1){
            verifyCounts += 1;
        }
        if(userVerifyInfoPo.getIdentity() == 1){
            verifyCounts += 1;
        }
        if(userVerifyInfoPo.getPeople() == 1){
            verifyCounts += 1;
        }

        if(verifyCounts <= 2) {
            userVerifyInfoPo.setVerifyLevel("r");
        }
        if(verifyCounts == 3) {
            userVerifyInfoPo.setVerifyLevel("y");
        }
        if(verifyCounts == 4) {
            userVerifyInfoPo.setVerifyLevel("b");
        }
        if(verifyCounts == 5) {
            userVerifyInfoPo.setVerifyLevel("g");
        }

        userVerifyInfoPo.setVerifyPercent(verifyCounts * 20);

        //常住地址
        if (userInfoOtherEntity.getLiveProvinceId() != 0) {
            userInfoOtherPo.setProvince(addressService.getAddressByCode(userInfoOtherEntity.getLiveProvinceId(), 1));
        }
        if (userInfoOtherEntity.getLiveCityId() != 0) {
            userInfoOtherPo.setCity(addressService.getAddressByCode(userInfoOtherEntity.getLiveCityId(), 2));
        }
        if (userInfoOtherEntity.getLiveAreaId() != 0) {
            userInfoOtherPo.setArea(addressService.getAddressByCode(userInfoOtherEntity.getLiveAreaId(), 3));
        }

        UserMateEntity userMateEntity = userService.getUserMateEntityByUserMateId(userEntity.getUserMateId());
        UserMetaInfoPo userMetaInfoPo = new UserMetaInfoPo();

        if (userMateEntity.getEducation() != 0 && userMateEntity.getMarriageStatus() != 0) {
            userMetaInfoPo.setEducationValue(configurationService.getEducationByCode(userMateEntity.getEducation(), 5));
            userMetaInfoPo.setMaritalStatusValue(configurationService.getConfigurationByCodeAndType(userMateEntity.getMarriageStatus(), 4));
        }

        userMetaInfoPo.setMateInfo(userMateEntity);

        userInfoAllPo.setUserInfo(userInfoPo);
        userInfoAllPo.setUserVerifyInfo(userVerifyInfoPo);
        userInfoAllPo.setUserInfoOtherMessage(userInfoOtherPo);
        userInfoAllPo.setUserMetaInfo(userMetaInfoPo);
        resultPo.setStatus("SUCCESS");
        resultPo.setData(userInfoAllPo);

        return resultPo;
    }

    //好友请求
    @RequestMapping(value = "/applyFriends")
    public ResultPo applyFriends(@RequestBody @Valid ApplyFriendsVo applyFriendsVo,
                                 BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (userEntity.getIsVerify() == 0) {
            throw new ControllerException("未进行身份认证，请先提交资料审核");
        }

        return userService.applyFriends(applyFriendsVo, userEntity.getId());
    }

    //关注
    @RequestMapping(value = "/attention")
    public ResultPo attention(@RequestBody @Valid AttentionVo attentionVo,
                                        BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (userEntity.getIsVerify() == 0) {
            throw new ControllerException("未进行身份认证，请先提交资料审核");
        }

        return userService.addAttention(attentionVo, userEntity);
    }

    //获取关注信息
    @RequestMapping(value = "/getAttentionMessage")
    public ResultPo getAttentionMessage(@RequestBody UserIdVo userIdVo, HttpSession httpSession) throws ControllerException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (userIdVo.getUserId() != 0) {
            if (userService.getUserEntityByUserId(userIdVo.getUserId()) == null) {
                throw new ControllerException("用户不存在");
            }

            return userService.getAttentionMessage(userIdVo.getUserId(), userEntity);
        }

        return userService.getAttentionMessage(-1, userEntity);
    }

    //获取粉丝or我的关注列表
    @RequestMapping(value = "/getFansOrAttentionList")
    public ResultPo getFansOrAttentionList(@RequestBody @Valid BasePageVo fansOrAttentionListVo,
                                           BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");
        FansOrAttentionListPo fansOrAttentionListPo = userService.getFansOrAttentionList(
                fansOrAttentionListVo.getType(),
                userEntity.getId(),
                fansOrAttentionListVo.getPageCode(),
                fansOrAttentionListVo.getPageSize()
        );

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(fansOrAttentionListPo);

        return resultPo;
    }

    //获取好友申请列表
    @RequestMapping(value = "/getApplyOrRequestFriendsList")
    public ResultPo getApplyOrRequestFriendsList(@RequestBody @Valid FriendsListVo friendsListVo,
                                          BindingResult bindingResult, HttpSession httpSession) throws ControllerException {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        notifyService.updateNotifyStatus(3, userEntity.getId(), 2);

        ResultPo resultPo = new ResultPo();
        ApplyFriendsListPo applyFriendsListPo = userService.getApplyFriendsPoList
                (
                        userEntity.getId(),
                        friendsListVo.getPageCode(),
                        friendsListVo.getPageSize()
                );

        resultPo.setStatus("SUCCESS");
        resultPo.setData(applyFriendsListPo);

        return resultPo;
    }

    //获取好友列表
    @RequestMapping(value = "/getFriendsList")
    public ResultPo getFriendsList(@RequestBody @Valid BasePageVo friendsListVo,
                                   BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        ResultPo resultPo = new ResultPo();
        FriendsListPo friendsListPo = userService.getFriendsList
                (
                        userEntity.getId(),
                        friendsListVo.getPageCode(),
                        friendsListVo.getPageSize()
                );

        resultPo.setStatus("SUCCESS");
        resultPo.setData(friendsListPo);

        return resultPo;
    }

    //activity person list
    @RequestMapping(value = "/getActivityUserList")
    public ResultPo getActivityUserList(@RequestBody @Valid BasePageVo activityUserListVo,
                                   BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        ResultPo resultPo = new ResultPo();

        resultPo.setStatus("SUCCESS");
        resultPo.setData(userService.getActivityUserList(
                activityUserListVo.getType(),
                userEntity.getId(), activityUserListVo.getPageCode(), activityUserListVo.getPageSize()));

        return resultPo;
    }

    //collection Or RemoveCollection
    @RequestMapping(value = "/collectionOperation")
    public ResultPo collectionOperation(@RequestBody @Valid UserCollectionVo userCollectionVo,
                                        BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        ResultPo resultPo = userService.collectionOperation
                                (
                                        userEntity,
                                        userCollectionVo.getOperationType(),
                                        userCollectionVo.getCollectionObjectId(),
                                        userCollectionVo.getActivityId()
                                );

        return resultPo;
    }

    //balance
    @RequestMapping(value = "/getBalance")
    public ResultPo getBalance(HttpSession httpSession) throws ControllerException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        if (userEntity == null || userEntity.getId() == 0) {
            throw new ControllerException("初始化失败");
        }

        return userService.getBalance(userEntity.getId());
    }

    @RequestMapping(value = "/getCollectionList")
    public ResultPo getCollectionList(@RequestBody @Valid BasePageVo collectionListVo,
                                        BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        ResultPo resultPo = new ResultPo();
        ActivityPersonListPo activityPersonListPo = userService.getCollectionList(userEntity, collectionListVo.getPageCode(), collectionListVo.getPageSize());

        resultPo.setStatus("SUCCESS");
        resultPo.setData(activityPersonListPo);

        return resultPo;
    }

    @RequestMapping(value = "/getAvatarList")
    public ResultPo getAvatarList(HttpSession httpSession) throws ControllerException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");
        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());

        if (userInfoEntity.getId() == 0) {
            throw new ControllerException("还未填写用户基础信息");
        }

        AvatarPo avatarPo = new AvatarPo();

        if (userInfoEntity.getAvatarNormal() != 0) {
            FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());
            avatarPo.setNormal(filePo);
        }
        if (userInfoEntity.getAvatarProB() != 0) {
            FilePo filePo  = fileService.getFileByFileId(userInfoEntity.getAvatarProB());
            avatarPo.setProB(filePo);
        }
        if (userInfoEntity.getAvatarProC() != 0) {
            FilePo filePo  = fileService.getFileByFileId(userInfoEntity.getAvatarProC());
            avatarPo.setProC(filePo);
        }
        if (userInfoEntity.getAvatarProD() != 0) {
            FilePo filePo  = fileService.getFileByFileId(userInfoEntity.getAvatarProD());
            avatarPo.setProD(filePo);
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(avatarPo);

        return resultPo;
    }

    @RequestMapping(value = "/getUserProveList")
    public ResultPo getUserProveList(HttpSession httpSession) throws ControllerException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");
        UserProveEntity userProveEntity = userService.getUserProveEntityByUserProveId(userEntity.getUserProveId());

        ProvePo provePo = new ProvePo();

        if (userProveEntity.getIdentityPro() != 0) {

            if (userProveEntity.getIdentityStatus() == 3) {
                provePo.setIdentityRefuse(userProveEntity.getIdentityRefuse());
            }

            FilePo filePo = fileService.getFileByFileId(userProveEntity.getIdentityPro());
            provePo.setIdentityStatus(userProveEntity.getIdentityStatus());
            provePo.setIdentity(filePo);
        }

        if (userProveEntity.getEducationPro() != 0) {

            if (userProveEntity.getEducationStatus() == 3) {
                provePo.setEducationRefuse(userProveEntity.getEducationRefuse());
            }

            FilePo filePo  = fileService.getFileByFileId(userProveEntity.getEducationPro());
            provePo.setEducationStatus(userProveEntity.getEducationStatus());
            provePo.setEducation(filePo);
        }

        if (userProveEntity.getCarPro() != 0) {

            if (userProveEntity.getCarStatus() == 3) {
                provePo.setCarRefuse(userProveEntity.getCarRefuse());
            }

            FilePo filePo  = fileService.getFileByFileId(userProveEntity.getCarPro());
            provePo.setCarStatus(userProveEntity.getCarStatus());
            provePo.setCar(filePo);
        }

        if (userProveEntity.getHousePro() != 0) {

            if (userProveEntity.getHouseStatus() == 3) {
                provePo.setHouseRefuse(userProveEntity.getHouseRefuse());
            }

            FilePo filePo  = fileService.getFileByFileId(userProveEntity.getHousePro());
            provePo.setHouseStatus(userProveEntity.getHouseStatus());
            provePo.setHouse(filePo);
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(provePo);

        return resultPo;
    }

    @RequestMapping(value = "/removeAvatar")
    public ResultPo removeAvatar(HttpSession httpSession,
                                 @RequestParam(value="picIndex")int picIndex) throws ControllerException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");
        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());

        if (userInfoEntity.getId() == 0) {
            throw new ControllerException("还未填写用户基础信息");
        }

        return userService.removeAvatar(userInfoEntity, picIndex);
    }


}
