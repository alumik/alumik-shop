package cn.alumik.shop.controller;

import cn.alumik.shop.entity.Address;
import cn.alumik.shop.entity.Gender;
import cn.alumik.shop.entity.User;
import cn.alumik.shop.service.AddressService;
import cn.alumik.shop.service.GenderService;
import cn.alumik.shop.service.SecurityService;
import cn.alumik.shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/info")
public class InfoController {

    private SecurityService securityService;

    private UserService userService;

    private GenderService genderService;

    private AddressService addressService;


    public InfoController(SecurityService securityService, UserService userService, GenderService genderService, AddressService addressService) {
        this.securityService = securityService;
        this.userService = userService;
        this.genderService = genderService;
        this.addressService = addressService;
    }

    @GetMapping("/")
    public String actionInfoGetter(Model model) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        List<Gender> genders = genderService.findAll();
        model.addAttribute("genders", genders);
        return "user/info";
    }

    @GetMapping("/modify")
    public String actionModifyGetter(Model model) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        List<Gender> genders = genderService.findAll();
        model.addAttribute("genders", genders);
        return "user/modifyInfo";
    }

    @PostMapping("/modify")
    public String actionModify(@ModelAttribute("userForm") User userForm) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        user.getUserProfile().setPhone(userForm.getUserProfile().getPhone());
        user.getUserProfile().setEmail(userForm.getUserProfile().getEmail());
        user.getUserProfile().setDetail(userForm.getUserProfile().getDetail());
        user.getUserProfile().setGender(userForm.getUserProfile().getGender());
        userService.save(user, false);
        return "redirect:/info/";
    }

    @GetMapping("/infoAddress")
    public String actionInfoAddressGetter(Model model){
        List<Address> addresses = addressService.getAll();
        model.addAttribute("addresses", addresses);
        return "user/address/index";
    }

    @GetMapping("/addAddress")
    public String actionAddAddressGetter(Model model){
        Address address = new Address();
        model.addAttribute("address", address);
        return "user/address/add";
    }

    @PostMapping("/addAddress")
    public String actionAddAddressPoster(@ModelAttribute("address")Address address){
        addressService.save(address);
        return "redirect:/info/";
    }

    @GetMapping("/modifyAddress")
    public String actionModifyAddressGetter(Model model, int id){
        Address address = addressService.getById(id);
        model.addAttribute("address", address);
        return "user/address/modify";

    }

    @PostMapping("/modifyAddress")
    public String actionModifyAddressPoster(@ModelAttribute("address")Address address){
        addressService.save(address);
        return "redirect:/info/";
    }

    @PostMapping("/deleteAddress")
    public String actionDeleteAddressPoster(Integer id){
        addressService.delete(addressService.getById(id));
        return "redirect:/info/";
    }
}
