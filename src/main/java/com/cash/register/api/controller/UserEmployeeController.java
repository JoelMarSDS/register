package com.cash.register.api.controller;

import com.cash.register.domain.entity.UserEmployee;
import com.cash.register.domain.service.UserEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/useremployee")
public class UserEmployeeController {

    @Autowired
    private UserEmployeeService userEmployeeService;

    @PostMapping
    public ResponseEntity<UserEmployee> addUserEmployee(@RequestBody UserEmployee userEmployee) {
        var userEmployeeSave = userEmployeeService.addUserEmployee(userEmployee);
        URI uri = URI.create("/useremployee/" + userEmployeeSave.getId());
        return ResponseEntity.created(uri).body(userEmployeeSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEmployee> updateUserEmployee(@PathVariable Long id, @RequestBody UserEmployee userEmployee) {
        var updateUserEmployee = userEmployeeService.updateUserEmployee(id, userEmployee);
        return ResponseEntity.ok(updateUserEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEmployee> getUserEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(userEmployeeService.getUserEmployeeById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserEmployee>> getUserEmployeeList() {
        return ResponseEntity.ok(userEmployeeService.getUserEmployeeList());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<UserEmployee>> getUserEmployeeFilter(@RequestParam String email,
                                                    @RequestParam String name) {

        return ResponseEntity.ok(userEmployeeService.getUserEmployeeFilter(name, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserEmployee> deleteUserEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(userEmployeeService.deleteUserEmployeeById(id));
    }
}
