package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BoardResponseDto {
    @ApiModel(value = "게시판 조회 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardInfo {
        // 페이지 번호, 한  페이지 당 게시글 갯수
        
        @ApiModelProperty(value = "페이지 No.")
        private Integer page;
        @ApiModelProperty(value = "한 페이지 당 게시글 개수")
        private Integer size;
        @ApiModelProperty(value = "게시글 리스트")
        private List<BoardPostInfo> post;

        public static BoardInfo of(Pageable pageable, List<BoardPostInfo> boardPostInfos) {
            return BoardInfo.builder()
                    .page(pageable.getPageNumber())
                    .size(pageable.getPageSize())
                    .post(boardPostInfos)
                    .build();
        }
    }

    @ApiModel(value = "타입별 게시글 조회 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardPostInfo {
        // 게시글 공통
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
        @ApiModelProperty(value = "게시글 진행여부")
        private Boolean ongoing;

        // 판매 게시글
        @ApiModelProperty(value = "[판매]게시글 가격")
        private Integer salePrice;

        // 교환 게시글
        @ApiModelProperty(value = "[교환]게시글 희망 물품")
        private String target;

        // 경매 게시글
        @ApiModelProperty(value = "[경매] 게시글 현재 가격")
        private Integer currentPrice;
        @ApiModelProperty(value = "[경매] 게시글 종료 시간")
        private Long deadline;

        // 나눔 게시글
        @ApiModelProperty(value = "[공구] 게시글 가격")
        private Integer groupPrice;
        @ApiModelProperty(value = "[공구] 게시글 총 인원")
        private Integer totalPeople;


        // 판매 게시글
        public static BoardPostInfo ofSale(SalePost salePost) {
            return BoardPostInfo.builder()
                    .id(salePost.getId())
                    .type(salePost.getType())
                    .title(salePost.getTitle())
                    .image(salePost.getImage().split(",")[0])
                    .location(salePost.getLocation())
                    .ongoing(salePost.getOngoing())
                    .salePrice(salePost.getSalePrice())
                    .build();
        }

        // 교환 게시글
        public static BoardPostInfo ofExchange(ExchangePost exchangePost) {
            return BoardPostInfo.builder()
                    .id(exchangePost.getId())
                    .type(exchangePost.getType())
                    .title(exchangePost.getTitle())
                    .image(exchangePost.getImage().split(",")[0])
                    .location(exchangePost.getLocation())
                    .ongoing(exchangePost.getOngoing())
                    .target(exchangePost.getTarget())
                    .build();
        }

        // 나눔 게시글
        public static BoardPostInfo ofFree(FreePost freePost) {
            return BoardPostInfo.builder()
                    .id(freePost.getId())
                    .type(freePost.getType())
                    .title(freePost.getTitle())
                    .image(freePost.getImage().split(",")[0])
                    .location(freePost.getLocation())
                    .ongoing(freePost.getOngoing())
                    .build();
        }

        // 경매 게시글
        public static BoardPostInfo ofAuction(AuctionPost auctionPost) {
            return BoardPostInfo.builder()
                    .id(auctionPost.getId())
                    .type(auctionPost.getType())
                    .title(auctionPost.getTitle())
                    .image(auctionPost.getImage().split(",")[0])
                    .location(auctionPost.getLocation())
                    .ongoing(auctionPost.getOngoing())
                    .currentPrice(auctionPost.getCurrentPrice())
                    .deadline(auctionPost.getDeadline())
                    .build();
        }

        // 공구 게시글
        public static BoardPostInfo ofGroup(GroupPost groupPost) {
            return BoardPostInfo.builder()
                    .id(groupPost.getId())
                    .type(groupPost.getType())
                    .title(groupPost.getTitle())
                    .image(groupPost.getImage().split(",")[0])
                    .location(groupPost.getLocation())
                    .ongoing(groupPost.getOngoing())
                    .groupPrice(groupPost.getGroupPrice())
                    .totalPeople(groupPost.getTotalPeople())
                    .build();
        }
    }
}
