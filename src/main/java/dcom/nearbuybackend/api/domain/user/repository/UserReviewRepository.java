package dcom.nearbuybackend.api.domain.user.repository;

import dcom.nearbuybackend.api.domain.user.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Integer> {
}
