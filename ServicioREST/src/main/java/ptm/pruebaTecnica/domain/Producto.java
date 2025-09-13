package ptm.pruebaTecnica.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Producto {
    @Setter
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadStock;

    public static Producto create(String nombre, String descripcion, double precio, int cantidadStock) {
        return new Producto(null, nombre, descripcion, precio, cantidadStock);
    }

    public boolean update(String nombre, String descripcion, double precio, int cantidadStock) {
        if (this.nombre.equals(nombre)
                && this.descripcion.equals(descripcion)
                && this.precio == precio
                && this.cantidadStock == cantidadStock)
            return false; // No changes

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        return true;
    }

    public double getValorInventario() {
        return this.precio * this.cantidadStock;
    }
}
