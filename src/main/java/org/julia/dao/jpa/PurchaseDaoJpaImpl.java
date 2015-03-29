package org.julia.dao.jpa;

import org.julia.dao.PurchaseDao;
import org.julia.domain.Purchase;
import org.julia.transfer.PurchaseDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * User: Миша
 * Date: 11.01.15
 */
@Repository("purchaseDao")
public class PurchaseDaoJpaImpl extends AbstractGenericDaoJPA<Purchase> implements PurchaseDao {
    @Override
    public List<Purchase> findAllWithFetch() {
        return entityManager.createQuery("select p from Purchase p join fetch p.goods join fetch p.shop", Purchase.class).getResultList();
    }

    @Override
    public Purchase findWithFetch(long id) {
        return entityManager.createQuery("select p from Purchase p join fetch p.goods join fetch p.shop where p.id = :id",
                Purchase.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public Purchase findByGoodsNameAndShopName(String goodsName, String shopName) {
        try {
        return entityManager.createQuery("select p from Purchase p where p.goods.name =:goodsName " +
                "and p.shop.name =:shopName", Purchase.class)
                .setParameter("goodsName", goodsName).setParameter("shopName", shopName).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}