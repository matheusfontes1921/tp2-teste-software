package com.seta.tis4.model.dtos.customer;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerDTO(
        @NotBlank String nome,
        @NotBlank String cpfCnpj,
        @NotBlank String endereco,
        @NotBlank String telefone,
        @NotBlank String email
) {}
