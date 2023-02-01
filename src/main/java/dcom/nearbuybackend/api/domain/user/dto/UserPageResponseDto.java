package dcom.nearbuybackend.api.domain.user.dto;

import dcom.nearbuybackend.api.domain.user.User;
import io.swagger.annotations.ApiModel;
import lombok.*;

public class UserPageResponseDto {

    @ApiModel(value = "유저 페이지 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPageInfo {
        private String name;
        private Double mannerPoint;
        private String image;
        private String location;

        public static UserPageResponseDto.UserPageInfo of(User user) {
            return UserPageResponseDto.UserPageInfo.builder()
                    .name(user.getName())
                    .mannerPoint(user.getMannerPoint())
                    .image(user.getImage())
                    .location(user.getLocation())
                    .build();
        }
    }
}
