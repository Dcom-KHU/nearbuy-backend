package dcom.nearbuybackend.api.domain.chat.service;

import dcom.nearbuybackend.api.domain.chat.Chat;
import dcom.nearbuybackend.api.domain.chat.dto.ChatResponseDto;
import dcom.nearbuybackend.api.domain.chat.repository.ChatRepository;
import dcom.nearbuybackend.api.domain.post.Post;
import dcom.nearbuybackend.api.domain.post.repository.PostRepository;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final TokenService tokenService;
    private final ChatRepository chatRepository;
    private final PostRepository postRepository;

    private static Integer room = 1;

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

    public void enterChatRoom(HttpServletRequest httpServletRequest, Integer id) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));

        List<String> userList = new ArrayList<>();
        userList.add(user.getName());
        userList.add(post.getWriter().getName());

        Chat chat = Chat.builder()
                .room(room++)
                .userList(userList)
                .message("[SYSTEM]" + user.getName() + ", " + post.getWriter().getName() + " 님이 입장하셨습니다.")
                .time(System.currentTimeMillis())
                .last(true)
                .build();

        chatRepository.save(chat);
    }

    public void sendMessage(HttpServletRequest httpServletRequest, Integer room, String message) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Chat lastChat = chatRepository.findFirstByRoomOrderByTimeDesc(room);

        Chat chat = Chat.builder()
                .room(room)
                .sender(user.getName())
                .userList(lastChat.getUserList())
                .message(message)
                .time(System.currentTimeMillis())
                .last(true)
                .build();

        lastChat.setLast(false);

        chatRepository.save(lastChat);
        chatRepository.save(chat);
    }

    public void exitChatRoom(HttpServletRequest httpServletRequest, Integer room) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        List<Chat> chatList = chatRepository.findAllByRoomAndUser(room, user.getName());

        for(Chat c : chatList){
            List<String> userList = c.getUserList();
            userList.remove(user.getName());
            c.setUserList(userList);
        }

        chatRepository.saveAll(chatList);

        Chat lastChat = chatRepository.findFirstByRoomOrderByTimeDesc(room);

        Chat chat = Chat.builder()
                .room(room)
                .userList(lastChat.getUserList())
                .message("[SYSTEM]" + user.getName() + " 님이 퇴장하셨습니다.")
                .time(System.currentTimeMillis())
                .last(true)
                .build();

        lastChat.setLast(false);

        chatRepository.save(lastChat);
        chatRepository.save(chat);
    }
}
