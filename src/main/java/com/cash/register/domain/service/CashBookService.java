package com.cash.register.domain.service;

import com.cash.register.domain.entity.CashBook;

import java.util.List;

public interface CashBookService {

    CashBook addCashBook(CashBook cashBook, String name, String password);

    CashBook updateCashBook(Long id, CashBook cashBook, String name, String password);

    CashBook getCashBookById(Long id, String name, String password);

    List<CashBook> getCashBookList(String name, String password);

    List<CashBook> getCashBookFilter(Long id, String name, String password);

    CashBook deleteCashBookById(Long id, String name, String password);
}
