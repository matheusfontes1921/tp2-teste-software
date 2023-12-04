package com.seta.tis4.model.entities.produto;

import com.seta.tis4.model.entities.Imagem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LogoMarca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private boolean silkado;
    @OneToOne
    private Imagem imagem;

    public LogoMarca(String descricao, boolean silkado, Imagem imagem) {
        this.descricao = descricao;
        this.silkado = silkado;
        this.imagem = imagem;
    }
}
