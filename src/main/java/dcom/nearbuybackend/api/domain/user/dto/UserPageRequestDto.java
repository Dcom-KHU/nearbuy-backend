package dcom.nearbuybackend.api.domain.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class UserPageRequestDto {

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

    @ApiModel(value = "거래 후기 등록")
    @Builder
    @Getter
    @Setter
    public static class UserReviewRegister {
        private String emotion;
        private Boolean reply;
        private Boolean location;
        private Boolean time;
        private Boolean manner;
        private String detail;
    }
}
