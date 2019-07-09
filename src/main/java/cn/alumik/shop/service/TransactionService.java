package cn.alumik.shop.service;

import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface TransactionService {

    void save(Transaction transaction);

    Transaction getById(int id);

    Page<Transaction> findAll(int i, int i1, Sort sortObj);

    Page<Transaction> findByItem(Item item, int i, int i1, Sort sortObj);
}
