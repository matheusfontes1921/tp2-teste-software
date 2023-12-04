package com.seta.tis4.model.entities.produto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bolso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidadeBolsos;
    private String descricao;

    public Bolso(Integer quantidadeBolsos, String descricao) {
        this.quantidadeBolsos = quantidadeBolsos;
        this.descricao = descricao;
    }
}
