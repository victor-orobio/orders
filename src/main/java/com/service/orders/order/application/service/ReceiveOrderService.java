package com.service.orders.order.application.service;

import com.service.orders.order.application.port.in.ReceiveOrderCommand;
import com.service.orders.order.application.port.in.ReceiveOrderUseCase;
import com.service.orders.order.application.port.out.OrderQueryPort;
import com.service.orders.order.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReceiveOrderService implements ReceiveOrderUseCase {

    private final OrderQueryPort queryPort;

    @Override
    public Order receiveOrder(ReceiveOrderCommand command) {
        Order.OrderId orderId = new Order.OrderId(1L);

        OrderedItems orderedItems = new OrderedItems(processRequestDetails(orderId, command.getRequestDetails()));
        OfferedDiscounts offeredDiscounts = new OfferedDiscounts(getApplicableDiscounts(orderedItems));

        return new Order(orderId, command.getClientId(), LocalDateTime.now(), orderedItems, offeredDiscounts);
    }

    private List<ItemDetail> processRequestDetails(Order.OrderId orderId, List<RequestedItem> requestDetails) {
        Map<Long, Item> items = queryPort.findAllItems();

        return requestDetails
                .stream()
                .map(requestedItem -> new ItemDetail(
                        new ItemDetail.DetailId(0L),
                        orderId,
                        new Item.ItemId(requestedItem.getItemId()),
                        requestedItem.getQuantity(),
                        (items.get(requestedItem.getItemId()).getCost())))
                .collect(Collectors.toList());
    }

    private List<DiscountDetail> getApplicableDiscounts(OrderedItems orderedItems){
        List<DiscountDetail> discounts = new ArrayList<>();
        Map<Long, Offer> offers = queryPort.findAllOffers();

        return orderedItems.getDetails()
                .stream()
                .filter(itemDetail -> offers.containsKey(itemDetail.getItemId().getValue()))
                .map(itemDetail ->{
                    Offer offer = offers.get(itemDetail.getItemId().getValue());
                    Integer discountQuantity = offer.calculateDiscountQuantity(itemDetail.getQuantity());
                    return new DiscountDetail(
                            new DiscountDetail.DetailId(0L),
                            itemDetail.getOrderId(),
                            itemDetail.getItemId(),
                            discountQuantity,
                            itemDetail.getCost());
                }).collect(Collectors.toList());


    }
}
