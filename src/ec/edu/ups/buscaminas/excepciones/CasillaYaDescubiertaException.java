package ec.edu.ups.buscaminas.excepciones;

/**
 * Excepción personalizada para validar la lógica del juego.
 * Se lanza cuando el usuario intenta seleccionar una casilla que ya ha sido revelada previamente.
 */
public class CasillaYaDescubiertaException extends Exception {
    public CasillaYaDescubiertaException(String mensaje) {
        super(mensaje);
    }
}