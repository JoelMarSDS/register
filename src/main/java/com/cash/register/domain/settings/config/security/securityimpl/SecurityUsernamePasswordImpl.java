package com.cash.register.domain.settings.config.security.securityimpl;

import com.cash.register.domain.repository.UserEmployeeRepository;
import com.cash.register.domain.service.UserEmployeeService;
import com.cash.register.domain.service.serviceimpl.UserEmployeeServiceImpl;
import com.cash.register.domain.settings.config.security.SecurityUsernamePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUsernamePasswordImpl implements SecurityUsernamePassword {

    @Autowired
    UserEmployeeRepository userEmployeeRepository;

    @Override
    public boolean namePassword(String login, String password) {

        var userEmployee = userEmployeeRepository.findByLogin(login);

        if (userEmployee.getPassword().equals(password) &&
                userEmployee.getStatus().toString().equalsIgnoreCase("a") ){
            return true;
        }else {
            return false;
        }
    }
}