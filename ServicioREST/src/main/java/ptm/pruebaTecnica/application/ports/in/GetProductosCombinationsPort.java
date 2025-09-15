package ptm.pruebaTecnica.application.ports.in;

import ptm.pruebaTecnica.application.DTOs.ProductoCombinationDTO;

import java.util.List;

public interface GetProductosCombinationsPort {
    List<ProductoCombinationDTO> execute(double valorComparacion);
}
