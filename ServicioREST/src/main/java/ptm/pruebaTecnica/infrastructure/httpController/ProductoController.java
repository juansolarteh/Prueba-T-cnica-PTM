package ptm.pruebaTecnica.infrastructure.httpController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ptm.pruebaTecnica.application.DTOs.ProductoConminationDTO;
import ptm.pruebaTecnica.application.ports.in.*;
import ptm.pruebaTecnica.domain.Producto;
import ptm.pruebaTecnica.infrastructure.exceptionHandler.PropertyErrorDTO;
import ptm.pruebaTecnica.infrastructure.exceptionHandler.UniqueErrorDTO;
import ptm.pruebaTecnica.infrastructure.httpController.DTOs.CommandProductoDTO;
import ptm.pruebaTecnica.infrastructure.httpController.DTOs.ProductoDTO;

import java.util.List;

@RestController
@RequestMapping("/productos")
@AllArgsConstructor
@Tag(name = "Productos", description = "Endpoints para la gestión de productos")
@CrossOrigin(origins = "*")
@Validated
public class ProductoController {
    private final CreateProductoPort createProductoPort;
    private final GetAllProductosPort getAllProductosPort;
    private final UpdateProductoPort updateProductoPort;
    private final DeleteProductoByIdPort deleteProductoByIdPort;
    private final GetProductosCombinationsPort getProductosCombinationsPort;
    private final ProductoMapper productoMapper;

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Registra un nuevo producto en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                    content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida, datos incorrectos o faltantes en el cuerpo",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PropertyErrorDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = UniqueErrorDTO.class)))
    })
    public ResponseEntity<ProductoDTO> post(@RequestBody @Valid CommandProductoDTO commandProductoDTO) {
        Producto producto = createProductoPort.execute(
                commandProductoDTO.getNombre(),
                commandProductoDTO.getDescripcion(),
                commandProductoDTO.getPrecio(),
                commandProductoDTO.getCantidadStock());
        ProductoDTO result = productoMapper.toDTO(producto);
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Recupera una lista de todos los productos registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos recuperada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = UniqueErrorDTO.class)))
    })
    public ResponseEntity<List<ProductoDTO>> get() {
        List<ProductoDTO> result = getAllProductosPort
                .execute()
                .stream()
                .map(productoMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente", description = "Actualiza los detalles de un producto existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida, datos incorrectos o faltantes en el cuerpo",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PropertyErrorDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(schema = @Schema(implementation = UniqueErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = UniqueErrorDTO.class)))
    })
    public ResponseEntity<ProductoDTO> put(@PathVariable @Min(1) Long id, @RequestBody @Valid CommandProductoDTO commandProductoDTO) {
        Producto producto = updateProductoPort.execute(
                id,
                commandProductoDTO.getNombre(),
                commandProductoDTO.getDescripcion(),
                commandProductoDTO.getPrecio(),
                commandProductoDTO.getCantidadStock());
        ProductoDTO result = productoMapper.toDTO(producto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto por ID", description = "Elimina un producto existente utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(schema = @Schema(implementation = UniqueErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = UniqueErrorDTO.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        deleteProductoByIdPort.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/combinaciones")
    @Operation(summary = "Obtener combinaciones de productos por valor máximo", description = "Recupera combinaciones de productos cuya suma de precios no exceda un valor dado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de combinaciones de productos recuperada exitosamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductoConminationDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida, parámetro de consulta incorrecto o faltante",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PropertyErrorDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = UniqueErrorDTO.class)))
    })
    public ResponseEntity<List<ProductoConminationDTO>> get(@RequestParam @DecimalMin("0.01") double valorComparacion) {
        List<ProductoConminationDTO> result = getProductosCombinationsPort.execute(valorComparacion);
        return ResponseEntity.ok(result);
    }
}
