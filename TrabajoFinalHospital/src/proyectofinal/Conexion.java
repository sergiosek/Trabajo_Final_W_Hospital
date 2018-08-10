/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import Entidades.Empleado;
import Entidades.Paciente;
import Entidades.Servicio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Proyecto
 */
public class Conexion {
    Connection conn;
    Statement stmt;
    public Conexion() throws ClassNotFoundException, SQLException{
        String urlDatabase = "jdbc:mysql://localhost/hopital";
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(urlDatabase, "root", "");
        stmt = conn.createStatement();
    }
    public void ejecutarQuery(String query) throws SQLException{
        stmt.execute(query);
    }
    public ArrayList<String> getEspecialidades() throws SQLException{
        ArrayList<String> especialidades = new ArrayList<String>();
        String query = "select distinct specialite FROM docteur"; 
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            especialidades.add(rs.getString("specialite"));
        }
        return especialidades;
    }
    public ArrayList<Servicio> getServicios() throws SQLException{
        ArrayList<Servicio> servicios = new ArrayList<Servicio>();
        String query = "select code, nom, batiment, directeur from service"; 
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            Servicio serv = new Servicio();
            serv.setCodigo(rs.getString("code"));
            serv.setNombre(rs.getString("nom"));
            serv.setEdificio(rs.getString("batiment"));
            serv.setDirector(rs.getInt("directeur"));
            servicios.add(serv);
        }
        return servicios;
    }

    public ArrayList<Empleado> getDoctores(String servicio, String especialidad, String paciente, String habitacion) throws SQLException{
        String se="";
        String es="";
        if(!servicio.equals("Seleccione una opción")){
            se=servicio;
        }
        if(!especialidad.equals("Seleccione una opción")){
            es=especialidad;
        }
        ArrayList<Empleado> empleados = new ArrayList<Empleado>();
        String query = "select distinct e.* from docteur d, employe e, hospitalisation h, soigne so, service se "
                + "where e.numero=d.numero "
                //para la especialidad
                + ((es.equals(""))?"":"and d.specialite='"+es+"' ")
                //para el servicio
                + ((se.equals(""))?"":("and h.code_service='" + se + "' and (d.numero=so.no_docteur) and (so.no_malade=h.no_malade) "))
                //para el paciente
                + ((paciente.equals(""))?"":"and (so.no_malade=" + paciente + " and d.numero=so.no_docteur) ")
                //para la habitacion
                + ((habitacion.equals(""))?"":"and (h.no_chambre=" + habitacion + " and h.no_malade=so.no_malade and d.numero=so.no_docteur) ");

        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            Empleado empleado = new Empleado();
            empleado.setNumero(rs.getInt("numero"));
            empleado.setNombre(rs.getString("prenom"));
            empleado.setApellido(rs.getString("nom"));
            empleado.setTelefono(rs.getString("tel"));
            empleado.setDireccion(rs.getString("adresse"));
            empleados.add(empleado);
        }
        return empleados;
    } 
    ArrayList<Paciente> getPacientes() throws SQLException{
        String query = "select * from malade";
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<Paciente> pacientes = new ArrayList<>();
        while(rs.next()){
            Paciente paciente = new Paciente();
            paciente.setNumero(rs.getInt("numero")); 
            paciente.setNombre(rs.getString("prenom")); 
            paciente.setApellido(rs.getString("nom")); 
            paciente.setDireccion(rs.getString("adresse"));
            paciente.setTelefono(rs.getString("tel"));
            paciente.setMutua(rs.getString("mutuelle"));
            pacientes.add(paciente);
        }
        
        return pacientes;
    }
    
    ArrayList<Paciente> getPacientesFiltrados(String numero, String nombre, String apellido, String telefono, String direccion, String mutua) throws SQLException{
        String query = "";
        if(numero.equals("") && nombre.equals("") && apellido.equals("") && telefono.equals("") && direccion.equals("") && mutua.equals("")){
            query = "select * from malade";
        }
        else{
            query = "select * from malade where "
                + ((numero.equals(""))?"":"concat(numero,'') like '"+numero+"%' ")
                //Numero
                + ((numero.equals("")&& !nombre.equals(""))?"":((!numero.equals("")&& !nombre.equals(""))?"and ":
                    (numero.equals("")&& !apellido.equals(""))?"":((!numero.equals("")&& !apellido.equals(""))?"and ":
                    (numero.equals("")&& !telefono.equals(""))?"":((!numero.equals("")&& !telefono.equals(""))?"and ":
                    (numero.equals("")&& !direccion.equals(""))?"":((!numero.equals("")&& !direccion.equals(""))?"and ":
                    (numero.equals("")&& !mutua.equals(""))?"":((!numero.equals("")&& !mutua.equals(""))?"and ":" "))))))
                + ((nombre.equals(""))?"":"prenom like '"+nombre+"%' ")
                
                //Nombre 
                +(((nombre.equals("")&& !apellido.equals(""))?"":((!nombre.equals("")&& !apellido.equals(""))?"and ":
                    (nombre.equals("")&& !telefono.equals(""))?"":((!nombre.equals("")&& !telefono.equals(""))?"and ":
                    (nombre.equals("")&& !direccion.equals(""))?"":((!nombre.equals("")&& !direccion.equals(""))?"and ":
                    (nombre.equals("")&& !mutua.equals(""))?"":((!nombre.equals("")&& !mutua.equals(""))?"and ":" "))))))
                + ((apellido.equals(""))?"":"nom like '"+apellido+"%' ")    
                    
                //Apellido
                +((((apellido.equals("")&& !telefono.equals(""))?"":((!apellido.equals("")&& !telefono.equals(""))?"and ":
                    (apellido.equals("")&& !direccion.equals(""))?"":((!apellido.equals("")&& !direccion.equals(""))?"and ":
                    (apellido.equals("")&& !mutua.equals(""))?"":((!apellido.equals("")&& !mutua.equals(""))?"and ":" "))))))
                + ((telefono.equals(""))?"":"tel like '"+telefono+"%' ")    
                    
                //Telefono 
                + (((((telefono.equals("")&& !direccion.equals(""))?"":((!telefono.equals("")&& !direccion.equals(""))?"and ":
                    (telefono.equals("")&& !mutua.equals(""))?"":((!telefono.equals("")&& !mutua.equals(""))?"and ":" "))))))
                + ((direccion.equals(""))?"":"adresse like '"+direccion+"%' ")   
                    
                //Dirección 
                    +((((((direccion.equals("")&& !mutua.equals(""))?"":((!direccion.equals("")&& !mutua.equals(""))?"and ":" "))))))
                + ((mutua.equals(""))?"":"mutuelle like '"+mutua+"%' ")
                ;
        }
        ResultSet rs = stmt.executeQuery(query);
        ArrayList<Paciente> pacientes = new ArrayList<>();
        while(rs.next()){
            Paciente paciente = new Paciente();
            paciente.setNumero(rs.getInt("numero")); 
            paciente.setNombre(rs.getString("prenom")); 
            paciente.setApellido(rs.getString("nom")); 
            paciente.setDireccion(rs.getString("adresse"));
            paciente.setTelefono(rs.getString("tel"));
            paciente.setMutua(rs.getString("mutuelle"));
            pacientes.add(paciente);
        }
        
        return pacientes;
    }
    
    ArrayList<Empleado> getEmpleados() throws SQLException{
        String query = "select distinct * from employe";
        ResultSet rs = stmt.executeQuery(query);
        
        ArrayList<Empleado> en = new ArrayList<>();
                
        while(rs.next()){
            Empleado empleado = new Empleado();
            empleado.setNumero(rs.getInt("numero"));
            empleado.setNombre(rs.getString("prenom"));
            empleado.setApellido(rs.getString("nom"));
            empleado.setDireccion(rs.getString("adresse"));
            empleado.setTelefono(rs.getString("tel"));           
            en.add(empleado);
        }
        
        return en;
    }
    ArrayList<Empleado> getEnfermeros() throws SQLException{
        String query = "select distinct e.* from infirmier f, employe e where e.numero=f.numero";
        ResultSet rs = stmt.executeQuery(query);
        
        ArrayList<Empleado> en = new ArrayList<>();
                
        while(rs.next()){
            Empleado empleado = new Empleado();
            empleado.setNumero(rs.getInt("numero"));
            empleado.setNombre(rs.getString("prenom"));
            empleado.setApellido(rs.getString("nom"));
            empleado.setDireccion(rs.getString("adresse"));
            empleado.setTelefono(rs.getString("tel"));           
            en.add(empleado);
        }
        
        return en;
    }
    ArrayList<Empleado> getDoctor() throws SQLException{
        String query = "select distinct e.* from docteur d, employe e where e.numero=d.numero";
        ResultSet rs = stmt.executeQuery(query);
        
        ArrayList<Empleado> en = new ArrayList<>();
                
        while(rs.next()){
            Empleado empleado = new Empleado();
            empleado.setNumero(rs.getInt("numero"));
            empleado.setNombre(rs.getString("prenom"));
            empleado.setApellido(rs.getString("nom"));
            empleado.setDireccion(rs.getString("adresse"));
            empleado.setTelefono(rs.getString("tel"));           
            en.add(empleado);
        }
        
        return en;
    }

    ArrayList<Empleado> getEnfermerosPorNumero(int numero) throws SQLException{
     String query = "select e.* from infirmier f, employe e where e.numero=f.numero and f.numero="+numero+"";
     ResultSet rs = stmt.executeQuery(query);

     ArrayList<Empleado> en = new ArrayList<>();

     while(rs.next()){
         Empleado empleado = new Empleado();
         empleado.setNumero(rs.getInt("numero"));
         empleado.setNombre(rs.getString("prenom"));
         empleado.setApellido(rs.getString("nom"));
         empleado.setDireccion(rs.getString("adresse"));
         empleado.setTelefono(rs.getString("tel"));           
         en.add(empleado);
     }

     return en;
    }
    
    ArrayList<Empleado> getEnfermerosPorNombre(String nombre) throws SQLException{
        String query = "select e.* from infirmier f, employe e where e.numero=f.numero and e.prenom like '"+nombre+"%'";
        ResultSet rs = stmt.executeQuery(query);
        
        ArrayList<Empleado> en = new ArrayList<>();
                
        while(rs.next()){
            Empleado empleado = new Empleado();
            empleado.setNumero(rs.getInt("numero"));
            empleado.setNombre(rs.getString("prenom"));
            empleado.setApellido(rs.getString("nom"));
            empleado.setDireccion(rs.getString("adresse"));
            empleado.setTelefono(rs.getString("tel"));           
            en.add(empleado);
        }
        
        return en;
    }
    
    ArrayList<Empleado> getDoctoresPorNumero(int numero) throws SQLException{
        String query = "select e.* from docteur f, employe e where e.numero=f.numero and f.numero="+numero+"";
        ResultSet rs = stmt.executeQuery(query);
        
        ArrayList<Empleado> en = new ArrayList<>();
                
        while(rs.next()){
            Empleado empleado = new Empleado();
            empleado.setNumero(rs.getInt("numero"));
            empleado.setNombre(rs.getString("prenom"));
            empleado.setApellido(rs.getString("nom"));
            empleado.setDireccion(rs.getString("adresse"));
            empleado.setTelefono(rs.getString("tel"));           
            en.add(empleado);
        }
        
        return en;
    }
    
    ArrayList<Empleado> getDoctoresPorNombre(String nombre) throws SQLException{
        String query = "select e.* from docteur f, employe e where e.numero=f.numero and e.prenom like '"+nombre+"%'";
        ResultSet rs = stmt.executeQuery(query);
        
        ArrayList<Empleado> en = new ArrayList<>();
                
        while(rs.next()){
            Empleado empleado = new Empleado();
            empleado.setNumero(rs.getInt("numero"));
            empleado.setNombre(rs.getString("prenom"));
            empleado.setApellido(rs.getString("nom"));
            empleado.setDireccion(rs.getString("adresse"));
            empleado.setTelefono(rs.getString("tel"));           
            en.add(empleado);
        }
        
        return en;
    }
    
    ArrayList<Empleado> getEmpleadosPorNumero(int numero) throws SQLException{
        String query = "select e.* from employe e where e.numero="+numero+"";
        ResultSet rs = stmt.executeQuery(query);
        
        ArrayList<Empleado> en = new ArrayList<>();
                
        while(rs.next()){
            Empleado empleado = new Empleado();
            empleado.setNumero(rs.getInt("numero"));
            empleado.setNombre(rs.getString("prenom"));
            empleado.setApellido(rs.getString("nom"));
            empleado.setDireccion(rs.getString("adresse"));
            empleado.setTelefono(rs.getString("tel"));           
            en.add(empleado);
        }
        
        return en;
    }
    
    ArrayList<Empleado> getEmpleadosPorNombre(String nombre) throws SQLException{
        String query = "select e.* from employe e where e.prenom like '"+nombre+"%'";
        ResultSet rs = stmt.executeQuery(query);
        
        ArrayList<Empleado> en = new ArrayList<>();
                
        while(rs.next()){
            Empleado empleado = new Empleado();
            empleado.setNumero(rs.getInt("numero"));
            empleado.setNombre(rs.getString("prenom"));
            empleado.setApellido(rs.getString("nom"));
            empleado.setDireccion(rs.getString("adresse"));
            empleado.setTelefono(rs.getString("tel"));           
            en.add(empleado);
        }
        
        return en;
    }
}
