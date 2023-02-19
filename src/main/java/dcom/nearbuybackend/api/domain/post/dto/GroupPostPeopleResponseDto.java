package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.GroupPostPeople;
import dcom.nearbuybackend.api.domain.user.User;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class GroupPostPeopleResponseDto {
    @ApiModel(value = "공구 게시글 참여자 조회")
    @Builder
    @Getter
    @Setter
    public static class GroupPostPeopleInfo {

        private List<GroupPostPersonInfo> user;

        public static GroupPostPeopleInfo of(List<GroupPostPersonInfo> groupPostPersonInfos) {

            return GroupPostPeopleInfo.builder()
                    .user(groupPostPersonInfos)
                    .build();
        }
    }

    @ApiModel(value = "공구 게시글 참여자별 조회")
    @Builder
    @Getter
    @Setter
    public static class GroupPostPersonInfo {

        private String name;
        private Double mannerPoint;
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
