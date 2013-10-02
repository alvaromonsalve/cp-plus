/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.ingreso;

import atencionurgencia.AtencionUrgencia;
import entidades.InfoHistoriac;
import entidades.InfoMedidasgHcu;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jpa.InfoMedidasgHcuJpaController;
import jpa.exceptions.NonexistentEntityException;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class pTratMedidaGeneral extends javax.swing.JPanel {
    private DefaultTableModel ModeloTabla;
    private Object dato[] = null;
    private List<InfoMedidasgHcu> listMedidasG;
    private InfoMedidasgHcuJpaController medidasgHcuJPA=null;

    /**
     * Creates new form pTratMedidaGeneral
     */
    public pTratMedidaGeneral() {
        initComponents();
        setCargaTabla();
    }
    
    private DefaultTableModel getModelo(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"Medida General","Observacion"}){
        Class[] types = new Class [] {
            java.lang.String.class,
            java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
        false,false
        };
        @Override
        public Class getColumnClass(int columnIndex) {
           return types [columnIndex];
        }
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex){
           return canEdit [colIndex];
        }
    };  
        return model;
    }
    
    private void setCargaTabla(){
        ModeloTabla = getModelo();
        jTable1.setModel(ModeloTabla);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Funciones.setOcultarColumnas(jTable1,new int[]{1});
    }
    
    public void cargaDato(){
        int rowIndex = ModeloTabla.getRowCount();
        if(!jTextArea27.getText().equals("")){
            ModeloTabla.addRow(dato);
            ModeloTabla.setValueAt(jTextArea27.getText().toUpperCase(), rowIndex, 0);
            ModeloTabla.setValueAt("", rowIndex, 1);
            jTextArea27.setText("");
        
        }
    }
    
    public void saveChanges(EntityManagerFactory factory, InfoHistoriac ihc){
        if(medidasgHcuJPA==null){
            medidasgHcuJPA=new InfoMedidasgHcuJpaController(factory);
        }
        listMedidasG = medidasgHcuJPA.ListFindInfoMedidasGHcu(ihc);
        for(int i=0;i<ModeloTabla.getRowCount();i++){
            InfoMedidasgHcu hcu=null;
            boolean exist=false;
            for(InfoMedidasgHcu imh:listMedidasG){
                if(((String)ModeloTabla.getValueAt(i, 0)).equals(imh.getMedidag())){
                    exist=true;
                    hcu=imh;
                    break;
                }
            }
            if(!exist){
                hcu=new InfoMedidasgHcu();
                hcu.setIdHistoriac(ihc.getId());
                hcu.setMedidag((String)ModeloTabla.getValueAt(i, 0));
                hcu.setObservacion((String)ModeloTabla.getValueAt(i, 1));
                hcu.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                medidasgHcuJPA.create(hcu);
            }else{
                if(hcu!=null){
                    try {
                        hcu.setMedidag((String)ModeloTabla.getValueAt(i, 0));
                        hcu.setObservacion((String)ModeloTabla.getValueAt(i, 1));
                        hcu.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                        medidasgHcuJPA.edit(hcu);
                    } catch (NonexistentEntityException ex) {
                        JOptionPane.showMessageDialog(null, "10100:\n"+ex.getMessage(), pTratMedidaGeneral.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "10101:\n"+ex.getMessage(), pTratMedidaGeneral.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
        for(InfoMedidasgHcu imh:listMedidasG){
            boolean exist=false;
            for(int i=0;i<ModeloTabla.getRowCount();i++){
                if(((String)ModeloTabla.getValueAt(i, 0)).equals(imh.getMedidag())){
                    exist=true;
                    break;
                }
            }
            if(!exist){
                try {
                    medidasgHcuJPA.destroy(imh.getId());
                } catch (NonexistentEntityException ex) {
                    JOptionPane.showMessageDialog(null, "10102:\n"+ex.getMessage(), pTratMedidaGeneral.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    public void showListExistentes(EntityManagerFactory factory,InfoHistoriac ihc){
        if(medidasgHcuJPA==null){
            medidasgHcuJPA=new InfoMedidasgHcuJpaController(factory);
        }
        listMedidasG=medidasgHcuJPA.ListFindInfoMedidasGHcu(ihc);
        for(int i=0;i<listMedidasG.size();i++){
            ModeloTabla.addRow(dato);
            ModeloTabla.setValueAt(listMedidasG.get(i).getMedidag(), i, 0);
            ModeloTabla.setValueAt(listMedidasG.get(i).getObservacion(), i, 1);
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
        buttonSeven6 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven7 = new org.edisoncor.gui.button.ButtonSeven();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel32 = new javax.swing.JPanel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTextArea26 = new javax.swing.JTextArea();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTextArea27 = new javax.swing.JTextArea();

        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(380, 420));
        setMinimumSize(new java.awt.Dimension(380, 420));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(380, 420));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        jLabel49.setText("MEDIDAS GENERALES");

        buttonSeven6.setForeground(new java.awt.Color(0, 0, 255));
        buttonSeven6.setText("Agregar");
        buttonSeven6.setColorBrillo(new java.awt.Color(255, 255, 255));
        buttonSeven6.setColorDeSombra(new java.awt.Color(255, 255, 255));
        buttonSeven6.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        buttonSeven6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven6ActionPerformed(evt);
            }
        });

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

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setOpaque(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTable1MouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jPanel32.setOpaque(false);

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

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        jScrollPane27.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane27.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane27.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea27.setColumns(20);
        jTextArea27.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea27.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea27.setLineWrap(true);
        jTextArea27.setRows(2);
        jTextArea27.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea27.setMinimumSize(new java.awt.Dimension(164, 40));
        jScrollPane27.setViewportView(jTextArea27);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonSeven6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSeven6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonSeven6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven6ActionPerformed
        this.cargaDato();
    }//GEN-LAST:event_buttonSeven6ActionPerformed

    private void buttonSeven7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven7ActionPerformed
        if(ModeloTabla.getRowCount()>0 && jTable1.getSelectedRow()>-1){
            ModeloTabla.removeRow(jTable1.getSelectedRow());
            jTextArea26.setText("");
        }
    }//GEN-LAST:event_buttonSeven7ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==1){
            jTextArea26.setText((String)jTable1.getValueAt(jTable1.getSelectedRow(), 1));
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved
        int rowIndex = jTable1.rowAtPoint(evt.getPoint());
        jTable1.setToolTipText("<html>\n" +
            "<div style=\"width:300;\">"+(String)jTable1.getValueAt(rowIndex, 0)+"</div>\n" +
            "\n" +
            "</html>");
    }//GEN-LAST:event_jTable1MouseMoved

    private void jTextArea26KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea26KeyReleased
        if(jTable1.getSelectedRow()>-1){
            ModeloTabla.setValueAt(jTextArea26.getText().toUpperCase(), jTable1.getSelectedRow(), 1);
        }
    }//GEN-LAST:event_jTextArea26KeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonSeven buttonSeven6;
    private org.edisoncor.gui.button.ButtonSeven buttonSeven7;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    public javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea26;
    private javax.swing.JTextArea jTextArea27;
    // End of variables declaration//GEN-END:variables
}
