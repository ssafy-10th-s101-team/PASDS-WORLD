package world.pasds.back.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.notification.entity.dto.response.NotificationResponseDto;
import world.pasds.back.notification.service.NotificationService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/app/api/notification")
public class NotificationController {
    private final Sinks.Many<NotificationResponseDto> sink;
    private final NotificationService notificationService;
    private final Map<String, Long> memberMap = new ConcurrentHashMap<>();

    @Autowired
    public NotificationController(Sinks.Many<NotificationResponseDto> sink, NotificationService notificationService) {
        this.sink = sink;
        this.notificationService = notificationService;
    }

    @GetMapping("/get-sse-token")
    public ResponseEntity<?> getSseToken(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String uuid = UUID.randomUUID().toString();
        memberMap.put(uuid, userDetails.getMemberId());
        return ResponseEntity.ok().body(uuid);
    }

    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<ServerSentEvent<NotificationResponseDto>> streamEvents(@RequestParam("token") String token) {
        Long memberId = memberMap.get(token);
        return sink.asFlux()
                .filter(data -> data.getToMemberId().equals(memberId))
                .map(data -> ServerSentEvent.builder(data)
                        .id(String.valueOf(data.getId()))
                        .event("notification")
                        .build());
    }

    @GetMapping("/count/unread")
    public ResponseEntity<?> getUnreadNotificationCount(@AuthenticationPrincipal CustomUserDetails userDetails) {
        int count = notificationService.countUnreadNotifications(userDetails.getMemberId());
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<?> readNotification(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable(name = "notificationId") Long notificationId) {
        NotificationResponseDto response = notificationService.readNotification(userDetails.getMemberId(), notificationId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> findAllUnreadNotifications(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(name = "offset") int offset) {
        List<NotificationResponseDto> response = notificationService.findAllUnreadNotifications(userDetails.getMemberId(), offset);
        return ResponseEntity.ok().body(response);
    }

}
