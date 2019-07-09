package cn.alumik.shop.dao;

import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.Transaction;
import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Page<Transaction> findAllByBuyer(User user, Pageable pageable);

    Page<Transaction> findAllByItem(Item item, Pageable pageable);
}
