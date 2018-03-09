package net.gzhqlf.dszdy.service.admin;

import net.gzhqlf.dszdy.entity.AreaEntity;
import net.gzhqlf.dszdy.entity.CityEntity;
import net.gzhqlf.dszdy.entity.ProvinceEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.repository.AreaRepository;
import net.gzhqlf.dszdy.repository.CityRepository;
import net.gzhqlf.dszdy.repository.ProvinceRepository;
import net.gzhqlf.dszdy.vo.AddressMsgVo;
import net.gzhqlf.dszdy.po.ResultPo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DESTINY on 2017/10/22.
 */

@Service
public class AddressService {
    @Resource
    private ProvinceRepository provinceRepository;

    @Resource
    private CityRepository cityRepository;

    @Resource
    private AreaRepository areaRepository;

    public ResultPo getAddressListBath(AddressMsgVo addressMsgVo) throws ControllerException {

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");

        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

        switch (Integer.valueOf(addressMsgVo.getType())) {
            case 0: {
                concurrentHashMap.put("list", provinceRepository.findAll());
                concurrentHashMap.put("city", cityRepository.findByFather(Integer.valueOf(addressMsgVo.getProvince_id())));
                concurrentHashMap.put("area", areaRepository.findByFather(Integer.valueOf(addressMsgVo.getCity_id())));
                resultPo.setData(concurrentHashMap);
                return resultPo;
            }
            case 1: {
                concurrentHashMap.put("list", cityRepository.findByFather(Integer.valueOf(addressMsgVo.getFather_id())));
                resultPo.setData(concurrentHashMap);
                return resultPo;
            }
            case 2: {
                concurrentHashMap.put("list", areaRepository.findByFather(Integer.valueOf(addressMsgVo.getFather_id())));
                resultPo.setData(concurrentHashMap);
                return resultPo;
            }
            default: {
                throw new ControllerException("找不到地址类型");
            }
        }
    }

    //1查省地址 2查市地址 3查区地址
    public String getAddressByCode(int code, int type) {

        if (type == 1) {

            ProvinceEntity provinceEntity = provinceRepository.findByCode(code);

            if (null == provinceEntity) {
                return null;
            }

            return provinceEntity.getName();
        }
        if (type == 2) {

            CityEntity cityEntity = cityRepository.findByCode(code);

            if (null == cityEntity) {
                return null;
            }

            return cityEntity.getName();
        }
        if (type == 3) {

            AreaEntity areaEntity = areaRepository.findByCode(code);

            if (null == areaEntity) {
                return null;
            }

            return areaEntity.getName();
        }
        return null;
    }

    //1查省地址 2查市地址 3查区地址
    public int getAddressByName(String name, int type) {

        if (type == 1) {

            ProvinceEntity provinceEntity = provinceRepository.findByNameLike("%" + name + "%");

            if (provinceEntity == null) {
                return -110;
            }

            return provinceEntity.getCode();
        }
        if (type == 2) {

            CityEntity cityEntity = cityRepository.findByNameLike("%" + name + "%");

            if (cityEntity == null) {
                return -110;
            }

            return cityEntity.getCode();
        }
        if (type == 3) {

            AreaEntity areaEntity = areaRepository.findByNameLike("%" + name + "%");

            if (areaEntity == null) {
                return -110;
            }

            return areaEntity.getCode();
        }
        return 0;
    }
}
