package cn.alumik.shop.controller.user;

import cn.alumik.shop.entity.User;
import cn.alumik.shop.service.SecurityService;
import cn.alumik.shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("userInfoController")
@RequestMapping("/user/info")
public class InfoController {

    private SecurityService securityService;

    private UserService userService;

    public InfoController(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    @GetMapping("")
    public String actionIndex(Model model) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);

        model.addAttribute("user", user);

        return "user/info/index";
    }
}
