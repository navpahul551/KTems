package com.navpahul.KTems.controllers;

import com.navpahul.KTems.data.ItemDetails;
import com.navpahul.KTems.exceptions.CategoryNotFoundException;
import com.navpahul.KTems.exceptions.ItemNotFoundException;
import com.navpahul.KTems.exceptions.UserNotFoundException;
import com.navpahul.KTems.services.ItemService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("items")
public class ItemController {
    
    private ItemService itemService;

    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllItems(){
        try{
            return ResponseEntity.ok(itemService.getAllItems());
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getItem(@PathVariable Long id){
        try{
            return ResponseEntity.ok(itemService.getItem(id));
        }
        catch(ItemNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody ItemDetails itemDetails){
        try{
            return ResponseEntity.ok(itemService.updateItem(id, itemDetails));
        }
        catch(ItemNotFoundException | CategoryNotFoundException | UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id){
        try{
            itemService.deleteItem(id);
            return ResponseEntity.ok("Item deleted successfully!!!");
        }
        catch(ItemNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
