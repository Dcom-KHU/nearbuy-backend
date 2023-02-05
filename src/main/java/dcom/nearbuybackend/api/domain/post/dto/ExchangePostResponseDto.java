package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.ExchangePost;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

public class ExchangePostResponseDto {
    @ApiModel(value = "교환 게시글 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangePostInfo {
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
        private String target;

        public static ExchangePostInfo of(ExchangePost exchangePost) {
            List<String> imageList = List.of(exchangePost.getImage().split(","));
            List<String> tagList = List.of(exchangePost.getTag().split(","));

            return ExchangePostInfo.builder()
                    .id(exchangePost.getId())
                    .type("exchange")
                    .title(exchangePost.getTitle())
                    .writer(exchangePost.getWriter().getName())
                    .detail(exchangePost.getDetail())
                    .image(imageList)
                    .time(exchangePost.getTime())
                    .location(exchangePost.getLocation())
                    .ongoing(exchangePost.getOngoing())
                    .tag(tagList)
                    .target(exchangePost.getTarget())
                    .build();
        }

    }
}
