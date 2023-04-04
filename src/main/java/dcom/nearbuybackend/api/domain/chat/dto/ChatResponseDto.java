package dcom.nearbuybackend.api.domain.chat.dto;

import dcom.nearbuybackend.api.domain.chat.Chat;
import io.swagger.annotations.ApiModel;
import lombok.*;

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
        private String sender;
        private String receiver;
        private String message;
        private Long time;
        private Boolean last;
        private Boolean senderOut;
        private Boolean receiverOut;

        public static ChatList of(Chat chat) {
            return ChatList.builder()
                    .id(chat.getId())
                    .room(chat.getRoom())
                    .sender(chat.getSender())
                    .receiver(chat.getReceiver())
                    .message(chat.getMessage())
                    .time(chat.getTime())
                    .last(chat.getLast())
                    .senderOut(chat.getSenderIn())
                    .receiverOut(chat.getReceiverIn())
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
        private String sender;
        private String message;
        private Long time;

        public static ChatRoomList of(Chat chat) {
            return ChatRoomList.builder()
                    .id(chat.getId())
                    .room(chat.getRoom())
                    .sender(chat.getSender())
                    .message(chat.getMessage())
                    .time(chat.getTime())
                    .build();
        }

        public static List<ChatRoomList> of(List<Chat> chatRoomList) {
            return chatRoomList.stream().map(ChatRoomList::of).collect(Collectors.toList());
        }
    }
}
