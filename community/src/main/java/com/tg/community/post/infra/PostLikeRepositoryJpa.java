package com.tg.community.post.infra;


import com.tg.community.post.domain.PostLike;
import com.tg.community.post.domain.PostLikeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepositoryJpa extends JpaRepository<PostLike, Long>, PostLikeRepository {

    Boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);

    PostLike save(PostLike build);
}
