package com.cash.register.domain.service;

import com.cash.register.domain.entity.CashBook;

import java.util.List;

public interface CashBookService {

    CashBook addCashBook(CashBook cashBook);

    CashBook updateCashBook(Long id, CashBook cashBook);

    CashBook getCashBookById(Long id);

    List<CashBook> getCashBookList();

    List<CashBook> getCashBookFilter(Long id);

    CashBook deleteCashBookById(Long id);
}
