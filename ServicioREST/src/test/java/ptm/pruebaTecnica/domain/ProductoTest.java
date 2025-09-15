package ptm.pruebaTecnica.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {
    @Test
    void shouldCreateProduct() {
        Producto producto = Producto.create("Laptop", "High-end gaming laptop", 1500.0, 10);
        assertNotNull(producto);
        assertNull(producto.getId());
        assertEquals("Laptop", producto.getNombre());
        assertEquals("High-end gaming laptop", producto.getDescripcion());
        assertEquals(1500.0, producto.getPrecio());
        assertEquals(10, producto.getCantidadStock());
    }

    @Test
    void shouldUpdateProduct() {
        Producto producto = new Producto(1L, "Laptop", "High-end gaming laptop", 1500.0, 10);
        boolean updated = producto.update("Laptop Pro", "High-end gaming laptop with extra features", 1800.0, 8);
        assertTrue(updated);
        assertEquals(1L, producto.getId());
        assertEquals("Laptop Pro", producto.getNombre());
        assertEquals("High-end gaming laptop with extra features", producto.getDescripcion());
        assertEquals(1800.0, producto.getPrecio());
        assertEquals(8, producto.getCantidadStock());
    }

    @Test
    void shouldNotUpdateProductIfNoChanges() {
        Producto producto = new Producto(1L, "Laptop", "High-end gaming laptop", 1500.0, 10);
        boolean updated = producto.update("Laptop", "High-end gaming laptop", 1500.0, 10);
        assertFalse(updated);
    }

    @Test
    void shouldCalculateInventoryValue() {
        Producto producto = new Producto(1L, "Laptop", "High-end gaming laptop", 1500.0, 10);
        double valorInventario = producto.getValorInventario();
        assertEquals(15000.0, valorInventario);
    }
}