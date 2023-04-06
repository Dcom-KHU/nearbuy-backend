package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.FreePost;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class FreePostResponseDto {
    @ApiModel(value = "나눔 게시글 정보")
    @Builder
    @Getter
    @Setter
    public static class FreePostInfo {
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

        public static FreePostInfo of(FreePost freePost) {
            List<String> tagList = List.of(freePost.getTag().split(","));

            return FreePostInfo.builder()
                    .id(freePost.getId())
                    .type("free")
                    .title(freePost.getTitle())
                    .writer(freePost.getWriter().getName())
                    .detail(freePost.getDetail())
                    .image(freePost.getImageList())
                    .time(freePost.getTime())
                    .location(freePost.getLocation())
                    .ongoing(freePost.getOngoing())
                    .tag(tagList)
                    .build();
        }
    }
}
