package ptm.pruebaTecnica.infrastructure.persistence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import ptm.pruebaTecnica.domain.Producto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductoMysqlRepository implements ProductoRepositoryPort {
    private final ProductoJpaRepository productoJpaRepository;
    private final ProductoDatabaseEntityMapper productoDatabaseEntityMapper;

    @Override
    public Producto save(Producto producto) {
        ProductoDatabaseEntity productoDatabase = productoDatabaseEntityMapper.toDatabaseEntity(producto);
        productoJpaRepository.save(productoDatabase);
        producto.setId(productoDatabase.getId());
        return producto;
    }

    @Override
    public List<Producto> findAll() {
        return productoJpaRepository.findAll()
                .stream()
                .map(productoDatabaseEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Producto> findById(long id) {
        return productoJpaRepository
                .findById(id)
                .map(productoDatabaseEntityMapper::toDomain);
    }

    @Override
    public void delete(Producto producto) {
        productoJpaRepository.deleteById(producto.getId());
    }

    @Override
    public List<Producto> findAllByPrecioLessThanOrderByPrecioDesc(double valorComparacion) {
        BigDecimal precio = new BigDecimal(valorComparacion);
        return productoJpaRepository
                .findByPrecioLessThanOrderByPrecioDesc(precio)
                .stream()
                .map(productoDatabaseEntityMapper::toDomain)
                .toList();
    }
}
