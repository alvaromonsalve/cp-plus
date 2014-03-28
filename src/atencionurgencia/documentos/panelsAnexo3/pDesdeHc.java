
package atencionurgencia.documentos.panelsAnexo3;

import entidades.HcuEvoInterconsulta;
import entidades.HcuEvoProcedimiento;
import entidades.HcuEvolucion;
import entidades.InfoHistoriac;
import entidades.InfoProcedimientoHcu;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import jpa.ConfigCupsJpaController;
import jpa.HcuEvolucionJpaController;
import jpa.InfoProcedimientoHcuJpaController;
import tools.Funciones;
import tools.JTreeRendererArbolEvo;
import tools.MyDate;

/**
 *
 * @author Alvaro Monsalve
 */
public class pDesdeHc extends javax.swing.JPanel {

    public DefaultTableModel ModeloTabla;
    private HcuEvolucionJpaController hcuEvolucionJpaController = null;
    private InfoProcedimientoHcuJpaController hcuProJpaController = null;
    private ConfigCupsJpaController configCupsJpa = null;
    private final EntityManagerFactory factory;
    private DefaultMutableTreeNode EvosHC;
    private DefaultTreeModel modeloTree;
    private final InfoHistoriac infohistoriac;
    private final Object dato[] = null;

    public pDesdeHc(InfoHistoriac infohistoriac, EntityManagerFactory factory) {
        initComponents();
        this.infohistoriac = infohistoriac;
        this.factory = factory;
        setCargaTabla();
    }

    private DefaultTableModel getModelo() {
        DefaultTableModel model = new DefaultTableModel(
                null, new String[]{"codCups", "CUPS", "Descripcion CUPS", "DescripcionHC"}) {
                    Class[] types = new Class[]{
                        entidades.ConfigCups.class,
                        java.lang.String.class,
                        java.lang.String.class,
                        java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false
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
        Funciones.setOcultarColumnas(jTable1, new int[]{0, 3});
        Funciones.setSizeColumnas(jTable1, new int[]{1}, new int[]{60});
        Funciones.setColorTableHeader(jTable1, new int[]{1, 2, 3});
    }

    public void setJTreeEvo() {
        if (hcuEvolucionJpaController == null) {
            hcuEvolucionJpaController = new HcuEvolucionJpaController(factory);
            hcuProJpaController = new InfoProcedimientoHcuJpaController(factory);
            configCupsJpa = new ConfigCupsJpaController(factory);
        }
        EvosHC = new DefaultMutableTreeNode(infohistoriac);
        modeloTree = new DefaultTreeModel(EvosHC);
        List<HcuEvolucion> hcuEvolucionList = hcuEvolucionJpaController.FindHcuEvolucions(infohistoriac);
        jTree1.setModel(modeloTree);
        jTree1.setCellRenderer(new JTreeRendererArbolEvo());
        for (HcuEvolucion hcuEvo : hcuEvolucionList) {
            TreeNode raiz = (TreeNode) jTree1.getModel().getRoot();
            DefaultMutableTreeNode fechaEvo = null;
            DefaultMutableTreeNode Evo = null;
            boolean existeFechaEvo = false;
            for (int i = 0; i < raiz.getChildCount(); i++) {
                if (raiz.getChildAt(i).toString().equals(MyDate.ddMMyyyy.format(hcuEvo.getFechaEvo()))) {
                    existeFechaEvo = true;
                    fechaEvo = (DefaultMutableTreeNode) modeloTree.getChild(EvosHC, i);
                    Evo = new DefaultMutableTreeNode(hcuEvo);
                    fechaEvo.add(Evo);
                    break;
                }
            }
            if (!existeFechaEvo) {
                fechaEvo = new DefaultMutableTreeNode(MyDate.ddMMyyyy.format(hcuEvo.getFechaEvo()));
                modeloTree.insertNodeInto(fechaEvo, EvosHC, 0);
                Evo = new DefaultMutableTreeNode(hcuEvo);
                fechaEvo.add(Evo);
            }
        }
        jTree1.expandRow(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(477, 278));
        setMinimumSize(new java.awt.Dimension(477, 278));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(477, 278));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTree1MouseReleased(evt);
            }
        });
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jTree1);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTree1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseReleased
        TreePath selPath = jTree1.getPathForLocation(evt.getX(), evt.getY());
        jTree1.setSelectionPath(selPath);
        if (evt.getButton()==MouseEvent.BUTTON1) {
            if (selPath != null) {
                Object nodo = selPath.getLastPathComponent();
                while (ModeloTabla.getRowCount() > 0) {
                    ModeloTabla.removeRow(0);
                }
                if (((DefaultMutableTreeNode) nodo).getUserObject() instanceof HcuEvolucion) {
                    HcuEvolucion evolucion = (HcuEvolucion) ((DefaultMutableTreeNode) nodo).getUserObject();
                    for (HcuEvoProcedimiento hep : evolucion.getHcuEvoProcedimientos()) {
                        int i = ModeloTabla.getRowCount();
                        ModeloTabla.addRow(dato);
                        ModeloTabla.setValueAt(hep.getIdConfigCups(), i, 0);
                        ModeloTabla.setValueAt(hep.getIdConfigCups().getCodigo(), i, 1);
                        ModeloTabla.setValueAt(hep.getIdConfigCups().getDeSubcategoria(), i, 2);
                        ModeloTabla.setValueAt(hep.getObservacion(), i, 3);
                    }
                    for (HcuEvoInterconsulta hei : evolucion.getHcuEvoInterconsultas()) {
                        int i = ModeloTabla.getRowCount();
                        ModeloTabla.addRow(dato);
                        ModeloTabla.setValueAt(hei.getIdConfigCups(), i, 0);
                        ModeloTabla.setValueAt(hei.getIdConfigCups().getCodigo(), i, 1);
                        ModeloTabla.setValueAt(hei.getIdConfigCups().getDeSubcategoria(), i, 2);
                        ModeloTabla.setValueAt(hei.getJustificacion(), i, 3);
                    }
                } else if (((DefaultMutableTreeNode) nodo).getUserObject() instanceof InfoHistoriac) {
                    InfoHistoriac ih = (InfoHistoriac) ((DefaultMutableTreeNode) nodo).getUserObject();
                    List<InfoProcedimientoHcu> procedimientoHcu = hcuProJpaController.ListFindInfoProcedimientoHcu(ih);
                    for (InfoProcedimientoHcu iph : procedimientoHcu) {
                        int i = ModeloTabla.getRowCount();
                        ModeloTabla.addRow(dato);
                        ModeloTabla.setValueAt(configCupsJpa.findConfigCups(iph.getIdCups()), i, 0);
                        ModeloTabla.setValueAt(configCupsJpa.findConfigCups(iph.getIdCups()).getCodigo(), i, 1);
                        ModeloTabla.setValueAt(configCupsJpa.findConfigCups(iph.getIdCups()).getDeSubcategoria(), i, 2);
                        ModeloTabla.setValueAt(configCupsJpa.findConfigCups(iph.getIdCups()), i, 3);
                    }
                }
            }
        }
    }//GEN-LAST:event_jTree1MouseReleased

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged

    }//GEN-LAST:event_jTree1ValueChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved

    }//GEN-LAST:event_jTable1MouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable jTable1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
