package cn.alumik.shop.service;

import cn.alumik.shop.entity.Cart;
import cn.alumik.shop.entity.Item;

import java.util.List;
import java.util.Set;


public interface CartService {
    Set<Cart> findAll();
    void delete(int id);
    void deleteAll();

    void save(Cart cart);

    Integer findExistUserItem(Item item);

    Cart getById(int id);

    List<Cart> findAllByUser_UsernameOrderByCreatedAt(String username);

    void deleteByUser_Username(String username);
}
