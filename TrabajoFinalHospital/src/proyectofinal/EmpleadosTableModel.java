/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import Entidades.Empleado;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Proyecto
 */
public class EmpleadosTableModel implements TableModel{
    String[] columnas = { "Número",
                          "Nombre",
                          "Apellido",
                          "Dirección",
                          "Teléfono"};
    ArrayList<Empleado> empleados;
    public EmpleadosTableModel(String servicio, String especialidad, String paciente, String habitacion){
        try {
            empleados = new Conexion().getDoctores(servicio,especialidad,paciente,habitacion);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadosTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public EmpleadosTableModel(){
        try {
            empleados = new Conexion().getEmpleados();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmpleadosTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public EmpleadosTableModel(String tipoEmpleado, int numero, String nombre){
        
        if("Enfermero".equals(tipoEmpleado) && numero == 0 && "".equals(nombre)){
            try {
              empleados = new Conexion().getEnfermeros();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EmpleadosTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if("Doctor".equals(tipoEmpleado) && numero == 0 && "".equals(nombre)){
             try {
              empleados = new Conexion().getDoctor();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EmpleadosTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if("Enfermero".equals(tipoEmpleado) && numero != 0){
            try {
              empleados = new Conexion().getEnfermerosPorNumero(numero);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EmpleadosTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if("Enfermero".equals(tipoEmpleado) && !"".equals(nombre)){
            try {
              empleados = new Conexion().getEnfermerosPorNombre(nombre);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EmpleadosTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        else if("Doctor".equals(tipoEmpleado) && numero != 0){
            try {
              empleados = new Conexion().getDoctoresPorNumero(numero);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EmpleadosTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if("Doctor".equals(tipoEmpleado) && !"".equals(nombre)){
            try {
              empleados = new Conexion().getDoctoresPorNombre(nombre);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EmpleadosTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         else if(numero != 0){
            try {
              empleados = new Conexion().getEmpleadosPorNumero(numero);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EmpleadosTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(!"".equals(nombre)){
            try {
              empleados = new Conexion().getEmpleadosPorNombre(nombre);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EmpleadosTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     
    }
    @Override
    public int getRowCount() {
        return empleados.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnas[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String val;
        switch (columnIndex) {
            case 0: val = "" + empleados.get(rowIndex).getNumero(); break;
            case 1: val = empleados.get(rowIndex).getNombre(); break;
            case 2: val = empleados.get(rowIndex).getApellido(); break;
            case 3: val = empleados.get(rowIndex).getDireccion(); break;
            case 4: val = empleados.get(rowIndex).getTelefono(); break;
            default: val = "";
        }
        return val;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
    }
    
}
