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
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jpa.InvSumGeneralJpaController;
import jpa.SumSuministroJpaController;
import tools.Funciones;
import tools.Numero_a_Letra;
import tools.convertores;


/**
 *
 * @author Alvaro Monsalve
 */
public class addMedicamentos extends javax.swing.JDialog {
    private DefaultTableModel ModeloTabla;
    private Object dato[] = null;
    private SumSuministroJpaController sumSuministroJPA=null;
    private InvSumGeneralJpaController invSumGeneralJPA=null;
    private EntityManagerFactory factory;
    private SumSuministro suminis=null;

    /**
     * Creates new form addMedicamentos
     */
    public addMedicamentos(java.awt.Frame parent, boolean modal) {
        super(parent,modal);
        initComponents();
        setCargaTabla();        
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
    
    private void setCargaTabla(){
        ModeloTabla = getModelo();
        jTable1.setModel(ModeloTabla);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Funciones.setOcultarColumnas(jTable1,new int[]{1,4,5});
        jTable1.setDefaultRenderer(Object.class, new tools.ColorTableCellRendererMedicamentos());
        Funciones.setSizeColumnas(jTable1, new int[]{0,2}, new int[]{400,150});
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
        jTextArea25.setText(null);
        jTextPane1.setText(null);
        jTextField1.setText("");
        jComboBox4.setSelectedIndex(-1);
        jComboBox5.setSelectedIndex(-1);
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
    
    private String returnHtml(String var, String var2){
        return "<p style=\"margin-top: 0\"<strong>"+var+"</strong> "+var2+"</p>";
    }
    
    public void editPosologia(SumSuministro sum,String admin,Float dosisn,String dosisu,String via,String cantidad){
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
        jTextField15.setEnabled(false);
        jTextArea25.setText(admin);
        jTextField1.setText(dosisn.toString());
        jComboBox4.setSelectedItem(dosisu);
        jComboBox5.setSelectedItem(via);
        jButton1.setEnabled(false);
        jTextField2.setText(cantidad);
        suminis = sum;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel42 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        buttonSeven14 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven13 = new org.edisoncor.gui.button.ButtonSeven();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea25 = new javax.swing.JTextArea();
        jLabel40 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel38 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(720, 540));
        setResizable(false);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setMaximumSize(new java.awt.Dimension(540, 360));
        jPanel5.setMinimumSize(new java.awt.Dimension(540, 360));
        jPanel5.setPreferredSize(new java.awt.Dimension(540, 360));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/medical_pot_pills.png"))); // NOI18N
        jLabel50.setText("Listado de Medicamentos");

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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
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

        jLabel35.setForeground(new java.awt.Color(0, 51, 255));
        jLabel35.setText("Dosis:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[G] Gramo", "[GT] Gotas", "[CC] Centimetros Cubicos", "[MCG] Microgramos", "[MCL] Microlitros", "[MEQ] Miliequivalentes", "[MG] Miligramos", "[ML] Mililitros", "[Puff] Inhalación", "[U] Unidades", "[Tsp] Cucharadita", "[TP] Cucharada", "[L] Litros" }));
        jComboBox4.setSelectedIndex(-1);
        jComboBox4.setFocusable(false);

        jLabel38.setForeground(new java.awt.Color(0, 51, 255));
        jLabel38.setText("Via:");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[IM] Intramucsular", "[IV] Intravenosa", "[VB] Vía  Bucal (enjuagues, aplicación tópica)", "[Vic] Vía Intracavernosa", "[Vinh] Vía Inhalatoria", "[VO] Vía Oral", "[Vof] Vía Oftálmica", "[Vot] Vía Otica", "[VP] Vía(s) Parental(es)", "[VR] Vía Rectal", "[VSc] Vía Subcutánea", "[VSl] Vía Sublingual", "[VT] Vía Tópica", "[VTd] Vía Transdérmica", "[VV] Vía Vaginal (o Vulvar)" }));
        jComboBox5.setSelectedIndex(-1);
        jComboBox5.setFocusable(false);

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });

        jLabel41.setForeground(new java.awt.Color(0, 51, 255));
        jLabel41.setText("Cantidad del Suministro.");

        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });

        jLabel43.setForeground(new java.awt.Color(255, 0, 0));
        jLabel43.setText("La cantidad del suministro solo es para la formula medica.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, 0, 1, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox5, 0, 244, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("POSOLOGIA", jPanel2);

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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
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
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
            setDatosTabla();
        }
    }//GEN-LAST:event_jTextField15KeyReleased

    private void buttonSeven13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven13ActionPerformed
        if(jTextField2.getText().isEmpty()){
            jTextField2.setText("0");
        }
        Numero_a_Letra num = new Numero_a_Letra();
                if(jTable1.getRowCount() > 0){
            suminis = (SumSuministro)jTable1.getValueAt(jTable1.getSelectedRow(), 0);
        }
        if(suminis!=null){
            if(!jTextField1.getText().equals("") && jComboBox4.getSelectedIndex()>-1 && jComboBox5.getSelectedIndex()>-1 && !"".equals(jTextArea25.getText()) && !"".equals(jTextPane1.getText())){
                AtencionUrgencia.panelindex.hc.pMedic.addMedicamento(suminis,Float.parseFloat(jTextField1.getText().replace(",", ".")),num.numericToString(jTextField2.getText()),
                        jComboBox4.getSelectedItem().toString(),jComboBox5.getSelectedItem().toString(), jTextArea25.getText().toUpperCase(),Float.parseFloat(jTextField2.getText().replace(",", ".")) );
                this.dispose();
            }
        }

    }//GEN-LAST:event_buttonSeven13ActionPerformed

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved
        int rowIndex = jTable1.rowAtPoint(evt.getPoint());
        int colunmIndex = jTable1.columnAtPoint(evt.getPoint());
        jTable1.setToolTipText(convertores.getTextToHtml(jTable1.getValueAt(rowIndex, colunmIndex).toString()));
    }//GEN-LAST:event_jTable1MouseMoved

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==1){
            int rowIndex = jTable1.rowAtPoint(evt.getPoint());
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
        }//laboratorio. registro invima. presentacion comercial (pueden ser diferente y no los estamos mostrando por ese motivo se estan repitiendo
    }//GEN-LAST:event_jTable1MouseClicked

    private void buttonSeven14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven14ActionPerformed
        while(ModeloTabla.getRowCount()>0){
            ModeloTabla.removeRow(0);
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
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        jTextField2.setText(Funciones.FormatDecimal(jTextField2.getText()));
    }//GEN-LAST:event_jTextField2FocusLost

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
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonSeven buttonSeven13;
    private org.edisoncor.gui.button.ButtonSeven buttonSeven14;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea25;
    private javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
