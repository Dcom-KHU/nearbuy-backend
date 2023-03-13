package dcom.nearbuybackend.api.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class UserLoginRequestDto {
    @ApiModel(value = "일반 회원가입 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserJoin {
        @ApiModelProperty(value = "아이디(이메일)")
        private String id;
        @ApiModelProperty(value = "이름(닉네임)")
        private String name;
        @ApiModelProperty(value = "비밀번호")
        private String password;
        @ApiModelProperty(value = "사용자 집 위치")
        private String location;
    }

    @ApiModel(value = "일반 로그인 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLogin {
        @ApiModelProperty(value = "아이디(이메일)")
        private String id;
        @ApiModelProperty(value = "비밀번호")
        private String password;
    }

    @ApiModel(value = "비밀번호 찾기 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserFindPassword {
        @ApiModelProperty(value = "아이디(이메일)")
        private String id;
        @ApiModelProperty(value = "이름(닉네임)")
        private String name;
    }

}
