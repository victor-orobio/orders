package com.service.orders.order.application.port.out;

import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.Offer;

import java.util.Map;
import java.util.Optional;

public interface OrderQueryPort {
    Map<Long, Item> findAllItems();
    Map<Long, Offer> findAllOffers();
}
