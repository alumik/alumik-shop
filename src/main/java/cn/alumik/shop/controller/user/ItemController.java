package cn.alumik.shop.controller.user;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.User;
import cn.alumik.shop.service.CategoryService;
import cn.alumik.shop.service.ItemService;
import cn.alumik.shop.service.SecurityService;
import cn.alumik.shop.service.UserService;
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

@Controller("userItemController")
@RequestMapping("/user/item")
public class ItemController {

    private ItemService itemService;

    private UserService userService;

    private SecurityService securityService;

    private CategoryService categoryService;

    public ItemController(ItemService itemService, UserService userService, SecurityService securityService, CategoryService categoryService) {
        this.itemService = itemService;
        this.userService = userService;
        this.securityService = securityService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String actionIndex(
            Model model,
            @RequestParam(defaultValue = "0") Integer categoryId,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer status,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") Integer page) {
        Sort sortObj;
        if (sort.startsWith("-")) {
            sortObj = Sort.by(sort.substring(1)).descending();
        } else {
            sortObj = Sort.by(sort);
        }

        Pageable pageable = PageRequest.of(page - 1, 30, sortObj);
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        Page<Item> items = itemService.findAll(user, categoryId, name, status, pageable);
        List<Category> categories = categoryService.findAll();

        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("name", name);
        model.addAttribute("status", status);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);

        return "user/item/index";
    }
}
