package cn.alumik.shop.dao;

import cn.alumik.shop.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findAllByTransaction_Item_Id(Integer id, Pageable pageable);

    Page<Comment> findAllByTransaction_Buyer_UsernameContainsAndContentContains(String username, String content, Pageable pageable);

    Page<Comment> findAllByTransaction_Buyer_UsernameContainsAndContentContainsAndTransaction_Item_Id(String username, String content, Integer itemId, Pageable pageable);

    Page<Comment> findAllByTransaction_Buyer_UsernameContainsAndContentContainsAndStar(String username, String content, Integer star, Pageable pageable);

    Page<Comment> findAllByTransaction_Buyer_UsernameContainsAndContentContainsAndStarAndTransaction_Item_Id(String username, String content, Integer star, Integer itemId, Pageable pageable);
}
