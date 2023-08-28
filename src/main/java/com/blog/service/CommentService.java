package com.blog.service;

import com.blog.entity.Comment;
import com.blog.payload.CommentDto;
import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updatecomment(Long postId, CommentDto commentDto, Long commentId);

    void deleteComment(Long postId, Long commentId);
}
