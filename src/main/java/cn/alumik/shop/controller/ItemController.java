package cn.alumik.shop.controller;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.service.CategoryService;
import cn.alumik.shop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;
    private CategoryService categoryService;

    public ItemController(ItemService itemService, CategoryService categoryService){
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public String actionAddGetter(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "item/add";
    }

    @PostMapping("/add")
    public String actionAddPoster(@Valid @ModelAttribute("item") Item item, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult);
            return "item/add";
        }
        itemService.save(item);
        return "redirect:/";
    }
}
