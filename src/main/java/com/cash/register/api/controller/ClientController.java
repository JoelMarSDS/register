package com.cash.register.api.controller;

import com.cash.register.domain.entity.Client;
import com.cash.register.domain.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> addUser(@RequestBody Client client, @RequestHeader String name, @RequestHeader String password) {
        var clientSave = clientService.addClient(client, name, password);
        URI uri = URI.create("/user/" + clientSave.getId());
        return ResponseEntity.created(uri).body(clientSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateUser(@PathVariable Long id, @RequestBody Client user, @RequestHeader String name, @RequestHeader String password) {
        var updateClient = clientService.updateClient(id, user, name, password);
        return ResponseEntity.ok(updateClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getUserById(@PathVariable Long id, @RequestHeader String name, @RequestHeader String password) {
        return ResponseEntity.ok(clientService.getClientById(id, name, password));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Client>> getUserList(@RequestHeader String name, @RequestHeader String password) {
        return ResponseEntity.ok(clientService.getClientList(name, password));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Client>> getUserFilter(
            @RequestParam String nameParam,
            @RequestParam String cpf,
            @RequestParam String city,
            @RequestParam String uf,
            @RequestHeader String name,
            @RequestHeader String password
    ) {
        return ResponseEntity.ok(clientService.getClientFilter(nameParam, cpf, city, uf, name, password));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteUserById(@PathVariable Long id, @RequestHeader String name, @RequestHeader String password) {
        return ResponseEntity.ok(clientService.deleteClientById(id, name, password));
    }
}
