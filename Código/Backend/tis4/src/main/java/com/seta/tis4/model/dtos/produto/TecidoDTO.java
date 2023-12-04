package com.seta.tis4.model.dtos.produto;

import com.seta.tis4.model.entities.produto.Tecido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record TecidoDTO(
        @NotBlank UUID id,
        @NotEmpty
        String nome,
        @NotEmpty
        String codigo,
        @NotEmpty
        String fornecedor,
        @NotEmpty
        String classificacao
) {
    public  Tecido mapToTecido(TecidoDTO dto) {
        if (dto == null) {
            return null;
        }

        Tecido tecido = new Tecido();
        tecido.setNome(dto.nome);
        tecido.setCodigo(dto.codigo);
        tecido.setFornecedor(dto.fornecedor);
        tecido.setClassificacao(dto.classificacao);

        return tecido;
    }
}
