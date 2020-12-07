package com.cash.register.domain.repository;

import com.cash.register.domain.entity.UserEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEmployeeRepository extends JpaRepository<UserEmployee, Long> {

    @Query("from UserEmployee u where lower(u.name) like %:nameUser% or lower(u.email) like %:emailUser%")
    List<UserEmployee> findFilter(@Param("nameUser") String nameUser, @Param("emailUser") String emailUser);

    UserEmployee findByLogin(String login);

    UserEmployee findByEmail(String email);
}
