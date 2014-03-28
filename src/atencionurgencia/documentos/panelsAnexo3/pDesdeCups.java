package atencionurgencia.documentos.panelsAnexo3;

import entidades.ConfigCups;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import jpa.ConfigCupsJpaController;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class pDesdeCups extends javax.swing.JPanel {
    private final EntityManagerFactory factory;
    public DefaultTableModel ModeloTabla;
    private ConfigCupsJpaController configCupsJpa = null;
    private final Object dato[] = null;

    public pDesdeCups(EntityManagerFactory factory) {
        initComponents();
        this.factory = factory;
        setCargaTabla();
        getDataTable() ;
    }

    private DefaultTableModel getModelo() {
        DefaultTableModel model = new DefaultTableModel(
                null, new String[]{"codCups", "CUPS", "Descripcion CUPS"}) {
                    Class[] types = new Class[]{
                        entidades.ConfigCups.class,
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
                };
        return model;
    }

    private void setCargaTabla() {
        ModeloTabla = getModelo();
        jTable1.setModel(ModeloTabla);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Funciones.setOcultarColumnas(jTable1, new int[]{0});
        Funciones.setSizeColumnas(jTable1, new int[]{1}, new int[]{60});
        Funciones.setColorTableHeader(jTable1, new int[]{1, 2});
    }
    
    private void getDataTable() {
        if (configCupsJpa == null) {
            configCupsJpa = new ConfigCupsJpaController(factory);
        }
        List<ConfigCups> lcc = configCupsJpa.findConfigCupsEntities();
        while (ModeloTabla.getRowCount() > 0) {
            ModeloTabla.removeRow(0);
        }
        for (int i = 0; i < lcc.size(); i++) {
            ModeloTabla.addRow(dato);
            ModeloTabla.setValueAt(lcc.get(i), i, 0);
            ModeloTabla.setValueAt(lcc.get(i).getCodigo(), i, 1);
            ModeloTabla.setValueAt(lcc.get(i).getDeSubcategoria(), i, 2);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane27 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(477, 278));
        setMinimumSize(new java.awt.Dimension(477, 278));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(477, 278));

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
        jTable1.setFocusable(false);
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
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
        jScrollPane27.setViewportView(jTable1);

        jLabel1.setText("Filtrar");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved

    }//GEN-LAST:event_jTable1MouseMoved

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        TableRowSorter sorter = new TableRowSorter(ModeloTabla);
        sorter.setRowFilter (RowFilter.regexFilter(jTextField1.getText().toUpperCase()));
        jTable1.setRowSorter(sorter);
    }//GEN-LAST:event_jTextField1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane27;
    public javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
