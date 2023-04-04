package dcom.nearbuybackend.api.domain.chat.controller;

import dcom.nearbuybackend.api.domain.chat.dto.ChatResponseDto;
import dcom.nearbuybackend.api.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
}
