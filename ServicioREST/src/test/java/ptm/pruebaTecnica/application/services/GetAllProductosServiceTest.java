package ptm.pruebaTecnica.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import ptm.pruebaTecnica.domain.Producto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllProductosServiceTest {
    @Mock
    private ProductoRepositoryPort productoRepositoryPort;
    @InjectMocks
    private GetAllProductosService getAllProductosService;
    @Test
    void shouldGetAllProductos() {
        // Arrange
        when(productoRepositoryPort.findAll()).thenReturn(List.of(mock(Producto.class)));

        // Act
        List<Producto> productos = getAllProductosService.execute();

        // Assert
        assertEquals(1, productos.size());
    }
}