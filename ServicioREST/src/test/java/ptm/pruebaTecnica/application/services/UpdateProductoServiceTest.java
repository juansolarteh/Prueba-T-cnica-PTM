package ptm.pruebaTecnica.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ptm.pruebaTecnica.application.exceptions.ProductoNoExistenteException;
import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import ptm.pruebaTecnica.domain.Producto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductoServiceTest {
    @Mock
    private ProductoRepositoryPort productoRepositoryPort;
    @InjectMocks
    private UpdateProductoService updateProductoService;
    @Test
    void throwProductoNoExistenteExceptionWhenProductoNotFound() {
        // Arrange
        when(productoRepositoryPort.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductoNoExistenteException.class, () -> updateProductoService.execute(1L, "Nombre", "Descripcion", 100.0, 10));
    }

    @Test
    void shouldNotUpdateWhenNoChanges() {
        // Arrange
        Producto producto = mock(Producto.class);
        when(productoRepositoryPort.findById(anyLong())).thenReturn(Optional.of(producto));
        when(producto.update(anyString(), anyString(), anyDouble(), anyInt())).thenReturn(false);

        // Act
        Producto result = updateProductoService.execute(1L, "Nombre", "Descripcion", 100.0, 10);

        // Assert
        assertNotNull(result);
        verify(productoRepositoryPort, times(1)).findById(anyLong());
        verify(productoRepositoryPort, never()).save(any(Producto.class));
    }

    @Test
    void shouldUpdateWhenThereAreChanges() {
        // Arrange
        Producto producto = mock(Producto.class);
        when(productoRepositoryPort.findById(anyLong())).thenReturn(Optional.of(producto));
        when(producto.update(anyString(), anyString(), anyDouble(), anyInt())).thenReturn(true);
        when(productoRepositoryPort.save(any(Producto.class))).thenReturn(producto);

        // Act
        Producto result = updateProductoService.execute(1L, "Nombre", "Descripcion", 100.0, 10);

        // Assert
        assertNotNull(result);
        verify(productoRepositoryPort, times(1)).findById(anyLong());
        verify(productoRepositoryPort, times(1)).save(any(Producto.class));
        assertEquals(producto, result);
    }
}