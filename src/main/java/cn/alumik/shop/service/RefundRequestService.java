package cn.alumik.shop.service;

import cn.alumik.shop.entity.RefundRequest;

public interface RefundRequestService {
    void save(RefundRequest refundRequest);

    RefundRequest getById(int id);
}
