package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.ExchangePostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.ExchangePostResponseDto;
import dcom.nearbuybackend.api.domain.post.service.ExchangePostService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"Exchange Post Controller"})
@RequestMapping("/api/post/exchange")
@RestController
@RequiredArgsConstructor
public class ExchangePostController {
    private final ExchangePostService exchangePostService;

    @ApiOperation(value = "교환 게시글 조회", notes = "교환 게시글 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExchangePostResponseDto.ExchangePostInfo> getExchangePost(@ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id) {
        return ResponseEntity.ok(exchangePostService.getExchangePost(id));
    }

    @ApiOperation(value = "교환 게시글 등록", notes = "[인증 필요] 교환 게시글 정보를 등록합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Integer>> registerExchangePost(HttpServletRequest httpServletRequest, @ApiParam(value = "교환 게시글 등록 정보", required = true) @RequestBody ExchangePostRequestDto.ExchangePostRegister exchangePost){
        Integer id = exchangePostService.registerExchangePost(httpServletRequest, exchangePost);
        Map<String, Integer> data = new HashMap<>();
        data.put("postId", id);
        return ResponseEntity.ok().body(data);
    }

    @ApiOperation(value = "교환 게시글 수정", notes = "[인증 필요] 교환 게시글 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 401, message = "게시물 수정 접근 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @PatchMapping
    public ResponseEntity<Void> modifyExchangePost(HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id, @ApiParam(value = "교환 게시글 수정 정보", required = true) @RequestBody ExchangePostRequestDto.ExchangePostModify exchangePost) {
        exchangePostService.modifyExchangePost(httpServletRequest, id, exchangePost);
        return ResponseEntity.ok().build();
    }
}
