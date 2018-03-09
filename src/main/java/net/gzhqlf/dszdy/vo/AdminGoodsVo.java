package net.gzhqlf.dszdy.vo;

/**
 * Created by Destiny_hao on 2017/11/19.
 */
public class AdminGoodsVo {

    private int type; //操作类型
    private int goodsId;
    private String goodsName;
    private String goodsDesc;
    private double goodsFee;
    private int goodsNum;
    private int goodsDays;
    private int goodsType;

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

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getGoodsDays() {
        return goodsDays;
    }

    public void setGoodsDays(int goodsDays) {
        this.goodsDays = goodsDays;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }
}
