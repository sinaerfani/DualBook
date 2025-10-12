package com.example.dualbook.controller.page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminPageController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        return "admin/dashboard";
    }

    @GetMapping("/admin/users")
    public String userManagement(Model model) {
        return "admin/users";
    }

    @GetMapping("/admin/transactions")
    public String transactionManagement(Model model) {
        return "admin/transactions";
    }

    @GetMapping("/admin/ledgers")
    public String ledgerManagement(Model model) {
        return "admin/ledgers";
    }
}