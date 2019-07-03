package cn.alumik.shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminItemController")
@RequestMapping("/admin/item")
public class ItemController {

    @GetMapping("")
    public String actionIndex() {
        return "admin/item/index";
    }
}
