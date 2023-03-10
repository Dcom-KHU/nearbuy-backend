package dcom.nearbuybackend.api.domain.chat.controller;

import dcom.nearbuybackend.api.domain.chat.Chat;
import dcom.nearbuybackend.api.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

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

    SSE(Server-Sent Events) Protocol은 postman에서 테스트 불가, 혹시라도 테스트해보고 싶으면 ChatRepository.java의 @Tailable 주석 처리하기
     */

    private final ChatService chatService;

    @GetMapping(value = "/list" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Chat>> getChatList(HttpServletRequest httpServletRequest, @RequestParam Integer room) {
        return ResponseEntity.ok(chatService.getChatList(httpServletRequest, room));
    }

    @GetMapping(value = "/room" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Chat>> getChatRoomList(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(chatService.getChatRoomList(httpServletRequest));
    }
}
