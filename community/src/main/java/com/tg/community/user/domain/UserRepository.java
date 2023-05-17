package com.tg.community.user.domain;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long userId);

    User getReferenceById(Long userId);
}
