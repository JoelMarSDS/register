package com.cash.register.domain.service;

import com.cash.register.domain.entity.UserEmployee;

import java.util.List;

public interface UserEmployeeService {

    UserEmployee addUserEmployee(UserEmployee userEmployee);

    UserEmployee updateUserEmployee(Long id, UserEmployee userEmployee);

    UserEmployee getUserEmployeeById(Long id);

    List<UserEmployee> getUserEmployeeList();

    List<UserEmployee> getUserEmployeeFilter(String name, String email);

    UserEmployee deleteUserEmployeeById(Long id);
}
