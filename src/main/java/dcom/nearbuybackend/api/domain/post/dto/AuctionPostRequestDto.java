package dcom.nearbuybackend.api.domain.post.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

public class AuctionPostRequestDto {
    //경매 게시글 등록
    @ApiModel("경매 게시글 등록 정보")
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionPostRegister {
        @ApiModelProperty(value = "게시글 제목")
        private String title;
        @ApiModelProperty(value = "게시글 내용")
        private String detail;
        @ApiModelProperty(value = "게시글 이미지")
        private List<String> image;
        @ApiModelProperty(value = "게시글 위치")
        private String location;
        @ApiModelProperty(value = "게시글 태그")
        private List<String> tag;
        @ApiModelProperty(value = "[경매] 게시글 시작 가격")
        private Integer startPrice;
        @ApiModelProperty(value = "[경매] 게시글 가격 단위")
        private Integer increasePrice;
        @ApiModelProperty(value = "[경매] 게시글 종료 시간")
        private Long deadline;
    }

    //경매 게시글 수정
    @ApiModel("경매 게시글 수정 정보")
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionPostModify {
        @ApiModelProperty(value = "게시글 제목")
        private String title;
        @ApiModelProperty(value = "게시글 내용")
        private String detail;
        @ApiModelProperty(value = "게시글 이미지")
        private List<String> image;
        @ApiModelProperty(value = "게시글 위치")
        private String location;
        @ApiModelProperty(value = "게시글 진행여부")
        private Boolean ongoing;
        @ApiModelProperty(value = "게시글 태그")
        private List<String> tag;
        @ApiModelProperty(value = "[경매] 게시글 가격 단위")
        private Integer increasePrice;
         @ApiModelProperty(value = "[경매] 게시글 종료 시간")
        private Long deadline;
    }
}
