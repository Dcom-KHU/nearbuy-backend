package dcom.nearbuybackend.api.domain.post.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class GroupPostRequestDto {
    @ApiModel(value = "공구 게시글 등록")
    @Getter
    @Setter
    public static class GroupPostRegister {
        private String title;
        private String detail;
        private List<String> image;
        private String location;
        private List<String> tag;
        private Integer groupPrice;
        private Integer totalPeople;
        private String distribute;
        private List<Long> day;
    }

    @ApiModel(value = "공구 게시글 수정")
    @Getter
    @Setter
    public static class GroupPostModify {
        private String title;
        private String detail;
        private List<String> image;
        private String location;
        private Boolean ongoing;
        private List<String> tag;
        private Integer groupPrice;
        private Integer totalPeople;
    }
}
