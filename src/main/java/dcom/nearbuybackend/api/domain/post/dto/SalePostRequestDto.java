package dcom.nearbuybackend.api.domain.post.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

public class SalePostRequestDto {
    @ApiModel(value = "판매 게시글 등록 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalePostRegister {
        @ApiModelProperty(value = "게시글 제목")
        private String title;
        @ApiModelProperty(value = "게시글 내용")
        private String detail;
        @ApiModelProperty(value = "게시글 위치")
        private String location;
        @ApiModelProperty(value = "게시글 태그")
        private List<String> tag;
        @ApiModelProperty(value = "[판매]게시글 가격")
        private Integer salePrice;
    }

    @ApiModel(value = "판매 게시글 수정 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalePostModify {
        @ApiModelProperty(value = "게시글 제목")
        private String title;
        @ApiModelProperty(value = "게시글 내용")
        private String detail;
        @ApiModelProperty(value = "게시글 위치")
        private String location;
        @ApiModelProperty(value = "게시글 진행여부")
        private Boolean ongoing;
        @ApiModelProperty(value = "게시글 태그")
        private List<String> tag;
        @ApiModelProperty(value = "[판매]게시글 가격")
        private Integer salePrice;
    }
}
