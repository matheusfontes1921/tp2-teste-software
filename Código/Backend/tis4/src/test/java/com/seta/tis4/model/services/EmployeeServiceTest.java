import com.seta.tis4.model.entities.user.Employee;
import com.seta.tis4.model.repositories.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    EmployeeService employeeService;
    @Mock
    EmployeeRepository employeeRepository;
    Employee employee;
    @BeforeEach
    public void setUp(){
        Employee employee = new Employee("Matheus", "matheusfontes1921@gmail.com", "Cruzeiro1921");
    }
    @Test
    void findEmployeeByEmailWithSuccess(){
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);
        Boolean existsBoolean = employeeService.findByEmail(employee.getEmail());
        assertEquals(true, existsBoolean);
        verify(employeeRepository).findByEmail(employee.getEmail());
        verifyNoInteractions(employeeRepository);
    }
    @Test
    void createEmployeeWithEmailAlreadyExists() {
        CreateEmployeeDTO existingEmailDTO = new CreateEmployeeDTO("email_que_ja_existe", "senha");
        when(employeeRepository.existsByEmail(existingEmailDTO.email())).thenReturn(true);
        ResponseEntity<?> response = employeeService.createEmployee(existingEmailDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email já cadastrado", response.getBody());
        verifyNoInteractions(employeeRepository);
    }

    @Test
    void createEmployeeWithEmailDoesNotExist() {
        CreateEmployeeDTO nonExistingEmailDTO = new CreateEmployeeDTO("email_novo", "senha");
        when(employeeRepository.existsByEmail(nonExistingEmailDTO.email())).thenReturn(false);
        when(encoder.encode(anyString())).thenReturn("senha_criptografada");
        ResponseEntity<?> response = employeeService.createEmployee(nonExistingEmailDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("objeto_correto", response.getBody());
        verify(employeeRepository).save(any(Employee.class));
    }
    @Test
    void deleteEmployeeWhenIdExists() {
        UUID existingId = UUID.randomUUID();
        when(employeeRepository.findById(existingId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> response = employeeService.deleteEmployee(existingId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário deletado com sucesso", response.getBody());
        verify(employeeRepository).deleteById(existingId);
    }

    @Test
    void deleteEmployeeWhenIdDoesNotExist() {
        UUID nonExistingId = UUID.randomUUID();
        when(employeeRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        ResponseEntity<?> response = employeeService.deleteEmployee(nonExistingId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Não existe um funcionário com esse id", response.getBody());
        verifyNoInteractions(employeeRepository);
    }
}
