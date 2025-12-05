package ec.edu.ups.buscaminas.excepciones;

/**
 * Excepción personalizada para el manejo de entradas.
 * Se utiliza cuando el usuario ingresa coordenadas fuera del rango del tablero (ej: Z99)
 * o con un formato incorrecto.
 */
public class CoordenadaInvalidaException extends Exception {
    public CoordenadaInvalidaException(String mensaje) {
        super(mensaje);
    }
}