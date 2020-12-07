package com.cash.register.domain.service.serviceimpl;

import com.cash.register.domain.entity.CashBook;
import com.cash.register.domain.entity.Client;
import com.cash.register.domain.repository.CashBookRepository;
import com.cash.register.domain.repository.ClientRepository;
import com.cash.register.domain.service.CashBookService;
import com.cash.register.domain.service.ClientService;
import com.cash.register.domain.settings.config.messageproperties.PropertiesSourceMessage;
import com.cash.register.domain.settings.config.security.SecurityUsernamePassword;
import com.cash.register.domain.settings.exceptions.BusinessException;
import com.cash.register.domain.settings.exceptions.InvalidUserOrPasswordException;
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
    private ClientRepository clientRepository;

    @Autowired
    private SecurityUsernamePassword securityUsernamePassword;

    @Override
    public CashBook addCashBook(CashBook cashBook, String name, String password) {

        validationLogin(name, password);

        var client = getClient(cashBook.getClient().getId());

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
    public CashBook updateCashBook(Long id, CashBook cashBook, String name, String password) {

        validationLogin(name, password);

        return cashBookRepository.findById(id)
                .map(uc -> {
                    exceptionCashBook(cashBook);
                    getClient(cashBook.getId());

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
    public CashBook getCashBookById(Long id, String name, String password) {

        validationLogin(name, password);

        return cashBookRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(PropertiesSourceMessage
                                .getMessageSource("msg.cashbook.not.found.error"))
                );
    }

    @Override
    public List<CashBook> getCashBookList( String name, String password) {

        validationLogin(name, password);

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
    public List<CashBook> getCashBookFilter(Long id, String name, String password) {

        validationLogin(name, password);

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
    public CashBook deleteCashBookById(Long id, String name, String password) {

        validationLogin(name, password);

        var cashBookDelete = cashBookRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(PropertiesSourceMessage
                                .getMessageSource("msg.cashbook.not.found.error"))
                );
        cashBookRepository.deleteById(cashBookDelete.getId());
        return cashBookDelete;
    }

    private Client getClient(Long id){
        return clientRepository.findById(id)
                .orElseThrow(
                        () ->  new BusinessException(PropertiesSourceMessage
                                .getMessageSource("msg.client.not.found.error"))
                );
    }

    private void exceptionCashBook(CashBook cashBook) {

        String character;

        character = cashBook.getType().toString();
        character.toLowerCase();
        if (!character.equalsIgnoreCase("D") && !character.equalsIgnoreCase("C")) {
            throw new BusinessException(PropertiesSourceMessage.getMessageSource("msg.properties.type.error"));
        }
    }

    private void validationLogin(String name, String password) {
        if (!securityUsernamePassword.namePassword(name, password)) {
            throw new InvalidUserOrPasswordException(
                    PropertiesSourceMessage.getMessageSource("invalid.user")
            );
        }
    }
}
