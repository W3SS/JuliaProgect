package org.julia.transfer;

import org.julia.domain.Purchase;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

/**
 * User: Миша
 * Date: 23.09.14
 */
public class PurchaseDTO {
    private long purchaseId;
    private long goodsId;
    private long shopId;
    private int version;
    private String price;
    private String shopName;
    private String goodsName;
    private Date timestamp;
    public PurchaseDTO(){}



    public PurchaseDTO(Purchase purchase){
       this.purchaseId = purchase.getId();
       this.goodsId = purchase.goodsId();
       this.shopId = purchase.shopId();
       this.version = purchase.getVersion();
       this.price = String.valueOf(purchase.getPrice());
       this.shopName = purchase.shopName();
       this.goodsName = purchase.goodsName();
        this.timestamp = purchase.getTimestamp();
    }

    public PurchaseDTO(long purchaseId, long goodsId, long shopId, double price, String shopName, String goodsName) {
        this.purchaseId = purchaseId;
        this.goodsId = goodsId;
        this.shopId = shopId;
        this.price = String.valueOf(price);
        this.shopName = shopName;
        this.goodsName = goodsName;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public long getShopId() {
        return shopId;
    }

    public int getVersion() {
        return version;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @NotNull(message = "purchase.price.empty.warning")
    @Pattern(regexp = "^\\d+(\\.\\d+)?", message = "purchase.price.format.warning")
    public String getPrice() {
        return price;
    }

    @NotNull(message = "shop.name.empty")
    public String getShopName() {
        return shopName;
    }
    @NotNull(message = "goods.name.empty")
    public String getGoodsName() {
        return goodsName;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
