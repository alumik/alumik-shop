package cn.alumik.shop.service;

import cn.alumik.shop.dao.CommentRepository;
import cn.alumik.shop.dao.TransactionRepository;
import cn.alumik.shop.entity.Comment;
import cn.alumik.shop.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Page<Comment> findAll(int id, int pageNum, int pageSize, Sort sort){
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return commentRepository.findAllByNameContainsSell(id, pageable);
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
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }
}
