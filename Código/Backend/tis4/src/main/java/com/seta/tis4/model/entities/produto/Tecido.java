package com.seta.tis4.model.entities.produto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tecido {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String codigo;
    private String fornecedor;
    private String classificacao;

    public Tecido(String nome, String codigo, String fornecedor, String classificacao) {
        this.nome = nome;
        this.codigo = codigo;
        this.fornecedor = fornecedor;
        this.classificacao = classificacao;
    }

    @Override
    public String toString() {
        return this.nome + " " + this.codigo + " " + this.fornecedor + " " + this.classificacao;
    }
}
