package atencionurgencia.ingreso;

import atencionurgencia.AtencionUrgencia;
import entidades.ConfigCups;
import entidades.InfoHistoriac;
import entidades.InfoProcedimientoHcu;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jpa.ConfigCupsJpaController;
import jpa.InfoProcedimientoHcuJpaController;
import jpa.exceptions.NonexistentEntityException;
import other.dSelectProcedimiento;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class pTratImagenologia extends javax.swing.JPanel {
    private dSelectProcedimiento dProcedimiento;
    private List<InfoProcedimientoHcu> listInfoProcedimientoHcu;
    private InfoProcedimientoHcuJpaController infoProcedimientoHcuJPA;
    private ConfigCupsJpaController cupsJpaController=null;
    private DefaultTableModel ModeloTabla;
    private final Object dato[] = null;

    /**
     * Creates new form pTratImagenologia
     */
    public pTratImagenologia() {
        initComponents();
        setCargaTabla();
    }
    
    private DefaultTableModel getModelo(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"entidadProced", "Codigo CUPS", "Descripción Procedimiento","Observacion"}){
        Class[] types = new Class [] {
            ConfigCups.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class
        };

        boolean[] canEdit = new boolean [] {
        false,false,false,false
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
        Funciones.setOcultarColumnas(jTable1,new int[]{0,3});
        Funciones.setSizeColumnas(jTable1, new int[]{1}, new int[]{80});
    }
    
    public void cargaDatoSeleccionado(ConfigCups cc,String observ){
        int rowIndex = ModeloTabla.getRowCount();
        boolean existe=false;
        for(int i=0;i<ModeloTabla.getRowCount();i++){
            if(cc.getId().equals(((ConfigCups)ModeloTabla.getValueAt(i, 0)).getId())){
                existe = true;
                break;
            }
        }
        if(!existe){
            ModeloTabla.addRow(dato);
            ModeloTabla.setValueAt(cc, rowIndex, 0);
            ModeloTabla.setValueAt(cc.getCodigo(), rowIndex, 1);
            ModeloTabla.setValueAt(cc.getDeSubcategoria(), rowIndex, 2);
            ModeloTabla.setValueAt(observ, rowIndex, 3);
        }
    }
    
    public void saveChanges(EntityManagerFactory factory, InfoHistoriac ihc){
        if(infoProcedimientoHcuJPA == null){
            infoProcedimientoHcuJPA =new InfoProcedimientoHcuJpaController(factory);
        }
        listInfoProcedimientoHcu = infoProcedimientoHcuJPA.ListFindInfoProcedimientoHcu(ihc);
        for(int i=0;i<ModeloTabla.getRowCount();i++){
            InfoProcedimientoHcu procedimientoHcu=null;
            boolean exist=false;
            for (InfoProcedimientoHcu listInfoProcedimientoHcu1 : listInfoProcedimientoHcu) {
                if (((ConfigCups)ModeloTabla.getValueAt(i, 0)).getId() == listInfoProcedimientoHcu1.getIdCups()) {
                    exist=true;
                    procedimientoHcu = listInfoProcedimientoHcu1;
                    break;
                }
            }
            if(!exist){
                procedimientoHcu = new InfoProcedimientoHcu();
                procedimientoHcu.setIdCups(((ConfigCups)ModeloTabla.getValueAt(i, 0)).getId());
                procedimientoHcu.setIdHistoriac(ihc.getId());
                procedimientoHcu.setObservacion(ModeloTabla.getValueAt(i, 3).toString());
                procedimientoHcu.setEstado(0);//estado inicial
                procedimientoHcu.setIdUsuario(atencionurgencia.AtencionUrgencia.configdecripcionlogin.getId());                                
                infoProcedimientoHcuJPA.create(procedimientoHcu);
            }else{
                if(procedimientoHcu!=null){
                    procedimientoHcu.setObservacion(ModeloTabla.getValueAt(i, 3).toString());
                    if(AtencionUrgencia.panelindex.hc.auditoria==false){
                        procedimientoHcu.setIdUsuario(atencionurgencia.AtencionUrgencia.configdecripcionlogin.getId());
                    }                    
                    try {
                        infoProcedimientoHcuJPA.edit(procedimientoHcu);
                    } catch (NonexistentEntityException ex) {
                        JOptionPane.showMessageDialog(null, "10085:\n"+ex.getMessage(), pTratImagenologia.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "10086:\n"+ex.getMessage(), pTratImagenologia.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
        for (InfoProcedimientoHcu listInfoProcedimientoHcu1 : listInfoProcedimientoHcu) {
            if(cupsJpaController==null){
                cupsJpaController=new ConfigCupsJpaController(factory);
            }
            ConfigCups cups = cupsJpaController.findConfigCups(listInfoProcedimientoHcu1.getIdCups());
            if (cups.getIdEstructuraCups().getId() ==15) {
                boolean exist=false;
                for (int a = 0; a<ModeloTabla.getRowCount(); a++) {
                    if (listInfoProcedimientoHcu1.getIdCups() == ((ConfigCups)ModeloTabla.getValueAt(a, 0)).getId()) {
                        exist=true;
                        break;
                    }
                }
                if (!exist) {
                    try {
                        infoProcedimientoHcuJPA.destroy(listInfoProcedimientoHcu1.getId());
                    }catch (NonexistentEntityException ex) {
                        JOptionPane.showMessageDialog(null, "10087:\n"+ex.getMessage(), pTratImagenologia.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }
    
    public void showListExistentes(EntityManagerFactory factory,InfoHistoriac ihc){
        if(infoProcedimientoHcuJPA == null){
            infoProcedimientoHcuJPA =new InfoProcedimientoHcuJpaController(factory);
        }
        if(cupsJpaController==null){
            cupsJpaController=new ConfigCupsJpaController(factory);
        }
        listInfoProcedimientoHcu = infoProcedimientoHcuJPA.ListFindInfoProcedimientoHcu(ihc);
        for (InfoProcedimientoHcu listInfoProcedimientoHcu1 : listInfoProcedimientoHcu) {
            ConfigCups cups = cupsJpaController.findConfigCups(listInfoProcedimientoHcu1.getIdCups());
            if (cups.getIdEstructuraCups().getId() ==15) {
                this.cargaDatoSeleccionado(cups, listInfoProcedimientoHcu1.getObservacion());
            }
        }
    }
    
    public void formularioOpen(){
        dProcedimiento = new dSelectProcedimiento(null,true,15,false);
        dProcedimiento.setVisible(true);
    }

    /**
     * Retorna el estado sobre los registros de las tablas
     * @return true si alguna de las tablas tienen registros y false si las dos tablas estan vacias
     */  
    public boolean estadoTablas(){
        return ModeloTabla.getRowCount()>0;   
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel49 = new javax.swing.JLabel();
        buttonSeven6 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven7 = new org.edisoncor.gui.button.ButtonSeven();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea25 = new javax.swing.JTextArea();

        setMaximumSize(new java.awt.Dimension(380, 420));
        setMinimumSize(new java.awt.Dimension(380, 420));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(380, 420));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/XRay.png"))); // NOI18N
        jLabel49.setText("Imagenologia");
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
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
            .addComponent(jScrollPane25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonSeven6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSeven6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonSeven6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven6ActionPerformed
        formularioOpen();
    }//GEN-LAST:event_buttonSeven6ActionPerformed

    private void buttonSeven7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven7ActionPerformed
        if(ModeloTabla.getRowCount()>0 && jTable1.getSelectedRow()>-1){
            ModeloTabla.removeRow(jTable1.getSelectedRow());
        }
    }//GEN-LAST:event_buttonSeven7ActionPerformed

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved
        int rowIndex = jTable1.rowAtPoint(evt.getPoint());
        jTable1.setToolTipText("<html>\n" +
            "<div style=\"width:300;\">"+(String)jTable1.getValueAt(rowIndex, 2)+"</div>\n" +
            "\n" +
            "</html>");
    }//GEN-LAST:event_jTable1MouseMoved

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==1){
            jTextArea25.setText((String)jTable1.getValueAt(jTable1.getSelectedRow(), 3));
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextArea25KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea25KeyReleased
       if(jTable1.getSelectedRow()>-1){
           ModeloTabla.setValueAt(jTextArea25.getText().toUpperCase(), jTable1.getSelectedRow(), 3);
       }
    }//GEN-LAST:event_jTextArea25KeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public org.edisoncor.gui.button.ButtonSeven buttonSeven6;
    public org.edisoncor.gui.button.ButtonSeven buttonSeven7;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane25;
    public javax.swing.JTable jTable1;
    public javax.swing.JTextArea jTextArea25;
    // End of variables declaration//GEN-END:variables
}
