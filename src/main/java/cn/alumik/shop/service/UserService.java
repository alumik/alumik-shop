package cn.alumik.shop.service;

import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    void save(User user, Boolean isNewUser);

    User findByUsername(String username);

    Page<User> findAll(String username, Boolean enabled, Pageable pageable);

    Optional<User> findById(Integer id);

    Page<User> findAll(String username, Pageable pageable);
}
