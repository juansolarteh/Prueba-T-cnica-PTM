package ptm.pruebaTecnica.infrastructure.persistence;

import ptm.pruebaTecnica.domain.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoDatabaseEntityMapper {
    ProductoDatabaseEntity toDatabaseEntity(Producto producto);
    Producto toDomain(ProductoDatabaseEntity productoDatabaseEntity);
}
