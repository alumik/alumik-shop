package cn.alumik.shop.dao;

import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    Page<User> findAllByUsernameContainingAndRoles_Name(String username, String roleName, Pageable pageable);

    Page<User> findAllByUsernameContainingAndEnabled(String username, Boolean enabled, Pageable pageable);
}
