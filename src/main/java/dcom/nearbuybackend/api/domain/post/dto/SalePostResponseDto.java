package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.SalePost;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalePostResponseDto {
    @ApiModel(value = "판매 게시글 조회 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalePostInfo{
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
        @ApiModelProperty(value = "[판매]게시글 가격")
        private Integer salePrice;

        public static SalePostInfo of(SalePost salePost) {
            List<String> imageList = new ArrayList(Arrays.asList(salePost.getImage().split(",")));
            List<String> tagList = new ArrayList(Arrays.asList(salePost.getTag().split(",")));

            return SalePostInfo.builder()
                    .id(salePost.getId())
                    .type("sale")
                    .title(salePost.getTitle())
                    .writer(salePost.getWriter().getName())
                    .detail(salePost.getDetail())
                    .image(imageList)
                    .time(salePost.getTime())
                    .location(salePost.getLocation())
                    .ongoing(salePost.getOngoing())
                    .tag(tagList)
                    .salePrice(salePost.getSalePrice())
                    .build();
        }
    }
}
