package com.cash.register.domain.service.serviceimpl;

import com.cash.register.domain.entity.UserEmployee;
import com.cash.register.domain.repository.UserEmployeeRepository;
import com.cash.register.domain.service.UserEmployeeService;
import com.cash.register.domain.settings.config.messageproperties.PropertiesSourceMessage;
import com.cash.register.domain.settings.exceptions.BusinessException;
import com.cash.register.domain.settings.exceptions.ConflictException;
import com.cash.register.domain.settings.exceptions.UsersNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class UserEmployeeServiceImpl implements UserEmployeeService {

    private static final String DDMMYY_HHMMSS = "dd-MM-yyyy HH:mm:ss";

    @Autowired
    private UserEmployeeRepository userEmployeeRepository;

    @Override
    public UserEmployee addUserEmployee(UserEmployee userEmployee) {

        exceptionUserEmployee(userEmployee);

        try {
            LocalDateTime localTimeNow = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DDMMYY_HHMMSS);
            localTimeNow.format(dateTimeFormatter);

            userEmployee.setDateRegister(localTimeNow);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userEmployeeRepository.save(userEmployee);
    }

    @Override
    public UserEmployee updateUserEmployee(Long id, UserEmployee userEmployee) {
        return userEmployeeRepository.findById(id)
                .map(us -> {

                    us.setName(userEmployee.getName());

                    if (userEmployee.getLogin() != null && userEmployee.getEmail() != null) {

                        exceptionUserEmployee(userEmployee);

                        us.setLogin(userEmployee.getLogin());
                        us.setEmail(userEmployee.getEmail());
                    }

                    us.setPassword(userEmployee.getPassword());
                    us.setTelephone(userEmployee.getTelephone());

                    exceptionUserEmployee(userEmployee);

                    us.setProfile(userEmployee.getProfile());
                    us.setStatus(userEmployee.getStatus());

                    return userEmployeeRepository.save(us);
                }).orElseThrow(
                        () -> new EntityNotFoundException(PropertiesSourceMessage
                                .getMessageSource("msg.user.not.found.error"))
                );
    }

    @Override
    public UserEmployee getUserEmployeeById(Long id) {
        return userEmployeeRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(PropertiesSourceMessage
                                .getMessageSource("msg.user.not.found.error"))
                );
    }

    @Override
    public List<UserEmployee> getUserEmployeeList() {

        var userEmployeeList = userEmployeeRepository.findAll();
        try {
            if (userEmployeeList.isEmpty()) {
                throw new UsersNotFoundException(PropertiesSourceMessage
                        .getMessageSource("msg.is.empty"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userEmployeeList;
    }

    @Override
    public List<UserEmployee> getUserEmployeeFilter(String name, String email) {
        var listUserEmployee = userEmployeeRepository.filter(name.toLowerCase(), email.toLowerCase());
        try {
            if (listUserEmployee.isEmpty()) {
                throw new UsersNotFoundException(PropertiesSourceMessage
                        .getMessageSource("msg.is.empty"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listUserEmployee;
    }


    @Override
    public UserEmployee deleteUserEmployeeById(Long id) {
        var userEmployeeDelete = getUserEmployeeById(id);
        userEmployeeRepository.deleteById(userEmployeeDelete.getId());
        return userEmployeeDelete;
    }


    private void exceptionUserEmployee(UserEmployee userEmployee) {

        String character;

        var loginExisting = userEmployeeRepository.findByLogin(userEmployee.getLogin());
        if (loginExisting != null) {
            throw new ConflictException(PropertiesSourceMessage.getMessageSource("msg.user.conflict.login"));
        }

        loginExisting = userEmployeeRepository.findByEmail(userEmployee.getEmail());
        if (loginExisting != null) {
            throw new ConflictException(PropertiesSourceMessage.getMessageSource("msg.user.conflict.email"));
        }

        character = userEmployee.getProfile().toString();
        character.toLowerCase();
        if (!character.equalsIgnoreCase("A") && !character.equalsIgnoreCase("O")) {
            throw new BusinessException(PropertiesSourceMessage.getMessageSource("msg.properties.profile.error"));
        }

        character = userEmployee.getStatus().toString();
        character.toLowerCase();
        if (!character.equalsIgnoreCase("A") && !character.equalsIgnoreCase("C")) {
            throw new BusinessException(PropertiesSourceMessage.getMessageSource("msg.properties.status.error"));
        }
    }
}
