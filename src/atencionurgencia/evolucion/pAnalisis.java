
package atencionurgencia.evolucion;

import entidades.HcuEvolucion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import jpa.HcuEvolucionJpaController;
import jpa.StaticCie10JpaController;
import other.cie10;

/**
 *
 * @author Alvaro Monsalve
 */
public class pAnalisis extends javax.swing.JPanel {
    private HcuEvolucionJpaController jpaController=null;
    private HcuEvolucion evolucion;
    public int dx=1, dx1=1, dx2=1, dx3=1, dx4=1;
    private cie10 cie=null;

    public pAnalisis() {
        initComponents();
    }

    public void setEvolucion(HcuEvolucion evolucion){
        if(evolucion.getAnalisis()!=null) jTextPane1.setText(evolucion.getAnalisis());
        this.evolucion=evolucion;
        if(evolucion.getDx().getId()==1){
            jTextField1.setText("");
        }else{
            jTextField1.setText("["+evolucion.getDx().getCodigo()+"] "+ evolucion.getDx().getDescripcion());
            jTextField1.setCaretPosition(0);
            dx=evolucion.getDx().getId();
        }
        if(evolucion.getDx1().getId()==1){
            jTextField2.setText("");
        }else{
            jTextField2.setText("["+evolucion.getDx1().getCodigo()+"] "+ evolucion.getDx1().getDescripcion());
            jTextField2.setCaretPosition(0);
            dx1=evolucion.getDx1().getId();
        }
        if(evolucion.getDx2().getId()==1){
            jTextField3.setText("");
        }else{
            jTextField3.setText("["+evolucion.getDx2().getCodigo()+"] "+ evolucion.getDx2().getDescripcion());
            jTextField3.setCaretPosition(0);
            dx2=evolucion.getDx2().getId();
        }
        if(evolucion.getDx3().getId()==1){
            jTextField4.setText("");
        }else{
            jTextField4.setText("["+evolucion.getDx3().getCodigo()+"] "+ evolucion.getDx3().getDescripcion());
            jTextField4.setCaretPosition(0);
            dx3=evolucion.getDx3().getId();
        }
        if(evolucion.getDx4().getId()==1){
            jTextField5.setText("");
        }else{
            jTextField5.setText("["+evolucion.getDx4().getCodigo()+"] "+ evolucion.getDx4().getDescripcion());
            jTextField5.setCaretPosition(0);
            dx4=evolucion.getDx4().getId();
        }
    }
    
    public void saveChanged(EntityManagerFactory factory,HcuEvolucion evolucion){
        if(jpaController==null) jpaController=new HcuEvolucionJpaController(factory);
        StaticCie10JpaController scjc = new StaticCie10JpaController(factory);
        if(dx != 1)
            evolucion.setDx( scjc.findStaticCie10(dx));
        if(dx1 != 1)
            evolucion.setDx1( scjc.findStaticCie10(dx1));                
        if(dx2 != 1)
            evolucion.setDx2( scjc.findStaticCie10(dx2));
        if(dx3 != 1)
            evolucion.setDx3( scjc.findStaticCie10(dx3));
        if(dx4 != 1)
            evolucion.setDx4( scjc.findStaticCie10(dx4));
        if(!jTextPane1.getText().equals(evolucion.getSubjetivo())){
            evolucion.setAnalisis(jTextPane1.getText().toUpperCase());
        }
        try {
            jpaController.edit(evolucion);            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10132:\n"+ex.getMessage(), pAnalisis.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public List<String> estadoTablasVal(){
        List<String> mensaje = new ArrayList<String>();
        if(jTextPane1.getText().isEmpty() == true){
            mensaje.add("*Analisis*");
        }        
        if(jTextField1.getText().isEmpty()){
            mensaje.add("*DX Principal*");
        }        
        return mensaje;
    }
    
    public boolean estadoTablas(){
        return estadoTablasVal().isEmpty();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(603, 386));
        setMinimumSize(new java.awt.Dimension(603, 386));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(603, 386));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "(A) ANALISIS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel1.setOpaque(false);

        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );

        jLabel1.setText("DX");

        jLabel2.setText("DX Rel. 1");

        jLabel3.setText("DX Rel. 2");

        jLabel4.setText("DX Rel. 3");

        jLabel5.setText("DX Rel. 4");

        jTextField1.setEditable(false);
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTextField1MouseReleased(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTextField2MouseReleased(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTextField3MouseReleased(evt);
            }
        });

        jTextField4.setEditable(false);
        jTextField4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTextField4MouseReleased(evt);
            }
        });

        jTextField5.setEditable(false);
        jTextField5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTextField5MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jTextField1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(29, 29, 29)
                        .addComponent(jTextField5))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(29, 29, 29)
                        .addComponent(jTextField2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(29, 29, 29)
                        .addComponent(jTextField3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(29, 29, 29)
                        .addComponent(jTextField4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseReleased
        if(cie==null){
            cie = new cie10();
            cie.setLocationRelativeTo(this);
            cie.setVisible(true);
        }else{
            cie.setVisible(true);
        }
       cie.diag=6;
    }//GEN-LAST:event_jTextField1MouseReleased

    private void jTextField2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseReleased
        if(cie==null){
            cie = new cie10();
            cie.setLocationRelativeTo(this);
            cie.setVisible(true);
        }else{
            cie.setVisible(true);
        }
       cie.diag=7;
    }//GEN-LAST:event_jTextField2MouseReleased

    private void jTextField3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseReleased
        if(cie==null){
            cie = new cie10();
            cie.setLocationRelativeTo(this);
            cie.setVisible(true);
        }else{
            cie.setVisible(true);
        }
       cie.diag=8;
    }//GEN-LAST:event_jTextField3MouseReleased

    private void jTextField4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField4MouseReleased
        if(cie==null){
            cie = new cie10();
            cie.setLocationRelativeTo(this);
            cie.setVisible(true);
        }else{
            cie.setVisible(true);
        }
       cie.diag=9;
    }//GEN-LAST:event_jTextField4MouseReleased

    private void jTextField5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField5MouseReleased
        if(cie==null){
            cie = new cie10();
            cie.setLocationRelativeTo(this);
            cie.setVisible(true);
        }else{
            cie.setVisible(true);
        }
       cie.diag=10;
    }//GEN-LAST:event_jTextField5MouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField2;
    public javax.swing.JTextField jTextField3;
    public javax.swing.JTextField jTextField4;
    public javax.swing.JTextField jTextField5;
    public javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
