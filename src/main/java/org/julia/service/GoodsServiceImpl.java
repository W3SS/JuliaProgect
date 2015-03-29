package org.julia.service;

import org.julia.dao.GoodsDao;
import org.julia.domain.Goods;
import org.julia.domain.Purchase;
import org.julia.validation.JuliaValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * User: Миша
 * Date: 16.11.14
 */
@Service("goodsService")
@Transactional
public class GoodsServiceImpl {
    private final static Logger log = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private JuliaValidationService validationService;

    @Autowired
    MessageSource messageSource;

    @Transactional(readOnly = true)
    public List<Goods> getAllGoods() {
        return goodsDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Purchase> getPurchasesForGood(long id) {
        return goodsDao.getPurchaseListFor(id);
    }

    public Goods updateGoods(Goods good) {
        validationService.validateItem(good);
        validationService.validateUniqueGoodsName(good);
        return goodsDao.makePersistent(good);
    }

    public Map<String, String> deleteGoodsWithResponse(long id, Map<String, String> params) {
        Map<String, String> responseMap = new HashMap<String, String>();
        boolean force = Boolean.parseBoolean(params.get("force"));
        if (force){
            deleteGoods(id);
            responseMap.put("count", "0");
        } else{
            Long purchaseCount = getPurchasesCountForGood(id);
            responseMap.put("count", purchaseCount.toString());
            if(purchaseCount == 0){
                deleteGoods(id);
            }   else{
                String message = messageSource.getMessage("goods.delete.confirm", null, Locale.getDefault());
                responseMap.put("message", message);
            }
        }
        return responseMap;
    }

    public void deleteGoods(long id) {
        Goods goods = goodsDao.findById(id, false);
        goodsDao.makeTransient(goods);
    }

    @Transactional(readOnly = true)
    public long getPurchasesCountForGood(long id) {
        return goodsDao.getPurchaseCountFor(id);
    }

    @Transactional(readOnly = true)
    public Goods getGoods(long id) {
        return goodsDao.findById(id, false);
    }
}

