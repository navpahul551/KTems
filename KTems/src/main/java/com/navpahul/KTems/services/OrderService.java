package com.navpahul.KTems.services;

import java.util.ArrayList;
import java.util.List;

import com.navpahul.KTems.entities.Item;
import com.navpahul.KTems.entities.Order;
import com.navpahul.KTems.entities.User;
import com.navpahul.KTems.exceptions.OrderNotFoundException;
import com.navpahul.KTems.exceptions.UserNotFoundException;
import com.navpahul.KTems.repositories.CartRepository;
import com.navpahul.KTems.repositories.OrderRepository;
import com.navpahul.KTems.repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    
    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    // public Order addOrder(Long cartId) throws Exception{
    //     Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
    //     if(cart.getItems().isEmpty())
    //         throw new CartIsEmptyException();
    //     List<Item> orderItems = deepCopyCartItems(cart.getItems());
    //     Order newOrder = new Order(cart.getUser(), orderItems);
    //     cart.getItems().clear();
    //     cartRepository.save(cart);
    //     return orderRepository.save(newOrder);
    // }

    public void deleteOrder(Long orderId) throws Exception{
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        orderRepository.delete(order);
    }

    public Order getOrderDetails(Long orderId) throws Exception{
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    public List<Order> getAllOrders(Long userId) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return orderRepository.findByUser(user);
    }

    private List<Item> deepCopyCartItems(List<Item> cartItems){
        List<Item> orderItems = new ArrayList<Item>();
        for(var item : cartItems){
            Item newItem = new Item(item.getName(), item.getPrice(), item.getDescription(), item.getQuantity(), item.getCategory());
            newItem.setId(item.getId());
            orderItems.add(item);
        }
        return orderItems;
    }
}
