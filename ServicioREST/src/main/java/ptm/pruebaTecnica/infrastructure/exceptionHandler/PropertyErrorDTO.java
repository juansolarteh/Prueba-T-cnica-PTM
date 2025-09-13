package ptm.pruebaTecnica.infrastructure.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PropertyErrorDTO {
    private String property;
    private String error;
}
