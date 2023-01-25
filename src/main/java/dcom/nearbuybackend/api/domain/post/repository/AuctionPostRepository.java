package dcom.nearbuybackend.api.domain.post.repository;

import dcom.nearbuybackend.api.domain.post.AuctionPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionPostRepository extends JpaRepository<AuctionPost, Integer> {
}
