package cn.alumik.shop.controller;

import cn.alumik.shop.entity.Item;
import cn.alumik.shop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
        List<List<Item> > results = new ArrayList<>();
        for (int i = 0; i < Math.ceil(items.size() / 4.0); ++i){
            results.add(new ArrayList<>());
            for (int j = 0; j < 4; ++j){
                if (i * 4 + j < items.size()) {
                    results.get(i).add(items.get(i * 4 + j));
                }
            }
        }
        model.addAttribute("items", results);
        return "site/index";
    }

    @GetMapping("/about")
    public String actionAboutGetter() {
        return "site/about";
    }
}
