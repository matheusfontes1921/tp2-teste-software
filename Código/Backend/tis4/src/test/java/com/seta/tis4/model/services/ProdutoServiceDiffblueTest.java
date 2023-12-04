package com.seta.tis4.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.seta.tis4.model.dtos.produto.ProdutoDTO;
import com.seta.tis4.model.entities.produto.Modelo;
import com.seta.tis4.model.entities.produto.Produto;
import com.seta.tis4.model.entities.produto.Tecido;
import com.seta.tis4.model.entities.produto.Tipo;
import com.seta.tis4.model.repositories.ModeloRepository;
import com.seta.tis4.model.repositories.ProdutoRepository;
import com.seta.tis4.model.repositories.TecidoRepository;
import com.seta.tis4.model.repositories.TipoRepository;

import java.util.ArrayList;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProdutoService.class})
@ExtendWith(SpringExtension.class)
class ProdutoServiceDiffblueTest {
    @MockBean
    private ModeloRepository modeloRepository;

    @MockBean
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @MockBean
    private TecidoRepository tecidoRepository;

    @MockBean
    private TipoRepository tipoRepository;

    /**
     * Method under test: {@link ProdutoService#get()}
     */
    @Test
    void testGet() {
        when(produtoRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<?> actualGetResult = produtoService.get();
        verify(produtoRepository).findAll();
        assertEquals(200, actualGetResult.getStatusCodeValue());
        assertTrue(actualGetResult.hasBody());
        assertTrue(actualGetResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#get()}
     */
    @Test
    void testGet2() {
        when(produtoRepository.findAll()).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> produtoService.get());
        verify(produtoRepository).findAll();
    }

    /**
     * Method under test: {@link ProdutoService#create(ProdutoDTO)}
     */
    @Test
    void testCreate() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());
        Optional<Modelo> ofResult = Optional.of(modelo);
        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        Modelo modelo2 = new Modelo();
        modelo2.setDetalhes("Detalhes");
        modelo2.setId(UUID.randomUUID());

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");

        Produto produto = new Produto();
        produto.setDescricaoBolso("Descricao Bolso");
        produto.setDescricaoLogo("Descricao Logo");
        produto.setId(UUID.randomUUID());
        produto.setModelo(modelo2);
        produto.setQuantidadeBolso(1);
        produto.setSilkando(true);
        produto.setTecido(tecido);
        produto.setTipo(tipo);
        produto.setUrlImagem("https://example.org/example");
        when(produtoRepository.save(Mockito.<Produto>any())).thenReturn(produto);

        Tipo tipo2 = new Tipo();
        tipo2.setId(UUID.randomUUID());
        tipo2.setTipo("Tipo");
        Optional<Tipo> ofResult2 = Optional.of(tipo2);
        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);

        Tecido tecido2 = new Tecido();
        tecido2.setClassificacao("Classificacao");
        tecido2.setCodigo("Codigo");
        tecido2.setFornecedor("Fornecedor");
        tecido2.setId(UUID.randomUUID());
        tecido2.setNome("Nome");
        Optional<Tecido> ofResult3 = Optional.of(tecido2);
        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult3);
        UUID id = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        UUID idModelo = UUID.randomUUID();
        ResponseEntity<?> actualCreateResult = produtoService.create(new ProdutoDTO(id, idTipo, idModelo, UUID.randomUUID(),
                "Descricao Bolso", 1, "Descricao Logo", true, "https://example.org/example"));
        verify(modeloRepository).findById(Mockito.<UUID>any());
        verify(tecidoRepository).findById(Mockito.<UUID>any());
        verify(tipoRepository).findById(Mockito.<UUID>any());
        verify(produtoRepository).save(Mockito.<Produto>any());
        assertEquals(200, actualCreateResult.getStatusCodeValue());
        assertTrue(actualCreateResult.hasBody());
        assertTrue(actualCreateResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#create(ProdutoDTO)}
     */
    @Test
    void testCreate2() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());
        Optional<Modelo> ofResult = Optional.of(modelo);
        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        when(produtoRepository.save(Mockito.<Produto>any())).thenThrow(new IllegalArgumentException("foo"));

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");
        Optional<Tipo> ofResult2 = Optional.of(tipo);
        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");
        Optional<Tecido> ofResult3 = Optional.of(tecido);
        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult3);
        UUID id = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        UUID idModelo = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> produtoService.create(new ProdutoDTO(id, idTipo, idModelo,
                UUID.randomUUID(), "Descricao Bolso", 1, "Descricao Logo", true, "https://example.org/example")));
        verify(modeloRepository).findById(Mockito.<UUID>any());
        verify(tecidoRepository).findById(Mockito.<UUID>any());
        verify(tipoRepository).findById(Mockito.<UUID>any());
        verify(produtoRepository).save(Mockito.<Produto>any());
    }

    /**
     * Method under test: {@link ProdutoService#create(ProdutoDTO)}
     */
    @Test
    void testCreate3() {
        Optional<Modelo> emptyResult = Optional.empty();
        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");
        Optional<Tipo> ofResult = Optional.of(tipo);
        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");
        Optional<Tecido> ofResult2 = Optional.of(tecido);
        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);
        UUID id = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        UUID idModelo = UUID.randomUUID();
        ResponseEntity<?> actualCreateResult = produtoService.create(new ProdutoDTO(id, idTipo, idModelo,
                UUID.randomUUID(), "Descricao Bolso", 1, "Descricao Logo", true, "https://example.org/example"));
        verify(modeloRepository).findById(Mockito.<UUID>any());
        assertEquals("Modelo nao existe", actualCreateResult.getBody());
        assertEquals(400, actualCreateResult.getStatusCodeValue());
        assertTrue(actualCreateResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#create(ProdutoDTO)}
     */
    @Test
    void testCreate4() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());
        Optional<Modelo> ofResult = Optional.of(modelo);
        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        Optional<Tipo> emptyResult = Optional.empty();
        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");
        Optional<Tecido> ofResult2 = Optional.of(tecido);
        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);
        UUID id = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        UUID idModelo = UUID.randomUUID();
        ResponseEntity<?> actualCreateResult = produtoService.create(new ProdutoDTO(id, idTipo, idModelo,
                UUID.randomUUID(), "Descricao Bolso", 1, "Descricao Logo", true, "https://example.org/example"));
        verify(modeloRepository).findById(Mockito.<UUID>any());
        verify(tecidoRepository).findById(Mockito.<UUID>any());
        verify(tipoRepository).findById(Mockito.<UUID>any());
        assertEquals("Tipo nao existe", actualCreateResult.getBody());
        assertEquals(400, actualCreateResult.getStatusCodeValue());
        assertTrue(actualCreateResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#create(ProdutoDTO)}
     */
    @Test
    void testCreate5() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());
        Optional<Modelo> ofResult = Optional.of(modelo);
        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");
        Optional<Tipo> ofResult2 = Optional.of(tipo);
        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);
        Optional<Tecido> emptyResult = Optional.empty();
        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        UUID id = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        UUID idModelo = UUID.randomUUID();
        ResponseEntity<?> actualCreateResult = produtoService.create(new ProdutoDTO(id, idTipo, idModelo,
                UUID.randomUUID(), "Descricao Bolso", 1, "Descricao Logo", true, "https://example.org/example"));
        verify(modeloRepository).findById(Mockito.<UUID>any());
        verify(tecidoRepository).findById(Mockito.<UUID>any());
        assertEquals("Tecido nao existe", actualCreateResult.getBody());
        assertEquals(400, actualCreateResult.getStatusCodeValue());
        assertTrue(actualCreateResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#update(ProdutoDTO)}
     */
    @Test
    void testUpdate() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());
        Optional<Modelo> ofResult = Optional.of(modelo);
        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        Modelo modelo2 = new Modelo();
        modelo2.setDetalhes("Detalhes");
        modelo2.setId(UUID.randomUUID());

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");

        Produto produto = new Produto();
        produto.setDescricaoBolso("Descricao Bolso");
        produto.setDescricaoLogo("Descricao Logo");
        produto.setId(UUID.randomUUID());
        produto.setModelo(modelo2);
        produto.setQuantidadeBolso(1);
        produto.setSilkando(true);
        produto.setTecido(tecido);
        produto.setTipo(tipo);
        produto.setUrlImagem("https://example.org/example");
        Optional<Produto> ofResult2 = Optional.of(produto);

        Modelo modelo3 = new Modelo();
        modelo3.setDetalhes("Detalhes");
        modelo3.setId(UUID.randomUUID());

        Tecido tecido2 = new Tecido();
        tecido2.setClassificacao("Classificacao");
        tecido2.setCodigo("Codigo");
        tecido2.setFornecedor("Fornecedor");
        tecido2.setId(UUID.randomUUID());
        tecido2.setNome("Nome");

        Tipo tipo2 = new Tipo();
        tipo2.setId(UUID.randomUUID());
        tipo2.setTipo("Tipo");

        Produto produto2 = new Produto();
        produto2.setDescricaoBolso("Descricao Bolso");
        produto2.setDescricaoLogo("Descricao Logo");
        produto2.setId(UUID.randomUUID());
        produto2.setModelo(modelo3);
        produto2.setQuantidadeBolso(1);
        produto2.setSilkando(true);
        produto2.setTecido(tecido2);
        produto2.setTipo(tipo2);
        produto2.setUrlImagem("https://example.org/example");
        when(produtoRepository.saveAndFlush(Mockito.<Produto>any())).thenReturn(produto2);
        when(produtoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);

        Tipo tipo3 = new Tipo();
        tipo3.setId(UUID.randomUUID());
        tipo3.setTipo("Tipo");
        Optional<Tipo> ofResult3 = Optional.of(tipo3);
        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult3);

        Tecido tecido3 = new Tecido();
        tecido3.setClassificacao("Classificacao");
        tecido3.setCodigo("Codigo");
        tecido3.setFornecedor("Fornecedor");
        tecido3.setId(UUID.randomUUID());
        tecido3.setNome("Nome");
        Optional<Tecido> ofResult4 = Optional.of(tecido3);
        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult4);
        UUID id = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        UUID idModelo = UUID.randomUUID();
        ResponseEntity<?> actualUpdateResult = produtoService.update(new ProdutoDTO(id, idTipo, idModelo,
                UUID.randomUUID(), "Descricao Bolso", 1, "Descricao Logo", true, "https://example.org/example"));
        verify(produtoRepository).saveAndFlush(Mockito.<Produto>any());
        verify(modeloRepository).findById(Mockito.<UUID>any());
        verify(produtoRepository).findById(Mockito.<UUID>any());
        verify(tecidoRepository).findById(Mockito.<UUID>any());
        verify(tipoRepository).findById(Mockito.<UUID>any());
        assertEquals(200, actualUpdateResult.getStatusCodeValue());
        assertTrue(actualUpdateResult.hasBody());
        assertTrue(actualUpdateResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#update(ProdutoDTO)}
     */
    @Test
    void testUpdate2() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());
        Optional<Modelo> ofResult = Optional.of(modelo);
        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        Modelo modelo2 = new Modelo();
        modelo2.setDetalhes("Detalhes");
        modelo2.setId(UUID.randomUUID());

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");

        Produto produto = new Produto();
        produto.setDescricaoBolso("Descricao Bolso");
        produto.setDescricaoLogo("Descricao Logo");
        produto.setId(UUID.randomUUID());
        produto.setModelo(modelo2);
        produto.setQuantidadeBolso(1);
        produto.setSilkando(true);
        produto.setTecido(tecido);
        produto.setTipo(tipo);
        produto.setUrlImagem("https://example.org/example");
        Optional<Produto> ofResult2 = Optional.of(produto);
        when(produtoRepository.saveAndFlush(Mockito.<Produto>any())).thenThrow(new IllegalArgumentException("foo"));
        when(produtoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);

        Tipo tipo2 = new Tipo();
        tipo2.setId(UUID.randomUUID());
        tipo2.setTipo("Tipo");
        Optional<Tipo> ofResult3 = Optional.of(tipo2);
        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult3);

        Tecido tecido2 = new Tecido();
        tecido2.setClassificacao("Classificacao");
        tecido2.setCodigo("Codigo");
        tecido2.setFornecedor("Fornecedor");
        tecido2.setId(UUID.randomUUID());
        tecido2.setNome("Nome");
        Optional<Tecido> ofResult4 = Optional.of(tecido2);
        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult4);
        UUID id = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        UUID idModelo = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> produtoService.update(new ProdutoDTO(id, idTipo, idModelo,
                UUID.randomUUID(), "Descricao Bolso", 1, "Descricao Logo", true, "https://example.org/example")));
        verify(produtoRepository).saveAndFlush(Mockito.<Produto>any());
        verify(modeloRepository).findById(Mockito.<UUID>any());
        verify(produtoRepository).findById(Mockito.<UUID>any());
        verify(tecidoRepository).findById(Mockito.<UUID>any());
        verify(tipoRepository).findById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link ProdutoService#update(ProdutoDTO)}
     */
    @Test
    void testUpdate3() {
        Optional<Modelo> emptyResult = Optional.empty();
        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);

        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");

        Produto produto = new Produto();
        produto.setDescricaoBolso("Descricao Bolso");
        produto.setDescricaoLogo("Descricao Logo");
        produto.setId(UUID.randomUUID());
        produto.setModelo(modelo);
        produto.setQuantidadeBolso(1);
        produto.setSilkando(true);
        produto.setTecido(tecido);
        produto.setTipo(tipo);
        produto.setUrlImagem("https://example.org/example");
        Optional<Produto> ofResult = Optional.of(produto);
        when(produtoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        Tipo tipo2 = new Tipo();
        tipo2.setId(UUID.randomUUID());
        tipo2.setTipo("Tipo");
        Optional<Tipo> ofResult2 = Optional.of(tipo2);
        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);

        Tecido tecido2 = new Tecido();
        tecido2.setClassificacao("Classificacao");
        tecido2.setCodigo("Codigo");
        tecido2.setFornecedor("Fornecedor");
        tecido2.setId(UUID.randomUUID());
        tecido2.setNome("Nome");
        Optional<Tecido> ofResult3 = Optional.of(tecido2);
        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult3);
        UUID id = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        UUID idModelo = UUID.randomUUID();
        ResponseEntity<?> actualUpdateResult = produtoService.update(new ProdutoDTO(id, idTipo, idModelo,
                UUID.randomUUID(), "Descricao Bolso", 1, "Descricao Logo", true, "https://example.org/example"));
        verify(modeloRepository).findById(Mockito.<UUID>any());
        verify(produtoRepository).findById(Mockito.<UUID>any());
        assertEquals("Modelo nao existe", actualUpdateResult.getBody());
        assertEquals(400, actualUpdateResult.getStatusCodeValue());
        assertTrue(actualUpdateResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#update(ProdutoDTO)}
     */
    @Test
    void testUpdate4() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());
        Optional<Modelo> ofResult = Optional.of(modelo);
        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        Optional<Produto> emptyResult = Optional.empty();
        when(produtoRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");
        Optional<Tipo> ofResult2 = Optional.of(tipo);
        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");
        Optional<Tecido> ofResult3 = Optional.of(tecido);
        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult3);
        UUID id = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        UUID idModelo = UUID.randomUUID();
        ResponseEntity<?> actualUpdateResult = produtoService.update(new ProdutoDTO(id, idTipo, idModelo,
                UUID.randomUUID(), "Descricao Bolso", 1, "Descricao Logo", true, "https://example.org/example"));
        verify(produtoRepository).findById(Mockito.<UUID>any());
        assertEquals("Nao existe", actualUpdateResult.getBody());
        assertEquals(400, actualUpdateResult.getStatusCodeValue());
        assertTrue(actualUpdateResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#update(ProdutoDTO)}
     */
    @Test
    void testUpdate5() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());
        Optional<Modelo> ofResult = Optional.of(modelo);
        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        Modelo modelo2 = new Modelo();
        modelo2.setDetalhes("Detalhes");
        modelo2.setId(UUID.randomUUID());

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");

        Produto produto = new Produto();
        produto.setDescricaoBolso("Descricao Bolso");
        produto.setDescricaoLogo("Descricao Logo");
        produto.setId(UUID.randomUUID());
        produto.setModelo(modelo2);
        produto.setQuantidadeBolso(1);
        produto.setSilkando(true);
        produto.setTecido(tecido);
        produto.setTipo(tipo);
        produto.setUrlImagem("https://example.org/example");
        Optional<Produto> ofResult2 = Optional.of(produto);
        when(produtoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);
        Optional<Tipo> emptyResult = Optional.empty();
        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);

        Tecido tecido2 = new Tecido();
        tecido2.setClassificacao("Classificacao");
        tecido2.setCodigo("Codigo");
        tecido2.setFornecedor("Fornecedor");
        tecido2.setId(UUID.randomUUID());
        tecido2.setNome("Nome");
        Optional<Tecido> ofResult3 = Optional.of(tecido2);
        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult3);
        UUID id = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        UUID idModelo = UUID.randomUUID();
        ResponseEntity<?> actualUpdateResult = produtoService.update(new ProdutoDTO(id, idTipo, idModelo,
                UUID.randomUUID(), "Descricao Bolso", 1, "Descricao Logo", true, "https://example.org/example"));
        verify(modeloRepository).findById(Mockito.<UUID>any());
        verify(produtoRepository).findById(Mockito.<UUID>any());
        verify(tecidoRepository).findById(Mockito.<UUID>any());
        verify(tipoRepository).findById(Mockito.<UUID>any());
        assertEquals("Tipo nao existe", actualUpdateResult.getBody());
        assertEquals(400, actualUpdateResult.getStatusCodeValue());
        assertTrue(actualUpdateResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#update(ProdutoDTO)}
     */
    @Test
    void testUpdate6() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());
        Optional<Modelo> ofResult = Optional.of(modelo);
        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        Modelo modelo2 = new Modelo();
        modelo2.setDetalhes("Detalhes");
        modelo2.setId(UUID.randomUUID());

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");

        Produto produto = new Produto();
        produto.setDescricaoBolso("Descricao Bolso");
        produto.setDescricaoLogo("Descricao Logo");
        produto.setId(UUID.randomUUID());
        produto.setModelo(modelo2);
        produto.setQuantidadeBolso(1);
        produto.setSilkando(true);
        produto.setTecido(tecido);
        produto.setTipo(tipo);
        produto.setUrlImagem("https://example.org/example");
        Optional<Produto> ofResult2 = Optional.of(produto);
        when(produtoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);

        Tipo tipo2 = new Tipo();
        tipo2.setId(UUID.randomUUID());
        tipo2.setTipo("Tipo");
        Optional<Tipo> ofResult3 = Optional.of(tipo2);
        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult3);
        Optional<Tecido> emptyResult = Optional.empty();
        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        UUID id = UUID.randomUUID();
        UUID idTipo = UUID.randomUUID();
        UUID idModelo = UUID.randomUUID();
        ResponseEntity<?> actualUpdateResult = produtoService.update(new ProdutoDTO(id, idTipo, idModelo,
                UUID.randomUUID(), "Descricao Bolso", 1, "Descricao Logo", true, "https://example.org/example"));
        verify(modeloRepository).findById(Mockito.<UUID>any());
        verify(produtoRepository).findById(Mockito.<UUID>any());
        verify(tecidoRepository).findById(Mockito.<UUID>any());
        assertEquals("Tecido nao existe", actualUpdateResult.getBody());
        assertEquals(400, actualUpdateResult.getStatusCodeValue());
        assertTrue(actualUpdateResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#delete(UUID)}
     */
    @Test
    void testDelete() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");

        Produto produto = new Produto();
        produto.setDescricaoBolso("Descricao Bolso");
        produto.setDescricaoLogo("Descricao Logo");
        produto.setId(UUID.randomUUID());
        produto.setModelo(modelo);
        produto.setQuantidadeBolso(1);
        produto.setSilkando(true);
        produto.setTecido(tecido);
        produto.setTipo(tipo);
        produto.setUrlImagem("https://example.org/example");
        Optional<Produto> ofResult = Optional.of(produto);
        doNothing().when(produtoRepository).deleteById(Mockito.<UUID>any());
        when(produtoRepository.existsById(Mockito.<UUID>any())).thenReturn(true);
        when(produtoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        ResponseEntity<?> actualDeleteResult = produtoService.delete(UUID.randomUUID());
        verify(produtoRepository).deleteById(Mockito.<UUID>any());
        verify(produtoRepository, atLeast(1)).existsById(Mockito.<UUID>any());
        verify(produtoRepository).findById(Mockito.<UUID>any());
        assertEquals("Nao existe", actualDeleteResult.getBody());
        assertEquals(400, actualDeleteResult.getStatusCodeValue());
        assertTrue(actualDeleteResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#delete(UUID)}
     */
    @Test
    void testDelete2() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");

        Produto produto = new Produto();
        produto.setDescricaoBolso("Descricao Bolso");
        produto.setDescricaoLogo("Descricao Logo");
        produto.setId(UUID.randomUUID());
        produto.setModelo(modelo);
        produto.setQuantidadeBolso(1);
        produto.setSilkando(true);
        produto.setTecido(tecido);
        produto.setTipo(tipo);
        produto.setUrlImagem("https://example.org/example");
        Optional<Produto> ofResult = Optional.of(produto);
        doThrow(new IllegalArgumentException("Nao existe")).when(produtoRepository).deleteById(Mockito.<UUID>any());
        when(produtoRepository.existsById(Mockito.<UUID>any())).thenReturn(true);
        when(produtoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        ResponseEntity<?> actualDeleteResult = produtoService.delete(UUID.randomUUID());
        verify(produtoRepository).deleteById(Mockito.<UUID>any());
        verify(produtoRepository).existsById(Mockito.<UUID>any());
        verify(produtoRepository).findById(Mockito.<UUID>any());
        assertEquals(400, actualDeleteResult.getStatusCodeValue());
        assertTrue(actualDeleteResult.hasBody());
        assertTrue(actualDeleteResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#delete(UUID)}
     */
    @Test
    void testDelete3() {
        Modelo modelo = new Modelo();
        modelo.setDetalhes("Detalhes");
        modelo.setId(UUID.randomUUID());

        Tecido tecido = new Tecido();
        tecido.setClassificacao("Classificacao");
        tecido.setCodigo("Codigo");
        tecido.setFornecedor("Fornecedor");
        tecido.setId(UUID.randomUUID());
        tecido.setNome("Nome");

        Tipo tipo = new Tipo();
        tipo.setId(UUID.randomUUID());
        tipo.setTipo("Tipo");

        Produto produto = new Produto();
        produto.setDescricaoBolso("Descricao Bolso");
        produto.setDescricaoLogo("Descricao Logo");
        produto.setId(UUID.randomUUID());
        produto.setModelo(modelo);
        produto.setQuantidadeBolso(1);
        produto.setSilkando(true);
        produto.setTecido(tecido);
        produto.setTipo(tipo);
        produto.setUrlImagem("https://example.org/example");
        Optional<Produto> ofResult = Optional.of(produto);
        when(produtoRepository.existsById(Mockito.<UUID>any())).thenReturn(false);
        when(produtoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        ResponseEntity<?> actualDeleteResult = produtoService.delete(UUID.randomUUID());
        verify(produtoRepository).existsById(Mockito.<UUID>any());
        verify(produtoRepository).findById(Mockito.<UUID>any());
        assertEquals("Nao existe", actualDeleteResult.getBody());
        assertEquals(400, actualDeleteResult.getStatusCodeValue());
        assertTrue(actualDeleteResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link ProdutoService#delete(UUID)}
     */
    @Test
    void testDelete4() {
        Optional<Produto> emptyResult = Optional.empty();
        when(produtoRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        ResponseEntity<?> actualDeleteResult = produtoService.delete(UUID.randomUUID());
        verify(produtoRepository).findById(Mockito.<UUID>any());
        assertEquals("Nao existe", actualDeleteResult.getBody());
        assertEquals(400, actualDeleteResult.getStatusCodeValue());
        assertTrue(actualDeleteResult.getHeaders().isEmpty());
    }
}

