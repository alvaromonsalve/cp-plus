
package other;

import atencionurgencia.AtencionUrgencia;
import entidades.ConfigCups;
import entidades.InfoHistoriac;
import entidades.InfoInterconsultaHcu;
import entidades.InfoPosologiaHcu;
import entidades.InfoProcedimientoHcu;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import jpa.ConfigCupsJpaController;
import jpa.InfoInterconsultaHcuJpaController;
import jpa.InfoPosologiaHcuJpaController;
import jpa.InfoProcedimientoHcuJpaController;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;
import oldConnection.Database;

/**
 *
 * @author Alvaro Monsalve
 */
public class impresionesHC extends javax.swing.JFrame {

    private InfoHistoriac idHC = null;
    private String destinoHc = null;
    private InfoPosologiaHcuJpaController infoPosologiaHcuJPA = null;
    private EntityManagerFactory factory;
    private InfoProcedimientoHcuJpaController infoProcedimientoHcuJPA = null;
    private InfoInterconsultaHcuJpaController infoInterconsultaHcuJPA = null;
    private ConfigCupsJpaController ccjc = null;
    private Boolean noValido;

    /**
     * Creates new form impresionesHC
     * @param factory
     */
    public impresionesHC(EntityManagerFactory factory) {
        initComponents();
        jLabel1.setVisible(false);
        this.setAlwaysOnTop(true);
        this.factory = factory;       
        enabledLink(false);
        this.pack();
    }

    public void setidHC(InfoHistoriac idHC) {
        this.idHC = idHC;
    }

    public void setdestinoHc(String destino) {
        this.destinoHc = destino;
    }

    public void setNoValido(boolean val) {
        this.noValido = val;
    }

    public String setValueValidoInt(boolean var) {
        if (var) {
            return "0";
        } else {
            return "1";
        }
    }
    
    private void enabledLink(boolean var){
        jLabel3.setEnabled(var);
        jLabel4.setEnabled(var);
        jLabel5.setEnabled(var);
        jLabel6.setEnabled(var);
        jLabel7.setEnabled(var);
    }
    
    public void activarLinks(){
        if (infoPosologiaHcuJPA == null) {
            infoPosologiaHcuJPA = new InfoPosologiaHcuJpaController(factory);
            infoProcedimientoHcuJPA = new InfoProcedimientoHcuJpaController(factory);
            ccjc = new ConfigCupsJpaController(factory);
            infoInterconsultaHcuJPA = new InfoInterconsultaHcuJpaController(factory);
        }
        List<InfoPosologiaHcu> listInfoPosologiaHcu = infoPosologiaHcuJPA.ListFindInfoPosologia(idHC);       
        if (listInfoPosologiaHcu.size() > 0) {
            jLabel3.setEnabled(true);
        }
        List<InfoProcedimientoHcu> listInfoProcedimientoHcuLabfilter = new ArrayList<InfoProcedimientoHcu>();
        List<InfoProcedimientoHcu> listInfoProcedimientoHcuRxfilter = new ArrayList<InfoProcedimientoHcu>();
        List<InfoProcedimientoHcu> listInfoProcedimientoHcuOtherfilter = new ArrayList<InfoProcedimientoHcu>();
        List<InfoProcedimientoHcu> listInfoProcedimientoHcu = this.ListFindInfoProcedimientoHcu(idHC);
        for (InfoProcedimientoHcu iph : listInfoProcedimientoHcu) {
            ConfigCups cc = ccjc.findConfigCups(iph.getIdCups());
            if (cc.getIdEstructuraCups().getId() == 17 || cc.getIdEstructuraCups().getId() == 18) {
                listInfoProcedimientoHcuLabfilter.add(iph);
            }else if(cc.getIdEstructuraCups().getId() == 15){
                listInfoProcedimientoHcuRxfilter.add(iph);
            }else{
                listInfoProcedimientoHcuOtherfilter.add(iph);
            }
        }
        if (listInfoProcedimientoHcuLabfilter != null & listInfoProcedimientoHcuLabfilter.size() > 0){
            jLabel4.setEnabled(true);
        }
        if (listInfoProcedimientoHcuRxfilter != null & listInfoProcedimientoHcuRxfilter.size() > 0){
            jLabel5.setEnabled(true);
        }
        if (listInfoProcedimientoHcuOtherfilter != null & listInfoProcedimientoHcuOtherfilter.size() > 0){
            jLabel6.setEnabled(true);
        }
        List<InfoInterconsultaHcu> listInfoInterconsultaHcu = infoInterconsultaHcuJPA.listInterconsultaHcu(idHC);
        if (listInfoInterconsultaHcu.size() > 0) {
            jLabel7.setEnabled(true);
        }
    }
    
    public List<InfoProcedimientoHcu> ListFindInfoProcedimientoHcu(InfoHistoriac ihc){
        EntityManager em = infoProcedimientoHcuJPA.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM InfoProcedimientoHcu i WHERE i.idHistoriac = :hc")
            .setParameter("hc", ihc.getId())
            .setHint("javax.persistence.cache.storeMode", "REFRESH")
            .getResultList();
        } finally {
            em.close();
        }
   }
    
    private class hiloReporte extends Thread{
        Frame form=null;
        JasperPrint informe;
        int n;
        
        public hiloReporte(Frame form,JasperPrint informe, int n){
            this.form =form;
            this.informe=informe;
            this.n=n;
        }
        
        @Override
        public void run(){
            ((impresionesHC)form).jLabel1.setVisible(true);
            if (n == 0) {
                JasperViewer.viewReport(informe, false);
            } else if(n == 1) {
                try {                    
                    JasperPrintManager.printReport(informe, true);
                } catch (JRException ex) {
                    JOptionPane.showMessageDialog(null, "10076:\n" + ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
            ((impresionesHC)form).jLabel1.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(344, 210));
        setMinimumSize(new java.awt.Dimension(344, 210));
        setPreferredSize(new java.awt.Dimension(344, 210));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print_32.png"))); // NOI18N
        jLabel49.setText("Impresion de Documentos");
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel49.setMaximumSize(new java.awt.Dimension(190, 32));
        jLabel49.setMinimumSize(new java.awt.Dimension(190, 32));
        jLabel49.setPreferredSize(new java.awt.Dimension(190, 32));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loader.gif"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Seleccione los documentos que desea digitalizar");

        jLabel3.setText("RECETA MEDICA");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel3MouseMoved(evt);
            }
        });
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel3MouseReleased(evt);
            }
        });

        jLabel4.setText("LABORATORIOS");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel4MouseMoved(evt);
            }
        });
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel4MouseReleased(evt);
            }
        });

        jLabel5.setText("IMAGENOLOGIA");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel5MouseMoved(evt);
            }
        });
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel5MouseReleased(evt);
            }
        });

        jLabel6.setText("OTROS PROCEDIMIENTOS");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel6MouseMoved(evt);
            }
        });
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel6MouseReleased(evt);
            }
        });

        jLabel7.setText("VALORACION ESPECIALISTA");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel7MouseMoved(evt);
            }
        });
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel7MouseReleased(evt);
            }
        });

        jLabel8.setText("HISTORIA CLINICA DE INGRESO");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel8MouseMoved(evt);
            }
        });
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel8MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        if (!noValido) {
//            AtencionUrgencia.panelindex.FramEnable(false);
//        }
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
//        if (!noValido) {
//            AtencionUrgencia.panelindex.FramEnable(true);
//        }
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void jLabel3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseReleased
        if(jLabel3.isEnabled()==true){
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master;
                //<![CDATA[call `rep_recetaMedica`($P{id_hc})]]> para el que genera el consecutivo hacerlo sin reportiar
                if ((idHC.getEstado() != 1 & noValido == true) | (idHC.getEstado() == 1 & noValido == false)) {
                    master = System.getProperty("user.dir") + "/reportes/resetaMedica.jasper";
                    param.put("entidadadmision", idHC.getIdInfoAdmision().getIdEntidadAdmision().getNombreEntidad());
                } else if ((idHC.getEstado() != 2 & noValido == true) | (idHC.getEstado() == 2 & noValido == false)) {
                    master = System.getProperty("user.dir") + "/reportes/resetaMedica.jasper";
                    param.put("entidadadmision", idHC.getIdInfoAdmision().getIdEntidadAdmision().getNombreEntidad());
                } else {
                    //reporte que no genera consecutivo
                    master = System.getProperty("user.dir") + "/reportes/solicitudmedicamentos.jasper";
                }
                if (master != null) {                    
                    db.Conectar();
                    param.put("id_hc", idHC.getId().toString());
                    if(noValido==true){
                        param.put("NombreReport", "PRESCRIPCION MEDICA (NO VALIDO)");
                    }else{
                        param.put("NombreReport", "PRESCRIPCION MEDICA");
                    }                    
                    param.put("version", "1.0");
                    param.put("codigo", "R-FA-003");
                    param.put("servicio", "URGENCIAS");
                    JasperPrint informe = JasperFillManager.fillReport(master, param, db.conexion);
                    Object[] objeto = {"Visualizar", "Imprimir"};
                    int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "PRESCRIPCION MEDICA", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                    hiloReporte ut = new hiloReporte(this,informe,n);
                    Thread thread = new Thread(ut);
                    thread.start();
                    db.DesconectarBasedeDatos();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10075:\n" + ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel3MouseReleased

    private void jLabel4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseReleased
        if (jLabel4.isEnabled() == true) {
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master;
                if ((idHC.getEstado() != 1 & noValido == true) | (idHC.getEstado() == 1 & noValido == false)) {
                    master = System.getProperty("user.dir") + "/reportes/solPorcedimientosLaboratorio.jasper";
                } else if ((idHC.getEstado() != 2 & noValido == true) | (idHC.getEstado() == 2 & noValido == false)) {
                    master = System.getProperty("user.dir") + "/reportes/solPorcedimientosLaboratorio.jasper";
                } else {
                    //reporte que no genera consecutivo
                    master = System.getProperty("user.dir") + "/reportes/solicitudprocedimientolabposthcu.jasper";
                }
                if (master != null) {                    
                    db.Conectar();
                    param.put("id_hc", idHC.getId().toString());
                    if(noValido==true){
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS DE LABORATORIO (NO VALIDO)");
                    }else{
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS DE LABORATORIO");
                    }                    
                    param.put("version", "1.0");
                    param.put("codigo", "R-FA-006");
                    param.put("servicio", "URGENCIAS");
                    param.put("novalido", setValueValidoInt(noValido));
                    JasperPrint informe = JasperFillManager.fillReport(master, param, db.conexion);
                    Object[] objeto = {"Visualizar", "Imprimir"};
                    int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "LABORATORIO", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                    hiloReporte ut = new hiloReporte(this,informe,n);
                    Thread thread = new Thread(ut);
                    thread.start();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10075:\n" + ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel4MouseReleased

    private void jLabel5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseReleased
        if(jLabel5.isEnabled()==true){
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master;
                if((idHC.getEstado()!=1 & noValido == true) | (idHC.getEstado()==1 & noValido == false)){
                    master = System.getProperty("user.dir") + "/reportes/solPorcedimientosLaboratorio.jasper";
                }else if((idHC.getEstado()!=2 & noValido == true) | (idHC.getEstado()==2 & noValido == false)){
                    master = System.getProperty("user.dir") + "/reportes/solPorcedimientosImagenologia.jasper";
                }else{
                    //reporte que no genera consecutivo
                     master = System.getProperty("user.dir") + "/reportes/solicitudprocedimientorxposthcu.jasper";
                }if (master != null) {                    
                    db.Conectar();
                    param.put("id_hc", idHC.getId().toString());
                    if(noValido==true){
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS DE IMAGENOLOGIA (NO VALIDO)");
                    }else{
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS DE IMAGENOLOGIA");
                    }
                    param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS DE IMAGENOLOGIA");
                    param.put("version", "1.0");
                    param.put("codigo", "R-FA-007");
                    param.put("servicio", "URGENCIAS");
                    param.put("novalido", setValueValidoInt(noValido));
                    JasperPrint informe = JasperFillManager.fillReport(master, param,db.conexion);
                    Object[] objeto ={"Visualizar","Imprimir"};
                    int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "IMAGENOLOGIA", JOptionPane.YES_NO_CANCEL_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                    hiloReporte ut = new hiloReporte(this,informe,n);
                    Thread thread = new Thread(ut);
                    thread.start();
                }                     
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10075:\n"+ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel5MouseReleased

    private void jLabel6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseReleased
        if(jLabel6.isEnabled()==true){
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master;
                if((idHC.getEstado()!=1 & noValido == true) | (idHC.getEstado()==1 & noValido == false)){
                    master = System.getProperty("user.dir") + "/reportes/solPorcedimientos.jasper";
                }else if((idHC.getEstado()!=2 & noValido == true) | (idHC.getEstado()==2 & noValido == false)){
                    master = System.getProperty("user.dir") + "/reportes/solPorcedimientos.jasper";
                }else{
                    //reporte que no genera consecutivo
                    master = System.getProperty("user.dir") + "/reportes/solicitudprocedimientos.jasper";
                }
                if (master != null) {                    
                    db.Conectar();
                    param.put("id_hc", idHC.getId().toString());
                    param.put("id_hc", idHC.getId());
                    if(noValido==true){
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS (NO VALIDO)");
                    }else{
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS");
                    }                    
                    param.put("version", "1.0");
                    param.put("codigo", "R-FA-005");
                    param.put("servicio", "URGENCIAS");
                    param.put("novalido", setValueValidoInt(noValido));
                    JasperPrint informe = JasperFillManager.fillReport(master, param,db.conexion);
                    Object[] objeto ={"Visualizar","Imprimir"};
                    int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "PROCEDIMIENTOS", JOptionPane.YES_NO_CANCEL_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                    hiloReporte ut = new hiloReporte(this,informe,n);
                    Thread thread = new Thread(ut);
                    thread.start();
                }                     
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10075:\n"+ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel6MouseReleased

    private void jLabel7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseReleased
        if(jLabel7.isEnabled()==true){
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master = System.getProperty("user.dir") + "/reportes/solValoracion.jasper";
                if (master != null) {                    
                    db.Conectar();
                    param.put("id_hc", idHC.getId());
                    param.put("entidadadmision", idHC.getIdInfoAdmision().getIdEntidadAdmision().getNombreEntidad());
                    if(noValido==true){
                        param.put("NombreReport", "SOLICITUD DE VALORACION POR ESPECIALISTA (NO VALIDO)");
                    }else{
                        param.put("NombreReport", "SOLICITUD DE VALORACION POR ESPECIALISTA");
                    }                    
                    param.put("version", "1.0");
                    param.put("codigo", "R-FA-004");
                    param.put("servicio", "URGENCIAS");
                    JasperPrint informe = JasperFillManager.fillReport(master, param,db.conexion);
                    Object[] objeto ={"Visualizar","Imprimir"};
                    int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "VALORACION", JOptionPane.YES_NO_CANCEL_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                    hiloReporte ut = new hiloReporte(this,informe,n);
                    Thread thread = new Thread(ut);
                    thread.start();
                }                     
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10075:\n"+ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel7MouseReleased

    private void jLabel8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseReleased
        if(jLabel8.isEnabled()==true){
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master = System.getProperty("user.dir") + "/reportes/HClinica.jasper";
                if (master != null) {                    
                    db.Conectar();
                    param.put("idHC", idHC.getId());
                    if(noValido==true){
                        param.put("NameReport", "HISTORIA CLINICA DE INGRESO (NO VALIDO)");
                    }else{
                        param.put("NameReport", "HISTORIA CLINICA DE INGRESO");
                    }                    
                    param.put("version", "1.0");
                    param.put("codigo", "R-FA-002");
                    param.put("servicio", "URGENCIAS");
                    JasperPrint informe = JasperFillManager.fillReport(master, param,db.conexion);
                    Object[] objeto ={"Visualizar","Imprimir"};
                    int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "NOTA DE INGRESO", JOptionPane.YES_NO_CANCEL_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                    hiloReporte ut = new hiloReporte(this,informe,n);
                    Thread thread = new Thread(ut);
                    thread.start();                    
                }                     
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10075:\n"+ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel8MouseReleased

    private void jLabel3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseMoved
        if(jLabel3.isEnabled()==true){
            jLabel3.setText("<html><a href=''>RECETA MEDICA</a></html>");
        }        
    }//GEN-LAST:event_jLabel3MouseMoved

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        jLabel3.setText("RECETA MEDICA");
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel4MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseMoved
        if(jLabel4.isEnabled()==true){
            jLabel4.setText("<html><a href=''>LABORATORIOS</a></html>");
        }  
    }//GEN-LAST:event_jLabel4MouseMoved

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        jLabel4.setText("LABORATORIOS");
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        jLabel5.setText("IMAGENOLOGIA");
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseMoved
        if(jLabel5.isEnabled()==true){
            jLabel5.setText("<html><a href=''>IMAGENOLOGIA</a></html>");
        } 
    }//GEN-LAST:event_jLabel5MouseMoved

    private void jLabel6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseMoved
        if(jLabel6.isEnabled()==true){
            jLabel6.setText("<html><a href=''>OTROS PROCEDIMIENTOS</a></html>");
        } 
    }//GEN-LAST:event_jLabel6MouseMoved

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        jLabel6.setText("OTROS PROCEDIMIENTOS");
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        jLabel7.setText("VALORACION ESPECIALISTA");
    }//GEN-LAST:event_jLabel7MouseExited

    private void jLabel7MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseMoved
        if(jLabel7.isEnabled()==true){
            jLabel7.setText("<html><a href=''>VALORACION ESPECIALISTA</a></html>");
        } 
    }//GEN-LAST:event_jLabel7MouseMoved

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        jLabel8.setText("HISTORIA CLINICA DE INGRESO");
    }//GEN-LAST:event_jLabel8MouseExited

    private void jLabel8MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseMoved
        if(jLabel8.isEnabled()==true){
            jLabel8.setText("<html><a href=''>HISTORIA CLINICA DE INGRESO</a></html>");
        } 
    }//GEN-LAST:event_jLabel8MouseMoved

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
