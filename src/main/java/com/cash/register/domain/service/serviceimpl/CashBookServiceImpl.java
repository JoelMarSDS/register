package com.cash.register.domain.service.serviceimpl;

import com.cash.register.domain.entity.CashBook;
import com.cash.register.domain.repository.CashBookRepository;
import com.cash.register.domain.service.CashBookService;
import com.cash.register.domain.service.ClientService;
import com.cash.register.domain.settings.config.messageproperties.PropertiesSourceMessage;
import com.cash.register.domain.settings.exceptions.BusinessException;
import com.cash.register.domain.settings.exceptions.UsersNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CashBookServiceImpl implements CashBookService {

    private static final String DDMMYY = "dd-MM-yyyy";

    @Autowired
    private CashBookRepository cashBookRepository;

    @Autowired
    private ClientService clientService;

    @Override
    public CashBook addCashBook(CashBook cashBook) {
        System.out.println(cashBook);
        var client = clientService.getClientById(cashBook.getClient().getId());

        try {
            exceptionCashBook(cashBook);

            LocalDate localTimeNow = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DDMMYY);
            localTimeNow.format(dateTimeFormatter);

            cashBook.setLancetDate(localTimeNow);
            cashBook.setClient(client);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cashBookRepository.save(cashBook);
    }

    @Override
    public CashBook updateCashBook(Long id, CashBook cashBook) {
        return cashBookRepository.findById(id)
                .map(uc -> {

                    exceptionCashBook(cashBook);

                    uc.setDescription(cashBook.getDescription());
                    uc.setType(cashBook.getType());
                    uc.setValue(cashBook.getValue());
                    if (cashBook.getClient() != null) {
                        uc.setClient(cashBook.getClient());
                    }

                    return cashBookRepository.save(uc);
                }).orElseThrow(
                        () -> new EntityNotFoundException(PropertiesSourceMessage
                                .getMessageSource("msg.cashbook.not.found.error"))
                );
    }

    @Override
    public CashBook getCashBookById(Long id) {
        return cashBookRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(PropertiesSourceMessage
                                .getMessageSource("msg.cashbook.not.found.error"))
                );
    }

    @Override
    public List<CashBook> getCashBookList() {

        var cashBookList = cashBookRepository.findAll();
        try {
            if (cashBookList.isEmpty()) {
                throw new UsersNotFoundException(PropertiesSourceMessage
                        .getMessageSource("msg.is.empty"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cashBookList;
    }

    @Override
    public List<CashBook> getCashBookFilter(Long id) {
        var cashBookList = cashBookRepository.findByClient(id);
        try {
            if (cashBookList.isEmpty()) {
                throw new UsersNotFoundException(PropertiesSourceMessage
                        .getMessageSource("msg.is.empty"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cashBookList;
    }

    @Override
    public CashBook deleteCashBookById(Long id) {
        var cashBookDelete = cashBookRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(PropertiesSourceMessage
                                .getMessageSource("msg.cashbook.not.found.error"))
                );
        cashBookRepository.deleteById(cashBookDelete.getId());
        return cashBookDelete;
    }

    private void exceptionCashBook(CashBook cashBook) {

        String character;

        var clientTemp = clientService.getClientById(cashBook.getClient().getId());
        if (clientTemp == null) {
            throw new BusinessException(PropertiesSourceMessage.getMessageSource("msg.client.not.found.error"));
        }

        character = cashBook.getType().toString();
        character.toLowerCase();
        if (!character.equalsIgnoreCase("D") && !character.equalsIgnoreCase("C")) {
            throw new BusinessException(PropertiesSourceMessage.getMessageSource("msg.properties.type.error"));
        }
    }
}
