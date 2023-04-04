package dcom.nearbuybackend.api.domain.chat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class ChatRequestDto {

    @ApiModel("채팅 보내기")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class sendChat {
        @ApiModelProperty("채팅방 ID")
        private int room;
        @ApiModelProperty("메세지 내용")
        private String message;
    }
}
