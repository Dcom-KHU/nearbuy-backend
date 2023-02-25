package dcom.nearbuybackend.api.domain.post.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

public class ReportPostRequestDto {
    @ApiModel(value = "게시글 신고")
    @Getter
    @Setter
    public static class ReportPost {
        private String type;
        private String detail;
    }
}
