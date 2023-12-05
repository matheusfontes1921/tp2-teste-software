package com.seta.tis4.services;

import com.seta.tis4.model.dtos.Option;
import com.seta.tis4.model.dtos.produto.ModeloDTO;
import com.seta.tis4.model.entities.produto.Modelo;
import com.seta.tis4.model.repositories.ModeloRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ModeloService {
    private final ModeloRepository modeloRepository;

    public ModeloService(ModeloRepository modeloRepository) {
        this.modeloRepository = modeloRepository;
    }

    public ResponseEntity<?> options() {
        List<Modelo> modeloList = modeloRepository.findAll();

        if(modeloList.isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe modelo cadastrados");
        }

        List<Option> options = new ArrayList<>();

        modeloList.forEach(modelo -> options.add(Option.builder()
                        .value(modelo.getId())
                        .label(modelo.getDetalhes())
                .build()));

        return ResponseEntity.ok().body(options);
    }

    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(modeloRepository.findAll());
    }

    public ResponseEntity<?> create(ModeloDTO dto) {
        return ResponseEntity.ok().body(modeloRepository.save(dto.mapToModelo(dto)));
    }

    public ResponseEntity<?> update(ModeloDTO dto) {

        Optional<Modelo> modelo = modeloRepository.findById(dto.id());
        if (modelo.isPresent()) {
            Modelo obj = modelo.get();
            obj.setDetalhes(dto.detalhes());

            return ResponseEntity.ok().body(modeloRepository.saveAndFlush(obj));
        }

        return ResponseEntity.ok().body("ta errado");
    }

    public ResponseEntity<?> delete(UUID id) {
        modeloRepository.deleteById(id);
        Optional<Modelo> modelo = modeloRepository.findById(id);
        if (modelo.isEmpty()) {
            return ResponseEntity.ok().body("apagado");
        }
        return ResponseEntity.badRequest().body("id nao existe");
    }
}
