package ptm.pruebaTecnica.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoJpaRepository extends JpaRepository<ProductoDatabaseEntity, Long> {
    List<ProductoDatabaseEntity> findByPrecioLessThanOrderByPrecioDesc(BigDecimal precio);
}
