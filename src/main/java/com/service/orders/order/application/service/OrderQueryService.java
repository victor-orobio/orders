package com.service.orders.order.application.service;

import com.service.orders.order.application.port.in.OrderQuery;
import com.service.orders.order.application.port.out.OrderQueryPort;
import com.service.orders.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class OrderQueryService implements OrderQuery {

    private final OrderQueryPort queryPort;

    @Override
    public Order findById(Long id) {
        return queryPort.findById(id).orElseThrow(() ->new NoSuchElementException("Element not found"));
    }

    @Override
    public List<Order> findAll() {
        return queryPort.findAll();
    }
}
