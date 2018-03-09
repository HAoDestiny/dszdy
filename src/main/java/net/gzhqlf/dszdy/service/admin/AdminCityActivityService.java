package net.gzhqlf.dszdy.service.admin;

import net.gzhqlf.dszdy.entity.CityActivityEntity;
import net.gzhqlf.dszdy.entity.FileEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.repository.CityActivityRepository;
import net.gzhqlf.dszdy.repository.FileRepository;
import net.gzhqlf.dszdy.util.Tool;
import net.gzhqlf.dszdy.vo.AdminUpdateActivityVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Destiny_hao on 2017/11/7.
 */

@Service
public class AdminCityActivityService {

    @Value("${dszdy.project.imagesProtocol}")
    private String IMAGE_PROTOCOL;

    @Value("${dszdy.project.imagesPath}")
    private String ROOT;

    @Resource
    private FileRepository fileRepository;

    @Resource
    private CityActivityRepository cityActivityRepository;

    public ResultPo uploadActivityPic(MultipartFile file, String activityTitle, String activityTag, String activityUrl) throws ControllerException {

        ResultPo resultPo = new ResultPo();

        try {
            String fileType = ".jpg";
            String randomName = Tool.getRandomString(32);
            String fileName = randomName + fileType;
            Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileUri(fileName);
            fileEntity.setCreateTime((int) (System.currentTimeMillis()/1000));
            fileEntity.setDeleteTag(0);
            fileRepository.saveAndFlush(fileEntity);

            CityActivityEntity cityActivityEntity = new CityActivityEntity();
            cityActivityEntity.setActivityTitle(activityTitle);
            cityActivityEntity.setActivityTag(activityTag);
            cityActivityEntity.setActivityUrl(activityUrl);
            cityActivityEntity.setActivityPic(fileEntity.getId());

            cityActivityRepository.saveAndFlush(cityActivityEntity);

            if (fileEntity.getId() == 0) {
                throw new ControllerException("保存图片路径失败");
            }

            if (cityActivityEntity.getId() == 0) {
                throw new ControllerException("活动添加失败");
            }
//            FilePo filePo = new FilePo();
//            filePo.setFileHost(IMAGE_PROTOCOL);
//            filePo.setFileName(randomName);
//            filePo.setFileType(fileType);

            resultPo.setStatus("SUCCESS");
            resultPo.setMessage("活动添加成功");
//            resultPo.setData(filePo);

            return resultPo;
        } catch (IOException e) {
            throw new ControllerException("保存图片数据失败");
        }
    }

    public ResultPo getCityActivityInfo(AdminUpdateActivityVo adminUpdateActivityVo) throws ControllerException {

        if (adminUpdateActivityVo.getActivityId() <= 0) {
            throw new ControllerException("活动不存在");
        }

        CityActivityEntity cityActivityEntity = cityActivityRepository.findOne(adminUpdateActivityVo.getActivityId());

        if (cityActivityEntity == null) {
            throw new ControllerException("活动不存在");
        }

        if (cityActivityEntity.getId() == 0) {
            throw new ControllerException("操作失败");
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(cityActivityEntity);

        return resultPo;
    }

    public ResultPo updateCityActivity(AdminUpdateActivityVo adminUpdateActivityVo) throws ControllerException {

        if (adminUpdateActivityVo.getActivityId() <= 0) {
            throw new ControllerException("活动不存在");
        }

        CityActivityEntity cityActivityEntity = cityActivityRepository.findOne(adminUpdateActivityVo.getActivityId());

        if (cityActivityEntity == null) {
            throw new ControllerException("活动不存在");
        }

        cityActivityEntity.setActivityTitle(adminUpdateActivityVo.getActivityTitle());
        cityActivityEntity.setActivityTag(adminUpdateActivityVo.getActivityTag());
        cityActivityEntity.setActivityUrl(adminUpdateActivityVo.getActivityUrl());

        cityActivityRepository.saveAndFlush(cityActivityEntity);

        if (cityActivityEntity.getId() == 0) {
            throw new ControllerException("操作失败");
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("更新成功");

        return resultPo;
    }

    public ResultPo updateActivityPic(MultipartFile file, int activityId) throws ControllerException {

        ResultPo resultPo = new ResultPo();

        if (activityId <= 0) {
            throw new ControllerException("活动Id错误");
        }

        try {
            String fileType = ".jpg";
            String randomName = Tool.getRandomString(32);
            String fileName = randomName + fileType;
            Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileUri(fileName);
            fileEntity.setCreateTime((int) (System.currentTimeMillis()/1000));
            fileEntity.setDeleteTag(0);
            fileRepository.saveAndFlush(fileEntity);

            CityActivityEntity cityActivityEntity = cityActivityRepository.findOne(activityId);

            if (cityActivityEntity == null) {
                throw new ControllerException("活动不存在");
            }

            cityActivityEntity.setActivityPic(fileEntity.getId());

            cityActivityRepository.saveAndFlush(cityActivityEntity);

            if (fileEntity.getId() == 0) {
                throw new ControllerException("保存图片路径失败");
            }

            if (cityActivityEntity.getId() == 0) {
                throw new ControllerException("活动海报更新失败");
            }

            resultPo.setStatus("SUCCESS");
            resultPo.setMessage("修改成功");

            return resultPo;
        } catch (IOException e) {
            throw new ControllerException("保存图片数据失败");
        }
    }

    public ResultPo endCityActivity(AdminUpdateActivityVo adminUpdateActivityVo) throws ControllerException {

        if (adminUpdateActivityVo.getActivityId() <= 0) {
            throw new ControllerException("活动不存在");
        }

        CityActivityEntity cityActivityEntity = cityActivityRepository.findOne(adminUpdateActivityVo.getActivityId());

        if (cityActivityEntity == null) {
            throw new ControllerException("活动不存在");
        }

        cityActivityEntity.setActivityStatus(1);

        cityActivityRepository.saveAndFlush(cityActivityEntity);

        if (cityActivityEntity.getId() == 0) {
            throw new ControllerException("操作失败");
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("操作成功");

        return resultPo;
    }
}
