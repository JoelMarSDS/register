package com.cash.register.domain.settings.config.security;

import com.cash.register.domain.entity.UserEmployee;
import com.cash.register.domain.entity.dto.UserEmployeeDTO;
import com.cash.register.domain.repository.UserEmployeeRepository;
import com.cash.register.domain.settings.config.messageproperties.PropertiesSourceMessage;
import com.cash.register.domain.settings.useful.InterfaceConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {

    @Autowired
    private UserEmployeeRepository userEmployeeRepository;

    @Autowired
    private InterfaceConverterDTO<UserEmployeeDTO, UserEmployee> dtoUserInterfaceConverterDTO;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEmployee userEmployee = userEmployeeRepository.findByLogin(login);
        UserEmployeeDTO userEmployeeDTO = dtoUserInterfaceConverterDTO.convertTo(userEmployee);

        if (userEmployeeDTO == null) {
            throw new UsernameNotFoundException(PropertiesSourceMessage.getMessageSource("msg.user.not.found.error"));
        }

        return new User(userEmployeeDTO.getUsername(), userEmployee.getPassword(),
                true, true, true,
                true, userEmployeeDTO.getAuthorities());
    }
}
