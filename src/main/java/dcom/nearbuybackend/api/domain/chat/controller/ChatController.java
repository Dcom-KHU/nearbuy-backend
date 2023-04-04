package dcom.nearbuybackend.api.domain.chat.controller;

import dcom.nearbuybackend.api.domain.chat.dto.ChatResponseDto;
import dcom.nearbuybackend.api.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/list")
    public ResponseEntity<List<ChatResponseDto.ChatList>> getChatList(HttpServletRequest httpServletRequest, @RequestParam Integer room) {
        return ResponseEntity.ok(chatService.getChatList(httpServletRequest, room));
    }

    @GetMapping("/room")
    public ResponseEntity<List<ChatResponseDto.ChatRoomList>> getChatRoomList(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(chatService.getChatRoomList(httpServletRequest));
    }

    @PostMapping("/enter")
    public ResponseEntity<Void> enterChatRoom(HttpServletRequest httpServletRequest, @RequestParam Integer id) {
        chatService.enterChatRoom(httpServletRequest, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(HttpServletRequest httpServletRequest, @RequestParam Integer room, @RequestParam String message) throws IOException {
        chatService.sendMessage(httpServletRequest, room, message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/exit")
    public ResponseEntity<Void> exitChatRoom(HttpServletRequest httpServletRequest, @RequestParam Integer room) {
        chatService.exitChatRoom(httpServletRequest, room);
        return ResponseEntity.ok().build();
    }
}
