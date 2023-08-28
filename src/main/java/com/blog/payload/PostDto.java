package com.blog.payload;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
    private long id;

    // title should not be null or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 5, message = "Description should have at least 5 characters")
    private String description;
    private String content;
}