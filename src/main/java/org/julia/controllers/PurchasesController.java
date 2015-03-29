package org.julia.controllers;

import org.julia.domain.Purchase;
import org.julia.service.PurchaseServiceImpl;
import org.julia.transfer.PurchaseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: Миша
 * Date: 18.09.14
 */

@Controller
@RequestMapping("/")
public class PurchasesController {
    private final static Logger log = LoggerFactory.getLogger(PurchasesController.class);
    @Autowired
    PurchaseServiceImpl purchaseService;


    @RequestMapping(method = RequestMethod.GET)
    public String start(ModelMap model){
        return "index";
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hello Julia!");
        return "hello";
    }

    @RequestMapping(value = "api/purchases", method = RequestMethod.GET)
    public @ResponseBody List<Purchase> getAllPurchases(){
          return purchaseService.getAllPurchases();
    }

    @RequestMapping(value = "api/purchase", method = RequestMethod.POST)
    public @ResponseBody Purchase addPurchase(@RequestBody PurchaseDTO purchaseDTO){
        return purchaseService.saveNewPurchase(purchaseDTO);
    }

    @RequestMapping(value = "api/purchase/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deletePurchase(@PathVariable long id){
        purchaseService.removePurchase(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "api/purchase/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Purchase getPurchase(@PathVariable long id) {
        return purchaseService.getPurchase(id);
    }
}
