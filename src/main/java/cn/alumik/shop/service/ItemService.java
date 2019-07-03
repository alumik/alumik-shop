package cn.alumik.shop.service;

import cn.alumik.shop.entity.Item;

import java.util.List;

public interface ItemService {
    void save(Item item);

    List<Item> findAll();

    Item getById(int id);
}
