package dcom.nearbuybackend.api.domain.post.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class FreePostRequestDto {
    @ApiModel(value = "나눔 게시글 등록 정보")
    @Getter
    @Setter
    public static class FreePostRegister {
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
    }

    @ApiModel(value = "나눔 게시글 수정 정보")
    @Getter
    @Setter
    public static class FreePostModify {
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
    }
}
