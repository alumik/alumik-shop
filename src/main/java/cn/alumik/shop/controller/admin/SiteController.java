package cn.alumik.shop.controller.admin;

import cn.alumik.shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminSiteController")
@RequestMapping("/admin")
public class SiteController {

    private UserService userService;

    public SiteController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String actionIndex(Model model) {
        long userCount = userService.count();
        model.addAttribute("userCount", userCount);
        return "admin/index";
    }
}
