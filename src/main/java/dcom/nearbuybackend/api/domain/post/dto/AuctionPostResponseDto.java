package dcom.nearbuybackend.api.domain.post.dto;

import dcom.nearbuybackend.api.domain.post.AuctionPost;
import dcom.nearbuybackend.api.domain.post.AuctionPostPeople;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

public class AuctionPostResponseDto {
    // 경매 게시글 조회
    @ApiModel("경매 게시글 조회")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionPostInfo {
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
        private Integer startPrice;
        private Integer increasePrice;
        private Integer currentPrice;
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
        private String name;
        private Double mannerPoint;
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
        private List<AuctionPeopleInfo> user;

        public static AuctionPostPeopleInfo of(List<AuctionPeopleInfo> list) {
            return AuctionPostPeopleInfo.builder()
                    .user(list)
                    .build();
        }
    }


}
