package dcom.nearbuybackend.api.domain.post.repository;

import dcom.nearbuybackend.api.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Post, Integer>   {

    // type 상관 없이 모든 게시글 조회
    Page<Post> findAll(Pageable pageable);

    // type별 게시글 조회
    @Query("select p from Post p where p.type = :type")
    List<Post> findAllByType(String type, Pageable pageable);
}
