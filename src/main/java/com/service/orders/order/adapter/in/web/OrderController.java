package com.service.orders.order.adapter.in.web;

import com.service.orders.common.ResponseHandler;
import com.service.orders.order.adapter.in.web.dto.DtoDataFormatter;
import com.service.orders.order.adapter.in.web.dto.OrderInputData;
import com.service.orders.order.application.port.in.ReceiveOrderCommand;
import com.service.orders.order.application.port.in.ReceiveOrderUseCase;
import com.service.orders.order.application.service.OrderQueryService;
import com.service.orders.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final ResponseHandler responseHandler;

    private final ReceiveOrderUseCase receiveOrderUseCaseService;

    private final OrderQueryService queryService;

    private final DtoDataFormatter formatter;

    @PostMapping("/orders")
    public ResponseEntity<Object> receiveOrder(@Valid @RequestBody OrderInputData inputData){
        Order.ClientId clientId = new Order.ClientId(inputData.getClientId());
        return responseHandler.generateResponse(
                HttpStatus.OK,
                formatter.toOutputFormat(receiveOrderUseCaseService.receiveOrder(
                        new ReceiveOrderCommand(clientId, inputData.getRequestedItems()))));
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> findAll(){
        Map<String, Object> response =  new HashMap<>();
        response.put("content", queryService.findAll().stream().map(formatter::toOutputFormat).collect(Collectors.toList()));
        return responseHandler.generateResponse(HttpStatus.OK, response);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return responseHandler.generateResponse( HttpStatus.OK, formatter.toOutputFormat(queryService.findById(id)));
    }

}
