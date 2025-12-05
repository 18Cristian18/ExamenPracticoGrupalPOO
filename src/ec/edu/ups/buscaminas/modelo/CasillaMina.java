package ec.edu.ups.buscaminas.modelo;

/**
 * Subclase concreta que define el comportamiento de una mina.
 */
public class CasillaMina extends Casilla {
    
    @Override
    public String obtenerSimbolo(boolean mostrarMinas) {
        // Si la casilla está descubierta o si el juego terminó (mostrarMinas es true),
        // mostramos el símbolo de la mina.
        if (descubierta || mostrarMinas) {
            return "*"; 
        }
        // Si el usuario puso una bandera, mostramos X.
        if (tieneBandera) return "X";
        
        // Si está cubierta y no es fin del juego, mostramos guion.
        return "-"; 
    }

    @Override
    public boolean esMina() {
        return true;
    }
}