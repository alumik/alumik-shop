package cn.alumik.shop.controller;

import cn.alumik.shop.entity.History;
import cn.alumik.shop.service.HistoryService;
import cn.alumik.shop.service.SecurityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/history")
public class HistoryController {

    private HistoryService historyService;

    private SecurityService securityService;

    public HistoryController(HistoryService historyService, SecurityService securityService) {
        this.historyService = historyService;
        this.securityService = securityService;
    }

    @GetMapping("")
    public String actionIndex(Model model, @RequestParam(defaultValue = "1") Integer page) {
        Pageable pageable = PageRequest.of(page - 1, 30);
        Page<History> histories = historyService.findAllByUser_UsernameOrderByLastViewedDesc(
                securityService.findLoggedInUsername(),
                pageable);

        model.addAttribute("histories", histories);
        model.addAttribute("page", page);

        return "history/index";
    }

    @GetMapping("/clear")
    public String actionClear() {
        historyService.deleteByUser_Username(securityService.findLoggedInUsername());
        return "redirect:/history";
    }
}
