package atencionurgencia.ListadoPacientes;

import java.awt.event.KeyEvent;
import atencionurgencia.*;
import atencionurgencia.ingreso.HC;
import entidades.HcuHistoriac2;
import entidades.InfoAdmision;
import entidades.InfoAntPersonales;
import entidades.InfoHcExpfisica;
import entidades.InfoHistoriac;
import entidades.InfoPaciente;
import entidades.PypInfoAntecedentesg;
import impr.urgencias.Iniciar;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import jpa.HcuHistoriac2JpaController;
import jpa.InfoAntPersonalesJpaController;
import jpa.InfoHcExpfisicaJpaController;
import jpa.PypInfoAntecedentesgJpaController;

/**
 *
 * @author Alvaro Monsalve
 */
public class Ftriaje extends javax.swing.JFrame {
    private final EntityManagerFactory factory;
    private PypInfoAntecedentesgJpaController piajc;
    private InfoAntPersonales antPersonales;
    private PypInfoAntecedentesg pia;
    private InfoHcExpfisica infoexploracion;
    private InfoHistoriac ih;
    private Boolean registroCreado=false;
    private HcuHistoriac2 hh;

    public Ftriaje(EntityManagerFactory factory) {
        initComponents();
        jLabel1.setVisible(false);   
        this.factory=factory;
    }
    
        @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("images/icono.png"));
        return retValue;
    }
    
    private int getNivelTriaje(){
        if(jRadioButton1.isSelected()) return 0;
        if(jRadioButton2.isSelected()) return 1;
        if(jRadioButton3.isSelected()) return 2;
        if(jRadioButton4.isSelected()) return 3;
        return 2;
    }
    
    private class hiloReporte extends Thread{
        Frame form=null;
        InfoHistoriac idHC;
        
        public hiloReporte(Frame form, InfoHistoriac idHC){
            this.form =form;
            this.idHC=idHC;
        }
        
        @Override
        public void run() {
            ((Ftriaje) form).jLabel1.setVisible(true);
            ((Ftriaje) form).jLabel2.setEnabled(false);
            Iniciar in = new Iniciar(AtencionUrgencia.props);
            in.imprimirTriaje(idHC.getId());
            AtencionUrgencia.panelindex.FramEnable(true);
            ((Ftriaje) form).jLabel1.setVisible(false);
            ((Ftriaje) form).jLabel2.setEnabled(true);
        }
    }
    
    private class hiloGenerarHc extends Thread{
        Frame form=null;
        InfoHistoriac idHC;
        
        public hiloGenerarHc(Frame form, InfoHistoriac idHC){
            this.form =form;
            this.idHC=idHC;
        }
        
        @Override
        public void run() {
            ((Ftriaje) form).jLabel1.setVisible(true);
            ((Ftriaje) form).jLabel2.setEnabled(false);
            AtencionUrgencia.panelindex.jpContainer.removeAll();
            AtencionUrgencia.panelindex.hc = new HC(factory);
            AtencionUrgencia.panelindex.hc.setBounds(0, 0, 764, 514);
            AtencionUrgencia.panelindex.jpContainer.add(AtencionUrgencia.panelindex.hc);
            AtencionUrgencia.panelindex.hc.setVisible(true);
            AtencionUrgencia.panelindex.jpContainer.validate();
            AtencionUrgencia.panelindex.jpContainer.repaint();
            AtencionUrgencia.panelindex.hc.jTextArea10.setText(jTextArea10.getText().toUpperCase());//observaciones
            AtencionUrgencia.panelindex.hc.setSelectionNivelTriage(getNivelTriaje());//nivel de triaje                   
            ((Ftriaje) form).CrearHistoriaC(AtencionUrgencia.panelindex.listaPacientes.ia);//creamos la historia clinica de ingreso
            AtencionUrgencia.panelindex.hc.viewHCinForm(ih,hh,pia);
            if (ih.getIdInfoAdmision() .getCausaExterna().equals("01")) {//ACCIDENTE DE TRABAJO
                AtencionUrgencia.panelindex.hc.jComboBox1.setSelectedItem("ACCIDENTE DE TRABAJO");
            } else if (ih.getIdInfoAdmision().getCausaExterna().equals("02")) {
                AtencionUrgencia.panelindex.hc.jComboBox1.setSelectedItem("ACCIDENTE DE TRANSITO");
            }
            ((Ftriaje) form).jLabel1.setVisible(false);
            ((Ftriaje) form).jLabel2.setEnabled(true);
            ((Ftriaje) form).dispose();
            AtencionUrgencia.panelindex.FramEnable(true);
        }
    }
    
    private void CrearHistoriaC(InfoAdmision ia){
        ih = new InfoHistoriac();
        hh = new HcuHistoriac2();
        DatosAntPersonales(ia.getIdDatosPersonales());
        ih.setIdInfoAdmision(ia);
        ih.setCausaExterna(jComboBox1.getSelectedItem().toString());
        ih.setMotivoConsulta(jTextArea10.getText().toUpperCase());
        ih.setNivelTriaje(this.getSelectionNivelTriage());
        ih.setAlergias(antPersonales.getAlergias());
        ih.setIngresosPrevios(antPersonales.getIngresosPrevios());
        ih.setTraumatismos(antPersonales.getTraumatismos());
        ih.setTratamientos(antPersonales.getTratamientos());
        ih.setSituacionBasal(antPersonales.getSituacionBasal());
        ih.setHta(antPersonales.getHta());
        ih.setDm(antPersonales.getDm());
        ih.setDislipidemia(antPersonales.getDislipidemia());
        ih.setTabaco(antPersonales.getTabaco());
        ih.setAlcohol(antPersonales.getAlcohol());
        ih.setDroga(antPersonales.getDroga());
        ih.setOtrosHabitos(antPersonales.getOtrosHabitos());
        ih.setDescHdd(antPersonales.getDescHdd());
        ih.setAntFamiliar(antPersonales.getAntFamiliares());
        ih.setEnfermedadActual("");
        ih.setDiagnostico(1);
        ih.setDiagnostico2(1);
        ih.setDiagnostico3(1);
        ih.setDiagnostico4(1);
        ih.setDiagnostico5(1);
        ih.setHallazgo("");
        ih.setDestino((String) jComboBox1.getSelectedItem());
        if(jComboBox1.getSelectedItem().equals("OBSERVACION")){
            ih.setTipoHc(0);
            ih.setEstado(0);
        }else if(jComboBox1.getSelectedItem().equals("CONSULTA EXTERNA (CITA PRIORITARIA)")){
             ih.setTipoHc(2);
             ih.setEstado(3);
        }else if(jComboBox1.getSelectedItem().equals("HOSPITALIZACION")){
            ih.setTipoHc(0);
            ih.setEstado(0);
        }else{
            ih.setTipoHc(2);
            ih.setEstado(2);
        }
        ih.setIdConfigdecripcionlogin(AtencionUrgencia.configdecripcionlogin);
        try {            
            AtencionUrgencia.panelindex.infoHistoriacJpaC.create(ih);
            hh.setIdInfoHistoriac(ih.getId());
            hh.setFIngreso(new Date());
            hh.setTiempoConsulta(0);
            hh.setFum(pia.getFum());
            hh.setNAbortos(pia.getAbortos());
            hh.setNCesarias(pia.getCesareas());
            hh.setNGestas(pia.getGestas());
            hh.setNPartos(pia.getPartos());
            hh.setObsAntGine(pia.getObservaciones());        
            AtencionUrgencia.panelindex.hcuHistoriac2JpaC = new HcuHistoriac2JpaController(factory);
            AtencionUrgencia.panelindex.hcuHistoriac2JpaC.create(hh);
            this.saveFisicExplorer(ih);
            ih.setInfoHcExpfisica(infoexploracion);
            registroCreado=true;
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10001:\n" + ex.getMessage(), Ftriaje.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void DatosAntPersonales(InfoPaciente ip) {
        AtencionUrgencia.panelindex.antPersonalesJPA = new InfoAntPersonalesJpaController(factory);
        piajc = new PypInfoAntecedentesgJpaController(factory);
        antPersonales = AtencionUrgencia.panelindex.antPersonalesJPA.findInfoAntPersonalesIDPac(ip);
        pia = findPiaIDPac(ip);
        if (antPersonales == null) {
            antPersonales = new InfoAntPersonales();
            antPersonales.setIdPaciente(ip.getId());
            antPersonales.setAlcohol(false);
            antPersonales.setAlergias("NINGUNO");
            antPersonales.setAntFamiliares("NINGUNO");
            antPersonales.setDescHdd("");
            antPersonales.setDislipidemia(false);
            antPersonales.setDm(false);
            antPersonales.setDroga(false);
            antPersonales.setHta(false);
            antPersonales.setIngresosPrevios("NINGUNO");
            antPersonales.setOtrosHabitos("NINGUNO");
            antPersonales.setSituacionBasal("NINGUNO");
            antPersonales.setTabaco(false);
            antPersonales.setTratamientos("NINGUNO");
            antPersonales.setTraumatismos("NINGUNO");
            AtencionUrgencia.panelindex.antPersonalesJPA.create(antPersonales);
        }
        if(pia==null){
            pia = new PypInfoAntecedentesg();
            pia.setInfoPaciente(ip);
            pia.setAbortos("0");
            pia.setCesareas("0");
            try{
            pia.setFum(new SimpleDateFormat("yyyy-MM-dd").parse("0001-01-01"));
            } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "10002:\n" + ex.getMessage(), Ftriaje.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }
            pia.setPartos("0");
            pia.setGestas("0");
            pia.setObservaciones("NINGUNO");  
            piajc.create(pia);
        }
    }
    
    private void saveFisicExplorer(InfoHistoriac ih) {
        infoexploracion = new InfoHcExpfisica();
        infoexploracion.setIdInfohistoriac(ih);
        infoexploracion.setTa("");
        infoexploracion.setT("");
        infoexploracion.setTam("");
        infoexploracion.setSao2("");
        infoexploracion.setFc("");
        infoexploracion.setPvc("");
        infoexploracion.setFr("");
        infoexploracion.setPic("");
        infoexploracion.setPeso("");
        infoexploracion.setTalla("");
        infoexploracion.setOtros("");
        infoexploracion.setAspectogeneral("");
        infoexploracion.setCara("NO SE ENCUENTRAN DATOS RELEVANTES");
        infoexploracion.setCardio("NO SE ENCUENTRAN DATOS RELEVANTES");
        infoexploracion.setRespiratorio("NO SE ENCUENTRAN DATOS RELEVANTES");
        infoexploracion.setGastro("NO SE ENCUENTRAN DATOS RELEVANTES");
        infoexploracion.setRenal("NO SE ENCUENTRAN DATOS RELEVANTES");
        infoexploracion.setHemato("NO SE ENCUENTRAN DATOS RELEVANTES");
        infoexploracion.setEndo("NO SE ENCUENTRAN DATOS RELEVANTES");
        infoexploracion.setOsteo("NO SE ENCUENTRAN DATOS RELEVANTES");
        AtencionUrgencia.panelindex.infohsfisicoJPA = new InfoHcExpfisicaJpaController(factory);
        AtencionUrgencia.panelindex.infohsfisicoJPA.create(infoexploracion);
    }
    
    public PypInfoAntecedentesg findPiaIDPac(InfoPaciente ip){
        EntityManager em = piajc.getEntityManager();
        try {
            List results = em.createQuery("SELECT p FROM PypInfoAntecedentesg p WHERE p.infoPaciente = :ip")
                    .setParameter("ip", ip)
                    .setHint("javax.persistence.cache.storeMode", "REFRESH")
                    .getResultList();
                    if (results.isEmpty()) return null;
                    else return (PypInfoAntecedentesg) results.get(0);
        }catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
    }
    
    private Integer getSelectionNivelTriage() {
        if (jRadioButton1.isSelected()) {
            return 0;
        } else if (jRadioButton2.isSelected()) {
            return 1;
        } else if (jRadioButton3.isSelected()) {
            return 2;
        } else {
            return 3;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea10 = new javax.swing.JTextArea();
        jPanel26 = new javax.swing.JPanel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setFocusable(false);
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(436, 323));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setText("OBSERVACIONES DE VALORACION (MOTIVO DE CONSULTA)");

        jScrollPane1.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea10.setColumns(20);
        jTextArea10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea10.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea10.setLineWrap(true);
        jTextArea10.setRows(5);
        jTextArea10.setDragEnabled(true);
        jTextArea10.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea10.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea10KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea10);

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nivel de Triaje", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel26.setOpaque(false);

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton4.setText("CUATRO");
        jRadioButton4.setOpaque(false);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton3.setSelected(true);
        jRadioButton3.setText("TRES");
        jRadioButton3.setOpaque(false);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton2.setText("DOS");
        jRadioButton2.setOpaque(false);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton1.setText("UNO");
        jRadioButton1.setOpaque(false);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton3)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "OBSERVACION", "CONSULTA EXTERNA (CITA PRIORITARIA)", "DOMICILIO", "HOSPITALIZACION" }));
        jComboBox1.setFocusable(false);

        jLabel44.setText("DESTINO:");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ajax-loader.gif"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Aceptar");
        jLabel2.setFocusable(false);
        jLabel2.setOpaque(true);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel2MouseReleased(evt);
            }
        });
        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel2MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(128, 128, 128))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

    private void jTextArea10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea10KeyTyped
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea10KeyTyped

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(registroCreado==false){
            Object[] objeto2 = {"Si", "No"};
            int n = JOptionPane.showOptionDialog(this, "Esta acción retornara el paciente a la lista de espera.\n¿Desea continuar?\n", "Cancelar triaje", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, objeto2, objeto2[1]);
            if (n == 0) {      
                try{
                AtencionUrgencia.panelindex.listaPacientes.ia.setEstado(1);
                AtencionUrgencia.panelindex.listaPacientes.admision.edit(AtencionUrgencia.panelindex.listaPacientes.ia);
                this.dispose();
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "10003:\n" + ex.getMessage(), Ftriaje.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Para generar nuevamente este documento diríjase al\nmódulo de archivo en el grupo de gestión administrativo", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
            AtencionUrgencia.panelindex.FramEnable(true);
            this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseReleased
        if (jLabel2.isEnabled()==true){
            //0 es observacion ---- 3 Hospitalizacion
            if (jComboBox1.getSelectedIndex() == 0 || jComboBox1.getSelectedIndex() == 3) {
                Object[] objeto = {"Si", "No"};
                int n = n = JOptionPane.showOptionDialog(this, "El paciente sera enviado a " + jComboBox1.getSelectedItem().toString() + "\n¿Desea seguir diligenciando la nota de ingreso?", "Mensaje", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                if (n == 0) {
                    hiloGenerarHc ut = new hiloGenerarHc(this, ih);
                    Thread thread = new Thread(ut);
                    thread.start();
                    
                }
                //1 consulta externa --- 2 Domicilio
            } else if (jComboBox1.getSelectedIndex() == 1 || jComboBox1.getSelectedIndex() == 2) {
                Object[] objeto = {"Si", "No"};
                int n = 0;
                if (jComboBox1.isEnabled() == true) {
                    n = JOptionPane.showOptionDialog(this, "Se generará la nota de triaje y no podrá realizar la nota de ingreso\n¿Desea continuar?", "Mensaje", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                }
                if (n == 0) {
                    jComboBox1.setEnabled(false);
                    jRadioButton1.setEnabled(false);
                    jRadioButton2.setEnabled(false);
                    jRadioButton3.setEnabled(false);
                    jRadioButton4.setEnabled(false);
                    jTextArea10.setEditable(false);
                    if(registroCreado==false) this.CrearHistoriaC(AtencionUrgencia.panelindex.listaPacientes.ia);
                    hiloReporte ut = new hiloReporte(this, ih);
                    Thread thread = new Thread(ut);
                    thread.start();
                }
            }
        }
    }//GEN-LAST:event_jLabel2MouseReleased

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        jLabel2.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseMoved
        jLabel2.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel2MouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea10;
    // End of variables declaration//GEN-END:variables
}
