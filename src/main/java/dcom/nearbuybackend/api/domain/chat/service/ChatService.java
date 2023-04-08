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
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static dcom.nearbuybackend.api.global.websocket.WebSocketHandler.userSession;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final TokenService tokenService;
    private final ChatRepository chatRepository;
    private final PostRepository postRepository;

    public static Integer room = 1;

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

        return ChatResponseDto.ChatRoomList.of(chatRoomList, user);
    }

    public void getChatRoomListSocket(WebSocketSession session, String accessToken) {
        User user = tokenService.getUserByToken(accessToken);

        userSession.put(user.getName(),session);
    }

    public void enterChatRoom(HttpServletRequest httpServletRequest, Integer id) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 게시물이 없습니다."));

        List<String> userIdList = new ArrayList<>();
        userIdList.add(user.getId());
        userIdList.add(post.getWriter().getId());

        List<String> userNameList = new ArrayList<>();
        userNameList.add(user.getName());
        userNameList.add(post.getWriter().getName());

        Chat chat = Chat.builder()
                .room(room++)
                .post(post.getId())
                .userIdList(userIdList)
                .userNameList(userNameList)
                .message("[SYSTEM]" + user.getName() + ", " + post.getWriter().getName() + " 님이 입장하셨습니다.")
                .time(System.currentTimeMillis())
                .last(true)
                .build();

        chatRepository.save(chat);
    }

    public void sendMessage(HttpServletRequest httpServletRequest, Integer room, String message) throws IOException {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        Chat lastChat = chatRepository.findFirstByRoomOrderByTimeDesc(room);

        Chat chat = Chat.builder()
                .room(room)
                .post(lastChat.getPost())
                .sender(user.getName())
                .userIdList(lastChat.getUserIdList())
                .userNameList(lastChat.getUserNameList())
                .message(message)
                .time(System.currentTimeMillis())
                .last(true)
                .build();

        lastChat.setLast(false);

        chatRepository.save(lastChat);
        chatRepository.save(chat);

        String chatMessage = "{\"id\": \"" + chat.getId() + "\","
                + "\"room\": \"" + chat.getRoom() +"\","
                + "\"post\": \"" + chat.getPost() +"\","
                + "\"sender\": \"" + chat.getSender() +"\","
                + "\"userIdList\": \"" + chat.getUserIdList() +"\","
                + "\"userNameList\": \"" + chat.getUserNameList() +"\","
                + "\"message\": \"" + chat.getMessage() +"\","
                + "\"time\": \"" + chat.getTime() +"\","
                + "\"last\": \"" + chat.getLast() +"\"}";

        for(String receiver : lastChat.getUserNameList()) {
            if(userSession.containsKey(receiver))
                userSession.get(receiver).sendMessage(new TextMessage(chatMessage));
        }
    }

    public void exitChatRoom(HttpServletRequest httpServletRequest, Integer room) {
        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        List<Chat> chatList = chatRepository.findAllByRoomAndUser(room, user.getName());

        for(Chat c : chatList){
            List<String> userNameList = c.getUserNameList();
            userNameList.remove(user.getName());
            c.setUserNameList(userNameList);
        }

        chatRepository.saveAll(chatList);

        Chat lastChat = chatRepository.findFirstByRoomOrderByTimeDesc(room);

        Chat chat = Chat.builder()
                .room(room)
                .post(lastChat.getPost())
                .userIdList(lastChat.getUserIdList())
                .userNameList(lastChat.getUserNameList())
                .message("[SYSTEM]" + user.getName() + " 님이 퇴장하셨습니다.")
                .time(System.currentTimeMillis())
                .last(true)
                .build();

        lastChat.setLast(false);

        chatRepository.save(lastChat);
        chatRepository.save(chat);
    }
}
