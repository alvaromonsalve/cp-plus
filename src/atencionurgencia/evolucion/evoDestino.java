
package atencionurgencia.evolucion;

import atencionurgencia.AtencionUrgencia;
import entidades.HcuEvoEgreso;
import entidades.HcuEvolucion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import jpa.HcuEvoEgresoJpaController;

/**
 *
 * @author Alvaro Monsalve
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
            if(jComboBox1.getSelectedIndex()!=-1) egreso.setDestino(jComboBox1.getSelectedIndex());
            egreso.setUsuario(AtencionUrgencia.idUsuario);
            egreso.setObservaciones(jTextArea26.getText().toUpperCase());
            if(jCheckBox1.isSelected()==true){
                egreso.setIncapacidad(1);
                egreso.setIncapacidadInit(jDateChooser1.getDate());
                egreso.setIncapacidadEnd(jDateChooser2.getDate());
            }else{
                egreso.setIncapacidad(0);
            }
            egreso.setEstado(1);
            jpaController.create(egreso);    
            List<HcuEvoEgreso> evodestino = new ArrayList<HcuEvoEgreso>();
            evodestino.add(egreso);
            evol.setHcuEvoEgreso(evodestino);
        }else{
            try {
                HcuEvoEgreso egreso = evoEgresos.get(0);
                egreso.setDestino(jComboBox1.getSelectedIndex());
                egreso.setUsuario(AtencionUrgencia.idUsuario);
                egreso.setObservaciones(jTextArea26.getText().toUpperCase());
                if(jCheckBox1.isSelected()==true){
                    egreso.setIncapacidad(1);
                    egreso.setIncapacidadInit(jDateChooser1.getDate());
                    egreso.setIncapacidadEnd(jDateChooser2.getDate());
                }else{
                    egreso.setIncapacidad(0);
                }
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
                if(egreso.getIncapacidad()==1){
                    jCheckBox1.setSelected(true);
                    jDateChooser1.setDate(egreso.getIncapacidadInit());
                    jDateChooser2.setDate(egreso.getIncapacidadEnd());
                }
                jComboBox1.setSelectedIndex(egreso.getDestino());
                jTextArea26.setText(egreso.getObservaciones());
            }
        }
    }
    
    public boolean estadoTablas(){
        return jComboBox1.getSelectedIndex()>-1;
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
        jPanel1 = new javax.swing.JPanel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();

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
        jScrollPane26.setViewportView(jTextArea26);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Incapacidad"));
        jPanel1.setOpaque(false);

        jDateChooser1.setEnabled(false);

        jLabel1.setText("Desde:");

        jDateChooser2.setEnabled(false);

        jLabel2.setText("Hasta:");

        jCheckBox1.setText("Generar Incapacidad");
        jCheckBox1.setOpaque(false);
        jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged
        if(jCheckBox1.isSelected()==false){
            jDateChooser1.setEnabled(false);
            jDateChooser2.setEnabled(false);
        }else{
            jDateChooser1.setEnabled(true);
            jDateChooser2.setEnabled(true);
        }
    }//GEN-LAST:event_jCheckBox1StateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JScrollPane jScrollPane26;
    public javax.swing.JTextArea jTextArea26;
    // End of variables declaration//GEN-END:variables
}
