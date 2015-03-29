package org.julia.validation;

import org.julia.dao.GoodsDao;
import org.julia.dao.PurchaseDao;
import org.julia.dao.ShopDao;
import org.julia.domain.BusinessEntity;
import org.julia.domain.Goods;
import org.julia.domain.Purchase;
import org.julia.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Locale;
import java.util.Set;

/**
 * User: Миша
 * Date: 24.11.14
 */

public class JuliaValidationService {

    private Validator validator;

    private PurchaseDao purchaseDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private MessageSource messageSource;

    /**
     * validate constrains for item if they are violated throws an exception
     *
     * @param item - BusinessEntity
     * @throws IllegalArgumentException
     */
    public <T> void validateItem(T item) {
        Set<ConstraintViolation<T>> violations = validator.validate(item);
        if (!violations.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                String message = messageSource.getMessage(violation.getMessage(), null, Locale.getDefault());
                messageBuilder.append(message).append("\n");
            }
            throw new IllegalArgumentException(messageBuilder.toString());
        }
    }

    public void validateUniqueShopName(Shop shop){
        if(shopDao.findByName(shop.getName()) != null){
            String message = messageSource.getMessage("shop.name.unique.warning", null, Locale.getDefault());
            throw new IllegalArgumentException(message);
        }
    }

    public void validateUniqueGoodsName(Goods goods){
        if(goodsDao.findByName(goods.getName()) != null){
            String message = messageSource.getMessage("goods.name.unique.warning", null, Locale.getDefault());
            throw new IllegalArgumentException(message);
        }
    }

    public void validateUniquePurchase(Long id, String goodsName, String shopName){
        Purchase purchase = purchaseDao.findByGoodsNameAndShopName(goodsName, shopName);
        if(purchase != null && purchase.getId() != id){
            String message = messageSource.getMessage("purchase.name.unique.warning", null, Locale.getDefault());
            throw new IllegalArgumentException(message);
        }
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setPurchaseDao(PurchaseDao purchaseDao) {
        this.purchaseDao = purchaseDao;
    }
}
