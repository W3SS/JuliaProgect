package org.julia.service;

import org.julia.dao.GoodsDao;
import org.julia.dao.PurchaseDao;
import org.julia.dao.ShopDao;
import org.julia.validation.JuliaValidationService;
import org.slf4j.LoggerFactory;
import org.julia.domain.Goods;
import org.julia.domain.Purchase;
import org.julia.domain.Shop;
import org.julia.transfer.PurchaseDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.util.List;

/**
 * User: Миша
 * Date: 19.09.14
 */

@Service("purchaseService")
@Transactional
public class PurchaseServiceImpl /*implements PurchaseService */ {
    private final static Logger log = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    @Autowired
    PurchaseDao purchaseDao;

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    ShopDao shopDao;

    @Autowired
    private JuliaValidationService validationService;

    /**
     * Saves new purchase
     */
    public Purchase saveNewPurchase(PurchaseDTO purchaseDTO) {
        // validate input data
        validationService.validateItem(purchaseDTO);
        double price = Double.parseDouble(purchaseDTO.getPrice());
        String goodsName = purchaseDTO.getGoodsName();
        String shopName = purchaseDTO.getShopName();

        // validate purchase unique
        long purchaseId = purchaseDTO.getPurchaseId();
        validationService.validateUniquePurchase(purchaseId, goodsName, shopName);

        // creating new purchase
        long shopId = purchaseDTO.getShopId();
        Shop shop = shopId == 0 ? shopDao.findById(shopId, true) : shopDao.findByName(shopName);
        if (shop ==null){
            shop = new Shop();
            shop.setName(shopName);
        }
        long goodsId = purchaseDTO.getGoodsId();
        Goods goods = goodsId == 0 ? goodsDao.findById(goodsId, true) : goodsDao.findByName(goodsName);
        if(goods == null){
            goods = new Goods();
            goods.setName(goodsName);
        }

        Purchase purchase = new Purchase();
        purchase.setId(purchaseId);
        purchase.setVersion(purchaseDTO.getVersion());
        purchase.setPrice(price);
        purchase.setGoods(goods);
        purchase.setShop(shop);
        purchase.setTimestamp(new Date(System.currentTimeMillis()));
        return purchaseDao.makePersistent(purchase);
    }

    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchases() {
        return purchaseDao.findAllWithFetch();
    }

    public void removePurchase(Long id) {
        Purchase purchase = purchaseDao.findById(id, false);
        purchaseDao.makeTransient(purchase);
    }

    public Purchase getPurchase(long id) {
        return purchaseDao.findWithFetch(id);
    }
}
