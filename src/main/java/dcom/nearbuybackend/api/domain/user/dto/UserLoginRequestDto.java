package dcom.nearbuybackend.api.domain.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class UserLoginRequestDto {
    @ApiModel(value = "일반 회원 가입")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserJoin {
        private String id;
        private String name;
        private String password;
        private String location;
    }

    @ApiModel(value = "일반 로그인")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLogin {
        private String id;
        private String password;
    }
}
