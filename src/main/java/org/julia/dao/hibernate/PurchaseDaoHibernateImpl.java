package org.julia.dao.hibernate;

import org.julia.dao.PurchaseDao;
import org.julia.domain.Purchase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Миша
 * Date: 05.02.15
 */
@Repository("purchaseDao")
public class PurchaseDaoHibernateImpl extends AbstractGenericDaoHibernate<Purchase> implements PurchaseDao {
    @Override
    public List<Purchase> findAllWithFetch() {
        return sessionFactory.getCurrentSession()
                .createQuery("select p from Purchase p join fetch p.goods join fetch p.shop").list();
    }

    @Override
    public Purchase findWithFetch(long id) {
        return (Purchase) sessionFactory.getCurrentSession()
                .createQuery("select p from Purchase p join fetch p.goods join fetch p.shop where p.id = :id")
                .setLong("id", id).uniqueResult();
    }

    @Override
    public Purchase findByGoodsNameAndShopName(String goodsName, String shopName) {
        return (Purchase) sessionFactory.getCurrentSession()
                .createQuery("select p from Purchase p where p.goods.name =:goodsName " +
                "and p.shop.name =:shopName").setString("goodsName", goodsName)
                .setString("shopName", shopName).uniqueResult();
    }
}
