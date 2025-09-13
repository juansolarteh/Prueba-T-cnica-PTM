package ptm.pruebaTecnica.application.services;

import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import ptm.pruebaTecnica.domain.Producto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductoServiceTest {
    @Mock
    private ProductoRepositoryPort productoRepositoryPort;
    @InjectMocks
    private CreateProductoService createUserService;

    @Test
    void shouldThrowExceptionWhenEmailIsDuplicated() {
        //Arrange
        when(productoRepositoryPort.existByEmail(anyString())).thenReturn(true);

        //Act - Assert
        assertThrows(DuplicatedEmailException.class, () -> createUserService.execute(1L, "John Doe", "email@hotmail.com"));
    }

    @Test
    void shouldCreateUserWhenIdAndEmailAreNotDuplicated() {
        //Arrange
        Producto newProducto = new Producto(1L, "John Doe", "email@hotmail.com");
        when(productoRepositoryPort.existByEmail(anyString())).thenReturn(false);

        //Act
        createUserService.execute(1L, "John Doe", "email@hotmail.com");

        //Assert
        verify(productoRepositoryPort, times(1)).save(eq(newProducto));
    }
}