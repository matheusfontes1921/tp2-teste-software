package com.seta.tis4.services;

import com.seta.tis4.model.dtos.produto.BolsoDTO;
import com.seta.tis4.model.entities.produto.Bolso;
import com.seta.tis4.model.repositories.BolsoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BolsoService {
    private final BolsoRepository bolsoRepository;

    public BolsoService(BolsoRepository bolsoRepository) {
        this.bolsoRepository = bolsoRepository;
    }

    public ResponseEntity<?> create(BolsoDTO dto) {
        Bolso bolso = dto.maptoBolso();
        Bolso bolsoCriado = bolsoRepository.save(bolso);
        if (bolsoCriado == null) {
            return ResponseEntity.badRequest().body("Erro ao criar bolso");
        }
        return ResponseEntity.ok().body(bolsoCriado.getId());
    }


    public ResponseEntity<?> delete(Long id) {
        bolsoRepository.deleteById(id);
        Optional<Bolso> bolso = bolsoRepository.findById(id);
        if (bolso.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("id nao existe");
    }

    public ResponseEntity<?> update(BolsoDTO dto) {

        Optional<Bolso> bolso = bolsoRepository.findById(dto.id());
        if (bolso.isPresent()) {
            Bolso obj = bolso.get();
            obj.setDescricao(dto.descricao());
            obj.setQuantidadeBolsos(dto.quantidadeBolsos());
            return ResponseEntity.ok().body(bolsoRepository.save(obj));
        }
        return ResponseEntity.badRequest().body("Erro no update");
    }


}
