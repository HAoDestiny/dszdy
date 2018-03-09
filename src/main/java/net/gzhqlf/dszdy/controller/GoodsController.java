package net.gzhqlf.dszdy.controller;

import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.service.GoodsService;
import net.gzhqlf.dszdy.vo.OrderIdVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Destiny_hao on 2017/11/19.
 */

@RestController
@RequestMapping(value = "/api/goods", method = RequestMethod.POST)
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping(value = "/getGoodsList")
    public ResultPo getGoodsList() {

        ResultPo resultPo = new ResultPo();

        resultPo.setStatus("SUCCESS");
        resultPo.setData(goodsService.getGoodsList());

        return resultPo;
    }

    @RequestMapping(value = "/getOrderInfo")
    public ResultPo getOrderInfo(@RequestBody OrderIdVo orderIdVo) throws ControllerException {

        ResultPo resultPo = new ResultPo();

        if ("".equals(orderIdVo.getOrderId())) {
            throw new ControllerException("订单id错误");
        }

        resultPo.setStatus("SUCCESS");
        resultPo.setData(goodsService.getOrdersEntityByOrderId(orderIdVo.getOrderId()));

        return resultPo;
    }

}
