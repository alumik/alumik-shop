package cn.alumik.shop.controller;

import cn.alumik.shop.entity.Item;
import cn.alumik.shop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class SiteController {

    private ItemService itemService;

    public SiteController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String actionIndexGetter(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "site/index";
    }

    @GetMapping("/about")
    public String actionAboutGetter() {
        return "site/about";
    }
}
