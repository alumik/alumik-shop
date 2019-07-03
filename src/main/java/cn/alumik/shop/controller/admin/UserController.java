package cn.alumik.shop.controller.admin;

import cn.alumik.shop.entity.User;
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

import java.util.Optional;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String actionIndex(
            Model model,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "0") Integer enabled,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "30") Integer pageSize) {
        Sort sortObj;
        if (sort.startsWith("-")) {
            sortObj = Sort.by(sort.substring(1)).descending();
        } else {
            sortObj = Sort.by(sort);
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize, sortObj);
        Page<User> users;
        switch (enabled) {
            case 1:
                users = userService.findAll(username, true, pageable);
                break;
            case 2:
                users = userService.findAll(username, false, pageable);
                break;
            default:
                users = userService.findAll(username, pageable);
        }

        model.addAttribute("users", users);
        model.addAttribute("username", username);
        model.addAttribute("enabled", enabled);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);

        return "admin/user/index";
    }

    @GetMapping("/toggle-enabled")
    public String actionToggleEnabled(Integer id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEnabled(!user.isEnabled());
            userService.save(user, false);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/view")
    public String actionView(Model model, Integer id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "admin/user/view";
        }
        return "redirect:/admin/user";
    }
}
