package cn.alumik.shop.service;

import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.Transaction;
import cn.alumik.shop.entity.User;

public interface TransactionService {

    void save(Transaction transaction, Item item);
}
