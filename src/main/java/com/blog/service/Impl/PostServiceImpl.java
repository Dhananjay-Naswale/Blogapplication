package com.blog.service.Impl;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    //Constructor based Dependency ---->
    private ModelMapper modelMapper;
    public PostServiceImpl(PostRepository postRepository) {

        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert into entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        //post save into it
        Post newPost = postRepository.save(post);

        //response back to postman
        PostDto dto = new PostDto();
        dto.setId(newPost.getId());
        dto.setTitle(newPost.getTitle());
        dto.setContent(newPost.getContent());
        dto.setDescription(newPost.getDescription());
        return dto;
    }
    @Override
    public List<PostDto> listAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){
        //if-else is replace by ternary operator
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        //Sort sort = Sort.by(sortBy);

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Post> listofPosts = postRepository.findAll(pageable);
        //convert page return type into the list.
        List<Post> posts = listofPosts.getContent(); //error of maptoDto(post)

        //Stream Api
        List<PostDto> postDtos = posts.stream().map(post -> maptoDto(post)).collect(Collectors.toList());
        //convert into postDtos
        return postDtos;
    }
    @Override
    public PostDto getpostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + id)
        );

        return maptoDto(post);
    }
    @Override
    public PostDto updatePost(long id, PostDto postDto) {   //-------------------4
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + id)
        );
        Post newpost = maptoEntity(postDto);
        newpost.setId(id);
        //save and //convert newpost to dto
        Post updatedPost = postRepository.save(newpost);
        PostDto dto = maptoDto(updatedPost);
        return dto;
    }

    @Override
    public void DeletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + id)
        );
        postRepository.deleteById(id);
    }
    //stream API-->
    PostDto maptoDto(Post post){                 //----------------- 2 --- 3
        PostDto dto = modelMapper.map(post, PostDto.class);     //mapper

//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setContent(post.getContent());
//        dto.setDescription(post.getDescription());
        return dto;
 }
    Post maptoEntity(PostDto postDto){        // ------------ 4
        Post post = modelMapper.map(postDto, Post.class);            // mapper

//        Post post = new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
        return post;
    }

}
