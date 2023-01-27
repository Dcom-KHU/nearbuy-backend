package dcom.nearbuybackend.api.domain.user.repository;

import dcom.nearbuybackend.api.domain.user.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Integer> {
}
