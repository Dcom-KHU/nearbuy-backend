package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.AuctionPostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.AuctionPostResponseDto;
import dcom.nearbuybackend.api.domain.post.service.AuctionPostService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"Auction Post Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/auction")
public class AuctionPostController {
    private final AuctionPostService auctionPostService;

    @ApiOperation(value = "경매 게시글 조회", notes = "경매 게시글 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuctionPostResponseDto.AuctionPostInfo> getAuctionPost(@ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id) {
        return ResponseEntity.ok(auctionPostService.getAuctionPost(id));
    }

    @ApiOperation(value = "경매 게시글 등록", notes = "[인증 필요] 경매 게시글 정보를 등록합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Integer>> registerAuctionPost(HttpServletRequest httpServletRequest, @ApiParam(value = "경매 게시글 등록 정보", required = true) @RequestBody AuctionPostRequestDto.AuctionPostRegister auctionPost) {
        Integer id = auctionPostService.registerAuctionPost(httpServletRequest, auctionPost);
        Map<String, Integer> data = new HashMap<>();
        data.put("postId", id);
        return ResponseEntity.ok().body(data);
    }

    @ApiOperation(value = "경매 게시글 등록", notes = "[인증 필요] 경매 게시글 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 401, message = "게시물 수정 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @PatchMapping
    public ResponseEntity<Void> modifyAuctionPost(HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id, @ApiParam(value = "경매 게시글 수정 정보", required = true) @RequestBody AuctionPostRequestDto.AuctionPostModify auctionPost) {
        auctionPostService.modifyAuctionPost(httpServletRequest, id, auctionPost);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "경매 게시글 참여자 조회", notes = "경매 게시글 참여자를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 401, message = "경매 참여자 조회 접근 권한이 없습니다."),
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @GetMapping("/participate")
    public ResponseEntity<AuctionPostResponseDto.AuctionPostPeopleInfo> getAuctionPostPeople(HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id) {
        return ResponseEntity.ok(auctionPostService.getAuctionPostPeople(httpServletRequest, id));
    }

    @ApiOperation(value = "경매 게시글 참여", notes = "경매 게시글에 참여합니다. 현재가에 가격 단위를 합친 가격으로 경매에 참여합니다.")
    @ApiResponses({
            @ApiResponse(code = 404, message = "해당하는 게시글이 없습니다.")
    })
    @PostMapping("/participate")
    public ResponseEntity<Void> joinAuctionPost(HttpServletRequest httpServletRequest, @ApiParam(value = "게시글 ID", required = true) @RequestParam Integer id) {
        auctionPostService.joinAuctionPost(httpServletRequest, id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation("경매 게시글 낙찰")
    @GetMapping("/finish")
    public ResponseEntity<Void> getSuccessAuctionPost() {
        // 아직 미구현
        return ResponseEntity.ok().build();
    }

}

