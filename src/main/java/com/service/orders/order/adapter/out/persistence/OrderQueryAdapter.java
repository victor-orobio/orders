package com.service.orders.order.adapter.out.persistence;

import com.service.orders.order.adapter.out.persistence.entity.DiscountDetailEntity;
import com.service.orders.order.adapter.out.persistence.entity.ItemDetailEntity;
import com.service.orders.order.adapter.out.persistence.entity.OrderDataMapper;
import com.service.orders.order.adapter.out.persistence.entity.OrderEntity;
import com.service.orders.order.application.port.out.OrderQueryPort;
import com.service.orders.order.domain.Item;
import com.service.orders.order.domain.Offer;
import com.service.orders.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderQueryAdapter implements OrderQueryPort {

    private final ItemRepository itemRepository;

    private final OfferRepository offerRepository;

    private final OrderRepository orderRepository;

    private final ItemDetailRepository itemDetailRepository;

    private final DiscountDetailRepository discountDetailRepository;

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

    @Override
    public Optional<Order> findById(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if(orderEntity.isEmpty()) return Optional.empty();
        List<ItemDetailEntity> itemDetailEntities = itemDetailRepository.findByOrderId(id);
        List<DiscountDetailEntity> discountDetailEntities = discountDetailRepository.findByOrderId(id);
        return Optional.of( OrderDataMapper.mapToOrder(orderEntity.get(), itemDetailEntities, discountDetailEntities));
    }

    @Override
    public List<Order> findAll() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<Long> orderIds = orderEntities.stream().map(OrderEntity::getId).collect(Collectors.toList());
        List<ItemDetailEntity> itemDetailEntities = itemDetailRepository.findAllByOrderIdIn(orderIds);
        List<DiscountDetailEntity> discountDetailEntities = discountDetailRepository.findAllByOrderIdIn(orderIds);
        return getOrders(orderEntities, itemDetailEntities, discountDetailEntities);
    }

    private List<Order> getOrders(List<OrderEntity> orderEntities,
                                  List<ItemDetailEntity> itemDetailEntities,
                                  List<DiscountDetailEntity> discountDetailEntities){
        return orderEntities.stream().map(orderEntity -> OrderDataMapper.mapToOrder(orderEntity,
                itemDetailEntities.stream().filter(itemDetailEntity ->
                        itemDetailEntity.getOrderId().equals(orderEntity.getId())).collect(Collectors.toList()),
                discountDetailEntities.stream().filter(discountDetail
                        -> discountDetail.getOrderId().equals(orderEntity.getId())).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

}
