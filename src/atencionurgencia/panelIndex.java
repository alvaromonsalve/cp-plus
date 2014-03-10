/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia;

import static atencionurgencia.AtencionUrgencia.roles;
import atencionurgencia.ListadoPacientes.enAtencion;
import atencionurgencia.ListadoPacientes.fListPacientes;
import atencionurgencia.ListadoPacientes.fListinterconsulta;
import atencionurgencia.ListadoPacientes.fPacientesCamas;
import atencionurgencia.documentos.jDocumentos;
import atencionurgencia.evolucion.Evo;
import atencionurgencia.ingreso.HC;
import entidades.AccessRoles;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class panelIndex extends javax.swing.JPanel {
    private enAtencion enatencion=null;
    public HC hc;
    public Evo evo;
    public jDocumentos documentos;    
    
//    private PanelHilo panelHilo=null;
    
    
    /**
     * Creates new form panelIndex
     */
    public panelIndex() {
        initComponents();
    }
    
    /**
     * habilita o desabilita el formulario principal
     * @param var indica el estado para habilitar o desabilitar 
     */
    public void FramEnable(boolean var){
        JFrame casa = (JFrame) SwingUtilities.getWindowAncestor(this);
        casa.setEnabled(var);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jlInfo = new javax.swing.JLabel();
        jpContainer = new org.edisoncor.gui.panel.PanelImage();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(840, 540));
        setMinimumSize(new java.awt.Dimension(840, 540));

        panelImage1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fondoButton.png"))); // NOI18N
        panelImage1.setMaximumSize(new java.awt.Dimension(70, 514));
        panelImage1.setMinimumSize(new java.awt.Dimension(70, 514));
        panelImage1.setPreferredSize(new java.awt.Dimension(70, 514));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/FirstAidKit.png"))); // NOI18N
        jButton1.setToolTipText("PACIENTES A ESPERA DE ATENCIÓN");
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setDoubleBuffered(true);
        jButton1.setFocusable(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
        });
        jButton1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton1MouseMoved(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/History.png"))); // NOI18N
        jButton2.setToolTipText("NOTA DE INGRESO SIN FINALIZAR");
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.setDoubleBuffered(true);
        jButton2.setFocusable(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton2MouseExited(evt);
            }
        });
        jButton2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton2MouseMoved(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LastThreeMonths.png"))); // NOI18N
        jButton3.setToolTipText("EVOLUCIONES");
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3.setDoubleBuffered(true);
        jButton3.setFocusable(false);
        jButton3.setMaximumSize(new java.awt.Dimension(46, 46));
        jButton3.setMinimumSize(new java.awt.Dimension(46, 46));
        jButton3.setPreferredSize(new java.awt.Dimension(46, 46));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton3MouseExited(evt);
            }
        });
        jButton3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton3MouseMoved(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Prescription.png"))); // NOI18N
        jButton4.setToolTipText("INTERCONSULTA Y VALORACIONES");
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton4.setDoubleBuffered(true);
        jButton4.setFocusable(false);
        jButton4.setMaximumSize(new java.awt.Dimension(46, 46));
        jButton4.setMinimumSize(new java.awt.Dimension(46, 46));
        jButton4.setPreferredSize(new java.awt.Dimension(46, 46));
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4MouseExited(evt);
            }
        });
        jButton4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton4MouseMoved(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/DefinedParameters.png"))); // NOI18N
        jButton5.setToolTipText("DOCUMENTOS");
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton5.setDoubleBuffered(true);
        jButton5.setFocusable(false);
        jButton5.setMaximumSize(new java.awt.Dimension(46, 46));
        jButton5.setMinimumSize(new java.awt.Dimension(46, 46));
        jButton5.setPreferredSize(new java.awt.Dimension(46, 46));
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton5MouseExited(evt);
            }
        });
        jButton5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton5MouseMoved(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(840, 20));
        jToolBar1.setMinimumSize(new java.awt.Dimension(840, 20));
        jToolBar1.setOpaque(false);
        jToolBar1.setPreferredSize(new java.awt.Dimension(840, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/UserSetup_16x16.png"))); // NOI18N
        jToolBar1.add(jLabel1);
        jToolBar1.add(jSeparator1);

        jlInfo.setText("...");
        jToolBar1.add(jlInfo);

        jpContainer.setBackground(new java.awt.Color(255, 255, 255));
        jpContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpContainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fondoIndexHC.png"))); // NOI18N
        jpContainer.setMaximumSize(new java.awt.Dimension(764, 514));
        jpContainer.setMinimumSize(new java.awt.Dimension(764, 514));
        jpContainer.setPreferredSize(new java.awt.Dimension(764, 514));
        jpContainer.setLayout(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(panelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelImage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseMoved
        jlInfo.setText(jButton1.getToolTipText());
        jButton1.setContentAreaFilled(true);
    }//GEN-LAST:event_jButton1MouseMoved

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        jlInfo.setText("");
        jButton1.setContentAreaFilled(false);
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jButton1.setEnabled(false);
        fListPacientes listaPacientes = null;
        listaPacientes = new fListPacientes();
        listaPacientes.setVisible(true);
        jButton1.setContentAreaFilled(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseExited
        jlInfo.setText("");
        jButton2.setContentAreaFilled(false);
    }//GEN-LAST:event_jButton2MouseExited

    private void jButton2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseMoved
        jlInfo.setText(jButton2.getToolTipText());
        jButton2.setContentAreaFilled(true);
    }//GEN-LAST:event_jButton2MouseMoved

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(enatencion==null){
            enatencion = new enAtencion();
        }
        enatencion.setVisible(true);
        enatencion.inicio();
        jButton2.setContentAreaFilled(false);
//        if(panelHilo==null){
//            panelHilo = new PanelHilo();
//        }
//        panelHilo.setBounds(0, 0, 764, 540);
//        jpContainer.add(panelHilo);
//        panelHilo.setVisible(true);
////        card.show(jpContainer, "PANELHILO");
//        jpContainer.repaint();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseExited
        Funciones.setLabelInfo();
        jButton3.setContentAreaFilled(false);
    }//GEN-LAST:event_jButton3MouseExited

    private void jButton3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseMoved
        jlInfo.setText(jButton3.getToolTipText());
        jButton3.setContentAreaFilled(true);
    }//GEN-LAST:event_jButton3MouseMoved

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jButton3.setEnabled(false);
        fPacientesCamas pacientesCamas = null;
        pacientesCamas = new fPacientesCamas();
        pacientesCamas.setVisible(true);
        pacientesCamas.inicio();
        jButton3.setContentAreaFilled(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseExited
        Funciones.setLabelInfo();
        jButton4.setContentAreaFilled(false);
    }//GEN-LAST:event_jButton4MouseExited

    private void jButton4MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseMoved
        jlInfo.setText(jButton4.getToolTipText());
        jButton4.setContentAreaFilled(true);
    }//GEN-LAST:event_jButton4MouseMoved

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jButton4.setEnabled(false);
        boolean accede = false;
        for(AccessRoles ar:roles){
            if(ar.getRuta()==10001){
                fListinterconsulta listPacientes =null;
                listPacientes = new fListinterconsulta();
                listPacientes.setVisible(true);
                jButton4.setContentAreaFilled(false);
                accede =true;
                break;
            }
        }
        if(!accede){
            jpContainer.removeAll();
            JLabel label = new JLabel("CODIGO DE PERMISO: 10001");
            label.setForeground(Color.white);
            label.setFont(new Font("Tahoma", Font.BOLD, 11));
            label.setVisible(true);
            jpContainer.add(label);
            jpContainer.setIcon(new javax.swing.ImageIcon(ClassLoader.getSystemResource("images/permiso2.png")));
            jpContainer.repaint();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseExited
        Funciones.setLabelInfo();
        jButton5.setContentAreaFilled(false);
    }//GEN-LAST:event_jButton5MouseExited

    private void jButton5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseMoved
        jlInfo.setText(jButton5.getToolTipText());
        jButton5.setContentAreaFilled(true);
    }//GEN-LAST:event_jButton5MouseMoved

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.jpContainer.removeAll();
        this.documentos = new jDocumentos();
        this.documentos.setBounds(0, 0, 764, 514);
        this.jpContainer.add(this.documentos);
        this.documentos.setVisible(true);
        this.jpContainer.validate();
        this.jpContainer.repaint();
    }//GEN-LAST:event_jButton5ActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    public javax.swing.JLabel jlInfo;
    public org.edisoncor.gui.panel.PanelImage jpContainer;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    // End of variables declaration//GEN-END:variables
}
