package cn.alumik.shop.controller;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/index")
    public String actionGetAllCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/index";
    }

    @GetMapping("/add")
    public String actionAddCategoriesGetter(Model model){
        Category category = new Category();
        model.addAttribute("category", category);
        return "category/add";
    }

    @PostMapping("/add")
    public String actionAddCategoriesPoster(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "category/add";
        }
        categoryService.add(category);
        return "redirect:/category/index";
    }

    @PostMapping("/delete")
    public String actionDeleteCategoriesPoster(Model model, int id){
        categoryService.delete(id);
        return "redirect:/category/index";
    }

    @GetMapping("/modify")
    public String actionModifyCategoriesGetter(Model model, int id){
        Optional<Category> gender = categoryService.getById(id);
        gender.ifPresent(new Consumer<Category>() {
            @Override
            public void accept(Category category) {
                model.addAttribute("category", category);
            }
        });
        return "category/modify";
    }

    @PostMapping("/modify")
    public String actionModifyCategoriesPoster(@Valid @ModelAttribute("category") Category gender, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println(gender);
            return "category/modify";
        }
        categoryService.modify(gender);
        return "redirect:/category/index";
    }
}
