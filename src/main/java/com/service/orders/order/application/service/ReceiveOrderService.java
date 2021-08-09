package com.service.orders.order.application.service;

import com.service.orders.order.application.port.in.ReceiveOrderCommand;
import com.service.orders.order.application.port.in.ReceiveOrderUseCase;
import com.service.orders.order.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReceiveOrderService implements ReceiveOrderUseCase {
    @Override
    public Order receiveOrder(ReceiveOrderCommand command) {
        Order.OrderId orderId = new Order.OrderId(1L);
        OrderDetail orderDetail = new OrderDetail(processRequestDetails(orderId, command.getRequestDetails()));

        return new Order(orderId, command.getClientId(), LocalDateTime.now(), orderDetail);
    }

    private List<Detail> processRequestDetails(Order.OrderId orderId, List<RequestedItem> requestDetails) {
        Map<Long, Item> items = new HashMap<>();
        items.put(10L, new Item(new Item.ItemId(10L), "Apple", 60));
        items.put(11L, new Item(new Item.ItemId(11L), "Orange", 25));

        return requestDetails
                .stream()
                .map(requestedItem -> new Detail(
                        new Detail.DetailId(0L),
                        orderId,
                        requestedItem.getItemId(),
                        requestedItem.getQuantity(),
                        (items.get(requestedItem.getItemId().getValue()).getCost())))
                .collect(Collectors.toList());

    }
}
