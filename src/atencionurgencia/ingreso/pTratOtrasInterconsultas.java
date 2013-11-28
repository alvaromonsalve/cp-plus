/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.ingreso;

import atencionurgencia.AtencionUrgencia;
import entidades.ConfigCups;
import entidades.HcuEvoInterconsulta;
import entidades.HcuEvolucion;
import entidades.InfoHistoriac;
import entidades.InfoInterconsultaHcu;
import entidades.StaticEspecialidades;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jpa.HcuEvoInterconsultaJpaController;
import jpa.InfoInterconsultaHcuJpaController;
import jpa.StaticEspecialidadesJpaController;
import jpa.exceptions.NonexistentEntityException;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class pTratOtrasInterconsultas extends javax.swing.JPanel {
    
    private DefaultTableModel ModeloTabla;
    Object dato[] = null;
    private EntityManagerFactory factory;
    private List<InfoInterconsultaHcu> listInterconsultaHcu;
    private InfoInterconsultaHcu interconsultaHcu;
    private InfoInterconsultaHcuJpaController interconsultaHcuJPA=null;
    private List<StaticEspecialidades> listStaticEspecialidades;
    private StaticEspecialidadesJpaController staticEspecialidadesJPA=null;
    private HcuEvoInterconsultaJpaController evoInterconsultaJpa=null;
    private List<HcuEvoInterconsulta> listInterconsultaEvo=null;

    /**
     * Creates new form pTratOtrasInterconsultas
     */
    public pTratOtrasInterconsultas() {
        initComponents();
        setCargaTabla();
    }
    
    
    
    private DefaultTableModel getModelo(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"e_especialidad","select" ,"nombre","Justificacion"}){
        Class[] types = new Class [] {
            StaticEspecialidades.class,
            javax.swing.JCheckBox.class,
            java.lang.String.class,
            java.lang.String.class
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
        Funciones.setOcultarColumnas(jTable1,new int[]{0,3});
        Funciones.setSizeColumnas(jTable1, new int[]{1}, new int[]{20});
        jTable1.setDefaultRenderer(Object.class, new tools.IconCellRenderer());
        jTable1.setTableHeader(null);
    }
    
    public void showLista(InfoHistoriac ihc){
        if(interconsultaHcuJPA==null){
            factory = Persistence.createEntityManagerFactory("ClipaEJBPU",AtencionUrgencia.props); 
            interconsultaHcuJPA = new InfoInterconsultaHcuJpaController(factory);
        }
        if(staticEspecialidadesJPA==null){
            staticEspecialidadesJPA = new StaticEspecialidadesJpaController(factory);
        }
        listStaticEspecialidades=staticEspecialidadesJPA.findStaticEspecialidadesEntities();
        listInterconsultaHcu = interconsultaHcuJPA.listInterconsultaOtrasHcu_HC(ihc);
        for(StaticEspecialidades se:listStaticEspecialidades){
            if(se.getId()!=5 && se.getId() !=16 && se.getId() !=23 && se.getId() !=36 ){
                if(se.getEstado()==1){
                    int row=ModeloTabla.getRowCount();
                    ModeloTabla.addRow(dato);
                    ModeloTabla.setValueAt(se, row, 0);
                    boolean exist = false;
                    for(InfoInterconsultaHcu iih:listInterconsultaHcu){
                        if(se.getId()==iih.getIdEspecialidades()){
                            exist=true;
                            ModeloTabla.setValueAt(iih.getJustificacion(), row, 3);
                            break;                        
                        }else{
                            ModeloTabla.setValueAt("", row, 3);
                        }
                    }
                    JCheckBox jcb = new JCheckBox();
                    jcb.setSelected(exist);
                    ModeloTabla.setValueAt(jcb, row, 1);
                    ModeloTabla.setValueAt(se.getEspecialidad(), row, 2);
                }

            }
        }
    }
    
    public void showLista(EntityManagerFactory factory, HcuEvolucion evol){
        if(evoInterconsultaJpa==null)
            evoInterconsultaJpa=new HcuEvoInterconsultaJpaController(factory);
        if(staticEspecialidadesJPA==null)
            staticEspecialidadesJPA=new StaticEspecialidadesJpaController(factory);
        listStaticEspecialidades=staticEspecialidadesJPA.findStaticEspecialidadesEntities();
        listInterconsultaEvo = evoInterconsultaJpa.listInterconsultaOtrasEvo(evol);
        for(StaticEspecialidades se:listStaticEspecialidades){
            if(se.getId()!=5 && se.getId() !=16 && se.getId() !=23 && se.getId() !=36 ){
                if(se.getEstado()==1){                    
                    int row=ModeloTabla.getRowCount();
                    ModeloTabla.addRow(dato);
                    ModeloTabla.setValueAt(se, row, 0);
                    boolean exist = false;
                    for(HcuEvoInterconsulta hei:listInterconsultaEvo){  
                        if(se.getEspecialidad().equals(hei.getIdStaticEspecialidades().getEspecialidad())){
                            exist=true;
                            ModeloTabla.setValueAt(hei.getJustificacion(), row, 3);
                            break;               
                        }else{
                            ModeloTabla.setValueAt("", row, 3);
                        }
                    }
                    JCheckBox jcb = new JCheckBox();
                    jcb.setSelected(exist);
                    ModeloTabla.setValueAt(jcb, row, 1);
                    ModeloTabla.setValueAt(se.getEspecialidad(), row, 2);
                }
            }
        }
    }
    
    public void saveChanges(InfoHistoriac ih){
        if(interconsultaHcuJPA ==null){
            interconsultaHcuJPA = new InfoInterconsultaHcuJpaController(factory);
        }
        listInterconsultaHcu = interconsultaHcuJPA.listInterconsultaOtrasHcu_HC(ih);
        for(int i=0;i<ModeloTabla.getRowCount();i++){
            if(((JCheckBox)ModeloTabla.getValueAt(i, 1)).isSelected()){
                boolean exist=false;
                for(InfoInterconsultaHcu iih:listInterconsultaHcu){
                    if(((StaticEspecialidades)ModeloTabla.getValueAt(i, 0)).getId()==iih.getIdEspecialidades()){
                        exist=true;
                        interconsultaHcu = iih;
                        break;                        
                    }
                }
                if(exist){
                    try {
                        interconsultaHcu.setJustificacion((String)ModeloTabla.getValueAt(i, 3));
                        interconsultaHcu.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                        interconsultaHcuJPA.edit(interconsultaHcu);
                    } catch (NonexistentEntityException ex) {
                        JOptionPane.showMessageDialog(null, "10097:\n"+ex.getMessage(), pTratOtrasInterconsultas.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "10098:\n"+ex.getMessage(), pTratOtrasInterconsultas.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }else{
                    interconsultaHcu=new InfoInterconsultaHcu();
                    interconsultaHcu.setIdEspecialidades(((StaticEspecialidades)ModeloTabla.getValueAt(i, 0)).getId());
                    interconsultaHcu.setIdHistoriac(ih.getId());
                    interconsultaHcu.setJustificacion((String)ModeloTabla.getValueAt(i, 3));
                    interconsultaHcu.setIdConfigCups(5129);// id 5129 de codigo cups 890702 - CONSULTA DE URGENCIAS, POR MEDICINA ESPECIALIZADA
                    interconsultaHcu.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                    interconsultaHcuJPA.create(interconsultaHcu);
                }
            }else{
                boolean exist=false;
                for(InfoInterconsultaHcu iih:listInterconsultaHcu){
                    if(iih.getIdEspecialidades()==((StaticEspecialidades)ModeloTabla.getValueAt(i, 0)).getId()){
                        exist=true;
                        interconsultaHcu = iih;
                        break; 
                    }
                }
                if(exist){
                    try {
                        interconsultaHcuJPA.destroy(interconsultaHcu.getId());
                    } catch (NonexistentEntityException ex) {
                        JOptionPane.showMessageDialog(null, "10099:\n"+ex.getMessage(), pTratOtrasInterconsultas.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }
    
    public void saveChanges(EntityManagerFactory factory, HcuEvolucion evol){
        if(evoInterconsultaJpa==null)
            evoInterconsultaJpa=new HcuEvoInterconsultaJpaController(factory);
        listInterconsultaEvo = evoInterconsultaJpa.listInterconsultaOtrasEvo(evol);
        HcuEvoInterconsulta evoInter = null;
        for(int i=0;i<ModeloTabla.getRowCount();i++){
            if(((JCheckBox)ModeloTabla.getValueAt(i, 1)).isSelected()){
                boolean exist=false;
                for(HcuEvoInterconsulta hei: listInterconsultaEvo){
                    if(((StaticEspecialidades)ModeloTabla.getValueAt(i, 0))==hei.getIdStaticEspecialidades()){
                        exist=true;
                        evoInter = hei;
                        break;
                    }
                }
                if(exist){
                    try {
                        evoInter.setJustificacion((String)ModeloTabla.getValueAt(i, 3));
                        evoInter.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                        evoInterconsultaJpa.edit(evoInter);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "10125:\n"+ex.getMessage(), pTratOtrasInterconsultas.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }else{
                    evoInter=new HcuEvoInterconsulta();
                    evoInter.setIdStaticEspecialidades(((StaticEspecialidades)ModeloTabla.getValueAt(i, 0)));
                    evoInter.setIdHcuEvolucion(evol);
                    evoInter.setJustificacion((String)ModeloTabla.getValueAt(i, 3));
                    evoInter.setIdConfigCups(new ConfigCups(5129));
                    evoInter.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                    evoInter.setEstado(1);
                    evoInterconsultaJpa.create(evoInter);                    
                }
            }else{
                boolean exist=false;
                for(HcuEvoInterconsulta hei: listInterconsultaEvo){
                    if(hei.getIdStaticEspecialidades() == ((StaticEspecialidades)ModeloTabla.getValueAt(i, 0))){
                        exist=true;
                        evoInter = hei;
                        break;
                    }
                }
                if(exist){
                    try {
                        evoInter.setEstado(0);//activo
                        evoInterconsultaJpa.edit(evoInter);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "10126:\n"+ex.getMessage(), pTratOtrasInterconsultas.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel49 = new javax.swing.JLabel();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea25 = new javax.swing.JTextArea();

        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(380, 420));
        setMinimumSize(new java.awt.Dimension(380, 420));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(380, 420));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Hospital.png"))); // NOI18N
        jLabel49.setText("Otras Especialidades");

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
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane27.setViewportView(jTable1);

        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder("Justificacion"));
        jPanel31.setOpaque(false);

        jScrollPane25.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane25.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane25.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea25.setColumns(20);
        jTextArea25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea25.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea25.setLineWrap(true);
        jTextArea25.setRows(2);
        jTextArea25.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea25.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextArea25KeyReleased(evt);
            }
        });
        jScrollPane25.setViewportView(jTextArea25);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel49)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
            if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==1){
            jTextArea25.setText((String)ModeloTabla.getValueAt(jTable1.getSelectedRow(), 3));
            }
            if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==2){
                JCheckBox box = new JCheckBox();
                if(((JCheckBox)ModeloTabla.getValueAt(jTable1.getSelectedRow(), 1)).isSelected()){
                    box.setSelected(false);
                }else{
                    box.setSelected(true);
                }
                ModeloTabla.setValueAt(box, jTable1.getSelectedRow(), 1);        
            }     
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextArea25KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea25KeyReleased
        if(jTable1.getSelectedRow()>-1){
            if(((JCheckBox)ModeloTabla.getValueAt(jTable1.getSelectedRow(), 1)).isSelected()){
                ModeloTabla.setValueAt(jTextArea25.getText().toUpperCase(), jTable1.getSelectedRow(), 3);
            }            
        }
    }//GEN-LAST:event_jTextArea25KeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel49;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JTable jTable1;
    public javax.swing.JTextArea jTextArea25;
    // End of variables declaration//GEN-END:variables
}
