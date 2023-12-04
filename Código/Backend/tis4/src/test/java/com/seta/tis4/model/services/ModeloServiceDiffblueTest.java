package com.seta.tis4.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.seta.tis4.model.dtos.produto.ModeloDTO;
import com.seta.tis4.model.entities.produto.Modelo;
import com.seta.tis4.model.repositories.ModeloRepository;

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

@ContextConfiguration(classes = {ModeloService.class})
@ExtendWith(SpringExtension.class)
class ModeloServiceDiffblueTest {
//    @MockBean
//    private ModeloRepository modeloRepository;
//
//    @Autowired
//    private ModeloService modeloService;
//
//    /**
//     * Method under test: {@link ModeloService#getName()}
//     */
//    @Test
//    void testGetName() {
//        when(modeloRepository.findAll()).thenReturn(new ArrayList<>());
//        ResponseEntity<?> actualName = modeloService.getName();
//        verify(modeloRepository).findAll();
//        assertEquals("Não existe modelo cadastrados", actualName.getBody());
//        assertEquals(400, actualName.getStatusCodeValue());
//        assertTrue(actualName.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link ModeloService#getName()}
//     */
//    @Test
//    void testGetName2() {
//        Modelo modelo = new Modelo();
//        modelo.setDetalhes("Não existe modelo cadastrados");
//        modelo.setId(UUID.randomUUID());
//
//        ArrayList<Modelo> modeloList = new ArrayList<>();
//        modeloList.add(modelo);
//        when(modeloRepository.findAll()).thenReturn(modeloList);
//        ResponseEntity<?> actualName = modeloService.getName();
//        verify(modeloRepository).findAll();
//        assertEquals(1, ((Map<UUID, String>) actualName.getBody()).size());
//        assertEquals(200, actualName.getStatusCodeValue());
//        assertTrue(actualName.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link ModeloService#getName()}
//     */
//    @Test
//    void testGetName3() {
//        Modelo modelo = new Modelo();
//        modelo.setDetalhes("Não existe modelo cadastrados");
//        modelo.setId(UUID.randomUUID());
//
//        Modelo modelo2 = new Modelo();
//        modelo2.setDetalhes("42");
//        modelo2.setId(UUID.randomUUID());
//
//        ArrayList<Modelo> modeloList = new ArrayList<>();
//        modeloList.add(modelo2);
//        modeloList.add(modelo);
//        when(modeloRepository.findAll()).thenReturn(modeloList);
//        ResponseEntity<?> actualName = modeloService.getName();
//        verify(modeloRepository).findAll();
//        assertEquals(2, ((Map<UUID, String>) actualName.getBody()).size());
//        assertEquals(200, actualName.getStatusCodeValue());
//        assertTrue(actualName.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link ModeloService#getAll()}
//     */
//    @Test
//    void testGetAll() {
//        when(modeloRepository.findAll()).thenReturn(new ArrayList<>());
//        ResponseEntity<?> actualAll = modeloService.getAll();
//        verify(modeloRepository).findAll();
//        assertEquals(200, actualAll.getStatusCodeValue());
//        assertTrue(actualAll.hasBody());
//        assertTrue(actualAll.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link ModeloService#create(ModeloDTO)}
//     */
//    @Test
//    void testCreate() {
//        Modelo modelo = new Modelo();
//        modelo.setDetalhes("Detalhes");
//        modelo.setId(UUID.randomUUID());
//        when(modeloRepository.save(Mockito.<Modelo>any())).thenReturn(modelo);
//        ResponseEntity<?> actualCreateResult = modeloService.create(new ModeloDTO(UUID.randomUUID(), "Detalhes"));
//        verify(modeloRepository).save(Mockito.<Modelo>any());
//        assertEquals(200, actualCreateResult.getStatusCodeValue());
//        assertTrue(actualCreateResult.hasBody());
//        assertTrue(actualCreateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link ModeloService#update(ModeloDTO)}
//     */
//    @Test
//    void testUpdate() {
//        Modelo modelo = new Modelo();
//        modelo.setDetalhes("Detalhes");
//        modelo.setId(UUID.randomUUID());
//        Optional<Modelo> ofResult = Optional.of(modelo);
//
//        Modelo modelo2 = new Modelo();
//        modelo2.setDetalhes("Detalhes");
//        modelo2.setId(UUID.randomUUID());
//        when(modeloRepository.saveAndFlush(Mockito.<Modelo>any())).thenReturn(modelo2);
//        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//        ResponseEntity<?> actualUpdateResult = modeloService.update(new ModeloDTO(UUID.randomUUID(), "Detalhes"));
//        verify(modeloRepository).saveAndFlush(Mockito.<Modelo>any());
//        verify(modeloRepository).findById(Mockito.<UUID>any());
//        assertEquals(200, actualUpdateResult.getStatusCodeValue());
//        assertTrue(actualUpdateResult.hasBody());
//        assertTrue(actualUpdateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link ModeloService#update(ModeloDTO)}
//     */
//    @Test
//    void testUpdate2() {
//        Optional<Modelo> emptyResult = Optional.empty();
//        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
//        ResponseEntity<?> actualUpdateResult = modeloService.update(new ModeloDTO(UUID.randomUUID(), "Detalhes"));
//        verify(modeloRepository).findById(Mockito.<UUID>any());
//        assertEquals("ta errado", actualUpdateResult.getBody());
//        assertEquals(200, actualUpdateResult.getStatusCodeValue());
//        assertTrue(actualUpdateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link ModeloService#delete(UUID)}
//     */
//    @Test
//    void testDelete() {
//        Modelo modelo = new Modelo();
//        modelo.setDetalhes("Detalhes");
//        modelo.setId(UUID.randomUUID());
//        Optional<Modelo> ofResult = Optional.of(modelo);
//        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
//        doNothing().when(modeloRepository).deleteById(Mockito.<UUID>any());
//        ResponseEntity<?> actualDeleteResult = modeloService.delete(UUID.randomUUID());
//        verify(modeloRepository).deleteById(Mockito.<UUID>any());
//        verify(modeloRepository).findById(Mockito.<UUID>any());
//        assertEquals("id nao existe", actualDeleteResult.getBody());
//        assertEquals(400, actualDeleteResult.getStatusCodeValue());
//        assertTrue(actualDeleteResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link ModeloService#delete(UUID)}
//     */
//    @Test
//    void testDelete2() {
//        Optional<Modelo> emptyResult = Optional.empty();
//        when(modeloRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
//        doNothing().when(modeloRepository).deleteById(Mockito.<UUID>any());
//        ResponseEntity<?> actualDeleteResult = modeloService.delete(UUID.randomUUID());
//        verify(modeloRepository).deleteById(Mockito.<UUID>any());
//        verify(modeloRepository).findById(Mockito.<UUID>any());
//        assertEquals("apagado", actualDeleteResult.getBody());
//        assertEquals(200, actualDeleteResult.getStatusCodeValue());
//        assertTrue(actualDeleteResult.getHeaders().isEmpty());
//    }
}

