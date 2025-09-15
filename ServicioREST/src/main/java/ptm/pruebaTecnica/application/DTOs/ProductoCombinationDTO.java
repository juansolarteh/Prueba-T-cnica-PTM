package ptm.pruebaTecnica.application.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import ptm.pruebaTecnica.domain.Producto;

import java.util.List;

@AllArgsConstructor
@Data
public class ProductoCombinationDTO {
    private List<Producto> productos;
    private double valorSumatoria;
}
