package net.gzhqlf.dszdy.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Destiny_hao on 2017/11/19.
 */
public class AdminGoodsOperationVo {

    @NotNull(message = "操作类型")
    private int type;

    @NotNull(message = "商品Id")
    private int goodsId;

    @NotNull(message = "商品名称")
    @NotEmpty(message = "商品名称")
    private String goodsName;

    @NotNull(message = "商品简介")
    @NotEmpty(message = "商品简介")
    private String goodsDesc;

    @NotNull(message = "商品单价")
    @NotEmpty(message = "商品单价")
    private double goodsFee;

    @NotNull(message = "商品类型")
    @NotEmpty(message = "商品类型")
    private Integer goodsType;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public double getGoodsFee() {
        return goodsFee;
    }

    public void setGoodsFee(double goodsFee) {
        this.goodsFee = goodsFee;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }
}
