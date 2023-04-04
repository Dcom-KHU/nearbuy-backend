package dcom.nearbuybackend.api.domain.chat.service;

import dcom.nearbuybackend.api.domain.chat.Chat;
import dcom.nearbuybackend.api.domain.chat.dto.ChatResponseDto;
import dcom.nearbuybackend.api.domain.chat.repository.ChatRepository;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final TokenService tokenService;
    private final ChatRepository chatRepository;

    // 유저가 속해있는 채팅방 별 채팅 목록 조회
    public List<ChatResponseDto.ChatList> getChatList(HttpServletRequest httpServletRequest, Integer room) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        List<Chat> chatList = chatRepository.findAllByRoomAndUser(room, user.getName());

        return ChatResponseDto.ChatList.of(chatList);
    }

    // 유저가 속해있는 채팅방 각각의 마지막 채팅 목록 조회
    public List<ChatResponseDto.ChatRoomList> getChatRoomList(HttpServletRequest httpServletRequest) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        List<Chat> chatRoomList = chatRepository.findAllByUser(user.getName());

        return ChatResponseDto.ChatRoomList.of(chatRoomList);
    }


}
