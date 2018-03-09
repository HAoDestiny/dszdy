package net.gzhqlf.dszdy.service;

import net.gzhqlf.dszdy.entity.ConfigurationEntity;
import net.gzhqlf.dszdy.repository.ConfigurationRepository;
import net.gzhqlf.dszdy.po.ResultPo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DESTINY on 2017/10/22.
 */

@Service
public class ConfigurationService {

    @Resource
    private ConfigurationRepository configurationRepository;

    public ResultPo getConstellation() {

        ResultPo resultPo = new ResultPo();

        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("type", 1);
        concurrentHashMap.put("value", configurationRepository.findByType(1));

        resultPo.setStatus("SUCCESS");
        resultPo.setData(concurrentHashMap);

        return resultPo;
    }

    public ResultPo getProfession() {

        ResultPo resultPo = new ResultPo();

        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("type", 2);
        concurrentHashMap.put("value", configurationRepository.findByType(2));

        resultPo.setStatus("SUCCESS");
        resultPo.setData(concurrentHashMap);

        return resultPo;
    }

    public ResultPo getIncome() {

        ResultPo resultPo = new ResultPo();

        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("type", 3);
        concurrentHashMap.put("value", configurationRepository.findByType(3));

        resultPo.setStatus("SUCCESS");
        resultPo.setData(concurrentHashMap);

        return resultPo;
    }

    public ResultPo getMaritalStatusList() {

        ResultPo resultPo = new ResultPo();

        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("type", 4);
        concurrentHashMap.put("value", configurationRepository.findByType(4));

        resultPo.setStatus("SUCCESS");
        resultPo.setData(concurrentHashMap);

        return resultPo;
    }

    public ResultPo getEducationList() {

        ResultPo resultPo = new ResultPo();

        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("value", configurationRepository.findByType(5));

        resultPo.setStatus("SUCCESS");
        resultPo.setData(concurrentHashMap);

        return resultPo;
    }

    public String getEducationByCode(int code, int type) {

        ConfigurationEntity configurationEntity = configurationRepository.findByCodeAndType(code, type);

        if (null == configurationEntity) {
            return null;
        }

        return configurationEntity.getValue();

    }

    public String getConfigurationByCodeAndType(int code, int type) {

        ConfigurationEntity configurationEntity = configurationRepository.findByCodeAndType(code, type);

        if (null == configurationEntity) {
            return null;
        }

        return configurationEntity.getValue();

    }
}
