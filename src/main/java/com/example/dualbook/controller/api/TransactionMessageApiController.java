package com.example.dualbook.controller.api;

import com.example.dualbook.dto.*;
import com.example.dualbook.entity.User;
import com.example.dualbook.mapper.TransactionMessageMapper;
import com.example.dualbook.service.message.TransactionMessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transaction-messages")
public class TransactionMessageApiController {

    private final TransactionMessageService messageService;
    private final TransactionMessageMapper messageMapper;

    public TransactionMessageApiController(TransactionMessageService messageService,
                                           TransactionMessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionMessageResponseDTO>> sendMessage(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody TransactionMessageCreateDTO createDTO) {
        try {
            var transactionMessage = messageService.sendMessage(user, createDTO.getTransactionId(), createDTO.getMessage());
            var responseDTO = messageMapper.toResponseDTO(transactionMessage, user);
            return ResponseEntity.ok(ApiResponse.success("Message sent successfully", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to send message: " + e.getMessage()));
        }
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<ApiResponse<List<TransactionMessageResponseDTO>>> getTransactionMessages(
            @AuthenticationPrincipal User user,
            @PathVariable Long transactionId) {
        try {
            var messages = messageService.getTransactionMessages(transactionId);
            var messageDTOs = messages.stream()
                    .map(message -> messageMapper.toResponseDTO(message, user))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success("Messages retrieved successfully", messageDTOs));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve messages: " + e.getMessage()));
        }
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<ApiResponse<TransactionMessageResponseDTO>> getMessage(
            @AuthenticationPrincipal User user,
            @PathVariable Long messageId) {
        try {
            var message = messageService.getMessageById(messageId);
            var messageDTO = messageMapper.toResponseDTO(message, user);
            return ResponseEntity.ok(ApiResponse.success("Message retrieved successfully", messageDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve message: " + e.getMessage()));
        }
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<ApiResponse<TransactionMessageResponseDTO>> updateMessage(
            @AuthenticationPrincipal User user,
            @PathVariable Long messageId,
            @Valid @RequestBody TransactionMessageCreateDTO updateDTO) {
        try {
            var updatedMessage = messageService.updateMessage(messageId, user, updateDTO.getMessage());
            var messageDTO = messageMapper.toResponseDTO(updatedMessage, user);
            return ResponseEntity.ok(ApiResponse.success("Message updated successfully", messageDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update message: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse<String>> deleteMessage(
            @AuthenticationPrincipal User user,
            @PathVariable Long messageId) {
        try {
            messageService.deleteMessage(messageId, user);
            return ResponseEntity.ok(ApiResponse.success("Message deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to delete message: " + e.getMessage()));
        }
    }
}