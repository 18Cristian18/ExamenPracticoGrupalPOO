package ec.edu.ups.buscaminas.modelo;

import java.io.*;

/**
 * Clase contenedora del estado del juego.
 * Encargada de la persistencia de datos (Guardar/Cargar).
 */
public class Juego {
    private Tablero tablero;
    private boolean juegoTerminado;

    public Juego() {
        this.tablero = new Tablero();
        this.juegoTerminado = false;
    }

    public Tablero getTablero() { return tablero; }
    public boolean isJuegoTerminado() { return juegoTerminado; }
    public void setJuegoTerminado(boolean terminado) { this.juegoTerminado = terminado; }

    /**
     * Persistencia: Serialización de Objetos.
     * Guarda el objeto 'Tablero' completo (y sus casillas) en un archivo binario.
     */
    public void guardarJuego(String archivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(tablero);
        }
    }

    /**
     * Persistencia: Deserialización.
     * Recupera el estado exacto del tablero desde el archivo.
     */
    public void cargarJuego(String archivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            this.tablero = (Tablero) in.readObject();
        }
    }
}