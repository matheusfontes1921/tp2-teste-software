package com.seta.tis4.model.dtos.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record EditCustomerDTO(
        @NotBlank UUID id,
        @NotEmpty String endereco,
        @NotEmpty String telefone,
        @NotEmpty String email
) {
}
