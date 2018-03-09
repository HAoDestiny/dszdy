package net.gzhqlf.dszdy.service;

import net.gzhqlf.dszdy.entity.CityActivityEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.CityActivityListPo;
import net.gzhqlf.dszdy.po.CityActivityPo;
import net.gzhqlf.dszdy.po.FilePo;
import net.gzhqlf.dszdy.repository.CityActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Destiny_hao on 2017/11/8.
 */

@Service
public class CityActivityService {

    @Resource
    private CityActivityRepository cityActivityRepository;

    @Resource
    private FileService fileService;

    public CityActivityListPo getCityActivityList(int pageCode, int pageSize) throws ControllerException {

        if (pageCode < 0 || pageSize < 0) {
            throw new ControllerException("页面加载失败");
        }

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);

        Page<CityActivityEntity> page = cityActivityRepository.findByActivityStatusAndDeleteTag(0, 0, pageable);

        List<CityActivityEntity> cityActivityEntityList = page.getContent();

        CityActivityListPo cityActivityListPo = new CityActivityListPo();
        List<CityActivityPo> cityActivityPos = new ArrayList<>();
        if (cityActivityEntityList.size() > 0) {

            for (CityActivityEntity cityActivityEntity : cityActivityEntityList) {
                CityActivityPo cityActivityPo = new CityActivityPo();

                FilePo filePo = fileService.getFileByFileId(cityActivityEntity.getActivityPic());

                cityActivityPo.setActivityId(cityActivityEntity.getId());
                cityActivityPo.setActivityTitle(cityActivityEntity.getActivityTitle());
                cityActivityPo.setActivityTag(cityActivityEntity.getActivityTag());
                cityActivityPo.setActivityUrl(cityActivityEntity.getActivityUrl());
                cityActivityPo.setCreateTime(cityActivityEntity.getCreateTime());
                cityActivityPo.setActivityStatus(cityActivityEntity.getActivityStatus());
                cityActivityPo.setActivityPic(filePo);

                cityActivityPos.add(cityActivityPo);
            }
        }

        cityActivityListPo.setData(cityActivityPos);
        cityActivityListPo.setPage(pageCode);
        cityActivityListPo.setPageSize(pageSize);
        cityActivityListPo.setPageTotal(page.getTotalPages());

        return cityActivityListPo;
    }

}
