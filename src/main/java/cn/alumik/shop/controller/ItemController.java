package cn.alumik.shop.controller;

import cn.alumik.shop.entity.*;
import cn.alumik.shop.service.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.*;

@Controller
@RequestMapping("/item")
public class ItemController {
    private ItemService itemService;
    private CategoryService categoryService;
    private UserService userService;
    private SecurityService securityService;
    private TransactionService transactionService;
    private CommentService commentService;
    private CartService cartService;

    public ItemController(ItemService itemService, CategoryService categoryService,
                          UserService userService, SecurityService securityService,
                          TransactionService transactionService, CommentService commentService,
                          CartService cartService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.securityService = securityService;
        this.transactionService = transactionService;
        this.commentService = commentService;
        this.cartService = cartService;
    }

    @GetMapping("")
    public String actionIndex(
            Model model,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer categoryId,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") Integer page) {
        Sort sortObj;

        if (sort.startsWith("-")) {
            sortObj = Sort.by(sort.substring(1)).descending();
        } else {
            sortObj = Sort.by(sort);
        }
        Page<Object[]> items;
        if (categoryId != 0) {
            items = itemService.findAll(name, categoryId, page - 1, 16, sortObj);
        } else {
            items = itemService.findAll(name, page - 1, 16, sortObj);
        }
        List<Category> categories = categoryService.findAll();

        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);

        return "item/index";
    }

    @GetMapping("/view")
    public String actionView(Model model, Integer id) {
        Optional<Item> itemOptional = itemService.findById(id);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            User user = userService.findByUsername(securityService.findLoggedInUsername());
            boolean isInCart = cartService.findExistUserItem(item) != null;
            boolean isFavorite = user.getFavoriteItems().contains(item);

            model.addAttribute("isFavorite", isFavorite);
            model.addAttribute("isInCart", isInCart);
            model.addAttribute("item", item);

            return "item/view";
        }
        return "redirect:/item";
    }

    @GetMapping("/comment")
    public String actionComment(
            Model model,
            Integer id,
            @RequestParam(defaultValue = "-createdAt") String sort,
            @RequestParam(defaultValue = "1") Integer page) {
        Optional<Item> itemOptional = itemService.findById(id);
        if (itemOptional.isPresent()) {
            Sort sortObj;
            if (sort.startsWith("-")) {
                sortObj = Sort.by(sort.substring(1)).descending();
            } else {
                sortObj = Sort.by(sort);
            }

            Pageable pageable = PageRequest.of(page - 1, 10, sortObj);
            Page<Comment> comments = commentService.findAllByTransaction_Item_Id(id, pageable);

            model.addAttribute("sort", sort);
            model.addAttribute("page", page);
            model.addAttribute("comments", comments);
            model.addAttribute("item", itemOptional.get());

            return "item/comment/index";
        }
        return "redirect:/item";
    }

    @GetMapping("/add")
    public String actionAddGetter(Model model) {
        Item item = new Item();
        item.setStock(1);
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
                                  @RequestParam("image") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            return "item/add";
        }
        getImageFromServer(item, file);
        return "redirect:/info/?tab=sellings";
    }

    @GetMapping("/modify")
    public String actionModifyGetter(Model model, int id) {
        Item item = itemService.getById(id);
        model.addAttribute("item", item);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "item/modify";
    }

    @PostMapping("/modify")
    public String actionModifyPoster(@Valid @ModelAttribute("item") Item item, BindingResult bindingResult,
                                     MultipartFile image,
                                     @RequestParam("changeImage") String changeImage) throws IOException {
        if (bindingResult.hasErrors()) {
            return "item/modify";
        }
        if (changeImage.equals("change")) {
            getImageFromServer(item, image);
        } else if (changeImage.equals("notChange")) {
            itemService.save(item);
        }
        return "redirect:/info/?tab=sellings";
    }

    private void getImageFromServer(@ModelAttribute("item") @Valid Item item, @RequestParam(defaultValue = "") MultipartFile image) throws IOException {
        String fileOriginalName = image.getOriginalFilename();
        String newFileName = UUID.randomUUID() + fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
        Socket socket = new Socket(IP_ADDR, uploadPort);
        DataOutputStream filenameos = new DataOutputStream(socket.getOutputStream());
        filenameos.writeUTF(newFileName);
        filenameos.writeLong(image.getSize());
        OutputStream filecontentos = socket.getOutputStream();
        InputStream filecontentis = image.getInputStream();
        byte[] results = filecontentis.readAllBytes();
        filecontentos.write(results);
        itemService.save(item, newFileName);
        socket.shutdownOutput();
    }

    @GetMapping("/buy")
    public String actionBuyGetter(Model model, int id) {
        Item item = itemService.getById(id);
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        Transaction transaction = new Transaction();
        transaction.setSinglePrice(item.getPrice());
        transaction.setItem(item);
        transaction.setBuyer(user);
        transaction.setAmount(1);
        model.addAttribute("transaction", transaction);
        return "item/buy";
    }

    @PostMapping("/buy")
    public String actionBuyPoster(@ModelAttribute("transaction") Transaction transaction,
                                  @RequestParam("tempAddress") String tempAddress) {
        if (transaction.getAddress().equals("")) {
            transaction.setAddress(tempAddress);
        }
        transactionService.save(transaction);
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

    @PostMapping("/delete")
    public String actionDeletePoster(Model model, int id) {
        Item item = itemService.getById(id);
        item.setAvailable(false);
        itemService.save(item);
        return "redirect:/info/?tab=sellings";
    }

    @GetMapping("/addComment")
    public String actionAddCommentGetter(Model model, int id) {
        Comment comment = new Comment();
        comment.setStar(3);
        Transaction transaction = transactionService.getById(id);
        comment.setTransaction(transaction);
        model.addAttribute("comment", comment);
        return "item/comment/add";
    }

    @PostMapping("/addComment")
    public String actionAddCommentPoster(@ModelAttribute("comment") Comment comment) {
        commentService.save(comment);
        return "redirect:/info/?tab=transactions";
    }

    @GetMapping("/modifyComment")
    public String actionModifyCommentGetter(Model model, int id) {
        Comment comment = commentService.getById(id);
        model.addAttribute("comment", comment);
        return "item/comment/modify";
    }

    @PostMapping("/modifyComment")
    public String actionModifyCommentPoster(@ModelAttribute("comment") Comment comment,
                                            @RequestParam("star") int star) {
        comment.setStar(star);
        commentService.save(comment);
        return "redirect:/info/?tab=transactions";
    }

    @PostMapping("/deleteComment")
    public String actionDeleteCommentPoster(Integer id) {
        commentService.deleteById(id);
        return "redirect:/info/?tab=transactions";
    }

    @GetMapping("/transactions")
    public String actionShowTransactionsGetter(Model model, Integer id,
                                               @RequestParam(defaultValue = "-soldAt") String sort,
                                               @RequestParam(defaultValue = "1") Integer page) {
        Sort sortObj;
        Item item = itemService.getById(id);
        model.addAttribute("item", item);
        if (sort.startsWith("-")) {
            sortObj = Sort.by(sort.substring(1)).descending();
        } else {
            sortObj = Sort.by(sort);
        }

        Page<Transaction> transactions = transactionService.findByItem(item, page - 1, 30, sortObj);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);
        model.addAttribute("transactions", transactions);
        model.addAttribute("item", item);
        return "transaction/index";
    }
}