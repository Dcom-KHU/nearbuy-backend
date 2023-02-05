package dcom.nearbuybackend.api.domain.chat.service;

import dcom.nearbuybackend.api.domain.chat.Chat;
import dcom.nearbuybackend.api.domain.chat.repository.ChatRepository;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final TokenService tokenService;

    // 유저가 속해있는 채팅방 별 채팅 목록 조회
    public Flux<Chat> getChatList(HttpServletRequest httpServletRequest, Integer room) {
        User user =  tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));
        Flux<Chat> chatList = chatRepository.findAllByRoomAndUsers(room, user.getId());
        return chatList.subscribeOn(Schedulers.boundedElastic());
    }
}
