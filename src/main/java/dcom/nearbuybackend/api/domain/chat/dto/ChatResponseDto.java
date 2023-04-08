package dcom.nearbuybackend.api.domain.chat.dto;

import dcom.nearbuybackend.api.domain.chat.Chat;
import dcom.nearbuybackend.api.domain.user.User;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatResponseDto {

    @ApiModel(value = "채팅 리스트")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatList {

        private String id;
        private Integer room;
        private Integer post;
        private String sender;
        private List<String> userIdList;
        private List<String> userNameList;
        private String message;
        private Long time;
        private Boolean last;

        public static ChatList of(Chat chat) {
            return ChatList.builder()
                    .id(chat.getId())
                    .room(chat.getRoom())
                    .post(chat.getPost())
                    .sender(chat.getSender())
                    .userIdList(chat.getUserIdList())
                    .userNameList(chat.getUserNameList())
                    .message(chat.getMessage())
                    .time(chat.getTime())
                    .last(chat.getLast())
                    .build();
        }

        public static List<ChatList> of(List<Chat> chatList) {
            return chatList.stream().map(ChatList::of).collect(Collectors.toList());
        }
    }

    @ApiModel(value = "채팅방 리스트")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomList {

        private String id;
        private Integer room;
        private Integer post;
        private String sender;
        private List<String> userIdList;
        private List<String> userNameList;
        private String message;
        private Long time;

        public static ChatRoomList of(Chat chat, User user) {
            List<String> otherUserIdList = new ArrayList<>();
            List<String> otherUserNameList = new ArrayList<>();

            for(String s : chat.getUserIdList()) {
                if(!s.equals(user.getId()))
                    otherUserIdList.add(s);
            }
            for(String s : chat.getUserNameList()) {
                if(!s.equals(user.getName()))
                    otherUserNameList.add(s);
            }

            return ChatRoomList.builder()
                    .id(chat.getId())
                    .room(chat.getRoom())
                    .post(chat.getPost())
                    .sender(chat.getSender())
                    .userIdList(otherUserIdList)
                    .userNameList(otherUserNameList)
                    .message(chat.getMessage())
                    .time(chat.getTime())
                    .build();
        }

        public static List<ChatRoomList> of(List<Chat> chatList, User user) {
            List<ChatRoomList> chatRoomList = new ArrayList<>();

            for(Chat c : chatList) {
                chatRoomList.add(of(c, user));
            }

            return chatRoomList;
        }
    }
}
