package com.service.orders.order.adapter.out.persistence;

import com.service.orders.order.application.port.out.OrderQueryPort;
import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.Offer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderQueryAdapter implements OrderQueryPort {
    @Override
    public Map<Long, Item> findAllItems() {
        Map<Long, Item> items = new HashMap<>();
        items.put(10L, new Item(new Item.ItemId(10L), "Apple", 60));
        items.put(11L, new Item(new Item.ItemId(11L), "Orange", 25));
        return items;
    }

    @Override
    public Map<Long, Offer> findAllOffers() {
        Map<Long, Offer> offers = new HashMap<>();
        offers.put(10L, new Offer(new Item.ItemId(10L), 2, 1));
        offers.put(11L, new Offer(new Item.ItemId(11L), 3, 2));
        return offers;
    }

}
