package cn.alumik.shop.controller;

import cn.alumik.shop.entity.*;
import cn.alumik.shop.service.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/item")
public class ItemController {

    public static final String filePath = "f:/home/upload/";
    private ItemService itemService;
    private CategoryService categoryService;
    private UserService userService;
    private SecurityService securityService;
    private TransactionService transactionService;

    public ItemController(ItemService itemService, CategoryService categoryService,
                          UserService userService, SecurityService securityService, TransactionService transactionService){
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.securityService = securityService;
        this.transactionService = transactionService;
    }

    @GetMapping("/add")
    public String actionAddGetter(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "item/add";
    }

    @PostMapping("/add")
    public String actionAddPoster(@Valid @ModelAttribute("item") Item item, BindingResult bindingResult,
                                  @RequestParam("image")MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()){
            return "item/add";
        }
        String fileOriginalName = file.getOriginalFilename();
        String newFileName = UUID.randomUUID() + fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
        File f = new File(filePath + newFileName);
        file.transferTo(f);
        itemService.save(item, f);
        return "redirect:/";
    }

    @GetMapping("/detail")
    public String actionDetailGetter(Model model, int id) {
        Item item = itemService.getById(id);
        model.addAttribute("item", item);
        return "item/detail";
    }

    @GetMapping("/buy")
    public String actionBuyGetter(Model model, int id) {
        Item item = itemService.getById(id);
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        model.addAttribute("srcItem", item);
        model.addAttribute("srcUser", user);
        Result result = new Result();
        result.setItemId(id);
        result.setAmount(1);
        model.addAttribute("result", result);
        return "item/buy";
    }

    @PostMapping("/buy")
    public String actionBuyPoster(@ModelAttribute("result") Result result){
        Transaction transaction = new Transaction();
        if (result.address.equals("")){
            transaction.setAddress(result.temp);
        }
        else{
            transaction.setAddress(result.address);
        }
        transaction.setAmount(result.amount);
        Item item = itemService.getById(result.itemId);
        transactionService.save(transaction, item);
        return "redirect:/";
    }

    @GetMapping("/getpic")
    public ResponseEntity<Resource> serveFile(Model model, int id) {
        Item item = itemService.getById(id);
        System.out.println(item.getPic());
        Resource file = new FileSystemResource(item.getPic());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}

class Result{

    int itemId;
    String address;
    int amount;
    String temp;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    Result(){
        itemId = 0;
        address = null;
        amount = 0;
        temp = null;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.valueOf(itemId) + " " + address + " " + String.valueOf(amount) + " " + temp;
    }
}