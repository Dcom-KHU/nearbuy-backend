package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.GroupPost;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupPostResponseDto {
    @ApiModel(value = "공구 게시글 조회")
    @Builder
    @Getter
    @Setter
    public static class GroupPostInfo {
        private Integer id;
        private String type;
        private String title;
        private String writer;
        private String detail;
        private List<String> image;
        private Long time;
        private String location;
        private Boolean ongoing;
        private List<String> tag;
        private Integer groupPrice;
        private Integer totalPeople;
        private String distribute;
        private List<Long> day;

        public static GroupPostInfo of(GroupPost groupPost) {

            List<String> imageList = List.of(groupPost.getImage().split(","));
            List<String> tagList = List.of(groupPost.getTag().split(","));
            Stream<String> dayStream = Arrays.stream(groupPost.getDay().split(","));
            List<Long> dayList = dayStream.map(Long::parseLong).collect(Collectors.toList());

            return GroupPostInfo.builder()
                    .id(groupPost.getId())
                    .type("group")
                    .title(groupPost.getTitle())
                    .writer(groupPost.getWriter().getName())
                    .detail(groupPost.getDetail())
                    .image(imageList)
                    .time(groupPost.getTime())
                    .location(groupPost.getLocation())
                    .ongoing(groupPost.getOngoing())
                    .tag(tagList)
                    .groupPrice(groupPost.getGroupPrice())
                    .totalPeople(groupPost.getTotalPeople())
                    .distribute(groupPost.getDistribute())
                    .day(dayList)
                    .build();
        }
    }
}
