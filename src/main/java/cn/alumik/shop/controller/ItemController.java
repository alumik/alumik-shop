package cn.alumik.shop.controller;

import cn.alumik.shop.entity.*;
import cn.alumik.shop.service.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/item")
public class ItemController {
    private ItemService itemService;
    private CategoryService categoryService;
    private UserService userService;
    private SecurityService securityService;
    private TransactionService transactionService;
    private CommentService commentService;

    public ItemController(ItemService itemService, CategoryService categoryService,
                          UserService userService, SecurityService securityService, TransactionService transactionService, CommentService commentService){
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.securityService = securityService;
        this.transactionService = transactionService;
        this.commentService = commentService;
    }

    @GetMapping("/add")
    public String actionAddGetter(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "item/add";
    }

    private static final String IP_ADDR = "111.230.242.203";//服务器地址
    //private static final String IP_ADDR = "localhost";//服务器地址
    private static final int uploadPort = 12345;//服务器端口号
    private static final int downloadPort = 10010;

    @PostMapping("/add")
    public String actionAddPoster(@Valid @ModelAttribute("item") Item item, BindingResult bindingResult,
                                  @RequestParam("image")MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()){
            return "item/add";
        }
        String fileOriginalName = file.getOriginalFilename();
        String newFileName = UUID.randomUUID() + fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
        Socket socket = new Socket(IP_ADDR, uploadPort);
        DataOutputStream filenameos = new DataOutputStream(socket.getOutputStream());
        filenameos.writeUTF(newFileName);
        filenameos.writeLong(file.getSize());
        OutputStream filecontentos = socket.getOutputStream();
        InputStream filecontentis = file.getInputStream();
        byte [] results = filecontentis.readAllBytes();
        filecontentos.write(results);
        itemService.save(item, newFileName);
        socket.shutdownOutput();
        return "redirect:/";
    }

    static class Result{

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
    public ResponseEntity<Resource> serveFile(Model model, int id) throws IOException {
        Item item = itemService.getById(id);
        Socket socket = new Socket(IP_ADDR, downloadPort);
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        os.writeUTF(item.getPic());
        InputStream is = socket.getInputStream();
        Resource file = new InputStreamResource(is);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/search")
    public String actionSearchGetter(
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

        Page<Object []> items = itemService.findAll(name, page - 1, 4, sortObj);
        model.addAttribute("name", name);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);
        model.addAttribute("items", items);
        return "item/search";
    }

    @GetMapping("/detail")
    public String actionDetailGetter(
            Model model, int id,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") Integer page) {
        Sort sortObj;
        Item item = itemService.getById(id);
        model.addAttribute("item", item);

        if (sort.startsWith("-")) {
            sortObj = Sort.by(sort.substring(1)).descending();
        } else {
            sortObj = Sort.by(sort);
        }

        Page<Comment> comments = commentService.findAll(id, page - 1, 4, sortObj);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);
        model.addAttribute("comments", comments);
        return "item/detail";
    }

    @GetMapping("/addComment")
    public String actionAddCommentGetter(Model model, int id){
        Comment comment = new Comment();
        comment.setStar(3);
        Transaction transaction = transactionService.getById(id);
        comment.setTransaction(transaction);
        model.addAttribute("comment", comment);
        return "item/comment/add";
    }

    @PostMapping("/addComment")
    public String actionAddCommentPoster(@ModelAttribute("comment") Comment comment){
        commentService.save(comment);
        return "redirect:/info/";
    }

    @GetMapping("/modifyComment")
    public String actionModifyCommentGetter(Model model, int id){
        Comment comment = commentService.getById(id);
        model.addAttribute("comment", comment);
        return "item/comment/modify";
    }

    @PostMapping("/modifyComment")
    public String actionModifyCommentPoster(@ModelAttribute("comment") Comment comment,
                                            @RequestParam("star") int star){
        comment.setStar(star);
        commentService.save(comment);
        return "redirect:/info/";
    }

    @PostMapping("/deleteComment")
    public String actionDeleteCommentPoster(Integer id){
        commentService.deleteById(id);
        return "redirect:/info/";
    }
}