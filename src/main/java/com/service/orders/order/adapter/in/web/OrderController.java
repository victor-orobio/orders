package com.service.orders.order.adapter.in.web;

import com.service.orders.common.ResponseHandler;
import com.service.orders.order.adapter.in.web.dto.DtoDataFormatter;
import com.service.orders.order.adapter.in.web.dto.OrderInputData;
import com.service.orders.order.application.port.in.ReceiveOrderCommand;
import com.service.orders.order.application.port.in.ReceiveOrderUseCase;
import com.service.orders.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final ResponseHandler responseHandler;

    private final ReceiveOrderUseCase receiveOrderUseCaseService;

    private final DtoDataFormatter formatter;

    @PostMapping("/orders")
    public ResponseEntity<Object> receiveOrder(@Valid @RequestBody OrderInputData inputData){
        Order.ClientId clientId = new Order.ClientId(inputData.getClientId());
        return responseHandler.generateResponse(
                HttpStatus.OK,
                formatter.toOutputFormat(receiveOrderUseCaseService.receiveOrder(new ReceiveOrderCommand(clientId, inputData.getRequestedItems()))));
    }
}
