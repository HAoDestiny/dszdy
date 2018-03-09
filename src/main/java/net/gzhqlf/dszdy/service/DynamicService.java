package net.gzhqlf.dszdy.service;

import net.gzhqlf.dszdy.entity.*;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.*;
import net.gzhqlf.dszdy.repository.DynamicCommentRepository;
import net.gzhqlf.dszdy.repository.DynamicPraiseRepository;
import net.gzhqlf.dszdy.repository.DynamicRepository;
import net.gzhqlf.dszdy.util.DynamicListComparator;
import net.gzhqlf.dszdy.vo.CommentDynamicVo;
import net.gzhqlf.dszdy.vo.DynamicMsgVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamicService {

    @Resource
    private DynamicRepository dynamicRepository;

    @Resource
    private DynamicPraiseRepository dynamicPraiseRepository;

    @Resource
    private DynamicCommentRepository dynamicCommentRepository;

    @Resource
    private UserService userService;

    @Resource
    private FileService fileService;

    public ResultPo post(UserEntity userEntity, DynamicMsgVo dynamicMsgVo) throws ControllerException {

        DynamicEntity dynamicEntity = new DynamicEntity();

        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserId(userEntity.getId());

        UserInfoOtherEntity userInfoOtherEntity = userService.getUserInfoOtherEntityByUserInfoOtherId(userEntity.getUserInfoOtherId());

        dynamicEntity.setUserId(userEntity.getId());
        dynamicEntity.setContent(dynamicMsgVo.getContent());
        dynamicEntity.setImages(dynamicMsgVo.getImages());
        dynamicEntity.setCityCode(userInfoEntity.getOriginCityId());
        dynamicEntity.setSchoolCode(userInfoOtherEntity.getSchool());
        dynamicEntity.setCreateTime(Math.toIntExact(dynamicMsgVo.getTime()));
        dynamicEntity.setDeleteTag(0);
        dynamicRepository.saveAndFlush(dynamicEntity);

        if (dynamicEntity.getId() == 0) {
            throw new ControllerException("发布失败");
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("发布成功");

        return resultPo;
    }

    public ResultPo findAllDynamic(UserEntity userEntity, int pageCode, int pageSize, int tag, List<Integer> dynamicIds) throws ControllerException {

        if (pageCode < 0 || pageSize < 0) {
            throw new ControllerException("页面加载失败");
        }

        ResultPo resultPo = new ResultPo();

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);

        Page<DynamicEntity> dynamicPage;

        // 0 全部
        if (tag == 0) {
            dynamicPage = dynamicRepository.findAll(pageable);
        }
        // 1 同乡
        else if (tag == 1) {
            UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserInfoId(userEntity.getUserInfoId());
            dynamicPage = dynamicRepository.findAllByCityCode(userInfoEntity.getOriginCityId(), pageable);
        }
        // 2 同校
        else if (tag == 2) {
            if (userEntity.getUserInfoOtherId() == 0) {
                throw new ControllerException("未填写信息");
            }
            UserInfoOtherEntity userInfoOtherEntity = userService.getUserInfoOtherEntityByUserInfoOtherId(userEntity.getUserInfoOtherId());

            if (userInfoOtherEntity.getSchool().equals("")) {
                throw new ControllerException("未填写学校");
            }

            dynamicPage = dynamicRepository.findAllBySchoolCode(userInfoOtherEntity.getSchool() ,pageable);
        }
        // 3 个人
        else if (tag == 3) {
            dynamicPage = dynamicRepository.findByUserId(userEntity.getId(), pageable);
        }
        // 4 消息指定
        else if(tag == 4) {
            dynamicPage = dynamicRepository.findByIdIn(dynamicIds, pageable);
        }
        // 5 关注
        else if(tag == 5) {
            dynamicPage = dynamicRepository.findByIdIn(dynamicIds, pageable);
        }
        // 防null
        else {
            dynamicPage = dynamicRepository.findAll(pageable);
        }

        System.out.println("总条数：" + dynamicPage.getTotalElements());
        System.out.println("总页数：" + dynamicPage.getTotalPages());

        List<DynamicEntity> dynamicList = dynamicPage.getContent();

        List<DynamicPo> dynamicPos = new ArrayList<>();

        for (DynamicEntity dynamicEntity : dynamicList) {
            DynamicPo dynamicPo = new DynamicPo();

            dynamicPo.setDynamicId(dynamicEntity.getId());

            String images = dynamicEntity.getImages();
            List<FilePo> filePoList = new ArrayList<>();

            if (!"".equals(images)) {
                if (images.contains(",")) {
                    String[] imagesId = images.split(",");
                    for (String id : imagesId) {
                        FilePo filePo = fileService.getFileByFileId(Integer.parseInt(id));
                        filePoList.add(filePo);
                    }

                } else {
                    FilePo filePo = fileService.getFileByFileId(Integer.parseInt(images));
                    filePoList.add(filePo);
                }
            }

            UserEntity posterEntity = userService.getUserEntityByUserId(dynamicEntity.getUserId());
            UserInfoEntity posterInfoEntity = userService.getUserInfoEntityByUserId(dynamicEntity.getUserId());
            FilePo filePoAvatar = fileService.getFileByFileId(posterInfoEntity.getAvatarNormal());

            dynamicPo.setPosterId(posterEntity.getId());
            dynamicPo.setPosterName(posterInfoEntity.getNickname());
            dynamicPo.setIsVerify(posterEntity.getIsVerify());
            dynamicPo.setAge(posterInfoEntity.getBirth());
            dynamicPo.setSex(posterInfoEntity.getSex());
            dynamicPo.setAvatar(filePoAvatar);
            dynamicPo.setImages(filePoList);
            dynamicPo.setContent(dynamicEntity.getContent());
            dynamicPo.setTime(dynamicEntity.getCreateTime());

            //点赞
            List<DynamicPraiseEntity> dynamicPraiseEntities = findDynamicPraiseEntityByDynamicId(dynamicEntity.getId());
            PraisePo praisePo = new PraisePo();
            List<PraiseListPo> praiseListPos = new ArrayList<>();


            for (DynamicPraiseEntity dynamicPraiseEntity : dynamicPraiseEntities) {
                PraiseListPo praiseListPo = new PraiseListPo();

                UserEntity praiseUserEntity = userService.getUserEntityByUserId(dynamicPraiseEntity.getPraisePersonId());

                UserInfoEntity praiseUserInfoEntity = userService.getUserInfoEntityByUserInfoId(praiseUserEntity.getUserInfoId());

                FilePo filePo = fileService.getFileByFileId(praiseUserInfoEntity.getAvatarNormal());

                praiseListPo.setUserId(praiseUserEntity.getId());
                praiseListPo.setUsername(praiseUserInfoEntity.getNickname());
                praiseListPo.setAvatar(filePo);

                praiseListPos.add(praiseListPo);

            }
            praisePo.setPraiseList(praiseListPos);
            praisePo.setPraiseTotal(dynamicEntity.getPraiseTotal());
            dynamicPo.setPraise(praisePo);

            //评论
            List<DynamicCommentEntity> dynamicCommentEntities = findDynamicCommentEntityByDynamicId(dynamicEntity.getId());
            CommentPo commentPo = new CommentPo();
            List<CommentListPo> commentListPos = new ArrayList<>();

            Map<Integer, UserInfoEntity> userInfoEntityMap = new HashMap<>();

            for (DynamicCommentEntity dynamicCommentEntity : dynamicCommentEntities) {
                boolean isCommentToPoster = false;
                CommentListPo commentListPo = new CommentListPo();
                UserInfoEntity commentUserInfoEntity;

                if (!userInfoEntityMap.containsKey(dynamicCommentEntity.getCommentPersonId())) {
                    commentUserInfoEntity = userService.getUserInfoEntityByUserId(dynamicCommentEntity.getCommentPersonId());
                    userInfoEntityMap.put(dynamicCommentEntity.getCommentPersonId(), commentUserInfoEntity);
                }

                if (dynamicCommentEntity.getCommentToUserId() == 0) {
                    isCommentToPoster = true;
                    dynamicCommentEntity.setCommentToUserId(dynamicEntity.getUserId());
                }

                if (!userInfoEntityMap.containsKey(dynamicCommentEntity.getCommentToUserId())) {
                    commentUserInfoEntity = userService.getUserInfoEntityByUserId(dynamicCommentEntity.getCommentToUserId());
                    userInfoEntityMap.put(dynamicCommentEntity.getCommentToUserId(), commentUserInfoEntity);
                }

                FilePo filePo = fileService.getFileByFileId(userInfoEntityMap.get(dynamicCommentEntity.getCommentPersonId()).getAvatarNormal());

                commentListPo.setDynamicId(dynamicCommentEntity.getDynamicId());

                commentListPo.setUserId(dynamicCommentEntity.getCommentPersonId());
                commentListPo.setUsername(userInfoEntityMap.get(dynamicCommentEntity.getCommentPersonId()).getNickname());
                commentListPo.setAvatar(filePo);

                commentListPo.setContent(dynamicCommentEntity.getCommentContent());

                if (isCommentToPoster) {
                    commentListPo.setCommentToUserId(0);
                } else {
                    commentListPo.setCommentToUserId(dynamicCommentEntity.getCommentToUserId());
                }

                commentListPo.setCommentToUserName(userInfoEntityMap.get(dynamicCommentEntity.getCommentToUserId()).getNickname());
//                commentListPo.setCommentToUserId(dynamicCommentEntity.getCommentToUserId());
//                commentListPo.setCommentToUserId(dynamicEntity.getUserId());
                commentListPo.setTime(dynamicCommentEntity.getCreateCommentTime());

                commentListPos.add(commentListPo);
            }

            commentPo.setCommentList(commentListPos);
            commentPo.setCommentTotal(dynamicEntity.getCommentTotal());
            dynamicPo.setComment(commentPo);

            dynamicPos.add(dynamicPo);
        }

        DynamicListComparator dynamicListComparator = new DynamicListComparator();
        dynamicPos.sort(dynamicListComparator);

        DynamicListPo dynamicListPo = new DynamicListPo();
        dynamicListPo.setDynamic(dynamicPos);
        dynamicListPo.setPage(pageCode);
        dynamicListPo.setPageSize(Math.toIntExact(dynamicPage.getTotalElements()));
        dynamicListPo.setPageTotal(dynamicPage.getTotalPages());

        resultPo.setStatus("SUCCESS");
        resultPo.setData(dynamicListPo);

        return resultPo;


    }

    public ResultPo findAllDynamic(UserEntity userEntity, int pageCode, int pageSize, int tag) throws ControllerException {
        return findAllDynamic(userEntity, pageCode, pageSize, tag, null);

    }

    public ResultPo praise(int dynamicId, int praisePersonId) throws ControllerException {

        DynamicEntity dynamicEntity = dynamicRepository.findOne(dynamicId);
        ResultPo resultPo = new ResultPo();

        if (dynamicEntity.getUserId() == praisePersonId) {
            throw new ControllerException("不能点赞自己");
        }

        if (dynamicPraiseRepository.findByDynamicIdAndPraisePersonId(dynamicId, praisePersonId) != null) {
            throw new ControllerException("不能重复点赞");
        }

        //点赞数+1
        dynamicPraiseRepository.updatePraiseTotal(dynamicId);

        DynamicPraiseEntity dynamicPraiseEntity = new DynamicPraiseEntity();
        dynamicPraiseEntity.setDynamicId(dynamicId);
        dynamicPraiseEntity.setPraisePersonId(praisePersonId);

        dynamicPraiseRepository.saveAndFlush(dynamicPraiseEntity);

        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("点赞成功");

        return resultPo;
    }

    public ResultPo comment(CommentDynamicVo commentDynamicVo, int commentPersonId) throws ControllerException {

        ResultPo resultPo = new ResultPo();

        if (commentDynamicVo.getDynamicId() == 0) {
            throw new ControllerException("评论失败");
        }

        //评论数+1
        dynamicRepository.updateCommentTotal(commentDynamicVo.getDynamicId());

        DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();

        dynamicCommentEntity.setCommentToUserId(commentDynamicVo.getCommentToUserId());
        dynamicCommentEntity.setCommentPersonId(commentPersonId);
        dynamicCommentEntity.setCommentContent(commentDynamicVo.getContent());
        dynamicCommentEntity.setCreateCommentTime(commentDynamicVo.getTime());
        dynamicCommentEntity.setDynamicId(commentDynamicVo.getDynamicId());
        dynamicCommentEntity.setDeleteTag(0);

        dynamicCommentRepository.saveAndFlush(dynamicCommentEntity);

        CommentListPo commentListPo = new CommentListPo();

        UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserId(commentPersonId);

        UserInfoEntity dynamicPosterInfoEntity =
                userService.getUserInfoEntityByUserId(
                        dynamicRepository.findOne(commentDynamicVo.getDynamicId()).getUserId()
                );

//        if (dynamicCommentEntity.getCommentToUserId() == 0) {
//            dynamicCommentEntity.setCommentToUserId(dynamicPosterInfoEntity.getId());
//        }

        FilePo filePo = fileService.getFileByFileId(userInfoEntity.getAvatarNormal());

        commentListPo.setDynamicId(commentDynamicVo.getDynamicId());
        commentListPo.setAvatar(filePo);
        commentListPo.setUserId(commentPersonId);
        commentListPo.setUsername(userInfoEntity.getNickname());
        commentListPo.setContent(commentDynamicVo.getContent());

        commentListPo.setCommentToUserName(dynamicPosterInfoEntity.getNickname());
        commentListPo.setCommentToUserId(commentDynamicVo.getCommentToUserId());

        commentListPo.setTime(commentDynamicVo.getTime());

        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("评论成功");
        resultPo.setData(commentListPo);

        return resultPo;
    }

    public DynamicEntity findDynamicEntityByDynamicId(int dynamicId) {
        return dynamicRepository.findOne(dynamicId);
    }

    public Page<DynamicEntity> findDynamicEntitiesByUserId(int userId, Pageable pageable) {
        return dynamicRepository.findByUserId(userId, pageable);
    }

    public List<DynamicPraiseEntity> findDynamicPraiseEntityByDynamicId(int dynamicId) {
        return dynamicPraiseRepository.findByDynamicId(dynamicId);
    }

    public List<DynamicCommentEntity> findDynamicCommentEntityByDynamicId(int dynamicId) {
        return dynamicCommentRepository.findByDynamicId(dynamicId);
    }
}
