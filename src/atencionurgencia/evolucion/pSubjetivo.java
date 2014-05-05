
package atencionurgencia.evolucion;

import entidades.HcuEvolucion;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import jpa.HcuEvolucionJpaController;

/**
 *
 * @author Alvaro Monsalve
 */
public class pSubjetivo extends javax.swing.JPanel {
    private HcuEvolucionJpaController jpaController=null;
    private HcuEvolucion evolucion;

    /**
     * Creates new form pSubjetivo
     */
    public pSubjetivo() {
        initComponents();
    }
    
    public void setEvolucion(HcuEvolucion evolucion){
        if(evolucion.getSubjetivo()!=null) jTextPane1.setText(evolucion.getSubjetivo());
        this.evolucion=evolucion;
    }
    
    public void saveChanged(EntityManagerFactory factory, HcuEvolucion evolucion){
        if(jpaController==null) jpaController=new HcuEvolucionJpaController(factory);
        if(!jTextPane1.getText().equals(evolucion.getSubjetivo())){
            try {
                evolucion.setSubjetivo(jTextPane1.getText().toUpperCase());
                if(evolucion.getId()!=null)   jpaController.edit(evolucion);            
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10129:\n"+ex.getMessage(), pSubjetivo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public boolean estadoTablas(){
        return !jTextPane1.getText().isEmpty();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setMaximumSize(new java.awt.Dimension(603, 386));
        setMinimumSize(new java.awt.Dimension(603, 386));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(603, 386));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "(S) SUBJETIVO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel1.setOpaque(false);

        jScrollPane1.setHorizontalScrollBar(null);
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
