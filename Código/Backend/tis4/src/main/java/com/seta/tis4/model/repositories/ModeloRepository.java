package com.seta.tis4.model.repositories;

import com.seta.tis4.model.entities.produto.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, UUID> {}
