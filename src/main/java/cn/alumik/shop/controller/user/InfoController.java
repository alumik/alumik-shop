package cn.alumik.shop.controller.user;

import cn.alumik.shop.entity.Gender;
import cn.alumik.shop.entity.User;
import cn.alumik.shop.service.GenderService;
import cn.alumik.shop.service.SecurityService;
import cn.alumik.shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("userInfoController")
@RequestMapping("/user/info")
public class InfoController {

    private SecurityService securityService;

    private UserService userService;

    private GenderService genderService;

    public InfoController(SecurityService securityService, UserService userService, GenderService genderService) {
        this.securityService = securityService;
        this.userService = userService;
        this.genderService = genderService;
    }

    @GetMapping("")
    public String actionIndex(Model model) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);

        model.addAttribute("user", user);

        return "user/info/index";
    }

    @GetMapping("/update")
    public String actionUpdate(Model model) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        List<Gender> genders = genderService.findAllByOrderById();

        model.addAttribute("user", user);
        model.addAttribute("genders", genders);

        return "user/info/update";
    }

    @PostMapping("/update")
    public String actionUpdate(@ModelAttribute("user") User newUser) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        user.getUserProfile().setPhone(newUser.getUserProfile().getPhone());
        user.getUserProfile().setEmail(newUser.getUserProfile().getEmail());
        user.getUserProfile().setDetail(newUser.getUserProfile().getDetail());
        user.getUserProfile().setGender(newUser.getUserProfile().getGender());
        userService.save(user, false);

        return "redirect:/user/info";
    }
}
