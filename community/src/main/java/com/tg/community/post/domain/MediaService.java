package com.tg.community.post.domain;

import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    String uploadFile(MultipartFile multipartFile, String dirName);

    void deleteFile(String fileName);
}
