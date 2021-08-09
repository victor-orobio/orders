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
    private final Item.ItemId itemId;

    @Getter
    @NonNull
    private final Integer quantity;
}
