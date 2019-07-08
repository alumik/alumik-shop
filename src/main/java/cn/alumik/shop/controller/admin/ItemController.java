package cn.alumik.shop.controller.admin;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.service.CategoryService;
import cn.alumik.shop.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller("adminItemController")
@RequestMapping("/admin/item")
public class ItemController {

    private ItemService itemService;

    private CategoryService categoryService;

    public ItemController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String actionIndex(
            Model model,
            @RequestParam(defaultValue = "0") Integer categoryId,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String sellerName,
            @RequestParam(defaultValue = "-1") Integer available,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") Integer page) {
        Sort sortObj;
        if (sort.startsWith("-")) {
            sortObj = Sort.by(sort.substring(1)).descending();
        } else {
            sortObj = Sort.by(sort);
        }

        Pageable pageable = PageRequest.of(page - 1, 30, sortObj);
        Page<Item> items = itemService.findAll(categoryId, name, sellerName, available, pageable);
        List<Category> categories = categoryService.findAll();

        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("name", name);
        model.addAttribute("sellerName", sellerName);
        model.addAttribute("available", available);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);

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
