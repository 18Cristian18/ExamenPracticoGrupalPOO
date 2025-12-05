package ec.edu.ups.buscaminas.modelo;

import java.io.Serializable;

/**
 * Clase Abstracta que representa la unidad base del tablero.
 * Implementa Serializable para permitir guardar el estado del objeto en un archivo binario.
 * Aplicamos ABSTRACCIÓN y HERENCIA aquí.
 */
public abstract class Casilla implements Serializable {
    // Atributos encapsulados (protected para acceso desde subclases)
    protected boolean descubierta;
    protected boolean tieneBandera;
    protected int minasAlrededor;

    public Casilla() {
        this.descubierta = false;
        this.tieneBandera = false;
        this.minasAlrededor = 0;
    }

    // Getters y Setters para mantener el ENCAPSULAMIENTO
    public boolean isDescubierta() { return descubierta; }
    public void setDescubierta(boolean descubierta) { this.descubierta = descubierta; }
    
    public boolean isTieneBandera() { return tieneBandera; }
    public void setTieneBandera(boolean tieneBandera) { this.tieneBandera = tieneBandera; }

    public int getMinasAlrededor() { return minasAlrededor; }
    public void setMinasAlrededor(int minas) { this.minasAlrededor = minas; }

    /**
     * Método abstracto para aplicar POLIMORFISMO.
     * Cada tipo de casilla (Mina o Vacía) decidirá cómo dibujarse en la consola.
     * @param mostrarMinas Indica si es el final del juego y se debe revelar todo.
     * @return El caracter a imprimir.
     */
    public abstract String obtenerSimbolo(boolean mostrarMinas);
    
    /**
     * Permite identificar el tipo de casilla sin usar 'instanceof', favoreciendo el polimorfismo.
     */
    public abstract boolean esMina();
}