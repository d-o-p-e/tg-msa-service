package com.tg.community.post.infra;

import com.tg.community.post.domain.FeedOption;
import com.tg.community.post.domain.dto.FeedResponseDto;

import java.util.List;

public interface PostRepositoryCustom {
    List<FeedResponseDto> getFeed(FeedOption feedOption);
}
