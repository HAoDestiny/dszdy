package net.gzhqlf.dszdy.service;

import net.gzhqlf.dszdy.entity.*;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.*;
import net.gzhqlf.dszdy.repository.*;
import net.gzhqlf.dszdy.util.Tool;
import net.gzhqlf.dszdy.vo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DESTINY on 2017/10/22.
 */

@Service
public class UserService {

    @Resource
    private FileService fileService;

    @Resource
    private NotifyService notifyService;

    @Resource
    private WeChatService weChatService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private UserMateRepository userMateRepository;

    @Resource
    private ApplyFriendRepository applyFriendRepository;

    @Resource
    private FriendsRepository friendsRepository;

    @Resource
    private UserInfoOtherRepository userInfoOtherRepository;

    @Resource
    private ConfigurationRepository configurationRepository;

    @Resource
    private AttentionRepository attentionRepository;

    @Resource
    private UserPopularRepository userPopularRepository;

    @Resource
    private UserProveRepository userProveRepository;

    @Resource
    private CityActivityRepository cityActivityRepository;

    @Resource
    private ActivityPersonRepository activityPersonRepository;

    @Resource
    private MateLikeRepository mateLikeRepository;

    @Resource
    private WeChatTokenRepository weChatTokenRepository;

    public ResultPo login(UserVo userVo) throws ControllerException {

        ResultPo resultPo = new ResultPo();

        UserEntity userEntity = userRepository.findByMobileAndPassword(userVo.getMobile(), userVo.getPassword());

        if (userEntity == null) {
            throw new ControllerException("密码错误");
        }

        if (userEntity.getUserLock() == 1 || userEntity.getDeleteTag() == 1) {
            throw new ControllerException("账号异常");
        }

        userEntity.setLastLoginTime((int) (System.currentTimeMillis() / 1000));
        userEntity.setLastLoginIp(userVo.getIP());

        userRepository.saveAndFlush(userEntity);

        LoginServicePo loginServicePo = new LoginServicePo();

        loginServicePo.setUserEntity(userEntity);

        //是否设置基本信息
        if (userEntity.getUserInfoId() == 0) {

            loginServicePo.setUri("profileBase.html");

            resultPo.setStatus("ACTION");
            resultPo.setData(loginServicePo);
            resultPo.setMessage("请设置用户基本信息");

            return resultPo;
        }

        //是否设置头像
        UserInfoEntity userInfoEntity = userInfoRepository.findOne(userEntity.getUserInfoId());
        if (userInfoEntity.getAvatarNormal() == 0) {

            loginServicePo.setUri("uploadPic.html");

            resultPo.setStatus("ACTION");
            resultPo.setData(loginServicePo);
            resultPo.setMessage("请设置头像");

            return resultPo;
        }

        resultPo.setStatus("SUCCESS");
        resultPo.setData(loginServicePo);
        resultPo.setMessage("登录成功");

        return resultPo;
    }

    public ResultPo weChatLogin(WeChatUserBasePo weChatUserBasePo, String ip) throws ControllerException {

        ResultPo resultPo = new ResultPo();
        LoginServicePo loginServicePo = new LoginServicePo();

        WeChatTokenEntity weChatTokenEntity = weChatTokenRepository.findByOpenId(weChatUserBasePo.getOpenid());

        if (weChatTokenEntity == null) {
            loginServicePo.setUri("register.html");
            resultPo.setStatus("ACTION");
            resultPo.setData(loginServicePo);
            resultPo.setMessage("授权失败");

            return resultPo;
        }

        UserEntity userEntity = userRepository.findOne(weChatTokenEntity.getUserId());

        if (userEntity == null) {
            loginServicePo.setUri("register.html");
            resultPo.setStatus("ACTION");
            resultPo.setData(loginServicePo);
            resultPo.setMessage("未注册");

            return resultPo;
        }

        if (userEntity.getUserLock() == 1 || userEntity.getDeleteTag() == 1) {
            throw new ControllerException("账号异常");
        }

        userEntity.setLastLoginTime((int) (System.currentTimeMillis() / 1000));
        userEntity.setLastLoginIp(ip);

        userRepository.saveAndFlush(userEntity);

        UserInfoEntity userInfoEntity = userInfoRepository.findOne(userEntity.getUserInfoId());

        if (null == userInfoEntity) {
            loginServicePo.setUri("register.html");
            resultPo.setStatus("ACTION");
            resultPo.setData(loginServicePo);
            resultPo.setMessage("请重新注册");

            return resultPo;
        }

        if (null == userInfoEntity.getNickname()) {
            loginServicePo.setUserEntity(userEntity);
            loginServicePo.setUri("profileBase.html");
            resultPo.setStatus("ACTION");
            resultPo.setData(loginServicePo);
            resultPo.setMessage("填写用户基本信息");

            return resultPo;
        }

        if (userInfoEntity.getAvatarNormal() == 0) {
            loginServicePo.setUserEntity(userEntity);
            loginServicePo.setUri("uploadPic.html");
            resultPo.setStatus("ACTION");
            resultPo.setData(loginServicePo);
            resultPo.setMessage("请设置头像");

            return resultPo;
        }

        loginServicePo.setUserEntity(userEntity);
        resultPo.setStatus("SUCCESS");
        resultPo.setData(loginServicePo);
        resultPo.setMessage("登录成功");

        return resultPo;
    }

    public ResultPo register(RegisterVo registerVo) throws ControllerException {

        if (userRepository.findByMobile(registerVo.getMobile()) != null) {
            throw new ControllerException("手机号已被注册");
        }

        ResultPo resultPo = new ResultPo();

        //用户详细信息
        UserInfoOtherEntity userInfoOtherEntity = new UserInfoOtherEntity();
        userInfoOtherRepository.saveAndFlush(userInfoOtherEntity);

        //添加用户择偶信息
        UserMateEntity userMateEntity = new UserMateEntity();
        userMateRepository.saveAndFlush(userMateEntity);

        //添加关注信息
        UserPopularEntity userPopularEntity = new UserPopularEntity();
        userPopularRepository.saveAndFlush(userPopularEntity);

        //认证
        UserProveEntity userProveEntity = new UserProveEntity();
        userProveRepository.saveAndFlush(userProveEntity);


        UserEntity userEntity = new UserEntity();

        userEntity.setMobile(registerVo.getMobile());
        userEntity.setPassword(registerVo.getPassword());
        userEntity.setRegisterTime((int) (System.currentTimeMillis() / 1000));
        userEntity.setRegisterIp(registerVo.getIp());
        userEntity.setLastLoginTime((int) (System.currentTimeMillis() / 1000));
        userEntity.setLastLoginIp(registerVo.getIp());

        userEntity.setUserInfoOtherId(userInfoOtherEntity.getId());
        userEntity.setUserMateId(userMateEntity.getId()); //择偶
        userEntity.setUserPopularId(userPopularEntity.getId()); //关注
        userEntity.setUserProveId(userProveEntity.getId()); //认证
        userRepository.saveAndFlush(userEntity);


        if (userEntity.getUserLock() != 0) {
            throw new ControllerException("业务异常");
        }

        resultPo.setStatus("SUCCESS");
        resultPo.setData(userEntity);
        resultPo.setMessage("注册成功");

        return resultPo;
    }

    public ResultPo weChatRegister(String ip, WeChatUserBasePo weChatUserBasePo,
                                   WeChatUserInfoEntity weChatUserInfoEntity) throws ControllerException {

        if (weChatUserBasePo == null || weChatUserInfoEntity == null) {
            throw new ControllerException("信息为空");
        }

        ResultPo resultPo = new ResultPo();

        //用户详细信息
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoRepository.saveAndFlush(userInfoEntity);

        //用户详细信息
        UserInfoOtherEntity userInfoOtherEntity = new UserInfoOtherEntity();
        userInfoOtherRepository.saveAndFlush(userInfoOtherEntity);

        //添加用户择偶信息
        UserMateEntity userMateEntity = new UserMateEntity();
        userMateRepository.saveAndFlush(userMateEntity);

        //添加关注信息
        UserPopularEntity userPopularEntity = new UserPopularEntity();
        userPopularRepository.saveAndFlush(userPopularEntity);

        //认证
        UserProveEntity userProveEntity = new UserProveEntity();
        userProveRepository.saveAndFlush(userProveEntity);


        UserEntity userEntity = new UserEntity();

        userEntity.setMobile("");
        userEntity.setPassword("");
        userEntity.setRegisterTime((int) (System.currentTimeMillis() / 1000));
        userEntity.setRegisterIp(ip);
        userEntity.setLastLoginTime((int) (System.currentTimeMillis() / 1000));
        userEntity.setLastLoginIp(ip);

        userEntity.setUserInfoId(userInfoEntity.getId());
        userEntity.setUserInfoOtherId(userInfoOtherEntity.getId());
        userEntity.setUserMateId(userMateEntity.getId()); //择偶
        userEntity.setUserPopularId(userPopularEntity.getId()); //关注
        userEntity.setUserProveId(userProveEntity.getId()); //认证
        userRepository.saveAndFlush(userEntity);

        if (userEntity.getUserLock() != 0) {
            throw new ControllerException("业务异常");
        }

        //weChat
        WeChatTokenEntity weChatTokenEntity = new WeChatTokenEntity();

        weChatTokenEntity.setUserId(userEntity.getId());
        weChatTokenEntity.setOpenId(weChatUserBasePo.getOpenid());
        weChatTokenEntity.setRefreshToken(weChatUserBasePo.getRefresh_token());
        weChatTokenEntity.setAccessToken(weChatUserBasePo.getAccess_token());
        weChatTokenEntity.setUnionId(weChatUserInfoEntity.getUnionid());
        weChatTokenEntity.setExpiresIn(weChatUserBasePo.getExpires_in());
        weChatTokenEntity.setRefreshTime((int) (System.currentTimeMillis() / 1000));

        weChatTokenRepository.saveAndFlush(weChatTokenEntity);

        resultPo.setStatus("SUCCESS");
        resultPo.setData(userEntity);
        resultPo.setMessage("注册成功");

        return resultPo;
    }

    public UserInfoEntity getUserInfoEntityByUserInfoId(int userInfoId) {

        UserInfoEntity userInfoEntity = userInfoRepository.findOne(userInfoId);

        return userInfoEntity;
    }

    public UserInfoOtherEntity getUserInfoOtherEntityByUserInfoOtherId(int userInfoOtherId) {
        UserInfoOtherEntity userInfoOtherEntity = userInfoOtherRepository.findOne(userInfoOtherId);
        return userInfoOtherEntity;
    }

    public UserInfoEntity getUserInfoEntityByUserId(int userId) {
        UserEntity userEntity = userRepository.findOne(userId);
        return getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());
    }

    public UserEntity getUserEntityByUserId(int userId) {
        return userRepository.findOne(userId);
    }

    public UserEntity getUserEntityByUserProveId(int userProveId) {
        return userRepository.findByUserProveId(userProveId);
    }

    public UserEntity getUserEntityByUserInfoId(int userInfoId) {
        return userRepository.findByUserInfoId(userInfoId);
    }

    public List<Integer> getUserIdListByNickname(String nickname) {

        List<UserInfoEntity> userInfoEntityList = userInfoRepository.findByNickname(nickname);

        List<Integer> userIdList = new ArrayList<>();

        for (UserInfoEntity userInfoEntity : userInfoEntityList) {

            UserEntity userEntity = getUserEntityByUserInfoId(userInfoEntity.getId());

            userIdList.add(userEntity.getId());
        }

        return userIdList;
    }

    public UserProveEntity getUserProveEntityByUserProveId(int userProveId) {
        return userProveRepository.findOne(userProveId);
    }

    public UserProveEntity getUserProveEntityByUserId(int userId) {
        UserEntity userEntity = getUserEntityByUserId(userId);
        return getUserProveEntityByUserProveId(userEntity.getUserProveId());
    }

    public List<AttentionEntity> getAttentionEntitiesByUserId(int userId) {
        return attentionRepository.findByFormAttentionIdAndAttentionStatus(userId, 0);
    }

    //获取用户择偶信息实体
    public UserMateEntity getUserMateEntityByUserMateId(int userMateId) {
        return userMateRepository.findOne(userMateId);
    }

    public FriendsEntity getFriendsEntityByUserIdAndFriendsIdAndDeleteTag(int userId, int friendsId, int deleteTag) {
        if (userId < friendsId) {
            return friendsRepository.findByUserIdAndFriendIdAndDeleteTag(userId, friendsId, deleteTag);
        }
        return friendsRepository.findByUserIdAndFriendIdAndDeleteTag(friendsId, userId, deleteTag);
    }

    public FriendsEntity getFriendsEntityByUserIdAndFriendsId(int userId, int friendsId) {
        if (userId < friendsId) {
            return friendsRepository.findByUserIdAndFriendId(userId, friendsId);
        }
        return friendsRepository.findByUserIdAndFriendId(friendsId, userId);
    }

    public Page<FriendsEntity> getFriendsEntityList(int userId, int pageCode, int pageSize) {

        Sort sort = new Sort(Sort.Direction.DESC, "addTime");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);

        Specification<FriendsEntity> specification = new Specification<FriendsEntity>() {
            @Override
            public Predicate toPredicate(Root<FriendsEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate conditionA = criteriaBuilder.equal(root.get("userId"), userId);

                Predicate conditionB = criteriaBuilder.equal(root.get("friendId"), userId);

                Predicate conditionC = criteriaBuilder.equal(root.get("deleteTag"), 0);

                return criteriaBuilder.and(conditionC, criteriaBuilder.or(conditionA, conditionB));
            }
        };
        return friendsRepository.findAll(specification, pageable);
    }

    public UserPopularEntity getUserPopularEntityByUserPopularId(int userPopularId) {
        UserPopularEntity userPopularEntity = userPopularRepository.findOne(userPopularId);
        return userPopularEntity;
    }

    public void updateFlowerTotal(int userId, int flowerTotal) {
        userRepository.addFlowerTotal(userId, flowerTotal);
    }

    public void updateUserVip(int userId, Integer timestamp, int balance, String vipType) {
        userRepository.updateUserVip(userId, timestamp, vipType, balance);

    }

    public void updateUserBalance(int userId) {
        userRepository.updateUserBalance(userId);

    }

    //关注
    public ResultPo addAttention(AttentionVo attentionVo, UserEntity userEntity) throws ControllerException {

        if (attentionVo.getToAttentionId() <= 0) {
            throw new ControllerException("参数错误");
        }

        AttentionEntity attentionEntity = null;
        UserEntity userEntityToAttention = getUserEntityByUserId(attentionVo.getToAttentionId());
        ResultPo resultPo = new ResultPo();

        switch (attentionVo.getType()) {
            case 1: {
                //添加关注

                if (attentionVo.getToAttentionId() == userEntity.getId()) {
                    resultPo.setStatus("ERROR");
                    resultPo.setMessage("不能关注自己");
                    return resultPo;
                }

                attentionEntity = attentionRepository.findByFormAttentionIdAndToAttentionIdAndAttentionStatus(userEntity.getId(), attentionVo.getToAttentionId(), 0);

                if (attentionEntity != null) {
                    resultPo.setStatus("SUCCESS");
                    resultPo.setMessage("已关注");
                    return resultPo;
                }

                attentionEntity = attentionRepository.findByFormAttentionIdAndToAttentionIdAndAttentionStatus(userEntity.getId(), attentionVo.getToAttentionId(), 1);

                //重复记录
                if (attentionEntity != null) {
                    attentionEntity.setAttentionStatus(0);
                    attentionEntity.setCreateTime((int) (System.currentTimeMillis() / 1000));
                    userPopularRepository.addToAttentionTotal(userEntity.getUserPopularId());
                    userPopularRepository.addFormAttentionTotal(userEntityToAttention.getUserPopularId());
                    attentionRepository.saveAndFlush(attentionEntity);
                    resultPo.setStatus("SUCCESS");
                    resultPo.setMessage("关注成功");
                    return resultPo;
                }

                attentionEntity = new AttentionEntity();
                attentionEntity.setFormAttentionId(userEntity.getId());
                attentionEntity.setToAttentionId(attentionVo.getToAttentionId());
                attentionEntity.setAttentionStatus(0);
                attentionEntity.setCreateTime((int) (System.currentTimeMillis() / 1000));
                attentionRepository.saveAndFlush(attentionEntity);
                //数量加一
                userPopularRepository.addToAttentionTotal(userEntity.getUserPopularId());
                userPopularRepository.addFormAttentionTotal(userEntityToAttention.getUserPopularId());
                resultPo.setStatus("SUCCESS");
                resultPo.setMessage("关注成功");
                break;
            }
            case 2: {
                //取消关注
                attentionEntity = attentionRepository.findByFormAttentionIdAndToAttentionIdAndAttentionStatus(userEntity.getId(), attentionVo.getToAttentionId(), 0);

                if (attentionEntity == null) {
                    resultPo.setStatus("SUCCESS");
                    resultPo.setMessage("关注TA");
                    return resultPo;
                }

                attentionEntity.setAttentionStatus(1);
                attentionEntity.setCreateTime((int) (System.currentTimeMillis() / 1000));
                attentionRepository.saveAndFlush(attentionEntity);
                //数量减一
                userPopularRepository.recallToAttentionTotal(userEntity.getUserPopularId());
                userPopularRepository.recallFormAttentionTotal(userEntityToAttention.getUserPopularId());
                resultPo.setStatus("SUCCESS");
                resultPo.setMessage("已取消关注");
                break;
            }
            default: {
                throw new ControllerException("业务异常");
            }
        }

        return resultPo;
    }

    //获取关注信息
    public ResultPo getAttentionMessage(int toAttentionId, UserEntity userEntity) throws ControllerException {

        if (userEntity.getUserPopularId() == 0) {
            throw new ControllerException("用户信息填写不完整");
        }

        UserPopularPo userPopularPo = new UserPopularPo();
        ResultPo resultPo = new ResultPo();
        UserPopularEntity userPopularEntity = null;

        if (toAttentionId == -1) {

            userPopularEntity = getUserPopularEntityByUserPopularId(userEntity.getUserPopularId());
            userPopularPo.setFlowerTotal(getFlowerTotal(userEntity.getId()));
        } else {

            //排除自己
            if (toAttentionId != userEntity.getId()) {

                AttentionEntity attentionEntity = attentionRepository.findByFormAttentionIdAndToAttentionIdAndAttentionStatus(userEntity.getId(), toAttentionId, 0);
                if (attentionEntity != null) {
                    userPopularPo.setAttentionStatus("已关注");
                }
                UserEntity toAttentionUserEntity = getUserEntityByUserId(toAttentionId);
                userPopularEntity = getUserPopularEntityByUserPopularId(toAttentionUserEntity.getUserPopularId());

            } else {

                userPopularEntity = getUserPopularEntityByUserPopularId(userEntity.getUserPopularId());
            }

            userPopularPo.setFlowerTotal(getFlowerTotal(toAttentionId));
        }

        userPopularPo.setToAttentionTotal(userPopularEntity.getToAttentionTotal());
        userPopularPo.setFormAttentionTotal(userPopularEntity.getFormAttentionTotal());


        resultPo.setStatus("SUCCESS");
        resultPo.setData(userPopularPo);
        return resultPo;
    }

    //好友请求处理
    public ResultPo applyFriends(ApplyFriendsVo applyFriendsVo, int applyId) {

        ResultPo resultPo = new ResultPo();

        //1:好友请求 2:请求处理 3:删除好友
        switch (applyFriendsVo.getType()) {
            case 1: {

                if (applyFriendsVo.getApplyObjectId() == 0) {
                    resultPo.setStatus("ERROR");
                    resultPo.setMessage("请求对象不存在");
                    return resultPo;
                }

                if (applyFriendsVo.getApplyObjectId() == applyId) {
                    resultPo.setStatus("ERROR");
                    resultPo.setMessage("不能添加自己为好友");
                    return resultPo;
                }

                //重复请求
                ApplyFriendEntity applyFriendEntity = applyFriendRepository.findByApplyIdAndApplyObjectIdAndDeleteTag(applyId, applyFriendsVo.getApplyObjectId(), 0);
                if (applyFriendEntity != null) {
                    resultPo.setData(-1);
                    resultPo.setStatus("SUCCESS");
                    resultPo.setMessage("正在等待好友同意");
                    return resultPo;
                }

                //已经是好友
                FriendsEntity friendsEntity = getFriendsEntityByUserIdAndFriendsIdAndDeleteTag(applyId, applyFriendsVo.getApplyObjectId(), 0);
                if (friendsEntity != null) {
                    resultPo.setData(0);
                    resultPo.setStatus("ERROR");
                    resultPo.setMessage("对方已经是你好友");
                    return resultPo;
                }

                //flowerTotal处理
                if (getFlowerTotal(applyId) <= 0) {
                    resultPo.setStatus("ERROR");
                    resultPo.setMessage("你的鲜花数量不足，请充值！");
                    return resultPo;
                }

                UserInfoEntity userInfoEntity = getUserInfoEntityByUserId(applyId);

                String notifyContent = userInfoEntity.getNickname() + " 请求加您为好友！";

                FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

                WeChatMessagePo weChatMessagePo = new WeChatMessagePo();
                weChatMessagePo.setDescription(userInfoEntity.getNickname() + " 请求加您为好友！");
                weChatMessagePo.setPicurl("http:" + filePo.getFileHost() + "/" + filePo.getFileName() + filePo.getFileType());
                weChatMessagePo.setTitle("有人添加您为好友啦！");
                weChatMessagePo.setUrl("http://" + weChatService.getDOMAIN() + "/m/home.html");

                weChatService.sendWeChatMessage(applyFriendsVo.getApplyObjectId(), weChatMessagePo);

                NotifyEntity notifyEntity = new NotifyEntity();
                notifyEntity.setCreateTime((int) (System.currentTimeMillis() / 1000));
                notifyEntity.setNotifyContent(notifyContent);
                notifyEntity.setNotifyStatus(0);
                notifyEntity.setNotifyToUserId(applyFriendsVo.getApplyObjectId());
                notifyEntity.setNotifyType(3);

                notifyService.setNotify(notifyEntity);

                applyFriendEntity = new ApplyFriendEntity();
                applyFriendEntity.setApplyId(applyId);
                applyFriendEntity.setApplyObjectId(applyFriendsVo.getApplyObjectId());
                applyFriendEntity.setCreateTime((int) applyFriendsVo.getCreateTime());
                applyFriendRepository.saveAndFlush(applyFriendEntity);

                //flowerTotal changer
                userRepository.recallFlowerTotal(applyId);
                userRepository.addFlowerTotal(applyFriendsVo.getApplyObjectId());

                resultPo.setData(1);
                resultPo.setStatus("SUCCESS");
                resultPo.setMessage("鲜花赠送成功,正在等待好友同意");
                break;
            }
            case 2: {
                ApplyFriendEntity applyFriendEntity = applyFriendRepository.findByApplyIdAndApplyObjectIdAndDeleteTag(applyFriendsVo.getApplyObjectId(), applyId, 0);
                //接受
                if (applyFriendsVo.getAccept() == 1) {
                    FriendsEntity friendsEntity = getFriendsEntityByUserIdAndFriendsId(applyId, applyFriendsVo.getApplyObjectId());

                    UserInfoEntity userInfoEntity = getUserInfoEntityByUserId(applyId);

                    String notifyContent = userInfoEntity.getNickname() + " 接受了您的好友请求！";

                    FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

                    WeChatMessagePo weChatMessagePo = new WeChatMessagePo();
                    weChatMessagePo.setDescription(userInfoEntity.getNickname() + " 同意加您为好友！");
                    weChatMessagePo.setPicurl("http:" + filePo.getFileHost() + "/" + filePo.getFileName() + filePo.getFileType());
                    weChatMessagePo.setTitle("您和" + userInfoEntity.getNickname() + "成为好友了，赶快去聊天吧！");
                    weChatMessagePo.setUrl("http://" + weChatService.getDOMAIN() + "/m/home.html");

                    weChatService.sendWeChatMessage(applyFriendsVo.getApplyObjectId(), weChatMessagePo);

                    NotifyEntity notifyEntity = new NotifyEntity();
                    notifyEntity.setCreateTime((int) (System.currentTimeMillis() / 1000));
                    notifyEntity.setNotifyContent(notifyContent);
                    notifyEntity.setNotifyStatus(0);
                    notifyEntity.setNotifyToUserId(applyFriendsVo.getApplyObjectId());
                    notifyEntity.setNotifyType(3);

                    notifyService.setNotify(notifyEntity);

                    if (friendsEntity != null) {
                        applyFriendEntity.setAccept(1);
                        applyFriendEntity.setDeleteTag(1); //请求已处理
                        applyFriendEntity.setCreateTime((int) (System.currentTimeMillis() / 1000));
                        friendsEntity.setDeleteTag(0);
                        applyFriendRepository.saveAndFlush(applyFriendEntity);
                        friendsRepository.saveAndFlush(friendsEntity);
                        resultPo.setStatus("SUCCESS");
                        resultPo.setMessage("添加成功");
                        return resultPo;
                    }

                    friendsEntity = new FriendsEntity();
                    if (applyId < applyFriendsVo.getApplyObjectId()) {
                        friendsEntity.setUserId(applyId);
                        friendsEntity.setFriendId(applyFriendsVo.getApplyObjectId());
                    } else {
                        friendsEntity.setUserId(applyFriendsVo.getApplyObjectId());
                        friendsEntity.setFriendId(applyId);
                    }
                    applyFriendEntity.setAccept(1);
                    applyFriendEntity.setDeleteTag(1); //请求已处理
                    applyFriendEntity.setCreateTime((int) (System.currentTimeMillis() / 1000));
                    applyFriendRepository.saveAndFlush(applyFriendEntity);
                    friendsRepository.saveAndFlush(friendsEntity);

                    resultPo.setStatus("SUCCESS");
                    resultPo.setMessage("添加成功");
                }

                //不接受
                if (applyFriendsVo.getAccept() == 2) {

                    String notifyContent = getUserInfoEntityByUserId(applyId).getNickname() + " 拒绝了您的好友请求！";

                    NotifyEntity notifyEntity = new NotifyEntity();
                    notifyEntity.setCreateTime((int) (System.currentTimeMillis() / 1000));
                    notifyEntity.setNotifyContent(notifyContent);
                    notifyEntity.setNotifyStatus(0);
                    notifyEntity.setNotifyToUserId(applyFriendsVo.getApplyObjectId());
                    notifyEntity.setNotifyType(3);

                    notifyService.setNotify(notifyEntity);

                    applyFriendEntity.setAccept(2);
                    applyFriendEntity.setDeleteTag(1); //请求已处理
                    applyFriendEntity.setCreateTime((int) (System.currentTimeMillis() / 1000));
                    applyFriendRepository.saveAndFlush(applyFriendEntity);
                    resultPo.setStatus("SUCCESS");
                    resultPo.setMessage("已拒绝好友请求");
                }
                break;
            }
            case 3: {
                //删除好友
                FriendsEntity friendsEntity;
                if (applyId < applyFriendsVo.getApplyObjectId()) {
                    friendsEntity = getFriendsEntityByUserIdAndFriendsId(applyId, applyFriendsVo.getApplyObjectId());

                } else {

                    friendsEntity = getFriendsEntityByUserIdAndFriendsId(applyFriendsVo.getApplyObjectId(), applyId);
                }

                friendsEntity.setDeleteTag(1);
                friendsEntity.setAddTime((int) (System.currentTimeMillis() / 1000));
                friendsRepository.saveAndFlush(friendsEntity);
                resultPo.setStatus("SUCCESS");
                resultPo.setMessage("删除成功");
                break;
            }
            default: {
                resultPo.setStatus("ERROR");
                resultPo.setMessage("请求失败");
                break;
            }
        }

        return resultPo;
    }

    //获取好友请求or申请列表
    public ApplyFriendsListPo getApplyFriendsPoList(int userId, int pageCode, int pageSize) throws ControllerException {

        if (pageCode < 0 || pageSize < 0) {
            throw new ControllerException("页面加载失败");
        }

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);

        Page<ApplyFriendEntity> applyFriendEntitiesPage = applyFriendRepository.findByApplyIdOrApplyObjectId(userId, userId, pageable);
        List<ApplyFriendEntity> applyFriendEntityList = applyFriendEntitiesPage.getContent();

        ApplyFriendsListPo applyFriendsListPo = new ApplyFriendsListPo();
        List<ApplyFriendsPo> applyFriendsPos = new ArrayList<>();
        if (applyFriendEntityList.size() > 0) {
            for (ApplyFriendEntity applyFriendEntity : applyFriendEntityList) {
                ApplyFriendsPo applyFriendsPo = new ApplyFriendsPo();
                UserInfoEntity userInfoEntity;

                if (applyFriendEntity.getApplyId() == userId) {
                    userInfoEntity = getUserInfoEntityByUserId(applyFriendEntity.getApplyObjectId());

                } else {

                    userInfoEntity = getUserInfoEntityByUserId(applyFriendEntity.getApplyId());
                }

                FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

                applyFriendsPo.setUserId(userId);
                applyFriendsPo.setAvatar(filePo);
                applyFriendsPo.setTrueName(userInfoEntity.getNickname());
                applyFriendsPo.setApplyFriendEntity(applyFriendEntity);
                applyFriendsPos.add(applyFriendsPo);
            }

            applyFriendsListPo.setApplyFriendsPoList(applyFriendsPos);
        }

        applyFriendsListPo.setPage(pageCode);
        applyFriendsListPo.setPageSize(pageSize);
        applyFriendsListPo.setPageTotal(applyFriendEntitiesPage.getTotalPages());

        return applyFriendsListPo;
    }

    //获取好友列表
    public FriendsListPo getFriendsList(int userId, int pageCode, int pageSize) {

        Page<FriendsEntity> friendsEntities = getFriendsEntityList(userId, pageCode, pageSize);
        List<FriendsEntity> friendsEntityList = friendsEntities.getContent();

        System.out.println("大小：" + friendsEntities.getContent().size() + "");

        List<FriendsPo> friendsPos = new ArrayList<>();
        FriendsListPo friendsListPo = new FriendsListPo();
        for (FriendsEntity friendsEntity : friendsEntityList) {
            FriendsPo friendsPo = new FriendsPo();
            UserInfoEntity userInfoEntity;

            if (friendsEntity.getUserId() == userId) {

                userInfoEntity = getUserInfoEntityByUserId(friendsEntity.getFriendId());
                friendsPo.setFriendId(friendsEntity.getFriendId());
            } else {

                userInfoEntity = getUserInfoEntityByUserId(friendsEntity.getUserId());
                friendsPo.setFriendId(friendsEntity.getUserId());
            }

            friendsPo.setAvatar(fileService.getFileByFileId(userInfoEntity.getAvatarNormal()));
            friendsPo.setTrueName(userInfoEntity.getNickname());
            friendsPo.setuId(userId);
            friendsPos.add(friendsPo);
        }

        friendsListPo.setFriendsPos(friendsPos);
        friendsListPo.setPage(pageCode);
        friendsListPo.setPageSize(pageSize);
        friendsListPo.setPageTotal(friendsEntities.getTotalPages());

        return friendsListPo;
    }

    //获取关注Or粉丝列表
    public FansOrAttentionListPo getFansOrAttentionList(int type, int userId, int pageCode, int pageSize) throws ControllerException {

        Page<AttentionEntity> attentionEntities = null;

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);

        //粉丝
        if (type == 1) {
            attentionEntities = attentionRepository.findByToAttentionIdAndAttentionStatus(userId, 0, pageable);

        }
        //我的关注
        else if (type == 2) {
            attentionEntities = attentionRepository.findByFormAttentionIdAndAttentionStatus(userId, 0, pageable);

        } else {

            throw new ControllerException("页面加载失败");
        }

        List<FansOrAttentionPo> fansOrAttentionPoList = new ArrayList<>();
        FansOrAttentionListPo fansOrAttentionListPo = new FansOrAttentionListPo();
        for (AttentionEntity attentionEntity : attentionEntities.getContent()) {

            FansOrAttentionPo fansOrAttentionPo = new FansOrAttentionPo();

            UserInfoEntity fansInfoEntity = null;
            if (type == 1) {
                fansInfoEntity = getUserInfoEntityByUserId(attentionEntity.getFormAttentionId());
                fansOrAttentionPo.setFansId(attentionEntity.getFormAttentionId());
                fansOrAttentionPo.setAttentionStatus("关注");
                AttentionEntity isAttention = attentionRepository.findByFormAttentionIdAndToAttentionIdAndAttentionStatus(userId, attentionEntity.getFormAttentionId(), 0);

                if (isAttention != null) {
                    fansOrAttentionPo.setAttentionStatus("互相关注");
                }
            }

            if (type == 2) {
                fansInfoEntity = getUserInfoEntityByUserId(attentionEntity.getToAttentionId());
                fansOrAttentionPo.setFansId(attentionEntity.getToAttentionId());
                fansOrAttentionPo.setAttentionStatus("取消关注");
            }

            FilePo fansFilePo = fileService.getFileByFileId(fansInfoEntity.getAvatarNormal());

            fansOrAttentionPo.setAvatar(fansFilePo);
            fansOrAttentionPo.setFansName(fansInfoEntity.getNickname());
            fansOrAttentionPo.setFansSex(fansInfoEntity.getSex());
            fansOrAttentionPo.setAge(fansInfoEntity.getBirth());
            fansOrAttentionPo.setTime(attentionEntity.getCreateTime());

            fansOrAttentionPoList.add(fansOrAttentionPo);
        }

        fansOrAttentionListPo.setFansOrAttentionPo(fansOrAttentionPoList);
        fansOrAttentionListPo.setPage(pageCode);
        fansOrAttentionListPo.setPageSize(pageSize);
        fansOrAttentionListPo.setPageTotal(attentionEntities.getTotalPages());

        return fansOrAttentionListPo;
    }

    //修改用户其他信息
    public ResultPo updateOtherProfile(UserInfoOtherVo userInfoOtherVo, UserEntity userEntity) {

        UserInfoOtherEntity userInfoOtherEntity = getUserInfoOtherEntityByUserInfoOtherId(userEntity.getUserInfoOtherId());
        ResultPo resultPo = new ResultPo();

        userInfoOtherEntity.setWeight(userInfoOtherVo.getWeight()); //体重
        userInfoOtherEntity.setLiveProvinceId(userInfoOtherVo.getLiveProvinceId()); //省
        userInfoOtherEntity.setLiveCityId(userInfoOtherVo.getLiveCityId()); //市
        userInfoOtherEntity.setLiveAreaId(userInfoOtherVo.getLiveAreaId()); //区
        userInfoOtherEntity.setConstellation(userInfoOtherVo.getConstellation()); //星座
        userInfoOtherEntity.setCareer(userInfoOtherVo.getProfession()); //职业
        userInfoOtherEntity.setHasCar(userInfoOtherVo.getHasCar()); //车
        userInfoOtherEntity.setHasHouse(userInfoOtherVo.getHasHome()); //房
        userInfoOtherEntity.setWorkPosition(userInfoOtherVo.getWorkPosition()); //职位
        userInfoOtherEntity.setWorkUnit(userInfoOtherVo.getCompany()); //单位
        userInfoOtherEntity.setYearIncome(userInfoOtherVo.getIncome()); //年收入
        userInfoOtherEntity.setSchool(userInfoOtherVo.getSchool()); //毕业学校
        userInfoOtherEntity.setMarriageStatus(userInfoOtherVo.getMaritalStatus()); //婚姻状态
        userInfoOtherEntity.setFamilyStructure(userInfoOtherVo.getFamilyStruct()); //家庭状况
        userInfoOtherEntity.setCharacterColor(userInfoOtherVo.getCharacterColor()); //性格色彩
        userInfoOtherEntity.setInterest(userInfoOtherVo.getInterest()); //兴趣
        userInfoOtherEntity.setSelfIntroduction(userInfoOtherVo.getSelfIntroduction()); //自我介绍
        userInfoOtherRepository.saveAndFlush(userInfoOtherEntity);

        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("修改成功");
        return resultPo;
    }

    //更新用户择偶信息
    public ResultPo updateMateInfoProfile(UserMetaInfoVo userMetaInfoVo, UserEntity userEntity) throws ControllerException {

        ResultPo resultPo = new ResultPo();
        if (userEntity.getUserMateId() == 0) {
            throw new ControllerException("择偶信息未填写");
        }

        UserMateEntity userMateEntity = getUserMateEntityByUserMateId(userEntity.getUserMateId());

        userMateEntity.setAgeRange(userMetaInfoVo.getAgeRang());
        userMateEntity.setHeightRange(userMetaInfoVo.getHeightRang());
        userMateEntity.setRefuseZodiac(userMetaInfoVo.getRefuseZodiac());
        userMateEntity.setEducation(userMetaInfoVo.getEducation());
        userMateEntity.setMarriageStatus(userMetaInfoVo.getMarriageStatus());

        userMateRepository.saveAndFlush(userMateEntity);

        userEntity.setUserMateId(userMateEntity.getId());
        userRepository.saveAndFlush(userEntity);

        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("修改成功");
        return resultPo;
    }

    //参加最新活动人员列表
    public ActivityPersonListPo getActivityUserList(int type, int userId, int pageCode, int pageSize) throws ControllerException {

        if (pageCode < 0 || pageSize < 0) {
            throw new ControllerException("页面加载失败");
        }

        if (type <= 0 ) {
            throw new ControllerException("页面加载失败");
        }

        if (userId <= 0) {
            throw new ControllerException("用户不存在");
        }

        List<ActivityPersonEntity> activityPersonEntities = activityPersonRepository.findByUserId(userId);

        Sort sort = new Sort(Sort.Direction.DESC, "joinTime");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);

        int activityId = Tool.getActivityUpToDate(activityPersonEntities);

        Page<ActivityPersonEntity> pageList = null;

        //全部
        if (type == 1) {
            pageList = activityPersonRepository.findByActivityId(activityId, pageable);
        }

        //男
        else if (type == 2) {
            pageList = activityPersonRepository.findByActivityIdAndUserSex(activityId, 1, pageable);
        }

        //女
        else if (type == 3) {
            pageList = activityPersonRepository.findByActivityIdAndUserSex(activityId, 0, pageable);
        }

        else {
            throw new ControllerException("参数错误");
        }

        activityPersonEntities = pageList.getContent();

        List<ActivityPersonPo> activityPersonPos = new ArrayList<>();
        ActivityPersonListPo activityPersonListPo = new ActivityPersonListPo();
        if (activityPersonEntities.size() > 0) {
            for (ActivityPersonEntity activityPersonEntity : activityPersonEntities) {
                ActivityPersonPo activityPersonPo = new ActivityPersonPo();

                UserInfoEntity userInfoEntity = getUserInfoEntityByUserId(activityPersonEntity.getUserId());

                FilePo avatar = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

                activityPersonPo.setUserId(activityPersonEntity.getUserId());
                activityPersonPo.setNickname(userInfoEntity.getNickname());
                activityPersonPo.setSex(userInfoEntity.getSex());
                //activityPersonPo.setSex(activityPersonEntity.getUserSex());
                activityPersonPo.setAvatar(avatar);

                activityPersonPos.add(activityPersonPo);
            }
        }

        activityPersonListPo.setActivityPersonPo(activityPersonPos);
        activityPersonListPo.setPage(pageCode);
        activityPersonListPo.setPageSize(pageSize);
        activityPersonListPo.setPageTotal(pageList.getTotalPages());
        activityPersonListPo.setActivityId(activityId);
        activityPersonListPo.setUserListType(type);

        return activityPersonListPo;
    }

    //my collection
    public ActivityPersonListPo getCollectionList(UserEntity userEntity, int pageCode, int pageSize) throws ControllerException {

        if (pageCode < 0 || pageSize < 0) {
            throw new ControllerException("页面加载失败");
        }

        if (userEntity.getId() == 0) {
            throw new ControllerException("用户不存在");
        }

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);

        Page<MateLikeEntity> pageList = mateLikeRepository.findByUserIdAndStatus(userEntity.getId(), 0, pageable);

        List<MateLikeEntity> mateLikeEntities = pageList.getContent();

        List<ActivityPersonPo> activityPersonPos = new ArrayList<>();
        ActivityPersonListPo activityPersonListPo = new ActivityPersonListPo();
        if (mateLikeEntities.size() > 0) {
            for (MateLikeEntity mateLikeEntity : mateLikeEntities) {
                ActivityPersonPo activityPersonPo = new ActivityPersonPo();

                UserInfoEntity userInfoEntity = getUserInfoEntityByUserId(mateLikeEntity.getLikeId());

                FilePo avatar = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

                activityPersonPo.setUserId(mateLikeEntity.getLikeId());
                activityPersonPo.setNickname(userInfoEntity.getNickname());
                activityPersonPo.setSex(userInfoEntity.getSex());
                activityPersonPo.setAvatar(avatar);
                activityPersonPo.setActivity(mateLikeEntity.getActivityId());

                activityPersonPos.add(activityPersonPo);
            }
        }

        activityPersonListPo.setActivityPersonPo(activityPersonPos);
        activityPersonListPo.setPage(pageCode);
        activityPersonListPo.setPageSize(pageSize);
        activityPersonListPo.setPageTotal(pageList.getTotalPages());

        return activityPersonListPo;
    }

    //1喜欢 2取消
    public ResultPo collectionOperation(UserEntity userEntity, int operationType, int collectionObjectId, int activityId)
            throws ControllerException {

        if (operationType <= 0) {
            throw new ControllerException("操作失败");
        }

        if (collectionObjectId <= 0) {
            throw new ControllerException("操作失败");
        }

        if (activityId <= 0 || cityActivityRepository.findOne(activityId) == null) {
            throw new ControllerException("活动不存在");
        }

        ResultPo resultPo = new ResultPo();
        MateLikeEntity mateLikeEntity = mateLikeRepository.findByUserIdAndLikeId(userEntity.getId(), collectionObjectId);

        if (operationType == 1) {

            if (userEntity.getId() == collectionObjectId) {
                throw new ControllerException("不能喜欢自己");
            }

            if (mateLikeEntity != null && mateLikeEntity.getStatus() == 0) {
                throw new ControllerException("不能重复喜欢");
            }

            if (getFlowerTotal(userEntity.getId()) == 0) {
                throw new ControllerException("鲜花数不足，请充值！");
            }

            UserInfoEntity userInfoEntity = userInfoRepository.findOne(userEntity.getUserInfoId());

            FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

            WeChatMessagePo weChatMessagePo = new WeChatMessagePo();
            weChatMessagePo.setDescription(userInfoEntity.getNickname() + "在活动中选择了您！");
            weChatMessagePo.setPicurl("http:" + filePo.getFileHost() + "/" + filePo.getFileName() + filePo.getFileType());
            weChatMessagePo.setTitle("有人悄悄的在活动上喜欢上你了哦，赶紧去看看他是谁吧！");
            weChatMessagePo.setUrl("http://" + weChatService.getDOMAIN() + "/m/userProfile.html?id=" + userEntity.getId());

            weChatService.sendWeChatMessage(collectionObjectId, weChatMessagePo);

            if (mateLikeEntity != null && mateLikeEntity.getStatus() == 1) {
                mateLikeEntity.setStatus(0);
                mateLikeRepository.saveAndFlush(mateLikeEntity);

                userRepository.recallFlowerTotal(userEntity.getId());//减
                userRepository.addFlowerTotal(collectionObjectId);//加

                resultPo.setStatus("SUCCESS");
                resultPo.setMessage("已喜欢");
                return resultPo;
            }

            mateLikeEntity = new MateLikeEntity();
            mateLikeEntity.setUserId(userEntity.getId());
            mateLikeEntity.setLikeId(collectionObjectId);
            mateLikeEntity.setActivityId(activityId);
            mateLikeEntity.setStatus(0);

            userRepository.recallFlowerTotal(userEntity.getId());//减
            userRepository.addFlowerTotal(collectionObjectId);//加

            resultPo.setStatus("SUCCESS");
            resultPo.setMessage("已喜欢");
        } else if (operationType == 2) {

            if (mateLikeEntity == null) {
                throw new ControllerException("请选择喜欢TA");
            }

            if (mateLikeEntity != null && mateLikeEntity.getStatus() == 1) {
                throw new ControllerException("请选择喜欢TA");
            }

            if (mateLikeEntity != null && mateLikeEntity.getStatus() == 0) {
                mateLikeEntity.setStatus(1);
            }

            resultPo.setStatus("SUCCESS");
            resultPo.setMessage("已取消喜欢");
        } else {
            throw new ControllerException("操作不存在");
        }

        mateLikeRepository.saveAndFlush(mateLikeEntity);

        return resultPo;
    }

    //返回用户其他信息Po
    public UserInfoOtherPo getUserInfoOther(int userInfoOtherId) throws ControllerException {

        UserInfoOtherEntity userInfoOtherEntity = getUserInfoOtherEntityByUserInfoOtherId(userInfoOtherId);

        if (userInfoOtherEntity.getId() == 0) {
            throw new ControllerException("还未填写用户基础信息");
        }

        UserInfoOtherPo userInfoOtherPo = new UserInfoOtherPo();

        if (userInfoOtherEntity.getConstellation() != 0) {
            userInfoOtherPo.setConstellation(configurationRepository.findByCodeAndType(userInfoOtherEntity.getConstellation(), 1).getValue());
        }

        if (userInfoOtherEntity.getCareer() != 0) {
            userInfoOtherPo.setProfession(configurationRepository.findByCodeAndType(userInfoOtherEntity.getCareer(), 2).getValue());
        }

        if (userInfoOtherEntity.getYearIncome() != 0) {
            userInfoOtherPo.setIncome(configurationRepository.findByCodeAndType(userInfoOtherEntity.getYearIncome(), 3).getValue());
        }

        if (userInfoOtherEntity.getMarriageStatus() != 0) {
            userInfoOtherPo.setMaritalStatus(configurationRepository.findByCodeAndType(userInfoOtherEntity.getMarriageStatus(), 4).getValue());
        }
        userInfoOtherPo.setUserInfoOther(userInfoOtherEntity);
        userInfoOtherPo.setSchool(userInfoOtherEntity.getSchool());

        return userInfoOtherPo;
    }

    //余额
    public ResultPo getBalance(int userId) {

        ResultPo resultPo = new ResultPo();

        UserEntity userEntity = getUserEntityByUserId(userId);

        BalancePo balancePo = new BalancePo();

        balancePo.setBalance(userEntity.getBalance());

        if((System.currentTimeMillis()/1000) > userEntity.getVipExp()) {
            userEntity.setVipType("普通会员");
            userEntity.setVipExp(0);
        }

        balancePo.setVipExp(userEntity.getVipExp());

        if ("".equals(userEntity.getVipType()))
            userEntity.setVipType("普通会员");

        balancePo.setVipType(userEntity.getVipType());
        balancePo.setFlowerTotal(userEntity.getFlowerTotal());

        resultPo.setStatus("SUCCESS");
        resultPo.setData(balancePo);

        return resultPo;
    }

    //获取鲜花数量
    public int getFlowerTotal(int userId) {
        return getUserEntityByUserId(userId).getFlowerTotal();
    }

    public ResultPo addProfile(UserInfoMsgVo userInfoMsgVo, UserEntity userEntity) throws ControllerException {

        ResultPo resultPo = new ResultPo();

        UserInfoEntity userInfoEntity = new UserInfoEntity();

        userInfoEntity.setNickname(userInfoMsgVo.getTrueName());
        userInfoEntity.setSex(Integer.valueOf(userInfoMsgVo.getSex()));
        userInfoEntity.setBirth(userInfoMsgVo.getBirth());
        userInfoEntity.setOriginProvinceId(Integer.valueOf(userInfoMsgVo.getProvinceID()));
        userInfoEntity.setOriginCityId(Integer.valueOf(userInfoMsgVo.getCityID()));
        userInfoEntity.setHeight(Integer.valueOf(userInfoMsgVo.getHeight()));
        userInfoEntity.setEducation(Integer.valueOf(userInfoMsgVo.getEducation()));

        userInfoRepository.saveAndFlush(userInfoEntity);

        if (userInfoEntity.getId() == 0) {
            throw new ControllerException("业务异常");
        }

        userEntity.setUserInfoId(userInfoEntity.getId());

        userRepository.saveAndFlush(userEntity);

        resultPo.setStatus("SUCCESS");

        return resultPo;
    }

    public ResultPo getMobileVerifyCode(MobileMsgVo mobileMsgVo, String smsCode) throws ControllerException {

        if (userRepository.findByMobile(mobileMsgVo.getMobile()) != null) {
            throw new ControllerException("手机号已被注册");
        }

        System.out.println("发送短信验证码:" + smsCode);
        //获取验证码

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("短信发送成功");

        return resultPo;
    }

    public ResultPo removeAvatar(int userInfoId, int type) throws ControllerException {

        UserInfoEntity userInfoEntity = userInfoRepository.findOne(userInfoId);

        if (type == 1) {
            throw new ControllerException("默认头像不能删除");
        }
        if (type == 2) {
            userInfoEntity.setAvatarProB(0);
        }
        if (type == 3) {
            userInfoEntity.setAvatarProC(0);
        }
        if (type == 4) {
            userInfoEntity.setAvatarProD(0);
        }

        userInfoRepository.saveAndFlush(userInfoEntity);

        if (type == 2) {
            if (userInfoEntity.getAvatarProB() != 0) {
                throw new ControllerException("删除失败");
            }
        }
        if (type == 3) {
            if (userInfoEntity.getAvatarProC() != 0) {
                throw new ControllerException("删除失败");
            }
        }
        if (type == 4) {
            if (userInfoEntity.getAvatarProD() != 0) {
                throw new ControllerException("删除失败");
            }
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("删除成功");

        return resultPo;
    }

    public ResultPo removeAvatar(UserInfoEntity userInfoEntity, int type) throws ControllerException {

        if (type == 1) {
            throw new ControllerException("默认头像不能删除");
        }
        if (type == 2) {
            userInfoEntity.setAvatarProB(0);
        }
        if (type == 3) {
            userInfoEntity.setAvatarProC(0);
        }
        if (type == 4) {
            userInfoEntity.setAvatarProD(0);
        }

        userInfoRepository.saveAndFlush(userInfoEntity);

        if (type == 2) {
            if (userInfoEntity.getAvatarProB() != 0) {
                throw new ControllerException("删除失败");
            }
        }
        if (type == 3) {
            if (userInfoEntity.getAvatarProC() != 0) {
                throw new ControllerException("删除失败");
            }
        }
        if (type == 4) {
            if (userInfoEntity.getAvatarProD() != 0) {
                throw new ControllerException("删除失败");
            }
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("删除成功");

        return resultPo;
    }

}


