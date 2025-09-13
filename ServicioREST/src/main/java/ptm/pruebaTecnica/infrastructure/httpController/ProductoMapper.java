package ptm.pruebaTecnica.infrastructure.httpController;

import org.mapstruct.Mapper;
import ptm.pruebaTecnica.domain.Producto;
import ptm.pruebaTecnica.infrastructure.httpController.DTOs.ProductoDTO;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    ProductoDTO toDTO(Producto producto);
}
