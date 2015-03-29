package org.julia.controllers;

import org.julia.domain.Goods;
import org.julia.domain.Purchase;
import org.julia.service.GoodsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * User: Миша
 * Date: 16.11.14
 */

@Controller
@RequestMapping("/")
public class GoodsController {
    private final static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    GoodsServiceImpl goodsService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "api/goods", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Goods> getAllGoods() {
        return goodsService.getAllGoods();
    }

    @RequestMapping(value = "api/goods/purchase/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Purchase> getPurchasesForGood(@PathVariable long id) {
        return goodsService.getPurchasesForGood(id);
    }

    @RequestMapping(value = "api/deleteGoods/{id}", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> deleteGoods(@PathVariable long id, @RequestBody Map<String, String> params) {
        Map<String, String> responseMap = goodsService.deleteGoodsWithResponse(id, params);
        return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.OK);
    }


    @RequestMapping(value = "api/goods/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Goods getGoods(@PathVariable long id) {
        return goodsService.getGoods(id);
    }

    @RequestMapping(value = "api/goods/", method = RequestMethod.POST)
    public
    @ResponseBody
    Goods updateGoods(@RequestBody Goods goods) {
        return goodsService.updateGoods(goods);
    }
}
