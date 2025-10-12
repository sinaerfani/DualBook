package com.example.dualbook.controller.api;

import com.example.dualbook.dto.ApiResponse;
import com.example.dualbook.dto.LedgerDTO;
import com.example.dualbook.entity.User;
import com.example.dualbook.mapper.LedgerMapper;
import com.example.dualbook.service.ledger.LedgerService;
import com.example.dualbook.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ledgers")
public class LedgerApiController {

    private final LedgerService ledgerService;
    private final LedgerMapper ledgerMapper;
    private final UserService userService;
    public LedgerApiController(LedgerService ledgerService, LedgerMapper ledgerMapper, UserService userService) {
        this.ledgerService = ledgerService;
        this.ledgerMapper = ledgerMapper;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LedgerDTO>>> getUserLedgers(@AuthenticationPrincipal User user) {
        try {
            var ledgers = ledgerService.getUserLedgers(user);
            var ledgerDTOs = ledgers.stream()
                    .map(ledgerMapper::toDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success("Ledgers retrieved successfully", ledgerDTOs));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve ledgers: " + e.getMessage()));
        }
    }

    @GetMapping("/balance/total")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalBalance(@AuthenticationPrincipal User user) {
        try {
            var totalBalance = ledgerService.getUserTotalBalance(user);
            return ResponseEntity.ok(ApiResponse.success("Total balance retrieved successfully", totalBalance));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve total balance: " + e.getMessage()));
        }
    }

    @GetMapping("/{userId}/{currencySymbol}")
    public ResponseEntity<ApiResponse<LedgerDTO>> getLedgerWithUser(
            @AuthenticationPrincipal User user,
            @PathVariable Long userId,
            @PathVariable String currencySymbol) {
        try {
            var otherUser = userService.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found or disabled"));
                    ; // نیاز به inject UserService
            var ledger = ledgerService.findLedgerBetweenUsers(user, otherUser, currencySymbol);
            var ledgerDTO = ledgerMapper.toDTO(ledger);
            return ResponseEntity.ok(ApiResponse.success("Ledger retrieved successfully", ledgerDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve ledger: " + e.getMessage()));
        }
    }
}