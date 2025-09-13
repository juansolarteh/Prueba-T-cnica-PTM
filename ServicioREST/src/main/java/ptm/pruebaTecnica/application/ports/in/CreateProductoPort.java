package ptm.pruebaTecnica.application.ports.in;

import ptm.pruebaTecnica.domain.Producto;

public interface CreateProductoPort {
    Producto execute(String nombre, String descripcion, double precio, int cantidadStock);
}