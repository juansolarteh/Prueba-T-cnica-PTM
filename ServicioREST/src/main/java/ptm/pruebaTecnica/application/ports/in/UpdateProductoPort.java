package ptm.pruebaTecnica.application.ports.in;

import ptm.pruebaTecnica.domain.Producto;

public interface UpdateProductoPort {
    Producto execute(long id, String nombre, String descripcion, double precio, int cantidadStock);
}
