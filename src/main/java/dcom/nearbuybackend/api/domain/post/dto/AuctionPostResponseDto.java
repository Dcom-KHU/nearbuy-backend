package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.AuctionPost;
import dcom.nearbuybackend.api.domain.post.AuctionPostPeople;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

public class AuctionPostResponseDto {
    // 경매 게시글 조회
    @ApiModel("경매 게시글 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionPostInfo {
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
         @ApiModelProperty(value = "[경매] 게시글 시작 가격")
        private Integer startPrice;
        @ApiModelProperty(value = "[경매] 게시글 가격 단위")
        private Integer increasePrice;
        @ApiModelProperty(value = "[경매] 게시글 현재 가격")
        private Integer currentPrice;
        @ApiModelProperty(value = "[경매] 게시글 종료 시간")
        private Long deadline;
    }

    public static AuctionPostInfo of(AuctionPost auctionPost) {
        List<String> imageList = List.of(auctionPost.getImage().split(","));
        List<String> tagList = List.of(auctionPost.getTag().split(","));

        return AuctionPostInfo.builder()
                .id(auctionPost.getId())
                .type("auction")
                .title(auctionPost.getTitle())
                .writer(auctionPost.getWriter().getName())
                .detail(auctionPost.getDetail())
                .image(imageList)
                .time(auctionPost.getTime())
                .location(auctionPost.getLocation())
                .ongoing(auctionPost.getOngoing())
                .tag(tagList)
                .startPrice(auctionPost.getStartPrice())
                .increasePrice(auctionPost.getIncreasePrice())
                .currentPrice(auctionPost.getCurrentPrice())
                .deadline(auctionPost.getDeadline())
                .build();
    }

    @ApiModel("경매 참여자별 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionPeopleInfo {
        @ApiModelProperty(value = "이름(닉네임)")
        private String name;
        @ApiModelProperty(value = "매너 온도")
        private Double mannerPoint;
        @ApiModelProperty(value = "[경매] 게시글 참여 가격")
        private Integer auctionPrice;

        public static AuctionPeopleInfo of(AuctionPostPeople auctionPostPeople) {
            return AuctionPeopleInfo.builder()
                    .name(auctionPostPeople.getUser().getName())
                    .mannerPoint(auctionPostPeople.getUser().getMannerPoint())
                    .auctionPrice(auctionPostPeople.getAuctionPrice())
                    .build();
        }

    }


    @ApiModel("경매 게시글 참여자 조회")
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionPostPeopleInfo {
        @ApiModelProperty(value = "[경매] 게시글 참여자 리스트")
        private List<AuctionPeopleInfo> user;

        public static AuctionPostPeopleInfo of(List<AuctionPeopleInfo> list) {
            return AuctionPostPeopleInfo.builder()
                    .user(list)
                    .build();
        }
    }
}