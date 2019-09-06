package cn.alumik.shop.controller.user;

import cn.alumik.shop.entity.Address;
import cn.alumik.shop.service.AddressService;
import cn.alumik.shop.service.SecurityService;
import cn.alumik.shop.service.UserService;
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

@Controller("userAddressController")
@RequestMapping("/user/address")
public class AddressController {

    private UserService userService;

    private SecurityService securityService;

    private AddressService addressService;

    public AddressController(UserService userService, SecurityService securityService, AddressService addressService) {
        this.userService = userService;
        this.securityService = securityService;
        this.addressService = addressService;
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

        String username = securityService.findLoggedInUsername();
        Pageable pageable = PageRequest.of(page - 1, 30, sortObj);
        Page<Address> addresses = addressService.findAllByNameContainsAndUser_Username(name, username, pageable);

        model.addAttribute("addresses", addresses);
        model.addAttribute("name", name);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);

        return "user/address/index";
    }

    @GetMapping("/create")
    public String actionCreate(Model model) {
        Address address = new Address();
        model.addAttribute("address", address);
        return "user/address/create";
    }

    @PostMapping("/create")
    public String actionCreate(
            @Valid @ModelAttribute("address") Address address,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/address/create";
        }
        address.setUser(userService.findByUsername(securityService.findLoggedInUsername()));
        addressService.save(address);
        return "redirect:/user/address";
    }

    @GetMapping("/update")
    public String actionUpdate(Model model, Integer id) {
        Optional<Address> addressOptional = addressService.findById(id);
        if (addressOptional.isPresent()) {
            model.addAttribute("address", addressOptional.get());
            return "user/address/update";
        }
        return "redirect:/user/address";
    }

    @PostMapping("/update")
    public String actionUpdate(
            @Valid @ModelAttribute("address") Address address,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/address/update";
        }
        addressService.save(address);
        return "redirect:/user/address";
    }

    @GetMapping("/delete")
    public String actionDelete(Integer id) {
        addressService.deleteById(id);
        return "redirect:/user/address";
    }
}
