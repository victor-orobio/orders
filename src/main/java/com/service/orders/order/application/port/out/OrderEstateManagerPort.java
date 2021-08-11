package com.service.orders.order.application.port.out;

import com.service.orders.order.domain.Order;

public interface OrderEstateManagerPort {
    Order save(Order order);
}
