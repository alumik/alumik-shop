package cn.alumik.shop.dao;

import cn.alumik.shop.entity.History;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Integer> {

    Optional<History> findByUserAndItem(User user, Item item);

    Page<History> findAllByUser_UsernameOrderByLastViewedDesc(String username, Pageable pageable);

    void deleteByUser_Username(String username);
}
