package cn.alumik.shop.controller.admin;

import cn.alumik.shop.entity.Role;
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
            @RequestParam(defaultValue = "true") Boolean enabled,
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
        Page<User> users = userService.findAll(username, enabled, pageable);

        model.addAttribute("users", users);
        model.addAttribute("username", username);
        model.addAttribute("enabled", enabled);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);

        return "admin/user/index";
    }
}
