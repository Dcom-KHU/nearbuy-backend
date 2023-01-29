package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.SalePost;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalePostResponseDto {
    @ApiModel(value = "판매 게시글 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalePostInfo{
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
