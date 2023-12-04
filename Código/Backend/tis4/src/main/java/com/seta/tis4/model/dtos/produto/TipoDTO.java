package com.seta.tis4.model.dtos.produto;

import com.seta.tis4.model.entities.produto.Tipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record TipoDTO(
        @NotBlank
        UUID id,
        @NotEmpty
        String tipo
) {
    public Tipo mapToTipo(TipoDTO dto) {
        if(dto == null) {
            return null;
        }

        Tipo tipo = new Tipo();
        tipo.setTipo(dto.tipo);

        return tipo;
    }
}
