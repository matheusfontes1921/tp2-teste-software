package com.seta.tis4.model.repositories;

import com.seta.tis4.model.entities.produto.Bolso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BolsoRepository extends JpaRepository<Bolso, Long> {
}
