package com.seta.tis4.controllers.produto;

import com.seta.tis4.model.dtos.produto.BolsoDTO;
import com.seta.tis4.model.services.BolsoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bolsos")
public class BolsoController {

    private final BolsoService bolsoService;

    public BolsoController(BolsoService bolsoService) {
        this.bolsoService = bolsoService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBolso(@RequestBody BolsoDTO bolsoDTO) {
        return bolsoService.create(bolsoDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBolso(@RequestBody BolsoDTO bolsoDTO) {
        return bolsoService.update(bolsoDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBolso(@PathVariable Long id) {
        return bolsoService.delete(id);
    }
}
