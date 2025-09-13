package ptm.pruebaTecnica.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductoTest {
    private final Producto producto = new Producto(1L, "John Doe", "email@hotmail.com");
    @Test
    void shouldReturnTrueWhenIdsAreEqual() {
        assertTrue(producto.equalsId(1L));
    }
    @Test
    void shouldReturnTrueWhenEmailsAreEqual() {
        assertTrue(producto.equalsEmail("email@hotmail.com"));
    }
}