package dcom.nearbuybackend.api.global.image;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"Image Controller"})
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @ApiOperation(value = "이미지 조회", notes = "이미지를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 500, message = "URL [...] cannot be resolved in the file system for checking its content length")
    })
    @ResponseBody
    @GetMapping("/{path}/{filename}")
    public Resource showImage(@PathVariable String path, @PathVariable String filename) throws MalformedURLException {
        String absolutePath = new File("").getAbsolutePath();
        return new UrlResource("file:" + absolutePath + "/image/" + path + "/" + filename);
    }

    @ApiOperation(value = "유저 이미지 업로드", notes = "[인증 필요] 유저의 이미지를 업로드합니다. 이미 업로드한 이미지가 있다면 교체됩니다. 이미지의 타입은 jpg, png, gif로 제한됩니다.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "이미지가 비어있습니다."),
            @ApiResponse(code = 415, message = "미디어 타입을 알 수 없습니다."),
            @ApiResponse(code = 415, message = "jpg, png, gif만 업로드할 수 있습니다.")
    })
    @PostMapping("/user")
    public ResponseEntity<Void> uploadUserImage(
            HttpServletRequest httpServletRequest,
            @ApiParam(value = "유저 이미지", required = true) @RequestParam MultipartFile image
    ) throws IOException {
        imageService.uploadUserImage(httpServletRequest, image);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "유저 이미지 삭제", notes = "[인증 필요] 유저의 이미지를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당 유저는 이미지가 없습니다."),
            @ApiResponse(code = 404, message = "해당 유저의 이미지 경로가 잘못되었습니다.")
    })
    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUserImage(HttpServletRequest httpServletRequest) {
        imageService.deleteUserImage(httpServletRequest);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "포스트 이미지 업로드", notes = "[인증 필요] 포스트의 이미지를 업로드합니다. 이미지의 타입은 jpg, png, gif로 제한됩니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당 id의 게시글을 찾을 수 없습니다."),
            @ApiResponse(code = 401, message = "게시물 접근 권한이 없습니다."),
            @ApiResponse(code = 400, message = "이미지가 비어있습니다."),
            @ApiResponse(code = 415, message = "미디어 타입을 알 수 없습니다."),
            @ApiResponse(code = 415, message = "jpg, png, gif만 업로드할 수 있습니다.")
    })
    @PostMapping("/post")
    public ResponseEntity<Map<String, Integer>> uploadPostImage(
            HttpServletRequest httpServletRequest,
            @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id,
            @ApiParam(value = "포스트 이미지", required = true) @RequestParam List<MultipartFile> image
    ) throws IOException {
        imageService.uploadPostImage(httpServletRequest, id, image);
        Map<String, Integer> data = new HashMap<>();
        data.put("postId", id);
        return ResponseEntity.ok().body(data);
    }

    @ApiOperation(value = "포스트 이미지 삭제", notes = "[인증 필요] 포스의 이미지를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당 id의 게시글을 찾을 수 없습니다."),
            @ApiResponse(code = 401, message = "게시물 접근 권한이 없습니다."),
            @ApiResponse(code = 400, message = "index가 범위를 벗어났습니다."),
            @ApiResponse(code = 404, message = "저장된 이미지 경로가 잘못되었습니다.")
    })
    @DeleteMapping("/post")
    public ResponseEntity<Void> deletePostImage(
            HttpServletRequest httpServletRequest,
            @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id,
            @ApiParam(value = "삭제할 이미지의 index", required = true) @RequestBody ImageRequestDto.PostImageDelete body
    ) {
        imageService.deletePostImage(httpServletRequest, id, body.getIndex());
        return ResponseEntity.ok().build();

    }
}
