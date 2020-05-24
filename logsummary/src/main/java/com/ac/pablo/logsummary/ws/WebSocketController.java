package com.ac.pablo.logsummary.ws;

import com.ac.pablo.logsummary.service.AggregatorService;
import com.ac.pablo.logsummary.service.model.Aggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final AggregatorService service;

    @MessageMapping("/log")
    @SendTo("/topic/messages")
    public Aggregate receive(String message) {
        return service.getLastAggregate();
    }

}
