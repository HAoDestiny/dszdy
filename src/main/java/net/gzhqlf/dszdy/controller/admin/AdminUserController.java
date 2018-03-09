package net.gzhqlf.dszdy.controller.admin;

import net.gzhqlf.dszdy.entity.*;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.*;
import net.gzhqlf.dszdy.repository.UserRepository;
import net.gzhqlf.dszdy.service.*;
import net.gzhqlf.dszdy.service.admin.AddressService;
import net.gzhqlf.dszdy.service.admin.AdminUserService;
import net.gzhqlf.dszdy.util.Tool;
import net.gzhqlf.dszdy.vo.*;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Destiny_hao on 2017/11/5.
 */

@RestController
@RequestMapping(value = "/api/admin", method = RequestMethod.POST)
public class AdminUserController {

    @Resource
    private UserService userService;

    @Resource
    private FileService fileService;

    @Resource
    private AddressService addressService;

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private ConfigurationService configurationService;

    @Resource
    private UserRepository userRepository;

    @RequestMapping(value = "/login")
    public ResultPo login(@RequestBody @Valid AdminLoginVo adminLoginVo,
                          BindingResult bindingResult, HttpServletRequest httpServletRequest,
                          HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        ResultPo resultPo = new ResultPo();

        adminLoginVo.setIp(Tool.getIpAddress(httpServletRequest));
        UserEntity userEntity = adminUserService.login(adminLoginVo);

        httpSession.setAttribute("Admin", userEntity);

        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("登录成功");
        resultPo.setData(userEntity.getUserType());
        return resultPo;
    }

    @RequestMapping(value = "/register")
    public ResultPo register(@RequestBody @Valid AdminRegisterVo adminRegisterVo,
                          BindingResult bindingResult, HttpServletRequest httpServletRequest,
                          HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("Admin");

        if (userEntity == null) {
            throw new ControllerException("已离线请重新登录");
        }

        if (userEntity.getUserType() != 1) {
            throw new ControllerException("当前账号的权限不足，请联系超级管理员获取权限");
        }

        adminRegisterVo.setIp(Tool.getIpAddress(httpServletRequest));

        return adminUserService.register(adminRegisterVo);
    }

    @RequestMapping(value = "/logout")
    public ResultPo logout(HttpSession httpSession) {
        httpSession.removeAttribute("Admin");
        httpSession.invalidate();

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("ACTION");
        resultPo.setData("/admin/");
        resultPo.setMessage("退出成功");

        return resultPo;
    }

    @RequestMapping(value = "/getAdminUserList")
    public ResultPo getAdminUserList(@RequestBody @Valid AdminProveListVo adminProveListVo,
                                     BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("Admin");

        ResultPo resultPo = new ResultPo();

        AdminUserListPo adminUserListPo = adminUserService.getAdminUserList(
                adminProveListVo.getType(),
                userEntity.getId(),
                adminProveListVo.getPageCode(),
                adminProveListVo.getPageSize()
        );

        resultPo.setStatus("SUCCESS");
        resultPo.setData(adminUserListPo);

        return resultPo;
    }

    @RequestMapping(value = "/getUserProveList")
    public ResultPo getUserProveList(@RequestBody @Valid AdminProveListVo adminProveListVo,
                                     BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        ResultPo resultPo = new ResultPo();

        AdminProveListPo adminProveListPo = adminUserService.getProveList(adminProveListVo.getType(), adminProveListVo.getPageCode(), adminProveListVo.getPageSize());

        resultPo.setStatus("SUCCESS");
        resultPo.setData(adminProveListPo);

        return resultPo;
    }

    @RequestMapping(value = "/getUserList")
    public ResultPo getUserList(@RequestBody @Valid AdminProveListVo adminProveListVo,
                                     BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        ResultPo resultPo = new ResultPo();

        UserEntityInfoListPo userEntityInfoListPo = adminUserService.getUserList(adminProveListVo.getPageCode(), adminProveListVo.getPageSize());

        resultPo.setStatus("SUCCESS");
        resultPo.setData(userEntityInfoListPo);

        return resultPo;
    }

    @RequestMapping(value = "/searchUserList")
    public ResultPo searchUserList(@RequestBody @Valid AdminProveListVo adminProveListVo,
                                BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        ResultPo resultPo = new ResultPo();

        UserEntityInfoListPo userEntityInfoListPo = adminUserService.search(
                adminProveListVo.getPageCode(),
                adminProveListVo.getPageSize(),
                adminProveListVo.getNickname()
        );

        resultPo.setStatus("SUCCESS");
        resultPo.setData(userEntityInfoListPo);

        return resultPo;
    }

    //获取用户详细信息
    @RequestMapping(value = "/getUserInfoAll")
    public ResultPo getUserInfoAll(@Param("id") String id, HttpSession httpSession) throws ControllerException {

        if (id == null || id.equals("")) {
            throw new ControllerException("参数错误");
        }

        UserEntity userEntityAdmin = (UserEntity) httpSession.getAttribute("Admin");

        if (userEntityAdmin == null) {
            throw new ControllerException("已离线请重新登录");
        }

        if (userEntityAdmin.getUserType() != 1) {
            throw new ControllerException("当前账号的权限不足，请联系超级管理员获取权限");
        }

        UserEntity userEntity = userRepository.findOne(Integer.valueOf(id));

        if (userEntity == null) {
            throw new ControllerException("用户不存在");
        }

        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());
        UserInfoOtherEntity userInfoOtherEntity = userService.getUserInfoOtherEntityByUserInfoOtherId(userEntity.getUserInfoOtherId());

        if (userInfoOtherEntity.getId() == 0 || userInfoEntity.getId() == 0) {
            throw new ControllerException("还未填写用户基础信息");
        }

//        List<FilePo> filePoList = new ArrayList<>();
        FilePo avatarNormalFilePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());


//        filePoList.add();

        ResultPo resultPo = new ResultPo();
        UserInfoPo userInfoPo = new UserInfoPo();
        UserInfoOtherPo userInfoOtherPo = userService.getUserInfoOther(userEntity.getUserInfoOtherId());
        UserInfoAllPo userInfoAllPo = new UserInfoAllPo();

        userInfoPo.setAvatar(avatarNormalFilePo);
        userInfoPo.setBirth(userInfoEntity.getBirth());
        userInfoPo.setProvince(addressService.getAddressByCode(userInfoEntity.getOriginProvinceId(), 1));
        userInfoPo.setCity(addressService.getAddressByCode(userInfoEntity.getOriginCityId(), 2));
        userInfoPo.setTrueName(userInfoEntity.getNickname());
        userInfoPo.setHeight(String.valueOf(userInfoEntity.getHeight()));
        userInfoPo.setEducation(configurationService.getConfigurationByCodeAndType(userInfoEntity.getEducation(), 5));

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
        userInfoAllPo.setUserInfoOtherMessage(userInfoOtherPo);
        userInfoAllPo.setUserMetaInfo(userMetaInfoPo);
        resultPo.setStatus("SUCCESS");
        resultPo.setData(userInfoAllPo);

        return resultPo;
    }

    //加入活动
    @RequestMapping(value = "/joinActivity")
    public ResultPo joinActivity(@RequestBody @Valid JoinActivityVo joinActivityVo,
                                 BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("Admin");

        if (userEntity == null) {
            throw new ControllerException("已离线请重新登录");
        }

        if (userEntity.getUserType() != 1) {
            throw new ControllerException("当前账号的权限不足，请联系超级管理员获取权限");
        }

        return adminUserService.joinActivity(joinActivityVo);
    }

    //送花
    @RequestMapping(value = "/sendFlower")
    public ResultPo sendFlower(@RequestBody @Valid AdminSendFlowerVo adminSendFlowerVo,
                                 BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        ResultPo resultPo = null;
        if (adminSendFlowerVo.getType() == 1) {
            resultPo = adminUserService.sendFlower(
                    adminSendFlowerVo.getUserId(),
                    adminSendFlowerVo.getFlowerNum(),
                    0,
                    adminSendFlowerVo.getType()
            );
        }

        if (adminSendFlowerVo.getType() == 2) {
            resultPo = adminUserService.sendFlower(
                    0,
                    adminSendFlowerVo.getFlowerNum(),
                    adminSendFlowerVo.getActivityId(),
                    adminSendFlowerVo.getType()
            );
        }

        return resultPo;
    }

    @RequestMapping(value = "/check")
    public ResultPo check(@RequestBody @Valid AdminCheckProveVo adminCheckProveVo,
                          BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("Admin");

        if (userEntity == null) {
            throw new ControllerException("已离线请重新登录");
        }

        if (userEntity.getUserType() != 1) {
            throw new ControllerException("当前账号的权限不足，请联系超级管理员获取权限");
        }

        ResultPo resultPo = adminUserService.check(
                adminCheckProveVo.getType(),
                adminCheckProveVo.getTag(),
                adminCheckProveVo.getUserId(),
                adminCheckProveVo.getContent()
        );

        httpSession.setAttribute("User", resultPo.getData());//刷新session

        resultPo.setData(null);
        return resultPo;
    }

    @RequestMapping(value = "/userOperation")
    public ResultPo userOperation(@RequestBody @Valid UserOperationVo userOperationVo,
                          BindingResult bindingResult, HttpSession httpSession) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("Admin");

        if (userEntity == null) {
            throw new ControllerException("已离线请重新登录");
        }

        if (userEntity.getUserType() != 1) {
            throw new ControllerException("当前账号的权限不足，请联系超级管理员获取权限");
        }

        return adminUserService.userOperation(userOperationVo.getUserId(), userOperationVo.getOperationType());
    }
}
