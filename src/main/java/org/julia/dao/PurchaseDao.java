package org.julia.dao;

import org.julia.domain.Purchase;
import org.julia.transfer.PurchaseDTO;

import java.util.List;

/**
 * User: Миша
 * Date: 11.01.15
 */
public interface PurchaseDao extends GenericDao<Purchase> {
    public List<Purchase> findAllWithFetch();
    public Purchase findWithFetch(long id);
    public Purchase findByGoodsNameAndShopName(String goodsName, String shopName);
}
