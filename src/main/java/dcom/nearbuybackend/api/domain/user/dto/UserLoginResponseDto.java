package dcom.nearbuybackend.api.domain.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class UserLoginResponseDto {

    @ApiModel(value = "일반 로그인")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLogin {
        private String accessToken;

        public static UserLoginResponseDto.UserLogin of(String accessToken) {
            return UserLogin.builder()
                    .accessToken(accessToken)
                    .build();
        }
    }
}
