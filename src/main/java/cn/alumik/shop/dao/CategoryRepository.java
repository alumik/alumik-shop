package cn.alumik.shop.dao;

import cn.alumik.shop.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findAllByNameContains(String name, Pageable pageable);

    Optional<Category> findByName(String name);
}
