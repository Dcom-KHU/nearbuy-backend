package dcom.nearbuybackend.api.domain.user.repository;

import dcom.nearbuybackend.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
