package cn.alumik.shop.controller.admin;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.service.CategoryService;
import cn.alumik.shop.validator.CategoryValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller("adminCategoryController")
@RequestMapping("/admin/item/category")
public class CategoryController {

    private CategoryService categoryService;

    private CategoryValidator categoryValidator;

    public CategoryController(CategoryService categoryService, CategoryValidator categoryValidator) {
        this.categoryService = categoryService;
        this.categoryValidator = categoryValidator;
    }

    @GetMapping("")
    public String actionIndex(
            Model model,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") Integer page) {
        Sort sortObj;
        if (sort.startsWith("-")) {
            sortObj = Sort.by(sort.substring(1)).descending();
        } else {
            sortObj = Sort.by(sort);
        }

        Pageable pageable = PageRequest.of(page - 1, 30, sortObj);
        Page<Category> categories = categoryService.findAll(name, pageable);

        model.addAttribute("categories", categories);
        model.addAttribute("name", name);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);

        return "admin/item/category/index";
    }

    @GetMapping("/create")
    public String actionCreate(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin/item/category/create";
    }

    @PostMapping("/create")
    public String actionCreate(
            @Valid @ModelAttribute("category") Category category,
            BindingResult bindingResult) {
        categoryValidator.validate(category, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/item/category/create";
        }
        categoryService.save(category);
        return "redirect:/admin/item/category";
    }

    @GetMapping("/update")
    public String actionUpdate(Model model, Integer id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isPresent()) {
            model.addAttribute("category", categoryOptional.get());
            return "admin/item/category/update";
        }
        return "redirect:/admin/item/category";
    }

    @PostMapping("/update")
    public String actionUpdate(
            @Valid @ModelAttribute("category") Category category,
            BindingResult bindingResult) {
        categoryValidator.validate(category, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/item/category/update";
        }
        categoryService.save(category);
        return "redirect:/admin/item/category";
    }

    @GetMapping("/delete")
    public String actionDelete(Integer id) {
        categoryService.deleteById(id);
        return "redirect:/admin/item/category";
    }
}
