/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.ListadoPacientes;

import java.awt.event.KeyEvent;
import atencionurgencia.*;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;
import entidades.InfoHistoriac;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import oldConnection.Database;

/**
 *
 * @author Alvaro Monsalve
 */
public class Ftriaje extends javax.swing.JFrame {

    /**
     * Creates new form Ftriaje
     */
    public Ftriaje() {
        initComponents();
        this.setLocationRelativeTo(null);
        jLabel1.setVisible(false);
    }
    
        @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("images/icono.png"));
        return retValue;
    }
    
    private int getNivelTriaje(){
        if(jRadioButton1.isSelected()) return 0;
        if(jRadioButton2.isSelected()) return 1;
        if(jRadioButton3.isSelected()) return 2;
        if(jRadioButton4.isSelected()) return 3;
        return 2;
    }
    
    /**
     * 
     * @param tipo valor que designa el tipo de hc.
     * 0:nota de ingreso; 1:observacion; 2:domicilio; 3:consulta externa
     */
    private void generateHC(int tipo){
        AtencionUrgencia.panelindex.hc.infohistoriac.setMotivoConsulta(jTextArea10.getText().toUpperCase());
        AtencionUrgencia.panelindex.hc.infohistoriac.setNivelTriaje(getNivelTriaje());
        AtencionUrgencia.panelindex.hc.finalizar = tipo;
        AtencionUrgencia.panelindex.hc.CrearHistoriaC();
        AtencionUrgencia.panelindex.hc.saveHistoryClinic();
    }
    

    private class hiloReporte extends Thread{
        Frame form=null;
        InfoHistoriac idHC;
        
        public hiloReporte(Frame form, InfoHistoriac idHC){
            this.form =form;
            this.idHC=idHC;
        }
        
        @Override
        public void run(){
            ((Ftriaje)form).jLabel1.setVisible(true);
            ((Ftriaje)form).jButton1.setEnabled(false);
            try {
                PdfReader reader2=null;
                File archivoTemporal = File.createTempFile("Reporte_Triaje",".pdf");
                String master = System.getProperty("user.dir")+"/reportes/reportTriage.jasper";
                if(master!=null){
                    Database db = new Database(AtencionUrgencia.props);
                    db.Conectar();
                    Map param = new HashMap();
                    param.put("idHc",idHC.getId());
                    param.put("nameReport","NOTA DE TRIAJE");
                    param.put("version","1.0");
                    param.put("codigo","R-FA-001");
                    param.put("servicio","URGENCIAS");
                    JasperPrint informe = JasperFillManager.fillReport(master, param,db.conexion);
                    JRExporter exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, informe);
                    File tempFile = File.createTempFile("Reporte_Triaje",".pdf");
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE,tempFile);
                    exporter.exportReport();
                    reader2 = new PdfReader(tempFile.getAbsolutePath());
                    db.DesconectarBasedeDatos();
                    tempFile.deleteOnExit();
             }     
                PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(archivoTemporal));

                if(reader2!=null) copy.addDocument(reader2);

                try{
                    copy.close();
                    Desktop.getDesktop().open(archivoTemporal);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"El documento no contiene paginas", "Clipa+", JOptionPane.INFORMATION_MESSAGE);
                }
                archivoTemporal.deleteOnExit();
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "10075:\n"+ex.getMessage(), Ftriaje.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10076:\n"+ex.getMessage(), Ftriaje.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }
            AtencionUrgencia.panelindex.FramEnable(true);
            AtencionUrgencia.panelindex.hc.cerrarPanel();
            ((Ftriaje)form).jLabel1.setVisible(false);
            ((Ftriaje)form).jButton1.setEnabled(true);
            ((Ftriaje)form).dispose();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea10 = new javax.swing.JTextArea();
        jPanel26 = new javax.swing.JPanel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(436, 323));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setText("OBSERVACIONES DE VALORACION (MOTIVO DE CONSULTA)");

        jScrollPane1.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea10.setColumns(20);
        jTextArea10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea10.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea10.setLineWrap(true);
        jTextArea10.setRows(5);
        jTextArea10.setDragEnabled(true);
        jTextArea10.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea10.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea10KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea10);

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nivel de Triaje", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel26.setOpaque(false);

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton4.setText("CUATRO");
        jRadioButton4.setOpaque(false);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton3.setSelected(true);
        jRadioButton3.setText("TRES");
        jRadioButton3.setOpaque(false);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton2.setText("DOS");
        jRadioButton2.setOpaque(false);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton1.setText("UNO");
        jRadioButton1.setOpaque(false);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton3)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "OBSERVACION", "CONSULTA EXTERNA (CITA PRIORITARIA)", "DOMICILIO" }));
        jComboBox1.setFocusable(false);

        jLabel44.setText("DESTINO:");

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
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(128, 128, 128))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
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

    private void jTextArea10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea10KeyTyped
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea10KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //0 es observacion
        if(jComboBox1.getSelectedIndex()==0){
            AtencionUrgencia.panelindex.jpContainer.removeAll();
            AtencionUrgencia.panelindex.hc.setBounds(0, 0, 764, 514);
            AtencionUrgencia.panelindex.jpContainer.add(AtencionUrgencia.panelindex.hc);
            AtencionUrgencia.panelindex.hc.setVisible(true);
            AtencionUrgencia.panelindex.jpContainer.validate();
            AtencionUrgencia.panelindex.jpContainer.repaint();
            AtencionUrgencia.panelindex.hc.jTextArea10.setText(jTextArea10.getText().toUpperCase());
            AtencionUrgencia.panelindex.hc.setSelectionNivelTriage(getNivelTriaje());
            AtencionUrgencia.panelindex.FramEnable(true);
            generateHC(0);//el estado para observacion es 1 pero aun no se ha terminado la nota de ingreso
            this.setVisible(false);            
            //3 consulta externa
        }else if(jComboBox1.getSelectedIndex()==1){
            Object[] objeto ={"Si","No","Cancelar"};
            int n = JOptionPane.showOptionDialog(this, "¿Desea continuar la atención como cita prioritaria?", "Mensaje", JOptionPane.YES_NO_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[2]);
            if(n==0){
                JOptionPane.showMessageDialog(this, "El modulo de Historia Clinica para consulta externa aun se encuentra en desarrollo.\nDebe diligenciar la Historia Clínica manual.");
                
                //comienza el hilo para reporte

                /**
                 * mostrar el panel de hc consulta externa y quitar el reporte de triaje
                 */
                AtencionUrgencia.panelindex.hc.jTextArea10.setText(jTextArea10.getText().toUpperCase());
                AtencionUrgencia.panelindex.hc.setSelectionNivelTriage(getNivelTriaje());
                AtencionUrgencia.panelindex.FramEnable(true);
                generateHC(3);//el estado para consulta externa
//                hiloReporte ut = new hiloReporte(this,AtencionUrgencia.panelindex.hc.infohistoriac);
//                Thread thread = new Thread(ut);
//                thread.start();
//                this.setVisible(false);                
            }else if(n==1){
                AtencionUrgencia.panelindex.hc.jTextArea10.setText(jTextArea10.getText().toUpperCase());
                AtencionUrgencia.panelindex.hc.setSelectionNivelTriage(getNivelTriaje());
                AtencionUrgencia.panelindex.FramEnable(true);
                generateHC(3);//el estado para consulta externa es 3
//                this.setVisible(false);
                //debemos generar cita prioritaria para que sea visualizada en el formulario de consulta externa
                //para citas prioritarias y quitar el reporte de triaje asi como quitarle el destino del paciente 
                // es posible que no sea necesario generar el reporte de triaje
                hiloReporte ut = new hiloReporte(this,AtencionUrgencia.panelindex.hc.infohistoriac);
                Thread thread = new Thread(ut);
                thread.start();
            }                    
        }else if(jComboBox1.getSelectedIndex()==2){
            AtencionUrgencia.panelindex.hc.jTextArea10.setText(jTextArea10.getText().toUpperCase());
            AtencionUrgencia.panelindex.hc.setSelectionNivelTriage(getNivelTriaje());
            AtencionUrgencia.panelindex.FramEnable(true);
            generateHC(2);//el estado para domicilio y generar reporte de triage
            hiloReporte ut = new hiloReporte(this,AtencionUrgencia.panelindex.hc.infohistoriac);
            Thread thread = new Thread(ut);
            thread.start();
//            this.setVisible(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea10;
    // End of variables declaration//GEN-END:variables
}
