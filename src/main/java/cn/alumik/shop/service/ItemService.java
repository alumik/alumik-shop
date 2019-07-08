package cn.alumik.shop.service;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface ItemService {
    void save(Item item, String filename);

    List<Item> findAll();

    Item getById(int id);

    Page<Object []> findAll(String name, int pageNum, int pageSize, Sort sort);

    List<Object []> findAllOrderByRand(String name, int size);

    Page<Item> findAllBySeller(int i, int i1, Sort sortObj);

    Optional<Item> findById(Integer id);
}
