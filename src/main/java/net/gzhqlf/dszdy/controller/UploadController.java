package net.gzhqlf.dszdy.controller;

import net.gzhqlf.dszdy.entity.UserEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.service.UploadService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by DESTINY on 2017/10/23.
 */

@RestController
@RequestMapping(value = "/api/upload", method = RequestMethod.POST)
public class UploadController {

    @Resource
    private UploadService uploadService;

    @RequestMapping(value = "/avatar")
    public ResultPo uploadAvatar(@RequestParam(value="userfile")MultipartFile userfile,
                                 @RequestParam(value="picIndex")String picIndex,
                                 HttpSession httpSession) throws ControllerException {

        if (userfile.isEmpty())
            throw new ControllerException("头像不能为空");

        if (picIndex == null || "".equals(picIndex)) {
            throw new ControllerException("上传图片索引为空");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        return uploadService.uploadPic(userfile, userEntity, picIndex);
    }

    @RequestMapping(value = "/image")
    public ResultPo uploadImage(@RequestParam(value="file")MultipartFile file,
                                 HttpSession httpSession) throws ControllerException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        return uploadService.uploadPic(file, userEntity, "5");
    }

    @RequestMapping(value = "/prove")
    public ResultPo uploadUserProveImage(@RequestParam(value="userfile")MultipartFile userfile,
                                         @RequestParam(value="picIndex")String picIndex,
                                         HttpSession httpSession) throws ControllerException {

        if (userfile.isEmpty())
            throw new ControllerException("认证图片不能为空");

        if (picIndex == null || "".equals(picIndex)) {
            throw new ControllerException("上传图片索引为空");
        }

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("User");

        return uploadService.uploadUserProvePic(userfile, userEntity, picIndex);
    }
}