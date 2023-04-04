package dcom.nearbuybackend.api.global.image;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ImageRequestDto {
    @ApiModel(value = "게시글 이미지 삭제 정보")
    @Getter
    @Setter
    public static class PostImageDelete {
        @ApiModelProperty(value = "삭제할 이미지 인덱스")
        private List<Integer> index;
    }
}
