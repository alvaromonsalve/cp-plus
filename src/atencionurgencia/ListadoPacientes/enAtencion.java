package atencionurgencia.ListadoPacientes;

import atencionurgencia.AtencionUrgencia;
import atencionurgencia.ingreso.HC;
import entidades.HcuHistoriac2;
import entidades.InfoAdmision;
import entidades.InfoHistoriac;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jpa.HcuHistoriac2JpaController;
import jpa.InfoHistoriacJpaController;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class enAtencion extends javax.swing.JDialog {

    private DefaultTableModel modelo;
    private InfoAdmision infoadmision = null;
    private InfoHistoriac infoHistoriac = null;
    private InfoHistoriacJpaController infoHistoriacJPA = null;
    private HcuHistoriac2JpaController hcuHistoriac2JpaC;
    private final EntityManagerFactory factory;
    private List<InfoHistoriac> listaHistoriaC;
    Object dato[] = null;
    private Timer timer;

    public enAtencion(EntityManagerFactory factory) {
        initComponents();
        jLabel1.setVisible(false);
        this.factory = factory;
    }

    public void inicio() {
        ModeloListadoPaciente();
        TimerTask timerListar = new TimerTask() {
            @Override
            public void run() {
                jLabel1.setVisible(true);
                TimerInicio();
                jLabel1.setVisible(false);
            }
        };
        timer = new Timer();
        timer.schedule(timerListar, new Date());
    }

    private void TimerInicio() {
        if (infoHistoriacJPA == null) {
            infoHistoriacJPA = new InfoHistoriacJpaController(factory);
            hcuHistoriac2JpaC = new HcuHistoriac2JpaController(factory);
        }
        listaHistoriaC = infoHistoriacJPA.findinfoHistoriacs(0);
        for (int i = 0; i < listaHistoriaC.size(); i++) {
            infoadmision = listaHistoriaC.get(i).getIdInfoAdmision();
            modelo.addRow(dato);
            modelo.setValueAt(listaHistoriaC.get(i).getId(), i, 0);
            modelo.setValueAt(infoadmision.getIdDatosPersonales().getNumDoc(), i, 1);
            modelo.setValueAt(infoadmision.getIdDatosPersonales().getNombre1() + " " + infoadmision.getIdDatosPersonales().getApellido1(), i, 2);
            modelo.setValueAt(listaHistoriaC.get(i).getIdConfigdecripcionlogin().getNombres() + " " + listaHistoriaC.get(i).getIdConfigdecripcionlogin().getApellidos(), i, 3);
            HcuHistoriac2 hh = findHcuHistoriac2(listaHistoriaC.get(i).getId());
            Date hoy = new Date();
            long diferencia;
            String difString;
            if(hh!=null){                
                diferencia = (hoy.getTime() - hh.getFIngreso().getTime())/ 1000/60;
                difString = String.format("%.2f", diferencia);
                
            }else{
                diferencia = (hoy.getTime() -listaHistoriaC.get(i).getFechaDato()  .getTime())/ 1000/60;
                difString = String.format("%.2f", diferencia);
            }
            modelo.setValueAt(difString, i, 4);
        }
        timer.cancel();
    }
    
    private HcuHistoriac2 findHcuHistoriac2(int info_historiac ){
        EntityManager em = hcuHistoriac2JpaC.getEntityManager();
        try {
            return (HcuHistoriac2) em.createQuery("SELECT h FROM HcuHistoriac2 h WHERE h.idInfoHistoriac = :hc")
                    .setParameter("hc", info_historiac)
                    .setHint("javax.persistence.cache.storeMode", "REFRESH")
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    private void ModeloListadoPaciente() {
        try {
            modelo = new DefaultTableModel(
                    null, new String[]{"id", "Documento", "Nombre", "Medico","Tiempo"}) {
                        Class[] types = new Class[]{
                            java.lang.String.class,
                            java.lang.String.class,
                            java.lang.String.class,
                            java.lang.String.class,
                            java.lang.String.class
                        };

                        boolean[] canEdit = new boolean[]{
                            false, false, false, false, false
                        };

                        @Override
                        public Class getColumnClass(int columnIndex) {
                            return types[columnIndex];
                        }

                        @Override
                        public boolean isCellEditable(int rowIndex, int colIndex) {
                            return canEdit[colIndex];
                        }
                    };
            jtListadoPacientes.setModel(modelo);
            jtListadoPacientes.getTableHeader().setReorderingAllowed(false);
            jtListadoPacientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Funciones.setOcultarColumnas(jtListadoPacientes, new int[]{0});
            jtListadoPacientes.getColumnModel().getColumn(1).setMinWidth(70);
            jtListadoPacientes.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(70);
            jtListadoPacientes.getColumnModel().getColumn(2).setMinWidth(160);
            jtListadoPacientes.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(160);
            jtListadoPacientes.getColumnModel().getColumn(3).setMinWidth(180);
            jtListadoPacientes.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(180);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10074:\n" + ex.getMessage(), enAtencion.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class hiloConsulta extends Thread {

        JDialog jdialog;

        private hiloConsulta(JDialog jdialog) {
            this.jdialog = jdialog;
        }

        @Override
        public void run() {
            if (infoHistoriac != null) {
                if (infoadmision != null) {
                    jLabel1.setVisible(true);
                    ((enAtencion) jdialog).jButton1.setEnabled(false);
                    AtencionUrgencia.panelindex.jpContainer.removeAll();
                    AtencionUrgencia.panelindex.hc = new HC(factory);
                    AtencionUrgencia.panelindex.hc.setBounds(0, 0, 764, 540);
                    AtencionUrgencia.panelindex.jpContainer.add(AtencionUrgencia.panelindex.hc);
                    AtencionUrgencia.panelindex.hc.setVisible(true);
                    AtencionUrgencia.panelindex.jpContainer.validate();
                    AtencionUrgencia.panelindex.jpContainer.repaint();
                    AtencionUrgencia.panelindex.hc.viewClinicHistory(infoHistoriac);
                    AtencionUrgencia.panelindex.hc.DatosAntPersonales();//crear o mostrar antecedentes personales
                    ((enAtencion) jdialog).jButton1.setEnabled(true);
                    jLabel1.setVisible(false);
                }
                closed();
            }
            infoadmision = null;
        }
    }

    private void closed() {
        if (timer != null) {
            timer.cancel();
        }
        AtencionUrgencia.panelindex.activeButton(true);
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtListadoPacientes = new javax.swing.JTable();
        jLabel49 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Listado de Pacientes");
        setMaximumSize(new java.awt.Dimension(510, 340));
        setMinimumSize(new java.awt.Dimension(510, 340));
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

        jtListadoPacientes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtListadoPacientes.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jtListadoPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtListadoPacientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jtListadoPacientes.setDoubleBuffered(true);
        jtListadoPacientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jtListadoPacientes.getTableHeader().setReorderingAllowed(false);
        jtListadoPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtListadoPacientesMouseReleased(evt);
            }
        });
        jtListadoPacientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtListadoPacientesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtListadoPacientes);

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/History.png"))); // NOI18N
        jLabel49.setText("Pacientes en atención");
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel49.setMaximumSize(new java.awt.Dimension(190, 32));
        jLabel49.setMinimumSize(new java.awt.Dimension(190, 32));
        jLabel49.setPreferredSize(new java.awt.Dimension(190, 32));

        jButton1.setText("Aceptar");
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtListadoPacientesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtListadoPacientesMouseReleased
        if (SwingUtilities.isLeftMouseButton(evt)) {
            int row = jtListadoPacientes.rowAtPoint(evt.getPoint());
            infoHistoriac = infoHistoriacJPA.findInfoHistoriac(Integer.parseInt(modelo.getValueAt(row, 0).toString()));
        }
    }//GEN-LAST:event_jtListadoPacientesMouseReleased

    private void jtListadoPacientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtListadoPacientesKeyReleased
        if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
            infoHistoriac = infoHistoriacJPA.findInfoHistoriac(Integer.parseInt(modelo.getValueAt(jtListadoPacientes.getSelectedRow(), 0).toString()));
        }
    }//GEN-LAST:event_jtListadoPacientesKeyReleased

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        hiloConsulta ut = new hiloConsulta(this);
        Thread thread = new Thread(ut);
        thread.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.closed();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jtListadoPacientes;
    // End of variables declaration//GEN-END:variables
}
