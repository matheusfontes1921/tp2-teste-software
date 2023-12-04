package com.seta.tis4.model.dtos.produto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record GetProdutoDTO(
        UUID id,
        String product
) {}
