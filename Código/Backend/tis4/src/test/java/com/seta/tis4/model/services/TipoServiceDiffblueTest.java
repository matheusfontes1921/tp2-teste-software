package com.seta.tis4.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.seta.tis4.model.dtos.produto.TipoDTO;
import com.seta.tis4.model.entities.produto.Tipo;
import com.seta.tis4.model.repositories.TipoRepository;

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

@ContextConfiguration(classes = {TipoService.class})
@ExtendWith(SpringExtension.class)
class TipoServiceDiffblueTest {
//    @MockBean
//    private TipoRepository tipoRepository;
//
//    @Autowired
//    private TipoService tipoService;
//
//    /**
//     * Method under test: {@link TipoService#getName()}
//     */
//    @Test
//    void testGetName() {
//        when(tipoRepository.findAll()).thenReturn(new ArrayList<>());
//        ResponseEntity<?> actualName = tipoService.getName();
//        verify(tipoRepository).findAll();
//        assertEquals("Não existe tipos cadastrados", actualName.getBody());
//        assertEquals(400, actualName.getStatusCodeValue());
//        assertTrue(actualName.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TipoService#getName()}
//     */
//    @Test
//    void testGetName2() {
//        Tipo tipo = new Tipo();
//        tipo.setId(UUID.randomUUID());
//        tipo.setTipo("Não existe tipos cadastrados");
//
//        ArrayList<Tipo> tipoList = new ArrayList<>();
//        tipoList.add(tipo);
//        when(tipoRepository.findAll()).thenReturn(tipoList);
//        ResponseEntity<?> actualName = tipoService.getName();
//        verify(tipoRepository).findAll();
//        assertEquals(1, ((Map<UUID, String>) actualName.getBody()).size());
//        assertEquals(200, actualName.getStatusCodeValue());
//        assertTrue(actualName.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TipoService#getName()}
//     */
//    @Test
//    void testGetName3() {
//        Tipo tipo = new Tipo();
//        tipo.setId(UUID.randomUUID());
//        tipo.setTipo("Não existe tipos cadastrados");
//
//        Tipo tipo2 = new Tipo();
//        tipo2.setId(UUID.randomUUID());
//        tipo2.setTipo("42");
//
//        ArrayList<Tipo> tipoList = new ArrayList<>();
//        tipoList.add(tipo2);
//        tipoList.add(tipo);
//        when(tipoRepository.findAll()).thenReturn(tipoList);
//        ResponseEntity<?> actualName = tipoService.getName();
//        verify(tipoRepository).findAll();
//        assertEquals(2, ((Map<UUID, String>) actualName.getBody()).size());
//        assertEquals(200, actualName.getStatusCodeValue());
//        assertTrue(actualName.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TipoService#get()}
//     */
//    @Test
//    void testGet() {
//        when(tipoRepository.findAll()).thenReturn(new ArrayList<>());
//        ResponseEntity<?> actualGetResult = tipoService.get();
//        verify(tipoRepository).findAll();
//        assertEquals(200, actualGetResult.getStatusCodeValue());
//        assertTrue(actualGetResult.hasBody());
//        assertTrue(actualGetResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TipoService#create(TipoDTO)}
//     */
//    @Test
//    void testCreate() {
//        Tipo tipo = new Tipo();
//        tipo.setId(UUID.randomUUID());
//        tipo.setTipo("Tipo");
//        when(tipoRepository.save(Mockito.<Tipo>any())).thenReturn(tipo);
//        ResponseEntity<?> actualCreateResult = tipoService.create(new TipoDTO(UUID.randomUUID(), "Tipo"));
//        verify(tipoRepository).save(Mockito.<Tipo>any());
//        assertEquals(200, actualCreateResult.getStatusCodeValue());
//        assertTrue(actualCreateResult.hasBody());
//        assertTrue(actualCreateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TipoService#update(TipoDTO)}
//     */
//    @Test
//    void testUpdate() {
//        Tipo tipo = new Tipo();
//        tipo.setId(UUID.randomUUID());
//        tipo.setTipo("Tipo");
//        Optional<Tipo> ofResult = Optional.of(tipo);
//
//        Tipo tipo2 = new Tipo();
//        tipo2.setId(UUID.randomUUID());
//        tipo2.setTipo("Tipo");
//        when(tipoRepository.saveAndFlush(Mockito.<Tipo>any())).thenReturn(tipo2);
//        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//        ResponseEntity<?> actualUpdateResult = tipoService.update(new TipoDTO(UUID.randomUUID(), "Tipo"));
//        verify(tipoRepository).saveAndFlush(Mockito.<Tipo>any());
//        verify(tipoRepository).findById(Mockito.<UUID>any());
//        assertEquals(200, actualUpdateResult.getStatusCodeValue());
//        assertTrue(actualUpdateResult.hasBody());
//        assertTrue(actualUpdateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TipoService#update(TipoDTO)}
//     */
//    @Test
//    void testUpdate2() {
//        Optional<Tipo> emptyResult = Optional.empty();
//        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
//        ResponseEntity<?> actualUpdateResult = tipoService.update(new TipoDTO(UUID.randomUUID(), "Tipo"));
//        verify(tipoRepository).findById(Mockito.<UUID>any());
//        assertEquals("ta errado", actualUpdateResult.getBody());
//        assertEquals(200, actualUpdateResult.getStatusCodeValue());
//        assertTrue(actualUpdateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TipoService#delete(UUID)}
//     */
//    @Test
//    void testDelete() {
//        Tipo tipo = new Tipo();
//        tipo.setId(UUID.randomUUID());
//        tipo.setTipo("Tipo");
//        Optional<Tipo> ofResult = Optional.of(tipo);
//        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//        doNothing().when(tipoRepository).deleteById(Mockito.<UUID>any());
//        ResponseEntity<?> actualDeleteResult = tipoService.delete(UUID.randomUUID());
//        verify(tipoRepository).deleteById(Mockito.<UUID>any());
//        verify(tipoRepository).findById(Mockito.<UUID>any());
//        assertEquals("id nao existe", actualDeleteResult.getBody());
//        assertEquals(400, actualDeleteResult.getStatusCodeValue());
//        assertTrue(actualDeleteResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link TipoService#delete(UUID)}
//     */
//    @Test
//    void testDelete2() {
//        Optional<Tipo> emptyResult = Optional.empty();
//        when(tipoRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
//        doNothing().when(tipoRepository).deleteById(Mockito.<UUID>any());
//        ResponseEntity<?> actualDeleteResult = tipoService.delete(UUID.randomUUID());
//        verify(tipoRepository).deleteById(Mockito.<UUID>any());
//        verify(tipoRepository).findById(Mockito.<UUID>any());
//        assertEquals("apagado", actualDeleteResult.getBody());
//        assertEquals(200, actualDeleteResult.getStatusCodeValue());
//        assertTrue(actualDeleteResult.getHeaders().isEmpty());
//    }
}

