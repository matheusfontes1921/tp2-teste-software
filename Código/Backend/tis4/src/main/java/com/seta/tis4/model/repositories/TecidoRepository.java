package com.seta.tis4.model.repositories;

import com.seta.tis4.model.entities.produto.Tecido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TecidoRepository extends JpaRepository<Tecido, UUID> {
}
