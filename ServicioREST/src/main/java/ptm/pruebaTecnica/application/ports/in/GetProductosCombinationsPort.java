package ptm.pruebaTecnica.application.ports.in;

import ptm.pruebaTecnica.application.DTOs.ProductoConminationDTO;

import java.util.List;

public interface GetProductosCombinationsPort {
    List<ProductoConminationDTO> execute(double valorComparacion);
}
