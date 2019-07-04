package cn.alumik.shop.service;

import cn.alumik.shop.entity.Item;

import java.io.File;
import java.util.List;

public interface ItemService {
    void save(Item item, File file);

    List<Item> findAll();

    Item getById(int id);
}
