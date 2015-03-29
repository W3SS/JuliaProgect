package org.julia.dao.jpa;

import org.julia.dao.ItemDao;
import org.julia.dao.ShopDao;
import org.julia.domain.Purchase;
import org.julia.domain.Shop;
import org.julia.transfer.PurchaseDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * User: Миша
 * Date: 10.01.15
 */
@Repository("shopDao")
public class ShopDaoJpaImpl extends AbstractGenericDaoJPA<Shop> implements ShopDao {
    @Override
    public List<Purchase> getPurchaseListFor(long id) {
        return entityManager.createQuery("select p from Purchase p join fetch p.goods join fetch p.shop where p.shop.id = :id",
                Purchase.class).setParameter("id", id).getResultList();
    }

    @Override
    public long getPurchaseCountFor(long id) {
        return entityManager.createQuery("select COUNT(p) from Purchase p where p.shop.id=:id", Long.class).
                setParameter("id", id).getSingleResult();
    }

    @Override
    public Shop findByName(String name) {
        try {
            return entityManager.createQuery("Select s from Shop s where s.name = :shopName", Shop.class).
                    setParameter("shopName", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
