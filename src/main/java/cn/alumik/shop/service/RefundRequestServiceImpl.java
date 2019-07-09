package cn.alumik.shop.service;

import cn.alumik.shop.dao.RefundRequestRepository;
import cn.alumik.shop.entity.RefundRequest;
import cn.alumik.shop.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefundRequestServiceImpl implements RefundRequestService {

    private RefundRequestRepository refundRequestRepository;

    public RefundRequestServiceImpl(RefundRequestRepository refundRequestRepository) {
        this.refundRequestRepository = refundRequestRepository;
    }

    @Override
    public void save(RefundRequest refundRequest) {
        refundRequestRepository.save(refundRequest);
    }

    @Override
    public RefundRequest getById(int id) {
        return refundRequestRepository.findById(id).get();
    }

    @Override
    public List<RefundRequest> findAllByTransaction(Transaction transaction) {
        return refundRequestRepository.findAllByTransactionOrderByCreatedAtDesc(transaction);
    }
}
