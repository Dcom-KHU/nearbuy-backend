package dcom.nearbuybackend.api.domain.post.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

public class SalePostRequestDto {
    @ApiModel(value = "판매 게시글 등록")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalePostRegister {
        private String title;
        private String detail;
        private List<String> image;
        private String location;
        private List<String> tag;
        private Integer salePrice;
    }

    @ApiModel(value = "판매 게시글 수정")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalePostModify {
        private String title;
        private String detail;
        private List<String> image;
        private String location;
        private Boolean ongoing;
        private List<String> tag;
        private Integer salePrice;
    }
}
