/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package atencionurgencia.evolucion;

import atencionurgencia.AtencionUrgencia;
import entidades.HcuEvoEgreso;
import entidades.HcuEvolucion;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import jpa.HcuEvoEgresoJpaController;

/**
 *
 * @author Administrador
 */
public class evoDestino extends javax.swing.JPanel {
    private HcuEvoEgresoJpaController jpaController=null;
    private List<HcuEvoEgreso> evoEgresos;
    

    /**
     * Creates new form evoDestino
     */
    public evoDestino() {
        initComponents();
    }
    
    public void saveChanges(EntityManagerFactory factory, HcuEvolucion evol){
        if(jpaController==null) jpaController = new HcuEvoEgresoJpaController(factory);
        evoEgresos=evol.getHcuEvoEgreso();
        if(evoEgresos.isEmpty()){
            HcuEvoEgreso egreso = new HcuEvoEgreso();
            egreso.setIdHcuEvolucion(evol);
            egreso.setDestino(jComboBox1.getSelectedIndex());
            egreso.setUsuario(AtencionUrgencia.idUsuario);
            egreso.setObservaciones(jTextArea26.getText().toUpperCase());
            egreso.setEstado(1);
            jpaController.create(egreso);    
            
        }else{
            try {
                HcuEvoEgreso egreso = evoEgresos.get(0);
                egreso.setDestino(jComboBox1.getSelectedIndex());
                egreso.setUsuario(AtencionUrgencia.idUsuario);
                egreso.setObservaciones(jTextArea26.getText().toUpperCase());
                egreso.setEstado(1);
                jpaController.edit(egreso);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10133:\n"+ex.getMessage(), evoDestino.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void showExistente(HcuEvolucion evol){
        evoEgresos=evol.getHcuEvoEgreso();
        if(evoEgresos!=null &&  !evoEgresos.isEmpty()){
            if(!evoEgresos.isEmpty()){
                HcuEvoEgreso egreso = evoEgresos.get(0);
                jComboBox1.setSelectedIndex(egreso.getDestino());
                jTextArea26.setText(egreso.getObservaciones());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel49 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTextArea26 = new javax.swing.JTextArea();

        setMaximumSize(new java.awt.Dimension(380, 420));
        setMinimumSize(new java.awt.Dimension(380, 420));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(380, 420));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        jLabel49.setText("DESTINO Y RECOMENDACIONES");

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder("Destino"));
        jPanel32.setOpaque(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DOMICILIO", "FUGA", "REMISION", "HOSPITALIZACION", "UCI", "UCE" }));
        jComboBox1.setSelectedIndex(-1);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder("Recomendaciones"));
        jPanel33.setOpaque(false);

        jScrollPane26.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane26.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane26.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea26.setColumns(20);
        jTextArea26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea26.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea26.setLineWrap(true);
        jTextArea26.setRows(2);
        jTextArea26.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea26.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextArea26KeyReleased(evt);
            }
        });
        jScrollPane26.setViewportView(jTextArea26);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextArea26KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea26KeyReleased
        
    }//GEN-LAST:event_jTextArea26KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JScrollPane jScrollPane26;
    public javax.swing.JTextArea jTextArea26;
    // End of variables declaration//GEN-END:variables
}
