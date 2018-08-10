/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author Proyecto
 */
public class Hospitalizacion {
    private int numeroPaciente;
    private String codigoServicio;
    private int numeroHabitacion;
    private int cama;

    /**
     * @return the numeroPaciente
     */
    public int getNumeroPaciente() {
        return numeroPaciente;
    }

    /**
     * @param numeroPaciente the numeroPaciente to set
     */
    public void setNumeroPaciente(int numeroPaciente) {
        this.numeroPaciente = numeroPaciente;
    }

    /**
     * @return the codigoServicio
     */
    public String getCodigoServicio() {
        return codigoServicio;
    }

    /**
     * @param codigoServicio the codigoServicio to set
     */
    public void setCodigoServicio(String codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    /**
     * @return the numeroHabitacion
     */
    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    /**
     * @param numeroHabitacion the numeroHabitacion to set
     */
    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    /**
     * @return the cama
     */
    public int getCama() {
        return cama;
    }

    /**
     * @param cama the cama to set
     */
    public void setCama(int cama) {
        this.cama = cama;
    }
    
}
