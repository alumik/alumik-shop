package cn.alumik.shop.service;

import cn.alumik.shop.dao.GenderRepository;
import cn.alumik.shop.entity.Gender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {

    private GenderRepository genderRepository;

    public GenderServiceImpl(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    @Override
    public List<Gender> findAll() {
        return genderRepository.findAll();
    }

    @Override
    public List<Gender> findAllByOrderById() {
        return genderRepository.findAllByOrderById();
    }
}
