package dcom.nearbuybackend.api.domain.chat.controller;

import dcom.nearbuybackend.api.domain.chat.Chat;
import dcom.nearbuybackend.api.domain.chat.dto.ChatRequestDto;
import dcom.nearbuybackend.api.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    /*
    사전 mongoDB 설정 필요! mongoDB console창에 다음과 같이 입력:
    use nearbuy
    db.createCollection("chat")
    db.runCommand({convertToCapped: "chat", size: 8192})
     */

    private final ChatService chatService;

    @PostMapping(value = "/enter")
    public ResponseEntity<Mono<Chat>> enterChatRoom(HttpServletRequest httpServletRequest, @RequestParam Integer id) {
        return ResponseEntity.ok(chatService.enterChatRoom(httpServletRequest, id));
    }

    @PostMapping(value = "/send")
    public ResponseEntity<Mono<Chat>> sendChat(HttpServletRequest httpServletRequest, @RequestBody ChatRequestDto.sendChat sendChat) {
        return ResponseEntity.ok(chatService.sendChat(httpServletRequest, sendChat));
    }

    @PostMapping(value = "/exit")
    public ResponseEntity<Mono<Chat>> exitChatRoom(HttpServletRequest httpServletRequest, @RequestParam Integer room) {
        return ResponseEntity.ok(chatService.exitChatRoom(httpServletRequest, room));
    }

    @GetMapping(value = "/list" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Chat>> getChatList(HttpServletRequest httpServletRequest, @RequestParam Integer room) {
        return ResponseEntity.ok(chatService.getChatList(httpServletRequest, room));
    }

    @GetMapping(value = "/room" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Chat>> getChatRoomList(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(chatService.getChatRoomList(httpServletRequest));
    }
}
