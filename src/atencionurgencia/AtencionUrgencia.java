/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia;

import entidades.AccessConfigUser;
import entidades.AccessRoles;
import entidades.CmProfesionales;
import entidades.Configdecripcionlogin;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jpa.AccessConfigUserJpaController;
import jpa.CmProfesionalesJpaController;
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
    public static List<AccessRoles> roles;
    public static AccessConfigUser configUser=null;
    public static CmProfesionales cep=null;
    public static boolean isAdministrador=false;

    /**
     * 
     * @param idUsuario2
     * @param props
     * @return panel principal de la clase setBounds(0, 0, 840, 540)
     */
    public static JPanel getPanelIndex(int idUsuario2, Properties props){
        JPanel jPanel = new Panel("images/permiso.jpg");
        JLabel label = new JLabel("CODIGO DE PERMISO: 10000");
        label.setForeground(Color.white);
        label.setFont(new Font("Tahoma", Font.BOLD, 11));
        jPanel.add(label);
        EntityManagerFactory factory=Persistence.createEntityManagerFactory("ClipaEJBPU",props);
        ConfigdecripcionloginJpaController configdecripcionloginJpaController = new ConfigdecripcionloginJpaController(factory);
        configdecripcionlogin = configdecripcionloginJpaController.findConfigdecripcionlogin(idUsuario2);
        AccessConfigUserJpaController acujc = new AccessConfigUserJpaController(factory);
        CmProfesionalesJpaController cpjc = new CmProfesionalesJpaController(factory);
        cep = cpjc.pprofesional(configdecripcionlogin);
        /**
         * el usuario debe el tipo de cuenta de usuario ACU por el cual desea acceder
         */
        List<AccessConfigUser> ACU = acujc.FindConfigUsers(configdecripcionlogin);
        if(ACU.size()>1){
            DseleccionarACU aCU = new DseleccionarACU(null,true);
            aCU.setACU(ACU);
            aCU.setLocationRelativeTo(null);
            aCU.setVisible(true);       
            configUser = aCU.configUser;
        }else if(ACU.size()==1){
            configUser = ACU.get(0);
        }
        if(configUser!=null){
            boolean varEntrada=false;
            roles = configUser.getIdPerfiles().getAccessRolesList();
            for (AccessConfigUser ACU1 : ACU) {
                if(ACU1.getIdPerfiles().getNombre().equals("SYSTEM")){
                    varEntrada = true;
                    isAdministrador=true;
                    break;
                }
            }
            for(AccessRoles ar:roles){
                if(ar.getRuta()==10000){
                    varEntrada=true;
                    break;
                }
            }
            if(varEntrada==true){
                panelindex = new panelIndex(factory);
                if (configUser.getIdPerfiles().getId() == 3 || configUser.getIdPerfiles().getId() == 7) {
                    panelindex.jButton4.setVisible(true);//3 es el id del perfil de especialista de urgencia y 7 es el administrador
                }
                panelindex.recordatorio();
                AtencionUrgencia.props = props;
                jPanel = panelindex;
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
            imagen=new ImageIcon(ClassLoader.getSystemResource(nombre));
            g.drawImage(imagen.getImage(), 0, 0, tamanio.width,tamanio.height,null);
            setOpaque(false);
            super.paintComponent(g);
        }
    }
}
