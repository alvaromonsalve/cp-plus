/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.ListadoPacientes;

import atencionurgencia.AtencionUrgencia;
import entidades.InfoCamas;
import entidades.StaticCie10;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import jpa.InfoCamasJpaController;
import jpa.StaticCie10JpaController;
import tools.Funciones;

/**
 *
 * @author AlvaroVirtual
 */
public class fPacientesCamas extends javax.swing.JFrame {
    private DefaultTableModel modeloC;
    private DefaultTableModel modeloS;
    private EntityManagerFactory factory;
    
    /**
     * Creates new form fPacientesCamas
     */
    public fPacientesCamas() {
        initComponents();
        /*
         * codigo para pruebas 
         */       
        jTextPane1.setCaretPosition(0);//hacer esto cuando se asigne un valor al textpanel
        this.modelCamas();
        jTextPane2.setText(jTextPane2.getText().toUpperCase());
        jTextPane2.setCaretPosition(0);
        jTextPane3.setCaretPosition(0);//hacer esto cuando se asigne un valor al textpanel
        jTextPane4.setText(jTextPane4.getText().toUpperCase());
        jTextPane4.setCaretPosition(0);
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("images/icono.png"));
        return retValue;
    }
        
    private void closed(){
        AtencionUrgencia.panelindex.jButton3.setEnabled(true);
        this.dispose();
    }
        
    private DefaultTableModel getModelo(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"Imagen","Imagen"}){
        Class[] types = new Class []{
                javax.swing.JLabel.class,
                javax.swing.JLabel.class
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
    
    private void modelCamas(){
        modeloC = getModelo();
        modeloS = getModelo();
        jTable1.setDefaultRenderer(Object.class, new tools.IconCellRendererHasFocus());
        jTable2.setDefaultRenderer(Object.class, new tools.IconCellRendererHasFocus());
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setVisible(false);
        jTable2.getTableHeader().setVisible(false);
        jTable1.getTableHeader().setPreferredSize(new Dimension(-1,0));
        jTable2.getTableHeader().setPreferredSize(new Dimension(-1,0));
        jTable1.setModel(modeloC);
        jTable2.setModel(modeloS);
        jTable1.setRowHeight(75);
        jTable2.setRowHeight(75);
        Datos();
    }
    
    private void Datos(){
        factory = Persistence.createEntityManagerFactory("ClipaEJBPU",AtencionUrgencia.props);
        InfoCamasJpaController icjc = new InfoCamasJpaController(factory);

        List<InfoCamas> infocamasList = icjc.findInfoCamasEntities(0);
        if(infocamasList!=null){
            while(modeloC.getRowCount()>0){
                modeloC.removeRow(0);
            }
            int siteRegInTable = 0;
            for(int i=0;i<infocamasList.size();i++){
                if(infocamasList.get(i).getIdConfigCamas().getServicio().getDescripcion().equals("URGENCIAS")){
                    if(siteRegInTable==0){
                        Object dato[] = null;
                        modeloC.addRow(dato);
                        pacientesCamasOfTable(infocamasList.get(i),modeloC.getRowCount()-1,siteRegInTable);

                        siteRegInTable=1;
                    }else{
                        pacientesCamasOfTable(infocamasList.get(i),modeloC.getRowCount()-1,siteRegInTable);
                        siteRegInTable=0;
                    }
                    
                }
//                modeloC.addRow(dato);
//                StaticCie10 cie10 = scjc.findStaticCie10( infocamasList.get(i).getIdInfoHistoriac().getDiagnostico());
//                StaticCie10 cie102 = scjc.findStaticCie10( infocamasList.get(i).getIdInfoHistoriac().getDiagnostico2());
//                StaticCie10 cie103 = scjc.findStaticCie10( infocamasList.get(i).getIdInfoHistoriac().getDiagnostico3());
//                StaticCie10 cie104 = scjc.findStaticCie10( infocamasList.get(i).getIdInfoHistoriac().getDiagnostico4());
//                StaticCie10 cie105 = scjc.findStaticCie10( infocamasList.get(i).getIdInfoHistoriac().getDiagnostico5());
//                
//                modeloC.setValueAt(getModImagen(infocamasList.get(i).getIdConfigCamas().getDescripcion()
//                        , infocamasList.get(i).getIdInfoHistoriac().getIdInfoAdmision().getIdDatosPersonales().getNombre1()
//                                +" "+infocamasList.get(i).getIdInfoHistoriac().getIdInfoAdmision().getIdDatosPersonales().getApellido1()
//                        ,"["+cie10.getCodigo()+"]-["+cie102.getCodigo()+"]-["+cie103.getCodigo()+"]-["+cie104.getCodigo()+"]-["+
//                                cie105.getCodigo()+"]"
//                        , 0), 0, 0);
//                i=1;
//         
//                cie10 = scjc.findStaticCie10( infocamasList.get(i).getIdInfoHistoriac().getDiagnostico());
//                cie102 = scjc.findStaticCie10( infocamasList.get(i).getIdInfoHistoriac().getDiagnostico2());
//                cie103 = scjc.findStaticCie10( infocamasList.get(i).getIdInfoHistoriac().getDiagnostico3());
//                cie104 = scjc.findStaticCie10( infocamasList.get(i).getIdInfoHistoriac().getDiagnostico4());
//                cie105 = scjc.findStaticCie10( infocamasList.get(i).getIdInfoHistoriac().getDiagnostico5());
//                modeloC.setValueAt(getModImagen(infocamasList.get(i).getIdConfigCamas().getDescripcion()
//                        , infocamasList.get(i).getIdInfoHistoriac().getIdInfoAdmision().getIdDatosPersonales().getNombre1()
//                                +" "+infocamasList.get(i).getIdInfoHistoriac().getIdInfoAdmision().getIdDatosPersonales().getApellido1()
//                        ,"["+cie10.getCodigo()+"]-["+cie102.getCodigo()+"]-["+cie103.getCodigo()+"]-["+cie104.getCodigo()+"]-["+
//                                cie105.getCodigo()+"]"
//                        , 0), 0, 1);                
            }
            
            
        }     
//        modeloS.addRow(dato);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 0, 0);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 0, 1);
//        modeloS.addRow(dato);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 1, 0);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 1, 1);
//        modeloS.addRow(dato);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 2, 0);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 2, 1);
//        modeloS.addRow(dato);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 3, 0);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 3, 1);
//        modeloS.addRow(dato);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 4, 0);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 4, 1);
//        modeloS.addRow(dato);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 5, 0);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 5, 1);
//        modeloS.addRow(dato);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 6, 0);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 6, 1);
//        modeloS.addRow(dato);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 7, 0);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 7, 1);
//        modeloS.addRow(dato);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 8, 0);
//        modeloS.setValueAt(getModImagen("nombre", "paciente", "diag",1), 8, 1);
    }
    
    private void pacientesCamasOfTable(InfoCamas ic, int rows,int siteTable){

        StaticCie10JpaController scjc = new StaticCie10JpaController(factory);
        StaticCie10 cie10 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico());
        StaticCie10 cie102 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico2());
        StaticCie10 cie103 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico3());
        StaticCie10 cie104 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico4());
        StaticCie10 cie105 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico5());
      
        modeloC.setValueAt(getModImagen(ic.getIdConfigCamas().getDescripcion()
                , ic.getIdInfoHistoriac().getIdInfoAdmision().getIdDatosPersonales().getNombre1()
                +" "+ic.getIdInfoHistoriac().getIdInfoAdmision().getIdDatosPersonales().getApellido1()
                ,"["+cie10.getCodigo()+"]-["+cie102.getCodigo()+"]-["+cie103.getCodigo()+"]-["+cie104.getCodigo()+"]-["+
                cie105.getCodigo()+"]"
                , 0), rows, siteTable);
    }
    
    
    
    //
     private JTable getModImagen(String nombreC, String paciente, String Diag, int tipo){
         JTable tabla = new JTable();
         ImageIcon icon;
         if(0==tipo){
             icon = new ImageIcon(ClassLoader.getSystemResource("images/medical-bed-icon.png"));
         }else{
             icon = new ImageIcon(ClassLoader.getSystemResource("images/front_row 75x75.png"));
         }
         
         DefaultTableModel modeloDesc;
         Object dato[] = null;
         try {
                modeloDesc = new DefaultTableModel(
                null, new String [] {"image","Datos"}){
                Class[] types = new Class [] {
                     javax.swing.JLabel.class,
                     javax.swing.JTable.class
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
                tabla.setModel(modeloDesc);
                tabla.getTableHeader().setReorderingAllowed(false);
                tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                Funciones.setSizeColumnas(tabla, new int[]{0}, new int[]{75});
                tabla.setShowGrid(false);
                tabla.getTableHeader().setVisible(false);
                tabla.getTableHeader().setPreferredSize(new Dimension(-1,0));
                tabla.setDefaultRenderer(Object.class, new tools.IconCellRenderer());
                tabla.setRowHeight(75);
                modeloDesc.addRow(dato);
                modeloDesc.setValueAt(new JLabel(icon), 0, 0);    
                modeloDesc.setValueAt(getModDescripcion(nombreC, paciente, Diag), 0, 1); 
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"getModImagen "+this.getName()+" - "+e.getMessage());
            }
            return tabla;
        }
    
    
    private JTable getModDescripcion(String nombreC, String paciente, String Diag){
         JTable tabla = new JTable(); 
         DefaultTableModel modeloDesc;
         Object dato[] = null;
         try {
                modeloDesc = new DefaultTableModel(
                null, new String [] {"nombre"}){
                Class[] types = new Class [] {
                     javax.swing.JLabel.class
                };
                boolean[] canEdit = new boolean [] {
                false
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
                tabla.setModel(modeloDesc);
                tabla.getTableHeader().setReorderingAllowed(false);
                tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                tabla.setShowGrid(false);
                tabla.getTableHeader().setVisible(false);
                tabla.getTableHeader().setPreferredSize(new Dimension(-1,0));
                tabla.setDefaultRenderer(Object.class, new tools.IconCellRenderer());
                tabla.setRowHeight(25);
                modeloDesc.addRow(dato);
                modeloDesc.setValueAt(new JLabel(nombreC), 0, 0);
                modeloDesc.addRow(dato);
                modeloDesc.setValueAt(new JLabel(paciente), 1, 0);
                modeloDesc.addRow(dato);
                modeloDesc.setValueAt(new JLabel(Diag), 2, 0);
            } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"getModDescripcion "+this.getName()+" - "+e.getMessage());
            }
            return tabla;
        }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane4 = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Listado de Camas");
        setFocusable(false);
        setIconImage(getIconImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(840, 540));
        jPanel1.setMinimumSize(new java.awt.Dimension(840, 540));
        jPanel1.setPreferredSize(new java.awt.Dimension(840, 540));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bed32.png"))); // NOI18N
        jLabel50.setText("Listado de Camas");

        jTabbedPane1.setFocusable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles del Paciente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
        jPanel4.setOpaque(false);

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 102, 255));
        jTextField1.setText("1038109517");
        jTextField1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField1.setOpaque(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel9.setText("Documento");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel10.setText("Nombre");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel11.setText("Edad");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel12.setText("Diagnostico Principal");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel13.setText("Enfermedad Actual");

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(0, 102, 255));
        jTextField4.setText("A000 - B000 - C000 - D000 - E000");
        jTextField4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField4.setOpaque(false);

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(0, 102, 255));
        jTextField3.setText("15 AÑOS");
        jTextField3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField3.setOpaque(false);

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(0, 102, 255));
        jTextField2.setText("RUFINO DEL CRISTO GONZALES DE LOS ROSALES");
        jTextField2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField2.setOpaque(false);

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jTextPane1.setForeground(new java.awt.Color(0, 102, 255));
        jTextPane1.setText("[Y518] EFECTOS ADVERSOS DE AGENTES BLOQUEADORES NEURO-ADRENERGICOS QUE ACTUAN CENTRALMENTE, NO CLASIFICADOS EN OTRA PARTE\n\n[Y518] EFECTOS ADVERSOS DE AGENTES BLOQUEADORES NEURO-ADRENERGICOS QUE ACTUAN CENTRALMENTE, NO CLASIFICADOS EN OTRA PARTE\n\n[Y518] EFECTOS ADVERSOS DE AGENTES BLOQUEADORES NEURO-ADRENERGICOS QUE ACTUAN CENTRALMENTE, NO CLASIFICADOS EN OTRA PARTE\n\n[Y518] EFECTOS ADVERSOS DE AGENTES BLOQUEADORES NEURO-ADRENERGICOS QUE ACTUAN CENTRALMENTE, NO CLASIFICADOS EN OTRA PARTE\n\n[Y518] EFECTOS ADVERSOS DE AGENTES BLOQUEADORES NEURO-ADRENERGICOS QUE ACTUAN CENTRALMENTE, NO CLASIFICADOS EN OTRA PARTE");
        jTextPane1.setFocusable(false);
        jScrollPane2.setViewportView(jTextPane1);

        jTextPane2.setEditable(false);
        jTextPane2.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jTextPane2.setForeground(new java.awt.Color(0, 102, 255));
        jTextPane2.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed quis tortor sit amet tellus tristique placerat. Vestibulum imperdiet lectus et magna blandit tincidunt. Proin erat felis, porta sed blandit sed, ultricies eget neque. Suspendisse porta eu risus vitae ultrices. Nulla pellentesque tincidunt libero ac vestibulum. Ut sollicitudin lacus vitae rhoncus auctor. Nunc sapien metus, suscipit a urna et, dictum placerat sem. Praesent nulla tortor, gravida id risus ut, blandit egestas lorem. Nulla scelerisque sapien sed orci cursus, nec auctor justo pulvinar. Aenean euismod lacinia congue. Suspendisse gravida libero quis commodo ultricies.");
        jTextPane2.setFocusable(false);
        jScrollPane3.setViewportView(jTextPane2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(306, 306, 306))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField1)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)))
                                .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Camas", new javax.swing.ImageIcon(getClass().getResource("/images/bed32.png")), jPanel2); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setFocusable(false);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles del Paciente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
        jPanel5.setOpaque(false);

        jTextField5.setEditable(false);
        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(0, 102, 255));
        jTextField5.setText("1038109517");
        jTextField5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField5.setOpaque(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel14.setText("Documento");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel15.setText("Nombre");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel16.setText("Edad");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel17.setText("Diagnostico Principal");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel18.setText("Enfermedad Actual");

        jTextField6.setEditable(false);
        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(0, 102, 255));
        jTextField6.setText("A000 - B000 - C000 - D000 - E000");
        jTextField6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField6.setOpaque(false);

        jTextField7.setEditable(false);
        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(0, 102, 255));
        jTextField7.setText("15 AÑOS");
        jTextField7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField7.setOpaque(false);

        jTextField8.setEditable(false);
        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(0, 102, 255));
        jTextField8.setText("RUFINO DEL CRISTO GONZALES DE LOS ROSALES");
        jTextField8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField8.setOpaque(false);

        jTextPane3.setEditable(false);
        jTextPane3.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jTextPane3.setForeground(new java.awt.Color(0, 102, 255));
        jTextPane3.setText("[Y518] EFECTOS ADVERSOS DE AGENTES BLOQUEADORES NEURO-ADRENERGICOS QUE ACTUAN CENTRALMENTE, NO CLASIFICADOS EN OTRA PARTE\n\n[Y518] EFECTOS ADVERSOS DE AGENTES BLOQUEADORES NEURO-ADRENERGICOS QUE ACTUAN CENTRALMENTE, NO CLASIFICADOS EN OTRA PARTE\n\n[Y518] EFECTOS ADVERSOS DE AGENTES BLOQUEADORES NEURO-ADRENERGICOS QUE ACTUAN CENTRALMENTE, NO CLASIFICADOS EN OTRA PARTE\n\n[Y518] EFECTOS ADVERSOS DE AGENTES BLOQUEADORES NEURO-ADRENERGICOS QUE ACTUAN CENTRALMENTE, NO CLASIFICADOS EN OTRA PARTE\n\n[Y518] EFECTOS ADVERSOS DE AGENTES BLOQUEADORES NEURO-ADRENERGICOS QUE ACTUAN CENTRALMENTE, NO CLASIFICADOS EN OTRA PARTE");
        jTextPane3.setFocusable(false);
        jScrollPane4.setViewportView(jTextPane3);

        jTextPane4.setEditable(false);
        jTextPane4.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jTextPane4.setForeground(new java.awt.Color(0, 102, 255));
        jTextPane4.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed quis tortor sit amet tellus tristique placerat. Vestibulum imperdiet lectus et magna blandit tincidunt. Proin erat felis, porta sed blandit sed, ultricies eget neque. Suspendisse porta eu risus vitae ultrices. Nulla pellentesque tincidunt libero ac vestibulum. Ut sollicitudin lacus vitae rhoncus auctor. Nunc sapien metus, suscipit a urna et, dictum placerat sem. Praesent nulla tortor, gravida id risus ut, blandit egestas lorem. Nulla scelerisque sapien sed orci cursus, nec auctor justo pulvinar. Aenean euismod lacinia congue. Suspendisse gravida libero quis commodo ultricies.");
        jTextPane4.setFocusable(false);
        jScrollPane5.setViewportView(jTextPane4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(306, 306, 306))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField5)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)))
                                .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5)
                .addContainerGap())
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane6.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sillas", new javax.swing.ImageIcon(getClass().getResource("/images/front_row.png")), jPanel3); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel50)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.closed();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JTextPane jTextPane4;
    // End of variables declaration//GEN-END:variables
}
