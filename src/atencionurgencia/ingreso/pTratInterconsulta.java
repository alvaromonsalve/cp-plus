/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.ingreso;

import atencionurgencia.AtencionUrgencia;
import entidades.InfoHistoriac;
import entidades.InfoInterconsultaHcu;
import entidades.StaticEspecialidades;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import jpa.InfoInterconsultaHcuJpaController;
import jpa.StaticEspecialidadesJpaController;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author Alvaro Monsalve
 */
public class pTratInterconsulta extends javax.swing.JPanel {
    private int tipo;
    private EntityManagerFactory factory;
    private List<StaticEspecialidades> listStaticEspecialidades;
    private StaticEspecialidadesJpaController staticEspecialidadesJPA=null;
    private InfoInterconsultaHcu interconsultaHcu=null;
    private InfoInterconsultaHcuJpaController interconsultaHcuJPA=null;
    private boolean delete=false;
    private StaticEspecialidades especialidades;
    private String s=System.getProperty("file.separator");

    /**
     * Creates new form pTratInterconsulta
     */
    public pTratInterconsulta(int tipo) {
        initComponents();
        
        this.tipo=tipo;
        setCargaCombo();
        setPanel();
    }
    
    private void setCargaCombo(){
        if(staticEspecialidadesJPA==null){
            factory = Persistence.createEntityManagerFactory("ClipaEJBPU",AtencionUrgencia.props);
            staticEspecialidadesJPA= new StaticEspecialidadesJpaController(factory);
        }
        listStaticEspecialidades=staticEspecialidadesJPA.findStaticEspecialidadesEntities();
        for(StaticEspecialidades se: listStaticEspecialidades){
            /** cirugia geenral */
            if(tipo==0){
                if(se.getId()==5){
                    especialidades = se;
                }
            /** Ginecologia */
            }else if(tipo==1){
                if(se.getId()==16){
                    especialidades = se;
                }
            /** Medicina Interna */
            }else if(tipo==2){
                if(se.getId()==23){
                    especialidades = se;
                }
            /** Ortopedia */
            }else if(tipo==3){
                if(se.getId()==36){
                    especialidades = se;
                }
            /** Pediatria */
            }else if(tipo==4){
                if(se.getId()==39){
                    especialidades = se;
                }
            }
        }
    }
    
    private void setPanel(){
        ImageIcon retValue=null;
        String StringValue=null;
        if(tipo==0){
            retValue=new javax.swing.ImageIcon(getClass().getResource("/images/surgeon_icon.png"));
            StringValue="Cirugia General";
        }else if(tipo==1){
            retValue=new javax.swing.ImageIcon(getClass().getResource("/images/Female_icon.png"));
            StringValue="Ginecologia";
        }else if(tipo==2){
            retValue=new javax.swing.ImageIcon(getClass().getResource("/images/Head_physician_icon.png"));
            StringValue="Medicina Interna";
        }else if(tipo==3){
            retValue=new javax.swing.ImageIcon(getClass().getResource("/images/plaster_icon.png"));
            StringValue="Ortopedia";
        }else if(tipo==4){
            retValue=new javax.swing.ImageIcon(getClass().getResource("/images/stethoscope-icon.png"));
            StringValue="Pediatria";
        }
        jLabel49.setIcon(retValue);
        jLabel49.setText(StringValue);
    }
    
    public void saveChanges(InfoHistoriac ihc){
        if(interconsultaHcuJPA==null){
            interconsultaHcuJPA = new InfoInterconsultaHcuJpaController(factory);
        }
        if(interconsultaHcu==null){
            if(!"".equals(jTextArea25.getText())){
                interconsultaHcu = new InfoInterconsultaHcu();
                interconsultaHcu.setIdEspecialidades(especialidades.getId());
                interconsultaHcu.setIdHistoriac(ihc.getId());
                interconsultaHcu.setJustificacion(jTextArea25.getText().toUpperCase());
                /** id 5129 de codigo cups 890702 - CONSULTA DE URGENCIAS, POR MEDICINA ESPECIALIZADA */
                interconsultaHcu.setIdConfigCups(5129);
                interconsultaHcu.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                interconsultaHcuJPA.create(interconsultaHcu);
            }
        }else{
            if(delete){
                try {
                    interconsultaHcuJPA.destroy(interconsultaHcu.getId());
                } catch (NonexistentEntityException ex) {
                    JOptionPane.showMessageDialog(null, "10094:\n"+ex.getMessage(), pTratInterconsulta.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                if(!"".equals(jTextArea25.getText())){
                    try {
                        interconsultaHcu.setIdEspecialidades(especialidades.getId());
                        interconsultaHcu.setJustificacion(jTextArea25.getText().toUpperCase());
                        interconsultaHcu.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                        interconsultaHcuJPA.edit(interconsultaHcu);
                    } catch (NonexistentEntityException ex) {
                        JOptionPane.showMessageDialog(null, "10095:\n"+ex.getMessage(), pTratInterconsulta.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "10096:\n"+ex.getMessage(), pTratInterconsulta.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(this,"La justificacion no puede estar vacia");
                }
            }
        }
    }
    
    public void showExistente(InfoHistoriac ihc){
        if(interconsultaHcuJPA==null){
            interconsultaHcuJPA = new InfoInterconsultaHcuJpaController(factory);
        }
            interconsultaHcu = interconsultaHcuJPA.findInterconsultaHcu_HC(ihc, especialidades);
        if(interconsultaHcu != null){
            jTextArea25.setText(interconsultaHcu.getJustificacion());
            jLabel1.setText("SOLICITADO");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel49 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea25 = new javax.swing.JTextArea();
        buttonSeven7 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven8 = new org.edisoncor.gui.button.ButtonSeven();
        jLabel1 = new javax.swing.JLabel();

        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(380, 420));
        setMinimumSize(new java.awt.Dimension(380, 420));
        setOpaque(false);

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/surgeon_icon.png"))); // NOI18N
        jLabel49.setText("...");

        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder("Justificacion"));
        jPanel31.setOpaque(false);

        jScrollPane25.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane25.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane25.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea25.setColumns(20);
        jTextArea25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea25.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea25.setLineWrap(true);
        jTextArea25.setRows(2);
        jTextArea25.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea25.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextArea25KeyReleased(evt);
            }
        });
        jScrollPane25.setViewportView(jTextArea25);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
        );

        buttonSeven7.setForeground(new java.awt.Color(0, 0, 255));
        buttonSeven7.setText("Borrar");
        buttonSeven7.setColorBrillo(new java.awt.Color(255, 255, 255));
        buttonSeven7.setColorDeSombra(new java.awt.Color(255, 255, 255));
        buttonSeven7.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        buttonSeven7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven7ActionPerformed(evt);
            }
        });

        buttonSeven8.setForeground(new java.awt.Color(0, 0, 255));
        buttonSeven8.setText("Aceptar");
        buttonSeven8.setColorBrillo(new java.awt.Color(255, 255, 255));
        buttonSeven8.setColorDeSombra(new java.awt.Color(255, 255, 255));
        buttonSeven8.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        buttonSeven8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven8ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SIN SOLICITUD");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(buttonSeven8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextArea25KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea25KeyReleased

    }//GEN-LAST:event_jTextArea25KeyReleased

    private void buttonSeven7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven7ActionPerformed
        jTextArea25.setText("");
        delete = true;
        jLabel1.setText("SIN SOLICITUD");
    }//GEN-LAST:event_buttonSeven7ActionPerformed

    private void buttonSeven8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven8ActionPerformed
        if(!"".equals(jTextArea25.getText())){
            jLabel1.setText("SOLICITADO"); 
            delete=false;
        }
    }//GEN-LAST:event_buttonSeven8ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public org.edisoncor.gui.button.ButtonSeven buttonSeven7;
    public org.edisoncor.gui.button.ButtonSeven buttonSeven8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JScrollPane jScrollPane25;
    public javax.swing.JTextArea jTextArea25;
    // End of variables declaration//GEN-END:variables
}
