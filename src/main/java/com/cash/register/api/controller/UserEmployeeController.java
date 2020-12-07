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
    public ResponseEntity<UserEmployee> addUserEmployee(@RequestBody UserEmployee userEmployee,
                                                        @RequestHeader String name, @RequestHeader String password) {
        var userEmployeeSave = userEmployeeService.addUserEmployee(userEmployee, name, password);
        URI uri = URI.create("/useremployee/" + userEmployeeSave.getId());
        return ResponseEntity.created(uri).body(userEmployeeSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEmployee> updateUserEmployee(@PathVariable Long id, @RequestBody UserEmployee userEmployee,
                                                           @RequestHeader String name, @RequestHeader String password) {
        var updateUserEmployee = userEmployeeService.updateUserEmployee(id, userEmployee, name, password);
        return ResponseEntity.ok(updateUserEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEmployee> getUserEmployeeById(@PathVariable Long id,
                                                            @RequestHeader String name, @RequestHeader String password) {
        return ResponseEntity.ok(userEmployeeService.getUserEmployeeById(id, name, password));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserEmployee>> getUserEmployeeList(@RequestHeader String name, @RequestHeader String password) {
        return ResponseEntity.ok(userEmployeeService.getUserEmployeeList(name,password));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<UserEmployee>> getUserEmployeeFilter(@RequestParam("name") String nameParam,
                                                    @RequestParam String email, @RequestHeader String name, @RequestHeader String password) {

        return ResponseEntity.ok(userEmployeeService.getUserEmployeeFilter(nameParam, email, name, password));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserEmployee> deleteUserEmployeeById(@PathVariable Long id,
                                                               @RequestHeader String name, @RequestHeader String password) {
        return ResponseEntity.ok(userEmployeeService.deleteUserEmployeeById(id, name, password));
    }
}
