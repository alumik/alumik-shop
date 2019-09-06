package cn.alumik.shop.service;

import cn.alumik.shop.dao.CommentRepository;
import cn.alumik.shop.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Page<Comment> findAll(int id, int pageNum, int pageSize, Sort sort) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return commentRepository.findAllByTransaction_Item_Id(id, pageable);
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment getById(int id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Page<Comment> findAll(String username, Integer itemId, String itemName, Integer star, String content, Pageable pageable) {
        if (star == 0) {
            if (itemId == 0) {
                return commentRepository.findAllByTransaction_Buyer_UsernameContainsAndContentContainsAndTransaction_Item_NameContains(username, content, itemName, pageable);
            }
            return commentRepository.findAllByTransaction_Buyer_UsernameContainsAndContentContainsAndTransaction_Item_IdAndTransaction_Item_NameContains(username, content, itemId, itemName, pageable);
        }
        if (itemId == 0) {
            return commentRepository.findAllByTransaction_Buyer_UsernameContainsAndContentContainsAndStarAndTransaction_Item_NameContains(username, content, star, itemName, pageable);
        }
        return commentRepository.findAllByTransaction_Buyer_UsernameContainsAndContentContainsAndStarAndTransaction_Item_IdAndTransaction_Item_NameContains(username, content, star, itemId, itemName, pageable);
    }

    @Override
    public Optional<Comment> findById(Integer id) {
        return commentRepository.findById(id);
    }

    @Override
    public Page<Comment> findAllByTransaction_Item_Id(Integer id, Pageable pageable) {
        return commentRepository.findAllByTransaction_Item_Id(id, pageable);
    }
}
