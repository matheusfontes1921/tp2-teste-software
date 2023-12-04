package com.seta.tis4.model.dtos.produto;

import com.seta.tis4.model.entities.produto.Modelo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record ModeloDTO(
        @NotBlank
        UUID id,
        @NotEmpty
        String detalhes
) {

    public Modelo mapToModelo(ModeloDTO dto) {
        if(dto == null) {
            return null;
        }

        Modelo modelo = new Modelo();
        modelo.setDetalhes(dto.detalhes);

        return modelo;
    }
}
