package other;

import atencionurgencia.AtencionUrgencia;
import atencionurgencia.evolucion.Evo;
import atencionurgencia.ingreso.HC;
import entidades.Configdecripcionlogin;
import entidades.HcuEvolucion;
import entidades.InfoAdmision;
import entidades.InfoHistoriac;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jpa.ConfigdecripcionloginJpaController;
import jpa.HcuEvolucionJpaController;
import jpa.InfoAdmisionJpaController;
import tools.Funciones;
import tools.MyDate;

/**
 *
 * @author Alvaro Monsalve
 */
public class hcuAdministrador extends javax.swing.JDialog {
    private DefaultTableModel modelo;
    private DefaultTableModel modelo2;    
    private final EntityManagerFactory factory;
    private final InfoAdmisionJpaController infoAdmisionJpaC;
    private final HcuEvolucionJpaController evolucionJpaC;
    private final ConfigdecripcionloginJpaController configdecripcionloginJpaC;
    private final Object dato[] = null;

    /**
     * Creates new form hcuAdministrador
     * @param parent
     * @param factory
     */
    public hcuAdministrador(java.awt.Frame parent, EntityManagerFactory factory) {
        super(parent, true);
        this.factory = factory;
        initComponents();
        setModeloTabla();
        infoAdmisionJpaC = new InfoAdmisionJpaController(factory);
        evolucionJpaC=new HcuEvolucionJpaController(factory);
        configdecripcionloginJpaC=new ConfigdecripcionloginJpaController(factory);
        jLabel2.setVisible(false);
    }
    
    private DefaultTableModel getModeloTabla() {
        return (new DefaultTableModel(
                null, new String[]{"Admisión", "Admisión", "Fecha"}) {
                    Class[] types = new Class[]{
                        InfoAdmision.class,
                        java.lang.String.class,
                        java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false
                    };

                    @Override
                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int colIndex) {
                        return canEdit[colIndex];
                    }
                });
    }
    
        private DefaultTableModel getModeloTabla2() {
        return (new DefaultTableModel(
                null, new String[]{"Evolución", "Fecha", "Evolución"}) {
                    Class[] types = new Class[]{
                        HcuEvolucion.class,
                        java.lang.String.class,
                        java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false
                    };

                    @Override
                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int colIndex) {
                        return canEdit[colIndex];
                    }
                });
    }
    
    private void setModeloTabla(){
           modelo = getModeloTabla();
           modelo2 = getModeloTabla2();
           jTable1.setModel(modelo);
           jTable2.setModel(modelo2);
           jTable1.getTableHeader().setReorderingAllowed(false);
           jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
           Funciones.setSizeColumnas(jTable1, new int[]{1}, new int[]{70});
           Funciones.setOcultarColumnas(jTable1,new int[]{0});
           jTable2.getTableHeader().setReorderingAllowed(false);
           jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
           Funciones.setSizeColumnas(jTable2, new int[]{1}, new int[]{100});
           Funciones.setOcultarColumnas(jTable2,new int[]{0});
    }
    
    private class hiloBusqueda extends Thread{
        
        public hiloBusqueda(){
        }
        
        @Override
        public void run(){
            jLabel2.setVisible(true);
            List<InfoAdmision> admisions = listAdmisiones(jTextField1.getText());
            for (InfoAdmision admision : admisions) {
                int i = jTable1.getRowCount();
                modelo.addRow(dato);
                modelo.setValueAt(admision, i, 0);
                modelo.setValueAt(admision.getId(), i, 1);
                Date date = new Date(admision.getHoraIngreso().getTime());
                String fecha = MyDate.ddMMyyyy.format(admision.getFechaIngreso()) + " " + MyDate.HHmm.format(date);
                modelo.setValueAt(fecha, i, 2);
                jLabel2.setVisible(false);
            }
        }
    }
    
    private List<InfoAdmision> listAdmisiones(String var){
        EntityManager em = infoAdmisionJpaC.getEntityManager();
        try {
            return em.createQuery("SELECT a FROM InfoAdmision a WHERE a.estado <> 0 AND a.idDatosPersonales.numDoc = :d")
                    .setParameter("d", var)
                    .setHint("javax.persistence.cache.storeMode", "REFRESH")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_add.png"))); // NOI18N
        jLabel50.setText("Administracion del Modulo de Historias Clinicas Urgencias");

        jTabbedPane1.setFocusable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setFocusable(false);

        jTextField1.setText("1038112701");
        jTextField1.setMaximumSize(new java.awt.Dimension(330, 20));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel1.setText("Documento:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.getTableHeader().setReorderingAllowed(false);
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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable2MouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Heart beat.gif"))); // NOI18N
        jLabel2.setMaximumSize(new java.awt.Dimension(60, 20));
        jLabel2.setMinimumSize(new java.awt.Dimension(60, 20));
        jLabel2.setPreferredSize(new java.awt.Dimension(60, 20));

        jLabel3.setMaximumSize(new java.awt.Dimension(200, 13));
        jLabel3.setMinimumSize(new java.awt.Dimension(200, 13));
        jLabel3.setPreferredSize(new java.awt.Dimension(200, 13));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Ver Ingreso");
        jLabel4.setMaximumSize(new java.awt.Dimension(200, 30));
        jLabel4.setMinimumSize(new java.awt.Dimension(200, 30));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(200, 30));
        jLabel4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel4MouseMoved(evt);
            }
        });
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel4MouseReleased(evt);
            }
        });

        jLabel5.setMaximumSize(new java.awt.Dimension(200, 13));
        jLabel5.setMinimumSize(new java.awt.Dimension(200, 13));
        jLabel5.setPreferredSize(new java.awt.Dimension(200, 13));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Ver Evolución");
        jLabel6.setMaximumSize(new java.awt.Dimension(200, 30));
        jLabel6.setMinimumSize(new java.awt.Dimension(200, 30));
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(200, 30));
        jLabel6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel6MouseMoved(evt);
            }
        });
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel6MouseReleased(evt);
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
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel6.getAccessibleContext().setAccessibleName("Ver Evolución");

        jTabbedPane1.addTab("Auditoria", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setFocusable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 491, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Acerca de", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
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

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            hiloBusqueda ut = new hiloBusqueda();
            Thread thread = new Thread(ut);
            thread.start();
        }        
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        if((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)){
            
        }
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        if(SwingUtilities.isLeftMouseButton(evt)){
            int row = jTable1.rowAtPoint(evt.getPoint());
            InfoHistoriac historiac=null;
            InfoAdmision ia = (InfoAdmision) modelo.getValueAt(row, 0);
            List<InfoHistoriac> historiacs = ia.getInfoHistoriacList();
            for (InfoHistoriac ih : historiacs) {
                if(ih.getEstado() != 0){
                    historiac=ih;
                    break;
                }
            }
            if(historiac!=null){
                jLabel3.setText(historiac.getIdConfigdecripcionlogin().getNombres()+" "+historiac.getIdConfigdecripcionlogin().getApellidos());
                while (modelo2.getRowCount()>0) {
                    modelo2.removeRow(0);
                }
                List<HcuEvolucion> evolucions = evolucionJpaC.FindHcuEvolucions(historiac);
                for (HcuEvolucion evolucion : evolucions) {
                    int i = jTable2.getRowCount();
                    modelo2.addRow(dato);
                    modelo2.setValueAt(evolucion, i, 0);
                    modelo2.setValueAt(MyDate.yyyyMMddHHmm2.format(evolucion.getFechaEvo())  , i, 1);
                    String tipo="";
                    if(evolucion.getTipo()==0){
                        if(evolucion.getEstado()==1 || evolucion.getEstado()==2){
                            tipo="Evolución M. General";
                        }else if(evolucion.getEstado()==3 || evolucion.getEstado()==4){
                            tipo="Egreso M. General";
                        }
                    }else if(evolucion.getTipo()==1){
                        if(evolucion.getEstado()==1 || evolucion.getEstado()==2){
                            tipo="Evolución M. Especializada";
                        }else if(evolucion.getEstado()==3 || evolucion.getEstado()==4){
                            tipo="Egreso M. Especializada";
                        }
                    }
                    modelo2.setValueAt(tipo, i, 2);
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseReleased

    private void jLabel4MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseMoved
        jLabel4.setBackground(new java.awt.Color(194, 224, 255));//194, 224, 255
    }//GEN-LAST:event_jLabel4MouseMoved

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        jLabel4.setBackground(new java.awt.Color(255, 255, 255));//194, 224, 255
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseReleased
        if (jTable1.getSelectedRow() > -1) {
            InfoHistoriac historiac = null;
            InfoAdmision ia = (InfoAdmision) modelo.getValueAt(jTable1.getSelectedRow(), 0);
            List<InfoHistoriac> historiacs = ia.getInfoHistoriacList();
            for (InfoHistoriac ih : historiacs) {
                if (ih.getEstado() != 0) {
                    historiac = ih;
                    break;
                }
            }
            if (historiac != null) {
                AtencionUrgencia.panelindex.jpContainer.removeAll();
                AtencionUrgencia.panelindex.hc = new HC(factory);
                AtencionUrgencia.panelindex.hc.setBounds(0, 0, 764, 540);
                AtencionUrgencia.panelindex.jpContainer.add(AtencionUrgencia.panelindex.hc);
                AtencionUrgencia.panelindex.hc.setVisible(true);
                AtencionUrgencia.panelindex.jpContainer.validate();
                AtencionUrgencia.panelindex.jpContainer.repaint();
                AtencionUrgencia.panelindex.hc.viewClinicHistory(historiac);
                AtencionUrgencia.panelindex.hc.DatosAntPersonales();
                AtencionUrgencia.panelindex.hc.jButton10.setEnabled(false);
                this.dispose();
            }
        }     
    }//GEN-LAST:event_jLabel4MouseReleased

    private void jLabel6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseMoved
        jLabel6.setBackground(new java.awt.Color(194, 224, 255));//194, 224, 255
    }//GEN-LAST:event_jLabel6MouseMoved

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        jLabel6.setBackground(new java.awt.Color(255, 255, 255));//194, 224, 255
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseReleased
        if (jTable2.getSelectedRow() > -1) {
            HcuEvolucion evolucion = (HcuEvolucion) modelo2.getValueAt(jTable2.getSelectedRow(),0);
            AtencionUrgencia.panelindex.jpContainer.removeAll();
            AtencionUrgencia.panelindex.evo = new Evo(factory);
            AtencionUrgencia.panelindex.evo.setBounds(0, 0, 764, 514);
            AtencionUrgencia.panelindex.jpContainer.add(AtencionUrgencia.panelindex.evo);
            AtencionUrgencia.panelindex.evo.jTabbedPane3.remove(0);
            AtencionUrgencia.panelindex.evo.viewEvoEdit(evolucion);
            AtencionUrgencia.panelindex.evo.setVisible(true); 
            AtencionUrgencia.panelindex.jpContainer.validate();
            AtencionUrgencia.panelindex.jpContainer.repaint();
            
            this.dispose();
        }
    }//GEN-LAST:event_jLabel6MouseReleased

    private void jTable2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseReleased
        if (jTable2.getSelectedRow() > -1) {
            HcuEvolucion evolucion = (HcuEvolucion) modelo2.getValueAt(jTable2.getSelectedRow(),0);
            Configdecripcionlogin c = configdecripcionloginJpaC.findConfigdecripcionlogin(evolucion.getUsuario());
            jLabel5.setText(c.getNombres()+" "+c.getApellidos());
        }
    }//GEN-LAST:event_jTable2MouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
