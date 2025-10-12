package com.example.dualbook.controller.api;

import com.example.dualbook.dto.ApiResponse;
import com.example.dualbook.entity.CurrencyType;
import com.example.dualbook.service.currencyType.CurrencyTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currency-types")
public class CurrencyTypeApiController {

    private final CurrencyTypeService currencyTypeService;

    public CurrencyTypeApiController(CurrencyTypeService currencyTypeService) {
        this.currencyTypeService = currencyTypeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CurrencyType>>> getAllCurrencyTypes() {
        try {
            var currencyTypes = currencyTypeService.getAllCurrencyTypes();
            return ResponseEntity.ok(ApiResponse.success("Currency types retrieved successfully", currencyTypes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve currency types: " + e.getMessage()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CurrencyType>> createCurrencyType(
            @RequestParam String symbol,
            @RequestParam String nameFa) {
        try {
            var currencyType = currencyTypeService.createCurrencyType(symbol, nameFa);
            return ResponseEntity.ok(ApiResponse.success("Currency type created successfully", currencyType));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to create currency type: " + e.getMessage()));
        }
    }
}