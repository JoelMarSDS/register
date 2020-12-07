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
    public ResponseEntity<CashBook> addCashBook(@RequestBody CashBook cashBook, @RequestHeader String name, @RequestHeader String password) {
        var cashBookSave = cashBookService.addCashBook(cashBook, name, password);
        URI uri = URI.create("/user/" + cashBookSave.getId());
        return ResponseEntity.created(uri).body(cashBookSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CashBook> updateCashBook(@PathVariable Long id, @RequestBody CashBook cashBook, @RequestHeader String name, @RequestHeader String password) {
        var updateCashBook = cashBookService.updateCashBook(id, cashBook, name, password);
        return ResponseEntity.ok(cashBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashBook> getCashBookById(@PathVariable Long id, @RequestHeader String name, @RequestHeader String password) {
        return ResponseEntity.ok(cashBookService.getCashBookById(id, name, password));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CashBook>> getCashBookList(@RequestHeader String name, @RequestHeader String password) {
        return ResponseEntity.ok(cashBookService.getCashBookList(name, password));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CashBook>> getCashBookFilter(@RequestParam Long id, @RequestHeader String name, @RequestHeader String password) {

        return ResponseEntity.ok(cashBookService.getCashBookFilter(id, name, password));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CashBook> deleteCashBookById(@PathVariable Long id, @RequestHeader String name, @RequestHeader String password) {
        return ResponseEntity.ok(cashBookService.deleteCashBookById(id, name, password));
    }
}
