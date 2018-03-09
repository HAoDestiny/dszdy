package net.gzhqlf.dszdy.service.admin;

import net.gzhqlf.dszdy.entity.*;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.*;
import net.gzhqlf.dszdy.repository.*;
import net.gzhqlf.dszdy.service.*;
import net.gzhqlf.dszdy.vo.AdminLoginVo;
import net.gzhqlf.dszdy.vo.AdminRegisterVo;
import net.gzhqlf.dszdy.vo.JoinActivityVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Destiny_hao on 2017/11/6.
 */

@Service
public class AdminUserService {

    @Resource
    private UserService userService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private FileService fileService;

    @Resource
    private WeChatService weChatService;

    @Resource
    private AddressService addressService;

    @Resource
    private ConfigurationService configurationService;

    @Resource
    private UserProveRepository userProveRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private CityActivityRepository cityActivityRepository;

    @Resource
    private ActivityPersonRepository activityPersonRepository;

    public UserEntity login(AdminLoginVo adminLoginVo) throws ControllerException {

        UserEntity userEntity = userRepository.findByMobileAndPassword(adminLoginVo.getMobile(), adminLoginVo.getPassword());

        if (userEntity == null) {
            throw new ControllerException("密码错误");
        }

        if (userEntity.getUserLock() == 1 || userEntity.getDeleteTag() == 1) {
            throw new ControllerException("账号异常");
        }

        if (userEntity.getUserType() == 0) {
            throw new ControllerException("权限不足");
        }

        userEntity.setLastLoginTime((int) (System.currentTimeMillis() / 1000));
        userEntity.setLastLoginIp(adminLoginVo.getIp());

        userRepository.saveAndFlush(userEntity);

        return userEntity;
    }

    public ResultPo register(AdminRegisterVo adminRegisterVo) throws ControllerException {

        if (userRepository.findByMobile(adminRegisterVo.getAccount()) != null) {
            throw new ControllerException("账号名已被注册");
        }

        if (adminRegisterVo.getType() < 0 || adminRegisterVo.getType() > 3) {
            throw new ControllerException("管理类型错误");
        }

        ResultPo resultPo = new ResultPo();
        UserEntity userEntity = new UserEntity();

        userEntity.setMobile(adminRegisterVo.getAccount());
        userEntity.setPassword(adminRegisterVo.getPassword());
        userEntity.setRegisterTime((int) (System.currentTimeMillis() / 1000));
        userEntity.setRegisterIp(adminRegisterVo.getIp());
        userEntity.setLastLoginTime(0);
        userEntity.setLastLoginIp("0");

        userEntity.setUserType(adminRegisterVo.getType()); //管理类型

        userRepository.saveAndFlush(userEntity);

        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("添加成功");

        return resultPo;
    }

    public UserEntityInfoListPo getUserList(int pageCode, int pageSize) throws ControllerException {

        if (pageCode < 0 || pageSize < 0) {
            throw new ControllerException("页面加载失败");
        }

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);

        Page<UserEntity> pagList = userRepository.findByUserType(0, pageable);

        List<UserEntity> userEntityList = pagList.getContent();

        List<UserEntityInfoPo> listPos = new ArrayList<>();
        UserEntityInfoListPo userEntityInfoListPo = new UserEntityInfoListPo();
        if (userEntityList.size() > 0 ) {
            for (UserEntity userEntity : userEntityList) {

                UserEntityInfoPo userEntityInfoPo = new UserEntityInfoPo();
                UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());

                if (userInfoEntity == null) {
                    continue;
                }

                userEntityInfoPo.setUserId(userEntity.getId());
                userEntityInfoPo.setMobile(userEntity.getMobile());

                userEntityInfoPo.setOriginProvinceValue(addressService.getAddressByCode(userInfoEntity.getOriginProvinceId(), 1));
                userEntityInfoPo.setOriginCityValue(addressService.getAddressByCode(userInfoEntity.getOriginCityId(), 2));

                userEntityInfoPo.setEducationValue(configurationService.getConfigurationByCodeAndType(userInfoEntity.getEducation(), 5));

                userEntityInfoPo.setUserInfo(userInfoEntity);
                userEntityInfoPo.setRegisterTime(userEntity.getRegisterTime());
                userEntityInfoPo.setLastLoginTime(userEntity.getLastLoginTime());
                userEntityInfoPo.setLastLoginIp(userEntity.getLastLoginIp());
                userEntityInfoPo.setDeleteTag(userEntity.getDeleteTag());
                userEntityInfoPo.setBalance(userEntity.getBalance());
                userEntityInfoPo.setVipExp(userEntity.getVipExp());

                if ("".equals(userEntity.getVipType()))
                    userEntity.setVipType("普通会员");


                userEntityInfoPo.setVipType(userEntity.getVipType());

                if ((System.currentTimeMillis()/1000) < userEntity.getVipExp()) {
                    userEntityInfoPo.setIsExp(0);
                }

                listPos.add(userEntityInfoPo);
            }
        }

        userEntityInfoListPo.setUserInfoListPo(listPos);
        userEntityInfoListPo.setPage(pageCode);
        userEntityInfoListPo.setPageSize(pageSize);
        userEntityInfoListPo.setPageTotal(pagList.getTotalPages());

        return userEntityInfoListPo;
    }

    public AdminUserListPo getAdminUserList(int type, int userId, int pageCode, int pageSize) throws ControllerException {

        if (pageCode < 0 || pageSize < 0) {
            throw new ControllerException("页面加载失败");
        }

        if (userId <= 0) {
            throw new ControllerException("页面加载失败");
        }

        Sort sort = new Sort(Sort.Direction.DESC, "registerTime");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);

        Page<UserEntity> pagList;

        if (type == 1) {
            pagList = userRepository.findByUserType(1, pageable);
        }

        else if (type == 2) {
            pagList = userRepository.findByUserType(2, pageable);
        }

        else {
            throw new ControllerException("列表类型不存在");
        }

        List<UserEntity> userEntityList = pagList.getContent();

        AdminUserListPo adminUserListPo = new AdminUserListPo();
        List<AdminUserPo> adminUserPos = new ArrayList<>();
        if (userEntityList.size() > 0 ) {
            for (UserEntity userEntity : userEntityList) {
                AdminUserPo adminUserPo = new AdminUserPo();

                if (type == 1 && userEntity.getId() == userId) {
                    continue;
                }

                adminUserPo.setAccount(userEntity.getMobile());
                adminUserPo.setUserId(userEntity.getId());
                adminUserPo.setType(userEntity.getUserType());
                adminUserPo.setDeleteTag(userEntity.getDeleteTag());
                adminUserPo.setRegisterTime(userEntity.getRegisterTime());
                adminUserPo.setLastLoginTime(userEntity.getLastLoginTime());
                adminUserPo.setLastLoginIp(userEntity.getLastLoginIp());

                adminUserPos.add(adminUserPo);
            }
        }

        adminUserListPo.setAdminUserPo(adminUserPos);
        adminUserListPo.setPage(pageCode);
        adminUserListPo.setPageSize(pageSize);
        adminUserListPo.setPageTotal(pagList.getTotalPages());

        return adminUserListPo;
    }

    public AdminProveListPo getProveList(int type, int pageCode, int pageSize) throws ControllerException {

        if (pageCode < 0 || pageSize < 0) {
            throw new ControllerException("页面加载失败");
        }

        Pageable pageable = new PageRequest(pageCode, pageSize);
        Page<UserProveEntity> pageList;

        if (type == 1) {
            pageList = userProveRepository.findByIdentityStatus(2, pageable);
        }

        else if (type == 2) {
            pageList = userProveRepository.findByEducationStatus(2, pageable);
        }

        else if (type == 3) {
            pageList = userProveRepository.findByCarStatus(2, pageable);
        }

        else if (type == 4) {
            pageList = userProveRepository.findByHouseStatus(2, pageable);
        }

        else {
            throw new ControllerException("列表类型不存在");
        }

        List<UserProveEntity> userProveEntityList = pageList.getContent();

        List<AdminProvePo> adminProvePos = new ArrayList<>();
        AdminProveListPo adminProveListPo = new AdminProveListPo();
        if (userProveEntityList.size() > 0) {
            for (UserProveEntity userProveEntity : userProveEntityList) {
                AdminProvePo adminProvePo = new AdminProvePo();
                UserEntity userEntity = userService.getUserEntityByUserProveId(userProveEntity.getId());
                UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());

                FilePo filePo = null;

                if (type == 1) {
                    adminProvePo.setFileId(userProveEntity.getIdentityPro());
                    filePo = fileService.getFileByFileId(userProveEntity.getIdentityPro());
                }

                if (type == 2) {
                    adminProvePo.setFileId(userProveEntity.getEducationPro());
                    filePo = fileService.getFileByFileId(userProveEntity.getEducationPro());
                }

                if (type == 3) {
                    adminProvePo.setFileId(userProveEntity.getCarPro());
                    filePo = fileService.getFileByFileId(userProveEntity.getCarPro());
                }

                if (type == 4) {
                    adminProvePo.setFileId(userProveEntity.getHousePro());
                    filePo = fileService.getFileByFileId(userProveEntity.getHousePro());
                }

                adminProvePo.setProvePic(filePo);
                adminProvePo.setTrueName(userInfoEntity.getNickname());
                adminProvePo.setUserId(userEntity.getId());
                adminProvePo.setMobile(userEntity.getMobile());

                adminProvePos.add(adminProvePo);

            }

            adminProveListPo.setAdminProveListPo(adminProvePos);
        }

        adminProveListPo.setPage(pageCode);
        adminProveListPo.setPageSize(pageSize);
        adminProveListPo.setPageTotal(pageList.getTotalPages());

        return adminProveListPo;
    }

    public ResultPo joinActivity(JoinActivityVo joinActivityVo) throws ControllerException {

        if (joinActivityVo.getUserId() <= 0 || joinActivityVo.getActivityId() <= 0) {
            throw new ControllerException("参数错误");
        }

        UserEntity userEntity = userService.getUserEntityByUserId(joinActivityVo.getUserId());

        if (userEntity.getIsVerify() == 0) {
            throw new ControllerException("用户未通过审核");
        }

        CityActivityEntity cityActivityEntity = cityActivityRepository.findOne(joinActivityVo.getActivityId());

        if (cityActivityEntity == null) {
            throw new ControllerException("该活动不存在");
        }

        if (activityPersonRepository.findByUserIdAndActivityId(joinActivityVo.getUserId(), joinActivityVo.getActivityId()) != null) {
            throw new ControllerException("用户已在该活动中");
        }

        if (joinActivityVo.getAddVip() == 1) {
            GoodsEntity goodsEntity = goodsService.getGoodsEntityByGoodsId(joinActivityVo.getGoodsId());

            userService.updateUserVip(
                    joinActivityVo.getUserId(),
                    (int)(System.currentTimeMillis()/1000) + 86400 * goodsEntity.getGoodsDays(),
                    goodsEntity.getGoodsNum(),
                    goodsEntity.getGoodsDesc()
            );

        } else {

            if (userEntity.getBalance() <= 0) {
                throw new ControllerException("用户参与活动次数不足");
            }

            if ((System.currentTimeMillis() / 1000) > userEntity.getVipExp()) {
                throw new ControllerException("用户会员已过期");
            }
        }

        ActivityPersonEntity activityPersonEntity = new ActivityPersonEntity();
        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserId(joinActivityVo.getUserId());

        if (userInfoEntity == null || userInfoEntity.getSex() == null) {
            throw new ControllerException("用户信息未填写");
        }

        activityPersonEntity.setUserSex(userInfoEntity.getSex());
        activityPersonEntity.setUserId(joinActivityVo.getUserId());
        activityPersonEntity.setActivityId(joinActivityVo.getActivityId());

        activityPersonRepository.saveAndFlush(activityPersonEntity);

        if (activityPersonEntity.getId() == 0) {
            throw new ControllerException("加入活动失败");
        }

        userService.updateUserBalance(joinActivityVo.getUserId());

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("加入成功");

        return resultPo;
    }

    public ResultPo sendFlower(int userId, int num, int activityId, int type) throws ControllerException {

        if (num <= 0) {
            throw new ControllerException("数量不正确");
        }

        switch (type) {
            //单个
            case 1: {

                if (userId <= 0) {
                    throw new ControllerException("用户不存在");
                }

                UserEntity userEntity = userService.getUserEntityByUserId(userId);

                if (userEntity == null) {
                    throw new ControllerException("用户不存在");
                }

                userService.updateFlowerTotal(userEntity.getId(), num);
                break;
            }
            //批量
            case 2: {

                if (activityId <= 0) {
                    throw new ControllerException("活动不存在");
                }

                List<ActivityPersonEntity> activityPersonEntities = activityPersonRepository.findByActivityId(activityId);

                if (activityPersonEntities.size() == 0) {
                    throw new ControllerException("该活动还没有人参与");
                }

                List<Integer> userIdList = new ArrayList<>();
                for (ActivityPersonEntity activityPersonEntity : activityPersonEntities) {

                    userIdList.add(activityPersonEntity.getUserId());
                }
                userRepository.addFlowerTotal(userIdList, num);

                break;
            }
            default: {
                throw new ControllerException("类型错误");
            }
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("赠送成功");

        return resultPo;
    }

    //审核0通过 1拒
    public ResultPo check(int type, int tag, int userId, String refuseContent) throws ControllerException {
        if (userId == 0) {
            throw new ControllerException("用户不存在");
        }

        UserEntity userEntity = userService.getUserEntityByUserId(userId);

        UserProveEntity userProveEntity = userService.getUserProveEntityByUserId(userId);

        ResultPo resultPo = new ResultPo();
        WeChatMessagePo weChatMessagePo;
        String typeStr;
        FilePo filePo;

        switch (type) {
            case 0:

                if (tag == 1) {
                    userProveEntity.setIdentityStatus(1);
                    filePo = fileService.getFileByFileId(userProveEntity.getIdentityPro());
                    typeStr = "身份认证";
                }

                else if(tag == 2) {
                    userProveEntity.setEducationStatus(1);
                    filePo = fileService.getFileByFileId(userProveEntity.getEducationPro());
                    typeStr = "学历认证";
                }

                else if(tag == 3) {
                    userProveEntity.setCarStatus(1);
                    filePo = fileService.getFileByFileId(userProveEntity.getCarPro());
                    typeStr = "车认证";
                }

                else if(tag == 4) {
                    userProveEntity.setHouseStatus(1);
                    filePo = fileService.getFileByFileId(userProveEntity.getHousePro());
                    typeStr = "房产认证";
                }
                else {
                    throw new ControllerException("类型索引不匹配");
                }

                weChatMessagePo = new WeChatMessagePo();
                weChatMessagePo.setDescription("您的" + typeStr + "审核已通过！");
                weChatMessagePo.setPicurl("http:" + filePo.getFileHost() + "/" + filePo.getFileName() + filePo.getFileType());
                weChatMessagePo.setTitle("您的资料审核已通过，赠送您10朵鲜花，赶快添加好友进行私聊吧，还有新功能等着你哦！");
                weChatMessagePo.setUrl("http://" + weChatService.getDOMAIN() + "/m/home.html");

                weChatService.sendWeChatMessage(userId, weChatMessagePo);

                userEntity.setIsVerify(1);

                userRepository.saveAndFlush(userEntity);
                userRepository.updateFlowerTotal(userEntity.getId()); //赠送flower
                resultPo.setMessage("审核已通过");
                break;
            case 1:

                if ("".equals(refuseContent)) {
                    throw new ControllerException("拒绝理由不能为空");
                }

                if (tag == 1) {
                    userProveEntity.setIdentityStatus(3);
                    userProveEntity.setIdentityRefuse(refuseContent);
                    filePo = fileService.getFileByFileId(userProveEntity.getIdentityPro());
                    typeStr = "身份认证";
                }

                else if(tag == 2) {
                    userProveEntity.setEducationStatus(3);
                    userProveEntity.setEducationRefuse(refuseContent);
                    filePo = fileService.getFileByFileId(userProveEntity.getEducationPro());
                    typeStr = "学历认证";
                }

                else if(tag == 3) {
                    userProveEntity.setCarStatus(3);
                    userProveEntity.setCarRefuse(refuseContent);
                    filePo = fileService.getFileByFileId(userProveEntity.getCarPro());
                    typeStr = "车认证";
                }

                else if(tag == 4) {
                    userProveEntity.setHouseStatus(3);
                    userProveEntity.setHouseRefuse(refuseContent);
                    filePo = fileService.getFileByFileId(userProveEntity.getHousePro());
                    typeStr = "房产认证";
                }

                else {
                    throw new ControllerException("类型索引不匹配");
                }

                weChatMessagePo = new WeChatMessagePo();
                weChatMessagePo.setDescription("您的" + typeStr + "审核已被拒绝");
                weChatMessagePo.setPicurl("http:" + filePo.getFileHost() + "/" + filePo.getFileName() + filePo.getFileType());
                weChatMessagePo.setTitle("您的资料审核已被拒绝，请仔细查看相关要求，重新提交资料审核！");
                weChatMessagePo.setUrl("http://" + weChatService.getDOMAIN() + "/m/home.html");

                weChatService.sendWeChatMessage(userId, weChatMessagePo);

                resultPo.setMessage("审核已拒绝");
                break;

            default:
                throw new ControllerException("类型索引不匹配");

        }

        userProveRepository.saveAndFlush(userProveEntity);

        resultPo.setStatus("SUCCESS");
        resultPo.setData(userEntity);
        return resultPo;

    }

    public ResultPo userOperation(int userId, int operationType) throws ControllerException {

        if (userId <= 0) {
            throw new ControllerException("操作失败");
        }

        if (operationType <= 0) {
            throw new ControllerException("操作失败");
        }

        UserEntity userEntity = userRepository.findOne(userId);

        if (userEntity == null) {
            throw new ControllerException("用户不存在");
        }

        //提升权限
        if (operationType == 1) {

            if (userEntity.getDeleteTag() == 1) {
                throw new ControllerException("操作失败,账号已被封");
            }

            userEntity.setUserType(1);

        }

        //封号
        else if (operationType == 2) {
            userEntity.setDeleteTag(1);
        }

        //解封
        else if (operationType == 3) {
            userEntity.setDeleteTag(0);
        }

        //限制权限
        else if (operationType == 4) {
            userEntity.setUserType(2);
        }

        else {
            throw new ControllerException("操作失败");
        }

        userRepository.saveAndFlush(userEntity);

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("操作成功");

        return resultPo;
    }

    public UserEntityInfoListPo search(int pageCode, int pageSize, String trueName) throws ControllerException {


        if (pageCode < 0 || pageSize < 0) {
            throw new ControllerException("页面加载失败");
        }

        if ("".equals(trueName) || trueName == null) {
            throw new ControllerException("搜索用户不存在");
        }

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);

        Page<UserInfoEntity> pagList = userInfoRepository.findByNickname(trueName, pageable);

        List<UserInfoEntity> userInfoEntityList = pagList.getContent();

        List<UserEntityInfoPo> listPos = new ArrayList<>();
        UserEntityInfoListPo userEntityInfoListPo = new UserEntityInfoListPo();
        if (userInfoEntityList.size() > 0) {
            for (UserInfoEntity userInfoEntity : userInfoEntityList) {
                UserEntityInfoPo userEntityInfoPo = new UserEntityInfoPo();

                UserEntity userEntity = userService.getUserEntityByUserInfoId(userInfoEntity.getId());

                userEntityInfoPo.setUserId(userEntity.getId());
                userEntityInfoPo.setMobile(userEntity.getMobile());
                userEntityInfoPo.setOriginProvinceValue(addressService.getAddressByCode(userInfoEntity.getOriginProvinceId(), 1));
                userEntityInfoPo.setOriginCityValue(addressService.getAddressByCode(userInfoEntity.getOriginCityId(), 2));
                userEntityInfoPo.setEducationValue(configurationService.getConfigurationByCodeAndType(userInfoEntity.getEducation(), 5));
                userEntityInfoPo.setUserInfo(userInfoEntity);
                userEntityInfoPo.setRegisterTime(userEntity.getRegisterTime());
                userEntityInfoPo.setLastLoginTime(userEntity.getLastLoginTime());
                userEntityInfoPo.setLastLoginIp(userEntity.getLastLoginIp());
                userEntityInfoPo.setDeleteTag(userEntity.getDeleteTag());
                userEntityInfoPo.setBalance(userEntity.getBalance());
                userEntityInfoPo.setVipExp(userEntity.getVipExp());

                if ("".equals(userEntity.getVipType()))
                    userEntity.setVipType("普通会员");


                userEntityInfoPo.setVipType(userEntity.getVipType());

                if ((System.currentTimeMillis()/1000) < userEntity.getVipExp()) {
                    userEntityInfoPo.setIsExp(0);
                }

                listPos.add(userEntityInfoPo);
            }
        }

        userEntityInfoListPo.setUserInfoListPo(listPos);
        userEntityInfoListPo.setPage(pageCode);
        userEntityInfoListPo.setPageSize(pageSize);
        userEntityInfoListPo.setPageTotal(pagList.getTotalPages());

        return userEntityInfoListPo;
    }
}
