package ptm.pruebaTecnica.infrastructure.httpController.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadStock;
    private double valorInventario;
}
