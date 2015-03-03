/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.ingreso;


import atencionurgencia.AtencionUrgencia;
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
import entidades.HcuEvoPosologia;
import entidades.HcuEvolucion;
import jpa.HcuEvoPosologiaJpaController;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class pTratMedic extends javax.swing.JPanel {
    private DefaultTableModel ModeloTabla;
    private final Object dato[] = null;
    private addMedicamentos medicamentos=null;
    private InfoPosologiaHcuJpaController infoPosologiaHcuJPA=null;
    private JPopupMenu pop=null;
    private pDatosMedic p=null;
    private pDatosMedicCombi pc=null;
    private HcuEvoPosologiaJpaController hcuEvoPosologiaJpaController=null;
    public boolean onEvolucion;

    /**
     * Creates new form pTratMedic
     */
    public pTratMedic() {
        initComponents();
        onEvolucion=false;
        setCargaTabla();
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
    
    private void setCargaTabla(){
        ModeloTabla = getModelo();
        jTable1.setModel(ModeloTabla);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Funciones.setSizeColumnas(jTable1,new int[]{1},new int[]{20});
        Funciones.setOcultarColumnas(jTable1,new int[]{1,2,3,4,5,6,7});
        jTable1.setDefaultRenderer(Object.class, new tools.IconCellRenderer());
        jTable1.setTableHeader(null);
    }
     
     public void addMedicamento(entidades.SumSuministro pActivo,Float dosis1, String cantidad_texto,String dosis2, String via, String Observacion,Float Cantidad){
         int row = ModeloTabla.getRowCount();
         boolean exist=false;

         for(int i=0;i<ModeloTabla.getRowCount();i++){
             if(((SumSuministro)ModeloTabla.getValueAt(i, 0)).getId().equals(pActivo.getId())){
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
                          if(AtencionUrgencia.panelindex.hc.auditoria==false){
                              hcu.setUsuario(AtencionUrgencia.idUsuario);
                          }                          
                          hcu.setCantidad((Float)ModeloTabla.getValueAt(i, 7));
                          infoPosologiaHcuJPA.edit(hcu);
                      } catch (Exception ex) {
                          JOptionPane.showMessageDialog(null, "10120:\n"+ex.getMessage(), pTratMedic.class.getName(), JOptionPane.INFORMATION_MESSAGE);
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
      }
      
      public void saveChanges(EntityManagerFactory factory, HcuEvolucion evol){
          if(hcuEvoPosologiaJpaController==null)
              hcuEvoPosologiaJpaController=new HcuEvoPosologiaJpaController(factory);          
          List<HcuEvoPosologia> evoPosologias = hcuEvoPosologiaJpaController.ListFindInfoPosologia(evol);
          for(int i=0;i<ModeloTabla.getRowCount();i++){
              HcuEvoPosologia hep = null;
              boolean exist=false;
              for(HcuEvoPosologia hep1:evoPosologias){
                  if(((SumSuministro)ModeloTabla.getValueAt(i, 0)).equals(hep1.getIdSuministro())){
                      exist=true;
                      hep=hep1;
                      break;
                  }
              }
              if(!exist){
                  hep=new HcuEvoPosologia();
                  hep.setAdministracion((String)ModeloTabla.getValueAt(i, 5));
                  hep.setDosisN((Float)ModeloTabla.getValueAt(i, 2));
                  hep.setCantidadTexto((String)ModeloTabla.getValueAt(i, 6));
                  hep.setDosisU((String)ModeloTabla.getValueAt(i, 3));
                  hep.setIdHcuEvolucion(evol);
                  hep.setIdSuministro((SumSuministro)ModeloTabla.getValueAt(i, 0));
                  hep.setVia((String)ModeloTabla.getValueAt(i, 4));
                      hep.setUsuario(AtencionUrgencia.idUsuario);
                                
                  hep.setCantidad((Float)ModeloTabla.getValueAt(i, 7));
                  hep.setEstado(1);//1 es activo
                  hcuEvoPosologiaJpaController.create(hep);                  
              }else{
                  if(hep!=null){
                      try {
                          hep.setAdministracion((String)ModeloTabla.getValueAt(i, 5));
                          hep.setDosisN((Float)ModeloTabla.getValueAt(i, 2));
                          hep.setCantidadTexto((String)ModeloTabla.getValueAt(i, 6));
                          hep.setDosisU((String)ModeloTabla.getValueAt(i, 3));
                          hep.setVia((String)ModeloTabla.getValueAt(i, 4));
                          if(AtencionUrgencia.panelindex.evo.auditoria==false){
                              hep.setUsuario(AtencionUrgencia.idUsuario);
                          }                          
                          hep.setCantidad((Float)ModeloTabla.getValueAt(i, 7));
                          hcuEvoPosologiaJpaController.edit(hep);
                      } catch (Exception ex) {
                          JOptionPane.showMessageDialog(null, "10119:\n"+ex.getMessage(), pTratMedic.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                      }
                  }
              }
          }
          for(HcuEvoPosologia hep:evoPosologias){
              boolean exist = false;
              for(int i=0;i<ModeloTabla.getRowCount();i++){
                  if(((SumSuministro)ModeloTabla.getValueAt(i, 0)).equals(hep.getIdSuministro())){
                      exist=true;
                      break;
                  }
              }
              if(!exist){
                  try {
                      hep.setEstado(0);//inactivo
                      hcuEvoPosologiaJpaController.edit(hep);
                  } catch (Exception ex) {
                      JOptionPane.showMessageDialog(null, "10121:\n"+ex.getMessage(), pTratMedic.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                  }
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
      }
      
      public void showListExistentes(EntityManagerFactory factory,HcuEvolucion evol){
          if(hcuEvoPosologiaJpaController==null){
              hcuEvoPosologiaJpaController=new HcuEvoPosologiaJpaController(factory);
          }
          List<HcuEvoPosologia> evoPosologias = hcuEvoPosologiaJpaController.ListFindInfoPosologia(evol);
          ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/idea.png"));
          for(int i=0;i<evoPosologias.size();i++){
              ModeloTabla.addRow(dato);
              ModeloTabla.setValueAt(evoPosologias.get(i).getIdSuministro(), i, 0);
              ModeloTabla.setValueAt(new JLabel(icon),i,1);
              ModeloTabla.setValueAt(evoPosologias.get(i).getDosisN(), i, 2);
              ModeloTabla.setValueAt(evoPosologias.get(i).getDosisU(), i, 3);
              ModeloTabla.setValueAt(evoPosologias.get(i).getVia(), i, 4);
              ModeloTabla.setValueAt(evoPosologias.get(i).getAdministracion(), i, 5);
              ModeloTabla.setValueAt(evoPosologias.get(i).getCantidadTexto(), i, 6);
              ModeloTabla.setValueAt(evoPosologias.get(i).getCantidad(), i, 7);
          }
      }
      
      /**
       * 
       * @param posologias list de medicamentos 
       * @param evo true si viene de evolucion; false si viene de hcu
       */
      private void migrarPosologiaToEvo(Object posologias, boolean evo){//Se quito la funcionalidad de migrar de otra evolucion CAUCASIA 11/02/2014 A LAS 7:53 AM
//          String mensaje = "¿Quiere continuar con los medicamentos de la Nota de Ingreso? ";
            String mensaje = "Agregue medicamentos";
        if(evo){
//            mensaje = "¿Quiere continuar con los medicamentos de la Evolución anterior? ";
              mensaje = "Agregue medicamentos";
        }
//        int entrada = JOptionPane.showConfirmDialog(null, mensaje,"Migración de medicamentos",JOptionPane.YES_NO_OPTION);
        int entrada = JOptionPane.showConfirmDialog(null, mensaje,"Seleccion procedimiento",JOptionPane.DEFAULT_OPTION);
        entrada = 1;
        if(entrada==0){
            if(evo){
                List<HcuEvoPosologia> evoPosologias = (List<HcuEvoPosologia>) posologias;
                ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/idea.png"));
                for(int i=0;i<evoPosologias.size();i++){
                    ModeloTabla.addRow(dato);
                    ModeloTabla.setValueAt(evoPosologias.get(i).getIdSuministro(), i, 0);
                    ModeloTabla.setValueAt(new JLabel(icon),i,1);
                    ModeloTabla.setValueAt(evoPosologias.get(i).getDosisN(), i, 2);
                    ModeloTabla.setValueAt(evoPosologias.get(i).getDosisU(), i, 3);
                    ModeloTabla.setValueAt(evoPosologias.get(i).getVia(), i, 4);
                    ModeloTabla.setValueAt(evoPosologias.get(i).getAdministracion(), i, 5);
                    ModeloTabla.setValueAt(evoPosologias.get(i).getCantidadTexto(), i, 6);
                    ModeloTabla.setValueAt(evoPosologias.get(i).getCantidad(), i, 7);
                }
            }else{
                List<InfoPosologiaHcu> listPosologia = (List<InfoPosologiaHcu>) posologias;
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
            }
        }
      }
      
    
     
    /**
     * Retorna el estado sobre los registros de las tablas de medicamentos y mezclas
     * @return true si alguna de las tablas tienen registros y false si las dos tablas estan vacias
     */  
    public boolean estadoTablas(){
        return ModeloTabla.getRowCount()>0;   
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel57 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(380, 420));
        setMinimumSize(new java.awt.Dimension(380, 420));
        setOpaque(false);

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Pills.png"))); // NOI18N
        jLabel57.setText("LISTADO DE MEDICAMENTOS");

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

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add20x20.png"))); // NOI18N
        jLabel2.setFocusable(false);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel2MouseReleased(evt);
            }
        });
        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel2MouseMoved(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cerrar_20x20.png"))); // NOI18N
        jLabel3.setFocusable(false);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel3MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int rowIndex = jTable1.rowAtPoint(evt.getPoint()); 
        if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount()==2){
            medicamentos = new addMedicamentos(null, true);
            medicamentos.editPosologia((SumSuministro)jTable1.getValueAt(rowIndex, 0), jTable1.getValueAt(rowIndex, 5).toString(), 
                    (Float)jTable1.getValueAt(rowIndex, 2), jTable1.getValueAt(rowIndex, 3).toString(), 
                    jTable1.getValueAt(rowIndex, 4).toString(),jTable1.getValueAt(rowIndex, 7).toString());
            medicamentos.onEvolucion=onEvolucion;
            medicamentos.setVisible(true);
            medicamentos.dispose();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        jLabel2.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseReleased
        if (jLabel2.isEnabled()==true){
            medicamentos = new addMedicamentos(null, true);
            medicamentos.onEvolucion=onEvolucion;
            medicamentos.setVisible(true);
            medicamentos.dispose();
        }
    }//GEN-LAST:event_jLabel2MouseReleased

    private void jLabel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseMoved
        jLabel2.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel2MouseMoved

    private void jLabel3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseReleased
        if(ModeloTabla.getRowCount()>0 && jTable1.getSelectedRow()>-1){
            ModeloTabla.removeRow(jTable1.getSelectedRow());
        }
    }//GEN-LAST:event_jLabel3MouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
