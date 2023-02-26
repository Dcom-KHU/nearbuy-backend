package dcom.nearbuybackend.api.domain.user.dto;

import dcom.nearbuybackend.api.domain.post.*;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.domain.user.UserReview;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

public class UserPageResponseDto {

    @ApiModel(value = "유저 페이지 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPageInfo {
        private String name;
        private Double mannerPoint;
        private String image;
        private String location;

        public static UserPageResponseDto.UserPageInfo of(User user) {
            return UserPageResponseDto.UserPageInfo.builder()
                    .name(user.getName())
                    .mannerPoint(user.getMannerPoint())
                    .image(user.getImage())
                    .location(user.getLocation())
                    .build();
        }
    }

    @ApiModel("내가 게시한 글 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPostInfo {
        List<PostInfo> post;

        public static UserPageResponseDto.MyPostInfo of(List<PostInfo> postList) {
            return MyPostInfo.builder()
                    .post(postList)
                    .build();
        }
    }

    @ApiModel("남이 게시한 글 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OthersPostInfo {
        List<PostInfo> post;

        public static OthersPostInfo of(List<PostInfo> postList) {
            return OthersPostInfo.builder()
                    .post(postList)
                    .build();
        }
    }

    @ApiModel("남이 게시한 글 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikedPostInfo {
        List<PostInfo> post;

        public static LikedPostInfo of(List<PostInfo> postList) {
            return LikedPostInfo.builder()
                    .post(postList)
                    .build();
        }
    }

    @ApiModel("게시글별 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostInfo {
        // 공통 부분
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

        // 공구 게시글
        private Integer groupPrice;
        private Integer totalPeople;
        private Integer currentPeople;

        // 판매
        public static PostInfo ofSale(SalePost salePost) {
            return PostInfo.builder()
                    .id(salePost.getId())
                    .type(salePost.getType())
                    .title(salePost.getTitle())
                    .image(salePost.getImage())
                    .location(salePost.getLocation())
                    .ongoing(salePost.getOngoing())
                    .salePrice(salePost.getSalePrice())
                    .build();
        }

        // 교환
        public static PostInfo ofExchange(ExchangePost exchangePost) {
            return PostInfo.builder()
                    .id(exchangePost.getId())
                    .type(exchangePost.getType())
                    .title(exchangePost.getTitle())
                    .image(exchangePost.getImage())
                    .location(exchangePost.getLocation())
                    .ongoing(exchangePost.getOngoing())
                    .target(exchangePost.getTarget())
                    .build();
        }

        // 나눔
        public static PostInfo ofFree(FreePost freePost) {
            return PostInfo.builder()
                    .id(freePost.getId())
                    .type(freePost.getType())
                    .title(freePost.getTitle())
                    .image(freePost.getImage())
                    .location(freePost.getLocation())
                    .ongoing(freePost.getOngoing())
                    .build();
        }

        // 경매
        public static PostInfo ofAuction(AuctionPost auctionPost) {
            return PostInfo.builder()
                    .id(auctionPost.getId())
                    .type(auctionPost.getType())
                    .title(auctionPost.getTitle())
                    .image(auctionPost.getImage())
                    .location(auctionPost.getLocation())
                    .ongoing(auctionPost.getOngoing())
                    .currentPrice(auctionPost.getCurrentPrice())
                    .deadline(auctionPost.getDeadline())
                    .build();
        }

        // 공구
        public static PostInfo ofGroup(GroupPost groupPost, Integer currentPeople) {
            return PostInfo.builder()
                    .id(groupPost.getId())
                    .type(groupPost.getType())
                    .title(groupPost.getTitle())
                    .image(groupPost.getImage())
                    .location(groupPost.getLocation())
                    .ongoing(groupPost.getOngoing())
                    .groupPrice(groupPost.getGroupPrice())
                    .totalPeople(groupPost.getTotalPeople())
                    .currentPeople(currentPeople)
                    .build();
        }

    }

    @ApiModel("거래 후기 조회")
    @Builder
    @Getter
    @Setter
    public static class UserReviewInfo {
        private String name;
        private Double mannerPoint;
        private List<ReviewInfo> review;

        public static UserReviewInfo of(User user, List<ReviewInfo> reviewList) {
            return UserReviewInfo.builder()
                    .name(user.getName())
                    .mannerPoint(user.getMannerPoint())
                    .review(reviewList)
                    .build();
        }
    }

    @ApiModel("거래 후기별 조회")
    @Builder
    @Getter
    @Setter
    public static class ReviewInfo {
        private Integer id;
        private String emotion;
        private Boolean reply;
        private Boolean location;
        private Boolean time;
        private Boolean manner;
        private String detail;

        public static ReviewInfo of(UserReview userReview) {
            return ReviewInfo.builder()
                    .id(userReview.getId())
                    .emotion(userReview.getEmotion())
                    .reply(userReview.getReply())
                    .location(userReview.getLocation())
                    .time(userReview.getTime())
                    .manner(userReview.getManner())
                    .detail(userReview.getDetail())
                    .build();
        }
    }
}
