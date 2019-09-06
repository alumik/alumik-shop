package cn.alumik.shop.dao;

import cn.alumik.shop.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    Page<Address> findAllByNameContainsAndUser_Username(String name, String username, Pageable pageable);
}
