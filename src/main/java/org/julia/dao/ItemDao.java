package org.julia.dao;

import org.julia.domain.Goods;
import org.julia.domain.Purchase;
import org.julia.transfer.PurchaseDTO;

import java.util.List;

/**
 * User: Миша
 * Date: 10.01.15
 */
public interface ItemDao<T> extends GenericDao<T> {
    public List<Purchase> getPurchaseListFor(long id);
    public long getPurchaseCountFor(long id);
    public  T findByName(String name);
}
