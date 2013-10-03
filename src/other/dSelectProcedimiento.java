
package other;


import atencionurgencia.AtencionUrgencia;
import entidades.ConfigCups;
import entidades.StaticEstructuraCups;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import jpa.ConfigCupsJpaController;
import jpa.StaticEstructuraCupsJpaController;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class dSelectProcedimiento extends javax.swing.JDialog {
    private int tipo;
    private DefaultTableModel ModeloTabla;
    private EntityManagerFactory factory;
    private List<ConfigCups> listaCups;
    private List<StaticEstructuraCups> listaEstructuraCups;
    private StaticEstructuraCupsJpaController estructuraCupsJPA=null;
    private ConfigCupsJpaController configCupsJPA=null;
    Object dato[] = null;
    
    
    /**
     * 
     * @param tipo tipo de procedimiento
     * 0 = sin seleccion
     */
    public dSelectProcedimiento(java.awt.Frame parent, boolean modal,int tipo) {
        super(parent,modal);
        initComponents();
        setCargaTabla();
        setCargaCombo(tipo);
        this.tipo=tipo;
    }
    
    private DefaultTableModel getModelo(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"entidadProced", "Codigo CUPS", "Descripción Procedimiento"}){
        Class[] types = new Class [] {
            ConfigCups.class,
            java.lang.String.class,
            java.lang.String.class
        };

        boolean[] canEdit = new boolean [] {
        false,false,false
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
        jTable1.getColumnModel().getColumn(1).setMinWidth(80);
        jTable1.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(80);
    }
    
    
    /**
     * @param tipo TIPO DE PROCEDIMIENTO CUPS
     * 
     * 1 = capitulo 16 - CONSULTA, MONITORIZACION Y PROCEDIMIENTOS DIAGNOSTICOS
     * 0 = TODOS LOS CAPITULOS EXCEPTO LOS SH
     */
    private void setCargaCombo(int tipo){
        if(estructuraCupsJPA==null){
            factory = Persistence.createEntityManagerFactory("ClipaEJBPU",AtencionUrgencia.props);
            estructuraCupsJPA = new StaticEstructuraCupsJpaController(factory);
        }
        listaEstructuraCups = estructuraCupsJPA.findStaticEstructuraCupsEntities();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        jComboBox4.setModel(dcbm);
        for(StaticEstructuraCups sec:listaEstructuraCups){
            if(tipo==0){
                if(sec.getId()>17 && sec.getId()<30){
                    dcbm.addElement(sec);                    
                }
            }else if(tipo>0 && tipo <=14){
                if(sec.getId()>0 && sec.getId()<=14){
                    sec.setDesCapitulo(renombrarString(sec.getDesCapitulo()));
                    dcbm.addElement(sec);
                }
            }else if(tipo==16){
                if(sec.getId()==16){
                    sec.setDesCapitulo("MONITORIZACION Y PROCEDIMIENTOS DIAGNOSTICOS");
                    dcbm.addElement(sec);
                    jComboBox4.setEnabled(false);
                    setDatosTabla();
                }
            }else if(tipo==17){
                if(sec.getId()==17){
                    dcbm.addElement(sec);
                    jComboBox4.setEnabled(false);
                    setDatosTabla();
                }
            }else if(tipo==15){
                if(sec.getId()==15){
                    dcbm.addElement(sec);
                    jComboBox4.setEnabled(false);
                    setDatosTabla();
                }
            }
        }
    }
    
    private String renombrarString(String val){
        String[] arrayText = val.split(" - ");
        return arrayText[0];
    }
    
    private void setDatosTabla(){
        if(configCupsJPA==null){
            configCupsJPA=new ConfigCupsJpaController(factory);
        }
        while(ModeloTabla.getRowCount()>0){
            ModeloTabla.removeRow(0);
        }
        listaCups = configCupsJPA.listConfigCups((StaticEstructuraCups)jComboBox4.getSelectedItem());
        for(int i=0;i<listaCups.size();i++){
            ModeloTabla.addRow(dato);
            ModeloTabla.setValueAt(listaCups.get(i), i, 0);
            ModeloTabla.setValueAt(listaCups.get(i).getCodigo(), i, 1);
            ModeloTabla.setValueAt(listaCups.get(i).getDeSubcategoria(), i, 2);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel34 = new javax.swing.JLabel();
        jComboBox4 = new org.jdesktop.swingx.JXComboBox();
        jLabel35 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel37 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel34.setForeground(new java.awt.Color(0, 51, 255));
        jLabel34.setText("Tipo");

        jComboBox4.setForeground(new java.awt.Color(0, 102, 255));
        jComboBox4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel35.setForeground(new java.awt.Color(0, 51, 255));
        jLabel35.setText("Buscar");

        jTextField14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(0, 102, 255));
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField14KeyReleased(evt);
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

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setText("PROCEDIMIENTOS (LISTADO DE PROCEDIMIENTOS)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField14)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyReleased
        TableRowSorter sorter = new TableRowSorter(ModeloTabla);
        sorter.setRowFilter (RowFilter.regexFilter(jTextField14.getText().toUpperCase()));
        jTable1.setRowSorter(sorter);
    }//GEN-LAST:event_jTextField14KeyReleased

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved
                int rowIndex = jTable1.rowAtPoint(evt.getPoint());
        jTable1.setToolTipText("<html>\n" +
        "<div style=\"width:300;\">"+(String)jTable1.getValueAt(rowIndex, 2)+"</div>\n" +
        "\n" +
        "</html>");
    }//GEN-LAST:event_jTable1MouseMoved

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==2){
            if(tipo==16){
                atencionurgencia.AtencionUrgencia.panelindex.hc.pConsultDiag.cargaDatoSeleccionado(
                    (ConfigCups) jTable1.getValueAt(jTable1.getSelectedRow(), 0),"");
            }else if(tipo==17){
                atencionurgencia.AtencionUrgencia.panelindex.hc.pLaboratorio.cargaDatoSeleccionado(
                    (ConfigCups) jTable1.getValueAt(jTable1.getSelectedRow(), 0),"");
            }else if(tipo==15){
                atencionurgencia.AtencionUrgencia.panelindex.hc.pImagenologia.cargaDatoSeleccionado(
                    (ConfigCups) jTable1.getValueAt(jTable1.getSelectedRow(), 0),"");
            }else if(tipo>0 && tipo <=14){
                atencionurgencia.AtencionUrgencia.panelindex.hc.pQuirurgico.cargaDatoSeleccionado(
                    (ConfigCups) jTable1.getValueAt(jTable1.getSelectedRow(), 0),"");
            }else if(tipo==0){
                atencionurgencia.AtencionUrgencia.panelindex.hc.pProcedimientos.cargaDatoSeleccionado(
                    (ConfigCups) jTable1.getValueAt(jTable1.getSelectedRow(), 0),"");
            }
            dispose();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        if(((javax.swing.JComboBox) evt.getSource()).getSelectedIndex()>-1){
            setDatosTabla();
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public org.jdesktop.swingx.JXComboBox jComboBox4;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    public javax.swing.JTextField jTextField14;
    // End of variables declaration//GEN-END:variables
}
