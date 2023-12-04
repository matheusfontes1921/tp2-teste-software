package com.seta.tis4.model.entities.purchase;

import com.seta.tis4.model.entities.user.Employee;
import com.seta.tis4.model.entities.produto.Produto;
import com.seta.tis4.model.entities.user.customer.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @OneToMany
    @Setter(AccessLevel.NONE)
    private List<Produto> products = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.ATIVO;

}
