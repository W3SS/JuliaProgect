package org.julia.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.sql.Date;

/**
 * User: Миша
 * Date: 18.09.14
 */

@Entity
@Table(name = "Purchase")
@NamedQuery(name = "Purchase.findAll", query = "select p from Purchase p")
public class Purchase implements BusinessEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "price")
    private double price;

//    @Temporal(TemporalType.DATE)
    @Column(name = "time_stamp")
    private Date timestamp;

    @Version
    @Column(name = "purchase_version")
    private int version;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name="shop_id")
    private Shop shop;
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name="goods_id")
    private Goods goods;

    @Override
    @JsonGetter("purchaseId")
    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public Shop getShop() {
        return shop;
    }

    @JsonIgnore
    public Goods getGoods() {
        return goods;
    }

    @JsonGetter("goodsName")
    public String goodsName(){
        return goods.getName();
    }

    @JsonGetter("shopName")
    public String shopName(){
        return shop.getName();
    }

    public int getVersion() {
        return version;
    }

    @JsonGetter("goodsId")
    public long goodsId(){
        return goods.getId();
    }

    @JsonGetter("shopId")
    public long shopId(){
        return shop.getId();
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Purchase{ id=" + id +", price=" + price+"}";
    }
}
