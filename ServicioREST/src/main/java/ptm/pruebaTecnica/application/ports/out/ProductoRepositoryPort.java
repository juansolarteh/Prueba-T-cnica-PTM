package ptm.pruebaTecnica.application.ports.out;

import ptm.pruebaTecnica.domain.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepositoryPort {
    Producto save(Producto producto);

    List<Producto> findAll();

    Optional<Producto> findById(long id);

    void delete(Producto producto);

    List<Producto> findAllByPrecioLessThanOrderByPrecioDesc(double valorComparacion);
}
