package com.service.orders.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@EqualsAndHashCode
@RequiredArgsConstructor
public class ItemDetail {
    @Getter
    private DetailId id;

    @Getter
    private final Order.OrderId orderId;

    @Getter
    private final Item.ItemId itemId;

    @Getter
    private final Integer quantity;

    @Getter
    private final Integer cost;

    public Integer calculateTotalCost(){
        return this.quantity * this.cost;
    }

    @Value
    public static class DetailId {
        private final Long value;
    }
}
