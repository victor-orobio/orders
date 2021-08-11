package com.service.orders.order.adapter.in.web.dto;

import com.service.orders.order.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DtoDataFormatter {
    public OrderOutputData toOutputFormat(Order order){
        return OrderOutputData.builder()
                .orderId(order.getId().getValue())
                .date(order.getTimestamp().toLocalDate())
                .clientId(order.getClientId().getValue())
                .cost(BigDecimal.valueOf(centsToDollars(order.calculateCost())))
                .details(order.getOrderedItems().getDetails())
                .build();
    }

    private Double centsToDollars(Integer cents){
        return cents / Double.valueOf(100);
    }
}
