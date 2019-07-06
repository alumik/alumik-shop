package cn.alumik.shop.service;

import cn.alumik.shop.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface CommentService {
    Page<Comment> findAll(int id, int i, int i1, Sort sortObj);

    void save(Comment comment);

    Comment getById(int id);

    void deleteById(Integer id);

    Page<Comment> findAll(String username, Integer itemId, Integer star, String content, Pageable pageable);
}
