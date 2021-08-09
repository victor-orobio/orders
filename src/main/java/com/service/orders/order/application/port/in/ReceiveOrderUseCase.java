package com.service.orders.order.application.port.in;

import com.service.orders.order.domain.Order;

public interface ReceiveOrderUseCase {

    Order receiveOrder(ReceiveOrderCommand command);

}
