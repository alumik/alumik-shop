package cn.alumik.shop.service;

import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.Transaction;
import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface TransactionService {

    void save(Transaction transaction, Item item);

    Transaction getById(int id);

    Page<Transaction> findAll(int i, int i1, Sort sortObj);
}
