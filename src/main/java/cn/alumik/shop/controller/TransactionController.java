package cn.alumik.shop.controller;


import cn.alumik.shop.entity.RefundRequest;
import cn.alumik.shop.entity.Transaction;
import cn.alumik.shop.service.RefundRequestService;
import cn.alumik.shop.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private TransactionService transactionService;
    private RefundRequestService refundRequestService;

    public TransactionController(TransactionService transactionService, RefundRequestService refundRequestService) {
        this.transactionService = transactionService;
        this.refundRequestService = refundRequestService;
    }

    @GetMapping("/detail")
    public String actionDetailGetter(Model model, int id) {
        Transaction transaction = transactionService.getById(id);
        model.addAttribute("transaction", transaction);

        return "transaction/detail";
    }

    @GetMapping("/refund")
    public String actionRefundGetter(Model model, int id){
        System.out.println(id);
        Transaction transaction = transactionService.getById(id);
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setTransaction(transaction);
        model.addAttribute("refund", refundRequest);
        return "transaction/refund";
    }

    @PostMapping("/refund")
    public String actionRefundPoster(@ModelAttribute("refund") RefundRequest refundRequest){
        refundRequestService.save(refundRequest);
        int id = refundRequest.getTransaction().getId();
        return "redirect:/transaction/detail?id=" + id;
    }
}
