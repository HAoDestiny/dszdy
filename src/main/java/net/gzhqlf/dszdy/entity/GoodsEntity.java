package net.gzhqlf.dszdy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/11/19.
 */
@Entity
@Table(name = "goods", schema = "dszdy", catalog = "")
public class GoodsEntity {
    private int id;
    private String goodsName;
    private String goodsDesc;
    private double goodsFee;
    private Integer goodsNum;
    private Integer goodsType;
    private Integer goodsDays;
    private Integer createTime;

    @JsonIgnore
    private Integer deleteTag;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "goods_name", nullable = true, length = 255)
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Basic
    @Column(name = "goods_desc", nullable = true, length = 255)
    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    @Basic
    @Column(name = "goods_fee", nullable = true, precision = 0)
    public double getGoodsFee() {
        return goodsFee;
    }

    public void setGoodsFee(double goodsFee) {
        this.goodsFee = goodsFee;
    }

    @Basic
    @Column(name = "goods_num", nullable = true, precision = 0)
    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    @Basic
    @Column(name = "goods_type", nullable = true)
    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    @Basic
    @Column(name = "goods_days", nullable = true)
    public Integer getGoodsDays() {
        return goodsDays;
    }

    public void setGoodsDays(Integer goodsDays) {
        this.goodsDays = goodsDays;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "delete_tag", nullable = true)
    public Integer getDeleteTag() {
        return deleteTag;
    }

    public void setDeleteTag(Integer deleteTag) {
        this.deleteTag = deleteTag;
    }
}
