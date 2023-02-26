package dcom.nearbuybackend.api.domain.post.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

public class ExchangePostRequestDto {
    @ApiModel(value = "교환 게시글 등록")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangePostRegister {
        private String title;
        private String detail;
        private List<String> image;
        private String location;
        private List<String> tag;
        private String target;

    }

    @ApiModel(value = "교환 게시글 수정")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangePostModify {
        private String title;
        private String detail;
        private List<String> image;
        private String location;
        private Boolean ongoing;
        private List<String> tag;
        private String target;

    }
}
