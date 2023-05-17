package com.tg.user.user.infra;

import com.tg.user.user.domain.User;
import com.tg.user.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<User, Long>, UserRepository {

    @Override
    Optional<User> findByProviderId(String providerId);

    @Override
    User save(User user);

    @Override
    Optional<User> findById(Long userId);
}
