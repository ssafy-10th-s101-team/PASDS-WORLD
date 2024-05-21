package world.pasds.back.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;
import world.pasds.back.notification.entity.dto.response.NotificationResponseDto;

@Component
public class NotificationEventPublisher {
    private final Sinks.Many<NotificationResponseDto> sink;

    @Autowired
    public NotificationEventPublisher(Sinks.Many<NotificationResponseDto> sink) {
        this.sink = sink;
    }

    public void publish(NotificationResponseDto notification) {
        sink.tryEmitNext(notification);
    }
}
