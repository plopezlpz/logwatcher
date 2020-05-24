package com.ac.pablo.logsummary.ws;

import com.ac.pablo.logsummary.service.Notifier;
import com.ac.pablo.logsummary.service.model.Aggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WebSocketNotifier implements Notifier {

    private final SimpMessagingTemplate template;

    @Override
    public void notifyAggregate(Aggregate aggregate) {
        template.convertAndSend("/topic/messages", aggregate);
    }
}
