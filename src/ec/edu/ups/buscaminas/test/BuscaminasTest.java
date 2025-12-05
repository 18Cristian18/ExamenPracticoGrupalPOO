package ec.edu.ups.buscaminas.test;

import ec.edu.ups.buscaminas.modelo.Casilla;
import ec.edu.ups.buscaminas.modelo.Juego;
import ec.edu.ups.buscaminas.modelo.Tablero;
import ec.edu.ups.buscaminas.excepciones.CasillaYaDescubiertaException;
import ec.edu.ups.buscaminas.excepciones.CoordenadaInvalidaException;

// Importaciones de JUnit 5
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

/**
 * Clase de Pruebas Unitarias usando JUnit 5.
 * Cumple con el criterio de "Desarrollo de Pruebas Unitarias y TDD".
 */
public class BuscaminasTest {

    private Tablero tablero;

    // Esta anotación hace que este método se ejecute ANTES de cada @Test
    // Garantiza que cada prueba empiece con un tablero limpio.
    @BeforeEach
    public void setUp() {
        tablero = new Tablero();
    }

    @Test
    @DisplayName("Debe haber exactamente 10 minas en el tablero")
    public void testCantidadDeMinas() {
        int contadorMinas = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero.getCasilla(i, j).esMina()) {
                    contadorMinas++;
                }
            }
        }
        // Aserción: Esperamos 10, si hay otro número, la prueba falla.
        assertEquals(10, contadorMinas, "El tablero debe inicializarse con 10 minas.");
    }

    @Test
    @DisplayName("Debe lanzar excepción si la coordenada está fuera de rango")
    public void testCoordenadaInvalida() {
        // assertThrows verifica que el código dentro de la lambda () -> lance la excepción esperada
        assertThrows(CoordenadaInvalidaException.class, () -> {
            tablero.descubrirCasilla(10, 0); // Fila 10 no existe (0-9)
        }, "Debería lanzar CoordenadaInvalidaException para índices fuera de limites");
        
        assertThrows(CoordenadaInvalidaException.class, () -> {
            tablero.descubrirCasilla(-1, 5); // Negativos no permitidos
        });
    }

    @Test
    @DisplayName("Debe lanzar excepción si se descubre una casilla ya descubierta")
    public void testCasillaYaDescubierta() throws Exception {
        // Buscamos una casilla segura para probar (para que no explote el juego)
        int f = 0, c = 0;
        // Bucle simple para encontrar una que no sea mina
        while(tablero.getCasilla(f, c).esMina()) {
            c++;
        }

        // 1. La descubrimos por primera vez
        tablero.descubrirCasilla(f, c);

        // 2. Intentamos descubrirla de nuevo -> Debe lanzar error
        int finalF = f; 
        int finalC = c;
        assertThrows(CasillaYaDescubiertaException.class, () -> {
            tablero.descubrirCasilla(finalF, finalC);
        }, "No se debe permitir descubrir una casilla dos veces.");
    }

    @Test
    @DisplayName("Una casilla con bandera NO debe poder ser descubierta")
    public void testProteccionBandera() throws CoordenadaInvalidaException, CasillaYaDescubiertaException {
        // Ponemos bandera en (0,0)
        tablero.alternarBandera(0, 0);
        
        // Verificamos que tenga bandera
        assertTrue(tablero.getCasilla(0, 0).isTieneBandera());

        // Intentamos descubrirla
        tablero.descubrirCasilla(0, 0);

        // Aserción: NO debe estar descubierta
        assertFalse(tablero.getCasilla(0, 0).isDescubierta(), 
                "La casilla con bandera no debió descubrirse.");
    }

    @Test
    @DisplayName("El juego debe guardar y cargar el estado correctamente")
    public void testPersistenciaJuego() throws IOException, ClassNotFoundException, CoordenadaInvalidaException {
        Juego juegoGuardar = new Juego();
        String archivoTest = "test_junit_save.dat";

        // Modificamos el estado: Ponemos bandera en (5,5)
        juegoGuardar.getTablero().alternarBandera(5, 5);

        // Guardamos
        juegoGuardar.guardarJuego(archivoTest);

        // Cargamos en un objeto nuevo
        Juego juegoCargar = new Juego();
        juegoCargar.cargarJuego(archivoTest);

        // Verificamos que la bandera siga ahí en el nuevo objeto
        boolean tieneBandera = juegoCargar.getTablero().getCasilla(5, 5).isTieneBandera();
        assertTrue(tieneBandera, "La bandera debió persistir después de cargar el juego.");

        // Limpieza: Borramos el archivo temporal
        new File(archivoTest).delete();
    }

    @Test
    @DisplayName("Debe detectar Victoria cuando todas las casillas seguras se abren")
    public void testVictoria() {
        // Truco para el test: Forzamos 'descubierta = true' en todas las que NO son minas
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Casilla casilla = tablero.getCasilla(i, j);
                if (!casilla.esMina()) {
                    casilla.setDescubierta(true);
                }
            }
        }

        // Aserción: El método verificarVictoria debe retornar true
        assertTrue(tablero.verificarVictoria(), "El juego debería estar ganado.");
    }

    @Test
    @DisplayName("Debe detectar Derrota al descubrir una mina")
    public void testDerrota() throws Exception {
        // Buscamos dónde hay una mina
        int fMina = -1, cMina = -1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero.getCasilla(i, j).esMina()) {
                    fMina = i;
                    cMina = j;
                    break;
                }
            }
            if (fMina != -1) break;
        }

        // Acción: Descubrir la mina
        boolean resultado = tablero.descubrirCasilla(fMina, cMina);

        // Aserción: El método debe retornar true (indicando explosión)
        assertTrue(resultado, "Descubrir una mina debe retornar true (Juego Terminado).");
    }
}