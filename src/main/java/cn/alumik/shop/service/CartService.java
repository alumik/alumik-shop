package cn.alumik.shop.service;

import cn.alumik.shop.entity.Cart;

import java.util.Set;


public interface CartService {
    Set<Cart> findAll();
}
