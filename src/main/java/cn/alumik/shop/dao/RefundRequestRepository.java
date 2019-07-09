package cn.alumik.shop.dao;

import cn.alumik.shop.entity.RefundRequest;
import cn.alumik.shop.entity.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRequestRepository extends JpaRepository<RefundRequest, Integer> {
    List<RefundRequest> findAllByTransactionOrderByCreatedAtDesc(Transaction transaction);
}
