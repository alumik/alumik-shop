package cn.alumik.shop.dao;

import cn.alumik.shop.entity.Cart;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserAndItem(User user, Item item);

    List<Cart> findAllByUser_UsernameOrderByCreatedAt(String username);

    void deleteByUser_Username(String username);
}
