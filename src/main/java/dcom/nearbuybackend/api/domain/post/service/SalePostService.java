package dcom.nearbuybackend.api.domain.post.service;

import dcom.nearbuybackend.api.domain.post.SalePost;
import dcom.nearbuybackend.api.domain.post.dto.SalePostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.SalePostResponseDto;
import dcom.nearbuybackend.api.domain.post.repository.SalePostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalePostService {
    private final SalePostRepository salePostRepository;

    // 판매 게시글 조회
    public SalePostResponseDto.SalePostInfo getSalePost(Integer id) {
        SalePost salePost = salePostRepository.findById(id).get();

        return SalePostResponseDto.SalePostInfo.of(salePost);
    }

    // 판매 게시글 등록
    public void registerSalePost(SalePostRequestDto.SalePostRegister post) {
        String imageString = StringUtils.join(post.getImage(),',');
        String tagString = StringUtils.join(post.getTag(),',');

        SalePost salePost = new SalePost();

        salePost.setTitle(post.getTitle());
        // salePost.setWriter();
        salePost.setDetail(post.getDetail());
        salePost.setImage(imageString);
        salePost.setTime(System.currentTimeMillis());
        salePost.setLocation(post.getLocation());
        salePost.setOngoing(true);
        salePost.setTag(tagString);
        salePost.setSalePrice(post.getSalePrice());

        salePostRepository.save(salePost);
    }

    // 판매 게시글 수정
    public void modifySalePost(Integer id, SalePostRequestDto.SalePostModify post) {
        String imageString = StringUtils.join(post.getImage(),',');
        String tagString = StringUtils.join(post.getTag(),',');

        SalePost salePost = salePostRepository.findById(id).get();

        salePost.setTitle(post.getTitle());
        salePost.setDetail(post.getDetail());
        salePost.setImage(imageString);
        salePost.setLocation(post.getLocation());
        salePost.setOngoing(post.getOngoing());
        salePost.setTag(tagString);
        salePost.setSalePrice(post.getSalePrice());

        salePostRepository.save(salePost);
    }
}
