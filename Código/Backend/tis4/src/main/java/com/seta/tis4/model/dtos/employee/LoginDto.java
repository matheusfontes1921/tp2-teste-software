package com.seta.tis4.model.dtos.employee;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank String email, @NotBlank String senha) {
}
