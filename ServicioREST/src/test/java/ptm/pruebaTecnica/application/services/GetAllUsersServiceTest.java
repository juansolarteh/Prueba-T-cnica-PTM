package ptm.pruebaTecnica.application.services;

import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetAllUsersServiceTest {
    @Mock
    private ProductoRepositoryPort productoRepositoryPort;
    @InjectMocks
    private GetAllProductosService getAllUsersService;
    @Test
    void shouldCallFindAllMethodOfUserRepositoryPort() {
        //Act
        getAllUsersService.execute();

        //Assert
        verify(productoRepositoryPort, times(1)).findAll();
    }
}