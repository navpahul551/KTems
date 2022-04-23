package com.navpahul.KTems.controllers;

import com.navpahul.KTems.data.CategoryDetails;
import com.navpahul.KTems.data.ItemDetails;
import com.navpahul.KTems.exceptions.CategoryNotFoundException;
import com.navpahul.KTems.services.CategoryService;
import com.navpahul.KTems.services.ItemService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categories")
public class CategoryController {

    private CategoryService categoryService;
    private ItemService itemService;
    
    public CategoryController(CategoryService categoryService, ItemService itemService){
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCategory(@PathVariable(name="id") Long categoryId){
        try{
            return ResponseEntity.ok(categoryService.getCategory(categoryId));
        }
        catch(CategoryNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDetails categoryDetails){
        try{
            return ResponseEntity.ok(categoryService.addCategory(categoryDetails));
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while processing your request!!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        try{
            return ResponseEntity.ok(categoryService.getAllCategories());
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}/items")
    public ResponseEntity<?> getItemsByCategoryID(@PathVariable(name="id") Long id){
        try{
            return ResponseEntity.ok(itemService.getItemsByCategoryID(id));
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{id}/items/add")
    public ResponseEntity<?> addItem(@RequestBody ItemDetails itemDetails){
        try{
            return ResponseEntity.ok(itemService.addItem(itemDetails));
        }
        catch(CategoryNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
