package com.seta.tis4.controllers.produto;

import com.seta.tis4.model.dtos.produto.TipoDTO;
import com.seta.tis4.services.TipoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/tipo/")
public class TipoController {
    private final TipoService tipoService;

    public TipoController(TipoService tipoService) {
        this.tipoService = tipoService;
    }

    @GetMapping("options")
    public ResponseEntity<?> getName() {
        return tipoService.options();
    }

    @GetMapping("get")
    public ResponseEntity<?> get() {
        return tipoService.get();
    }

    @PostMapping("create")
    public ResponseEntity<?> createTecido(@RequestBody TipoDTO tipoDTO) {
        return tipoService.create(tipoDTO);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateTecido(@RequestBody TipoDTO tipoDTO) {
        return tipoService.update(tipoDTO);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteTecido(@PathVariable UUID id) {
        return tipoService.delete(id);
    }

}
