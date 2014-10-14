/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.ListadoPacientes;

import atencionurgencia.AtencionUrgencia;
import atencionurgencia.DselectEspecialidad;
import atencionurgencia.evolucion.Evo;
import entidades.CmEspPprofesional;
import entidades.InfoCamas;
import entidades.InfoHistoriac;
import entidades.StaticCie10;
import entidades.StaticEspecialidades;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
import jpa.StaticEspecialidadesJpaController;
import tools.Funciones;

/**
 *
 * @author AlvaroVirtual
 */
public class fPacientesCamas extends javax.swing.JFrame {
    private DefaultTableModel modeloC;
    private DefaultTableModel modeloS;
    private final EntityManagerFactory factory;
    private Timer timer;
    private InfoHistoriac historiac =null;
    
    public fPacientesCamas(EntityManagerFactory factory) {
        initComponents();
        jLabel1.setVisible(false);  
        this.factory=factory;
    }
    
    public void inicio(){
        TimerTask timerListar = new TimerTask(){
        @Override
        public void run() {
                jLabel1.setVisible(true);
                initComponents2();
                jLabel1.setVisible(false);
            }
        };
        timer = new Timer();
        timer.schedule(timerListar, new Date());
    }

    @Override
    public Image getIconImage(){
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
        InfoCamasJpaController icjc = new InfoCamasJpaController(factory);

        List<InfoCamas> infocamasList = icjc.findInfoCamasEntities(1);
        if(infocamasList!=null){
            while(modeloC.getRowCount()>0){
                modeloC.removeRow(0);
            }
            int siteRegInTable = 0;
            int siteRegInTable2 = 0;
            for (InfoCamas infocamasList1 : infocamasList) {
                if (infocamasList1.getIdConfigCamas().getServicio().getDescripcion().equals("URGENCIAS") && infocamasList1.getIdConfigCamas().getId() != 1) {
                    if (siteRegInTable==0) {
                        Object dato[] = null;
                        modeloC.addRow(dato);
                        pacientesCamasOfTable(infocamasList1, modeloC.getRowCount()-1, siteRegInTable);
                        siteRegInTable=1;
                    } else {
                        pacientesCamasOfTable(infocamasList1, modeloC.getRowCount()-1, siteRegInTable);
                        siteRegInTable=0;
                    }
                } else if (infocamasList1.getIdConfigCamas().getServicio().getDescripcion().equals("URGENCIAS") && infocamasList1.getIdConfigCamas().getId() == 1) {
                    if (siteRegInTable2==0) {
                        Object dato[] = null;
                        modeloS.addRow(dato);
                        pacientesSillasOfTable(infocamasList1, modeloS.getRowCount()-1, siteRegInTable2);
                        siteRegInTable2=1;
                    } else {
                        pacientesSillasOfTable(infocamasList1, modeloS.getRowCount()-1, siteRegInTable2);
                        siteRegInTable2=0;
                    }
                }
            }
        }     
    }
    
    private void pacientesCamasOfTable(InfoCamas ic, int rows,int siteTable){
        StaticCie10JpaController scjc = new StaticCie10JpaController(factory);
        StaticCie10 cie10 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico());
        StaticCie10 cie102 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico2());
        StaticCie10 cie103 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico3());
        StaticCie10 cie104 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico4());
        StaticCie10 cie105 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico5());
        modeloC.setValueAt(getModImagen(ic.getIdInfoHistoriac(),ic.getIdConfigCamas().getDescripcion()
            , ic.getIdInfoHistoriac().getIdInfoAdmision().getIdDatosPersonales().getNombre1()
            +" "+ic.getIdInfoHistoriac().getIdInfoAdmision().getIdDatosPersonales().getApellido1()
            ,"["+cie10.getCodigo()+"]-["+cie102.getCodigo()+"]-["+cie103.getCodigo()+"]-["+cie104.getCodigo()+"]-["+
            cie105.getCodigo()+"]"
            , 0), rows, siteTable);
    }
    
        private void pacientesSillasOfTable(InfoCamas ic, int rows,int siteTable){
        StaticCie10JpaController scjc = new StaticCie10JpaController(factory);
        StaticCie10 cie10 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico());
        StaticCie10 cie102 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico2());
        StaticCie10 cie103 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico3());
        StaticCie10 cie104 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico4());
        StaticCie10 cie105 = scjc.findStaticCie10( ic.getIdInfoHistoriac().getDiagnostico5());
        modeloS.setValueAt(getModImagen(ic.getIdInfoHistoriac(),ic.getIdConfigCamas().getDescripcion()
            , ic.getIdInfoHistoriac().getIdInfoAdmision().getIdDatosPersonales().getNombre1()
            +" "+ic.getIdInfoHistoriac().getIdInfoAdmision().getIdDatosPersonales().getApellido1()
            ,"["+cie10.getCodigo()+"]-["+cie102.getCodigo()+"]-["+cie103.getCodigo()+"]-["+cie104.getCodigo()+"]-["+
            cie105.getCodigo()+"]"
            , 1), rows, siteTable);
    }

     private JTable getModImagen(InfoHistoriac hc, String nombreC, String paciente, String Diag, int tipo){
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
                null, new String [] {"InfoHistoriac","image","Datos"}){
                Class[] types = new Class [] {
                    InfoHistoriac.class,
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
                Funciones.setSizeColumnas(tabla, new int[]{1}, new int[]{75});
                Funciones.setOcultarColumnas(tabla, new int[]{0});
                tabla.setShowGrid(false);
                tabla.getTableHeader().setVisible(false);
                tabla.getTableHeader().setPreferredSize(new Dimension(-1,0));
                tabla.setDefaultRenderer(Object.class, new tools.IconCellRenderer());
                tabla.setRowHeight(75);
                modeloDesc.addRow(dato);
                modeloDesc.setValueAt(hc, 0, 0);  
                modeloDesc.setValueAt(new JLabel(icon), 0, 1);    
                modeloDesc.setValueAt(getModDescripcion(nombreC, paciente, Diag), 0, 2); 
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
    
        private void initComponents2() {
            jTextPane1.setCaretPosition(0);//hacer esto cuando se asigne un valor al textpanel
            this.modelCamas();
            jTextPane2.setText(jTextPane2.getText().toUpperCase());
            jTextPane2.setCaretPosition(0);
            jTextPane3.setCaretPosition(0);//hacer esto cuando se asigne un valor al textpanel
            jTextPane4.setText(jTextPane4.getText().toUpperCase());
            jTextPane4.setCaretPosition(0);
            timer.cancel();
        }
        
        private void selectTableCama(JTable table){
            int row = table.getSelectedRow();
            int colu = table.getSelectedColumn();
            if(table.getValueAt(row, colu) !=null){
                DefaultTableModel modeloC1 = (DefaultTableModel) table.getModel();
                DefaultTableModel modelo2 = (DefaultTableModel) ((JTable) modeloC1.getValueAt(row, colu)).getModel();
                InfoHistoriac ih =(InfoHistoriac) modelo2.getValueAt(0, 0);
                jTextField1.setText(ih.getIdInfoAdmision().getIdDatosPersonales().getNumDoc());
                jTextField3.setText(ih.getIdInfoAdmision().getEdad());
                jTextField2.setText(ih.getIdInfoAdmision().getIdDatosPersonales().getNombre1()+" "+
                        ih.getIdInfoAdmision().getIdDatosPersonales().getNombre2()+" "+
                        ih.getIdInfoAdmision().getIdDatosPersonales().getApellido1()+" "+
                        ih.getIdInfoAdmision().getIdDatosPersonales().getApellido2());
                DefaultTableModel modeloDesc1 = (DefaultTableModel) ((JTable)modelo2.getValueAt(0, 2)).getModel();
                jTextField4.setText(((JLabel)modeloDesc1.getValueAt(2, 0)).getText());
                StaticCie10JpaController scjc = new StaticCie10JpaController(factory);
                StaticCie10 cie10 = scjc.findStaticCie10( ih.getDiagnostico());
                StaticCie10 cie102 = scjc.findStaticCie10( ih.getDiagnostico2());
                StaticCie10 cie103 = scjc.findStaticCie10( ih.getDiagnostico3());
                StaticCie10 cie104 = scjc.findStaticCie10( ih.getDiagnostico4());
                StaticCie10 cie105 = scjc.findStaticCie10( ih.getDiagnostico5());
                String cie = "["+cie10.getCodigo()+"] "+cie10.getDescripcion()+"\n";
                cie = cie+"["+cie102.getCodigo()+"] "+cie102.getDescripcion()+"\n";
                cie = cie+"["+cie103.getCodigo()+"] "+cie103.getDescripcion()+"\n";
                cie = cie+"["+cie104.getCodigo()+"] "+cie104.getDescripcion()+"\n";
                cie = cie+"["+cie105.getCodigo()+"] "+cie105.getDescripcion();
                jTextPane1.setText(cie);
                jTextPane2.setText(ih.getEnfermedadActual());
                historiac = ih;
            }
        }
        
        private void selectTableSilla(JTable table){
            int row = table.getSelectedRow();
            int colu = table.getSelectedColumn();
            if(table.getValueAt(row, colu) !=null){
                DefaultTableModel modeloS1 = (DefaultTableModel) table.getModel();
                DefaultTableModel modelo2 = (DefaultTableModel) ((JTable) modeloS1.getValueAt(row, colu)).getModel();
                InfoHistoriac ih =(InfoHistoriac) modelo2.getValueAt(0, 0);
                jTextField5.setText(ih.getIdInfoAdmision().getIdDatosPersonales().getNumDoc());
                jTextField7.setText(ih.getIdInfoAdmision().getEdad());
                jTextField8.setText(ih.getIdInfoAdmision().getIdDatosPersonales().getNombre1()+" "+
                        ih.getIdInfoAdmision().getIdDatosPersonales().getNombre2()+" "+
                        ih.getIdInfoAdmision().getIdDatosPersonales().getApellido1()+" "+
                        ih.getIdInfoAdmision().getIdDatosPersonales().getApellido2());
                DefaultTableModel modeloDesc1 = (DefaultTableModel) ((JTable)modelo2.getValueAt(0, 2)).getModel();
                jTextField6.setText(((JLabel)modeloDesc1.getValueAt(2, 0)).getText());
                StaticCie10JpaController scjc = new StaticCie10JpaController(factory);
                StaticCie10 cie10 = scjc.findStaticCie10( ih.getDiagnostico());
                StaticCie10 cie102 = scjc.findStaticCie10( ih.getDiagnostico2());
                StaticCie10 cie103 = scjc.findStaticCie10( ih.getDiagnostico3());
                StaticCie10 cie104 = scjc.findStaticCie10( ih.getDiagnostico4());
                StaticCie10 cie105 = scjc.findStaticCie10( ih.getDiagnostico5());
                String cie = "["+cie10.getCodigo()+"] "+cie10.getDescripcion()+"\n";
                cie = cie+"["+cie102.getCodigo()+"] "+cie102.getDescripcion()+"\n";
                cie = cie+"["+cie103.getCodigo()+"] "+cie103.getDescripcion()+"\n";
                cie = cie+"["+cie104.getCodigo()+"] "+cie104.getDescripcion()+"\n";
                cie = cie+"["+cie105.getCodigo()+"] "+cie105.getDescripcion();
                jTextPane3.setText(cie);
                jTextPane4.setText(ih.getEnfermedadActual());
                historiac = ih;
            }
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
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

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
        jTextField4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField4.setOpaque(false);

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(0, 102, 255));
        jTextField3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField3.setOpaque(false);

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(0, 102, 255));
        jTextField2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField2.setOpaque(false);

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jTextPane1.setForeground(new java.awt.Color(0, 102, 255));
        jTextPane1.setFocusable(false);
        jScrollPane2.setViewportView(jTextPane1);

        jTextPane2.setEditable(false);
        jTextPane2.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jTextPane2.setForeground(new java.awt.Color(0, 102, 255));
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
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
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
        jTextField6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField6.setOpaque(false);

        jTextField7.setEditable(false);
        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(0, 102, 255));
        jTextField7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField7.setOpaque(false);

        jTextField8.setEditable(false);
        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(0, 102, 255));
        jTextField8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField8.setOpaque(false);

        jTextPane3.setEditable(false);
        jTextPane3.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jTextPane3.setForeground(new java.awt.Color(0, 102, 255));
        jTextPane3.setFocusable(false);
        jScrollPane4.setViewportView(jTextPane3);

        jTextPane4.setEditable(false);
        jTextPane4.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jTextPane4.setForeground(new java.awt.Color(0, 102, 255));
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
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable2MouseReleased(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable2KeyReleased(evt);
            }
        });
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
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sillas", new javax.swing.ImageIcon(getClass().getResource("/images/front_row.png")), jPanel3); // NOI18N

        jButton1.setText("Aceptar");
        jButton1.setDoubleBuffered(true);
        jButton1.setFocusPainted(false);
        jButton1.setFocusable(false);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ajax-loader.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel50)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.closed();
    }//GEN-LAST:event_formWindowClosing

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        selectTableCama((JTable)evt.getSource());
    }//GEN-LAST:event_jTable1MouseReleased

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
         if((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)){
             selectTableCama((JTable)evt.getSource());
         }
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseReleased
        selectTableSilla((JTable)evt.getSource());
    }//GEN-LAST:event_jTable2MouseReleased

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        if((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)){
             selectTableSilla((JTable)evt.getSource());
         }
    }//GEN-LAST:event_jTable2KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(historiac!=null && AtencionUrgencia.cep!=null){
            int especialidad=0;
            List<StaticEspecialidades> ses=new ArrayList<StaticEspecialidades>();
            StaticEspecialidades se=null;
            if(AtencionUrgencia.cep.getCmEspPprofesionalList().size()>1){
                for(CmEspPprofesional cep:AtencionUrgencia.cep.getCmEspPprofesionalList()){
                    if(cep.getIdEspecialidad().getId()!=45){
                        especialidad = especialidad+1;
                        ses.add(cep.getIdEspecialidad());
                    }
                }
            }else if(AtencionUrgencia.cep.getCmEspPprofesionalList().size()==1){
                if(AtencionUrgencia.cep.getCmEspPprofesionalList().get(0).getIdEspecialidad().getId()!=45){
                        se=AtencionUrgencia.cep.getCmEspPprofesionalList().get(0).getIdEspecialidad();
                 }
            }
            if(especialidad>0 && AtencionUrgencia.configUser.getIdPerfiles().getId()==3){
                DselectEspecialidad de = new DselectEspecialidad(null, true);
                    de.setEspecialidades(ses);
                    de.setLocationRelativeTo(null);
                    de.setVisible(true);
                    se = de.especialidades;
            }
            StaticEspecialidadesJpaController sejc = new StaticEspecialidadesJpaController(factory);
            AtencionUrgencia.panelindex.jpContainer.removeAll();
            AtencionUrgencia.panelindex.evo = new Evo();
            AtencionUrgencia.panelindex.evo.setBounds(0, 0, 764, 514);
            AtencionUrgencia.panelindex.jpContainer.add(AtencionUrgencia.panelindex.evo);
            AtencionUrgencia.panelindex.evo.setVisible(true);            
            if(AtencionUrgencia.configUser.getIdPerfiles().getId()==3){//ESPECIALISTA EN EL SERVICIO DE URGENCIAS
                AtencionUrgencia.panelindex.evo.tipoEvo=2;//2 es interconsulta  
                AtencionUrgencia.panelindex.evo.staticEspecialidades = se;
            }else{
                AtencionUrgencia.panelindex.evo.staticEspecialidades = sejc.findStaticEspecialidades(45);//medicina general
                AtencionUrgencia.panelindex.evo.tipoEvo=0;//0 es evolucion med general ronda                
            }                        
            AtencionUrgencia.panelindex.jpContainer.validate();
            AtencionUrgencia.panelindex.jpContainer.repaint();
            AtencionUrgencia.panelindex.evo.viewClinicHistory(historiac);
            AtencionUrgencia.panelindex.evo.DatosAntPersonales();//mostrar antecedentes personales
            closed();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
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
