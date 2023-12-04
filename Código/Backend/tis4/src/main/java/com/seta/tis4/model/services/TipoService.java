package com.seta.tis4.model.services;

import com.seta.tis4.model.dtos.Option;
import com.seta.tis4.model.dtos.produto.TipoDTO;
import com.seta.tis4.model.entities.produto.Tipo;
import com.seta.tis4.model.repositories.TipoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TipoService {
    private final TipoRepository tipoRepository;

    public TipoService(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }

    public ResponseEntity<?> options() {
        List<Tipo> tipoList = tipoRepository.findAll();

        if(tipoList.isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe tipos cadastrados");
        }

        List<Option> options = new ArrayList<>();

        tipoList.forEach(tipo -> options.add(Option.builder()
                        .value(tipo.getId())
                        .label(tipo.getTipo())
                .build()));

        return ResponseEntity.ok().body(options);
    }

    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(tipoRepository.findAll());
    }

    public ResponseEntity<?> create(TipoDTO dto) {

        Tipo tipo = dto.mapToTipo(dto);
        return ResponseEntity.ok().body(tipoRepository.save(tipo));
    }

    public ResponseEntity<?> update(TipoDTO dto) {

        Optional<Tipo> tipo = tipoRepository.findById(dto.id());
        if (tipo.isPresent()) {
            Tipo obj = tipo.get();
            obj.setTipo(dto.tipo());

            return ResponseEntity.ok().body(tipoRepository.saveAndFlush(obj));
        }

        return ResponseEntity.ok().body("ta errado");
    }

    public ResponseEntity<?> delete(UUID id) {
        tipoRepository.deleteById(id);
        Optional<Tipo> tipo = tipoRepository.findById(id);
        if (tipo.isEmpty()) {
            return ResponseEntity.ok().body("apagado");
        }
        return ResponseEntity.badRequest().body("id nao existe");
    }
}
