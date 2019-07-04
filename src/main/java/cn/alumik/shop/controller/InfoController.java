package cn.alumik.shop.controller;

import cn.alumik.shop.entity.Gender;
import cn.alumik.shop.entity.User;
import cn.alumik.shop.service.GenderService;
import cn.alumik.shop.service.SecurityService;
import cn.alumik.shop.service.UserService;
import cn.alumik.shop.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/info")
public class InfoController {

    private SecurityService securityService;

    private UserService userService;

    private GenderService genderService;


    public InfoController(SecurityService securityService, UserService userService, GenderService genderService) {
        this.securityService = securityService;
        this.userService = userService;
        this.genderService = genderService;
    }

    @GetMapping("/")
    public String actionInfoGetter(Model model) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        List<Gender> genders = genderService.findAll();
        model.addAttribute("genders", genders);
        return "user/info";
    }

    @GetMapping("/modify")
    public String actionModifyGetter(Model model) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        List<Gender> genders = genderService.findAll();
        model.addAttribute("genders", genders);
        return "user/modifyInfo";
    }

    @PostMapping("/modify")
    public String actionModify(@ModelAttribute("userForm") User userForm) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        user.getUserProfile().setPhone(userForm.getUserProfile().getPhone());
        user.getUserProfile().setEmail(userForm.getUserProfile().getEmail());
        user.getUserProfile().setDetail(userForm.getUserProfile().getDetail());
        user.getUserProfile().setGender(userForm.getUserProfile().getGender());
        userService.save(user, false);
        return "redirect:/info/";
    }
}
