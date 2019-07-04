package cn.alumik.shop.service;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.util.List;

public interface ItemService {
    void save(Item item, File file);

    List<Item> findAll();

    Item getById(int id);

    Page<Object []> findAll(String name, int pageNum, int pageSize, Sort sort);

    List<Object []> findAllOrderByRand(String name, int size);
}
