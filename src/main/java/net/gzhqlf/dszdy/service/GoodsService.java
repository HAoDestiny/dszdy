package net.gzhqlf.dszdy.service;

import net.gzhqlf.dszdy.entity.GoodsEntity;
import net.gzhqlf.dszdy.entity.OrdersEntity;
import net.gzhqlf.dszdy.entity.UserInfoEntity;
import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.AdminOrderListPo;
import net.gzhqlf.dszdy.po.AdminOrderPo;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.repository.GoodsRepository;
import net.gzhqlf.dszdy.repository.OrderRepository;
import net.gzhqlf.dszdy.vo.AdminGoodsVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Destiny_hao on 2017/11/19.
 */

@Service
public class GoodsService {

    @Resource
    private UserService userService;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private OrderRepository orderRepository;

    public List<GoodsEntity> getGoodsList() {
        return goodsRepository.findByDeleteTag(0);
    }

    public List<GoodsEntity> getGoodsListByGoodsType(int goodsType) {
        return goodsRepository.findByGoodsTypeAndDeleteTag(goodsType,0);
    }

    public GoodsEntity getGoodsEntityByGoodsId(int goodsId) {
        return goodsRepository.findOne(goodsId);
    }

    public void addGoods(AdminGoodsVo adminGoodsVo) throws ControllerException {

        if (adminGoodsVo.getGoodsNum() <= 0) {
            throw new ControllerException("商品数量错误");
        }

        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsName(adminGoodsVo.getGoodsName());
        goodsEntity.setGoodsDesc(adminGoodsVo.getGoodsDesc());
        goodsEntity.setGoodsFee(adminGoodsVo.getGoodsFee());
        goodsEntity.setGoodsType(adminGoodsVo.getGoodsType());
        goodsEntity.setGoodsNum(adminGoodsVo.getGoodsNum());
        goodsEntity.setGoodsDays(adminGoodsVo.getGoodsDays());
        goodsEntity.setCreateTime((int)(System.currentTimeMillis()/1000));
        goodsEntity.setDeleteTag(0);

        goodsRepository.saveAndFlush(goodsEntity);

        if (goodsEntity.getId() == 0) {
            throw new ControllerException("商品添加失败");
        }

    }

    public AdminOrderListPo getUserList(int type, String value, int pageCode, int pageSize) throws ControllerException {

        if (pageCode < 0 || pageSize < 0) {
            throw new ControllerException("页面加载失败");
        }

        if ("".equals(value)) {
            throw new ControllerException("页面加载失败");
        }

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(pageCode, pageSize, sort);
        Page<OrdersEntity> pageList;

        if (type == 0) {
            pageList = orderRepository.findAll(pageable);
        }

        //订单号查询
        else if(type == 1) {
            pageList = orderRepository.findByOrderId(value, pageable);
        }

        //用户名查询
        else if(type == 2) {
            List<Integer> userIdList = userService.getUserIdListByNickname(value);
            pageList = orderRepository.findByOrderUserIdIn(userIdList, pageable);
        }

        else {
            throw new ControllerException("列表初始化失败");
        }

        List<OrdersEntity> ordersEntityList = pageList.getContent();

        AdminOrderListPo adminOrderListPo = new AdminOrderListPo();
        List<AdminOrderPo> adminOrderListPoList = new ArrayList<>();
        if (ordersEntityList.size() > 0) {
            for (OrdersEntity ordersEntity : ordersEntityList) {
                AdminOrderPo adminOrderPo = new AdminOrderPo();
                UserInfoEntity userInfoEntity = userService.getUserInfoEntityByUserId(ordersEntity.getOrderUserId());

                adminOrderPo.setOrder(ordersEntity);
                adminOrderPo.setUserName(userInfoEntity.getNickname());

                adminOrderListPoList.add(adminOrderPo);
            }
        }

        adminOrderListPo.setOrders(adminOrderListPoList);
        adminOrderListPo.setPage(pageCode);
        adminOrderListPo.setPageSize(pageSize);
        adminOrderListPo.setPageTotal(pageList.getTotalPages());

        return adminOrderListPo;
    }

    public OrdersEntity createOrder(OrdersEntity ordersEntity) {
        return orderRepository.saveAndFlush(ordersEntity);
    }

    public OrdersEntity saveOrder(OrdersEntity ordersEntity) {
        return orderRepository.saveAndFlush(ordersEntity);
    }

    public OrdersEntity getOrdersEntityByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    public ResultPo goodsOperation(AdminGoodsVo adminGoodsVo) throws ControllerException {

        if (adminGoodsVo.getGoodsId() <= 0) {
            throw new ControllerException("商品不存在");
        }

        GoodsEntity goodsEntity = goodsRepository.findOne(adminGoodsVo.getGoodsId());

        if (goodsEntity == null) {
            throw new ControllerException("商品不存在");
        }

        ResultPo resultPo = new ResultPo();
        switch (adminGoodsVo.getType()) {
            //修改
            case 1: {

                if (adminGoodsVo.getGoodsNum() <= 0) {
                    throw new ControllerException("商品数量错误");
                }

                goodsEntity.setGoodsName(adminGoodsVo.getGoodsName());
                goodsEntity.setGoodsDesc(adminGoodsVo.getGoodsDesc());
                goodsEntity.setGoodsFee(adminGoodsVo.getGoodsFee());
                goodsEntity.setGoodsType(adminGoodsVo.getGoodsType());
                goodsEntity.setGoodsNum(adminGoodsVo.getGoodsNum());
                goodsEntity.setGoodsDays(adminGoodsVo.getGoodsDays());
                goodsEntity.setCreateTime((int) (System.currentTimeMillis() / 1000));

                break;
            }
            //删除
            case 2: {
                goodsEntity.setDeleteTag(1);

                break;
            }
            default: {
                throw new ControllerException("操作类型错误");
            }

        }

        goodsRepository.saveAndFlush(goodsEntity);


        if (goodsEntity.getId() == 0) {
            throw new ControllerException("操作失败");
        }

        resultPo.setStatus("SUCCESS");
        resultPo.setMessage("操作成功");

        return resultPo;
    }

    public GoodsEntity getGoodsInfo(int goodsId) throws ControllerException {

        if (goodsId <= 0) {
            throw new ControllerException("商品不存在");
        }

        return goodsRepository.findOne(goodsId);
    }
}

