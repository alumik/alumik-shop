package cn.alumik.shop.service;

import cn.alumik.shop.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    Page<Category> findAll(String name, Pageable pageable);

    Category save(Category category);

    Optional<Category> findByName(String name);

    void deleteById(Integer id);
}
