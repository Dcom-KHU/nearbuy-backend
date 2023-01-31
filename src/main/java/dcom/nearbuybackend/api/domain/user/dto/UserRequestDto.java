package dcom.nearbuybackend.api.domain.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class UserRequestDto {

    @ApiModel(value = "유저 페이지 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPageModify {
        private String name;
        private String image;
        private String location;
    }

    @ApiModel(value = "유저 비밀번호 변경")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserChangePassword {
        private String password;
        private String newPassword;
        private String newPasswordCheck;
    }
}
