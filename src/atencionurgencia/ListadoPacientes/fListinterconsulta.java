
package atencionurgencia.ListadoPacientes;

import atencionurgencia.AtencionUrgencia;
import atencionurgencia.evolucion.Evo;
import entidades.CmEspPprofesional;
import entidades.HcuEvolucion;
import entidades.InfoHistoriac;
import entidades.StaticEspecialidades;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import jpa.HcuEvoInterconsultaJpaController;
import jpa.HcuEvolucionJpaController;
import jpa.InfoHistoriacJpaController;
import jpa.InfoInterconsultaHcuJpaController;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class fListinterconsulta extends javax.swing.JFrame {
    private DefaultTableModel modelo;
    private final EntityManagerFactory factory;
    private StaticEspecialidades especialidades = null;
    private final Object dato[] = null;
    private InfoInterconsultaHcuJpaController iihjc = null;
    private InfoHistoriacJpaController ihjc = null;
    private HcuEvolucionJpaController hejc = null;
    private HcuEvoInterconsultaJpaController heijc = null;
    private Timer timer,timerSelect;

    /**
     * Creates new form fListinterconsulta
     * @param factory
     */
    public fListinterconsulta(EntityManagerFactory factory) {
        initComponents();        
        this.factory = factory;
        TablaModeloTabla();
        showEspecialidades();
        jLabel1.setVisible(false);
    }
    
    public void inicio(){
        TimerTask timerListar = new TimerTask() {
        @Override
        public void run() {
                jLabel1.setVisible(true);
                jComboBox1.setEnabled(false);
                showPacientes();
                jComboBox1.setEnabled(true);
                jLabel1.setVisible(false);
            }
        };
        timer = new Timer();
        timer.schedule(timerListar, new Date());
    }
    
    public void SelectTimer(){
        TimerTask timerListar = new TimerTask() {
        @Override
        public void run() {
                jLabel1.setVisible(true);
                select();
                jLabel1.setVisible(false);
            }
        };
        timerSelect = new Timer();
        timerSelect.schedule(timerListar, new Date());
    }
    
    private DefaultTableModel getModeloTabla() {
        return (new DefaultTableModel(
                null, new String[]{"hcu", "Documento", "Nombre"}) {
                    Class[] types = new Class[]{
                        InfoHistoriac.class,
                        java.lang.String.class,
                        java.lang.String.class,
                        java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false
                    };

                    @Override
                    public Class getColumnClass(int columnIndex){
                        return types[columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int colIndex) {
                        return canEdit[colIndex];
                    }
                });
    }
    
    private void TablaModeloTabla(){
           modelo = getModeloTabla();
           jTable1.setModel(modelo);
           jTable1.getTableHeader().setReorderingAllowed(false);
           jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
           Funciones.setSizeColumnas(jTable1, new int[]{1}, new int[]{80});
           Funciones.setOcultarColumnas(jTable1,new int[]{0});
//           jTable1.setTableHeader(null);
    }
    
    private void showEspecialidades(){
        
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        jComboBox1.setModel(dcbm);
        if(AtencionUrgencia.cep!=null){
            for(CmEspPprofesional cep:AtencionUrgencia.cep.getCmEspPprofesionalList()){
                dcbm.addElement(cep.getIdEspecialidad());
            }   
        }else{
            JOptionPane.showMessageDialog(null, "El profesional no tiene especialidades asignadas");
            jComboBox1.setEnabled(false);
        }  
    }
    
    public void showPacientes(){
        if(iihjc==null)  iihjc = new InfoInterconsultaHcuJpaController(factory);
        if(ihjc==null) ihjc = new InfoHistoriacJpaController(factory);
        if(hejc==null) hejc = new HcuEvolucionJpaController(factory);
        if(heijc==null) heijc = new HcuEvoInterconsultaJpaController(factory);
        especialidades = (StaticEspecialidades)jComboBox1.getModel().getSelectedItem();
        List<InfoHistoriac> historiacs = ihjc.findinfoHistoriacs(1);//Notas de ingreso finalizadas
        Integer count=0;
        while(modelo.getRowCount()>0){
            modelo.removeRow(0);
        }
        for(InfoHistoriac ih:historiacs){
            count =0;
            int row = modelo.getRowCount();
            count = count +  iihjc.CountInterconsultas(ih, (StaticEspecialidades) jComboBox1.getSelectedItem()).intValue();
            List<HcuEvolucion> evolucions = hejc.FindHcuEvolucions(ih);
            for(HcuEvolucion he:evolucions){
                count = count + CountInterconsultas(he, (StaticEspecialidades) jComboBox1.getSelectedItem()).intValue();                
            }
            count = count - hejc.CountInterconsultasGeneradas(ih, especialidades).intValue();
            if(count>0){
                modelo.addRow(dato);
                modelo.setValueAt(ih, row, 0);
                modelo.setValueAt(ih.getIdInfoAdmision().getIdDatosPersonales().getNumDoc(), row, 1);
                modelo.setValueAt(ih.getIdInfoAdmision().getIdDatosPersonales().getNombre1()+" "
                        +ih.getIdInfoAdmision().getIdDatosPersonales().getApellido1(), row, 2);
            }            
        }
        timer.cancel();
    }
    
    private Long CountInterconsultas(HcuEvolucion evo, StaticEspecialidades se){
        EntityManager em = heijc.getEntityManager();
        em.clear();
        try {
            return (Long) em.createQuery("SELECT COUNT(h) FROM HcuEvoInterconsulta h WHERE h.idHcuEvolucion = :evo AND h.idStaticEspecialidades = :se AND h.idHcuEvolucion.estado='2'")
            .setParameter("evo", evo)
            .setParameter("se", se)
            .setHint("javax.persistence.cache.storeMode", "REFRESH")
            .getSingleResult();
        } finally {
            em.close();
        }
   }
    
    private void closed(){
        AtencionUrgencia.panelindex.jButton4.setEnabled(true);
        this.dispose();
    }
    
    private void select() {
        AtencionUrgencia.panelindex.jpContainer.removeAll();
        AtencionUrgencia.panelindex.evo = new Evo(factory);
        AtencionUrgencia.panelindex.evo.setBounds(0, 0, 764, 514);
        AtencionUrgencia.panelindex.jpContainer.add(AtencionUrgencia.panelindex.evo);
        AtencionUrgencia.panelindex.evo.setVisible(true);
        AtencionUrgencia.panelindex.evo.tipoEvo = 1;//1 es valoracion
        AtencionUrgencia.panelindex.evo.staticEspecialidades = especialidades;
        AtencionUrgencia.panelindex.jpContainer.validate();
        AtencionUrgencia.panelindex.jpContainer.repaint();
        AtencionUrgencia.panelindex.evo.viewClinicHistory((InfoHistoriac) modelo.getValueAt(jTable1.getSelectedRow(), 0));
        AtencionUrgencia.panelindex.evo.DatosAntPersonales();//mostrar antecedentes personales
        timerSelect.cancel();
        closed();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Prescription.png"))); // NOI18N
        jLabel50.setText("Listado De Pacientes Para Valoracion Por Especialista");

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder("Especialidad"));
        jPanel32.setOpaque(false);

        jComboBox1.setDoubleBuffered(true);
        jComboBox1.setFocusable(false);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jTable1);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ajax-loader.gif"))); // NOI18N

        jButton1.setText("Aceptar");
        jButton1.setFocusable(false);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        AtencionUrgencia.panelindex.jButton4.setEnabled(true);
        timer.cancel();
        if(timerSelect!=null) timerSelect.cancel();
    }//GEN-LAST:event_formWindowClosing

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if(((javax.swing.JComboBox) evt.getSource()).getSelectedIndex()>-1 && ((javax.swing.JComboBox) evt.getSource()).getSelectedItem()!= null){
            this.inicio();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jTable1.getSelectedRow()>-1){
            this.SelectTimer();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
