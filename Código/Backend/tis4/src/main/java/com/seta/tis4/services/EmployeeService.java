package com.seta.tis4.services;

import com.seta.tis4.model.dtos.employee.CreateEmployeeDTO;
import com.seta.tis4.model.dtos.employee.LoginDto;
import com.seta.tis4.model.dtos.employee.UpdateEmployeeDTO;
import com.seta.tis4.model.entities.user.Employee;
import com.seta.tis4.model.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder encoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder encoder) {
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
    }

    public ResponseEntity<?> getName() {
        Map<UUID, String> userMap = new HashMap<>();

        employeeRepository.findAll().forEach(employee -> {
            userMap.put(employee.getId(), employee.getNome());
        });

        return ResponseEntity.ok(userMap);
    }

    public ResponseEntity<?> createEmployee(CreateEmployeeDTO dto) {
        if (existsByEmail(dto.email())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        } else {
            String senhaCriptografada = encoder.encode(dto.senha());
            return ResponseEntity.ok().body(employeeRepository.save(dto.toEmployee(senhaCriptografada)));
        }
    }

    public ResponseEntity<?> deleteEmployee(UUID id) {
        if (employeeRepository.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe um funcionário com esse id");
        } else {
            employeeRepository.deleteById(id);
            return ResponseEntity.ok().body("Funcionário deletado com sucesso");
        }
    }

    public ResponseEntity<?> updateEmployee(UpdateEmployeeDTO dto) {
        var optionalEmployee = employeeRepository.findById(dto.id());
        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe um funcionário com esse id");
        }
        Employee employee = optionalEmployee.get();
        employee.setSenha(encoder.encode(dto.senha()));

        return ResponseEntity.ok(employeeRepository.saveAndFlush(employee));
    }


    public ResponseEntity<?> login(LoginDto dto) {
        if (!existsByEmail(dto.email())) {
            return ResponseEntity.badRequest().body("Email incorreto");
        } else {
            Employee employee = employeeRepository.findByEmail(dto.email()).get();
            if (encoder.matches(dto.senha(), employee.getSenha())) {
                return ResponseEntity.status(HttpStatus.OK).body(employee);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
        }
    }

    public ResponseEntity<?> getEmployee() {
        return ResponseEntity.ok().body(employeeRepository.findAll());
    }
    private boolean existsByEmail(String email) {
        return employeeRepository.findByEmail(email).isPresent();
    }




}
