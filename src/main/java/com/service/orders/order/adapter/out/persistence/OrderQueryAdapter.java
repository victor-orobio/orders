package com.service.orders.order.adapter.out.persistence;

import com.service.orders.order.application.port.out.OrderQueryPort;
import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OrderQueryAdapter implements OrderQueryPort {

    private final ItemRepository itemRepository;

    @Override
    public Map<Long, Item> findAllItems() {
        Map<Long, Item> items = new HashMap<>();
        itemRepository.findAll().stream()
                .forEach(itemEntity -> items.put(itemEntity.getId(), new Item(
                        new Item.ItemId(itemEntity.getId()),
                        itemEntity.getDescription(),
                        itemEntity.getCost())));
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
