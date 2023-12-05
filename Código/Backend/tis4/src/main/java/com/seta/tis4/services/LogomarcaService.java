package com.seta.tis4.services;

import com.seta.tis4.model.dtos.produto.LogoMarcaDTO;
import com.seta.tis4.model.entities.produto.LogoMarca;
import com.seta.tis4.model.repositories.ImagemRepository;
import com.seta.tis4.model.repositories.LogomarcaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogomarcaService {

    private final LogomarcaRepository logomarcaRepository;
    private final ImagemRepository imagemRepository;

    public LogomarcaService(LogomarcaRepository logomarcaRepository, ImagemRepository imagemRepository) {
        this.logomarcaRepository = logomarcaRepository;
        this.imagemRepository = imagemRepository;
    }

    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(logomarcaRepository.findAll());
    }

    public ResponseEntity<?> create(LogoMarcaDTO dto) {
        LogoMarca logo = dto.mapToLogomarca();
        imagemRepository.save(logo.getImagem());

        return ResponseEntity.ok(logomarcaRepository.save(logo));
    }

    public ResponseEntity<?> delete(Long id) {
        logomarcaRepository.deleteById(id);
        Optional<LogoMarca> tecido = logomarcaRepository.findById(id);
        if (tecido.isEmpty()) {
            return ResponseEntity.ok().body("apagado");
        }
        return ResponseEntity.badRequest().body("id nao existe");
    }

    public ResponseEntity<?> update(LogoMarcaDTO dto) {

        Optional<LogoMarca> tecido = logomarcaRepository.findById(dto.id());
        if (tecido.isPresent()) {
            LogoMarca obj = tecido.get();
            obj.setId(dto.id());
            obj.setDescricao(dto.descricao());
            obj.setSilkado(dto.silkado());
            return ResponseEntity.ok().body(logomarcaRepository.save(obj));
        }

        return ResponseEntity.ok().body("ta errado");
    }
}
