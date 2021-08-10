package com.service.orders.order.adapter.in.web.dto;

import com.service.orders.order.domain.RequestedItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@RequiredArgsConstructor
public class OrderInputData {

    @Positive
    private final Long clientId;

    @NotEmpty
    private final List<RequestedItem> requestedItems;
}
