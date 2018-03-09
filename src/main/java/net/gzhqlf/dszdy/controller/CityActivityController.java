package net.gzhqlf.dszdy.controller;

import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.service.CityActivityService;
import net.gzhqlf.dszdy.vo.CityActivityListVo;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Destiny_hao on 2017/11/8.
 */

@RestController
@RequestMapping(value = {"/api/activity", "/api/admin"}, method = RequestMethod.POST)
public class CityActivityController {

    @Resource
    private CityActivityService cityActivityService;

    //活动列表
    @RequestMapping(value = "/getCityActivityList")
    public ResultPo getCityActivityList(@RequestBody @Valid CityActivityListVo cityActivityListVo,
                                           BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }


        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(cityActivityService.getCityActivityList(cityActivityListVo.getPageCode(), cityActivityListVo.getPageSize()));

        return resultPo;
    }

}
