package com.seta.tis4.services;

import com.seta.tis4.model.dtos.Option;
import com.seta.tis4.model.dtos.produto.TecidoDTO;
import com.seta.tis4.model.entities.produto.Tecido;
import com.seta.tis4.model.repositories.TecidoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TecidoService {
    private final TecidoRepository tecidoRepository;

    public TecidoService(TecidoRepository tecidoRepository) {
        this.tecidoRepository = tecidoRepository;
    }

    public ResponseEntity<?> options() {
        List<Tecido> tecidoList = tecidoRepository.findAll();

        if(tecidoList.isEmpty()) {
            return ResponseEntity.badRequest().body("Não existe tecidos cadastrados");
        }

        List<Option> options = new ArrayList<>();

        tecidoList.forEach(tecido -> options.add(Option.builder()
                        .value(tecido.getId())
                        .label(tecido.getNome())
                .build()));

        return ResponseEntity.ok().body(options);
    }

    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(tecidoRepository.findAll());
    }

    public ResponseEntity<?> create(TecidoDTO dto) {
        Tecido tecido = dto.mapToTecido(dto);
        return ResponseEntity.ok().body(tecidoRepository.save(tecido));
    }

    public ResponseEntity<?> update(TecidoDTO dto) {

        Optional<Tecido> tecido = tecidoRepository.findById(dto.id());
        if (tecido.isPresent()) {
            Tecido obj = tecido.get();
            obj.setNome(dto.nome());
            obj.setCodigo(dto.codigo());
            obj.setFornecedor(dto.fornecedor());
            obj.setClassificacao(dto.classificacao());
            return ResponseEntity.ok().body(tecidoRepository.saveAndFlush(obj));
        }

        return ResponseEntity.ok().body("ta errado");
    }

    public ResponseEntity<?> delete(UUID id) {
        tecidoRepository.deleteById(id);
        Optional<Tecido> tecido = tecidoRepository.findById(id);
        if (tecido.isEmpty()) {
            return ResponseEntity.ok().body("apagado");
        }
        return ResponseEntity.badRequest().body("id nao existe");
    }
}
