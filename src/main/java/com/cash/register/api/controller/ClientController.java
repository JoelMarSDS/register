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
    public ResponseEntity<Client> addUser(@RequestBody Client client) {
        var clientSave = clientService.addClient(client);
        URI uri = URI.create("/user/" + clientSave.getId());
        return ResponseEntity.created(uri).body(clientSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateUser(@PathVariable Long id, @RequestBody Client user) {
        var updateClient = clientService.updateClient(id, user);
        return ResponseEntity.ok(updateClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Client>> getUserList() {
        return ResponseEntity.ok(clientService.getClientList());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Client>> getUserFilter(
            @RequestParam String name,
            @RequestParam String cpf,
            @RequestParam String city,
            @RequestParam String uf
    ) {
        return ResponseEntity.ok(clientService.getClientFilter(name, cpf, city, uf));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.deleteClientById(id));
    }
}
