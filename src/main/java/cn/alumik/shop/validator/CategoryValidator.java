package cn.alumik.shop.validator;

import cn.alumik.shop.entity.Category;
import cn.alumik.shop.entity.User;
import cn.alumik.shop.service.CategoryService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {

    private CategoryService categoryService;

    public CategoryValidator(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return User.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Category category = (Category) obj;

        if (categoryService.findByName(category.getName()).isPresent()) {
            errors.rejectValue("name", "Duplicate");
        }

        if (category.getName().equals("未分类")) {
            errors.rejectValue("name", "Invalid");
        }
    }
}
