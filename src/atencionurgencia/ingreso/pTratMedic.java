/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.ingreso;


import atencionurgencia.AtencionUrgencia;
import atencionurgencia.ListadoPacientes.addMedicCombinados;
import entidades.InfoHistoriac;
import entidades.InfoPosologiaHcu;
import entidades.SumSuministro;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jpa.InfoPosologiaHcuJpaController;
import jpa.exceptions.NonexistentEntityException;
import atencionurgencia.ListadoPacientes.addMedicamentos;
import entidades.HcuMezclasMedicamentos;
import entidades.HcuMezclasMedicamentosDesc;
import jpa.HcuMezclasMedicamentosDescJpaController;
import jpa.HcuMezclasMedicamentosJpaController;
import jpa.exceptions.IllegalOrphanException;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class pTratMedic extends javax.swing.JPanel {
    private DefaultTableModel ModeloTabla, MTMezcla;
    private Object dato[] = null;
    private addMedicamentos medicamentos=null;
    private addMedicCombinados medicCombinados=null;
    private InfoPosologiaHcuJpaController infoPosologiaHcuJPA=null;
    private JPopupMenu pop=null;
    private pDatosMedic p=null;
    private pDatosMedicCombi pc=null;
    private HcuMezclasMedicamentosJpaController hcuMezclasMedicamentosJPA = null;
    private HcuMezclasMedicamentosDescJpaController descJpaController=null;


    /**
     * Creates new form pTratMedic
     */
    public pTratMedic() {
        initComponents();
        setCargaTabla();
        setCargaTablaMezcla();
    }
    
    private DefaultTableModel getModelo(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"principio Activo","dato", "dosis1","dosis2","via","observ","dosisT","cantidad"}){
        Class[] types = new Class [] {
            entidades.SumSuministro.class,
            javax.swing.JLabel.class,
            java.lang.Float.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class
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
    
    private DefaultTableModel getModeloMezcla(){
        DefaultTableModel model = new DefaultTableModel(
        null, new String [] {"modelo","principio Activo","dato","via","observ"}){
        Class[] types = new Class [] {
            DefaultTableModel.class,
            java.lang.String.class,
            javax.swing.JLabel.class,
            java.lang.String.class,
            java.lang.String.class
        };

        boolean[] canEdit = new boolean [] {
        false,false,false,false,false
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
        Funciones.setSizeColumnas(jTable1,new int[]{1},new int[]{20});
        Funciones.setOcultarColumnas(jTable1,new int[]{2,3,4,5,6,7});
        jTable1.setDefaultRenderer(Object.class, new tools.IconCellRenderer());
        jTable1.setTableHeader(null);
    }
    
    private void setCargaTablaMezcla(){
        MTMezcla=getModeloMezcla();
        jTable2.setModel(MTMezcla);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Funciones.setSizeColumnas(jTable2,new int[]{2},new int[]{20});
        Funciones.setOcultarColumnas(jTable2,new int[]{0,3,4});
        jTable2.setDefaultRenderer(Object.class, new tools.IconCellRenderer());
        jTable2.setTableHeader(null);
    }
    
    public void addMezcla(DefaultTableModel modelo, String StringVia, String Observacion){
        int row = MTMezcla.getRowCount();
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/idea.png"));
        MTMezcla.addRow(dato);
        MTMezcla.setValueAt(modelo, row, 0);
        MTMezcla.setValueAt(toHTMLMezcla(modelo,0), row, 1);
        MTMezcla.setValueAt(new JLabel(icon),row,2);
        MTMezcla.setValueAt(StringVia, row, 3);
        MTMezcla.setValueAt(Observacion, row, 4);
        jTable2.setRowHeight(row, modelo.getRowCount()*16);
        
        
//      System.out.println(jTable2.getRowHeight());//16
    }
     
     public void addMedicamento(entidades.SumSuministro pActivo,Float dosis1, String cantidad_texto,String dosis2, String via, String Observacion,Float Cantidad){
         int row = ModeloTabla.getRowCount();
         boolean exist=false;
         for(int i=0;i<ModeloTabla.getRowCount();i++){
             if(((entidades.SumSuministro)ModeloTabla.getValueAt(i, 0)).getId()==pActivo.getId()){
                 exist=true;
                 row = i;
                 break;
             }
         }
         
         ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/idea.png"));
         if(!exist){
             ModeloTabla.addRow(dato);
             ModeloTabla.setValueAt(pActivo,row,0);
             ModeloTabla.setValueAt(new JLabel(icon),row,1);
             ModeloTabla.setValueAt(dosis1,row,2);
             ModeloTabla.setValueAt(dosis2,row,3);
             ModeloTabla.setValueAt(via,row,4);
             ModeloTabla.setValueAt(Observacion,row,5);
             ModeloTabla.setValueAt(cantidad_texto,row,6);
             ModeloTabla.setValueAt(Cantidad,row,7);
         }else{
             ModeloTabla.setValueAt(dosis1,row,2);
             ModeloTabla.setValueAt(dosis2,row,3);
             ModeloTabla.setValueAt(via,row,4);
             ModeloTabla.setValueAt(Observacion,row,5);
             ModeloTabla.setValueAt(cantidad_texto,row,6);
             ModeloTabla.setValueAt(Cantidad,row,7);
         }
     }
     
     /**
      * 
      * @param modelo
      * @param tipo indica el tipo de mensaje, 0 para tabla;1 para tooltip
      * @return 
      */
     private String toHTMLMezcla(DefaultTableModel modelo, int tipo){
         String text = null;
         for(int i=0;i<modelo.getRowCount();i++){
             String var = ((SumSuministro)modelo.getValueAt(i, 0)).toString();
             if(i==0){
                 if(tipo==0){
                    text = "<html>"+var+"<br/>";
                 }else{
                     text = "<html></p>"+var+"<br/>";
                 }
             }else if(i<modelo.getRowCount()-1){
                 text = text+var+"<br/>";
             }else{
                 if(tipo==0){
                     text=text+var+"</html>";
                 }else{
                     text=text+var+"</p></html>";
                 }
             }
         }
         return text;
     }
     
      public void saveChanges(EntityManagerFactory factory, InfoHistoriac ihc){
          if(infoPosologiaHcuJPA==null){
              infoPosologiaHcuJPA=new InfoPosologiaHcuJpaController(factory);
          }
          List<InfoPosologiaHcu> listPosologia = infoPosologiaHcuJPA.ListFindInfoPosologia(ihc);
          for(int i=0;i<ModeloTabla.getRowCount();i++){
              InfoPosologiaHcu hcu=null;
              boolean exist=false;
              for(InfoPosologiaHcu iph:listPosologia){
                  if(((SumSuministro)ModeloTabla.getValueAt(i, 0)).equals(iph.getIdSuministro())){
                      exist=true;
                      hcu=iph;
                      break;
                  }
              }
              if(!exist){
                  hcu=new InfoPosologiaHcu();
                  hcu.setAdministracion((String)ModeloTabla.getValueAt(i, 5));
                  hcu.setDosisN((Float)ModeloTabla.getValueAt(i, 2));
                  hcu.setCantidadTexto((String)ModeloTabla.getValueAt(i, 6));
                  hcu.setDosisU((String)ModeloTabla.getValueAt(i, 3));
                  hcu.setIdHistoriac(ihc);
                  hcu.setIdSuministro((SumSuministro)ModeloTabla.getValueAt(i, 0));
                  hcu.setVia((String)ModeloTabla.getValueAt(i, 4));
                  hcu.setUsuario(AtencionUrgencia.idUsuario);
                  hcu.setCantidad((Float)ModeloTabla.getValueAt(i, 7));
                  infoPosologiaHcuJPA.create(hcu);
              }else{
                  if(hcu!=null){
                      try {
                          hcu.setAdministracion((String)ModeloTabla.getValueAt(i, 5));
                          hcu.setDosisN((Float)ModeloTabla.getValueAt(i, 2));
                          hcu.setCantidadTexto((String)ModeloTabla.getValueAt(i, 6));
                          hcu.setDosisU((String)ModeloTabla.getValueAt(i, 3));
                          hcu.setVia((String)ModeloTabla.getValueAt(i, 4));
                          hcu.setUsuario(AtencionUrgencia.idUsuario);
                          hcu.setCantidad((Float)ModeloTabla.getValueAt(i, 7));
                          infoPosologiaHcuJPA.edit(hcu);
                      } catch (NonexistentEntityException ex) {
                          JOptionPane.showMessageDialog(this,"saveChanges pTratMedic: "+ex.getMessage());
                      } catch (Exception ex) {
                          JOptionPane.showMessageDialog(this,"saveChanges pTratMedic: "+ex.getMessage());
                      }
                  }
              }
          }
          for(InfoPosologiaHcu iph:listPosologia){
              boolean exist = false;
              for(int i=0;i<ModeloTabla.getRowCount();i++){
                  if(((SumSuministro)ModeloTabla.getValueAt(i, 0)).equals(iph.getIdSuministro())){
                      exist=true;
                      break;
                  }
              }
              if(!exist){
                  try {
                      infoPosologiaHcuJPA.destroy(iph.getId());
                  } catch (NonexistentEntityException ex) {
                      JOptionPane.showMessageDialog(this,"saveChanges pTratMedic: "+ex.getMessage());
                  }
              }
          }          
            if(hcuMezclasMedicamentosJPA==null)
                hcuMezclasMedicamentosJPA=new HcuMezclasMedicamentosJpaController(factory);
            if(descJpaController==null)
                descJpaController=new HcuMezclasMedicamentosDescJpaController(factory);
            List<HcuMezclasMedicamentos> hcuMezclasList = hcuMezclasMedicamentosJPA.ListFindHcuMezclas(ihc);
            for(HcuMezclasMedicamentos hmm:hcuMezclasList){
                hmm.setEstado(1);//inahibilitamos la mezcla
              try {
                  hcuMezclasMedicamentosJPA.edit(hmm);
              } catch (IllegalOrphanException ex) {
                  JOptionPane.showMessageDialog(null, "10113:\n"+ex.getMessage(), pTratMedic.class.getName(), JOptionPane.INFORMATION_MESSAGE);
              } catch (NonexistentEntityException ex) {
                  JOptionPane.showMessageDialog(null, "10114:\n"+ex.getMessage(), pTratMedic.class.getName(), JOptionPane.INFORMATION_MESSAGE);
              } catch (Exception ex) {
                  JOptionPane.showMessageDialog(null, "10115:\n"+ex.getMessage(), pTratMedic.class.getName(), JOptionPane.INFORMATION_MESSAGE);
              }
            }
            for(int i=0;i<MTMezcla.getRowCount();i++){
                HcuMezclasMedicamentos hmm = new HcuMezclasMedicamentos();
                hmm.setAdministracion(MTMezcla.getValueAt(i, 4).toString());
                hmm.setIdHistoriac(ihc);
                hmm.setVia(MTMezcla.getValueAt(i, 3).toString());
                hmm.setUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                hcuMezclasMedicamentosJPA.create(hmm);//entidad mezcla creada
                DefaultTableModel model = (DefaultTableModel)MTMezcla.getValueAt(i, 0);
                for(int a=0;a<model.getRowCount();a++){
                    HcuMezclasMedicamentosDesc desc = new HcuMezclasMedicamentosDesc();
                    desc.setIdHcuMezclasMedicamentos(hmm);
                    desc.setIdSuministro((SumSuministro)model.getValueAt(a, 0));
                    desc.setDosisN((Float)model.getValueAt(a, 1));
                    desc.setDosisU(model.getValueAt(a, 2).toString());
                    desc.setSolDiluir((Boolean)model.getValueAt(a, 3));
                    desc.setUsuario(AtencionUrgencia.configdecripcionlogin.getId());
                    desc.setEstado(0);
                    descJpaController.create(desc);
                }
           
            }
      }
      
      public void showListExistentes(EntityManagerFactory factory,InfoHistoriac ihc){
          if(infoPosologiaHcuJPA==null){
              infoPosologiaHcuJPA=new InfoPosologiaHcuJpaController(factory);
          }
          List<InfoPosologiaHcu> listPosologia = infoPosologiaHcuJPA.ListFindInfoPosologia(ihc);
          ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/idea.png"));
          for(int i=0;i<listPosologia.size();i++){
              ModeloTabla.addRow(dato);
              ModeloTabla.setValueAt(listPosologia.get(i).getIdSuministro(), i, 0);
              ModeloTabla.setValueAt(new JLabel(icon),i,1);
              ModeloTabla.setValueAt(listPosologia.get(i).getDosisN(), i, 2);
              ModeloTabla.setValueAt(listPosologia.get(i).getDosisU(), i, 3);
              ModeloTabla.setValueAt(listPosologia.get(i).getVia(), i, 4);
              ModeloTabla.setValueAt(listPosologia.get(i).getAdministracion(), i, 5);
              ModeloTabla.setValueAt(listPosologia.get(i).getCantidadTexto(), i, 6);
              ModeloTabla.setValueAt(listPosologia.get(i).getCantidad(), i, 7);
          }
          if(hcuMezclasMedicamentosJPA==null)
              hcuMezclasMedicamentosJPA = new HcuMezclasMedicamentosJpaController(factory);
          List<HcuMezclasMedicamentos> hcuMezclasList = hcuMezclasMedicamentosJPA.ListFindHcuMezclas(ihc);
          for(int i=0;i<hcuMezclasList.size();i++){
              MTMezcla.addRow(dato);
              MTMezcla.setValueAt(new JLabel(icon),i,2);
              MTMezcla.setValueAt(hcuMezclasList.get(i).getVia(), i, 3);
              MTMezcla.setValueAt(hcuMezclasList.get(i).getAdministracion(), i, 4);
              medicCombinados = new addMedicCombinados(null, true);                
              DefaultTableModel model= medicCombinados.getModeloMezcla();
              medicCombinados.dispose();
              for(int a=0;a<hcuMezclasList.get(i).getHcuMezclasMedicamentosDescList().size();a++){
                  model.addRow(dato);
                  model.setValueAt(hcuMezclasList.get(i).getHcuMezclasMedicamentosDescList().get(a).getIdSuministro(), a, 0);
                  model.setValueAt(hcuMezclasList.get(i).getHcuMezclasMedicamentosDescList().get(a).getDosisN(), a, 1);
                  model.setValueAt(hcuMezclasList.get(i).getHcuMezclasMedicamentosDescList().get(a).getDosisU(), a, 2);
                  model.setValueAt(hcuMezclasList.get(i).getHcuMezclasMedicamentosDescList().get(a).getSolDiluir(), a, 3);
              }
              MTMezcla.setValueAt(model, i, 0);
              MTMezcla.setValueAt(toHTMLMezcla(model,0), i, 1);
              jTable2.setRowHeight(i, model.getRowCount()*16);
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

        jLabel57 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        buttonSeven6 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven7 = new org.edisoncor.gui.button.ButtonSeven();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        buttonSeven8 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven9 = new org.edisoncor.gui.button.ButtonSeven();

        setMaximumSize(new java.awt.Dimension(380, 420));
        setMinimumSize(new java.awt.Dimension(380, 420));
        setOpaque(false);

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Pills.png"))); // NOI18N
        jLabel57.setText("Listado de Medicamentos");
        jLabel57.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel57MouseExited(evt);
            }
        });
        jLabel57.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel57MouseMoved(evt);
            }
        });

        jPanel1.setOpaque(false);

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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setFocusable(false);
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.setOpaque(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonSeven6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                        .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSeven6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Único", jPanel1);

        jPanel2.setOpaque(false);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setFocusable(false);
        jTable2.setGridColor(new java.awt.Color(255, 255, 255));
        jTable2.setOpaque(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        buttonSeven8.setForeground(new java.awt.Color(0, 0, 255));
        buttonSeven8.setText("Agregar");
        buttonSeven8.setColorBrillo(new java.awt.Color(255, 255, 255));
        buttonSeven8.setColorDeSombra(new java.awt.Color(255, 255, 255));
        buttonSeven8.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        buttonSeven8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven8ActionPerformed(evt);
            }
        });

        buttonSeven9.setForeground(new java.awt.Color(0, 0, 255));
        buttonSeven9.setText("Borrar");
        buttonSeven9.setColorBrillo(new java.awt.Color(255, 255, 255));
        buttonSeven9.setColorDeSombra(new java.awt.Color(255, 255, 255));
        buttonSeven9.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        buttonSeven9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(buttonSeven8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                        .addComponent(buttonSeven9, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSeven8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Combinaciones", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel57MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel57MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel57MouseExited

    private void jLabel57MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel57MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel57MouseMoved

    private void buttonSeven6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven6ActionPerformed
        medicamentos = new addMedicamentos(null, true);
        medicamentos.setVisible(true);
        medicamentos.dispose();
    }//GEN-LAST:event_buttonSeven6ActionPerformed
    
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int rowIndex = jTable1.rowAtPoint(evt.getPoint()); 
        if(jTable1.columnAtPoint(evt.getPoint())==1){
            if(pop==null){
                pop = new JPopupMenu();
            }else{
                pop.removeAll();
            }
            if(p==null){
                p = new pDatosMedic(pop);
            }
            NumberFormat formatter = new DecimalFormat("#0.00");
            p.setShowDat( ((SumSuministro)jTable1.getValueAt(rowIndex, 0)).toString(),formatter.format(jTable1.getValueAt(rowIndex, 2))
                    +" - "+jTable1.getValueAt(rowIndex, 3).toString(), jTable1.getValueAt(rowIndex, 4).toString(), jTable1.getValueAt(rowIndex, 5).toString());
            pop.add(p);
            pop.setVisible(true);
            pop.setLocation(jTable1.getLocationOnScreen().x-70,evt.getLocationOnScreen().y-1);
        }else if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==2 && buttonSeven6.isVisible()){
            medicamentos = new addMedicamentos(null, true);
            medicamentos.editPosologia((SumSuministro)jTable1.getValueAt(rowIndex, 0), jTable1.getValueAt(rowIndex, 5).toString(), 
                    (Float)jTable1.getValueAt(rowIndex, 2), jTable1.getValueAt(rowIndex, 3).toString(), 
                    jTable1.getValueAt(rowIndex, 4).toString(),jTable1.getValueAt(rowIndex, 7).toString());
            medicamentos.setVisible(true);
            medicamentos.dispose();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void buttonSeven7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven7ActionPerformed
        if(ModeloTabla.getRowCount()>0 && jTable1.getSelectedRow()>-1){
            ModeloTabla.removeRow(jTable1.getSelectedRow());
        }
    }//GEN-LAST:event_buttonSeven7ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int row = ((javax.swing.JTable)evt.getSource()).getSelectedRow();
        DefaultTableModel model =(DefaultTableModel) ((DefaultTableModel) jTable2.getModel()).getValueAt(row, 0);
        //verificamos que se de click en la celda del icono
        if(jTable2.columnAtPoint(evt.getPoint())==2){
            if(pop==null){
                pop = new JPopupMenu();
            }else{
                pop.removeAll();
            }
            if(pc==null){
                pc = new pDatosMedicCombi(pop);
            }
            pc.setShowDat( model,jTable2.getValueAt(row, 3).toString(),jTable2.getValueAt(row, 4).toString());
            pop.add(pc);
            pop.setVisible(true);
            pop.setLocation(jTable2.getLocationOnScreen().x-70,evt.getLocationOnScreen().y-1);
        }else if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==2 && buttonSeven6.isVisible()){
            medicCombinados = new addMedicCombinados(null, true);
            medicCombinados.editPosologia(model, jTable2.getValueAt(row, 4).toString(), 
                     jTable2.getValueAt(row, 3).toString());
            medicCombinados.setVisible(true);
            int cerrado = medicCombinados.cerrado;
            medicCombinados.dispose();
            if(MTMezcla.getRowCount()>0 && jTable2.getSelectedRow()>-1 && cerrado==0){
                MTMezcla.removeRow(jTable2.getSelectedRow());
            }
        }        
    }//GEN-LAST:event_jTable2MouseClicked

    private void buttonSeven8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven8ActionPerformed
        medicCombinados = new addMedicCombinados(null, true);
        medicCombinados.setVisible(true);
        medicCombinados.dispose();
    }//GEN-LAST:event_buttonSeven8ActionPerformed

    private void buttonSeven9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven9ActionPerformed
        if(MTMezcla.getRowCount()>0 && jTable2.getSelectedRow()>-1){
            MTMezcla.removeRow(jTable2.getSelectedRow());
        }
    }//GEN-LAST:event_buttonSeven9ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public org.edisoncor.gui.button.ButtonSeven buttonSeven6;
    public org.edisoncor.gui.button.ButtonSeven buttonSeven7;
    public org.edisoncor.gui.button.ButtonSeven buttonSeven8;
    public org.edisoncor.gui.button.ButtonSeven buttonSeven9;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
