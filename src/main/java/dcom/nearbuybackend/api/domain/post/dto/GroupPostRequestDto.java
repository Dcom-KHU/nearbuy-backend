package dcom.nearbuybackend.api.domain.post.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class GroupPostRequestDto {
    @ApiModel("공구 게시글 등록 정보")
    @Getter
    @Setter
    public static class GroupPostRegister {
        @ApiModelProperty(value = "게시글 제목")
        private String title;
        @ApiModelProperty(value = "게시글 내용")
        private String detail;
        @ApiModelProperty(value = "게시글 위치")
        private String location;
        @ApiModelProperty(value = "게시글 태그")
        private List<String> tag;
        @ApiModelProperty(value = "[공구] 게시글 가격")
        private Integer groupPrice;
        @ApiModelProperty(value = "[공구] 게시글 총 인원")
        private Integer totalPeople;
        @ApiModelProperty(value = "[공구] 게시글 공구 방식(DIRECT, POST)")
        private String distribute;
        @ApiModelProperty(value = "[공구] 게시글 공구 날짜")
        private List<Long> day;
    }

    @ApiModel(value = "공구 게시글 수정 정보")
    @Getter
    @Setter
    public static class GroupPostModify {
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
        @ApiModelProperty(value = "[공구] 게시글 가격")
        private Integer groupPrice;
        @ApiModelProperty(value = "[공구] 게시글 총 인원")
        private Integer totalPeople;
    }
}
