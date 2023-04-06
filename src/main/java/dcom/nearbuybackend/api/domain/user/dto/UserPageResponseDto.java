package dcom.nearbuybackend.api.domain.user.dto;

import dcom.nearbuybackend.api.domain.post.*;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.domain.user.UserReview;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

public class UserPageResponseDto {

    @ApiModel(value = "유저 페이지 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPageInfo {
        @ApiModelProperty(value = "이름(닉네임)")
        private String name;
        @ApiModelProperty(value = "매너 온도")
        private Double mannerPoint;
        @ApiModelProperty(value = "사용자 프로필 이미지")
        private String image;
        @ApiModelProperty(value = "사용자 집 위치")
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

    @ApiModel("유저 페이지 - 내가 게시한 글 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPostInfo {
        @ApiModelProperty(value = "게시글 목록")
        List<PostInfo> post;

        public static UserPageResponseDto.MyPostInfo of(List<PostInfo> postList) {
            return MyPostInfo.builder()
                    .post(postList)
                    .build();
        }
    }

    @ApiModel("유저 페이지 - 남이 게시한 글 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OthersPostInfo {
        @ApiModelProperty(value = "게시글 목록")
        List<PostInfo> post;

        public static OthersPostInfo of(List<PostInfo> postList) {
            return OthersPostInfo.builder()
                    .post(postList)
                    .build();
        }
    }

    @ApiModel("유저 페이지 - 찜 게시글 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikedPostInfo {
        @ApiModelProperty(value = "게시글 목록")
        List<PostInfo> post;

        public static LikedPostInfo of(List<PostInfo> postList) {
            return LikedPostInfo.builder()
                    .post(postList)
                    .build();
        }
    }

    @ApiModel("게시글 타입별 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostInfo {
        // 공통 부분
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

        // 공구 게시글
        @ApiModelProperty(value = "[공구] 게시글 가격")
        private Integer groupPrice;
        @ApiModelProperty(value = "[공구] 게시글 총 인원")
        private Integer totalPeople;
        @ApiModelProperty(value = "[공구] 게시글 현재 인원")
        private Integer currentPeople;

        // 판매
        public static PostInfo ofSale(SalePost salePost) {
            return PostInfo.builder()
                    .id(salePost.getId())
                    .type(salePost.getType())
                    .title(salePost.getTitle())
                    .image(salePost.getThumbnailImage())
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
                    .image(exchangePost.getThumbnailImage())
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
                    .image(freePost.getThumbnailImage())
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
                    .image(auctionPost.getThumbnailImage())
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
                    .image(groupPost.getThumbnailImage())
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
        @ApiModelProperty(value = "이름(닉네임)")
        private String name;
        @ApiModelProperty(value = "매너 온도")
        private Double mannerPoint;
        @ApiModelProperty(value = "리뷰 목록")
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
        @ApiModelProperty(value = "리뷰 ID")
        private Integer id;
        @ApiModelProperty(value = "리뷰 감정(GOOD, SOSO, BAD)")
        private String emotion;
        @ApiModelProperty(value = "리뷰 답장 속도")
        private Boolean reply;
        @ApiModelProperty(value = "리뷰 위치 선정")
        private Boolean location;
        @ApiModelProperty(value = "리뷰 시간 준수")
        private Boolean time;
        @ApiModelProperty(value = "리뷰 거래 매너")
        private Boolean manner;
        @ApiModelProperty(value = "리뷰 세부 내용")
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
