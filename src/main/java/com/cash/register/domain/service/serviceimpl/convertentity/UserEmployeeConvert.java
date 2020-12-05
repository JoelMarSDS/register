package com.cash.register.domain.service.serviceimpl.convertentity;

import com.cash.register.domain.entity.UserEmployee;
import com.cash.register.domain.entity.dto.UserEmployeeDTO;
import com.cash.register.domain.settings.useful.InterfaceConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class UserEmployeeConvert implements InterfaceConverterDTO<UserEmployeeDTO,UserEmployee> {

    @Autowired
    private UserEmployee userEmployee;

    @Autowired
    private UserEmployeeDTO userEmployeeDTO;

    @Override
    public UserEmployeeDTO convertTo(UserEmployee u) {
        userEmployeeDTO.setId(u.getId());
        userEmployeeDTO.setLogin(u.getLogin());
        userEmployeeDTO.setPassword(u.getPassword());
        userEmployeeDTO.getRoleDTO().setProfile(u.getProfile());
        userEmployeeDTO.getRoleDTO().setStatus(u.getStatus());

        return userEmployeeDTO;

    }

    @Override
    public UserEmployee convertBack(UserEmployeeDTO userEmployeeDTO) {
        return null;
    }
}
