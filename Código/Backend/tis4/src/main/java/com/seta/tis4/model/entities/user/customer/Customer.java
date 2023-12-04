package com.seta.tis4.model.entities.user.customer;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @Column(unique = true)
    private String telefone;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String endereco;

    @Column(unique = true)
    private String cpfCnpj;

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

}
