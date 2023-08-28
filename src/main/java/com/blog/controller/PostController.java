package com.blog.controller;

import com.blog.payload.PostDto;

import com.blog.service.PostService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService)
      //<--DependencyInjection can be performed in two ways 1>.@Autowired.2>.Constructor Based Injection.
    {

        this.postService = postService;
    }

    //http://localhost:8080/api/posts         ----1
    @PreAuthorize("hasRole('ADMIN')")    //----Spring Security ADMIN Can Access.
    @PostMapping
    public ResponseEntity<?> createPost(@Valid  @RequestBody PostDto postDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
      PostDto dto =  postService.createPost(postDto);
      //return dto as response
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=desc
    @GetMapping                 //------------2
    public List<PostDto> listAllPosts(
    @RequestParam(value="pageNo",defaultValue = "0",required = false)int pageNo,
    @RequestParam(value="pageSize",defaultValue = "10",required = false)int pageSize,
    @RequestParam(value="sortBy",defaultValue = "id",required = false)String sortBy,
    @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir
    ){

        List<PostDto> postDtos = postService.listAllPosts(pageNo,pageSize,sortBy,sortDir);
        return postDtos;
    }

    //http://localhost:8080/api/posts
    @GetMapping("/{id}")   //----------------------   3
    public ResponseEntity<PostDto> getPostById(@PathVariable ("id") long id){
        PostDto dto = postService.getpostById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")        // -------------- 4
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto,
            @PathVariable("id") long id
    ) {
        PostDto dto = postService.updatePost(id,postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeletepostById(@PathVariable("id") long id){
        postService.DeletePostById(id);
        return new ResponseEntity<>("Post is Deleted",HttpStatus.OK);
    }
}
