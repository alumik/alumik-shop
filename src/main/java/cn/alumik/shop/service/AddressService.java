package cn.alumik.shop.service;

import cn.alumik.shop.entity.Address;

import java.util.List;

public interface AddressService {

    void save(Address address);

    void delete(Address address);

    List<Address> getAll();

    Address getById(int id);
}
