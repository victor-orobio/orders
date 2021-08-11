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

    private final OfferRepository offerRepository;

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
        offerRepository.findAll().stream()
                .forEach(offerEntity -> offers.put(offerEntity.getItemId(), new Offer(
                        new Item.ItemId(offerEntity.getItemId()),
                        offerEntity.getOfferedQuantity(),
                        offerEntity.getBaseQuantity())));
        return offers;
    }

}
