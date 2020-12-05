package com.cash.register.domain.repository;

import com.cash.register.domain.entity.UserEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEmployeeRepository extends JpaRepository<UserEmployee, Long> {

    @Query("from User u " +
            "where lower(u.name) like %:nameUserEmployee% " +
            "or lower(u.email) like %:emailUserEmployee%")
    List<UserEmployee> filter(@Param("nameUserEmployee") String nameUserEmployee,
                              @Param("emailUserEmployee") String emailUserEmployee);

    UserEmployee findByLogin(String login);

    UserEmployee findByEmail(String email);
}
