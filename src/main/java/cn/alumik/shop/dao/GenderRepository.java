package cn.alumik.shop.dao;

import cn.alumik.shop.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenderRepository extends JpaRepository<Gender, Integer> {
    Optional<Gender> findByName(String name);

    List<Gender> findAllByOrderById();
}
