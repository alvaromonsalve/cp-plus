/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia;

import entidades.AccessConfigUser;
import entidades.AccessRoles;
import entidades.Configdecripcionlogin;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import jpa.AccessConfigUserJpaController;
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
     * @param idUsuario2
     * @param props
     * @return panel principal de la clase setBounds(0, 0, 840, 540)
     */
    public static JPanel getPanelIndex(int idUsuario2, Properties props){
        JPanel jPanel = new Panel("/images/permiso.png");
        EntityManagerFactory factory=Persistence.createEntityManagerFactory("ClipaEJBPU",props);
        ConfigdecripcionloginJpaController configdecripcionloginJpaController = new ConfigdecripcionloginJpaController(factory);
        configdecripcionlogin = configdecripcionloginJpaController.findConfigdecripcionlogin(idUsuario2);
        AccessConfigUserJpaController acujc = new AccessConfigUserJpaController(factory);
        List<AccessConfigUser> ACU = acujc.FindConfigUsers(configdecripcionlogin);
        for(AccessConfigUser accessConfigUser:ACU){
            List<AccessRoles> roles = accessConfigUser.getIdPerfiles().getAccessRolesList();
            for(AccessRoles ar:roles){
                if(ar.getRuta()==10000){
                    panelindex = new panelIndex();
                    AtencionUrgencia.props =props;       
                    jPanel = panelindex;
                }
            }
        }
        return jPanel;
    }
    
    private static class Panel extends JPanel{
        ImageIcon imagen;
        String nombre;
        
        public Panel(String nombre){
            this.nombre = nombre;
        }
        
        @Override
        public void paint(Graphics g){
            Dimension tamanio = getSize();
            imagen=new ImageIcon(getClass().getResource(nombre));
            g.drawImage(imagen.getImage(), 0, 0, tamanio.width,tamanio.height,null);
            setOpaque(false);
            super.paint(g);
        }
        
    }
}
