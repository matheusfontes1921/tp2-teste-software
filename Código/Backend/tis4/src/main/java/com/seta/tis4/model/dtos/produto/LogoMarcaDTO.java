package com.seta.tis4.model.dtos.produto;

import com.seta.tis4.model.entities.Imagem;
import com.seta.tis4.model.entities.produto.LogoMarca;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record LogoMarcaDTO(
        @NotBlank
        Long id,
        @NotEmpty
        String descricao,
        @NotEmpty
        boolean silkado,
        @NotEmpty
        String imagem
) {

    public LogoMarca mapToLogomarca() {
        LogoMarca logo = new LogoMarca();
        Imagem img = new Imagem(this.imagem);

        logo.setDescricao(this.descricao());
        logo.setSilkado(this.silkado);
        logo.setImagem(img);

        return logo;
    }
}
