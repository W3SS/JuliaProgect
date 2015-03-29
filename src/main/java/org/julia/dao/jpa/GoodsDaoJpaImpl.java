package org.julia.dao.jpa;

import org.julia.dao.GoodsDao;
import org.julia.dao.ItemDao;
import org.julia.domain.Goods;
import org.julia.domain.Purchase;
import org.julia.transfer.PurchaseDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


/**
 * User: Миша
 * Date: 10.01.15
 */

@Repository(value = "goodsDao")
public class GoodsDaoJpaImpl extends AbstractGenericDaoJPA<Goods> implements GoodsDao {

    @Override
    public List<Purchase> getPurchaseListFor(long id) {
        return entityManager.createQuery("select p from Purchase p join fetch p.goods join fetch p.shop where p.goods.id = :id", Purchase.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public long getPurchaseCountFor(long id) {
        return entityManager.createQuery("select COUNT(p) from Purchase p where p.goods.id=:id", Long.class).
                setParameter("id", id).getSingleResult();
    }

    @Override
    public Goods findByName(String name) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Goods> query = cb.createQuery(Goods.class);
            Root<Goods> root = query.from(Goods.class);
            query.where(cb.equal(root.get("name"), cb.parameter(String.class, "name")));
            return entityManager.createQuery(query).setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
