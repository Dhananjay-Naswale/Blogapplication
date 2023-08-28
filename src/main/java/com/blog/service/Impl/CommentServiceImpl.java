package com.blog.service.Impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.xml.bind.v2.schemagen.episode.SchemaBindings;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl<commentDto> implements CommentService {



    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        //post is there or not
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("POST NOT FOUND WITH ID: " + postId)
        );
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newcomment = commentRepository.save(comment);
        CommentDto dto = mapToDTO(newcomment);

        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //post is there or not
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("POST NOT FOUND WITH ID: " + postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }
    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("POST NOT FOUND WITH ID: "+postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not Found with Id: "+commentId)
               );
        return mapToDTO(comment);
    }
    @Override
    public CommentDto updatecomment(Long postId, CommentDto commentDto, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post Not Found With Id:  "+postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment Not Found With Id: "+commentId)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatecomment = commentRepository.save(comment);

        return mapToDTO(updatecomment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post Not Found With Id:  "+postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment Not Found With Id: "+commentId)
        );
        commentRepository.deleteById(commentId);
    }

    private CommentDto mapToDTO(Comment comment){

      CommentDto commentDto  = modelMapper.map(comment,CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto){
    Comment comment = modelMapper.map(commentDto,Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}

