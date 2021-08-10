package com.service.orders.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseHandler {

    public ResponseEntity<Object> generateResponse(HttpStatus status, Object content){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("content", content);
        responseMap.put("timestamp", LocalDateTime.now());
        responseMap.put("status", status);
        return ResponseEntity.status(status).body(responseMap);
    }
}
