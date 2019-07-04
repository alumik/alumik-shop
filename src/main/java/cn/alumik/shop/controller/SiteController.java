package cn.alumik.shop.controller;

import cn.alumik.shop.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/")
public class SiteController {

    private ItemService itemService;

    public SiteController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String actionIndexGetter(Model model) {
        List<Object []> items = itemService.findAllOrderByRand("", 20);
        model.addAttribute("items", items);
        return "site/index";
    }

    @GetMapping("/about")
    public String actionAboutGetter() {
        return "site/about";
    }
}
