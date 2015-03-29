package org.julia.service;

import org.julia.dao.GoodsDao;
import org.julia.domain.Goods;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * User: Миша
 * Date: 24.01.15
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class GoodsServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private GoodsDao goodsDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test

    public void testGetAllGoods() throws Exception {
        List<Goods> all = goodsDao.findAll();
        System.out.println("goodsDao "+all.size());
        Assert.notEmpty(all);
    }

    @Test
    public void testGetPurchasesForGood() throws Exception {

    }

    @Test
    public void testUpdateGoods() throws Exception {

    }

    @Test
    public void testDeleteGoods() throws Exception {
    }

    @Test
    public void testGetPurchasesCountForGood() throws Exception {

    }

    @Test
    public void testGetGoods() throws Exception {

    }
}
