/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia;

import entidades.Configdecripcionlogin;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JPanel;
import jpa.ConfigdecripcionloginJpaController;


/**
 *
 * @author Alvaro Monsalve
 */
public class AtencionUrgencia {
    public static panelIndex panelindex;
    public static int idUsuario;//id config_descripcion_login
    public static Configdecripcionlogin configdecripcionlogin;
    public static Properties props;
    

    /**
     * 
     * @param idUsuario
     * @return panel principal de la clase setBounds(0, 0, 840, 540)
     */
    public static JPanel getPanelIndex(int idUsuario2, Properties props){
        EntityManagerFactory factory=Persistence.createEntityManagerFactory("ClipaEJBPU",props);
        ConfigdecripcionloginJpaController configdecripcionloginJpaController = new ConfigdecripcionloginJpaController(factory);
        configdecripcionlogin = configdecripcionloginJpaController.findConfigdecripcionlogin(idUsuario2);
        panelindex = new panelIndex();
        AtencionUrgencia.props =props;
        return panelindex;
    }
}
