package cn.alumik.shop.controller.admin;

import cn.alumik.shop.entity.Role;
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

@Controller
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
            @RequestParam(defaultValue = "false") Boolean isSuperAdmin,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "30") Integer pageSize) {
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

        Pageable pageable = PageRequest.of(page - 1, pageSize, sortObj);
        Page<User> admins = adminService.findAll(username, roleName, pageable);

        for (User admin : admins) {
            admin.setIsSuperAdmin(false);
            for (Role role : admin.getRoles()) {
                if (role.getName().equals("ROLE_SUPER_ADMIN")) {
                    admin.setIsSuperAdmin(true);
                    break;
                }
            }
        }

        model.addAttribute("admins", admins);
        model.addAttribute("username", username);
        model.addAttribute("isSuperAdmin", isSuperAdmin);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);

        return "admin/admin/index";
    }
}
