package com.cash.register.domain.repository;

import com.cash.register.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("from Client c where lower(c.name) like %:nameClient% " +
            "or lower(c.cpf) like %:cpfClient% " +
            "or lower(c.city) like %:cityClient% " +
            "or lower(c.uf) like %:ufClient%")
    List<Client> filter(@Param("nameClient") String nameClient, @Param("cpfClient") String cpfClient, @Param("cityClient") String cityClient, @Param("ufClient") String ufClient);

    Client findByCpf(String login);

    Client findByEmail(String email);
}
