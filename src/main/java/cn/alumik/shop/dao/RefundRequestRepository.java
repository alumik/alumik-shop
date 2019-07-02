package cn.alumik.shop.dao;

import cn.alumik.shop.entity.RefundRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRequestRepository extends JpaRepository<RefundRequest, Integer> {
}
