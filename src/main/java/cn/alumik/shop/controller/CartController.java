package cn.alumik.shop.controller;

import cn.alumik.shop.entity.Cart;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.User;
import cn.alumik.shop.service.CartService;
import cn.alumik.shop.service.ItemService;
import cn.alumik.shop.service.SecurityService;
import cn.alumik.shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;
    private ItemService itemService;
    private UserService userService;
    private SecurityService securityService;

    public CartController(CartService cartService, ItemService itemService, UserService userService, SecurityService securityService) {
        this.cartService = cartService;
        this.itemService = itemService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/index")
    public String actionGetGoods(Model model) {
        Set<Cart> carts = cartService.findAll();
        model.addAttribute("carts", carts);
        return "cart/cart";
    }

    @PostMapping("/delete")
    public String actionDeleteGood(Model model, int id) {
        cartService.delete(id);
        return "redirect:/info/";
    }

    @GetMapping("/add")
    public String actionAddtoCartGetter(Model model, int id){
        Item item = itemService.getById(id);
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItem(item);
        cart.setAmount(1);
        model.addAttribute("cart", cart);
        return "cart/add";
    }

    @PostMapping("/add")
    public String actionAddtoCartPoster(@ModelAttribute("cart") Cart cart){
        cartService.save(cart);
        return "redirect:/item/detail?id="+cart.getItem().getId();
    }

    @GetMapping("/modify")
    public String actionModifytoCartGetter(Model model, int id){
        Cart cart = cartService.getById(id);
        model.addAttribute("cart", cart);
        return "cart/modify";
    }

    @PostMapping("/modify")
    public String actionModifytoCartPoster(@ModelAttribute("cart") Cart cart){
        cartService.save(cart);
        return "redirect:/item/detail?id="+cart.getItem().getId();
    }
}
