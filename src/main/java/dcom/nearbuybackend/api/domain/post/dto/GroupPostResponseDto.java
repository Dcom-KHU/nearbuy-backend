package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.GroupPost;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
        @ApiModelProperty(value = "게시글 ID")
        private Integer id;
        @ApiModelProperty(value = "게시글 종류")
        private String type;
        @ApiModelProperty(value = "게시글 제목")
        private String title;
        @ApiModelProperty(value = "게시글 작성자")
        private String writer;
        @ApiModelProperty(value = "게시글 내용")
        private String detail;
        @ApiModelProperty(value = "게시글 이미지")
        private List<String> image;
        @ApiModelProperty(value = "게시글 작성 시간")
        private Long time;
        @ApiModelProperty(value = "게시글 위치")
        private String location;
        @ApiModelProperty(value = "게시글 진행여부")
        private Boolean ongoing;
        @ApiModelProperty(value = "게시글 태그")
        private List<String> tag;
        @ApiModelProperty(value = "[공구] 게시글 가격")
        private Integer groupPrice;
        @ApiModelProperty(value = "[공구] 게시글 총 인원")
        private Integer totalPeople;

        @ApiModelProperty(value = "[공구] 게시글 현재 인원")
        private Integer currentPeople;
        @ApiModelProperty(value = "[공구] 게시글 공구 방식(DIRECT, POST)")
        private String distribute;
        @ApiModelProperty(value = "[공구] 게시글 공구 날짜")
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
                    .currentPeople(groupPost.getCurrentPeople())
                    .distribute(groupPost.getDistribute())
                    .day(dayList)
                    .build();
        }
    }
}
