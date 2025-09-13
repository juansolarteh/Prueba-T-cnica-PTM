package ptm.pruebaTecnica.application.exceptions;

public class ProductoNoExistenteException extends RuntimeException {
    public ProductoNoExistenteException(long id) {
        super("El producto con id " + id + " no existe.");
    }
}
