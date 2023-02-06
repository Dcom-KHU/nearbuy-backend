package dcom.nearbuybackend.api.domain.post.controller;

import dcom.nearbuybackend.api.domain.post.dto.AuctionPostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.AuctionPostResponseDto;
import dcom.nearbuybackend.api.domain.post.service.AuctionPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"Auction Post Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/auction")
public class AuctionPostController {
    private final AuctionPostService auctionPostService;

    @ApiOperation("경매 게시글 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuctionPostResponseDto.AuctionPostInfo> getAuctionPost(@RequestParam Integer id) {
        return ResponseEntity.ok(auctionPostService.getAuctionPost(id));
    }

    @ApiOperation("경매 게시글 등록")
    @PostMapping
    public ResponseEntity<AuctionPostRequestDto.AuctionPostRegister> registerAuctionPost(HttpServletRequest httpServletRequest, @RequestBody AuctionPostRequestDto.AuctionPostRegister auctionPost) {
        auctionPostService.registerAuctionPost(httpServletRequest, auctionPost);

        return ResponseEntity.ok().build();
    }

    @ApiOperation("경매 게시글 수정")
    @PatchMapping
    public ResponseEntity<AuctionPostRequestDto.AuctionPostModify> modifyAuctionPost(HttpServletRequest httpServletRequest, @RequestParam Integer id, @RequestBody AuctionPostRequestDto.AuctionPostModify auctionPost) {
        auctionPostService.modifyAuctionPost(httpServletRequest, id, auctionPost);

        return ResponseEntity.ok().build();
    }


}
