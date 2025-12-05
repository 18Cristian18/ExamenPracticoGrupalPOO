package ec.edu.ups.buscaminas.main;

import ec.edu.ups.buscaminas.controlador.ControladorJuego;
import ec.edu.ups.buscaminas.modelo.Juego;
import ec.edu.ups.buscaminas.vista.VistaConsola;

/**
 * Punto de entrada de la aplicación.
 * Se encarga de ensamblar las dependencias del patrón MVC.
 */
public class Main {
    public static void main(String[] args) {
        // Instanciación de componentes
        Juego modelo = new Juego();
        VistaConsola vista = new VistaConsola();
        
        // Inyección de dependencias en el controlador
        ControladorJuego controlador = new ControladorJuego(modelo, vista);
        
        // Inicio del flujo de la aplicación
        controlador.iniciarJuego();
    }
}