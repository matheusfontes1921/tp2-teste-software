package com.seta.tis4.model.services;

import com.seta.tis4.model.dtos.produto.GetProdutoDTO;
import com.seta.tis4.model.dtos.produto.ProdutoDTO;
import com.seta.tis4.model.entities.produto.*;
import com.seta.tis4.model.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {
    private final ModeloRepository modeloRepository;
    private final ProdutoRepository produtoRepository;
    private final TipoRepository tipoRepository;
    private final TecidoRepository tecidoRepository;

    public ProdutoService(
            ModeloRepository modeloRepository,
            ProdutoRepository produtoRepository,
            TipoRepository tipoRepository,
            TecidoRepository tecidoRepository
    ) {
        this.modeloRepository = modeloRepository;
        this.produtoRepository = produtoRepository;
        this.tipoRepository = tipoRepository;
        this.tecidoRepository = tecidoRepository;
    }

    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(produtoRepository.findAll());
    }

    public ResponseEntity<?> create(ProdutoDTO dto) {
        Optional<Modelo> modeloOpt = modeloRepository.findById(dto.idModelo());
        if(modeloOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Modelo nao existe");
        }

        Optional<Tecido> tecidoOpt = tecidoRepository.findById(dto.idTecido());
        if(tecidoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Tecido nao existe");
        }

        Optional<Tipo> tipoOpt = tipoRepository.findById(dto.idTipo());
        if(tipoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Tipo nao existe");
        }

        Modelo modelo = modeloOpt.get();
        Tecido tecido = tecidoOpt.get();
        Tipo tipo = tipoOpt.get();


        Produto produto = dto.toProduto(tecido, modelo, tipo);
        produtoRepository.save(produto);

        String logoSilkada = "";

        if(produto.getSilkando())
            logoSilkada = " silkada";

        String product = produto.getTipo().getTipo() + " de " + produto.getTecido().getNome() + " com " + produto.getQuantidadeBolso() + " bolso e " + produto.getDescricaoLogo() + logoSilkada ;

        return ResponseEntity.ok().body(GetProdutoDTO.builder()
                        .id(produto.getId())
                        .product(product)
                .build());
    }

    public ResponseEntity<?> update(ProdutoDTO dto) {
        Optional<Produto> produtoOptional = Optional.of(new Produto());

        try {
            produtoOptional = produtoRepository.findById(dto.id());
        } catch (IllegalArgumentException e) {
            ResponseEntity.badRequest().body(e);
        }

        if(produtoOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Nao existe");
        }

        Optional<Modelo> modeloOpt = modeloRepository.findById(dto.idModelo());
        if(modeloOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Modelo nao existe");
        }

        Optional<Tecido> tecidoOpt = tecidoRepository.findById(dto.idTecido());
        if(tecidoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Tecido nao existe");
        }

        Optional<Tipo> tipoOpt = tipoRepository.findById(dto.idTipo());
        if(tipoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Tipo nao existe");
        }

        Modelo modelo = modeloOpt.get();
        Tecido tecido = tecidoOpt.get();
        Tipo tipo = tipoOpt.get();


        Produto produto = produtoOptional.get();

        produto.setTecido(tecido);
        produto.setModelo(modelo);
        produto.setTipo(tipo);
        produto.setSilkando(dto.silkando());
        produto.setQuantidadeBolso(dto.quantidadeBolso());
        produto.setDescricaoBolso(dto.descricaoBolso());
        produto.setUrlImagem(dto.urlImagem());
        produto.setDescricaoLogo(dto.descricaoLogo());

        return ResponseEntity.ok().body(produtoRepository.saveAndFlush(produto));
    }

    public ResponseEntity<?> delete(UUID id) {
        try {
            Optional<Produto> produtoOptional = produtoRepository.findById(id);
            if(produtoOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Nao existe");
            }

            if(!produtoRepository.existsById(id)) {
                return ResponseEntity.badRequest().body("Nao existe");
            }
            produtoRepository.deleteById(id);

            if(!produtoRepository.existsById(id)) {
                return ResponseEntity.ok().body("Deletado");
            }

            return ResponseEntity.badRequest().body("Nao existe");
        } catch(IllegalArgumentException error) {
            return ResponseEntity.badRequest().body(error);

        }
    }
}
