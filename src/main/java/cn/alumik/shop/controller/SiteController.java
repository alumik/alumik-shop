package cn.alumik.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SiteController {

    @GetMapping("/")
    public String actionIndex() {
        return "site/index";
    }

    @GetMapping("/about")
    public String actionAbout() {
        return "site/about";
    }

    @GetMapping("/login")
    public String actionLogin() {
        return "site/login";
    }
}
