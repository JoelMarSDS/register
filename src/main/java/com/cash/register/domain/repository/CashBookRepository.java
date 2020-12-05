package com.cash.register.domain.repository;

import com.cash.register.domain.entity.CashBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashBookRepository extends JpaRepository<CashBook, Long> {

    List<CashBook> findByClient(Long id);

}