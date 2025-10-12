package com.example.dualbook.controller.page;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.dualbook.entity.User;

@Controller
public class TransactionPageController {

    @GetMapping("/transactions")
    public String transactions(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "transactions/list";
    }

    @GetMapping("/transactions/create")
    public String createTransaction(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "transactions/create";
    }

    @GetMapping("/transactions/{id}")
    public String transactionDetail(@AuthenticationPrincipal User user, @PathVariable Long id, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("transactionId", id);
        return "transactions/detail";
    }
}