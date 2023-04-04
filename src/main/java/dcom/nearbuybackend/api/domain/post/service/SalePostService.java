package dcom.nearbuybackend.api.domain.post.service;

import dcom.nearbuybackend.api.domain.post.SalePost;
import dcom.nearbuybackend.api.domain.post.dto.SalePostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.SalePostResponseDto;
import dcom.nearbuybackend.api.domain.post.repository.SalePostRepository;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class SalePostService {
    private final SalePostRepository salePostRepository;

    private final TokenService tokenService;

    // 판매 게시글 조회
    public SalePostResponseDto.SalePostInfo getSalePost(Integer id) {
        SalePost salePost = salePostRepository.findById(id).get();

        return SalePostResponseDto.SalePostInfo.of(salePost);
    }

    // 판매 게시글 등록
    public Integer registerSalePost(HttpServletRequest httpServletRequest, SalePostRequestDto.SalePostRegister post) {
        User user =  tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        String tagString = StringUtils.join(post.getTag(),',');

        SalePost salePost = new SalePost();

        salePost.setType("sale");
        salePost.setTitle(post.getTitle());
        salePost.setWriter(user);
        salePost.setDetail(post.getDetail());
        salePost.setTime(System.currentTimeMillis());
        salePost.setLocation(post.getLocation());
        salePost.setOngoing(true);
        salePost.setTag(tagString);
        salePost.setSalePrice(post.getSalePrice());

        return salePostRepository.save(salePost).getId();
    }

    // 판매 게시글 수정
    public void modifySalePost(HttpServletRequest httpServletRequest, Integer id, SalePostRequestDto.SalePostModify post) {
        User user =  tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        String tagString = StringUtils.join(post.getTag(),',');

        SalePost salePost = salePostRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"해당하는 게시물이 없습니다."));

        if(user.equals(salePost.getWriter())) {
            salePost.setTitle(post.getTitle());
            salePost.setDetail(post.getDetail());
            salePost.setLocation(post.getLocation());
            salePost.setOngoing(post.getOngoing());
            salePost.setTag(tagString);
            salePost.setSalePrice(post.getSalePrice());

            salePostRepository.save(salePost);
        }
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"게시물 수정 접근 권한이 없습니다.");
    }
}
