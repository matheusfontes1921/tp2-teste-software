package com.seta.tis4.model.dtos.employee;

import com.seta.tis4.model.entities.user.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record CreateEmployeeDTO(@NotBlank UUID id, @NotEmpty String nome, @NotEmpty String email, @NotEmpty String senha) {

    public Employee toEmployee(String senhaCriptografada) {

        return new Employee(nome, email, senhaCriptografada);
    }

}
