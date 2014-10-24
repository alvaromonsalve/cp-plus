/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.ListadoPacientes;

import atencionurgencia.AtencionUrgencia;
import atencionurgencia.ingreso.HC;
import ejb.FuncionesBD;
import entidades.InfoAdmision;
import entidades.InfoOtrosdatosAdmision;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.persistence.EntityManagerFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jpa.InfoAdmisionJpaController;
import jpa.InfoOtrosdatosAdmisionJpaController;
import jpa.InfoPacienteJpaController;
import jpa.exceptions.IllegalOrphanException;
import jpa.exceptions.NonexistentEntityException;
import tools.CausasExternas;
import tools.Funciones;

/**
 *
 * @author AlvaroVirtual
 */
public class fListPacientes extends javax.swing.JFrame {
    private DefaultTableModel modelo;
    private long idCambiosAdmision=0;
    private Timer timer;
    private Timer reloj;
    InfoAdmisionJpaController admision = null;
    InfoOtrosdatosAdmisionJpaController oadmision = null;
            InfoPacienteJpaController infopacienteJPA = null;

    public fListPacientes(EntityManagerFactory factory) {
        initComponents();
        admision = new InfoAdmisionJpaController(factory);
        oadmision = new   InfoOtrosdatosAdmisionJpaController(factory);
        infopacienteJPA = new InfoPacienteJpaController(factory);
        this.ModeloListadoPaciente();        
    }
    
        @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("images/icono.png"));
        return retValue;
    }
        
    private void ModeloListadoPaciente(){
         try {
             modelo =getModelo();
             jtable1.setModel(modelo);       
             jtable1.getTableHeader().setReorderingAllowed(false);
             jtable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
             jtable1.setDefaultRenderer(Object.class, new tools.ColorTableCellRendererMedicamentos());
             Funciones.setOcultarColumnas(jtable1, new int[]{7});
             Funciones.setSizeColumnas(jtable1, new int[]{0,1,2,3,4,5,6}, new int[]{60,80,140,100,90,270,58});
             jtable1.setDefaultRenderer(Object.class, new tools.ColorDefaultTableCellRenderer());
         } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "10045:\n"+ex.getMessage(), fListPacientes.class.getName(), JOptionPane.INFORMATION_MESSAGE);
         }
    }
    
    private DefaultTableModel getModelo(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"Admisión","Documento", "Nombre", "Entidad", "Prioridad", "Subjetividad del Paciente","Hora",""}){
        Class[] types = new Class []{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                InfoAdmision.class
        };
        boolean[] canEdit = new boolean [] {
        false,false,false,false,false,false,false,false
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
    
    private void actualizarTabla(){
        long var = FuncionesBD.verCambiosListPacientesTriage(AtencionUrgencia.props);
        while(idCambiosAdmision<var){
            idCambiosAdmision=var;
            
            Object dato[] = null;
            //1 es el numero del estado en la tabla de admisiones
            List<InfoAdmision> listaAdmision = admision.findInfoAdmisionActivado(1);
            if (listaAdmision!=null){
                try {
                    while(modelo.getRowCount()>0){
                        modelo.removeRow(0);
                    }  
                    for (int i = 0; i < listaAdmision.size(); i++){
                        modelo.addRow(dato);
                        modelo.setValueAt(listaAdmision.get(i).getId(), i, 0);
                        modelo.setValueAt(listaAdmision.get(i).getIdDatosPersonales().getNumDoc(), i, 1);
                        modelo.setValueAt(listaAdmision.get(i).getIdDatosPersonales().getNombre1()+" "+
                                listaAdmision.get(i).getIdDatosPersonales().getApellido1(), i, 2);
                        modelo.setValueAt(listaAdmision.get(i).getIdEntidadAdmision().getNombreEntidad(), i, 3);
                        String materna = "0";
                        String dolor = "0";
                        if(oadmision.getInfoOtrosdatosAdmisionCount(listaAdmision.get(i).getId())>0){
                        InfoOtrosdatosAdmision infoOtros = oadmision.findInfoOtrosdatosAdmision(listaAdmision.get(i).getId());
                        materna = infoOtros.getMaterna();
                        dolor = infoOtros.getExposicionDolor();
                        }
                        modelo.setValueAt(this.getPriority(
                                materna, 
                                dolor,
                                listaAdmision.get(i).getEdad())                      
                                , i, 4);
                        modelo.setValueAt(listaAdmision.get(i).getSubjetividadPaciente(), i, 5);
                        modelo.setValueAt(listaAdmision.get(i).getHoraIngreso(), i, 6);
                        modelo.setValueAt(listaAdmision.get(i), i, 7);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "10043:\n"+e.getMessage(), fListPacientes.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    private String getPriority(String materna, String exp, String edad){
        String valor = "";
        try {                    
        String[] sepEdad = edad.split(" ");
        long edadint=0;
        edadint = Integer.parseInt((String)sepEdad[0]);
        if(exp!=null){
            if(exp.equals("1")){
                valor = "PRIORIDAD";
            }
        }
        if(((String)sepEdad[1]).equals("Años")){
            if(edadint<9){
                valor = "MENOR 8 AÑOS";
            }else if(edadint>64){
                valor = "MAYOR 65 AÑOS";
            }
            if(materna!=null){
                if(materna.equals("1")){
                    valor = "MATERNA";
                }
            }
        }else{
            valor = "MENOR 8 AÑOS";
            if(materna!=null){
                if(materna.equals("1")){
                    valor = "MATERNA";
                }
            }
        }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10051:\n"+ex.getMessage(), fListPacientes.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
        return valor;
    }
    
    private void timeRun(final InfoAdmision ia){
        TimerTask timerReloj = new TimerTask() {
        @Override
        public void run() {
                List<String> tiempo = tools.MyDate.getDifDateMinutes(ia.getFechaIngreso(),ia.getHoraIngreso(), (new Date()).getTime());
                jLabel16.setText(tiempo.get(0)+":"+tiempo.get(1)+":"+tiempo.get(2)); 
            }
        };
        reloj = new Timer();
        reloj.scheduleAtFixedRate(timerReloj, new Date(), 1000);
    }
    
    private void selectAdmision(){
        if(reloj!=null){
            reloj.cancel();
        }
        InfoAdmision ia = (InfoAdmision) jtable1.getValueAt(jtable1.getSelectedRow(), 7);
        jTextField1.setText(ia.getIdDatosPersonales().getNumDoc());
        jTextField2.setText(ia.getIdDatosPersonales().getNombre1()+" "+ia.getIdDatosPersonales().getApellido1());
        jTextField3.setText(String.valueOf(ia.getEdad()));
        jTextField4.setText(ia.getIdDatosPersonales().getContratante().getNombreEntidad());
        jTextField5.setText(ia.getIdEntidadAdmision().getNombreEntidad());
        jTextField6.setText(CausasExternas.causas(ia.getCausaExterna()));
        jTextField7.setText(CausasExternas.medioIngreso(ia.getMediodeingreso()));
        jTextArea1.setText(String.valueOf(ia.getSubjetividadAdmisionista()));
        jTextArea1.setCaretPosition(0);
        jTextArea2.setText(String.valueOf(ia.getSubjetividadPaciente()));
        jTextArea2.setCaretPosition(0);
        this.timeRun(ia);
    }
    
    private void clear(){
        jTextField1.setText("...");
        jTextField2.setText("...");
        jTextField3.setText("...");
        jTextField4.setText("...");
        jTextField5.setText("...");
        jTextField6.setText("...");
        jTextField7.setText("...");
        jTextArea1.setText("...");
        jTextArea2.setText("...");
    }
    
    private void closed(){
        if(timer!=null){
            timer.cancel();
        }
        if(reloj!=null){
            reloj.cancel();
        }
        this.clear();
        AtencionUrgencia.panelindex.activeButton(true);
        JFrame casa = (JFrame) SwingUtilities.getWindowAncestor(AtencionUrgencia.panelindex);
        casa.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pacientes con Admisión");
        setFocusable(false);
        setIconImage(getIconImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(840, 540));
        jPanel1.setMinimumSize(new java.awt.Dimension(840, 540));
        jPanel1.setPreferredSize(new java.awt.Dimension(840, 540));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_group.png"))); // NOI18N
        jLabel50.setText("Listado de Pacientes Admisionados en Espera de Atención");

        jtable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtable1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jtable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jtable1.setDoubleBuffered(true);
        jtable1.getTableHeader().setReorderingAllowed(false);
        jtable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtable1MouseReleased(evt);
            }
        });
        jtable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtable1);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(840, 20));
        jToolBar1.setMinimumSize(new java.awt.Dimension(840, 20));
        jToolBar1.setOpaque(false);
        jToolBar1.setPreferredSize(new java.awt.Dimension(840, 20));

        jLabel1.setBackground(java.awt.Color.lightGray);
        jLabel1.setMaximumSize(new java.awt.Dimension(14, 14));
        jLabel1.setMinimumSize(new java.awt.Dimension(14, 14));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(14, 14));
        jToolBar1.add(jLabel1);

        jLabel2.setText("MUCHO DOLOR");
        jToolBar1.add(jLabel2);
        jToolBar1.add(jSeparator1);

        jLabel4.setBackground(java.awt.Color.pink);
        jLabel4.setMaximumSize(new java.awt.Dimension(14, 14));
        jLabel4.setMinimumSize(new java.awt.Dimension(14, 14));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(14, 14));
        jToolBar1.add(jLabel4);

        jLabel3.setText("MAYOR 65 AÑOS");
        jToolBar1.add(jLabel3);
        jToolBar1.add(jSeparator2);

        jLabel5.setBackground(java.awt.Color.cyan);
        jLabel5.setMaximumSize(new java.awt.Dimension(14, 14));
        jLabel5.setMinimumSize(new java.awt.Dimension(14, 14));
        jLabel5.setOpaque(true);
        jLabel5.setPreferredSize(new java.awt.Dimension(14, 14));
        jToolBar1.add(jLabel5);

        jLabel6.setText("MENOR 8 AÑOS");
        jToolBar1.add(jLabel6);
        jToolBar1.add(jSeparator3);

        jLabel7.setBackground(java.awt.Color.orange);
        jLabel7.setMaximumSize(new java.awt.Dimension(14, 14));
        jLabel7.setMinimumSize(new java.awt.Dimension(14, 14));
        jLabel7.setOpaque(true);
        jLabel7.setPreferredSize(new java.awt.Dimension(14, 14));
        jToolBar1.add(jLabel7);

        jLabel8.setText("MATERNA");
        jToolBar1.add(jLabel8);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel2.setOpaque(false);

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 102, 255));
        jTextField1.setText("...");
        jTextField1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField1.setOpaque(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel9.setText("Documento");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel10.setText("Nombre");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(0, 102, 255));
        jTextField2.setText("...");
        jTextField2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField2.setOpaque(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel11.setText("Edad");

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(0, 102, 255));
        jTextField3.setText("...");
        jTextField3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField3.setOpaque(false);

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(0, 102, 255));
        jTextField4.setText("...");
        jTextField4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField4.setOpaque(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel12.setText("Entidad");

        jTextField5.setEditable(false);
        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(0, 102, 255));
        jTextField5.setText("...");
        jTextField5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField5.setOpaque(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel13.setText("Entidad de Admisión");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel14.setText("Causa Externa");

        jTextField6.setEditable(false);
        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(0, 102, 255));
        jTextField6.setText("...");
        jTextField6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField6.setOpaque(false);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel15.setText("Medio de Ingreso");

        jTextField7.setEditable(false);
        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(0, 102, 255));
        jTextField7.setText("...");
        jTextField7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField7.setOpaque(false);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 102, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("00:00:00");
        jLabel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tiempo de Espera", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel17.setText("Subjetividad / Comentario del Admisionista");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(3);
        jTextArea1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane2.setViewportView(jTextArea1);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel18.setText("Subjetividad / Comentario del Paciente");

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(3);
        jTextArea2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane3.setViewportView(jTextArea2);

        jButton1.setText("Triaje");
        jButton1.setBorderPainted(false);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4)
                            .addComponent(jTextField5)
                            .addComponent(jTextField6)
                            .addComponent(jTextField7)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtable1MouseReleased
       if(jtable1.getSelectedRow()!=-1){
            selectAdmision();
        }
    }//GEN-LAST:event_jtable1MouseReleased

    private void jtable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtable1KeyReleased
        if((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)){
            selectAdmision();
        }
    }//GEN-LAST:event_jtable1KeyReleased
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        TimerTask timerListar = new TimerTask() {
        @Override
        public void run() {
                actualizarTabla();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerListar, new Date(), 5000);
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closed();
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        InfoAdmision ia = (InfoAdmision) jtable1.getValueAt(jtable1.getSelectedRow(), 7);
        if (jTextField1.getText()!=null && !jTextField1.getText().equals("") && !jTextField1.getText().equals("...")){  
            AtencionUrgencia.panelindex.hc.ftriaje = new Ftriaje();
            AtencionUrgencia.panelindex.hc.ftriaje.setTitle(ia.getIdDatosPersonales().getNombre1()+" "
                    +ia.getIdDatosPersonales().getApellido1()+" ["+ia.getIdDatosPersonales().getNumDoc()+"]");
            AtencionUrgencia.panelindex.hc.ftriaje.setVisible(true);
            AtencionUrgencia.panelindex.FramEnable(false);
            AtencionUrgencia.panelindex.hc.infopaciente = infopacienteJPA.findInfoPaciente(ia.getIdDatosPersonales().getId());
            ia.setEstado(5);
            try {
                admision.edit(ia);
                AtencionUrgencia.panelindex.hc.infoadmision=ia;
                AtencionUrgencia.panelindex.hc.CrearHistoriaC();//creo la historia clinica de esa admision
                AtencionUrgencia.panelindex.hc.DatosAntPersonales();//crear o mostrar antecedentes personales
            } 
            catch (IllegalOrphanException ex) {
                JOptionPane.showMessageDialog(null, "10048:\n"+ex.getMessage(), fListPacientes.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            } catch (NonexistentEntityException ex) {
                JOptionPane.showMessageDialog(null, "10049:\n"+ex.getMessage(), fListPacientes.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10050:\n"+ex.getMessage(), fListPacientes.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }
            AtencionUrgencia.panelindex.hc.jlbNombrePaciente.setText(jTextField2.getText()+" ["+jTextField1.getText()+"]    ["+jTextField5.getText()+"]");
            closed();
            this.dispose();
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JToolBar jToolBar1;
    public javax.swing.JTable jtable1;
    // End of variables declaration//GEN-END:variables
}
