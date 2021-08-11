package com.service.orders.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
public class OrderedItems {

    @Getter
    private List<ItemDetail> details;

    public OrderedItems(@NonNull ItemDetail... details) {
        this.details = new ArrayList<>(Arrays.asList(details));
    }

    public OrderedItems(@NonNull List<ItemDetail> details) {
        this.details = details;
    }


    public Integer calculateCost() {
        return details.stream()
                .map(ItemDetail::calculateTotalCost)
                .mapToInt(Integer::intValue).sum();
    }
}
