package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.GroupPostPeople;
import dcom.nearbuybackend.api.domain.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class GroupPostPeopleResponseDto {
    @ApiModel(value = "공구 게시글 참여자 정보")
    @Builder
    @Getter
    @Setter
    public static class GroupPostPeopleInfo {

        @ApiModelProperty(value = "공구 게시글 참여자 리스트")
        private List<GroupPostPersonInfo> user;

        public static GroupPostPeopleInfo of(List<GroupPostPersonInfo> groupPostPersonInfos) {

            return GroupPostPeopleInfo.builder()
                    .user(groupPostPersonInfos)
                    .build();
        }
    }

    @ApiModel(value = "공구 게시글 참여자별 정보")
    @Builder
    @Getter
    @Setter
    public static class GroupPostPersonInfo {

        @ApiModelProperty(value = "이름(닉네임)")
        private String name;
        @ApiModelProperty(value = "매너 온도")
        private Double mannerPoint;
        @ApiModelProperty(value = "참여 가능 여부")
        private Boolean participate;

        public static GroupPostPersonInfo of(GroupPostPeople groupPostPeople) {

            User user = groupPostPeople.getUser();

            return GroupPostPersonInfo.builder()
                    .name(user.getName())
                    .mannerPoint(user.getMannerPoint())
                    .participate(groupPostPeople.getParticipate())
                    .build();
        }
    }
}
