package dcom.nearbuybackend.api.domain.post.repository;

import dcom.nearbuybackend.api.domain.post.SalePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalePostRepository extends JpaRepository<SalePost, Integer> {
}
