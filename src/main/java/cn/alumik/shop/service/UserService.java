package cn.alumik.shop.service;

import cn.alumik.shop.entity.User;
import cn.alumik.shop.form.ChangePasswordForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    void save(User user, Boolean isNewUser);

    User findByUsername(String username);

    Page<User> findAll(String username, Integer enabled, Pageable pageable);

    Optional<User> findById(Integer id);

    long count();

    void setPassword(User user, ChangePasswordForm changePasswordForm);

    boolean passwordMatches(User user, String password);
}
