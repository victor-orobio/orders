package com.service.orders.order.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Item {

    @Getter
    private ItemId id;

    @Getter
    @NonNull
    private String description;

    @Getter
    @NonNull
    private Integer cost;

    @Value
    public static class ItemId {
        private final Long value;
    }
}
