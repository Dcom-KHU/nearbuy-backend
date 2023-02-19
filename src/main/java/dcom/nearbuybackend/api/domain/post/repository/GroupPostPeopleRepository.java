package dcom.nearbuybackend.api.domain.post.repository;

import dcom.nearbuybackend.api.domain.post.GroupPostPeople;
import dcom.nearbuybackend.api.domain.post.Post;
import dcom.nearbuybackend.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupPostPeopleRepository extends JpaRepository<GroupPostPeople, Integer> {
    Optional<GroupPostPeople> findByPostAndUser(Post post, User user);

    List<GroupPostPeople> findByPost(Post post);
}
