package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.FreePost;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class FreePostResponseDto {
    @ApiModel(value = "나눔 게시글 조회")
    @Builder
    @Getter
    @Setter
    public static class FreePostInfo {
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

        public static FreePostInfo of(FreePost freePost) {

            List<String> imageList = List.of(freePost.getImage().split(","));
            List<String> tagList = List.of(freePost.getTag().split(","));

            return FreePostInfo.builder()
                    .id(freePost.getId())
                    .type("free")
                    .title(freePost.getTitle())
                    .writer(freePost.getWriter().getName())
                    .detail(freePost.getDetail())
                    .image(imageList)
                    .time(freePost.getTime())
                    .location(freePost.getLocation())
                    .ongoing(freePost.getOngoing())
                    .tag(tagList)
                    .build();
        }
    }
}
