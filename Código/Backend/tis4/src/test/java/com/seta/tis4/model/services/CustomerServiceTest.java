
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import com.seta.tis4.model.entities.user.customer.Customer;
import com.seta.tis4.model.entities.user.customer.CustomerType;
import com.seta.tis4.model.repositories.CustomerRepository;
import static org.mockito.Mockito.*;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class CostumerServiceTest {

    @InjectMocks
    CustomerService customerService;
    @Mock
    CustomerRepository customerRepository;
    Customer customer;
    @BeforeEach
    public void setUp() {
        Customer customer = new Customer("Matheus", "(31) 99860-4412", "matheusfontes1921@gmail.com", "Rua Cruzeiro, 1921", "15546727675", CustomerType.PF);
    }
    @Test
    void findCustomerByCpfCnpjWithSuccess() {
        when(customerRepository.existsByCpfCnpj(customer.getCpfCnpj())).thenReturn(true);
        Boolean exists = customerService.existsByCpfCnpj(customer.getCpfCnpj());
        assertEquals(true, exists);
        verify(customerRepository).existsByCpfCnpj(customer.getCpfCnpj());
        verifyNoInteractions(customerRepository);
    } 
    @Test
    void findCustomerByEmailWithSuccess() {
        when(customerRepository.existsByEmail(customer.getEmail())).thenReturn(true);
        Boolean existsEmail = customerService.existsByEmail(customer.getEmail());
        assertEquals(true, existsEmail);
        verify(customerRepository).existsByEmail(customer.getEmail());
        verifyNoInteractions(customerRepository);     
    }
    @Test
    void findCustomerByPhoneWithSuccess() {
        when(customerRepository.existsByTelefone(customer.getTelefone())).thenReturn(true);
        Boolean existsTelefone = customerService.existsByTelefone(customer.getTelefone());
        assertEquals(true, existsTelefone);
        verify(customerRepository).existsByTelefone(customer.getTelefone());
        verifyNoInteractions(customerRepository);
    }
}

