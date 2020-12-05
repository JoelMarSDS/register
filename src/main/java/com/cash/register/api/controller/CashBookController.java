package com.cash.register.api.controller;

import com.cash.register.domain.entity.CashBook;
import com.cash.register.domain.service.CashBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cashbook")
public class CashBookController {

    @Autowired
    private CashBookService cashBookService;

    @PostMapping
    public ResponseEntity<CashBook> addCashBook(@RequestBody CashBook cashBook) {
        var cashBookSave = cashBookService.addCashBook(cashBook);
        URI uri = URI.create("/user/" + cashBookSave.getId());
        return ResponseEntity.created(uri).body(cashBookSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CashBook> updateCashBook(@PathVariable Long id, @RequestBody CashBook cashBook) {
        var updateCashBook = cashBookService.updateCashBook(id, cashBook);
        return ResponseEntity.ok(cashBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashBook> getCashBookById(@PathVariable Long id) {
        return ResponseEntity.ok(cashBookService.getCashBookById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CashBook>> getCashBookList() {
        return ResponseEntity.ok(cashBookService.getCashBookList());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CashBook>> getCashBookFilter(@RequestParam Long id) {

        return ResponseEntity.ok(cashBookService.getCashBookFilter(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CashBook> deleteCashBookById(@PathVariable Long id) {
        return ResponseEntity.ok(cashBookService.deleteCashBookById(id));
    }
}
