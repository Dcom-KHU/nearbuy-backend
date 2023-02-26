package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.*;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BoardResponseDto {
    @ApiModel(value = "게시판 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardInfo {
        // 페이지 번호, 한  페이지 당 게시글 갯수
        private Integer page;
        private Integer size;
        private List<BoardPostInfo> post;

        public static BoardInfo of(Pageable pageable, List<BoardPostInfo> boardPostInfos) {
            return BoardInfo.builder()
                    .page(pageable.getPageNumber())
                    .size(pageable.getPageSize())
                    .post(boardPostInfos)
                    .build();
        }
    }

    @ApiModel(value = "게시글별 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardPostInfo {
        // 게시글 공통
        private Integer id;
        private String type;
        private String title;
        private String image;
        private String location;
        private Boolean ongoing;

        // 판매 게시글
        private Integer salePrice;

        // 교환 게시글
        private String target;

        // 경매 게시글
        private Integer currentPrice;
        private Long deadline;

        // 나눔 게시글
        private Integer groupPrice;
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
