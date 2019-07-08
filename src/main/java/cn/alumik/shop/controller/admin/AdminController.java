package cn.alumik.shop.controller.admin;

import cn.alumik.shop.entity.User;
import cn.alumik.shop.service.AdminService;
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

@Controller("adminAdminController")
@RequestMapping("/admin/admin")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("")
    public String actionIndex(
            Model model,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "0") Boolean isSuperAdmin,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") Integer page) {
        Sort sortObj;
        if (sort.startsWith("-")) {
            sortObj = Sort.by(sort.substring(1)).descending();
        } else {
            sortObj = Sort.by(sort);
        }

        String roleName;
        if (isSuperAdmin) {
            roleName = "ROLE_SUPER_ADMIN";
        } else {
            roleName = "ROLE_ADMIN";
        }

        Pageable pageable = PageRequest.of(page - 1, 30, sortObj);
        Page<User> admins = adminService.findAll(username, roleName, pageable);

        model.addAttribute("admins", admins);
        model.addAttribute("username", username);
        model.addAttribute("isSuperAdmin", isSuperAdmin);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);

        return "admin/admin/index";
    }

    @GetMapping("/create")
    public String actionCreate(
            Model model,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") Integer page) {
        Sort sortObj;
        if (sort.startsWith("-")) {
            sortObj = Sort.by(sort.substring(1)).descending();
        } else {
            sortObj = Sort.by(sort);
        }

        Pageable pageable = PageRequest.of(page - 1, 30, sortObj);
        Page<User> users = adminService.findAllNormalUser(username, pageable);

        model.addAttribute("users", users);
        model.addAttribute("username", username);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);

        return "admin/admin/create";
    }

    @GetMapping("/toggle-super-admin")
    public String actionToggleSuperAdmin(Integer id) {
        Optional<User> userOptional = adminService.findById(id);
        userOptional.ifPresent(user -> adminService.toggleSuperAdmin(user));
        return "redirect:/admin/admin";
    }

    @GetMapping("/toggle-admin")
    public String actionToggleAdmin(Integer id) {
        Optional<User> userOptional = adminService.findById(id);
        userOptional.ifPresent(user -> adminService.toggleAdmin(user));
        return "redirect:/admin/admin";
    }
}
