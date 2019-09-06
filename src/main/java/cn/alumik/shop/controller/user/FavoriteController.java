package cn.alumik.shop.controller.user;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.User;
import cn.alumik.shop.service.CategoryService;
import cn.alumik.shop.service.ItemService;
import cn.alumik.shop.service.SecurityService;
import cn.alumik.shop.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller("userFavoriteController")
@RequestMapping("/user/favorite")
public class FavoriteController {

    private UserService userService;

    private SecurityService securityService;

    private ItemService itemService;

    private CategoryService categoryService;

    public FavoriteController(UserService userService, SecurityService securityService, ItemService itemService, CategoryService categoryService) {
        this.userService = userService;
        this.securityService = securityService;
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String actionIndex(
            Model model,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer categoryId,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") Integer page) {
        Sort sortObj;

        if (sort.startsWith("-")) {
            sortObj = Sort.by(sort.substring(1)).descending();
        } else {
            sortObj = Sort.by(sort);
        }
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        Page<Item> items;
        if (categoryId != 0) {
            items = itemService.findAll(user, name, categoryId, page - 1, 10, sortObj);
        } else {
            items = itemService.findAll(user, name, page - 1, 10, sortObj);
        }
        List<Category> categories = categoryService.findAll();

        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);

        return "user/favorite/index";
    }

    @GetMapping("/create")
    public String actionCreate(Integer id) {
        Optional<Item> itemOptional = itemService.findById(id);
        if (itemOptional.isPresent()) {
            User user = userService.findByUsername(securityService.findLoggedInUsername());
            user.addFavoriteItem(itemOptional.get());
            userService.save(user, false);
        }
        return "redirect:/user/favorite";
    }

    @GetMapping("/delete")
    public String actionDelete(Integer id) {
        Optional<Item> itemOptional = itemService.findById(id);
        if (itemOptional.isPresent()) {
            User user = userService.findByUsername(securityService.findLoggedInUsername());
            user.removeFavoriteItem(itemOptional.get());
            userService.save(user, false);
        }
        return "redirect:/user/favorite";
    }
}
