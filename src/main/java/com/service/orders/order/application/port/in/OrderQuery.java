package com.service.orders.order.application.port.in;

import com.service.orders.order.domain.Order;

import java.util.List;

public interface OrderQuery {

    Order findById(Long id);

    List<Order> findAll();

}
