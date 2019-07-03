package cn.alumik.shop.service;

import cn.alumik.shop.dao.CartRepository;
import cn.alumik.shop.entity.Cart;
import cn.alumik.shop.entity.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private SecurityService securityService;
    private UserService userService;

    public CartServiceImpl(CartRepository cartRepository, SecurityService securityService, UserService userService) {
        this.cartRepository = cartRepository;
        this.securityService = securityService;
        this.userService = userService;
    }

    @Override
    public Set<Cart> findAll() {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        return user.getCarts();
    }

    @Override
    public void delete(int id) {
        cartRepository.deleteById(id);
    }
}
