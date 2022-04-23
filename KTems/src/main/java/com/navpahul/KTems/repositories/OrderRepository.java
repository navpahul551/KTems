package com.navpahul.KTems.repositories;

import java.util.List;
import java.util.Optional;

import com.navpahul.KTems.entities.Order;
import com.navpahul.KTems.entities.User;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    public Optional<Order> findById(Long id);
    public List<Order> findByUser(User user);
}
