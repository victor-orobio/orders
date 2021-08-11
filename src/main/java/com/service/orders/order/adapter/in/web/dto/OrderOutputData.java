package com.service.orders.order.adapter.in.web.dto;

import com.service.orders.order.domain.Detail;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class OrderOutputData {

    @NonNull
    private final Long orderId;

    @NonNull
    private final LocalDate date;

    @NonNull
    private final Long clientId;

    @NonNull
    private final BigDecimal cost;

    @NonNull
    private final List<Detail> details;
}
