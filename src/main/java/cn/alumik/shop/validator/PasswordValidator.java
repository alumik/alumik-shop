package cn.alumik.shop.validator;

import cn.alumik.shop.form.ChangePasswordForm;
import cn.alumik.shop.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {

    private UserService userService;

    public PasswordValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return ChangePasswordForm.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ChangePasswordForm changePasswordForm = (ChangePasswordForm) obj;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "originalPassword", "NotEmpty");
        if (!userService.passwordMatches(
                userService.findByUsername(changePasswordForm.getUsername()),
                changePasswordForm.getOriginalPassword())) {
            errors.rejectValue("originalPassword", "WrongPassword");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (changePasswordForm.getPassword().length() < 8 || changePasswordForm.getPassword().length() > 32) {
            errors.rejectValue("password", "Size");
        }
        if (!changePasswordForm.getPasswordConfirm().equals(changePasswordForm.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff");
        }
    }
}
