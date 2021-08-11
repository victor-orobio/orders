package com.service.orders.order.adapter.out.persistence.entity;

import com.service.orders.order.domain.DiscountDetail;
import com.service.orders.order.domain.OfferedDiscounts;
import com.service.orders.order.domain.Order;
import com.service.orders.order.domain.OrderedItems;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDataMapper {

    public static Order mapToOrder(OrderEntity orderEntity, List<ItemDetailEntity> detailEntityList, List<DiscountDetailEntity> discountDetailEntityList) {
        return new Order(
                new Order.OrderId(orderEntity.getId()),
                new Order.ClientId(orderEntity.getClientId()),
                orderEntity.getDate(),
                new OrderedItems(detailEntityList.stream()
                        .map(ItemDetailDataMapper::mapToBusiness).collect(Collectors.toList())),
                discountDetailEntityList.isEmpty() ?
                        new OfferedDiscounts( new ArrayList<DiscountDetail>()) :
                        new OfferedDiscounts( discountDetailEntityList.stream()
                                .map(DiscountDetailDataMapper::mapToBusiness).collect(Collectors.toList())));
    }
}
