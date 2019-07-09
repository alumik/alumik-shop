package cn.alumik.shop.controller;

import cn.alumik.shop.entity.*;
import cn.alumik.shop.form.ChangePasswordForm;
import cn.alumik.shop.service.*;
import cn.alumik.shop.validator.PasswordValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/info")
public class InfoController {

    private SecurityService securityService;

    private UserService userService;

    private GenderService genderService;

    private AddressService addressService;

    private TransactionService transactionService;

    private ItemService itemService;

    private PasswordValidator passwordValidator;


    public InfoController(SecurityService securityService, UserService userService, GenderService genderService, AddressService addressService, TransactionService transactionService, ItemService itemService, PasswordValidator passwordValidator) {
        this.securityService = securityService;
        this.userService = userService;
        this.genderService = genderService;
        this.addressService = addressService;
        this.transactionService = transactionService;
        this.itemService = itemService;
        this.passwordValidator = passwordValidator;
    }

    @GetMapping("")
    public String actionInfoGetter(Model model, @RequestParam(defaultValue = "info") String tab,
                                   @RequestParam(defaultValue = "soldAt") String sortTransaction,
                                   @RequestParam(defaultValue = "1") Integer pageTransaction,
                                   @RequestParam(defaultValue = "createdAt") String sortSelling,
                                   @RequestParam(defaultValue = "1") Integer pageSelling,
                                   @RequestParam(defaultValue = "id") String sortFavorite,
                                   @RequestParam(defaultValue = "1") Integer pageFavorite) {
        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        List<Gender> genders = genderService.findAll();
        model.addAttribute("genders", genders);

        //Sort and page by transactions
        Sort sortObj;
        if (sortTransaction.startsWith("-")) {
            sortObj = Sort.by(sortTransaction.substring(1)).descending();
        } else {
            sortObj = Sort.by(sortTransaction);
        }

        Page<Transaction> transactions = transactionService.findAll(pageTransaction - 1, 6, sortObj);
        model.addAttribute("sortTransaction", sortTransaction);
        model.addAttribute("pageTransaction", pageTransaction);
        model.addAttribute("transactions", transactions);

        //Sort and page by sellings
        if (sortSelling.startsWith("-")) {
            sortObj = Sort.by(sortSelling.substring(1)).descending();
        } else {
            sortObj = Sort.by(sortSelling);
        }

        Page<Item> sellings = itemService.findAllBySeller(pageSelling - 1, 6, sortObj);
        model.addAttribute("sortSelling", sortSelling);
        model.addAttribute("pageSelling", pageSelling);
        model.addAttribute("sellings", sellings);

        //Sort and page by favorites
        if (sortFavorite.startsWith("-")) {
            sortObj = Sort.by(sortFavorite.substring(1)).descending();
        } else {
            sortObj = Sort.by(sortFavorite);
        }

        Page<Item> favoriteItems = itemService.findAllByFavoriteBy(pageFavorite - 1, 6, sortObj);
        model.addAttribute("sortFavorite", sortFavorite);
        model.addAttribute("pageFavorite", pageFavorite);
        model.addAttribute("favorites", favoriteItems);

        model.addAttribute("tab", tab);
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
        address.setUser(userService.findByUsername(securityService.findLoggedInUsername()));
        addressService.save(address);
        return "redirect:/info/infoAddress";
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
        return "redirect:/info/infoAddress";
    }

    @PostMapping("/deleteAddress")
    public String actionDeleteAddressPoster(Integer id){
        addressService.delete(addressService.getById(id));
        return "redirect:/info/";
    }

    @GetMapping("/change-password")
    public String actionChangePassword(Model model) {
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setUsername(securityService.findLoggedInUsername());
        model.addAttribute("changePasswordForm", changePasswordForm);
        return "user/change-password";
    }

    @PostMapping("/change-password")
    public String actionChangePassword(
            @ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
            BindingResult bindingResult) {
        passwordValidator.validate(changePasswordForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "user/change-password";
        }
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        userService.setPassword(user, changePasswordForm);
        return "redirect:/info";
    }
}
