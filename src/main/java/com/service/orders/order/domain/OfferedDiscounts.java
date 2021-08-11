package com.service.orders.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
public class OfferedDiscounts {

    @Getter
    private List<DiscountDetail> details;

    public OfferedDiscounts(@NonNull DiscountDetail... details) {
        this.details = new ArrayList<>(Arrays.asList(details));
    }

    public OfferedDiscounts(@NonNull List<DiscountDetail> details) {
        this.details = details;
    }

    public Integer calculateDiscount() {
        if (details.isEmpty()) return 0;

        return details.stream()
                .map(DiscountDetail::calculateTotalDiscount)
                .mapToInt(Integer::intValue).sum();
    }
}
