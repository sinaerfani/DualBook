package com.example.dualbook.controller.api;

import com.example.dualbook.dto.*;
import com.example.dualbook.entity.Transaction;
import com.example.dualbook.entity.User;
import com.example.dualbook.entity.enums.TransactionStatus;
import com.example.dualbook.mapper.TransactionMapper;
import com.example.dualbook.service.transaction.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionApiController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionApiController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> createTransaction(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody TransactionCreateDTO createDTO) {
        try {
            var transaction = transactionService.createTransaction(user, createDTO);
            var responseDTO = transactionMapper.toResponseDTO(transaction);
            return ResponseEntity.ok(ApiResponse.success("Transaction created successfully", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Transaction creation failed: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> confirmTransaction(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        try {
            var transaction = transactionService.confirmTransaction(id, user);
            var responseDTO = transactionMapper.toResponseDTO(transaction);
            return ResponseEntity.ok(ApiResponse.success("Transaction confirmed successfully", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Confirmation failed: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> rejectTransaction(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestParam String reason) {
        try {
            var transaction = transactionService.rejectTransaction(id, user, reason);
            var responseDTO = transactionMapper.toResponseDTO(transaction);
            return ResponseEntity.ok(ApiResponse.success("Transaction rejected successfully", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Rejection failed: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getUserTransactions(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) TransactionStatus status) {
        try {
            List<Transaction> transactions;
            if (status != null) {
                transactions = transactionService.getTransactionsByStatus(user, status);
            } else {
                transactions = transactionService.getUserTransactions(user);
            }

            List<TransactionResponseDTO> responseDTOs = transactions.stream()
                    .map(transactionMapper::toResponseDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success("Transactions retrieved successfully", responseDTOs));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve transactions: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> createReturnTransaction(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestParam String description) {
        try {
            var transaction = transactionService.createReturnTransaction(user, id, description);
            var responseDTO = transactionMapper.toResponseDTO(transaction);
            return ResponseEntity.ok(ApiResponse.success("Return transaction created successfully", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Return transaction creation failed: " + e.getMessage()));
        }
    }
}