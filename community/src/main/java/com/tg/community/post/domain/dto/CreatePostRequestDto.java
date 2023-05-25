package com.tg.community.post.domain.dto;

import com.tg.community.post.domain.PostCategory;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class CreatePostRequestDto {
    private String content;
    private PostCategory category;
    private MultipartFile image;
}
