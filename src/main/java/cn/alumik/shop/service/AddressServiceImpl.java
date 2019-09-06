package cn.alumik.shop.service;

import cn.alumik.shop.dao.AddressRepository;
import cn.alumik.shop.dao.UserRepository;
import cn.alumik.shop.entity.Address;
import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;
    private SecurityService securityService;
    private UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository,
                              SecurityService securityService,
                              UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    @Override
    public void save(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void delete(Address address) {
        User user = userRepository.findByUsername(securityService.findLoggedInUsername());
        address.setUser(user);
        addressRepository.delete(address);
    }

    @Override
    public List<Address> getAll() {
        User user = userRepository.findByUsername(securityService.findLoggedInUsername());
        return new ArrayList<>(user.getAddresses());
    }

    @Override
    public Address getById(int id) {
        return addressRepository.findById(id).get();
    }

    @Override
    public Page<Address> findAllByNameContainsAndUser_Username(String name, String username, Pageable pageable) {
        return addressRepository.findAllByNameContainsAndUser_Username(name, username, pageable);
    }

    @Override
    public void deleteById(Integer id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Optional<Address> findById(Integer id) {
        return addressRepository.findById(id);
    }
}
