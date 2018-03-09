package net.gzhqlf.dszdy.service.admin;

import net.gzhqlf.dszdy.po.SystemTotalPo;
import net.gzhqlf.dszdy.repository.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.TimeZone;

/**
 * Created by Destiny_hao on 2017/11/22.
 */

@Service
public class AdminSystemService {

    @Resource
    private UserProveRepository userProveRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private FileRepository fileRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private CityActivityRepository cityActivityRepository;

    public SystemTotalPo getSystemTotal(int timestamp) {

        SystemTotalPo systemTotalPo = new SystemTotalPo();
        systemTotalPo.setProveCounts(userProveRepository.getProveCounts());
        systemTotalPo.setNewUserCounts(userRepository.getNewUserCounts(timestamp));
        systemTotalPo.setUserCounts(userRepository.getCounts());
        systemTotalPo.setPicCounts(fileRepository.getCounts());
        systemTotalPo.setOrderCounts(orderRepository.getCounts());
        systemTotalPo.setActivityCounts(cityActivityRepository.getCounts());

        return systemTotalPo;
    }

}
