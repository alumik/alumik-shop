package cn.alumik.shop.service;

import cn.alumik.shop.dao.AddressRepository;
import cn.alumik.shop.dao.UserRepository;
import cn.alumik.shop.entity.Address;
import cn.alumik.shop.entity.User;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findByUsername(securityService.findLoggedInUsername());
        address.setUser(user);
        addressRepository.save(address);
    }
}
