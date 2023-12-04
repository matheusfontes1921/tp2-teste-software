package com.seta.tis4.model.repositories;

import com.seta.tis4.model.entities.user.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    boolean existsByEmail(String email);

    Boolean existsByCpfCnpj(String cpf_cnpj);

    boolean existsByTelefone(String telefone);
}
