package com.service.orders.order.adapter.out.persistence;

import com.service.orders.order.adapter.out.persistence.entity.*;
import com.service.orders.order.application.port.out.OrderEstateManagerPort;
import com.service.orders.order.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderEstateManagerAdapter implements OrderEstateManagerPort {

    private final OrderRepository orderRepository;

    private final ItemDetailRepository itemDetailRepository;

    private final DiscountDetailRepository discountDetailRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderRepository.save(new OrderEntity(
                null, order.getClientId().getValue(), order.getTimestamp()));
        List<ItemDetailEntity> detailEntityList = persistDetailEntityList(order.getOrderedItems().getDetails(), orderEntity);
        List<DiscountDetailEntity> discountDetailEntityList = persistDiscountEntityList(order.getOfferedDiscounts().getDetails(), orderEntity);
        return OrderDataMapper.mapToOrder(orderEntity, detailEntityList, discountDetailEntityList);
    }

    private List<ItemDetailEntity> persistDetailEntityList(List<ItemDetail> orderDetails, OrderEntity orderEntity) {
        return itemDetailRepository.saveAll(
                orderDetails.stream()
                        .map(ItemDetailDataMapper::mapToEntityNoId)
                        .collect(Collectors.toList()));
    }

    private List<DiscountDetailEntity> persistDiscountEntityList(List<DiscountDetail> discountDetails, OrderEntity orderEntity){
        if (discountDetails.isEmpty()) return new ArrayList<DiscountDetailEntity>();

        return discountDetailRepository.saveAll(
                discountDetails.stream()
                        .map(DiscountDetailDataMapper::mapToEntityNoId)
                        .collect(Collectors.toList()));
    }
}
