package ec.edu.ups.buscaminas.modelo;

import ec.edu.ups.buscaminas.excepciones.CasillaYaDescubiertaException;
import ec.edu.ups.buscaminas.excepciones.CoordenadaInvalidaException;
import java.io.Serializable;
import java.util.Random;

/**
 * Clase que gestiona la lógica de la matriz del juego.
 * Contiene la estructura de datos principal (matriz de Casillas).
 */
public class Tablero implements Serializable {
    private Casilla[][] casillas;
    private final int FILAS = 10;
    private final int COLUMNAS = 10;
    private final int TOTAL_MINAS = 10;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        inicializarTablero();
    }

    /**
     * Configuración inicial: Llena el tablero de casillas vacías, luego inserta minas
     * y finalmente calcula los números de proximidad.
     */
    private void inicializarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                // Polimorfismo: Inicialmente todas son vacías
                casillas[i][j] = new CasillaVacia();
            }
        }
        colocarMinas();
        calcularNumeros();
    }

    /**
     * Coloca las minas aleatoriamente garantizando que sean exactamente 10.
     */
    private void colocarMinas() {
        Random random = new Random();
        int minasColocadas = 0;
        while (minasColocadas < TOTAL_MINAS) {
            int f = random.nextInt(FILAS);
            int c = random.nextInt(COLUMNAS);
            
            // Solo colocamos si no hay una mina previa en esa posición
            if (!casillas[f][c].esMina()) {
                // Reemplazamos la casilla vacía por una de tipo mina
                casillas[f][c] = new CasillaMina();
                minasColocadas++;
            }
        }
    }

    /**
     * Recorre el tablero para calcular cuántas minas rodean a cada casilla vacía.
     */
    private void calcularNumeros() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (!casillas[i][j].esMina()) {
                    int minas = contarMinasAdyacentes(i, j);
                    casillas[i][j].setMinasAlrededor(minas);
                }
            }
        }
    }

    /**
     * Revisa las 8 casillas circundantes buscando minas.
     */
    private int contarMinasAdyacentes(int fila, int col) {
        int contador = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int f = fila + i;
                int c = col + j;
                // Validamos límites para evitar ArrayIndexOutOfBoundsException
                if (dentroDeLimites(f, c) && casillas[f][c].esMina()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    private boolean dentroDeLimites(int f, int c) {
        return f >= 0 && f < FILAS && c >= 0 && c < COLUMNAS;
    }

    /**
     * Lógica principal de turno.
     * @return true si el jugador descubrió una mina (Juego Perdido).
     */
    public boolean descubrirCasilla(int f, int c) throws CasillaYaDescubiertaException, CoordenadaInvalidaException {
        if (!dentroDeLimites(f, c)) throw new CoordenadaInvalidaException("Coordenada fuera de rango.");
        
        Casilla casilla = casillas[f][c];

        if (casilla.isDescubierta()) throw new CasillaYaDescubiertaException("Esta casilla ya fue descubierta.");
        
        // Protección: Si tiene bandera, no permitimos descubrirla por accidente
        if (casilla.isTieneBandera()) return false; 

        casilla.setDescubierta(true);

        // Si es mina, retornamos true para indicar Game Over
        if (casilla.esMina()) return true; 

        // Algoritmo de expansión recursiva (Flood Fill):
        // Si es un 0 (sin minas cerca), abrimos automáticamente las vecinas.
        if (casilla.getMinasAlrededor() == 0) {
            abrirAdyacentes(f, c);
        }
        return false;
    }

    /**
     * Recursividad para abrir áreas vacías automáticamente.
     */
    private void abrirAdyacentes(int fila, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int f = fila + i;
                int c = col + j;
                if (dentroDeLimites(f, c) && !casillas[f][c].isDescubierta() && !casillas[f][c].esMina()) {
                    casillas[f][c].setDescubierta(true);
                    // Si la vecina también es 0, seguimos expandiendo (llamada recursiva)
                    if (casillas[f][c].getMinasAlrededor() == 0) {
                        abrirAdyacentes(f, c); 
                    }
                }
            }
        }
    }

    public void alternarBandera(int f, int c) throws CoordenadaInvalidaException {
        if (!dentroDeLimites(f, c)) throw new CoordenadaInvalidaException("Coordenada fuera de rango.");
        // Solo permitimos poner bandera si está oculta
        if (!casillas[f][c].isDescubierta()) {
            casillas[f][c].setTieneBandera(!casillas[f][c].isTieneBandera());
        }
    }

    /**
     * Condición de victoria: Ganamos si todas las casillas NO minadas están descubiertas.
     */
    public boolean verificarVictoria() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (!casillas[i][j].esMina() && !casillas[i][j].isDescubierta()) {
                    return false; // Aún faltan casillas seguras por abrir
                }
            }
        }
        return true;
    }

    public Casilla getCasilla(int f, int c) {
        return casillas[f][c];
    }
}