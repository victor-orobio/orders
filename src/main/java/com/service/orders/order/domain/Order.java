package com.service.orders.order.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
public class Order {
    @Getter
    private OrderId id;

    @Getter
    @NonNull
    private final ClientId clientId;

    @Getter
    @NonNull
    private final LocalDateTime timestamp;

    @Getter
    @NonNull
    private final OrderDetail orderDetail;

    public Integer calculateCost() {
        return orderDetail.calculateCost();
    }

    @Value
    public static class OrderId {
        private final Long value;
    }

    @Value
    public static class ClientId {
        private final Long value;
    }
}
