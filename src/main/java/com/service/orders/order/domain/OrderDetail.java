package com.service.orders.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class OrderDetail {

    @Getter
    private List<Detail> details;

    public OrderDetail(@NonNull Detail... details) {
        this.details = new ArrayList<>(Arrays.asList(details));
    }

    public OrderDetail(@NonNull List<Detail> details) {
        this.details = details;
    }


    public Integer calculateCost() {
        return details.stream()
                .map(Detail::calculateTotalCost)
                .mapToInt(Integer::intValue).sum();
    }
}
