package com.seta.tis4.controllers.produto;


import com.seta.tis4.model.dtos.produto.LogoMarcaDTO;
import com.seta.tis4.services.LogomarcaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/logomarcas")
public class LogomarcaController {

    private final LogomarcaService logomarcaService;

    public LogomarcaController(LogomarcaService logomarcaService) {
        this.logomarcaService = logomarcaService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return logomarcaService.get();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLogomarca(@RequestBody LogoMarcaDTO logoMarcaDTO) {
        return logomarcaService.create(logoMarcaDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLogomarca(@RequestBody LogoMarcaDTO logoMarcaDTO) {
        return logomarcaService.update(logoMarcaDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLogomarca(@PathVariable Long id) {
        return logomarcaService.delete(id);
    }
}
