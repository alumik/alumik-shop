package cn.alumik.shop.controller.admin;

import cn.alumik.shop.entity.Item;
import cn.alumik.shop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller("adminItemController")
@RequestMapping("/admin/item")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("")
    public String actionIndex() {
        return "admin/item/index";
    }

    @GetMapping("/view")
    public String actionView(Model model, Integer id) {
        Optional<Item> itemOptional = itemService.findById(id);
        if (itemOptional.isPresent()) {
            model.addAttribute("item", itemOptional.get());
            return "admin/item/view";
        }
        return "redirect:/admin/item";
    }

    @GetMapping("/toggle-available")
    public String actionDisable(Integer id) {
        itemService.toggleAvailable(id);
        return "redirect:/admin/item";
    }
}
