package com.tg.community.post.infra;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tg.community.post.domain.FeedOption;
import com.tg.community.post.domain.dto.FeedResponseDto;
import com.tg.community.post.domain.dto.QFeedResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tg.community.post.domain.QPost.post;
import static com.tg.community.user.domain.QUser.user;

@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<FeedResponseDto> getFeed(FeedOption feedOption) {
        return queryFactory
                .select(new QFeedResponseDto(
                        post.id,
                        post.content,
                        post.imageUrl,
                        post.category,
                        post.createdAt,
                        post.updatedAt,
                        user.id,
                        user.nickname,
                        user.profileImageUrl,
                        post.likeList.size(),
                        post.commentList.size(),
                        Expressions.asBoolean(false),
                        Expressions.asBoolean(false)
                ))
                .from(post)
                .join(post.user, user)
                .where(
                        cursorPagination(feedOption.getLastPostId()),
                        userPage(feedOption.getTargetUserId())
                )
                .orderBy(post.id.desc())
                .limit(feedOption.getSize())
                .fetch();
    }

    private Predicate cursorPagination(Long lastPostId) {
        if (lastPostId == null) {
            return null;
        }
        return post.id.lt(lastPostId);
    }

    private Predicate userPage(Long targetUserId) {
        if(targetUserId == null) {
            return null;
        }
        return post.user.id.eq(targetUserId);
    }
}
