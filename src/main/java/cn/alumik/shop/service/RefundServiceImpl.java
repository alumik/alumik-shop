package cn.alumik.shop.service;

import cn.alumik.shop.dao.RefundRepository;
import cn.alumik.shop.entity.Refund;
import org.springframework.stereotype.Service;

@Service
public class RefundServiceImpl implements RefundService {
    private RefundRepository refundRepository;

    public RefundServiceImpl(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

    @Override
    public void save(Refund refund) {
        refundRepository.save(refund);
    }
}
