package cn.alumik.shop.service;

import cn.alumik.shop.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryService {

    void saveHistory(Integer id);

    Page<History> findAllByUser_UsernameOrderByLastViewedDesc(String username, Pageable pageable);

    void deleteByUser_Username(String username);
}
