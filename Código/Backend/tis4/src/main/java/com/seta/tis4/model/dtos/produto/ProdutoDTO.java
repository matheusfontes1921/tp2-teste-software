package com.seta.tis4.model.dtos.produto;

import com.seta.tis4.model.entities.produto.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record ProdutoDTO(
        @NotBlank UUID id,
        @NotEmpty UUID idTipo,
        @NotEmpty UUID idModelo,
        @NotEmpty UUID idTecido,
        @NotEmpty String descricaoBolso,
        @NotEmpty Integer quantidadeBolso,
        @NotEmpty String descricaoLogo,
        @NotEmpty Boolean silkando,
        @NotEmpty String urlImagem
) {
    public Produto toProduto(Tecido tecido, Modelo modelo, Tipo tipo) {
        Produto produto = new Produto();

        //Informacoes do bolso
        produto.setDescricaoBolso(this.descricaoBolso);
        produto.setQuantidadeBolso(this.quantidadeBolso);

        //Informacoes da logo
        produto.setSilkando(this.silkando);
        produto.setDescricaoLogo(this.descricaoLogo);
        produto.setUrlImagem(this.urlImagem);

        produto.setModelo(modelo);
        produto.setTecido(tecido);
        produto.setTipo(tipo);


        return produto;
    }
}
