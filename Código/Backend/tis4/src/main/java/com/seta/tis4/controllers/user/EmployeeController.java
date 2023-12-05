package com.seta.tis4.controllers.user;

import com.seta.tis4.model.dtos.employee.CreateEmployeeDTO;
import com.seta.tis4.model.dtos.employee.LoginDto;
import com.seta.tis4.model.dtos.employee.UpdateEmployeeDTO;
import com.seta.tis4.services.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/employee/")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("get")
    public ResponseEntity<?> getEmployee() {
        return employeeService.getEmployee();
    }

    @GetMapping("get_name")
    public ResponseEntity<?> getName() {
        return employeeService.getName();
    }

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createEmployee(@RequestBody CreateEmployeeDTO dto) {
        return employeeService.createEmployee(dto);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEmployee(@RequestBody UpdateEmployeeDTO updateEmployee) {
        return employeeService.updateEmployee(updateEmployee);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable UUID id) {
        return employeeService.deleteEmployee(id);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        return employeeService.login(dto);
    }
}
