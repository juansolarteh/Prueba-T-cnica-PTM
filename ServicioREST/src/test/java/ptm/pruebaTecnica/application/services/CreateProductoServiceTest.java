package ptm.pruebaTecnica.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import ptm.pruebaTecnica.domain.Producto;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductoServiceTest {
    @Mock
    private ProductoRepositoryPort productoRepositoryPort;
    @InjectMocks
    private CreateProductoService createProductoService;
    @Test
    void shouldCreateProducto() {
        // Arrange
        when(productoRepositoryPort.save(any(Producto.class))).thenReturn(mock(Producto.class));

        // Act
        Producto producto = createProductoService.execute("Producto1", "Descripcion1", 100.0, 10);

        // Assert
        assertNotNull(producto);
        verify(productoRepositoryPort, times(1)).save(any(Producto.class));
    }
}