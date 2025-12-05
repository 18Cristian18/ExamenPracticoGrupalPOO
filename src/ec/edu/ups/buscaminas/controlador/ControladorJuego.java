package ec.edu.ups.buscaminas.controlador;

import ec.edu.ups.buscaminas.excepciones.*;
import ec.edu.ups.buscaminas.modelo.Juego;
import ec.edu.ups.buscaminas.vista.VistaConsola;
import java.io.IOException;

/**
 * Componente CONTROLADOR del patrón MVC.
 * Actúa como intermediario entre el Modelo y la Vista.
 * Gestiona el flujo del programa y el manejo de excepciones.
 */
public class ControladorJuego {
    private Juego modelo;
    private VistaConsola vista;

    public ControladorJuego(Juego modelo, VistaConsola vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    /**
     * Ciclo principal del juego (Game Loop).
     */
    public void iniciarJuego() {
        boolean salir = false;
        
        while (!salir) {
            // Actualizamos la vista en cada iteración
            vista.mostrarTablero(modelo.getTablero(), modelo.isJuegoTerminado());
            
            if (modelo.isJuegoTerminado()) {
                vista.mostrarMensaje("¡Juego Terminado! Reinicia la aplicación para jugar de nuevo.");
                break;
            }

            int opcion = vista.mostrarMenu();

            switch (opcion) {
                case 1: // Descubrir
                    procesarAccion(true);
                    break;
                case 2: // Bandera
                    procesarAccion(false);
                    break;
                case 3: // Guardar
                    try {
                        modelo.guardarJuego("buscaminas.dat");
                        vista.mostrarMensaje("Juego guardado exitosamente.");
                    } catch (IOException e) {
                        vista.mostrarMensaje("Error de I/O al guardar: " + e.getMessage());
                    }
                    break;
                case 4: // Cargar
                    try {
                        modelo.cargarJuego("buscaminas.dat");
                        vista.mostrarMensaje("Juego cargado. El tablero se ha actualizado.");
                    } catch (Exception e) {
                        vista.mostrarMensaje("Error al cargar (puede que el archivo no exista): " + e.getMessage());
                    }
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida.");
            }

            // Verificación de estado de victoria después de cada movimiento
            if (!modelo.isJuegoTerminado() && modelo.getTablero().verificarVictoria()) {
                vista.mostrarTablero(modelo.getTablero(), true); // Mostramos todo al ganar
                vista.mostrarMensaje("\n*************************************************");
                vista.mostrarMensaje("    ¡FELICIDADES! HAS COMPLETADO EL CAMPO    ");
                vista.mostrarMensaje("*************************************************");
                modelo.setJuegoTerminado(true);
            }
        }
    }

    /**
     * Procesa la lógica de coordenadas y maneja las excepciones específicas.
     * @param esDescubrir true si la acción es revelar, false si es poner bandera.
     */
    private void procesarAccion(boolean esDescubrir) {
        String coord = vista.pedirCoordenada();
        try {
            int[] indices = convertirCoordenada(coord);
            int f = indices[0];
            int c = indices[1];

            if (esDescubrir) {
                boolean exploto = modelo.getTablero().descubrirCasilla(f, c);
                if (exploto) {
                    modelo.setJuegoTerminado(true);
                    vista.mostrarTablero(modelo.getTablero(), true); // Revelamos las minas
                    vista.mostrarMensaje("\n*************************************************");
                    vista.mostrarMensaje("       BOOM! HAS PISADO UNA MINA :(      ");
                    vista.mostrarMensaje("*************************************************");
                }
            } else {
                modelo.getTablero().alternarBandera(f, c);
            }

        } catch (CoordenadaInvalidaException | CasillaYaDescubiertaException e) {
            // Manejo de nuestras excepciones personalizadas
            vista.mostrarMensaje("Error de Lógica: " + e.getMessage());
        } catch (Exception e) {
            // Manejo genérico para errores de formato
            vista.mostrarMensaje("Entrada inválida. Formato esperado: LetraNumero (Ej: A5)");
        }
    }

    /**
     * Convierte la cadena "A5" a índices de matriz [0, 4].
     */
    private int[] convertirCoordenada(String coord) throws CoordenadaInvalidaException {
        if (coord == null || coord.length() < 2) 
            throw new CoordenadaInvalidaException("La coordenada es muy corta.");
        
        char letra = coord.charAt(0);
        String numStr = coord.substring(1);
        
        // Operación ASCII: 'A' - 'A' = 0, 'B' - 'A' = 1, etc.
        int fila = letra - 'A';
        
        int col;
        try {
            col = Integer.parseInt(numStr) - 1; // Restamos 1 porque el array empieza en 0
        } catch (NumberFormatException e) {
            throw new CoordenadaInvalidaException("El número de columna no es válido.");
        }

        return new int[]{fila, col};
    }
}