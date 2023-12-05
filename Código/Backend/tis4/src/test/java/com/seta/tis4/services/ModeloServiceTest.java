package com.seta.tis4.services;

import com.seta.tis4.model.dtos.produto.ModeloDTO;
import com.seta.tis4.model.entities.produto.Modelo;
import com.seta.tis4.model.repositories.ModeloRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ModeloServiceTest {

    private final ModeloRepository modeloRepository = mock(ModeloRepository.class);
    private final ModeloService modeloService = new ModeloService(modeloRepository);

    @Test
    void testOptionsWithEmptyModelList() {
        when(modeloRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = modeloService.options();

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Não existe modelo cadastrados", response.getBody());
    }

    @Test
    void testOptionsWithModelList() {
        Modelo modelo = new Modelo();
        modelo.setId(UUID.randomUUID());
        modelo.setDetalhes("Detalhes do Modelo");

        when(modeloRepository.findAll()).thenReturn(Collections.singletonList(modelo));

        ResponseEntity<?> response = modeloService.options();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAll() {
        Modelo modelo = new Modelo();
        modelo.setId(UUID.randomUUID());
        modelo.setDetalhes("Detalhes do Modelo");

        when(modeloRepository.findAll()).thenReturn(Collections.singletonList(modelo));

        ResponseEntity<?> response = modeloService.getAll();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCreate() {
        ModeloDTO modeloDTO = new ModeloDTO(UUID.randomUUID(), "Detalhes do Modelo");

        when(modeloRepository.save(any(Modelo.class))).thenReturn(new Modelo());

        ResponseEntity<?> response = modeloService.create(modeloDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(modeloRepository).save(any(Modelo.class));
    }

    @Test
    void testUpdate() {
        ModeloDTO modeloDTO = new ModeloDTO(UUID.randomUUID(), "Detalhes Atualizados");

        when(modeloRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Modelo()));

        ResponseEntity<?> response = modeloService.update(modeloDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(modeloRepository).saveAndFlush(any(Modelo.class));
    }

    @Test
    void testDeleteWithInvalidId() {
        UUID existingId = UUID.randomUUID();
        when(modeloRepository.findById(existingId)).thenReturn(Optional.of(new Modelo()));

        ResponseEntity<?> response = modeloService.delete(existingId);

        assertEquals(400, response.getStatusCodeValue());
        verify(modeloRepository).deleteById(existingId);
    }

    @Test
    void testDeleteWithValidId() {
        UUID nonExistingId = UUID.randomUUID();
        when(modeloRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = modeloService.delete(nonExistingId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("apagado", response.getBody());
        verify(modeloRepository, never()).deleteById(any(UUID.class));
    }
}
