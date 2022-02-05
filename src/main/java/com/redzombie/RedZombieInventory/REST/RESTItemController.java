package com.redzombie.RedZombieInventory.REST;

import com.redzombie.RedZombieInventory.Item.ItemService;
import com.redzombie.RedZombieInventory.Model.ItemModel;
import com.redzombie.RedZombieInventory.aop.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dto.ItemDto;

@RestController
@RequestMapping("API")
public class RESTItemController{
    
    private Logger logger = LoggerFactory.getLogger(RESTItemController.class);

    private ItemService itemService;

    @Autowired
    public RESTItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("testGet")
    @Log
    public ResponseEntity<Object> testGET(){
        return ResponseEntity.ok("Hi there");
    }

    @PostMapping("itemActualUpdate")
    @Log
    public ResponseEntity<Object> updateActualTotal(
        @RequestParam(name="itemID", required = true) String itemID,
        @RequestParam(name= "actualValue", required=false) String actual  
    ){
        Object item = new Object();
        try{
            ItemModel existingItem = itemService.getItemInfo(itemID);
            int actualValue = Integer.parseInt(actual);
            existingItem.setActualTotal(actual);
            itemService.updateItem(existingItem);
            item = existingItem;
        }catch(Exception e){
            return ResponseEntity.ok("Internal error occured updating the item " + e.toString());
        }
        if(item != null){
            return ResponseEntity.ok(item);
        }
        else{
            return ResponseEntity.ok("item not fetched");
        }
    }

    @PostMapping("itemComingUpdate")
    @Log
    public ResponseEntity<Object> updateComingAmount(
        @RequestParam(name="itemID", required = true) String itemID,
        @RequestParam(name= "comingValue", required=false) String coming        
    ){
        Object item = new Object();
        try{
            ItemModel existingItem = itemService.getItemInfo(itemID);
            int comingValue = Integer.parseInt(coming);
            existingItem.setComing(comingValue);
            itemService.updateItem(existingItem);
            item = existingItem;
        }catch(Exception e){
            return ResponseEntity.ok("Internal error occured updating the item " + e.toString());
        }
        if(item != null){
            return ResponseEntity.ok(item);
        }
        else{
            return ResponseEntity.ok("item not fetched");
        }
    }

}