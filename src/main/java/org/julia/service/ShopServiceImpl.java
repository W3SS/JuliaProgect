package org.julia.service;

import org.julia.dao.ShopDao;
import org.julia.domain.Purchase;
import org.julia.domain.Shop;
import org.julia.validation.JuliaValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Миша
 * Date: 17.11.14
 */
@Service("shopService")
@Transactional
public class ShopServiceImpl {
    private final static Logger log = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private JuliaValidationService validationService;


    @Transactional(readOnly = true)
    public List<Shop> getAllShops() {
        return shopDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Purchase> getPurchasesForShop(long id) {
        return shopDao.getPurchaseListFor(id);
    }

    @Transactional(readOnly = true)
    public Long getPurchasesCountForShop(long id) {
        return shopDao.getPurchaseCountFor(id);
    }

    public void deleteShop(long id) {
        log.debug("delete shop: {}", id);
        Shop shop = shopDao.findById(id, false);
        shopDao.makeTransient(shop);
    }

    public Shop updateShop(Shop shop) {
        validationService.validateItem(shop);
        validationService.validateUniqueShopName(shop);
        return shopDao.makePersistent(shop);
    }

    @Transactional(readOnly = true)
    public Shop getShop(long id) {
        return shopDao.findById(id, false);
    }
}
