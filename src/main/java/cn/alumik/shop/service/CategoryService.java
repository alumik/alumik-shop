package cn.alumik.shop.service;

import cn.alumik.shop.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    void add(Category category);

    void modify(Category category);

    void delete(int id);

    Optional<Category> getById(int id);
}
