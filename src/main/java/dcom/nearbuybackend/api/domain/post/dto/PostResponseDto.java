package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class PostResponseDto {

    @ApiModel("게시글 요약 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostSummary {
        @ApiModelProperty(value = "게시글 ID")
        private Integer id;
        @ApiModelProperty(value = "게시글 종류")
        private String type;
        @ApiModelProperty(value = "게시글 제목")
        private String title;
        @ApiModelProperty(value = "게시글 이미지")
        private String image;
        @ApiModelProperty(value = "게시글 위치")
        private String location;

        public static PostSummary of(Post post) {
            return PostSummary.builder()
                    .id(post.getId())
                    .type(post.getType())
                    .title(post.getTitle())
                    .image(post.getThumbnailImage())
                    .location(post.getLocation())
                    .build();
        }
    }
}
