package com.seta.tis4.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.seta.tis4.model.dtos.produto.TecidoDTO;
import com.seta.tis4.model.entities.produto.Tecido;
import com.seta.tis4.model.repositories.TecidoRepository;

import java.util.ArrayList;
import java.util.Map;

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

@ContextConfiguration(classes = {TecidoService.class})
@ExtendWith(SpringExtension.class)
class TecidoServiceDiffblueTest {
//    @MockBean
//    private TecidoRepository tecidoRepository;
//
//    @Autowired
//    private TecidoService tecidoService;
//
//    /**
//     * Method under test: {@link TecidoService#getName()}
//     */
//    @Test
//    void testGetName() {
//        when(tecidoRepository.findAll()).thenReturn(new ArrayList<>());
//        ResponseEntity<?> actualName = tecidoService.getName();
//        verify(tecidoRepository).findAll();
//        assertEquals("Não existe tecidos cadastrados", actualName.getBody());
//        assertEquals(400, actualName.getStatusCodeValue());
//        assertTrue(actualName.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TecidoService#getName()}
//     */
//    @Test
//    void testGetName2() {
//        Tecido tecido = new Tecido();
//        tecido.setClassificacao("Não existe tecidos cadastrados");
//        tecido.setCodigo("Não existe tecidos cadastrados");
//        tecido.setFornecedor("Não existe tecidos cadastrados");
//        tecido.setId(UUID.randomUUID());
//        tecido.setNome("Não existe tecidos cadastrados");
//
//        ArrayList<Tecido> tecidoList = new ArrayList<>();
//        tecidoList.add(tecido);
//        when(tecidoRepository.findAll()).thenReturn(tecidoList);
//        ResponseEntity<?> actualName = tecidoService.getName();
//        verify(tecidoRepository).findAll();
//        assertEquals(1, ((Map<UUID, String>) actualName.getBody()).size());
//        assertEquals(200, actualName.getStatusCodeValue());
//        assertTrue(actualName.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TecidoService#getName()}
//     */
//    @Test
//    void testGetName3() {
//        Tecido tecido = new Tecido();
//        tecido.setClassificacao("Não existe tecidos cadastrados");
//        tecido.setCodigo("Não existe tecidos cadastrados");
//        tecido.setFornecedor("Não existe tecidos cadastrados");
//        tecido.setId(UUID.randomUUID());
//        tecido.setNome("Não existe tecidos cadastrados");
//
//        Tecido tecido2 = new Tecido();
//        tecido2.setClassificacao("42");
//        tecido2.setCodigo("42");
//        tecido2.setFornecedor("42");
//        tecido2.setId(UUID.randomUUID());
//        tecido2.setNome("42");
//
//        ArrayList<Tecido> tecidoList = new ArrayList<>();
//        tecidoList.add(tecido2);
//        tecidoList.add(tecido);
//        when(tecidoRepository.findAll()).thenReturn(tecidoList);
//        ResponseEntity<?> actualName = tecidoService.getName();
//        verify(tecidoRepository).findAll();
//        assertEquals(2, ((Map<UUID, String>) actualName.getBody()).size());
//        assertEquals(200, actualName.getStatusCodeValue());
//        assertTrue(actualName.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TecidoService#get()}
//     */
//    @Test
//    void testGet() {
//        when(tecidoRepository.findAll()).thenReturn(new ArrayList<>());
//        ResponseEntity<?> actualGetResult = tecidoService.get();
//        verify(tecidoRepository).findAll();
//        assertEquals(200, actualGetResult.getStatusCodeValue());
//        assertTrue(actualGetResult.hasBody());
//        assertTrue(actualGetResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TecidoService#create(TecidoDTO)}
//     */
//    @Test
//    void testCreate() {
//        Tecido tecido = new Tecido();
//        tecido.setClassificacao("Classificacao");
//        tecido.setCodigo("Codigo");
//        tecido.setFornecedor("Fornecedor");
//        tecido.setId(UUID.randomUUID());
//        tecido.setNome("Nome");
//        when(tecidoRepository.save(Mockito.<Tecido>any())).thenReturn(tecido);
//        ResponseEntity<?> actualCreateResult = tecidoService
//                .create(new TecidoDTO(UUID.randomUUID(), "Nome", "Codigo", "Fornecedor", "Classificacao"));
//        verify(tecidoRepository).save(Mockito.<Tecido>any());
//        assertEquals(200, actualCreateResult.getStatusCodeValue());
//        assertTrue(actualCreateResult.hasBody());
//        assertTrue(actualCreateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TecidoService#update(TecidoDTO)}
//     */
//    @Test
//    void testUpdate() {
//        Tecido tecido = new Tecido();
//        tecido.setClassificacao("Classificacao");
//        tecido.setCodigo("Codigo");
//        tecido.setFornecedor("Fornecedor");
//        tecido.setId(UUID.randomUUID());
//        tecido.setNome("Nome");
//        Optional<Tecido> ofResult = Optional.of(tecido);
//
//        Tecido tecido2 = new Tecido();
//        tecido2.setClassificacao("Classificacao");
//        tecido2.setCodigo("Codigo");
//        tecido2.setFornecedor("Fornecedor");
//        tecido2.setId(UUID.randomUUID());
//        tecido2.setNome("Nome");
//        when(tecidoRepository.saveAndFlush(Mockito.<Tecido>any())).thenReturn(tecido2);
//        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//        ResponseEntity<?> actualUpdateResult = tecidoService
//                .update(new TecidoDTO(UUID.randomUUID(), "Nome", "Codigo", "Fornecedor", "Classificacao"));
//        verify(tecidoRepository).saveAndFlush(Mockito.<Tecido>any());
//        verify(tecidoRepository).findById(Mockito.<UUID>any());
//        assertEquals(200, actualUpdateResult.getStatusCodeValue());
//        assertTrue(actualUpdateResult.hasBody());
//        assertTrue(actualUpdateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TecidoService#update(TecidoDTO)}
//     */
//    @Test
//    void testUpdate2() {
//        Optional<Tecido> emptyResult = Optional.empty();
//        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
//        ResponseEntity<?> actualUpdateResult = tecidoService
//                .update(new TecidoDTO(UUID.randomUUID(), "Nome", "Codigo", "Fornecedor", "Classificacao"));
//        verify(tecidoRepository).findById(Mockito.<UUID>any());
//        assertEquals("ta errado", actualUpdateResult.getBody());
//        assertEquals(200, actualUpdateResult.getStatusCodeValue());
//        assertTrue(actualUpdateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TecidoService#delete(UUID)}
//     */
//    @Test
//    void testDelete() {
//        Tecido tecido = new Tecido();
//        tecido.setClassificacao("Classificacao");
//        tecido.setCodigo("Codigo");
//        tecido.setFornecedor("Fornecedor");
//        tecido.setId(UUID.randomUUID());
//        tecido.setNome("Nome");
//        Optional<Tecido> ofResult = Optional.of(tecido);
//        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//        doNothing().when(tecidoRepository).deleteById(Mockito.<UUID>any());
//        ResponseEntity<?> actualDeleteResult = tecidoService.delete(UUID.randomUUID());
//        verify(tecidoRepository).deleteById(Mockito.<UUID>any());
//        verify(tecidoRepository).findById(Mockito.<UUID>any());
//        assertEquals("id nao existe", actualDeleteResult.getBody());
//        assertEquals(400, actualDeleteResult.getStatusCodeValue());
//        assertTrue(actualDeleteResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TecidoService#delete(UUID)}
//     */
//    @Test
//    void testDelete2() {
//        Optional<Tecido> emptyResult = Optional.empty();
//        when(tecidoRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
//        doNothing().when(tecidoRepository).deleteById(Mockito.<UUID>any());
//        ResponseEntity<?> actualDeleteResult = tecidoService.delete(UUID.randomUUID());
//        verify(tecidoRepository).deleteById(Mockito.<UUID>any());
//        verify(tecidoRepository).findById(Mockito.<UUID>any());
//        assertEquals("apagado", actualDeleteResult.getBody());
//        assertEquals(200, actualDeleteResult.getStatusCodeValue());
//        assertTrue(actualDeleteResult.getHeaders().isEmpty());
//    }
}

