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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductoByIdServiceTest {
    @Mock
    private ProductoRepositoryPort productoRepositoryPort;
    @InjectMocks
    private DeleteProductoByIdService deleteProductoByIdService;
    @Test
    void shouldThrowExceptionWhenProductoNotFound() {
        // Arrange
        when(productoRepositoryPort.findById(anyLong())).thenReturn(Optional.empty());

        // Act And Assert
        assertThrows(ProductoNoExistenteException.class, () -> deleteProductoByIdService.execute(1L));
    }

    @Test
    void shouldDeleteProductoById() {
        // Arrange
        when(productoRepositoryPort.findById(anyLong())).thenReturn(Optional.of(mock(Producto.class)));

        // Act
        deleteProductoByIdService.execute(1L);

        // Assert
        verify(productoRepositoryPort, times(1)).findById(anyLong());
        verify(productoRepositoryPort, times(1)).delete(any(Producto.class));
    }
}