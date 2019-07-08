package cn.alumik.shop.service;

import cn.alumik.shop.dao.RefundRequestRepository;
import cn.alumik.shop.entity.RefundRequest;
import org.springframework.stereotype.Service;

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
}
