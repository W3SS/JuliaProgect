package org.julia.dao.hibernate;

import org.hibernate.criterion.Restrictions;
import org.julia.dao.GoodsDao;
import org.julia.domain.Goods;
import org.julia.domain.Purchase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Миша
 * Date: 05.02.15
 */
@Repository(value = "goodsDao")
public class GoodsDaoHibernateImpl extends AbstractGenericDaoHibernate<Goods> implements GoodsDao {

    @Override
    public List<Purchase> getPurchaseListFor(long id) {
        return sessionFactory.getCurrentSession()
        .createQuery("from Purchase p join fetch p.goods join fetch p.shop where p.goods.id = :id")
        .setLong("id", id).list();
    }

    @Override
    public long getPurchaseCountFor(long id) {
        return (Long)sessionFactory.getCurrentSession()
                .createQuery("select COUNT(p) from Purchase p where p.goods.id=:id").setLong("id",id).uniqueResult();
    }

    @Override
    public Goods findByName(String name) {

        return (Goods) sessionFactory.getCurrentSession().createCriteria(Goods.class).
                add(Restrictions.eq("name", name)).uniqueResult();

    }
}
