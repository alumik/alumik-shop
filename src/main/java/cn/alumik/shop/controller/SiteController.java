package cn.alumik.shop.controller;

import cn.alumik.shop.entity.User;
import cn.alumik.shop.service.ItemService;
import cn.alumik.shop.service.SecurityService;
import cn.alumik.shop.service.UserService;
import cn.alumik.shop.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class SiteController {

    private ItemService itemService;

    private UserValidator userValidator;

    private UserService userService;

    private SecurityService securityService;

    public SiteController(ItemService itemService, UserValidator userValidator, UserService userService, SecurityService securityService) {
        this.itemService = itemService;
        this.userValidator = userValidator;
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/")
    public String actionIndexGetter(Model model) {
        List<Object[]> items = itemService.findAllOrderByRand("", 20);
        model.addAttribute("items", items);
        return "site/index";
    }

    @GetMapping("/about")
    public String actionAboutGetter() {
        return "site/about";
    }

    @GetMapping("/registration")
    public String actionRegistration(Model model) {
        model.addAttribute("userForm", new User());
        return "site/registration";
    }

    @PostMapping("/registration")
    public String actionRegistration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "site/registration";
        }
        userService.save(userForm, true);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String actionLogin() {
        return "site/login";
    }
}
