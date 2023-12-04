package com.seta.tis4.controllers.produto;

import com.seta.tis4.model.dtos.produto.ProdutoDTO;
import com.seta.tis4.model.services.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/produto/")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("get")
    public ResponseEntity<?> get() {
        return produtoService.get();
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody ProdutoDTO dto) {
        return produtoService.create(dto);
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody ProdutoDTO dto){
        return produtoService.update(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return produtoService.delete(id);
    }

}
