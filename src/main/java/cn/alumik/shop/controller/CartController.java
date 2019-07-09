package cn.alumik.shop.controller;

import cn.alumik.shop.entity.Cart;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.Transaction;
import cn.alumik.shop.entity.User;
import cn.alumik.shop.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;
    private ItemService itemService;
    private UserService userService;
    private SecurityService securityService;
    private TransactionService transactionService;

    public CartController(CartService cartService, ItemService itemService, UserService userService, SecurityService securityService, TransactionService transactionService) {
        this.cartService = cartService;
        this.itemService = itemService;
        this.userService = userService;
        this.securityService = securityService;
        this.transactionService = transactionService;
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

    @GetMapping("/checkout")
    public String actionCheckout(Model model) {
        List<Cart> carts = cartService.findAllByUser_UsernameOrderByCreatedAt(securityService.findLoggedInUsername());
        BigDecimal total = new BigDecimal(0);
        for (Cart cart : carts) {
            total = total.add(cart.getItem().getPrice().multiply(new BigDecimal(cart.getAmount())));
        }

        Checkout checkout = new Checkout();
        checkout.setCarts(carts);
        checkout.setTotal(total);

        model.addAttribute("checkout", checkout);

        return "cart/checkout";
    }

    @PostMapping("/checkout")
    public String actionCheckout(@ModelAttribute("checkout") Checkout checkout) {
        List<Cart> carts = cartService.findAllByUser_UsernameOrderByCreatedAt(securityService.findLoggedInUsername());
        String address = checkout.getAddress();
        if (address.equals("")) {
            address = checkout.getNewAddress();
        }
        for (Cart cart : carts) {
            Transaction transaction = new Transaction();
            transaction.setItem(cart.getItem());
            transaction.setBuyer(cart.getUser());
            transaction.setSinglePrice(cart.getItem().getPrice());
            transaction.setAmount(cart.getAmount());
            transaction.setAddress(address);
            transactionService.save(transaction);
        }
        cartService.deleteByUser_Username(securityService.findLoggedInUsername());
        return "redirect:/info";
    }
}

class Checkout {

    private List<Cart> carts;

    private String address;

    private String newAddress;

    private BigDecimal total;

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }
}
