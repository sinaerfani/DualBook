package com.example.dualbook.controller.api;

import com.example.dualbook.dto.ApiResponse;
import com.example.dualbook.entity.User;
import com.example.dualbook.entity.enums.RoleName;
import com.example.dualbook.service.admin.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminApiController {

    private final AdminService adminService;

    public AdminApiController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getStats() {
        try {
            Map<String, Long> stats = Map.of(
                    "usersCount", adminService.getUsersCount(),
                    "activeUsersCount", adminService.getActiveUsersCount(),
                    "transactionsCount", adminService.getTransactionsCount(),
                    "pendingTransactionsCount", adminService.getPendingTransactionsCount()
            );
            return ResponseEntity.ok(ApiResponse.success("Stats retrieved successfully", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve stats: " + e.getMessage()));
        }
    }

    @PostMapping("/users/{userId}/toggle-status")
    public ResponseEntity<ApiResponse<String>> toggleUserStatus(@PathVariable Long userId) {
        try {
            adminService.toggleUserStatus(userId);
            return ResponseEntity.ok(ApiResponse.success("User status updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update user status: " + e.getMessage()));
        }
    }

    @PostMapping("/users/{userId}/role")
    public ResponseEntity<ApiResponse<String>> changeUserRole(
            @PathVariable Long userId,
            @RequestParam RoleName newRole) {
        try {
            adminService.changeUserRole(userId, newRole);
            return ResponseEntity.ok(ApiResponse.success("User role updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update user role: " + e.getMessage()));
        }
    }
}