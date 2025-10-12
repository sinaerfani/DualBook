package com.example.dualbook.service.currencyType;

import com.example.dualbook.entity.CurrencyType;

import java.util.List;

public interface CurrencyTypeService {

    // ایجاد نوع ارز جدید
    CurrencyType createCurrencyType(String symbol, String nameFa);

    // پیدا کردن نوع ارز با symbol
    CurrencyType findBySymbol(String symbol);

    // لیست تمام انواع ارز
    List<CurrencyType> getAllCurrencyTypes();

    // به‌روزرسانی نوع ارز
    CurrencyType updateCurrencyType(Long id, String nameFa);

    // حذف منطقی نوع ارز
    void deleteCurrencyType(Long id);
}
