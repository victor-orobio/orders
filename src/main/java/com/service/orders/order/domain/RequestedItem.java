package com.service.orders.order.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class RequestedItem {

    @Getter
    @NonNull
    private final Long itemId;

    @Getter
    @NonNull
    private final Integer quantity;
}
