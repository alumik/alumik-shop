package cn.alumik.shop.service;

import cn.alumik.shop.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    void save(Address address);

    void delete(Address address);

    List<Address> getAll();

    Address getById(int id);

    Page<Address> findAllByNameContainsAndUser_Username(String name, String username, Pageable pageable);

    void deleteById(Integer id);

    Optional<Address> findById(Integer id);
}
