/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.math.BigDecimal;

/**
 *
 * @author Proyecto
 */
public class Enfermero {
    private int numero;
    private String codigoServicio;
    private String rotacion;
    private BigDecimal salario;

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
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
     * @return the rotacion
     */
    public String getRotacion() {
        return rotacion;
    }

    /**
     * @param rotacion the rotacion to set
     */
    public void setRotacion(String rotacion) {
        this.rotacion = rotacion;
    }

    /**
     * @return the salario
     */
    public BigDecimal getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
    
}
