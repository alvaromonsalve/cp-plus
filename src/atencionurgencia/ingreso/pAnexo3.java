/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.ingreso;

import atencionurgencia.documentos.dCupsAnexo3;
import entidades.ConfigCups;
import entidades.HcuAnexo3;
import entidades.HcuAnexo3Det;
import entidades.InfoHistoriac;
import entidades.InfoProcedimientoHcu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jpa.HcuAnexo3DetJpaController;
import jpa.HcuAnexo3JpaController;
import jpa.InfoProcedimientoHcuJpaController;
import tools.Funciones;
import tools.numericKeypad;
import atencionurgencia.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jpa.exceptions.IllegalOrphanException;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author Alvaro Monsalve
 */
public class pAnexo3 extends javax.swing.JPanel {
    
    private DefaultTableModel ModeloTabla;
    private InfoProcedimientoHcuJpaController procedimientoHcuJPA=null;
    private List<InfoProcedimientoHcu> listProcedimientoHcu;
    private EntityManagerFactory factory;
    private final Object dato[] = null;
    private HcuAnexo3 hcuAnexo3;
    private HcuAnexo3JpaController hcuAnexo3JPA=null;
    private HcuAnexo3Det hcuAnexo3Det;
    private HcuAnexo3DetJpaController hcuAnexo3DetJPA=null;
    /**Esta variable indica si el anexo puede ser modificado o no true:es modificable; false:no es modificable
       el anexo aun no ha sido enviado a la entidad por lo que podemos modificarlo*/
    private boolean editeAnexo=false;
    /**Esta variable indica si el anexo3 ha sido notificado al admisionista true:a sido notificado; false:no a sido notificado*/ 
    private boolean NotificacionAnexo=false;
    private InfoHistoriac infohistoriac;
    private dCupsAnexo3 ca=null;
    
    public pAnexo3(InfoHistoriac infohistoriac, EntityManagerFactory factory) {
        initComponents();
        this.infohistoriac=infohistoriac;
        this.factory=factory;
        setCargaTabla();
    }
    
    private DefaultTableModel getModelo(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"codCups","Cantidad" ,"CUPS","Descripcion CUPS"}){
        Class[] types = new Class [] {
            entidades.ConfigCups.class,
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
        Funciones.setOcultarColumnas(jTable1,new int[]{0});
        Funciones.setSizeColumnas(jTable1, new int[]{1,2}, new int[]{60,60});
//        jTable1.setDefaultRenderer(Object.class, new tools.IconCellRenderer());
        Funciones.setColorTableHeader(jTable1, new int[]{1,2,3});
    }
    
    public void showLista(){
        if(hcuAnexo3JPA==null){
            hcuAnexo3JPA= new HcuAnexo3JpaController(factory);
        }
        while(ModeloTabla.getRowCount()>0){
            ModeloTabla.removeRow(0);
        }
        //findHcuAnexo3 filtra estados mayores a 1
        hcuAnexo3 = hcuAnexo3JPA.findHcuAnexo3(infohistoriac);
        /**
         * Si la hc tiene anexos sin finalizar este sera mostrado para posterior modificacion 
         * o en su defecto prepararlo para enviar, pero esto solo lo hara el admisionista (estado 2)
         */
        if(hcuAnexo3!=null){
            for(int i=0;i<hcuAnexo3.getHcuAnexo3DetList().size();i++){
                ModeloTabla.addRow(dato);
                ModeloTabla.setValueAt(hcuAnexo3.getHcuAnexo3DetList().get(i).getCodigoCup(), i, 0);
                ModeloTabla.setValueAt("1", i, 1);
                ModeloTabla.setValueAt(hcuAnexo3.getHcuAnexo3DetList().get(i).getCodigoCup().getCodigo(), i, 2);
                ModeloTabla.setValueAt(hcuAnexo3.getHcuAnexo3DetList().get(i).getCodigoCup().getDeSubcategoria(), i, 3);
            }
        }
    }

    
    /**
     * guarda en la bd los cambios realizados en el panel anexo3
     * @param ih entidad historia clinica de urgencia
     */
    public void saveChanges(InfoHistoriac ih){
        if(ModeloTabla.getRowCount()>0){//Si existen registros en jtable del anexo
            if(hcuAnexo3JPA==null){
                  hcuAnexo3JPA= new HcuAnexo3JpaController(factory);
                  hcuAnexo3DetJPA= new HcuAnexo3DetJpaController(factory);
            }      
            if(hcuAnexo3JPA.getHcuAnexo3Count(ih)==0){// si no existen anexos3 activos sin finalizar          
                hcuAnexo3 = new HcuAnexo3();
                hcuAnexo3.setJustificacion(jTextArea26.getText().toUpperCase());
                hcuAnexo3.setIdInfoHistoriac(ih);
                hcuAnexo3.setNombreMedico(AtencionUrgencia.configdecripcionlogin.getNombres()+
                       " "+AtencionUrgencia.configdecripcionlogin.getNombres());
                hcuAnexo3.setIdusuario(AtencionUrgencia.configdecripcionlogin.getId());
                hcuAnexo3.setEstado(1);
                List<HcuAnexo3Det> hads = new ArrayList<HcuAnexo3Det>();
                for(int i=0;i<ModeloTabla.getRowCount();i++){
                    HcuAnexo3Det det = new HcuAnexo3Det();
                    det.setCodigoCup((ConfigCups)ModeloTabla.getValueAt(i, 0));
                    det.setCantidad(Short.valueOf(ModeloTabla.getValueAt(i, 1).toString()));
                    det.setIdHcuAnexo3(hcuAnexo3);
                    hads.add(det);
                }
                hcuAnexo3.setHcuAnexo3DetList(hads);
                hcuAnexo3JPA.create(hcuAnexo3);
            }else{//existen anexos3 tecnicos activos sin finalizar
                hcuAnexo3.setJustificacion(jTextArea26.getText().toUpperCase());
                hcuAnexo3.setIdInfoHistoriac(ih);
                hcuAnexo3.setNombreMedico(AtencionUrgencia.configdecripcionlogin.getNombres()+
                       " "+AtencionUrgencia.configdecripcionlogin.getNombres());
                hcuAnexo3.setIdusuario(AtencionUrgencia.configdecripcionlogin.getId());
                hcuAnexo3.setEstado(1);
//                hcuAnexo3.getHcuAnexo3DetList().clear();
//                List<HcuAnexo3Det> hads = new ArrayList<HcuAnexo3Det>();
//                for(int i=0;i<ModeloTabla.getRowCount();i++){
//                    HcuAnexo3Det det = new HcuAnexo3Det();
//                    det.setCodigoCup((ConfigCups)ModeloTabla.getValueAt(i, 0));
//                    det.setCantidad(Short.valueOf(ModeloTabla.getValueAt(i, 1).toString()));
//                    det.setIdHcuAnexo3(hcuAnexo3);
//                    hads.add(det);
//                }
//                hcuAnexo3.setHcuAnexo3DetList(hads);
                try {
                    hcuAnexo3JPA.edit(hcuAnexo3);
                } catch (IllegalOrphanException ex) {
                    JOptionPane.showMessageDialog(null, "10103:\n"+ex.getMessage(), pAnexo3.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                } catch (NonexistentEntityException ex) {
                    JOptionPane.showMessageDialog(null, "10104:\n"+ex.getMessage(), pAnexo3.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "10105:\n"+ex.getMessage(), pAnexo3.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

//                for(int i=0;i<ModeloTabla.getRowCount();i++){//recorrer tabla
//                    if((Boolean)jTable1.getValueAt(i, 0)==true){//registros que estan seleccionados
//                        boolean exist=false;
//                        for(HcuAnexo3Det det:hcuAnexo3.getHcuAnexo3DetList()){//recorrer los registros de la bd
//                            if((Integer.parseInt(ModeloTabla.getValueAt(i, 1).toString()))== det.getCodigoCup()){//comparar registros seleccionados de la tabla con la bd
//                                exist=true;
//                                hcuAnexo3Det=det;
//                                break;
//                            }
//                        }
//                        if(!exist){//registro no existe en la bd pero esta seleccionado en la tabla
//                                hcuAnexo3Det=new HcuAnexo3Det();
//                                hcuAnexo3Det.setCantidad(Short.valueOf(ModeloTabla.getValueAt(i, 2).toString()));
//                                hcuAnexo3Det.setCodigoCup(Integer.parseInt(ModeloTabla.getValueAt(i, 1).toString()));
//                                hcuAnexo3Det.setDescripcion((String)ModeloTabla.getValueAt(i, 4));
//                                hcuAnexo3Det.setIdHcuAnexo3(hcuAnexo3);
//                                hcuAnexo3DetJPA.create(hcuAnexo3Det);
//                        }else{//registro existe en la bd y esta seleccionado en la tabla
//                            try {
//                                hcuAnexo3Det.setCantidad(Short.valueOf(ModeloTabla.getValueAt(i, 2).toString()));
//                                hcuAnexo3DetJPA.edit(hcuAnexo3Det);
//                            } catch (NonexistentEntityException ex) {
//                                JOptionPane.showMessageDialog(null, "10106:\n"+ex.getMessage(), pAnexo3.class.getName(), JOptionPane.INFORMATION_MESSAGE);
//                            } catch (Exception ex) {
//                                JOptionPane.showMessageDialog(null, "10107:\n"+ex.getMessage(), pAnexo3.class.getName(), JOptionPane.INFORMATION_MESSAGE);
//                            }
//                        }
//                    }else{//registros que NO estan seleccionados
//                        boolean exist=false;
//                        if(hcuAnexo3!=null){
//                            for(HcuAnexo3Det det:hcuAnexo3.getHcuAnexo3DetList()){//recorrer los registros de la bd
//                                if(Integer.parseInt(ModeloTabla.getValueAt(i, 1).toString()) == det.getCodigoCup()){//comparar registros No seleccionados de la tabla con la bd
//                                    exist=true;
//                                    hcuAnexo3Det=det;
//                                    break;
//                                }
//                            }
//                        }
//                        if(exist){//registro existe en la bd y No esta seleccionado en la tabla
//                            try {
//                                hcuAnexo3DetJPA.destroy(hcuAnexo3Det.getId());
//                            } catch (NonexistentEntityException ex) {
//                                JOptionPane.showMessageDialog(null, "10108:\n"+ex.getMessage(), pAnexo3.class.getName(), JOptionPane.INFORMATION_MESSAGE);
//                            }
//                        }
//                    }
//                }
//                if(hcuAnexo3!=null){
//                    for(int i=0;i<hcuAnexo3.getHcuAnexo3DetList().size();i++){//recorrer registros de la bd
//                        boolean exist=false;
//                        for(int a=0;a<ModeloTabla.getRowCount();a++){//recorrer los registros de la tabla
//                            if(Integer.parseInt(ModeloTabla.getValueAt(a, 1).toString()) == hcuAnexo3.getHcuAnexo3DetList().get(i).getCodigoCup()){
//                                exist=true;
//                                break;
//                            }
//                        }
//                        if(!exist){
//                            try {
//                                hcuAnexo3DetJPA.destroy(hcuAnexo3.getHcuAnexo3DetList().get(i).getId());
//                            } catch (NonexistentEntityException ex) {
//                                JOptionPane.showMessageDialog(null, "10109:\n"+ex.getMessage(), pAnexo3.class.getName(), JOptionPane.INFORMATION_MESSAGE);
//                            }
//                        }
//                    }   
//                }
//            }else{//si no existen registos en jtable se omite Solicitud de Anexo 3
//                hcuAnexo3 = hcuAnexo3JPA.findHcuAnexo3(ih.getId());
//                if(hcuAnexo3!=null){
//                   for(int i=0;i<hcuAnexo3.getHcuAnexo3DetList().size();i++){//recorrer registros de la bd
//                        try {
//                            hcuAnexo3DetJPA.destroy(hcuAnexo3.getHcuAnexo3DetList().get(i).getId());
//                        } catch (NonexistentEntityException ex) {
//                            JOptionPane.showMessageDialog(null, "10110:\n"+ex.getMessage(), pAnexo3.class.getName(), JOptionPane.INFORMATION_MESSAGE);
//                        }
//                    }
//                    try {
//                        hcuAnexo3JPA.destroy(hcuAnexo3.getId());
//                    } catch (IllegalOrphanException ex) {
//                        JOptionPane.showMessageDialog(null, "10111:\n"+ex.getMessage(), pAnexo3.class.getName(), JOptionPane.INFORMATION_MESSAGE);
//                    } catch (NonexistentEntityException ex) {
//                        JOptionPane.showMessageDialog(null, "10112:\n"+ex.getMessage(), pAnexo3.class.getName(), JOptionPane.INFORMATION_MESSAGE);
//                    }  
//                }
//        }
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel32 = new javax.swing.JPanel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTextArea26 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/closeTabButton.png"))); // NOI18N
        jMenuItem1.setText("Borrar");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseReleased(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setOpaque(false);

        jScrollPane27.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane27.setPreferredSize(new java.awt.Dimension(200, 200));

        jTable1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable1.setForeground(new java.awt.Color(0, 51, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setComponentPopupMenu(jPopupMenu1);
        jTable1.setFocusable(false);
        jTable1.setName(""); // NOI18N
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTable1MouseMoved(evt);
            }
        });
        jScrollPane27.setViewportView(jTable1);

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder("Justificacion"));
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
        jScrollPane26.setViewportView(jTextArea26);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Omitir Solicitud de Anexo 3");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Agregar");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    private JPopupMenu pop=null;
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==2){
//            JCheckBox box = new JCheckBox();
//            if(((JCheckBox)ModeloTabla.getValueAt(jTable1.getSelectedRow(), 1)).isSelected()){
//                box.setSelected(false);
//            }else{
//                box.setSelected(true);
//            }
//            ModeloTabla.setValueAt(box, jTable1.getSelectedRow(), 1);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved
        int rowIndex = jTable1.rowAtPoint(evt.getPoint());
        jTable1.setToolTipText("<html>\n" +
        "<div style=\"width:300;\">"+(String)jTable1.getValueAt(rowIndex, 3)+"</div>\n" +
        "\n" +
        "</html>");
    }//GEN-LAST:event_jTable1MouseMoved

    private void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseReleased
        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        if (ca == null) {
            ca = new dCupsAnexo3(infohistoriac, factory);
            ca.jLabel4.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if ((ca.tipoAdd == 0 && e.getButton() == MouseEvent.BUTTON1) & ca.desdeHc.jTable1.getSelectedRow() > -1) {
                        int i = ModeloTabla.getDataVector().size();
                        ModeloTabla.addRow(dato);
                        ModeloTabla.setValueAt((ConfigCups) ca.desdeHc.ModeloTabla.getValueAt(ca.desdeHc.jTable1.getSelectedRow(), 0), i, 0);
                        ModeloTabla.setValueAt("1", i, 1);
                        ModeloTabla.setValueAt(ca.desdeHc.ModeloTabla.getValueAt(ca.desdeHc.jTable1.getSelectedRow(), 1), i, 2);
                        ModeloTabla.setValueAt(ca.desdeHc.ModeloTabla.getValueAt(ca.desdeHc.jTable1.getSelectedRow(), 2), i, 3);
                        ca.dispose();
                    } else if ((ca.tipoAdd == 1 && e.getButton() == MouseEvent.BUTTON1) & ca.desdeCups.jTable1.getSelectedRow() > -1) {
                        int i = ModeloTabla.getDataVector().size();
                        ModeloTabla.addRow(dato);
                        ModeloTabla.setValueAt((ConfigCups) ca.desdeCups.ModeloTabla.getValueAt(ca.desdeCups.jTable1.getSelectedRow(), 0), i, 0);
                        ModeloTabla.setValueAt("1", i, 1);
                        ModeloTabla.setValueAt(ca.desdeCups.ModeloTabla.getValueAt(ca.desdeCups.jTable1.getSelectedRow(), 1), i, 2);
                        ModeloTabla.setValueAt(ca.desdeCups.ModeloTabla.getValueAt(ca.desdeCups.jTable1.getSelectedRow(), 2), i, 3);
                        ca.dispose();
                    }
                }
            });
        }
        ca.setVisible(true);
    }//GEN-LAST:event_jLabel2MouseReleased

//            DefaultTableModel model = new DefaultTableModel(
//        null, new String [] {"codCups","Cantidad" ,"CUPS","Descripcion CUPS"}){
//        Class[] types = new Class [] {
//            entidades.ConfigCups.class,
//            java.lang.String.class,
//            java.lang.String.class,
//            java.lang.String.class
//    
//    all
//                            null, new String[]{"codCups", "CUPS", "Descripcion CUPS", "DescripcionHC"}) {
//                    Class[] types = new Class[]{
//                        entidades.ConfigCups.class,
//                        java.lang.String.class,
//                        java.lang.String.class,
//                        java.lang.String.class
    
    
    
    
    
    
    
    
    private void jLabel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseMoved
        jLabel2.setBackground(new java.awt.Color(205,230,254));
    }//GEN-LAST:event_jLabel2MouseMoved

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        jLabel2.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel2MouseExited

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==1){
            if(jTable1.getSelectedRow()>-1 & jTable1.columnAtPoint(evt.getPoint())==1){
                if(pop==null){
                    pop=new JPopupMenu();
                }else{
                    pop.removeAll();
                }               
                numericKeypad keypad= new numericKeypad(jTable1,jTable1.getSelectedRow(),pop,1);
                pop.add(keypad);
                pop.setVisible(true);
                pop.setLocation(evt.getLocationOnScreen().x+80,evt.getLocationOnScreen().y-1);
            }
        }
    }//GEN-LAST:event_jTable1MouseReleased

    private void jMenuItem1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseReleased
        if(jTable1.getSelectedRow()>-1){
            ModeloTabla.removeRow(jTable1.getSelectedRow());
        }
    }//GEN-LAST:event_jMenuItem1MouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea26;
    // End of variables declaration//GEN-END:variables

}
