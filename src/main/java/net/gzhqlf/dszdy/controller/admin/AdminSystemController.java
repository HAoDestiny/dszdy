package net.gzhqlf.dszdy.controller.admin;

import net.gzhqlf.dszdy.entity.AdminNavEntity;
import net.gzhqlf.dszdy.entity.UserEntity;
import net.gzhqlf.dszdy.po.AdminNavPo;
import net.gzhqlf.dszdy.po.AdminSystemInfoPo;
import net.gzhqlf.dszdy.po.SystemTotalPo;
import net.gzhqlf.dszdy.repository.AdminNavRepository;
import net.gzhqlf.dszdy.service.admin.AdminSystemService;
import net.gzhqlf.dszdy.util.OSinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Destiny_hao on 2017/11/22.
 */

@RestController
@RequestMapping(value = "/api/admin/system", method = RequestMethod.GET)
public class AdminSystemController {

    @Autowired
    private AdminSystemInfoPo adminSystemInfoPo;

    @Resource
    private AdminSystemService adminSystemService;

    @Resource
    private AdminNavRepository adminNavRepository;

    @RequestMapping(value = "/getSystemNav")
    public List<AdminNavPo> getSystemNav(HttpSession httpSession) {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("Admin");
        List<AdminNavEntity> adminNavEntities = adminNavRepository.findAll();

        List<AdminNavPo> adminNavPos = new ArrayList<>();
        for(AdminNavEntity adminNavEntity : adminNavEntities) {

            AdminNavPo adminNavPo = new AdminNavPo();

            if(userEntity.getUserType() != 0 && userEntity.getUserType() <= adminNavEntity.getPro()) {
                adminNavPo.setTitle(adminNavEntity.getTitle());
                adminNavPo.setHref(adminNavEntity.getHref());
                adminNavPo.setIcon(adminNavEntity.getIcon());
                adminNavPo.setSpread(adminNavEntity.isSpread());

                adminNavPos.add(adminNavPo);
            }

        }

        return adminNavPos;
    }

    @RequestMapping(value = "/getSystemTotal")
    public SystemTotalPo getSystemTotal() {

        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数

        Timestamp timestamp = new Timestamp(zero);

        return adminSystemService.getSystemTotal((int) (timestamp.getTime() / 1000));
    }

    @RequestMapping(value = "/getSystemInfo")
    public AdminSystemInfoPo getSystemInfo(HttpSession httpSession) {
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("Admin");

        if(userEntity.getUserType() == 1) {
            adminSystemInfoPo.setUserRights("超级管理员");
        }
        if(userEntity.getUserType() == 2) {
            adminSystemInfoPo.setUserRights("活动管理员");
        }

        adminSystemInfoPo.setServer(OSinfo.getOSname().toString());

        return adminSystemInfoPo;
    }
}
