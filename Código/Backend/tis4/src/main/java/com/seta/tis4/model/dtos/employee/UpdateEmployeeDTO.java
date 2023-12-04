package com.seta.tis4.model.dtos.employee;

import com.seta.tis4.model.entities.user.Employee;

import java.util.UUID;

public record UpdateEmployeeDTO(UUID id, String nome, String email, String senha) {
    public Employee toEmployee() {
        return new Employee(nome, email, senha);
    }
}
