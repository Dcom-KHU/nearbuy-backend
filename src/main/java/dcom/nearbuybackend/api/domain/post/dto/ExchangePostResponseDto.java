package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.ExchangePost;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

public class ExchangePostResponseDto {
    @ApiModel(value = "교환 게시글 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangePostInfo {
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
        @ApiModelProperty(value = "[교환]게시글 희망 물품")
        private String target;

        public static ExchangePostInfo of(ExchangePost exchangePost) {
            List<String> tagList = List.of(exchangePost.getTag().split(","));

            return ExchangePostInfo.builder()
                    .id(exchangePost.getId())
                    .type("exchange")
                    .title(exchangePost.getTitle())
                    .writer(exchangePost.getWriter().getName())
                    .detail(exchangePost.getDetail())
                    .image(exchangePost.getImageList())
                    .time(exchangePost.getTime())
                    .location(exchangePost.getLocation())
                    .ongoing(exchangePost.getOngoing())
                    .tag(tagList)
                    .target(exchangePost.getTarget())
                    .build();
        }
    }
}
