package net.gzhqlf.dszdy.controller;

import net.gzhqlf.dszdy.service.ConfigurationService;
import net.gzhqlf.dszdy.po.ResultPo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by DESTINY on 2017/10/21.
 * 获取星座，专业，年收入，婚姻状况信息
 */

@RestController
@RequestMapping(value = "/api/configuration", method = RequestMethod.POST)
public class ConfigurationController {

    @Resource
    ConfigurationService configurationService;

    @RequestMapping(value = "/getConstellationList")
    public ResultPo getConstellation() {

        return configurationService.getConstellation();
    }

    @RequestMapping(value = "/getProfessionList")
    public ResultPo getProfession() {

        return configurationService.getProfession();
    }

    @RequestMapping(value = "/getIncomeList")
    public ResultPo getIncome() {

        return configurationService.getIncome();
    }

    @RequestMapping(value = "/getMaritalStatusList")
    public ResultPo getMaritalStatusList() {

        return configurationService.getMaritalStatusList();
    }

    @RequestMapping(value = "/getEducationList")
    public ResultPo getEducationList() {

        return configurationService.getEducationList();
    }

}
