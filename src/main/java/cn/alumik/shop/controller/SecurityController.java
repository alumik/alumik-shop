package cn.alumik.shop.controller;

import cn.alumik.shop.entity.User;
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

@Controller
@RequestMapping("/")
public class SecurityController {

    private UserService userService;

    private SecurityService securityService;

    private UserValidator userValidator;

    public SecurityController(UserService userService, SecurityService securityService, UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String actionRegistration(Model model) {
        model.addAttribute("userForm", new User());
        return "user/registration";
    }

    @PostMapping("/registration")
    public String actionRegistration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "user/registration";
        }
        userService.save(userForm, true);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String actionLogin() {
        return "user/login";
    }
}
