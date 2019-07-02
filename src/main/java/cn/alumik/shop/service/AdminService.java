package cn.alumik.shop.service;

import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    Page<User> findAll(String username, String roleName, Pageable pageable);
}
