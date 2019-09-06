package cn.alumik.shop.controller.user;

import cn.alumik.shop.entity.User;
import cn.alumik.shop.form.ChangePasswordForm;
import cn.alumik.shop.service.SecurityService;
import cn.alumik.shop.service.UserService;
import cn.alumik.shop.validator.PasswordValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("userPasswordController")
@RequestMapping("/user/password")
public class PasswordController {

    private UserService userService;

    private SecurityService securityService;

    private PasswordValidator passwordValidator;

    public PasswordController(UserService userService, SecurityService securityService, PasswordValidator passwordValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.passwordValidator = passwordValidator;
    }

    @GetMapping("/update")
    public String actionUpdate(Model model) {
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setUsername(securityService.findLoggedInUsername());

        model.addAttribute("changePasswordForm", changePasswordForm);

        return "user/password/update";
    }

    @PostMapping("/update")
    public String actionUpdate(
            @ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
            BindingResult bindingResult) {
        passwordValidator.validate(changePasswordForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "user/change-password";
        }
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        userService.setPassword(user, changePasswordForm);

        return "redirect:/user/info";
    }
}
