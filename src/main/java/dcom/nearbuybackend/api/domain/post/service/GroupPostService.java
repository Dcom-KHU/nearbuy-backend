package dcom.nearbuybackend.api.domain.post.service;

import dcom.nearbuybackend.api.domain.post.GroupPost;
import dcom.nearbuybackend.api.domain.post.dto.GroupPostRequestDto;
import dcom.nearbuybackend.api.domain.post.dto.GroupPostResponseDto;
import dcom.nearbuybackend.api.domain.post.repository.GroupPostRepository;
import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.global.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupPostService {

    private final GroupPostRepository groupPostRepository;
    private final TokenService tokenService;

    /**
     * 공구 게시글 조회
     */
    public GroupPostResponseDto.GroupPostInfo getGroupPost(Integer id) {
        GroupPost groupPost = groupPostRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 게시글을 찾을 수 없습니다.")
        );

        return GroupPostResponseDto.GroupPostInfo.of(groupPost);
    }

    /**
     * 공구 게시글 등록
     */
    public void registerGroupPost(HttpServletRequest httpServletRequest, GroupPostRequestDto.GroupPostRegister post) {

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        GroupPost groupPost = new GroupPost();
        groupPost.setTitle(post.getTitle());
        groupPost.setWriter(user);
        groupPost.setDetail(post.getDetail());
        groupPost.setImage(getJoinByComma(post.getImage()));
        groupPost.setTime(System.currentTimeMillis());
        groupPost.setLocation(post.getLocation());
        groupPost.setOngoing(true);
        groupPost.setTag(getJoinByComma(post.getTag()));
        groupPost.setGroupPrice(post.getGroupPrice());
        groupPost.setTotalPeople(post.getTotalPeople());
        groupPost.setDistribute(post.getDistribute());
        groupPost.setDay(post.getDay().stream().map(l -> Long.toString(l)).collect(Collectors.joining(",")));

        groupPostRepository.save(groupPost);
    }

    private static String getJoinByComma(List<String> list) {
        return String.join(",", list);
    }

    /**
     * 공구 게시글 수정
     */
    public void modifyGroupPost(HttpServletRequest httpServletRequest, Integer id,
                                GroupPostRequestDto.GroupPostModify post) {

        User user = tokenService.getUserByToken(tokenService.resolveToken(httpServletRequest));

        GroupPost groupPost = groupPostRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 게시글을 찾을 수 없습니다."));

        if (user.equals(groupPost.getWriter())) {
            String imageList = getJoinByComma(post.getImage());
            String tagList = getJoinByComma(post.getTag());

            groupPost.setTitle(post.getTitle());
            groupPost.setDetail(post.getDetail());
            groupPost.setImage(imageList);
            groupPost.setLocation(post.getLocation());
            groupPost.setOngoing(post.getOngoing());
            groupPost.setTag(tagList);
            groupPost.setGroupPrice(post.getGroupPrice());
            groupPost.setTotalPeople(post.getTotalPeople());

            groupPostRepository.save(groupPost);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "게시물 수정 접근 권한이 없습니다.");
        }
    }

}
