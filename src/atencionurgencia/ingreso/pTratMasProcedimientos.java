
package atencionurgencia.ingreso;

import atencionurgencia.AtencionUrgencia;
import entidades.ConfigCups;
import entidades.HcuEvoProcedimiento;
import entidades.HcuEvolucion;
import entidades.InfoHistoriac;
import entidades.InfoProcedimientoHcu;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import jpa.ConfigCupsJpaController;
import jpa.HcuEvoProcedimientoJpaController;
import jpa.HcuEvolucionJpaController;
import jpa.InfoProcedimientoHcuJpaController;
import jpa.StaticEstructuraCupsJpaController;
import jpa.exceptions.NonexistentEntityException;
import other.dSelectProcedimiento;
import tools.Funciones;
import tools.convertores;

/**
 *
 * @author Alvaro Monsalve
 */
public class pTratMasProcedimientos extends javax.swing.JPanel {
    private dSelectProcedimiento dProcedimiento;
    private List<InfoProcedimientoHcu> listInfoProcedimientoHcu;
    private InfoProcedimientoHcuJpaController infoProcedimientoHcuJPA;
    private ConfigCupsJpaController cupsJpaController=null;
    private DefaultTableModel ModeloTabla;
    Object dato[] = null;
    private HcuEvoProcedimientoJpaController hcuEvoProcedimientoJpa=null;
    private List<HcuEvoProcedimiento> listInfoProcedimientoEvo;
    RowSorter<TableModel> sorter;
    private boolean evo=false;
    private StaticEstructuraCupsJpaController estructuraCupsJPA=null;
//    private HcuEvolucionJpaController jpaController=null;

    /**
     * Creates new form pTratMasProcedimientos
     * @param evo
     */
    public pTratMasProcedimientos(boolean evo) {
        initComponents();
        this.evo=evo;
        setCargaTabla();
    }
    
     private DefaultTableModel getModelo(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"entidadProced", "Codigo CUPS", "Descripción Procedimiento","Observacion"}){
        Class[] types = new Class [] {
            ConfigCups.class,
            java.lang.String.class,
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
//        sorter=new TableRowSorter<TableModel>(ModeloTabla);
        jTable1.setModel(ModeloTabla);
//        jTable1.setRowSorter(sorter);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Funciones.setOcultarColumnas(jTable1,new int[]{0,3});
        Funciones.setSizeColumnas(jTable1, new int[]{1}, new int[]{80});
    }
    
    public boolean findCUPS(String text,EntityManagerFactory factory){
        if(estructuraCupsJPA==null){
            estructuraCupsJPA = new StaticEstructuraCupsJpaController(factory);
        }
        ConfigCups cc = estructuraCupsJPA.FindCups(text);
        int rowIndex = ModeloTabla.getRowCount();
        boolean existe=false;
        if(cc!=null){
            for(int i=0;i<ModeloTabla.getRowCount();i++){
                if(cc.getId().equals(((ConfigCups)ModeloTabla.getValueAt(i, 0)).getId())){
                    existe = true;
                    break;
                }
            }
            if(!existe){
                ModeloTabla.addRow(dato);
                ModeloTabla.setValueAt(cc, rowIndex, 0);
                ModeloTabla.setValueAt(cc.getCodigo(), rowIndex, 1);
                ModeloTabla.setValueAt(cc.getDeSubcategoria(), rowIndex, 2);
                ModeloTabla.setValueAt("", rowIndex, 3);
                jTable1.getRowSorter().toggleSortOrder(1);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
        
    }
    
    public void cargaDatoSeleccionado(ConfigCups cc,String observ){
        int rowIndex = ModeloTabla.getRowCount();
        boolean existe=false;
        for(int i=0;i<ModeloTabla.getRowCount();i++){            
            if(cc.getId().equals(((ConfigCups)ModeloTabla.getValueAt(i, 0)).getId())){
                existe = true;
                break;
            }
        }
        if(existe==false){
            ModeloTabla.addRow(dato);
            ModeloTabla.setValueAt(cc, rowIndex, 0);
            ModeloTabla.setValueAt(cc.getCodigo(), rowIndex, 1);
            ModeloTabla.setValueAt(cc.getDeSubcategoria(), rowIndex, 2);
            ModeloTabla.setValueAt(observ, rowIndex, 3);
//            jTable1.getRowSorter().toggleSortOrder(1);
        }
    }
    
    public void saveChanges(EntityManagerFactory factory, InfoHistoriac ihc){
        if(infoProcedimientoHcuJPA == null){
            infoProcedimientoHcuJPA =new InfoProcedimientoHcuJpaController(factory);
        }
        listInfoProcedimientoHcu = infoProcedimientoHcuJPA.ListFindInfoProcedimientoHcu(ihc);
        for(int i=0;i<ModeloTabla.getRowCount();i++){
            InfoProcedimientoHcu procedimientoHcu=null;
            boolean exist=false;
            for (InfoProcedimientoHcu listInfoProcedimientoHcu1 : listInfoProcedimientoHcu) {
                if (((ConfigCups)ModeloTabla.getValueAt(i, 0)).getId() == listInfoProcedimientoHcu1.getIdCups()) {
                    exist=true;
                    procedimientoHcu = listInfoProcedimientoHcu1;
                    break;
                }
            }
            if(!exist){
                procedimientoHcu = new InfoProcedimientoHcu();
                procedimientoHcu.setIdCups(((ConfigCups)ModeloTabla.getValueAt(i, 0)).getId());
                procedimientoHcu.setIdHistoriac(ihc.getId());
                procedimientoHcu.setObservacion(ModeloTabla.getValueAt(i, 3).toString());
                procedimientoHcu.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                procedimientoHcu.setEstado(0);//estado inicial
                infoProcedimientoHcuJPA.create(procedimientoHcu);
            }else{
                if(procedimientoHcu!=null){
                    procedimientoHcu.setObservacion(ModeloTabla.getValueAt(i, 3).toString());
                    procedimientoHcu.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                    try {
                        infoProcedimientoHcuJPA.edit(procedimientoHcu);
                    } catch (NonexistentEntityException ex){
                        JOptionPane.showMessageDialog(null, "10091:\n"+ex.getMessage(), pTratMasProcedimientos.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "10092:\n"+ex.getMessage(), pTratMasProcedimientos.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
        for (InfoProcedimientoHcu listInfoProcedimientoHcu1 : listInfoProcedimientoHcu) {
            if(cupsJpaController==null){
                cupsJpaController=new ConfigCupsJpaController(factory);
            }
            ConfigCups cups = cupsJpaController.findConfigCups(listInfoProcedimientoHcu1.getIdCups());
            if (cups.getIdEstructuraCups().getId() >17 && cups.getIdEstructuraCups().getId() <30) {
                boolean exist=false;
                for (int a = 0; a<ModeloTabla.getRowCount(); a++) {
                    if (listInfoProcedimientoHcu1.getIdCups() == ((ConfigCups)ModeloTabla.getValueAt(a, 0)).getId()) {
                        exist=true;
                        break;
                    }
                }
                if (!exist) {
                    try {
                        infoProcedimientoHcuJPA.destroy(listInfoProcedimientoHcu1.getId());
                    }catch (NonexistentEntityException ex) {
                        JOptionPane.showMessageDialog(null, "10093:\n"+ex.getMessage(), pTratMasProcedimientos.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }
    
    public void saveChanges(EntityManagerFactory factory,HcuEvolucion evo){
        if(hcuEvoProcedimientoJpa==null)
            hcuEvoProcedimientoJpa=new HcuEvoProcedimientoJpaController(factory);
        listInfoProcedimientoEvo = evo.getHcuEvoProcedimientos();
        for(int i=0;i<ModeloTabla.getRowCount();i++){            
            HcuEvoProcedimiento hcuEvoProcedimiento=null;
            boolean exist=false;
            for (HcuEvoProcedimiento listInfoProcedimientoEvo1 : listInfoProcedimientoEvo) {
                hcuEvoProcedimiento = listInfoProcedimientoEvo1;
                if (((ConfigCups)ModeloTabla.getValueAt(i, 0)).equals(listInfoProcedimientoEvo1.getIdConfigCups())) {
                    exist=true;
                    break;
                }
            }
            if(!exist){
                hcuEvoProcedimiento=new HcuEvoProcedimiento();
                hcuEvoProcedimiento.setIdConfigCups((ConfigCups)ModeloTabla.getValueAt(i, 0));
                hcuEvoProcedimiento.setIdHcuEvolucion(evo);
                hcuEvoProcedimiento.setObservacion(ModeloTabla.getValueAt(i, 3).toString());
                hcuEvoProcedimiento.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                hcuEvoProcedimiento.setEstado(1);//estado activo                
                hcuEvoProcedimientoJpa.create(hcuEvoProcedimiento);
                listInfoProcedimientoEvo.add(hcuEvoProcedimiento);
                evo.setHcuEvoProcedimientos(listInfoProcedimientoEvo);    
            }else{
                if(hcuEvoProcedimiento!=null){
                    try {                        
                        hcuEvoProcedimiento.setObservacion(ModeloTabla.getValueAt(i, 3).toString());
                        hcuEvoProcedimiento.setIdUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                        hcuEvoProcedimientoJpa.edit(hcuEvoProcedimiento);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "10127:\n"+ex.getMessage(), pTratMasProcedimientos.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
        for (HcuEvoProcedimiento hcuEvoProcedimiento : listInfoProcedimientoEvo) {            
            boolean exist=false;
            for (int a = 0; a<ModeloTabla.getRowCount(); a++) {
                if (hcuEvoProcedimiento.getIdConfigCups().equals((ConfigCups)ModeloTabla.getValueAt(a, 0))) {
                    exist=true;
                    break;                    
                }
            }
            if(!exist){
                try {
                    hcuEvoProcedimiento.setEstado(0);//incativo
                    hcuEvoProcedimientoJpa.edit(hcuEvoProcedimiento);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "10128:\n"+ex.getMessage(), pTratMasProcedimientos.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    public void showListExistentes(EntityManagerFactory factory,InfoHistoriac ihc){
        if(infoProcedimientoHcuJPA == null){
            infoProcedimientoHcuJPA =new InfoProcedimientoHcuJpaController(factory);
        }
        if(cupsJpaController==null){
            cupsJpaController=new ConfigCupsJpaController(factory);
        }
        listInfoProcedimientoHcu = infoProcedimientoHcuJPA.ListFindInfoProcedimientoHcu(ihc);
        for(int i=0;i<listInfoProcedimientoHcu.size();i++){
            ConfigCups cups = cupsJpaController.findConfigCups(listInfoProcedimientoHcu.get(i).getIdCups());
            if(cups.getIdEstructuraCups().getId() >17 && cups.getIdEstructuraCups().getId() <30){
                this.cargaDatoSeleccionado(cups,listInfoProcedimientoHcu.get(i).getObservacion());
            }
        }
    }
    
    public void showListExistentes(EntityManagerFactory factory,HcuEvolucion evol){
        if(hcuEvoProcedimientoJpa==null)
            hcuEvoProcedimientoJpa = new HcuEvoProcedimientoJpaController(factory);
        if(cupsJpaController==null)
            cupsJpaController=new ConfigCupsJpaController(factory);        
        listInfoProcedimientoEvo = hcuEvoProcedimientoJpa.ListFindInfoProcedimientoEvo(evol);
        for (HcuEvoProcedimiento listInfoProcedimientoEvo1 : listInfoProcedimientoEvo) {
            ConfigCups cups = cupsJpaController.findConfigCups(listInfoProcedimientoEvo1.getIdConfigCups().getId());
            this.cargaDatoSeleccionado(cups, listInfoProcedimientoEvo1.getObservacion());
        }        
//        /* Comprobamos que la evolucion no contiene procedimientos */
//        if(listInfoProcedimientoEvo.isEmpty()){
//            List<HcuEvoProcedimiento> evoProcedimientos=null;
//            HcuEvolucionJpaController hejc = new HcuEvolucionJpaController(factory);
//            /* consulta de las evoluciones que pertenecen a la nota de ingreso */
//            List<HcuEvolucion> hes= FindHcuEvolucions(evol.getIdInfoHistoriac(),hejc);
//            /*
//             * verifica si tiene evoluciones y toma la ultima listada.
//             * el orden de la fecha es ascendente
//             */
//            if(!hes.isEmpty()){
//                evoProcedimientos = hes.get(hes.size()-1).getHcuEvoProcedimientos();
//                 /* verificamos si la ultima evolucion tiene procedimientos. 
//                 *   --> si tiene procedimientos en la ultima evolucion hacemos la migracion
//                 *   --> a la evolucion que se esta creando
//                 */
//                if(!evoProcedimientos.isEmpty() && evol.getFechaEvo().compareTo(hes.get(hes.size()-1).getFechaEvo())>0 ){
//                    /* preguntamos si se desea hacer la migracion */
////                    migrarProcedimToEvo(hes, evo, factory);//Se quito la funcionalidad de migrar de otra evolucion CAUCASIA 10/02/2014 A LAS 3:25 PM
//                }
//            }else{
//                HcuEvoProcedimientoJpaController hepjc = new HcuEvoProcedimientoJpaController(factory);
//                List<HcuEvoProcedimiento> heps = hepjc.ListFindInfoProcedimientoEvo(evol);
//                /* verificamos que la hcu tiene procedimientos */
//                if(!heps.isEmpty()){
//                    migrarProcedimToEvo(heps, evo, factory);
//                }
//            }
//        }
    }
    
//    public List<HcuEvolucion> FindHcuEvolucions(InfoHistoriac ihc,HcuEvolucionJpaController hejc){
//        EntityManager em = hejc.getEntityManager();
//        try {
//            return em.createQuery("SELECT h FROM HcuEvolucion h WHERE h.idInfoHistoriac = :hc AND h.estado = '2' ORDER BY h.fechaEvo ASC")
//            .setParameter("hc", ihc)
//            .setHint("javax.persistence.cache.storeMode", "REFRESH")
//            .getResultList();
//        } finally {
//            em.close();
//        }
//    }
    
//    private void migrarProcedimToEvo(Object proceds, boolean evo, EntityManagerFactory factory){//Se quito la funcionalidad de migrar de otra evolucion CAUCASIA 10/02/2014 A LAS 3:25 PM
////        String mensaje = "¿Quiere continuar con los procedimientos de la Nota de Ingreso? ";
//        String mensaje = "Seleccione procedimiento";
//        if(evo)
////            mensaje = "¿Quiere continuar con los procedimientos de la Evolución anterior? ";
//            mensaje = "Seleccione procedimiento";
////        int entrada = JOptionPane.showConfirmDialog(null, mensaje,"Migración de procedimientos",JOptionPane.YES_NO_OPTION);
//        int entrada = JOptionPane.showConfirmDialog(null, mensaje,"Seleccion procedimiento",JOptionPane.DEFAULT_OPTION);
//        entrada = 1;
//        if(entrada==0){
//            if(evo){
//                List<HcuEvoProcedimiento> evoProceds = (List<HcuEvoProcedimiento>) proceds;
//                for(int i=0;i<evoProceds.size();i++){
//                    ModeloTabla.addRow(dato);
//                    ModeloTabla.setValueAt(evoProceds.get(i).getIdConfigCups(), i, 0);
//                    ModeloTabla.setValueAt(evoProceds.get(i).getIdConfigCups().getCodigo(), i, 1);
//                    ModeloTabla.setValueAt(evoProceds.get(i).getIdConfigCups().getDeSubcategoria(), i, 2);
//                    ModeloTabla.setValueAt(evoProceds.get(i).getObservacion(), i, 3);
//                    jTable1.getRowSorter().toggleSortOrder(1);
//                }
//            }else{
//                if(cupsJpaController==null)
//                    cupsJpaController=new ConfigCupsJpaController(factory);                
//                List<InfoProcedimientoHcu> infoProcedimientoHcus = (List<InfoProcedimientoHcu>) proceds;
//                for(int i=0;i<infoProcedimientoHcus.size();i++){
//                    ConfigCups cups = cupsJpaController.findConfigCups(infoProcedimientoHcus.get(i).getIdCups());
//                    ModeloTabla.addRow(dato);
//                    ModeloTabla.setValueAt(cups.getId(), i, 0);
//                    ModeloTabla.setValueAt(cups.getCodigo(), i, 1);
//                    ModeloTabla.setValueAt(cups.getDeSubcategoria(), i, 2);
//                    ModeloTabla.setValueAt(infoProcedimientoHcus.get(i).getObservacion(), i, 3);
//                    jTable1.getRowSorter().toggleSortOrder(1);
//                }
//            }
//        }
//    }
    
    public void formularioOpen(int tipo){
        dProcedimiento = new dSelectProcedimiento(null,true,tipo,evo);//0
        dProcedimiento.setVisible(true);
    }
    
    /**
     * Retorna el estado sobre los registros de las tablas
     * @return true si alguna de las tablas tienen registros y false si las dos tablas estan vacias
     */  
    public boolean estadoTablas(){
        return ModeloTabla.getRowCount()>0;   
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel51 = new javax.swing.JLabel();
        buttonSeven6 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven7 = new org.edisoncor.gui.button.ButtonSeven();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea25 = new javax.swing.JTextArea();

        setMaximumSize(new java.awt.Dimension(380, 420));
        setMinimumSize(new java.awt.Dimension(380, 420));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(380, 420));

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Hospital.png"))); // NOI18N
        jLabel51.setText("Mas Procedimientos");

        buttonSeven6.setForeground(new java.awt.Color(0, 0, 255));
        buttonSeven6.setText("Agregar");
        buttonSeven6.setColorBrillo(new java.awt.Color(255, 255, 255));
        buttonSeven6.setColorDeSombra(new java.awt.Color(255, 255, 255));
        buttonSeven6.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        buttonSeven6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven6ActionPerformed(evt);
            }
        });

        buttonSeven7.setForeground(new java.awt.Color(0, 0, 255));
        buttonSeven7.setText("Borrar");
        buttonSeven7.setColorBrillo(new java.awt.Color(255, 255, 255));
        buttonSeven7.setColorDeSombra(new java.awt.Color(255, 255, 255));
        buttonSeven7.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        buttonSeven7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven7ActionPerformed(evt);
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

        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
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
            .addComponent(jScrollPane25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonSeven6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSeven6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonSeven6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven6ActionPerformed
        formularioOpen(0);
    }//GEN-LAST:event_buttonSeven6ActionPerformed

    private void buttonSeven7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven7ActionPerformed
        if(ModeloTabla.getRowCount()>0 && jTable1.getSelectedRow()>-1){
            ModeloTabla = (DefaultTableModel) jTable1.getModel();
            ModeloTabla.removeRow(jTable1.getSelectedRow());
        }
    }//GEN-LAST:event_buttonSeven7ActionPerformed

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved
        int rowIndex = jTable1.rowAtPoint(evt.getPoint());
        jTable1.setToolTipText(convertores.getTextToHtml((String)jTable1.getValueAt(rowIndex, 2)));
    }//GEN-LAST:event_jTable1MouseMoved

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==1){
            jTextArea25.setText((String)jTable1.getValueAt(jTable1.getSelectedRow(), 3));
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextArea25KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea25KeyReleased
        if(jTable1.getSelectedRow()>-1){
           ModeloTabla.setValueAt(jTextArea25.getText().toUpperCase(), jTable1.getSelectedRow(), 3);
       }
    }//GEN-LAST:event_jTextArea25KeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public org.edisoncor.gui.button.ButtonSeven buttonSeven6;
    public org.edisoncor.gui.button.ButtonSeven buttonSeven7;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane25;
    public javax.swing.JTable jTable1;
    public javax.swing.JTextArea jTextArea25;
    // End of variables declaration//GEN-END:variables
}
