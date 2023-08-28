package com.blog.repository;

import com.blog.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(long id); //findBy - method , PostId-- Coloumn in comment return List<Comment>

}