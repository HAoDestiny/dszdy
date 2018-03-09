package net.gzhqlf.dszdy.service;

import net.gzhqlf.dszdy.entity.FileEntity;
import net.gzhqlf.dszdy.entity.UserEntity;
import net.gzhqlf.dszdy.entity.UserInfoEntity;
import net.gzhqlf.dszdy.entity.UserProveEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.FilePo;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.po.WeChatMessagePo;
import net.gzhqlf.dszdy.repository.FileRepository;
import net.gzhqlf.dszdy.repository.UserInfoRepository;
import net.gzhqlf.dszdy.repository.UserProveRepository;
import net.gzhqlf.dszdy.util.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by DESTINY on 2017/10/23.
 */

@Service
public class UploadService {

    @Value("${dszdy.project.imagesPath}")
    private String ROOT;

    @Value("${dszdy.project.imagesProtocol}")
    private String IMAGE_PROTOCOL;

    @Resource
    private WeChatService weChatService;

    @Resource
    private FileRepository fileRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private UserProveRepository userProveRepository;

    public ResultPo uploadPic(MultipartFile file, UserEntity userEntity, String picIndex) throws ControllerException {

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

            if (fileEntity.getId() == 0) {
                throw new ControllerException("保存图片路径失败");
            }

            if (picIndex.equals("5")) {
                resultPo.setStatus("SUCCESS");
                resultPo.setData(fileEntity);
                return resultPo;
            }

            UserInfoEntity userInfoEntity = userInfoRepository.findOne(userEntity.getUserInfoId());

            if (picIndex.equals("1")) {
                userInfoEntity.setAvatarNormal(fileEntity.getId());
            }

            else if (picIndex.equals("2")) {
                userInfoEntity.setAvatarProB(fileEntity.getId());
            }

            else if (picIndex.equals("3")) {
                userInfoEntity.setAvatarProC(fileEntity.getId());
            }

            else if (picIndex.equals("4")) {
                userInfoEntity.setAvatarProD(fileEntity.getId());
            }

            else {
                throw new ControllerException("上传图片索引不匹配");
            }

            userInfoRepository.save(userInfoEntity);

            if (picIndex.equals("1")) {
                if (userInfoEntity.getAvatarNormal() == 0) {
                    throw new ControllerException("保存用户图片失败");
                }
            }

            else if (picIndex.equals("2")) {
                if (userInfoEntity.getAvatarProB() == 0) {
                    throw new ControllerException("保存用户图片失败");
                }
            }

            else if (picIndex.equals("3")) {
                if (userInfoEntity.getAvatarProC() == 0) {
                    throw new ControllerException("保存用户图片失败");
                }
            }

            else if (picIndex.equals("4")) {
                if (userInfoEntity.getAvatarProD() == 0) {
                    throw new ControllerException("保存用户图片失败");
                }
            }

            FilePo filePo = new FilePo();
            filePo.setFileHost(IMAGE_PROTOCOL);
            filePo.setFileName(randomName);
            filePo.setFileType(fileType);

            resultPo.setStatus("SUCCESS");
            resultPo.setData(filePo);

            return resultPo;

        } catch (IOException | RuntimeException e) {
            throw new ControllerException("保存图片数据失败");

        }

    }

    public ResultPo uploadUserProvePic(MultipartFile file, UserEntity userEntity, String picIndex) throws ControllerException {

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

            if (fileEntity.getId() == 0) {
                throw new ControllerException("保存图片路径失败");
            }

            UserProveEntity userProveEntity = userProveRepository.findOne(userEntity.getUserProveId());

            if (picIndex.equals("1")) {
                userProveEntity.setIdentityPro(fileEntity.getId());
                userProveEntity.setIdentityStatus(2); // 审核中
            }

            else if (picIndex.equals("2")) {
                userProveEntity.setEducationPro(fileEntity.getId());
                userProveEntity.setEducationStatus(2);
            }

            else if (picIndex.equals("3")) {
                userProveEntity.setCarPro(fileEntity.getId());
                userProveEntity.setCarStatus(2);
            }

            else if (picIndex.equals("4")) {
                userProveEntity.setHousePro(fileEntity.getId());
                userProveEntity.setHouseStatus(2);
            }

            else {
                throw new ControllerException("上传图片索引不匹配");
            }

            userProveRepository.saveAndFlush(userProveEntity);

            if (picIndex.equals("1")) {
                if (userProveEntity.getIdentityPro() == 0) {
                    throw new ControllerException("保存用户图片失败");
                }
            }

            else if (picIndex.equals("2")) {
                if (userProveEntity.getEducationPro() == 0) {
                    throw new ControllerException("保存用户图片失败");
                }
            }

            else if (picIndex.equals("3")) {
                if (userProveEntity.getCarPro() == 0) {
                    throw new ControllerException("保存用户图片失败");
                }
            }

            else if (picIndex.equals("4")) {
                if (userProveEntity.getHousePro() == 0) {
                    throw new ControllerException("保存用户图片失败");
                }
            }

            FilePo filePo = new FilePo();
            filePo.setFileHost(IMAGE_PROTOCOL);
            filePo.setFileName(randomName);
            filePo.setFileType(fileType);

            resultPo.setStatus("SUCCESS");
            resultPo.setData(filePo);

            WeChatMessagePo weChatMessagePo = new WeChatMessagePo();
            weChatMessagePo.setDescription("已有用户提交审核，请赶快处理");
            weChatMessagePo.setPicurl("http:" + filePo.getFileHost() + "/" + filePo.getFileName() + filePo.getFileType());
            weChatMessagePo.setTitle("有新的审核任务，请赶快处理");
            weChatMessagePo.setUrl("http://" + weChatService.getDOMAIN() + "/admin/index.html");

            weChatService.sendWeChatMessage(0, weChatMessagePo);

            return resultPo;
        } catch (IOException e) {
            throw new ControllerException("保存图片数据失败");
        }
    }
}
