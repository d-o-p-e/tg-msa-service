package com.tg.user.user.domain;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByProviderId(String providerId);

    Optional<User> findById(Long userId);

    User save(User user);
}
