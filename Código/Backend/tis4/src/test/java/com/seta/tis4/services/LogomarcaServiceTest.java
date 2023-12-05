package com.seta.tis4.services;

import com.seta.tis4.model.dtos.produto.LogoMarcaDTO;
import com.seta.tis4.model.entities.Imagem;
import com.seta.tis4.model.entities.produto.LogoMarca;
import com.seta.tis4.model.repositories.ImagemRepository;
import com.seta.tis4.model.repositories.LogomarcaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LogomarcaServiceTest {

    private LogomarcaRepository logomarcaRepository;
    private ImagemRepository imagemRepository;
    private LogomarcaService logomarcaService;
    private LogoMarcaDTO logoMarcaDTO;
    private Imagem imagem;

    @BeforeEach
    void setUp() {
        logomarcaRepository = mock(LogomarcaRepository.class);
        imagemRepository = mock(ImagemRepository.class);
        logomarcaService = new LogomarcaService(logomarcaRepository, imagemRepository);
        logoMarcaDTO = new LogoMarcaDTO(1L, "Descrição", true, "url_da_imagem");
        imagem = new Imagem("url_da_imagem");
    }

    @Test
    void testGetLogomarcas() {
        when(logomarcaRepository.findAll()).thenReturn(java.util.List.of());

        ResponseEntity<?> response = logomarcaService.get();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof java.util.List);
        verify(logomarcaRepository).findAll();
    }



    @Test
    void testDeleteLogomarcaById() {
        when(logomarcaRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = logomarcaService.delete(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("apagado", response.getBody());
        verify(logomarcaRepository).deleteById(1L);
    }


    @Test
    void testUpdateLogomarca() {
        when(logomarcaRepository.findById(1L)).thenReturn(Optional.of(new LogoMarca()));
        when(logomarcaRepository.save(any(LogoMarca.class))).thenReturn(new LogoMarca());

        ResponseEntity<?> response = logomarcaService.update(logoMarcaDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(logomarcaRepository).findById(1L);
        verify(logomarcaRepository).save(any(LogoMarca.class));
    }
}
