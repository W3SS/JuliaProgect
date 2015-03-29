package org.julia.controllers;

import org.julia.domain.Goods;
import org.julia.service.GoodsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * User: Миша
 * Date: 08.02.15
 */
public class GoodsControllerTest {
    List<Goods> list = new ArrayList<Goods>();

    GoodsController controller;

    GoodsServiceImpl goodsService;

    MessageSource messageSource;

    @Before
    public void setUp() throws Exception {
        controller = new GoodsController();
        goodsService = mock(GoodsServiceImpl.class);
        messageSource = mock(MessageSource.class);
        ReflectionTestUtils.setField(controller, "goodsService", goodsService);
        ReflectionTestUtils.setField(controller, "messageSource", messageSource);

    }

    @Test
    public void testGetAllGoods() throws Exception {
        Goods goods = new Goods();
        goods.setName("Йогурт");
        list.add(goods);
        when(goodsService.getAllGoods()).thenReturn(list);
        List<Goods> allGoods = controller.getAllGoods();
        assertEquals(allGoods.size(), 1);
    }

    @Test
    public void testGetPurchasesForGood() throws Exception {

    }

    @Test
    public void testDeleteGoods() throws Exception {
        Map<String,String> params = new HashMap<>();
        Goods goods = new Goods();
        goods.setName("Йогурт");
        list.add(goods);
        when(messageSource.getMessage("goods.delete.confirm", null, Locale.getDefault())).thenReturn("confirm");
        when(goodsService.getPurchasesCountForGood(0)).thenReturn(1L);
        doAnswer((invocationOnMock) -> list.remove(0)).when(goodsService).deleteGoods(0);
        ResponseEntity<Map<String,String>> mapResponseEntity = controller.deleteGoods((long) 0, (Map<String, String>) params);
        Map<String, String> responseMap = mapResponseEntity.getBody();
        String count = responseMap.get("count");
        String message = responseMap.get("message");
        assertEquals(count, "1");
        assertEquals(message, "confirm");
        assertEquals(list.size(), 1);

    }

    @Test
    public void testGetGoods() throws Exception {

    }

    @Test
    public void testUpdateGoods() throws Exception {
        Goods goods = new Goods();
        goods.setName("goodsForUpdate");
        when(goodsService.updateGoods(goods)).thenAnswer((invocationOnMock) -> {
            list.add(goods);
            return goods;
        });
        controller.updateGoods(goods);
        assertEquals(list.size(), 1);
    }
}
