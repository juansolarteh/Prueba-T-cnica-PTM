package ptm.pruebaTecnica.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ptm.pruebaTecnica.application.DTOs.ProductoCombinationDTO;
import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import ptm.pruebaTecnica.domain.Producto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductosCombinationsServiceTest {
    @Mock
    private ProductoRepositoryPort productoRepositoryPort;
    @InjectMocks
    private GetProductosCombinationsService getProductosCombinationsService;
    private final Producto producto1 = new Producto(1L, "Producto 1", "Descripcion 1", 10.0, 1);
    private final Producto producto2 = new Producto(2L, "Producto 2", "Descripcion 2", 20.0, 1);
    private final Producto producto3 = new Producto(3L, "Producto 3", "Descripcion 3", 30.0, 1);
    @Test
    void shouldReturnEmptyListWhenNoProducts() {
        // Arrange
        when(productoRepositoryPort.findAllByPrecioLessThanOrderByPrecioDesc(anyDouble())).thenReturn(List.of());

        // Act
        List<ProductoCombinationDTO> result = getProductosCombinationsService.execute(50.0);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenOneProduct() {
        // Arrange
        when(productoRepositoryPort.findAllByPrecioLessThanOrderByPrecioDesc(anyDouble())).thenReturn(List.of(producto1));

        // Act
        List<ProductoCombinationDTO> result = getProductosCombinationsService.execute(50.0);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnCombinationsOfTwoProducts() {
        // Arrange
        when(productoRepositoryPort.findAllByPrecioLessThanOrderByPrecioDesc(anyDouble()))
                .thenReturn(List.of(producto1, producto2));

        // Act
        List<ProductoCombinationDTO> result = getProductosCombinationsService.execute(30.0);

        // Assert
        assertEquals(1, result.size());
        ProductoCombinationDTO combination = result.get(0);
        assertEquals(2, combination.getProductos().size());
        assertEquals(30.0, combination.getValorSumatoria());
        assertTrue(combination.getProductos().contains(producto1));
        assertTrue(combination.getProductos().contains(producto2));
    }

    @Test
    void shouldReturnCombinationsOfThreeProducts() {
        // Arrange
        when(productoRepositoryPort.findAllByPrecioLessThanOrderByPrecioDesc(anyDouble()))
                .thenReturn(List.of(producto1, producto2, producto3));

        // Act
        List<ProductoCombinationDTO> result = getProductosCombinationsService.execute(60.0);

        // Assert
        assertEquals(4, result.size());
        ProductoCombinationDTO combination1 = result.get(0);
        assertEquals(3, combination1.getProductos().size());
        assertEquals(60.0, combination1.getValorSumatoria());
        assertTrue(combination1.getProductos().contains(producto1));
        assertTrue(combination1.getProductos().contains(producto2));
        assertTrue(combination1.getProductos().contains(producto3));
        ProductoCombinationDTO combination2 = result.get(1);
        assertEquals(2, combination2.getProductos().size());
        assertEquals(50.0, combination2.getValorSumatoria());
        assertTrue(combination2.getProductos().contains(producto2));
        assertTrue(combination2.getProductos().contains(producto3));
        ProductoCombinationDTO combination3 = result.get(2);
        assertEquals(2, combination3.getProductos().size());
        assertEquals(40.0, combination3.getValorSumatoria());
        assertTrue(combination3.getProductos().contains(producto1));
        assertTrue(combination3.getProductos().contains(producto3));
        ProductoCombinationDTO combination4 = result.get(3);
        assertEquals(2, combination4.getProductos().size());
        assertEquals(30.0, combination4.getValorSumatoria());
        assertTrue(combination4.getProductos().contains(producto1));
        assertTrue(combination4.getProductos().contains(producto2));
    }
}