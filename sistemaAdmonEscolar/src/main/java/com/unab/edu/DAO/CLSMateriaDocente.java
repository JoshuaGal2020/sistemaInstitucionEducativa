/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unab.edu.DAO;

import com.unab.edu.Conexion.ConexionBD;
import com.unab.edu.Entidades.Materias_Docentes;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author dayan
 */
public class CLSMateriaDocente {
    ConexionBD claseConectar = new ConexionBD();
        Connection conectar = claseConectar.RetornarConexion();
        
    public  ArrayList<Materias_Docentes> MostrarMateriaDocente(){
    ArrayList<Materias_Docentes> MateriaAlumno = new ArrayList<> ();
    
        try {
        CallableStatement Statement = conectar.prepareCall("call SP_S_MATERIAS_DOCENTES()");
        ResultSet resultadoConsulta = Statement.executeQuery();
        
        while(resultadoConsulta.next()){
              Materias_Docentes MatProf = new Materias_Docentes();
              
              MatProf.setIdMateriaDocente(resultadoConsulta.getInt("idMateriaDocente"));
              MatProf.setIdDocente(resultadoConsulta.getInt("idDocente"));
              MatProf.setIdMateria(resultadoConsulta.getInt("idMateria"));
              MatProf.setUltima_Modificacion(resultadoConsulta.getDate("UltimaModificacion"));
              MatProf.setEstado(resultadoConsulta.getInt("Estado"));
              
              MateriaAlumno.add(MatProf);
          }
            conectar.close();
        } catch (Exception e) {
        }
        return MateriaAlumno;
    } 
    
    public void BorrarMateriaDocente(Materias_Docentes matProf){
        
        try {
           CallableStatement Statement = conectar.prepareCall("call SP_D_MATERIAS_DOCENTES(?)");
           
           Statement.setInt("JIdMateriaDocente", matProf.getIdMateriaDocente());
           
           Statement.execute();
           JOptionPane.showMessageDialog(null, "Datos eliminados con éxito");
           
           conectar.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }
    
    public void ActualizarMateriaDocente(Materias_Docentes matProf){
        
        try {
            CallableStatement Statement = conectar.prepareCall("call SP_U_MATERIAS_DOCENTES(?,?,?,?,?)");
            
            Statement.setInt("DidMateriaAlumno", matProf.getIdMateriaDocente());
            Statement.setInt("DidDocente", matProf.getIdDocente());
            Statement.setInt("DidMateria", matProf.getIdMateria());
            Statement.setDate("DUltimaModificacion", new java.sql.Date(matProf.getUltima_Modificacion().getTime())); 
            Statement.setInt("DEstado", matProf.getEstado());
            
            Statement.execute();
            JOptionPane.showMessageDialog(null, "Datos actualizados con éxito");
            
            conectar.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void AgregarMateriaDocente(Materias_Docentes matProf){
    
        try {
            
            CallableStatement Statement = conectar.prepareCall("call SP_I_MATERIAS_DOCENTES(?,?)");
            
            Statement.setDate("DUltimaModificacion", new java.sql.Date(matProf.getUltima_Modificacion().getTime())); 
            Statement.setInt("DEstado", matProf.getEstado());
            
            Statement.execute();
            JOptionPane.showMessageDialog(null, "Datos agregados con éxito");
            
            conectar.close();
            
        } catch (Exception e) {
        }
    }
}
