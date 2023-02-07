package dcom.nearbuybackend.api.domain.post.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class FreePostRequestDto {
    @ApiModel(value = "나눔 게시글 등록")
    @Getter
    @Setter
    public static class FreePostRegister {
        private String title;
        private String detail;
        private List<String> image;
        private String location;
        private List<String> tag;
    }

    @ApiModel(value = "나눔 게시글 수정")
    @Getter
    @Setter
    public static class FreePostModify {
        private String title;
        private String detail;
        private List<String> image;
        private String location;
        private Boolean ongoing;
        private List<String> tag;
    }
}
