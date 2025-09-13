package ptm.pruebaTecnica.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Entity
@Table(name = "Producto")
@NoArgsConstructor
public class ProductoDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false, scale = 2)
    private BigDecimal precio;
    @Column(nullable = false)
    private Integer cantidadStock;
}
