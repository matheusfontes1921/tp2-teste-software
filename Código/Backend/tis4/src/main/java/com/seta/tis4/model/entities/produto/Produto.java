package com.seta.tis4.model.entities.produto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private Modelo modelo;
    @ManyToOne
    private Tecido tecido;
    @ManyToOne
    private Tipo tipo;

    //BOLSO
    private Integer quantidadeBolso;
    private String descricaoBolso;

    //LOGO
    private String descricaoLogo;
    private Boolean silkando;
    @Column(columnDefinition = "text")
    private String urlImagem;


}
