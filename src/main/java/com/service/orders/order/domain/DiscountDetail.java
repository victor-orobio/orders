package com.service.orders.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@EqualsAndHashCode
@RequiredArgsConstructor
public class DiscountDetail {
    @Getter
    private DetailId id;

    @Getter
    private final Order.OrderId orderId;

    @Getter
    private final Item.ItemId itemId;

    @Getter
    private final Integer quantity;

    @Getter
    private final Integer costDiscounted;

    public Integer calculateTotalDiscount(){
        return this.quantity * this.costDiscounted;
    }

    @Value
    public static class DetailId {
        private final Long value;
    }
}
