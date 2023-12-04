package com.seta.tis4.controllers.produto;

import com.seta.tis4.model.dtos.produto.TecidoDTO;
import com.seta.tis4.model.services.TecidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/tecido/")
public class TecidoController {
    private final TecidoService tecidoService;

    public TecidoController(TecidoService tecidoService) {
        this.tecidoService = tecidoService;
    }

    @GetMapping("options")
    public ResponseEntity<?> getName() {
        return tecidoService.options();
    }

    @GetMapping("get")
    public ResponseEntity<?> get() {
        return tecidoService.get();
    }

    @PostMapping("create")
    public ResponseEntity<?> createTecido(@RequestBody TecidoDTO tecidoDTO) {
        return tecidoService.create(tecidoDTO);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateTecido(@RequestBody TecidoDTO tecidoDTO) {
        return tecidoService.update(tecidoDTO);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteTecido(@PathVariable UUID id) {
        return tecidoService.delete(id);
    }
}

