package cn.alumik.shop.service;

import cn.alumik.shop.dao.ItemRepository;
import cn.alumik.shop.dao.TransactionRepository;
import cn.alumik.shop.dao.UserRepository;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.Transaction;
import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private SecurityService securityService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ItemRepository itemRepository, UserRepository userRepository, SecurityService securityService) {
        this.transactionRepository = transactionRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }


    @Override
    public void save(Transaction transaction) {
        transaction.setSoldAt(new Timestamp(System.currentTimeMillis()));
        Item item = transaction.getItem();
        item.setStock(item.getStock() - transaction.getAmount());
        transactionRepository.save(transaction);
        itemRepository.save(item);
    }

    @Override
    public Transaction getById(int id) {
        return transactionRepository.findById(id).get();
    }

    @Override
    public Page<Transaction> findAll(int pageNum, int pageSize, Sort sort){
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        User user = userRepository.findByUsername(securityService.findLoggedInUsername());
        return transactionRepository.findAllByBuyer(user, pageable);
    }

    @Override
    public Page<Transaction> findByItem(Item item, int pageNum, int pageSize, Sort sortObj) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, sortObj);
        return transactionRepository.findAllByItem(item, pageable);
    }
}
