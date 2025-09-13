package ptm.pruebaTecnica.application.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptm.pruebaTecnica.application.DTOs.ProductoConminationDTO;
import ptm.pruebaTecnica.application.ports.in.GetProductosCombinationsPort;
import ptm.pruebaTecnica.application.ports.out.ProductoRepositoryPort;
import ptm.pruebaTecnica.domain.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetProductosCombinationsService implements GetProductosCombinationsPort {
    private final ProductoRepositoryPort productoRepositoryPort;
    private final int LIMIT_ELEMENTS_DEFAULT = 5;
    @Override
    public List<ProductoConminationDTO> execute(double valorComparacion) {
        List<Producto> productos = productoRepositoryPort.findAllByPrecioLessThanOrderByPrecioDesc(valorComparacion);
        if (productos.isEmpty() || productos.size() < 2) return new ArrayList<>();
        List<ProductoConminationDTO> resultado = new ArrayList<>();

        //Combinaciones de 2 productos
        for (int i = 0; i < productos.size() - 1; i++) {
            Producto producto1 = productos.get(i);
            for (int j = i + 1; j < productos.size(); j++) {
                Producto producto2 = productos.get(j);
                double suma = producto1.getPrecio() + producto2.getPrecio();
                if (suma <= valorComparacion) {
                    resultado.add(new ProductoConminationDTO(List.of(producto1, producto2), suma));
                }
            }
        }

        if (productos.size() < 3) return resultado;

        //Combinaciones de 3 productos
        for (int i = 0; i < productos.size() - 2; i++) {
            Producto producto1 = productos.get(i);
            for (int j = i + 1; j < productos.size() - 1; j++) {
                Producto producto2 = productos.get(j);
                double filterSuma = producto1.getPrecio() + producto2.getPrecio();
                if (filterSuma >= valorComparacion) continue;
                for (int k = j + 1; k < productos.size(); k++) {
                    Producto producto3 = productos.get(k);
                    double suma = producto1.getPrecio() + producto2.getPrecio() + producto3.getPrecio();
                    if (suma <= valorComparacion) {
                        resultado.add(new ProductoConminationDTO(List.of(producto1, producto2, producto3), suma));
                    }
                }
            }
        }

        return resultado.stream()
                .sorted((a, b) -> Double.compare(b.getValorSumatoria(), a.getValorSumatoria()))
                .limit(LIMIT_ELEMENTS_DEFAULT)
                .collect(Collectors.toList());
    }
}
