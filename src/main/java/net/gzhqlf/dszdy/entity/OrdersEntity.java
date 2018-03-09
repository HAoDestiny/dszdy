package net.gzhqlf.dszdy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Destiny_hao on 2017/11/19.
 */
@Entity
@Table(name = "orders", schema = "dszdy", catalog = "")
public class OrdersEntity {

    @JsonIgnore
    private int id;

    private Integer goodsId;
    private Integer goodsTotal;
    private String orderName;
    private String orderFee;
    private Integer orderUserId;
    private Integer orderPayStatus;
    private String orderId;
    private Integer createTime;

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
    @Column(name = "goods_id", nullable = true)
    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    @Basic
    @Column(name = "goods_total", nullable = true)
    public Integer getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(Integer goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    @Basic
    @Column(name = "order_name", nullable = true, length = 255)
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Basic
    @Column(name = "order_fee", nullable = true, length = 255)
    public String getOrderFee() {
        return orderFee;
    }

    public void setOrderFee(String orderFee) {
        this.orderFee = orderFee;
    }

    @Basic
    @Column(name = "order_user_id", nullable = true)
    public Integer getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(Integer orderUserId) {
        this.orderUserId = orderUserId;
    }

    @Basic
    @Column(name = "order_pay_status", nullable = true)
    public Integer getOrderPayStatus() {
        return orderPayStatus;
    }

    public void setOrderPayStatus(Integer orderPayStatus) {
        this.orderPayStatus = orderPayStatus;
    }

    @Basic
    @Column(name = "order_id", nullable = true, length = 255)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersEntity that = (OrdersEntity) o;

        if (id != that.id) return false;
        if (goodsId != null ? !goodsId.equals(that.goodsId) : that.goodsId != null) return false;
        if (goodsTotal != null ? !goodsTotal.equals(that.goodsTotal) : that.goodsTotal != null) return false;
        if (orderName != null ? !orderName.equals(that.orderName) : that.orderName != null) return false;
        if (orderFee != null ? !orderFee.equals(that.orderFee) : that.orderFee != null) return false;
        if (orderUserId != null ? !orderUserId.equals(that.orderUserId) : that.orderUserId != null) return false;
        if (orderPayStatus != null ? !orderPayStatus.equals(that.orderPayStatus) : that.orderPayStatus != null)
            return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (goodsTotal != null ? goodsTotal.hashCode() : 0);
        result = 31 * result + (orderName != null ? orderName.hashCode() : 0);
        result = 31 * result + (orderFee != null ? orderFee.hashCode() : 0);
        result = 31 * result + (orderUserId != null ? orderUserId.hashCode() : 0);
        result = 31 * result + (orderPayStatus != null ? orderPayStatus.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
