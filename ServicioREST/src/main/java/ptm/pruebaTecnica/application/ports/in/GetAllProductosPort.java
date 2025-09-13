package ptm.pruebaTecnica.application.ports.in;

import ptm.pruebaTecnica.domain.Producto;

import java.util.List;

public interface GetAllProductosPort {
    List<Producto> execute();
}
