package dcom.nearbuybackend.api.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class UserPageRequestDto {

    @ApiModel(value = "유저 페이지 수정 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPageModify {
        @ApiModelProperty(value = "이름(닉네임)")
        private String name;
        @ApiModelProperty(value = "사용자 프로필 이미지")
        private String image;
        @ApiModelProperty(value = "사용자 집 위치")
        private String location;
    }

    @ApiModel(value = "유저 비밀번호 변경 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserChangePassword {
        @ApiModelProperty(value = "기존 비밀번호")
        private String password;
        @ApiModelProperty(value = "새로운 비밀번호")
        private String newPassword;
        @ApiModelProperty(value = "새로운 비밀번호 확인")
        private String newPasswordCheck;
    }

    @ApiModel(value = "거래 후기 정보")
    @Builder
    @Getter
    @Setter
    public static class UserReviewRegister {
        @ApiModelProperty(value = "감정(GOOD, SOSO, BAD)")
        private String emotion;
        @ApiModelProperty(value = "답장 속도")
        private Boolean reply;
        @ApiModelProperty(value = "위치 선정")
        private Boolean location;
        @ApiModelProperty(value = "시간 준수")
        private Boolean time;
        @ApiModelProperty(value = "거래 매너")
        private Boolean manner;
        @ApiModelProperty(value = "세부 내용")
        private String detail;
    }
}
