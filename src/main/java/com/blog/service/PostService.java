package com.blog.service;

import com.blog.payload.PostDto;
import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> listAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getpostById(long id);

    PostDto updatePost(long id,PostDto postDto);

    void DeletePostById(long id);
}

