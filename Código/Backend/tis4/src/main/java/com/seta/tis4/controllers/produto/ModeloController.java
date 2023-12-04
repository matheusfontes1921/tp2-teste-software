package com.seta.tis4.controllers.produto;

import com.seta.tis4.model.dtos.produto.ModeloDTO;
import com.seta.tis4.model.services.ModeloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/modelo/")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @GetMapping("options")
    public ResponseEntity<?> getName() {
        return modeloService.options();
    }

    @GetMapping("get")
    public ResponseEntity<?> getAll() {
        return modeloService.getAll();
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody ModeloDTO modeloDTO) {
        return modeloService.create(modeloDTO);
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody ModeloDTO dto) {
        return modeloService.update(dto);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return modeloService.delete(id);
    }


}
