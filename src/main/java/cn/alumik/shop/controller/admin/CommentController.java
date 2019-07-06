package cn.alumik.shop.controller.admin;

import cn.alumik.shop.entity.Comment;
import cn.alumik.shop.service.CommentService;
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
@RequestMapping("/admin/comment")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("")
    public String actionIndex(
            Model model,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "0") Integer itemId,
            @RequestParam(defaultValue = "0") Integer star,
            @RequestParam(defaultValue = "") String content,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "1") Integer page) {
        Sort sortObj;
        if (sort.startsWith("-")) {
            sortObj = Sort.by(sort.substring(1)).descending();
        } else {
            sortObj = Sort.by(sort);
        }

        Pageable pageable = PageRequest.of(page - 1, 30, sortObj);
        Page<Comment> comments = commentService.findAll(username, itemId, star, content, pageable);

        model.addAttribute("comments", comments);
        model.addAttribute("username", username);
        model.addAttribute("itemId", itemId);
        model.addAttribute("star", star);
        model.addAttribute("content", content);
        model.addAttribute("sort", sort);
        model.addAttribute("page", page);

        return "admin/comment/index";
    }
}
