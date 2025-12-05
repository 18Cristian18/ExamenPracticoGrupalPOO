package ec.edu.ups.buscaminas.vista;

import ec.edu.ups.buscaminas.modelo.Tablero;
import java.util.Scanner;

/**
 * Componente VISTA del patrón MVC.
 * Su única responsabilidad es mostrar datos al usuario y capturar entradas.
 * No contiene lógica de negocio.
 */
public class VistaConsola {
    private Scanner scanner;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Dibuja el tablero en consola iterando sobre la matriz.
     * Utiliza el método polimórfico 'obtenerSimbolo' de las casillas.
     */
    public void mostrarTablero(Tablero tablero, boolean mostrarMinas) {
        System.out.println("\n************** JUEGO DE BUSCAMINAS **************");
        System.out.println("   1 2 3 4 5 6 7 8 9 10"); // Cabecera de columnas
        for (int i = 0; i < 10; i++) {
            char letra = (char) ('A' + i); // Generación dinámica de letras A-J
            System.out.print(letra + "  ");
            for (int j = 0; j < 10; j++) {
                // Delegamos a la casilla cómo debe dibujarse
                System.out.print(tablero.getCasilla(i, j).obtenerSimbolo(mostrarMinas) + " ");
            }
            System.out.println();
        }
        System.out.println("*************************************************");
    }

    public int mostrarMenu() {
        System.out.println("\n--- Menú de Acciones ---");
        System.out.println("1. Descubrir casilla");
        System.out.println("2. Poner/Quitar Bandera (Marca X)");
        System.out.println("3. Guardar partida actual");
        System.out.println("4. Cargar partida guardada");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Retorno de error controlado
        }
    }

    public String pedirCoordenada() {
        System.out.print("Ingrese coordenada (ej. A5): ");
        return scanner.nextLine().toUpperCase();
    }
}