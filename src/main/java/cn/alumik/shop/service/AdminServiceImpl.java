package cn.alumik.shop.service;

import cn.alumik.shop.dao.UserRepository;
import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> findAll(String username, String roleName, Pageable pageable) {
        return userRepository.findAllByUsernameContainingAndRoles_Name(username, roleName, pageable);
    }
}
