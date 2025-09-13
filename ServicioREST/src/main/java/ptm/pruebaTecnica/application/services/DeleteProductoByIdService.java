package ptm.pruebaTecnica.application.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptm.pruebaTecnica.application.exceptions.ProductoNoExistenteException;
import ptm.pruebaTecnica.application.ports.in.DeleteProductoByIdPort;
import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import ptm.pruebaTecnica.domain.Producto;

@Service
@AllArgsConstructor
public class DeleteProductoByIdService implements DeleteProductoByIdPort {
    private final ProductoRepositoryPort productoRepositoryPort;

    @Override
    public void execute(long id) {
        Producto producto = productoRepositoryPort
                .findById(id)
                .orElseThrow(() -> new ProductoNoExistenteException(id));

        productoRepositoryPort.delete(producto);
    }
}
