package cn.alumik.shop.service;

import cn.alumik.shop.entity.RefundRequest;
import cn.alumik.shop.entity.Transaction;

import java.util.List;

public interface RefundRequestService {
    void save(RefundRequest refundRequest);

    RefundRequest getById(int id);

    List<RefundRequest> findAllByTransaction(Transaction transaction);
}
