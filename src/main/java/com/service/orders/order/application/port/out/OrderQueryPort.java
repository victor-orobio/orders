package com.service.orders.order.application.port.out;

import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.Offer;
import com.service.orders.order.domain.Order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderQueryPort {
    Map<Long, Item> findAllItems();
    Map<Long, Offer> findAllOffers();
    Optional<Order> findById(Long id);
    List<Order> findAll();
}
