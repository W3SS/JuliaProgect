package org.julia.dao.hibernate;

import org.julia.dao.ShopDao;
import org.julia.domain.Purchase;
import org.julia.domain.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Миша
 * Date: 05.02.15
 */
@Repository("shopDao")
public class ShopDaoHibernateImpl  extends AbstractGenericDaoHibernate<Shop> implements ShopDao {
    @Override
    public List<Purchase> getPurchaseListFor(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Purchase p join fetch p.goods join fetch p.shop where p.shop.id = :id")
                .setLong("id", id).list();
    }

    @Override
    public long getPurchaseCountFor(long id) {
        return (Long)sessionFactory.getCurrentSession()
                .createQuery("select COUNT(p) from Purchase p where p.shop.id=:id").setLong("id",id).uniqueResult();
    }

    @Override
    public Shop findByName(String name) {
        return (Shop) sessionFactory.getCurrentSession().
                createQuery("from Shop s where s.name = :name").setString("name", name).uniqueResult();
    }
}
