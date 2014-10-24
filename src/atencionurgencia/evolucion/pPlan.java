
package atencionurgencia.evolucion;

import atencionurgencia.ingreso.pTratInterconsulta;
import atencionurgencia.ingreso.pTratMasProcedimientos;
import atencionurgencia.ingreso.pTratMedic;
import atencionurgencia.ingreso.pTratMedidaGeneral;
import atencionurgencia.ingreso.pTratOtrasInterconsultas;
import entidades.HcuEvolucion;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jpa.HcuEvolucionJpaController;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class pPlan extends javax.swing.JPanel {
    public pTratMedidaGeneral pMedidaGeneral=null;
    public pTratMedic pMedic=null;
    public pTratInterconsulta pInterconsulta0=null,pInterconsulta1=null,pInterconsulta2=null,pInterconsulta3=null,pInterconsulta4=null;
    private final EntityManagerFactory factory;
    private HcuEvolucion hcuEvolucion;
    private HcuEvolucionJpaController hejc = null;
    private pTratOtrasInterconsultas pOtrasInterconsultas=null;
    public pTratMasProcedimientos pProcedimientos;
    private evoDestino destino=null;
    

    /**
     * Creates new form pPlan
     * @param hcuEvolucion
     * @param factory
     */
    public pPlan(HcuEvolucion hcuEvolucion, EntityManagerFactory factory) {
        initComponents();
        this.hcuEvolucion = hcuEvolucion;
        this.factory = factory;
    }
    
    public HcuEvolucion saveChanged(EntityManagerFactory factory, HcuEvolucion hcuEvolucion){
        if(pMedidaGeneral!=null) pMedidaGeneral.saveChanges(factory, hcuEvolucion);
        if(pMedic!=null) pMedic.saveChanges(factory, hcuEvolucion);
        if(pInterconsulta0!=null) pInterconsulta0.saveChanges(factory, hcuEvolucion);
        if(pInterconsulta1!=null) pInterconsulta1.saveChanges(factory, hcuEvolucion);
        if(pInterconsulta2!=null) pInterconsulta2.saveChanges(factory, hcuEvolucion);
        if(pInterconsulta3!=null) pInterconsulta3.saveChanges(factory, hcuEvolucion);
        if(pInterconsulta4!=null) pInterconsulta4.saveChanges(factory, hcuEvolucion);
        if(pOtrasInterconsultas!=null) pOtrasInterconsultas.saveChanges(factory, hcuEvolucion);
        if(pProcedimientos!=null) pProcedimientos.saveChanges(factory, hcuEvolucion);
        if(destino!=null) destino.saveChanges(factory, hcuEvolucion);
        return hcuEvolucion;
    }
    
    public boolean estadoTablas(){
        return !(getValidPanels(pMedidaGeneral)==false && getValidPanels(pMedic)==false 
                && getValidPanels(pInterconsulta0)==false && getValidPanels(pInterconsulta1)==false
                && getValidPanels(pInterconsulta2)==false && getValidPanels(pInterconsulta3)==false
                && getValidPanels(pInterconsulta4)==false && getValidPanels(pOtrasInterconsultas)==false
                && getValidPanels(pProcedimientos)==false && getValidPanels(destino)==false);
    }
    
    private boolean getValidPanels(JPanel jp){
        if (jp != null){
            if(jp instanceof pTratMedidaGeneral){
                if(((pTratMedidaGeneral)jp).estadoTablas()==true){
                    return true;
                }
            }else if(jp instanceof pTratMedic){
                if(((pTratMedic)jp).estadoTablas()==true){
                    return true;
                }
            }else if(jp instanceof pTratInterconsulta){
                if(((pTratInterconsulta)jp).estadoTablas()==true){
                    return true;
                }
            }else if(jp instanceof pTratOtrasInterconsultas){
                if(((pTratOtrasInterconsultas)jp).estadoTablas()==true){
                    return true;
                }
            }else if(jp instanceof pTratMasProcedimientos){
                if(((pTratMasProcedimientos)jp).estadoTablas()==true){
                    return true;
                }
            }else if(jp instanceof evoDestino){
                if(((evoDestino)jp).estadoTablas()==true){
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel16 = new javax.swing.JPanel();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jLabel46 = new javax.swing.JLabel();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jTextField1 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jXTaskPane3 = new org.jdesktop.swingx.JXTaskPane();
        jLabel48 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jXTaskPane4 = new org.jdesktop.swingx.JXTaskPane();
        jLabel47 = new javax.swing.JLabel();
        jXTaskPane5 = new org.jdesktop.swingx.JXTaskPane();
        jLabel50 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(603, 386));
        setMinimumSize(new java.awt.Dimension(603, 386));
        setOpaque(false);

        jPanel16.setMaximumSize(new java.awt.Dimension(194, 386));
        jPanel16.setMinimumSize(new java.awt.Dimension(194, 386));
        jPanel16.setOpaque(false);
        jPanel16.setPreferredSize(new java.awt.Dimension(194, 386));

        jXTaskPane2.setExpanded(false);
        jXTaskPane2.setTitle("MEDICAMENTOS");
        jXTaskPane2.setAnimated(false);
        jXTaskPane2.setFocusable(false);
        jXTaskPane2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jXTaskPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXTaskPane2MouseReleased(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Pills.png"))); // NOI18N
        jLabel46.setText("Medicamentos");
        jLabel46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel46.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel46MouseMoved(evt);
            }
        });
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel46MouseExited(evt);
            }
        });
        jXTaskPane2.getContentPane().add(jLabel46);

        jXTaskPane1.setExpanded(false);
        jXTaskPane1.setTitle("PROCEDIMIENTOS");
        jXTaskPane1.setAnimated(false);
        jXTaskPane1.setFocusable(false);
        jXTaskPane1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jXTaskPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXTaskPane1MouseReleased(evt);
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jXTaskPane1.getContentPane().add(jTextField1);

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/microscope_icon.png"))); // NOI18N
        jLabel45.setText("<html><p>Laboratorio Clinico</p></html>");
        jLabel45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel45.setDoubleBuffered(true);
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel45MouseExited(evt);
            }
        });
        jLabel45.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel45MouseMoved(evt);
            }
        });
        jXTaskPane1.getContentPane().add(jLabel45);

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/XRay.png"))); // NOI18N
        jLabel49.setText("Imagenologia");
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel49.setDoubleBuffered(true);
        jLabel49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel49MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel49MouseExited(evt);
            }
        });
        jLabel49.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel49MouseMoved(evt);
            }
        });
        jXTaskPane1.getContentPane().add(jLabel49);

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Hospital.png"))); // NOI18N
        jLabel51.setText("Otros Procedimientos");
        jLabel51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel51.setDoubleBuffered(true);
        jLabel51.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel51MouseMoved(evt);
            }
        });
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel51MouseExited(evt);
            }
        });
        jXTaskPane1.getContentPane().add(jLabel51);

        jXTaskPane3.setExpanded(false);
        jXTaskPane3.setTitle("VALORACION ESPECIALISTA");
        jXTaskPane3.setAnimated(false);
        jXTaskPane3.setFocusable(false);
        jXTaskPane3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jXTaskPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXTaskPane3MouseReleased(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/surgeon_icon.png"))); // NOI18N
        jLabel48.setText("Cirugia General");
        jLabel48.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel48.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel48MouseMoved(evt);
            }
        });
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel48MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel48MouseExited(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel48);

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Female_icon.png"))); // NOI18N
        jLabel52.setText("Ginecología");
        jLabel52.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel52.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel52MouseMoved(evt);
            }
        });
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel52MouseExited(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel52);

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Head_physician_icon.png"))); // NOI18N
        jLabel53.setText("Medicina Interna");
        jLabel53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel53MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel53MouseExited(evt);
            }
        });
        jLabel53.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel53MouseMoved(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel53);

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plaster_icon.png"))); // NOI18N
        jLabel54.setText("Ortopedia");
        jLabel54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel54MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel54MouseExited(evt);
            }
        });
        jLabel54.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel54MouseMoved(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel54);

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stethoscope-icon.png"))); // NOI18N
        jLabel55.setText("Pediatria");
        jLabel55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel55MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel55MouseExited(evt);
            }
        });
        jLabel55.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel55MouseMoved(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel55);

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Hospital.png"))); // NOI18N
        jLabel56.setText("Otras Especialidades");
        jLabel56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel56MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel56MouseExited(evt);
            }
        });
        jLabel56.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel56MouseMoved(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel56);

        jXTaskPane4.setTitle("MEDIDAS GENERALES");
        jXTaskPane4.setAnimated(false);
        jXTaskPane4.setFocusable(false);
        jXTaskPane4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jXTaskPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXTaskPane4MouseReleased(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        jLabel47.setText("Medidas generales");
        jLabel47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel47.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel47MouseMoved(evt);
            }
        });
        jLabel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel47MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel47MouseExited(evt);
            }
        });
        jXTaskPane4.getContentPane().add(jLabel47);

        jXTaskPane5.setVisible(false);
        jXTaskPane5.setExpanded(false);
        jXTaskPane5.setTitle("DESTINO");
        jXTaskPane5.setAnimated(false);
        jXTaskPane5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXTaskPane5MouseReleased(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        jLabel50.setText("Destino y Recomendaciones");
        jLabel50.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel50.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel50MouseMoved(evt);
            }
        });
        jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel50MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel50MouseExited(evt);
            }
        });
        jXTaskPane5.getContentPane().add(jLabel50);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jXTaskPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jXTaskPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
            .addComponent(jXTaskPane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jXTaskPane5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jXTaskPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setMaximumSize(new java.awt.Dimension(403, 386));
        jPanel1.setMinimumSize(new java.awt.Dimension(403, 386));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(403, 386));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
        if(pMedic==null){
            pMedic = new pTratMedic();
            pMedic.showListExistentes(factory, hcuEvolucion);
        }
        jPanel1.removeAll();
        pMedic.setBounds(0,0,403,386);
        jPanel1.add(pMedic);
        pMedic.buttonSeven6.setVisible(true);
        pMedic.buttonSeven7.setVisible(true);
        pMedic.buttonSeven8.setVisible(true);
        pMedic.buttonSeven9.setVisible(true);
        pMedic.onEvolucion=true;
        pMedic.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
    }//GEN-LAST:event_jLabel46MouseClicked

    private void jLabel46MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseExited
        Funciones.setLabelInfo();
        jLabel46.setForeground(Color.black);
    }//GEN-LAST:event_jLabel46MouseExited

    private void jLabel46MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseMoved
        Funciones.setLabelInfo("LISTADO DE MEDICAMENTOS");
        jLabel46.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel46MouseMoved

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
        if(pProcedimientos==null){
            pProcedimientos = new pTratMasProcedimientos(true);
            pProcedimientos.showListExistentes(factory, hcuEvolucion);
        }
        jPanel1.removeAll();
        pProcedimientos.setBounds(0,0,403,386);
        jPanel1.add(pProcedimientos);
        pProcedimientos.buttonSeven6.setVisible(true);
        pProcedimientos.buttonSeven7.setVisible(true);
        pProcedimientos.jTextArea25.setEditable(true);
        pProcedimientos.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
        pProcedimientos.formularioOpen(17);
    }//GEN-LAST:event_jLabel45MouseClicked

    private void jLabel45MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseExited
        Funciones.setLabelInfo();
        jLabel45.setForeground(Color.black);
    }//GEN-LAST:event_jLabel45MouseExited

    private void jLabel45MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseMoved
        Funciones.setLabelInfo("LABORATORIO CLINICO");
        jLabel45.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel45MouseMoved

    private void jLabel49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseClicked
        if(pProcedimientos==null){
            pProcedimientos = new pTratMasProcedimientos(true);
            pProcedimientos.showListExistentes(factory, hcuEvolucion);
        }
        jPanel1.removeAll();
        pProcedimientos.setBounds(0,0,403,386);
        jPanel1.add(pProcedimientos);
        pProcedimientos.buttonSeven6.setVisible(true);
        pProcedimientos.buttonSeven7.setVisible(true);
        pProcedimientos.jTextArea25.setEditable(true);
        pProcedimientos.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
        pProcedimientos.formularioOpen(15);
    }//GEN-LAST:event_jLabel49MouseClicked

    private void jLabel49MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseExited
        Funciones.setLabelInfo();
        jLabel49.setForeground(Color.black);
    }//GEN-LAST:event_jLabel49MouseExited

    private void jLabel49MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseMoved
        Funciones.setLabelInfo("IMAGENOLOGIA");
        jLabel49.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel49MouseMoved

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        if(pProcedimientos==null){
            pProcedimientos = new pTratMasProcedimientos(true);
            pProcedimientos.showListExistentes(factory, hcuEvolucion);
        }
        jPanel1.removeAll();
        pProcedimientos.setBounds(0,0,403,386);
        jPanel1.add(pProcedimientos);
        pProcedimientos.buttonSeven6.setVisible(true);
        pProcedimientos.buttonSeven7.setVisible(true);
        pProcedimientos.jTextArea25.setEditable(true);
        pProcedimientos.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
        pProcedimientos.formularioOpen(0);
    }//GEN-LAST:event_jLabel51MouseClicked

    private void jLabel51MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseExited
        Funciones.setLabelInfo();
        jLabel51.setForeground(Color.black);
    }//GEN-LAST:event_jLabel51MouseExited

    private void jLabel51MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseMoved
        Funciones.setLabelInfo("TODOS LOS PROCEDIMIENTOS Y SERVICIOS");
        jLabel51.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel51MouseMoved

    private void jLabel48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseClicked
        if(pInterconsulta0==null){
            pInterconsulta0 = new pTratInterconsulta(0);//cirugia
            pInterconsulta0.showExistente(factory, hcuEvolucion);
        }
        jPanel1.removeAll();
        pInterconsulta0.setBounds(0,0,403,386);
        jPanel1.add(pInterconsulta0);
        pInterconsulta0.buttonSeven7.setVisible(true);
        pInterconsulta0.buttonSeven8.setVisible(true);
        pInterconsulta0.jTextArea25.setEditable(true);
        pInterconsulta0.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
    }//GEN-LAST:event_jLabel48MouseClicked

    private void jLabel48MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseExited
        Funciones.setLabelInfo();
        jLabel48.setForeground(Color.black);
    }//GEN-LAST:event_jLabel48MouseExited

    private void jLabel48MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseMoved
        Funciones.setLabelInfo("CIRUGIA GENERAL");
        jLabel48.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel48MouseMoved

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked
        if(pInterconsulta1==null){
            pInterconsulta1 = new pTratInterconsulta(1);//Ginecologia
            pInterconsulta1.showExistente(factory, hcuEvolucion);
        }
        jPanel1.removeAll();
        pInterconsulta1.setBounds(0,0,403,386);
        jPanel1.add(pInterconsulta1);
        pInterconsulta1.buttonSeven7.setVisible(true);
        pInterconsulta1.buttonSeven8.setVisible(true);
        pInterconsulta1.jTextArea25.setEditable(true);
        pInterconsulta1.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
    }//GEN-LAST:event_jLabel52MouseClicked

    private void jLabel52MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseExited
        Funciones.setLabelInfo();
        jLabel52.setForeground(Color.black);
    }//GEN-LAST:event_jLabel52MouseExited

    private void jLabel52MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseMoved
        Funciones.setLabelInfo("GINECOLOGIA");
        jLabel52.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel52MouseMoved

    private void jLabel53MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseClicked
        if(pInterconsulta2==null){
            pInterconsulta2 = new pTratInterconsulta(2);//Medicina Interna
            pInterconsulta2.showExistente(factory, hcuEvolucion);
        }
        jPanel1.removeAll();
        pInterconsulta2.setBounds(0,0,403,386);
        jPanel1.add(pInterconsulta2);
        pInterconsulta2.buttonSeven7.setVisible(true);
        pInterconsulta2.buttonSeven8.setVisible(true);
        pInterconsulta2.jTextArea25.setEditable(true);
        pInterconsulta2.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
    }//GEN-LAST:event_jLabel53MouseClicked

    private void jLabel53MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseExited
        Funciones.setLabelInfo();
        jLabel53.setForeground(Color.black);
    }//GEN-LAST:event_jLabel53MouseExited

    private void jLabel53MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseMoved
        Funciones.setLabelInfo("MEDICINA INTERNA");
        jLabel53.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel53MouseMoved

    private void jLabel54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseClicked
        if(pInterconsulta3==null){
            pInterconsulta3 = new pTratInterconsulta(3);//Ortopedia
            pInterconsulta3.showExistente(factory, hcuEvolucion);
        }
        jPanel1.removeAll();
        pInterconsulta3.setBounds(0,0,403,386);
        jPanel1.add(pInterconsulta3);
        pInterconsulta3.buttonSeven7.setVisible(true);
        pInterconsulta3.buttonSeven8.setVisible(true);
        pInterconsulta3.jTextArea25.setEditable(true);
        pInterconsulta3.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
    }//GEN-LAST:event_jLabel54MouseClicked

    private void jLabel54MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseExited
        Funciones.setLabelInfo();
        jLabel54.setForeground(Color.black);
    }//GEN-LAST:event_jLabel54MouseExited

    private void jLabel54MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseMoved
        Funciones.setLabelInfo("ORTOPEDIA");
        jLabel54.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel54MouseMoved

    private void jLabel55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseClicked
        if(pInterconsulta4==null){
            pInterconsulta4 = new pTratInterconsulta(4);//Pediatria
            pInterconsulta4.showExistente(factory, hcuEvolucion);
        }
        jPanel1.removeAll();
        pInterconsulta4.setBounds(0,0,403,386);
        jPanel1.add(pInterconsulta4);
        pInterconsulta4.buttonSeven7.setVisible(true);
        pInterconsulta4.buttonSeven8.setVisible(true);
        pInterconsulta4.jTextArea25.setEditable(true);
        pInterconsulta4.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
    }//GEN-LAST:event_jLabel55MouseClicked

    private void jLabel55MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseExited
        Funciones.setLabelInfo();
        jLabel55.setForeground(Color.black);
    }//GEN-LAST:event_jLabel55MouseExited

    private void jLabel55MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseMoved
        Funciones.setLabelInfo("PEDIATRIA");
        jLabel55.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel55MouseMoved

    private void jLabel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseClicked
        if(pOtrasInterconsultas==null){
            pOtrasInterconsultas = new pTratOtrasInterconsultas();
            pOtrasInterconsultas.showLista(factory, hcuEvolucion);
        }
        jPanel1.removeAll();
        pOtrasInterconsultas.setBounds(0,0,403,386);
        jPanel1.add(pOtrasInterconsultas);
        pOtrasInterconsultas.jTextArea25.setEditable(true);
        pOtrasInterconsultas.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
    }//GEN-LAST:event_jLabel56MouseClicked

    private void jLabel56MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseExited
        Funciones.setLabelInfo();
        jLabel56.setForeground(Color.black);
    }//GEN-LAST:event_jLabel56MouseExited

    private void jLabel56MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseMoved
        Funciones.setLabelInfo("OTRAS ESPECIALIDADES");
        jLabel56.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel56MouseMoved

    private void jLabel47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseClicked
        if(pMedidaGeneral==null){
            pMedidaGeneral = new pTratMedidaGeneral();
            pMedidaGeneral.showListExistentes(factory, hcuEvolucion);
        }
        jPanel1.removeAll();
        pMedidaGeneral.setBounds(0,0,403,386);//[403, 386]
        jPanel1.add(pMedidaGeneral);
        pMedidaGeneral.jButton1.setVisible(true);
        pMedidaGeneral.jButton2.setVisible(true);
        pMedidaGeneral.jTextArea26.setEditable(true);
        pMedidaGeneral.jTextArea27.setEditable(true);
        pMedidaGeneral.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
    }//GEN-LAST:event_jLabel47MouseClicked

    private void jLabel47MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseExited
        Funciones.setLabelInfo();
        jLabel47.setForeground(Color.black);
    }//GEN-LAST:event_jLabel47MouseExited

    private void jLabel47MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseMoved
        Funciones.setLabelInfo("MEDIDAS GENERALES");
        jLabel47.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel47MouseMoved

    private void jXTaskPane4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTaskPane4MouseReleased
        jXTaskPane1.setExpanded(false);
        jXTaskPane2.setExpanded(false);
        jXTaskPane3.setExpanded(false);
        jXTaskPane5.setExpanded(false);
    }//GEN-LAST:event_jXTaskPane4MouseReleased

    private void jXTaskPane2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTaskPane2MouseReleased
        jXTaskPane1.setExpanded(false);
        jXTaskPane4.setExpanded(false);
        jXTaskPane3.setExpanded(false);
        jXTaskPane5.setExpanded(false);
    }//GEN-LAST:event_jXTaskPane2MouseReleased

    private void jXTaskPane3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTaskPane3MouseReleased
        jXTaskPane1.setExpanded(false);
        jXTaskPane2.setExpanded(false);
        jXTaskPane4.setExpanded(false);
        jXTaskPane5.setExpanded(false);
    }//GEN-LAST:event_jXTaskPane3MouseReleased

    private void jXTaskPane1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTaskPane1MouseReleased
        jXTaskPane4.setExpanded(false);
        jXTaskPane2.setExpanded(false);
        jXTaskPane3.setExpanded(false);
        jXTaskPane5.setExpanded(false);
    }//GEN-LAST:event_jXTaskPane1MouseReleased

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            JTextField field = (JTextField) evt.getComponent();
            if(pProcedimientos==null){
                pProcedimientos = new pTratMasProcedimientos(true);
                pProcedimientos.showListExistentes(factory, hcuEvolucion);
            }
            if(pProcedimientos.findCUPS(field.getText(), factory)){
                jPanel1.removeAll();
                pProcedimientos.setBounds(0,0,403,386);
                jPanel1.add(pProcedimientos);
                pProcedimientos.buttonSeven6.setVisible(true);
                pProcedimientos.buttonSeven7.setVisible(true);
                pProcedimientos.jTextArea25.setEditable(true);
                pProcedimientos.setVisible(true);
                jPanel1.validate();
                jPanel1.repaint();
            }else{
                JOptionPane.showMessageDialog(null, "El Codigo ["+field.getText()+"] no es valido");
            }
        }
        
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jXTaskPane5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTaskPane5MouseReleased
        jXTaskPane4.setExpanded(false);
        jXTaskPane2.setExpanded(false);
        jXTaskPane3.setExpanded(false);
        jXTaskPane1.setExpanded(false);
    }//GEN-LAST:event_jXTaskPane5MouseReleased

    private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
        if(destino==null){
            destino = new evoDestino();
            destino.showExistente(hcuEvolucion);
        }        
        jPanel1.removeAll();
        destino.setBounds(0,0,403,386);//[403, 386]
        jPanel1.add(destino);
        jPanel1.setVisible(true);
        jPanel1.validate();
        jPanel1.repaint();
    }//GEN-LAST:event_jLabel50MouseClicked

    private void jLabel50MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseExited
        Funciones.setLabelInfo();
        jLabel50.setForeground(Color.black);
    }//GEN-LAST:event_jLabel50MouseExited

    private void jLabel50MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseMoved
        Funciones.setLabelInfo(jLabel50.getText().toUpperCase());
        jLabel47.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel50MouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JTextField jTextField1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    public org.jdesktop.swingx.JXTaskPane jXTaskPane3;
    public org.jdesktop.swingx.JXTaskPane jXTaskPane4;
    public org.jdesktop.swingx.JXTaskPane jXTaskPane5;
    // End of variables declaration//GEN-END:variables
}
