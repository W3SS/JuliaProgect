package org.julia.controllers;

import org.julia.domain.Purchase;
import org.julia.domain.Shop;
import org.julia.service.ShopServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * User: Миша
 * Date: 17.11.14
 */
@Controller
@RequestMapping("/")
public class ShopController {
    private final static Logger log = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    ShopServiceImpl shopService;
    @Autowired
    MessageSource messageSource;


    @RequestMapping(value = "api/shops", method = RequestMethod.GET)
    public @ResponseBody
    List<Shop> getAllShops(){
        return shopService.getAllShops();
    }

    @RequestMapping(value = "api/shops/purchase/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Purchase> getPurchasesForGood(@PathVariable long id) {
        return shopService.getPurchasesForShop(id);
    }

    @RequestMapping(value = "api/shops/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Shop getShop(@PathVariable long id) {
        return shopService.getShop(id);
    }

    @RequestMapping(value = "api/shops/", method = RequestMethod.POST)
    public
    @ResponseBody
    Shop updateShop(@RequestBody Shop shop) {
        return shopService.updateShop(shop);
    }

    @RequestMapping(value = "api/deleteShops/{id}", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> deleteShop(@PathVariable long id,  @RequestBody Map<String,String> params) {
        String force1 = params.get("force");
        Map<String, String> responseMap = new HashMap<String, String>();
        boolean force = Boolean.parseBoolean(force1);
        if (force){
            shopService.deleteShop(id);
            responseMap.put("count", "0");
        } else{
            Long purchaseCount = shopService.getPurchasesCountForShop(id);
            responseMap.put("count", purchaseCount.toString());
            if(purchaseCount == 0){
                shopService.deleteShop(id);
            }else{
                String message = messageSource.getMessage("shop.delete.confirm", null, Locale.getDefault());
                responseMap.put("message", message);
            }
        }
        return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.OK);
    }
}
