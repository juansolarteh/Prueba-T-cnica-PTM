package ptm.pruebaTecnica.application.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptm.pruebaTecnica.application.exceptions.ProductoNoExistenteException;
import ptm.pruebaTecnica.application.ports.in.UpdateProductoPort;
import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import ptm.pruebaTecnica.domain.Producto;

@Service
@AllArgsConstructor
public class UpdateProductoService implements UpdateProductoPort {
    private final ProductoRepositoryPort productoRepositoryPort;

    /**
     * Actualiza un producto existente.
     * @param id el id del producto a actualizar
     * @param nombre nuevo nombre
     * @param descripcion nueva descripcion
     * @param precio nuevo precio
     * @param cantidadStock nueva cantidad en stock
     * @return el producto actualizado o el mismo si no hubo cambios
     * @throws ProductoNoExistenteException si el producto con el id dado no existe
     */
    @Override
    public Producto execute(long id, String nombre, String descripcion, double precio, int cantidadStock) {
        Producto producto = productoRepositoryPort
                .findById(id)
                .orElseThrow(() -> new ProductoNoExistenteException(id));

        boolean updated = producto.update(nombre, descripcion, precio, cantidadStock);

        if (updated) return productoRepositoryPort.save(producto);
        else return producto;
    }
}
