package cn.alumik.shop.service;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.User;
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

    Page<Object []> findAll(String name, int categoryId, int pageNum, int pageSize, Sort sort);

    List<Object []> findAllOrderByRand(String name, int size);

    Page<Item> findAllBySeller(int i, int i1, Sort sortObj);

    Optional<Item> findById(Integer id);

    void toggleAvailable(Integer id);

    Page<Item> findAll(Integer categoryId, String name, String sellerName, Integer available, Pageable pageable);

    void save(Item item);

    Page<Item> findAllByFavoriteBy(int i, int i1, Sort sortObj);

    void addFavoriteItem(Item item);

    void delFavoriteItem(Item item);

    Page<Item> findAll(User user, String name, Integer categoryId, int pageNum, int pageSize, Sort sort);

    Page<Item> findAll(User user, String name, int pageNum, int pageSize, Sort sort);
}
