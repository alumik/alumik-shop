package cn.alumik.shop.service;

import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdminService {

    Page<User> findAll(String username, String roleName, Pageable pageable);

    Optional<User> findById(Integer id);

    void toggleSuperAdmin(User user);

    void toggleAdmin(User user);
}
