import com.seta.tis4.model.dtos.produto.TecidoDTO;
import com.seta.tis4.model.entities.produto.Tecido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import com.seta.tis4.model.repositories.TecidoRepository;

@ExtendWith(MockitoExtension.class)
public class TecidoServiceTest {
    @InjectMocks
    TecidoService tecidoService;
    @Mock
    TecidoRepository tecidoRepository;
    Tecido tecido;
    @BeforeEach
    public void setUp(){
        Tecido tecido = new Tecido("Algodão", "1921", "Renner", "Malha");
        TecidoDTO tecidoDTO = new TecidoDTO("Algodão", "1921", "Renner", "Malha")
    }
    @Test
    void deleteTecidoByIdWithSuccess() {
        UUID id = UUID.randomUUID();
        doNothing().when(tecidoRepository).deleteById(id);
        when(tecidoRepository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = tecidoService.delete(id);
        verify(tecidoRepository, times(1)).deleteById(id);
        verify(tecidoRepository, times(1)).findById(id);
        assertEquals("apagado", responseEntity.getBody());
    }
    @Test
    void createTecidoWithSuccess(){
        when(tecidoRepository.save(any(Tecido.class))).thenReturn(tecido);
        ResponseEntity<?> responseEntity = tecidoService.create(tecidoDTO);
        verify(tecidoRepository, times(1)).save(any(Tecido.class));
        assertEquals(tecido, responseEntity.getBody());
    }
}
