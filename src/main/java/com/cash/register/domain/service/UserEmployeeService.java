package com.cash.register.domain.service;

import com.cash.register.domain.entity.UserEmployee;

import java.util.List;

public interface UserEmployeeService {

    UserEmployee addUserEmployee(UserEmployee userEmployee, String name, String password);

    UserEmployee updateUserEmployee(Long id, UserEmployee userEmployee, String name, String password);

    UserEmployee getUserEmployeeById(Long id, String name, String password);

    List<UserEmployee> getUserEmployeeList(String name, String password);

    List<UserEmployee> getUserEmployeeFilter(String nameParam, String email, String name, String password);

    UserEmployee deleteUserEmployeeById(Long id, String name, String password);
}
