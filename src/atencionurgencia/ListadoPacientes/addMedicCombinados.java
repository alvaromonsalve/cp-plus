/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.ListadoPacientes;

import atencionurgencia.AtencionUrgencia;
import entidades.SumSuministro;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import jpa.InvSumGeneralJpaController;
import jpa.SumSuministroJpaController;
import tools.Funciones;
import tools.convertores;


/**
 *
 * @author Alvaro Monsalve
 */
public class addMedicCombinados extends javax.swing.JDialog {
    private DefaultTableModel ModeloTabla, MTMezcla;
    private Object dato[] = null;
    private SumSuministroJpaController sumSuministroJPA=null;
    private InvSumGeneralJpaController invSumGeneralJPA=null;
    private EntityManagerFactory factory;
    private SumSuministro suminis=null;
    public int cerrado = 0;
    
    /**
     * Creates new form addMedicamentos
     */
    public addMedicCombinados(java.awt.Frame parent, boolean modal) {
        super(parent,modal);
        initComponents();
        setCargaTabla();        
        setCargaTablaMezcla();
    }
    
    private DefaultTableModel getModelo(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"Medicamento","PrinActivo","Concentracion","Via Administración","POS","existencias"}){
        Class[] types = new Class []{
            entidades.SumSuministro.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.Boolean.class,
            java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
        false,false,false,false,false,false
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
    
        public DefaultTableModel getModeloMezcla(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"Medicamento en mezcla","Dosis_n","id_Dosis","solucion para diluir"}){
        Class[] types = new Class []{
            entidades.SumSuministro.class,
            java.lang.Float.class,
            java.lang.Object.class,
            java.lang.Boolean.class
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
        Funciones.setOcultarColumnas(jTable1,new int[]{1,4,5});
        jTable1.setDefaultRenderer(Object.class, new tools.ColorTableCellRendererMedicamentos());
        Funciones.setSizeColumnas(jTable1, new int[]{0,2}, new int[]{400,150});
    }
    
    private void setCargaTablaMezcla(){
        MTMezcla = getModeloMezcla();
        jTable2.setModel(MTMezcla);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Funciones.setOcultarColumnas(jTable2,new int[]{1,2,3});
    }
    
    private void setDatosTabla() {
        if(invSumGeneralJPA==null || sumSuministroJPA==null){
            factory = Persistence.createEntityManagerFactory("ClipaEJBPU",AtencionUrgencia.props);
            invSumGeneralJPA=new InvSumGeneralJpaController(factory);
            sumSuministroJPA=new SumSuministroJpaController(factory);
        }
        while(ModeloTabla.getRowCount()>0){
            ModeloTabla.removeRow(0);
        }
        List<SumSuministro> listSumSuministro = sumSuministroJPA.ListfindFiltro(jTextField15.getText().toUpperCase());
        for(int i=0;i<listSumSuministro .size();i++){
            ModeloTabla.addRow(dato);
            ModeloTabla.setValueAt(listSumSuministro.get(i), i, 0);
            ModeloTabla.setValueAt(listSumSuministro.get(i).getIdPricipioactivo().getNombre(), i, 1);
            ModeloTabla.setValueAt(listSumSuministro.get(i).getConcentracion(), i, 2);
            ModeloTabla.setValueAt(listSumSuministro.get(i).getViaadministracion(), i, 3);
            ModeloTabla.setValueAt(listSumSuministro.get(i).getPos(), i, 4);
            if(invSumGeneralJPA.CountExistenciasInv(listSumSuministro.get(i))>0){
                ModeloTabla.setValueAt("EXISTE", i, 5);
            }else{
                ModeloTabla.setValueAt("NO EXISTE", i, 5);
            }
        } 
    }
    
    private void setMTMezcla(java.awt.Component c){
        javax.swing.JPopupMenu popup = (javax.swing.JPopupMenu)c.getParent();
        javax.swing.JTable table= (javax.swing.JTable)popup.getInvoker();
        SumSuministro ss = (SumSuministro)table.getValueAt(table.getSelectedRow(), 0);
        
        if(MTMezcla.getRowCount()==0){
            int dil = JOptionPane.showConfirmDialog(this, "El primer medicamento será tomado como solución para diluir/medicamento pral.\n¿Desea Continuar?"
                    , "Mensaje de Confirmacion", JOptionPane.YES_NO_OPTION);
            if(dil==0){//0 = si
                MTMezcla.addRow(dato);
                MTMezcla.setValueAt(ss, 0, 0);
                MTMezcla.setValueAt(true, 0, 3);
            }
        }else{
            boolean exist = false;
            for(int i=0;i<MTMezcla.getRowCount();i++){
                if(MTMezcla.getValueAt(i, 0)==ss){
                    exist=true;
                    JOptionPane.showMessageDialog(this,  "El Principio Activo ya está en la mezcla");
                    break;
                }
            }
            if(!exist){
                MTMezcla.addRow(dato);
                MTMezcla.setValueAt(ss, MTMezcla.getRowCount()-1, 0);
                MTMezcla.setValueAt(false, MTMezcla.getRowCount()-1, 3);                
            }
        }
    }
    
    private String returnHtml(String var, String var2){
        return "<p style=\"margin-top: 0\"<strong>"+var+"</strong> "+var2+"</p>";
    }
    
    public void editPosologia(DefaultTableModel model,String admin,String via){
        for(int i=0;i<model.getRowCount();i++){
            MTMezcla.addRow(dato);
            MTMezcla.setValueAt(model.getValueAt(i, 0), i, 0);
            MTMezcla.setValueAt(model.getValueAt(i, 1), i, 1);
            MTMezcla.setValueAt(model.getValueAt(i, 2), i, 2);
            MTMezcla.setValueAt(model.getValueAt(i, 3), i, 3);
        }
//        jTextField15.setEnabled(false);
        jTextArea25.setText(admin);
        jComboBox5.setSelectedItem(via);
//        jButton1.setEnabled(false);
    }
    
    private boolean validaListaMezcla(){
        boolean val=true;
        if(MTMezcla.getRowCount()>0){
            for(int i=0;i<MTMezcla.getRowCount();i++){
                if((MTMezcla.getValueAt(i, 1) == null) || (MTMezcla.getValueAt(i, 2) == null)){
                    JOptionPane.showMessageDialog(this, "El medicamento ["+(SumSuministro) MTMezcla.getValueAt(i, 0) +"] no tiene dosificacion para la mezcla");
                    val=false;
                    break;
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, "No ha seleccionado medicamentos para la mezcla");
            val=false;
        }

        return val;
    }
    
    private boolean validaMezcla(){
        boolean val=true;
        if(jComboBox5.getSelectedIndex()==-1 || jTextArea25.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "A la mezcla le faltan datos de administracion del medicamento");
            val=false;
        }
        return val;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel5 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel42 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel35 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        buttonSeven14 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven13 = new org.edisoncor.gui.button.ButtonSeven();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea25 = new javax.swing.JTextArea();
        jLabel40 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        jPopupMenu1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_16x16.png"))); // NOI18N
        jMenuItem1.setText("Agregar a Mezcla");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete16x16.png"))); // NOI18N
        jMenuItem2.setText("Eliminar de la mezcla");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(720, 540));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setMaximumSize(new java.awt.Dimension(540, 360));
        jPanel5.setMinimumSize(new java.awt.Dimension(540, 360));
        jPanel5.setPreferredSize(new java.awt.Dimension(540, 360));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/medical_pot_pills.png"))); // NOI18N
        jLabel50.setText("Listado de Medicamentos Para Mezclas");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setOpaque(false);
        jTable1.setShowHorizontalLines(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTable1MouseMoved(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel42.setForeground(new java.awt.Color(0, 51, 255));
        jLabel42.setText("Buscar");

        jTextField15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(0, 102, 255));
        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField15KeyReleased(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton1.setForeground(new java.awt.Color(85, 185, 251));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/001_38.png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.setFocusable(false);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setOpaque(false);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setOpaque(false);
        jTable2.setShowHorizontalLines(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable2MouseReleased(evt);
            }
        });
        jTable2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTable2MouseMoved(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable2KeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[G] Gramo", "[GT] Gotas", "[CC] Centimetros Cubicos", "[MCG] Microgramos", "[MCL] Microlitros", "[MEQ] Miliequivalentes", "[MG] Miligramos", "[ML] Mililitros", "[Puff] Inhalación", "[U] Unidades", "[Tsp] Cucharadita", "[TP] Cucharada", "[L] Litros" }));
        jComboBox4.setSelectedIndex(-1);
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel35.setForeground(new java.awt.Color(0, 51, 255));
        jLabel35.setText("Dosis:");

        jLabel43.setForeground(new java.awt.Color(255, 0, 0));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel43.setText("<html> <p> Digite primero la catidad de la dosificacion y luego seleccione la unidad de medida en la lista desplegable </p> </html>");
        jLabel43.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel43.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox4, 0, 1, Short.MAX_VALUE))
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 24, Short.MAX_VALUE)))
                .addContainerGap())
        );

        buttonSeven14.setForeground(new java.awt.Color(0, 0, 255));
        buttonSeven14.setText("Cancelar");
        buttonSeven14.setColorBrillo(new java.awt.Color(255, 255, 255));
        buttonSeven14.setColorDeSombra(new java.awt.Color(255, 255, 255));
        buttonSeven14.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        buttonSeven14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven14ActionPerformed(evt);
            }
        });

        buttonSeven13.setForeground(new java.awt.Color(0, 0, 255));
        buttonSeven13.setText("Aceptar");
        buttonSeven13.setColorBrillo(new java.awt.Color(255, 255, 255));
        buttonSeven13.setColorDeSombra(new java.awt.Color(255, 255, 255));
        buttonSeven13.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        buttonSeven13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven13ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);

        jScrollPane25.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane25.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane25.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea25.setColumns(20);
        jTextArea25.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jTextArea25.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea25.setLineWrap(true);
        jTextArea25.setRows(3);
        jTextArea25.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea25.setMinimumSize(new java.awt.Dimension(164, 40));
        jScrollPane25.setViewportView(jTextArea25);

        jLabel40.setForeground(new java.awt.Color(0, 51, 255));
        jLabel40.setText("Administracion");

        jLabel38.setForeground(new java.awt.Color(0, 51, 255));
        jLabel38.setText("Via:");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[IM] Intramucsular", "[IV] Intravenosa", "[VB] Vía  Bucal (enjuagues, aplicación tópica)", "[Vic] Vía Intracavernosa", "[Vinh] Vía Inhalatoria", "[VO] Vía Oral", "[Vof] Vía Oftálmica", "[Vot] Vía Otica", "[VP] Vía(s) Parental(es)", "[VR] Vía Rectal", "[VSc] Vía Subcutánea", "[VSl] Vía Sublingual", "[VT] Vía Tópica", "[VTd] Vía Transdérmica", "[VV] Vía Vaginal (o Vulvar)" }));
        jComboBox5.setSelectedIndex(-1);
        jComboBox5.setFocusable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jComboBox5, 0, 244, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("POSOLOGIA DE LA MEZCLA", jPanel2);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setOpaque(false);

        jTextPane1.setEditable(false);
        jTextPane1.setContentType("text/html"); // NOI18N
        jTextPane1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextPane1.setForeground(new java.awt.Color(0, 102, 255));
        jScrollPane2.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("DESCRIPCION", jPanel1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(buttonSeven14, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSeven13, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane2)
                    .addComponent(jTabbedPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSeven14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            this.setDatosTabla();
        }
    }//GEN-LAST:event_jTextField15KeyReleased

    private void buttonSeven13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven13ActionPerformed
        if(validaListaMezcla()){
            if(validaMezcla()){ 
                AtencionUrgencia.panelindex.hc.pMedic.addMezcla(MTMezcla,jComboBox5.getSelectedItem().toString(), jTextArea25.getText().toUpperCase());
                this.dispose();
            }
        }
    }//GEN-LAST:event_buttonSeven13ActionPerformed

    private void buttonSeven14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven14ActionPerformed
        while(ModeloTabla.getRowCount()>0){
            ModeloTabla.removeRow(0);
        }
        while(MTMezcla.getRowCount()>0){
            MTMezcla.removeRow(0);
        }
        jTextField15.setText(null);
        jTextArea25.setText(null);
        jTextPane1.setText(null);
        jTextField1.setText("");
        jComboBox4.setSelectedIndex(-1);
        jComboBox5.setSelectedIndex(-1);
    }//GEN-LAST:event_buttonSeven14ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setDatosTabla();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        jTextField1.setText(Funciones.FormatDecimal(jTextField1.getText()));
        jTextField1.setText(jTextField1.getText().replace(",", "."));
        if(!jTextField1.getText().isEmpty() && MTMezcla.getRowCount()>0 && jTable2.getSelectedRow()>-1)
            MTMezcla.setValueAt(Float.parseFloat(jTextField1.getText().replace(",", ".")), jTable2.getSelectedRow(), 1);
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        javax.swing.JTable source = (javax.swing.JTable)evt.getSource();    
        int row = source.rowAtPoint(evt.getPoint());
            SumSuministro sum = ((SumSuministro)jTable1.getValueAt(row, 0));
            String ePOS="<FONT COLOR=red SIZE=2><p style=\"margin-top: 0\"<strong> MEDICAMENTO NO POS </strong></p></FONT>";
            if(sum.getPos()){
                ePOS="<FONT SIZE=2><p style=\"margin-top: 0\"<strong> MEDICAMENTO POS </strong></p></FONT>";
            }
            jTextPane1.setText("<html><FONT SIZE=2>"+returnHtml("NOMBRE:",sum.getSuministro().toUpperCase())+
                    returnHtml("PRINCIPIO ACTIVO:",sum.getIdPricipioactivo().getNombre().toUpperCase())+
                    returnHtml("CONCENTRACION:",sum.getConcentracion().toUpperCase())+
                    returnHtml("PRESENTACION FARMACEUTICA:",sum.getIdPresentacionfarmaceutica().getNombre().toUpperCase())+
                    returnHtml("DOSIS:",sum.getIdPresentacionfarmaceutica().getDosis().toUpperCase())+
                    returnHtml("VIA ADMINISTRACION:",sum.getViaadministracion().toUpperCase())+
                    "</FONT>"+ePOS+"<FONT SIZE=2>"+ returnHtml("REGISTRO INVIMA:",sum.getRegistroInvima()) +
                    returnHtml("PRESENTACION COMERCIAL:",sum.getPresentacioncomercial())+"</html>");
            jTextPane1.setCaretPosition(0);
        //mostrar jpopupmenu
        if(evt.isPopupTrigger()){
            int column = source.columnAtPoint(evt.getPoint());
            if(!source.isRowSelected(row))
                source.changeSelection(row, column, false,false);
            jPopupMenu1.show(evt.getComponent(),evt.getX(),evt.getY());
        }
    }//GEN-LAST:event_jTable1MouseReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        java.awt.Component c = (java.awt.Component)evt.getSource();
        this.setMTMezcla(c);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        if((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)){

            int rowIndex = jTable1.getSelectedRow();
            SumSuministro sum = ((SumSuministro)jTable1.getValueAt(rowIndex, 0));
            String ePOS="<FONT COLOR=red SIZE=2><p style=\"margin-top: 0\"<strong> MEDICAMENTO NO POS </strong></p></FONT>";
            if(sum.getPos()){
                ePOS="<FONT SIZE=2><p style=\"margin-top: 0\"<strong> MEDICAMENTO POS </strong></p></FONT>";
            }
            jTextPane1.setText("<html><FONT SIZE=2>"+returnHtml("NOMBRE:",sum.getSuministro().toUpperCase())+
                returnHtml("PRINCIPIO ACTIVO:",sum.getIdPricipioactivo().getNombre().toUpperCase())+
                returnHtml("CONCENTRACION:",sum.getConcentracion().toUpperCase())+
                returnHtml("PRESENTACION FARMACEUTICA:",sum.getIdPresentacionfarmaceutica().getNombre().toUpperCase())+
                returnHtml("DOSIS:",sum.getIdPresentacionfarmaceutica().getDosis().toUpperCase())+
                returnHtml("VIA ADMINISTRACION:",sum.getViaadministracion().toUpperCase())+
                "</FONT>"+ePOS+"<FONT SIZE=2>"+ returnHtml("REGISTRO INVIMA:",sum.getRegistroInvima()) +
                returnHtml("PRESENTACION COMERCIAL:",sum.getPresentacioncomercial())+"</html>");
            jTextPane1.setCaretPosition(0);
        }
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved
        int rowIndex = jTable1.rowAtPoint(evt.getPoint());
        int colunmIndex = jTable1.columnAtPoint(evt.getPoint());
        jTable1.setToolTipText(convertores.getTextToHtml(jTable1.getValueAt(rowIndex, colunmIndex).toString()));
    }//GEN-LAST:event_jTable1MouseMoved

    private void jTable2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyReleased
        if((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)){
            int rowIndex = jTable2.getSelectedRow();
            SumSuministro sum = ((SumSuministro)jTable2.getValueAt(rowIndex, 0));
            String ePOS="<FONT COLOR=red SIZE=2><p style=\"margin-top: 0\"<strong> MEDICAMENTO NO POS </strong></p></FONT>";
            if(sum.getPos()){
                ePOS="<FONT SIZE=2><p style=\"margin-top: 0\"<strong> MEDICAMENTO POS </strong></p></FONT>";
            }
            jTextPane1.setText("<html><FONT SIZE=2>"+returnHtml("NOMBRE:",sum.getSuministro().toUpperCase())+
                returnHtml("PRINCIPIO ACTIVO:",sum.getIdPricipioactivo().getNombre().toUpperCase())+
                returnHtml("CONCENTRACION:",sum.getConcentracion().toUpperCase())+
                returnHtml("PRESENTACION FARMACEUTICA:",sum.getIdPresentacionfarmaceutica().getNombre().toUpperCase())+
                returnHtml("DOSIS:",sum.getIdPresentacionfarmaceutica().getDosis().toUpperCase())+
                returnHtml("VIA ADMINISTRACION:",sum.getViaadministracion().toUpperCase())+
                "</FONT>"+ePOS+"<FONT SIZE=2>"+ returnHtml("REGISTRO INVIMA:",sum.getRegistroInvima()) +
                returnHtml("PRESENTACION COMERCIAL:",sum.getPresentacioncomercial())+"</html>");
            jTextPane1.setCaretPosition(0);
            jTextField1.setText(null);
            jComboBox4.setSelectedIndex(-1);
            if(MTMezcla.getValueAt(rowIndex, 1)!=null)
                jTextField1.setText(String.valueOf(MTMezcla.getValueAt(rowIndex, 1)));
            if(MTMezcla.getValueAt(rowIndex, 2)!=null){
                jComboBox4.setSelectedItem(((Object)MTMezcla.getValueAt(rowIndex, 2)));

            }
        }
    }//GEN-LAST:event_jTable2KeyReleased

    private void jTable2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseReleased
        javax.swing.JTable source = (javax.swing.JTable)evt.getSource();    
        int row = source.rowAtPoint(evt.getPoint());
            SumSuministro sum = ((SumSuministro)jTable2.getValueAt(row, 0));
            String ePOS="<FONT COLOR=red SIZE=2><p style=\"margin-top: 0\"<strong> MEDICAMENTO NO POS </strong></p></FONT>";
            if(sum.getPos()){
                ePOS="<FONT SIZE=2><p style=\"margin-top: 0\"<strong> MEDICAMENTO POS </strong></p></FONT>";
            }
            jTextPane1.setText("<html><FONT SIZE=2>"+returnHtml("NOMBRE:",sum.getSuministro().toUpperCase())+
                    returnHtml("PRINCIPIO ACTIVO:",sum.getIdPricipioactivo().getNombre().toUpperCase())+
                    returnHtml("CONCENTRACION:",sum.getConcentracion().toUpperCase())+
                    returnHtml("PRESENTACION FARMACEUTICA:",sum.getIdPresentacionfarmaceutica().getNombre().toUpperCase())+
                    returnHtml("DOSIS:",sum.getIdPresentacionfarmaceutica().getDosis().toUpperCase())+
                    returnHtml("VIA ADMINISTRACION:",sum.getViaadministracion().toUpperCase())+
                    "</FONT>"+ePOS+"<FONT SIZE=2>"+ returnHtml("REGISTRO INVIMA:",sum.getRegistroInvima()) +
                    returnHtml("PRESENTACION COMERCIAL:",sum.getPresentacioncomercial())+"</html>");
            jTextPane1.setCaretPosition(0);
            jTextField1.setText(null);
            jComboBox4.setSelectedIndex(-1);
            if(MTMezcla.getValueAt(row, 1)!=null)
                jTextField1.setText(String.valueOf(MTMezcla.getValueAt(row, 1)));
            if(MTMezcla.getValueAt(row, 2)!=null)
                jComboBox4.setSelectedItem((Object)MTMezcla.getValueAt(row, 2));
        //mostrar jpopupmenu
        if(evt.isPopupTrigger()){
            int column = source.columnAtPoint(evt.getPoint());
            if(!source.isRowSelected(row))
                source.changeSelection(row, column, false,false);
            jPopupMenu2.show(evt.getComponent(),evt.getX(),evt.getY());
        }
    }//GEN-LAST:event_jTable2MouseReleased

    private void jTable2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseMoved
        javax.swing.JTable source = (javax.swing.JTable)evt.getSource();
        int column = source.columnAtPoint(evt.getPoint());
        int row = source.rowAtPoint(evt.getPoint());
        jTable2.setToolTipText(convertores.getTextToHtml(source.getValueAt(row, column).toString()));
    }//GEN-LAST:event_jTable2MouseMoved

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
       
        if(MTMezcla.getRowCount()>0 && jTable2.getSelectedRow()>-1){
            javax.swing.JComboBox source =(javax.swing.JComboBox)evt.getSource();
            if(source.getSelectedIndex()>-1){
                jTextField1.setText(jTextField1.getText().replace(",", "."));
                if(!jTextField1.getText().isEmpty())
                    MTMezcla.setValueAt(Float.parseFloat(jTextField1.getText().replace(",", ".")), jTable2.getSelectedRow(), 1);
                MTMezcla.setValueAt(source.getSelectedItem(), jTable2.getSelectedRow(), 2);
            }
        }

    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        ((javax.swing.JTextField)evt.getSource()).selectAll();
    }//GEN-LAST:event_jTextField1FocusGained

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        java.awt.Component c = (java.awt.Component)evt.getSource();
        javax.swing.JPopupMenu popup = (javax.swing.JPopupMenu)c.getParent();
        javax.swing.JTable table= (javax.swing.JTable)popup.getInvoker();
        MTMezcla.removeRow(table.getSelectedRow());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrado=1;
    }//GEN-LAST:event_formWindowClosing
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonSeven buttonSeven13;
    private org.edisoncor.gui.button.ButtonSeven buttonSeven14;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea25;
    private javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField15;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
