package net.gzhqlf.dszdy.controller.admin;

import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.service.GoodsService;
import net.gzhqlf.dszdy.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Destiny_hao on 2017/11/5.
 */

@RestController
@RequestMapping(value = "/api/admin/goods", method = RequestMethod.POST)
public class AdminGoodsController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping(value = "/addGoods")
    public ResultPo addGoods(@RequestBody AdminGoodsVo adminGoodsVo) throws ControllerException {

        goodsService.addGoods(adminGoodsVo);

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("添加成功");

        return resultPo;
    }

    @RequestMapping(value = "/getGoodsList")
    public ResultPo getGoodsList() {

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(goodsService.getGoodsList());

        return resultPo;
    }

    @RequestMapping(value = "/getGoodsListByGoodsType")
    public ResultPo getGoodsListByGoodsType(@RequestBody AdminGoodListVo adminGoodListVo) {

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(goodsService.getGoodsListByGoodsType(adminGoodListVo.getGoodsType()));

        return resultPo;
    }

    @RequestMapping(value = "/goodsOperation")
    public ResultPo goodsOperation(@RequestBody AdminGoodsVo adminGoodsVo) throws ControllerException {

        return goodsService.goodsOperation(adminGoodsVo);
    }

    @RequestMapping(value = "/getGoodsInfo")
    public ResultPo getGoodsInfo(@RequestBody AdminGoodsVo adminGoodsVo) throws ControllerException {

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(goodsService.getGoodsInfo(adminGoodsVo.getGoodsId()));

        return resultPo;
    }

    @RequestMapping(value = "/getOrderList")
    public ResultPo getOrderList(@RequestBody @Valid AdminOrderListVo adminOrderListVo,
                                 BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(goodsService.getUserList(0, "all", adminOrderListVo.getPageCode(), adminOrderListVo.getPageSize()));

        return resultPo;
    }

    @RequestMapping(value = "/searchOrderList")
    public ResultPo searchOrderList(@RequestBody @Valid AdminSearchOrderVo adminSearchOrderVo,
                                 BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        ResultPo resultPo = new ResultPo();
        resultPo.setStatus("SUCCESS");
        resultPo.setData(goodsService.getUserList(
                adminSearchOrderVo.getType(),
                adminSearchOrderVo.getValue(),
                adminSearchOrderVo.getPageCode(),
                adminSearchOrderVo.getPageSize())
        );

        return resultPo;
    }
}
