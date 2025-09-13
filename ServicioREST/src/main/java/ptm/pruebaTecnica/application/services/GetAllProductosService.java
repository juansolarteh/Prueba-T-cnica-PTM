package ptm.pruebaTecnica.application.services;

import ptm.pruebaTecnica.application.ports.in.GetAllProductosPort;
import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import ptm.pruebaTecnica.domain.Producto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllProductosService implements GetAllProductosPort {
    private final ProductoRepositoryPort productoRepositoryPort;
    @Override
    public List<Producto> execute() {
        return productoRepositoryPort.findAll();
    }
}
