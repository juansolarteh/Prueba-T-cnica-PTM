package ptm.pruebaTecnica.application.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptm.pruebaTecnica.application.ports.in.CreateProductoPort;
import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import ptm.pruebaTecnica.domain.Producto;

@Service
@AllArgsConstructor
public class CreateProductoService implements CreateProductoPort {
    private final ProductoRepositoryPort productoRepositoryPort;

    @Override
    public Producto execute(String nombre, String descripcion, double precio, int cantidadStock) {
        Producto producto = Producto.create(nombre, descripcion, precio, cantidadStock);
        return productoRepositoryPort.save(producto);
    }
}
