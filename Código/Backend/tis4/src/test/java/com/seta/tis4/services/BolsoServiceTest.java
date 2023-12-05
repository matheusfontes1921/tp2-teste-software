package com.seta.tis4.services;

import com.seta.tis4.model.dtos.produto.BolsoDTO;
import com.seta.tis4.model.entities.produto.Bolso;
import com.seta.tis4.model.repositories.BolsoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BolsoServiceTest {

    @Mock
    private BolsoRepository bolsoRepository;

    @InjectMocks
    private BolsoService bolsoService;

    private Bolso bolso;
    private BolsoDTO bolsoDTO;

    @BeforeEach
    public void setUp() {
        bolso = new Bolso(3, "Bolso de couro");
        bolsoDTO = new BolsoDTO(null, 3, "Bolso de couro");
    }

    @Test
    public void testCreate() {
        when(bolsoRepository.save(any(Bolso.class))).thenReturn(bolso);

        ResponseEntity<?> response = bolsoService.create(bolsoDTO);

        assertEquals(ResponseEntity.ok().body(bolso.getId()), response);
        verify(bolsoRepository, times(1)).save(any(Bolso.class));
    }

    @Test
    public void testDelete() {
        when(bolsoRepository.save(any(Bolso.class))).thenReturn(bolso);
        Bolso savedBolso = bolsoRepository.save(bolso);
        Long id = savedBolso.getId();

        when(bolsoRepository.findById(id)).thenReturn(Optional.of(savedBolso));
        doNothing().when(bolsoRepository).deleteById(id);

        ResponseEntity<?> response = bolsoService.delete(id);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(bolsoRepository, times(1)).deleteById(id);
        verify(bolsoRepository, times(1)).findById(id);
    }


    @Test
    public void testCreateWithNullBolso() {
        when(bolsoRepository.save(any(Bolso.class))).thenReturn(null);

        ResponseEntity<?> response = bolsoService.create(bolsoDTO);

        assertEquals(ResponseEntity.badRequest().body("Erro ao criar bolso"), response);
        verify(bolsoRepository, times(1)).save(any(Bolso.class));
    }


    @Test
    public void testDeleteNonExistingId() {
        Long id = 99L;

        ResponseEntity<?> response = bolsoService.delete(id);

        assertEquals(ResponseEntity.badRequest().body("id nao existe"), response);
        verify(bolsoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateNonExistingBolso() {
        when(bolsoRepository.findById(bolsoDTO.id())).thenReturn(Optional.empty());

        ResponseEntity<?> response = bolsoService.update(bolsoDTO);

        assertEquals(ResponseEntity.badRequest().body("Erro no update"), response);
        verify(bolsoRepository, times(1)).findById(bolsoDTO.id());
    }

    @Test
    public void testUpdateWithNullId() {
        var dtoTeste = new BolsoDTO(null, 3, "Cruzeiro");

        ResponseEntity<?> response = bolsoService.update(dtoTeste);

        assertEquals(ResponseEntity.badRequest().body("Erro no update"), response);
    }

    @Test
    public void testUpdateExistingBolso() {
        var bolsoDTOwithId = new BolsoDTO(bolso.getId(),3,"teste" );


        when(bolsoRepository.findById(bolso.getId())).thenReturn(Optional.of(bolso));

        when(bolsoRepository.save(any(Bolso.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<?> response = bolsoService.update(bolsoDTOwithId);

        assertEquals(ResponseEntity.ok().body(bolso), response);
        verify(bolsoRepository, times(1)).findById(bolsoDTOwithId.id());
        verify(bolsoRepository, times(1)).save(bolso);

        assertEquals("teste", bolso.getDescricao());
        assertEquals(3, bolso.getQuantidadeBolsos());
    }
}


