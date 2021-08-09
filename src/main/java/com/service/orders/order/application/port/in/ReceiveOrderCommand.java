package com.service.orders.order.application.port.in;

import com.service.orders.order.domain.Order;
import com.service.orders.order.domain.OrderDetail;
import com.service.orders.order.domain.RequestedItem;
import lombok.EqualsAndHashCode;

import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = false)
public class ReceiveOrderCommand {

    @NotNull
    private final Order.ClientId clientId;

    @NotEmpty
    private final List<RequestedItem> requestDetails;

    public ReceiveOrderCommand(Order.ClientId clientId, List<RequestedItem> requestDetails) {
        this.clientId = clientId;
        this.requestDetails = requestDetails;
    }
}
