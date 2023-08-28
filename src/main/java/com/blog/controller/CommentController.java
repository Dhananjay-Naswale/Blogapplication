package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:8080/api/posts/2/comments----post
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
   // http://localhost:8080/api/posts/2/comments ----GET  2
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentByPostId(@PathVariable(value="postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }
    //http://localhost:8080/api/posts/2/comments/1     -----GET  3
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value="postId") Long postId,
                                                     @PathVariable(value="id")Long CommentId){
        CommentDto commentDto = commentService.getCommentById(postId, CommentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/{postId}/comments/{id}
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value="postId") Long postId,
                                                    @RequestBody CommentDto commentDto,
                                                    @PathVariable(value="id") Long CommentId)
    {
        CommentDto dto = commentService.updatecomment(postId, commentDto, CommentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/{postId}/comments/{id}
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value ="postId") Long postId,
            @PathVariable(value ="id") Long commentId)
    {
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment is deleted",HttpStatus.OK);
    }
}
