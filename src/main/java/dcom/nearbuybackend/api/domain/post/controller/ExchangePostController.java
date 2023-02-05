package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.ExchangePostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.ExchangePostResponseDto;
import dcom.nearbuybackend.api.domain.post.service.ExchangePostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"Exchange Post Controller"})
@RequestMapping("/api/post/exchange")
@RestController
@RequiredArgsConstructor
public class ExchangePostController {
    private final ExchangePostService exchangePostService;

    @ApiOperation("교환 게시글 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExchangePostResponseDto.ExchangePostInfo> getExchangePost(@RequestParam Integer id) {
        return ResponseEntity.ok(exchangePostService.getExchangePost(id));
    }

    @ApiOperation("교환 게시글 등록")
    @PostMapping
    public ResponseEntity<ExchangePostRequestDto.ExchangePostRegister> registerExchangePost(HttpServletRequest httpServletRequest, @RequestBody ExchangePostRequestDto.ExchangePostRegister exchangePost){
        exchangePostService.registerExchangePost(httpServletRequest, exchangePost);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("교환 게시글 수정")
    @PatchMapping
    public ResponseEntity<ExchangePostRequestDto.ExchangePostModify> modifyExchangePost(HttpServletRequest httpServletRequest, @RequestParam Integer id, @RequestBody ExchangePostRequestDto.ExchangePostModify exchangePost) {
        exchangePostService.modifyExchangePost(httpServletRequest, id, exchangePost);
        return ResponseEntity.ok().build();
    }
}
