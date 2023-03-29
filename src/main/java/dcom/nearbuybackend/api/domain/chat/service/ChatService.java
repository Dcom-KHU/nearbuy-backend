package dcom.nearbuybackend.api.domain.chat.service;

import dcom.nearbuybackend.api.domain.chat.Chat;
import dcom.nearbuybackend.api.domain.chat.dto.ChatRequestDto;
import dcom.nearbuybackend.api.domain.chat.repository.ChatRepository;
import dcom.nearbuybackend.api.domain.post.Post;
import dcom.nearbuybackend.api.domain.post.repository.PostRepository;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final PostRepository postRepository;

    private final TokenService tokenService;
    private static Integer roomNumber;

    public Mono<Chat> enterChatRoom(HttpServletRequest httpServletRequest, Integer id) {
        User sender = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));
        User receiver = post.getWriter();
        Chat chat = new Chat();
        chat.setSender(sender.getName());
        chat.setRoom(roomNumber++);
        chat.setTime(System.currentTimeMillis());
        chat.setUsers(String.join(",", List.of(sender.getName(), receiver.getName())));
        chat.setMessage(sender.getName() + "님께서 입장하셨습니다.");
        chat.setLast(true);
        return chatRepository.save(chat);
    }

    public Mono<Chat> sendChat(HttpServletRequest httpServletRequest, ChatRequestDto.sendChat sendChat) {
        User sender = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));
        Integer room = sendChat.getRoom();
        String message = sendChat.getMessage();

        Flux<Chat> chatFlux = chatRepository.findAllByRoomAndUsers(room, sender.getId())
                .subscribeOn(Schedulers.boundedElastic());
        Stream<Chat> chatStream = chatFlux.toStream().peek(chat -> chat.setLast(false));
        chatRepository.saveAll(Flux.fromStream(chatStream));

        Chat chat = new Chat();
        chat.setRoom(room);
        chat.setMessage(message);
        chat.setSender(sender.getName());
        chat.setTime(System.currentTimeMillis());
        chat.setLast(true);
        return chatRepository.save(chat);
    }

    // 유저가 속해있는 채팅방 별 채팅 목록 조회
    public Flux<Chat> getChatList(HttpServletRequest httpServletRequest, Integer room) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));
        Flux<Chat> chatList = chatRepository.findAllByRoomAndUsers(room, user.getId());
        return chatList.subscribeOn(Schedulers.boundedElastic());
    }

    // 유저가 속해있는 채팅방 각각의 마지막 채팅 목록 조회
    public Flux<Chat> getChatRoomList(HttpServletRequest httpServletRequest) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));
        Flux<Chat> chatList = chatRepository.findAllByUsers(user.getId());
        return chatList.subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Chat> exitChatRoom(HttpServletRequest httpServletRequest, Integer room) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));
        return null;
    }
}
