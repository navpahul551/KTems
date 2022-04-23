package com.navpahul.KTems.services;

import java.util.List;

import com.navpahul.KTems.data.ItemDetails;
import com.navpahul.KTems.entities.Category;
import com.navpahul.KTems.entities.Item;
import com.navpahul.KTems.exceptions.CategoryNotFoundException;
import com.navpahul.KTems.exceptions.ItemNotFoundException;
import com.navpahul.KTems.repositories.CategoryRepository;
import com.navpahul.KTems.repositories.ItemRepository;
import com.navpahul.KTems.repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository,
            UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItem(Long id) throws Exception {
        Item item = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        return item;
    }

    public Item addItem(ItemDetails itemDetails) throws Exception {
        Category category = categoryRepository.findById(itemDetails.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Item newItem = new Item(itemDetails.getName(),
                itemDetails.getPrice(),
                itemDetails.getDescription(),
                itemDetails.getQuantity(),
                category);

        return itemRepository.save(newItem);
    }

    public void deleteItem(Long id) throws Exception {
        Item itemToDelete = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        itemRepository.delete(itemToDelete);
    }

    public Item updateItem(Long id, ItemDetails itemDetails) throws Exception {
        Item itemToUpdate = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);

        Category category = categoryRepository.findById(itemDetails.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        itemToUpdate.setDescription(itemDetails.getDescription());
        itemToUpdate.setName(itemDetails.getName());
        itemToUpdate.setPrice(itemDetails.getPrice());
        itemToUpdate.setQuantity(itemDetails.getQuantity());
        itemToUpdate.setCategory(category);
        return itemRepository.save(itemToUpdate);
    }

    public List<Item> getItemsByCategoryID(Long categoryId) {
        return itemRepository.findAllByCategoryId(categoryId);
    }
}
