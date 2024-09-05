package Bussines.RTrees;

import Bussines.Trees.Habitant;

import java.awt.*;

public class Bardissa {
    private int tipus;
    private double mida;
    private double latitud;     // Vertical
    private double longitud;    // Horizontal
    private Color color;

    /**
     * Constructor de la classe Bardissa
     * @param tipus Tipus de bardissa
     * @param mida Mida de la bardissa
     * @param latitud Latitud de la bardissa
     * @param longitud Longitud de la bardissa
     * @param color Color de la bardissa
     */
    public Bardissa(int tipus, double mida, double latitud, double longitud, Color color) {
        this.tipus = tipus;
        this.mida = mida;
        this.latitud = latitud;
        this.longitud = longitud;
        this.color = color;
    }

    /**
     * Constructor de la classe Bardissa
     */
    public Bardissa(){
        this.tipus = -1;
        this.mida = -1;
        this.latitud = -1;
        this.longitud = -1;
        this.color = null;
    }

    /**
     * Metode que compara la latitud i longitud de la bardissa amb les que es passen per parametre
     * @param latitud Latitud a comparar
     * @param longitud Longitud a comparar
     * @return Retorna true si la latitud i longitud de la bardissa coincideixen amb les que es passen per parametre
     */
    public boolean compare(double latitud, double longitud) {
        return this.latitud == latitud && this.longitud == longitud;
    }

    /**
     * Metode que retorna la latitud de la bardissa
     * @return Retorna la latitud de la bardissa
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Metode que retorna la longitud de la bardissa
     * @return Retorna la longitud de la bardissa
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Metode que retorna el tipus de la bardissa
     * @return Retorna el tipus de la bardissa
     */
    public int getTipus() {
        return tipus;
    }

    /**
     * Metode que retorna la mida de la bardissa
     * @return Retorna la mida de la bardissa
     */
    public double getMida() {
        return mida;
    }

    /**
     * Metode que retorna el color de la bardissa
     * @return Retorna el color de la bardissa
     */
    public Color getColor() {
        return color;
    }

    /**
     * Metode que afegueix el tipus de la bardissa
     * @param tipus Tipus de la bardissa
     */
    public void setTipus(int tipus){
        this.tipus = tipus;
    }

    /**
     * Metode que afegueix la mida de la bardissa
     * @param mida Mida de la bardissa
     */
    public void setMida(double mida) {
        this.mida = mida;
    }

    /**
     * Metode que afegueix la latitud de la bardissa
     * @param latitud Latitud de la bardissa
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * Metode que afegueix la longitud de la bardissa
     * @param longitud Longitud de la bardissa
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * Metode que afegueix el color de la bardissa
     * @param color Color de la bardissa
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
