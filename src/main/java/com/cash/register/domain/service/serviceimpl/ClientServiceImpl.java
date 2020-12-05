package com.cash.register.domain.service.serviceimpl;

import com.cash.register.domain.entity.Client;
import com.cash.register.domain.repository.ClientRepository;
import com.cash.register.domain.service.ClientService;
import com.cash.register.domain.settings.config.cpfcnpj.CpfCnpj;
import com.cash.register.domain.settings.config.messageproperties.PropertiesSourceMessage;
import com.cash.register.domain.settings.exceptions.ConflictException;
import com.cash.register.domain.settings.exceptions.UsersNotFoundException;
import com.cash.register.domain.settings.exceptions.ValidationException;
import com.cash.register.domain.settings.exceptions.ValidationNotNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ClientServiceImpl implements ClientService {

    private static final String DDMMYY_HHMMSS = "dd-MM-yyyy HH:mm:ss";

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client addClient(Client client) {

        exceptionClient(client);

        try {
            LocalDateTime localTimeNow = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DDMMYY_HHMMSS);
            localTimeNow.format(dateTimeFormatter);

            client.setDateRegister(localTimeNow);
            client.setUf(client.getUf().toUpperCase());
            client.setCity(toUpperCaseFirstLetter(client.getCity()));
            client.setCpf(replace(client.getCpf()));
            client.setCep(replace(client.getCep()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Long id, Client client) {
        return clientRepository.findById(id)
                .map(uc -> {
                    exceptionClient(client);

                    uc.setName(client.getName());
                    uc.setCpf(replace(client.getCpf()));
                    uc.setAddress(client.getAddress());
                    uc.setCity(toUpperCaseFirstLetter(client.getCity()));
                    uc.setUf(client.getUf());
                    uc.setCep(replace(client.getCep()));

                    if (client.getEmail() != null && client.getTelephone() != null) {
                        uc.setEmail(client.getEmail());
                        uc.setTelephone(client.getTelephone());
                    }

                    return clientRepository.save(uc);
                }).orElseThrow(
                        () -> new EntityNotFoundException(PropertiesSourceMessage
                                .getMessageSource("msg.client.not.found.error"))
                );
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(PropertiesSourceMessage
                                .getMessageSource("msg.client.not.found.error"))
                );
    }

    @Override
    public List<Client> getClientList() {
        var userList = clientRepository.findAll();
        try {
            if (userList.isEmpty()) {
                throw new UsersNotFoundException(PropertiesSourceMessage
                        .getMessageSource("msg.is.empty"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public List<Client> getClientFilter(String name, String cpf, String city, String uf) {
        var listUser = clientRepository.filter(
                name.toLowerCase(),
                cpf.toUpperCase(),
                city.toLowerCase(),
                uf.toLowerCase()
        );
        try {
            if (listUser.isEmpty()) {
                throw new UsersNotFoundException(PropertiesSourceMessage
                        .getMessageSource("msg.is.empty"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listUser;
    }

    @Override
    public Client deleteClientById(Long id) {
        var userDelete = clientRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(PropertiesSourceMessage
                                .getMessageSource("msg.client.not.found.error"))
                );
        clientRepository.deleteById(userDelete.getId());
        return userDelete;
    }

    private String toUpperCaseFirstLetter(String temp) {
        char tempUpperCase = Character.toTitleCase(temp.charAt(0));
        return tempUpperCase + temp.substring(1);
    }

    private String replace(String temp) {
        return temp.replace(".", "")
                .replace("-", "")
                .replace(" ", "")
                .replace("/", "");
    }

    private void exceptionClient(Client client) {

        if (client.getCpf() == null) {
            throw new ValidationNotNullException(PropertiesSourceMessage.getMessageSource("msg.client.valid.not.null.cpf"));
        }

        var clientTemp = clientRepository.findByCpf(client.getCpf());
        if (clientTemp != null) {
            throw new ConflictException(PropertiesSourceMessage.getMessageSource("msg.client.conflict.cpf"));
        }

        if (CpfCnpj.isValid(client.getCpf())) {
            throw new ValidationException(PropertiesSourceMessage.getMessageSource("msg.client.valid.cpf"));
        }
    }
}
