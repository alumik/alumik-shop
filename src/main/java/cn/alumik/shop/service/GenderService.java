package cn.alumik.shop.service;

import cn.alumik.shop.entity.Gender;

import java.util.List;

public interface GenderService {

    List<Gender> findAll();

    List<Gender> findAllByOrderById();
}
