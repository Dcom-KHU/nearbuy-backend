package dcom.nearbuybackend.api.domain.post.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

public class AuctionPostRequestDto {
    //경매 게시글 등록
    @ApiModel("경매 게시글 등록")
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionPostRegister {
        private String title;
        private String detail;
        private List<String> image;
        private String location;
        private List<String> tag;
        private Integer startPrice;
        private Integer increasePrice;
        private Long deadline;
    }

    //경매 게시글 수정
    @ApiModel("경매 게시글 수정")
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionPostModify {
        private String title;
        private String detail;
        private List<String> image;
        private String location;
        private Boolean ongoing;
        private List<String> tag;
        private Integer increasePrice;
        private Long deadline;
    }

}
