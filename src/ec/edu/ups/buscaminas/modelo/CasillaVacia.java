package ec.edu.ups.buscaminas.modelo;

/**
 * Subclase concreta para casillas seguras.
 */
public class CasillaVacia extends Casilla {
    
    @Override
    public String obtenerSimbolo(boolean mostrarMinas) {
        if (tieneBandera) return "X";
        
        // Si no se ha descubierto, permanece oculta.
        if (!descubierta) return "-";
        
        // Lógica específica: Si no hay minas alrededor, retornamos 'O' (espacio libre),
        // de lo contrario retornamos el número de minas vecinas.
        if (minasAlrededor == 0) return "O";
        
        return String.valueOf(minasAlrededor);
    }

    @Override
    public boolean esMina() {
        return false;
    }
}