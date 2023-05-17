package com.tg.community.user.infra;

import com.tg.community.user.domain.User;
import com.tg.community.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<User, Long>, UserRepository {

    User save(User user);

    Optional<User> findById(Long userId);

    User getReferenceById(Long userId);
}
