package ptm.pruebaTecnica.infrastructure.httpController.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommandProductoDTO {
    @NotNull
    @NotBlank
    private String nombre;
    @NotNull
    @NotBlank
    private String descripcion;
    @NotNull
    @DecimalMin("0.01")
    private Double precio;
    @NotNull
    @Min(0)
    private Integer cantidadStock;
}
