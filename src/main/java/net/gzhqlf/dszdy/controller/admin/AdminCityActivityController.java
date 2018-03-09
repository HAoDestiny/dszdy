package net.gzhqlf.dszdy.controller.admin;

import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.service.admin.AdminCityActivityService;
import net.gzhqlf.dszdy.vo.AdminUpdateActivityVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Destiny_hao on 2017/11/7.
 */

@RestController
@RequestMapping(value = "/api/admin/activity", method = RequestMethod.POST)
public class AdminCityActivityController {

    @Resource
    private AdminCityActivityService adminCityActivityService;

    @RequestMapping(value = "/upload")
    public ResultPo uploadImage(@RequestParam(value="file")MultipartFile file,
                                HttpServletRequest httpServletRequest) throws ControllerException {

        String activityTitle = httpServletRequest.getParameter("activityTitle");
        String activityTag = httpServletRequest.getParameter("activityTag");
        String activityUrl = httpServletRequest.getParameter("activityUrl");

        if ("".equals(activityTitle) || activityTitle == null)
            throw new ControllerException("活动标题不能为空");

        if ("".equals(activityTag) || activityTag == null)
            throw new ControllerException("活动简介不能为空");

        if ("".equals(activityUrl) || activityUrl == null)
            throw new ControllerException("活动链接不能为空");


        return adminCityActivityService.uploadActivityPic(file, activityTitle, activityTag, activityUrl);
    }

    @RequestMapping(value = "/getCityActivityInfo")
    public ResultPo getCityActivityInfo(@RequestBody AdminUpdateActivityVo adminUpdateActivityVo) throws ControllerException {

        return adminCityActivityService.getCityActivityInfo(adminUpdateActivityVo);
    }

    @RequestMapping(value = "/updateCityActivity")
    public ResultPo updateCityActivity(@RequestBody AdminUpdateActivityVo adminUpdateActivityVo) throws ControllerException {

        return adminCityActivityService.updateCityActivity(adminUpdateActivityVo);
    }

    @RequestMapping(value = "/updateActivityPic")
    public ResultPo updateActivityPic(@RequestParam(value="activity_pic")MultipartFile activity_pic,
                                HttpServletRequest httpServletRequest) throws ControllerException {

        String activityTitle = httpServletRequest.getParameter("activityId");

        if ("".equals(activityTitle) || activityTitle == null)
            throw new ControllerException("活动Id不能为空");

        return adminCityActivityService.updateActivityPic(activity_pic, Integer.valueOf(activityTitle));
    }

    @RequestMapping(value = "/endCityActivity")
    public ResultPo CityActivity(@RequestBody AdminUpdateActivityVo adminUpdateActivityVo) throws ControllerException {

        return adminCityActivityService.endCityActivity(adminUpdateActivityVo);

    }
}
