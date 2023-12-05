package com.seta.tis4.model.dtos.produto;

import com.seta.tis4.model.entities.produto.Bolso;

public record BolsoDTO( Long id,
                       Integer quantidadeBolsos,
                       String descricao) {
    public Bolso maptoBolso() {
        Bolso bolso = new Bolso();
        bolso.setQuantidadeBolsos(this.quantidadeBolsos());
        bolso.setDescricao(this.descricao());
        return bolso;
    }


}
