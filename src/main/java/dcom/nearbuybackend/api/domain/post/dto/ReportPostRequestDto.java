package dcom.nearbuybackend.api.domain.post.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class ReportPostRequestDto {
    @ApiModel(value = "게시글 신고 정보")
    @Getter
    @Setter
    public static class ReportPost {

        @ApiModelProperty(value = "게시글 신고 유형(BAD MANNER, FRAUD, SEXUAL HARASSMENT")
        private String type;
        @ApiModelProperty(value = "게시글 신고 내용")
        private String detail;
    }
}
