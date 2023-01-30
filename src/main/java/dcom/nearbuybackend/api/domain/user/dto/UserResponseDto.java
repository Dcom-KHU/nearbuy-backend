package dcom.nearbuybackend.api.domain.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class UserResponseDto {

    @ApiModel(value = "일반 로그인")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLogin {
        private String accessToken;

        public static UserResponseDto.UserLogin of(String accessToken) {
            return UserLogin.builder()
                    .accessToken(accessToken)
                    .build();
        }
    }
}
