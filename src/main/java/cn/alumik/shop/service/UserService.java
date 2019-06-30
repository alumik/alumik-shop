package cn.alumik.shop.service;

import cn.alumik.shop.entity.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
