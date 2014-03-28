/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import atencionurgencia.AtencionUrgencia;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import entidades.InfoHistoriac;
import entidades.InfoInterconsultaHcu;
import entidades.InfoPosologiaHcu;
import entidades.InfoProcedimientoHcu;
import java.awt.Desktop;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import jpa.InfoInterconsultaHcuJpaController;
import jpa.InfoPosologiaHcuJpaController;
import jpa.InfoProcedimientoHcuJpaController;
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
public class impresionesHC extends javax.swing.JFrame {
    private InfoHistoriac idHC = null;
    private String destinoHc=null;
    private InfoPosologiaHcuJpaController infoPosologiaHcuJPA=null;
    private EntityManagerFactory factory;
    private InfoProcedimientoHcuJpaController infoProcedimientoHcuJPA=null;
    private InfoInterconsultaHcuJpaController infoInterconsultaHcuJPA=null;
    private Boolean noValido;

    /**
     * Creates new form impresionesHC
     */
    public impresionesHC() {
        initComponents();
        jLabel1.setVisible(false);
    }
    
    public void setidHC(InfoHistoriac idHC){
        this.idHC = idHC;
    }
    
    public void setdestinoHc(String destino){
        this.destinoHc=destino;
    }
    
    public void setNoValido(boolean val){
        this.noValido = val;
    }
    
    public String setValueValidoInt(boolean var){
        if(var){
            return "0";
        }else{
            return "1";
        }        
    }
    
    /**
     * 
     * Genera el reporte de receta medica.
     * El reporte utiliza un procedimiento almacenado que crea el consecutivo del mismo
     * y guarda toda la informacion de este en un registro de log
     */
    
    private class hiloReporte extends Thread{
        Frame form=null;
        
        public hiloReporte(Frame form){
            this.form =form;
        }
        
        @Override
        public void run(){
            ((impresionesHC)form).jLabel1.setVisible(true);
            ((impresionesHC)form).jButton1.setEnabled(false);
            try {
                PdfReader reader1 = null,reader2 = null,reader3 = null,reader4=null,reader5=null,reader6=null;
                File archivoTemporal = File.createTempFile("Historia_Urgencia",".pdf");
                
                
                if(jCheckBox2.isSelected()){//otros procedimientos
                    if(infoProcedimientoHcuJPA==null){
                        factory = Persistence.createEntityManagerFactory("ClipaEJBPU",AtencionUrgencia.props);
                        infoProcedimientoHcuJPA = new InfoProcedimientoHcuJpaController(factory);
                    }
                    List<InfoProcedimientoHcu> listInfoProcedimientoHcu = infoProcedimientoHcuJPA.ListFindInfoProcedimientoHcu(idHC);
                    if(listInfoProcedimientoHcu.size()>0){
                        String master = System.getProperty("user.dir")+"/reportes/solPorcedimientos.jasper";
                        if(master!=null){
                            oldConnection.Database db = new Database(AtencionUrgencia.props);
                            db.Conectar();
                            Map param = new HashMap();
                            param.put("id_hc",idHC.getId());
                            param.put("NombreReport","SOLICITUD DE PROCEDIMIENTOS");
                            param.put("version","1.0");
                            param.put("codigo","R-FA-005");
                            param.put("servicio","URGENCIAS");
                            param.put("novalido",setValueValidoInt(noValido));
                            JasperPrint informe = JasperFillManager.fillReport(master, param,db.conexion);
                            JRExporter exporter = new JRPdfExporter();
                            exporter.setParameter(JRExporterParameter.JASPER_PRINT, informe);
                            File tempFile = File.createTempFile("Solicitud_de_Procedimientos",".pdf");
                            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,tempFile);
                            exporter.exportReport();
                            reader1 = new PdfReader(tempFile.getAbsolutePath());
                            db.DesconectarBasedeDatos();
                            tempFile.deleteOnExit();
                        }
                    }
                }
                if(jCheckBox5.isSelected()){//Laboratorios
                    if(infoProcedimientoHcuJPA==null){
                        factory = Persistence.createEntityManagerFactory("ClipaEJBPU",AtencionUrgencia.props);
                        infoProcedimientoHcuJPA = new InfoProcedimientoHcuJpaController(factory);
                    }
                    List<InfoProcedimientoHcu> listInfoProcedimientoHcu = infoProcedimientoHcuJPA.ListFindInfoProcedimientoHcu(idHC,17);
                    if(listInfoProcedimientoHcu.size()>0){
                        String master = System.getProperty("user.dir")+"/reportes/solPorcedimientosLaboratorio.jasper";
                        if(master!=null){
                            oldConnection.Database db = new Database(AtencionUrgencia.props);
                            db.Conectar();
                            Map param = new HashMap();
                            param.put("id_hc",idHC.getId());
                            param.put("NombreReport","SOLICITUD DE PROCEDIMIENTOS DE LABORATORIO");
                            param.put("version","1.0");
                            param.put("codigo","R-FA-006");
                            param.put("servicio","URGENCIAS");
                            param.put("novalido",setValueValidoInt(noValido));
                            JasperPrint informe = JasperFillManager.fillReport(master, param,db.conexion);
                            JRExporter exporter = new JRPdfExporter();
                            exporter.setParameter(JRExporterParameter.JASPER_PRINT, informe);
                            File tempFile = File.createTempFile("Solicitud_de_Procedimientos",".pdf");
                            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,tempFile);
                            exporter.exportReport();
                            reader5 = new PdfReader(tempFile.getAbsolutePath());
                            db.DesconectarBasedeDatos();
                            tempFile.deleteOnExit();
                        }
                    }
                }
                if(jCheckBox6.isSelected()){//IMAGENOLOGIA
                    if(infoProcedimientoHcuJPA==null){
                        factory = Persistence.createEntityManagerFactory("ClipaEJBPU",AtencionUrgencia.props);
                        infoProcedimientoHcuJPA = new InfoProcedimientoHcuJpaController(factory);
                    }
                    List<InfoProcedimientoHcu> listInfoProcedimientoHcu = infoProcedimientoHcuJPA.ListFindInfoProcedimientoHcu(idHC,15);
                    if(listInfoProcedimientoHcu.size()>0){
                        String master = System.getProperty("user.dir")+"/reportes/solPorcedimientosImagenologia.jasper";
                        if(master!=null){
                            oldConnection.Database db = new Database(AtencionUrgencia.props);
                            db.Conectar();
                            Map param = new HashMap();
                            param.put("id_hc",idHC.getId());
                            param.put("NombreReport","SOLICITUD DE PROCEDIMIENTOS DE IMAGENOLOGIA");
                            param.put("version","1.0");
                            param.put("codigo","R-FA-007");
                            param.put("servicio","URGENCIAS");
                            param.put("novalido",setValueValidoInt(noValido));
                            JasperPrint informe = JasperFillManager.fillReport(master, param,db.conexion);
                            JRExporter exporter = new JRPdfExporter();
                            exporter.setParameter(JRExporterParameter.JASPER_PRINT, informe);
                            File tempFile = File.createTempFile("Solicitud_de_Procedimientos",".pdf");
                            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,tempFile);
                            exporter.exportReport();
                            reader6 = new PdfReader(tempFile.getAbsolutePath());
                            db.DesconectarBasedeDatos();
                            tempFile.deleteOnExit();
                        }
                    }
                }
                if(jCheckBox1.isSelected()){
                    if(infoPosologiaHcuJPA == null){
                        factory = Persistence.createEntityManagerFactory("ClipaEJBPU",AtencionUrgencia.props);
                        infoPosologiaHcuJPA = new InfoPosologiaHcuJpaController(factory);
                    }
                    List<InfoPosologiaHcu> listInfoPosologiaHcu = infoPosologiaHcuJPA.ListFindInfoPosologia(idHC);
                    if(listInfoPosologiaHcu.size()>0){  
                        String master = System.getProperty("user.dir")+"/reportes/resetaMedica.jasper";
                        if(master!=null){
                            oldConnection.Database db = new Database(AtencionUrgencia.props);
                            db.Conectar();
                            Map param = new HashMap();
                            param.put("id_hc",idHC.getId().toString());
                            param.put("NombreReport","PRESCRIPCION MEDICA");
                            param.put("version","1.0");
                            param.put("codigo","R-FA-003");
                            param.put("servicio","URGENCIAS");
                            JasperPrint informe = JasperFillManager.fillReport(master, param,db.conexion);
                            JRExporter exporter = new JRPdfExporter();
                            exporter.setParameter(JRExporterParameter.JASPER_PRINT, informe);
                            File tempFile = File.createTempFile("Prescripcion_Medica",".pdf");
                            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,tempFile);
                            exporter.exportReport();
                            reader2 = new PdfReader(tempFile.getAbsolutePath());
                            db.DesconectarBasedeDatos();
                            tempFile.deleteOnExit();
                        }                        
                    }
                }
                if(jCheckBox3.isSelected()){
                    if(infoInterconsultaHcuJPA==null){
                        factory = Persistence.createEntityManagerFactory("ClipaEJBPU",AtencionUrgencia.props);
                        infoInterconsultaHcuJPA=new InfoInterconsultaHcuJpaController(factory);
                    }
                    List<InfoInterconsultaHcu> listInfoInterconsultaHcu=infoInterconsultaHcuJPA.listInterconsultaHcu(idHC);
                    if(listInfoInterconsultaHcu.size()>0){
                        String master = System.getProperty("user.dir")+"/reportes/solValoracion.jasper";
                        if(master!=null){
                            oldConnection.Database db = new Database(AtencionUrgencia.props);
                            db.Conectar();
                            Map param = new HashMap();
                            param.put("id_hc",idHC.getId());                            
                            param.put("NombreReport","SOLICITUD DE VALORACION POR ESPECIALISTA");
                            param.put("version","1.0");
                            param.put("codigo","R-FA-004");
                            param.put("servicio","URGENCIAS");
                            JasperPrint informe = JasperFillManager.fillReport(master, param,db.conexion);
                            JRExporter exporter = new JRPdfExporter();
                            exporter.setParameter(JRExporterParameter.JASPER_PRINT, informe);
                            File tempFile = File.createTempFile("Solicitud_de_Valoracion",".pdf");
                            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,tempFile);
                            exporter.exportReport();
                            reader3 = new PdfReader(tempFile.getAbsolutePath());
                            db.DesconectarBasedeDatos();
                            tempFile.deleteOnExit();
                        }
                    }
                }            
                if(jCheckBox4.isSelected()){
                    String master = System.getProperty("user.dir")+"/reportes/HClinica.jasper";
                        if(master!=null){
                            oldConnection.Database db = new Database(AtencionUrgencia.props);
                            db.Conectar();
                            Map param = new HashMap();
                            param.put("idHC",idHC.getId());
                            param.put("NameReport","HISTORIA CLINICA DE INGRESO");
                            param.put("version","1.0");
                            param.put("codigo","R-FA-002");
                            param.put("servicio","URGENCIAS");
                            param.put("DestinoHC",destinoHc);
                            JasperPrint informe = JasperFillManager.fillReport(master, param,db.conexion);
                            JRExporter exporter = new JRPdfExporter();
                            exporter.setParameter(JRExporterParameter.JASPER_PRINT, informe);
                            File tempFile = File.createTempFile("historia_urgencia",".pdf");
                            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,tempFile);
                            exporter.exportReport();
                            reader4 = new PdfReader(tempFile.getAbsolutePath());
                            db.DesconectarBasedeDatos();
                            tempFile.deleteOnExit();
                        }
                }
                PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(archivoTemporal));
                if(jCheckBox2.isSelected()){
                    if(reader1!=null) copy.addDocument(reader1);
                }                
                if(jCheckBox1.isSelected()){
                    if(reader2!=null) copy.addDocument(reader2);
                }
                if(jCheckBox5.isSelected()){
                    if(reader5!=null) copy.addDocument(reader5);
                }
                if(jCheckBox6.isSelected()){
                    if(reader6!=null) copy.addDocument(reader6);
                }
                if(jCheckBox3.isSelected()){
                    if(reader3!=null) copy.addDocument(reader3);
                }
                if(jCheckBox4.isSelected()){
                    if(reader4!=null) copy.addDocument(reader4);
                }
                try{
                    copy.close();
                    if(noValido){
                        marcaAguaPDF(archivoTemporal);
                    }else{
                        Desktop.getDesktop().open(archivoTemporal);
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"El documento no contiene paginas", "Clipa+", JOptionPane.INFORMATION_MESSAGE);
                }
                archivoTemporal.deleteOnExit();
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "10075:\n"+ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10076:\n"+ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }
            if(!noValido){
                AtencionUrgencia.panelindex.FramEnable(true);
                AtencionUrgencia.panelindex.hc.cerrarPanel();
            }
            ((impresionesHC)form).jLabel1.setVisible(false);
            ((impresionesHC)form).jButton1.setEnabled(true);
            ((impresionesHC)form).dispose();
        }
    }
    
    private void marcaAguaPDF(File archivo_entrada){
        try {
            File archivoTemporal = File.createTempFile("Historia_Urgencia",".pdf");
            FileOutputStream fop = new FileOutputStream(archivoTemporal);
            PdfReader pdfReader = new PdfReader(archivo_entrada.getPath());
            PdfStamper pdfStamper = new PdfStamper(pdfReader, fop);
            float alto_pagina = 0;
            float ancho_pagina = 0;
            float angulo_marca_agua=0;
            for(int i=1; i<= pdfReader.getNumberOfPages(); i++){
                PdfContentByte content_fondo = pdfStamper.getUnderContent(i);
                if((pdfReader.getPageRotation(i)==0)||(pdfReader.getPageRotation(i)==180)){
                    alto_pagina = pdfReader.getPageSize(i).getHeight();
                    ancho_pagina = pdfReader.getPageSize(i).getWidth();
                    angulo_marca_agua = 60;
                }else{
                    alto_pagina = pdfReader.getPageSize(i).getWidth();
                    ancho_pagina = pdfReader.getPageSize(i).getHeight();
                    angulo_marca_agua = 30;
                }
                String marca_agua= "NO VALIDO";
                Phrase frase_marca_agua= new Phrase(marca_agua, FontFactory.getFont(BaseFont.HELVETICA, 85, Font.BOLD));
                content_fondo.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
                content_fondo.setColorStroke(new BaseColor(0xD7,0xD7,0xD7));
                content_fondo.setColorFill(BaseColor.WHITE);
                ColumnText.showTextAligned(content_fondo, Element.ALIGN_CENTER, frase_marca_agua,ancho_pagina/2 , alto_pagina/2, angulo_marca_agua);
            }
            pdfStamper.close();
            Desktop.getDesktop().open(archivoTemporal);
            archivoTemporal.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(impresionesHC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(impresionesHC.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(344, 210));
        setMinimumSize(new java.awt.Dimension(344, 210));
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

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print_32.png"))); // NOI18N
        jLabel49.setText("Impresion de Documentos");
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel49.setMaximumSize(new java.awt.Dimension(190, 32));
        jLabel49.setMinimumSize(new java.awt.Dimension(190, 32));
        jLabel49.setPreferredSize(new java.awt.Dimension(190, 32));

        jCheckBox1.setText("Receta medica");
        jCheckBox1.setFocusable(false);
        jCheckBox1.setOpaque(false);

        jCheckBox2.setText("Otros procedimientos");
        jCheckBox2.setFocusable(false);
        jCheckBox2.setOpaque(false);

        jButton1.setText("Aceptar");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBox3.setText("Valoracion por especialista");
        jCheckBox3.setFocusable(false);
        jCheckBox3.setOpaque(false);

        jCheckBox4.setSelected(true);
        jCheckBox4.setText("Historia clinica de ingreso");
        jCheckBox4.setFocusable(false);
        jCheckBox4.setOpaque(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loader.gif"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Seleccione los documentos que desea digitalizar");

        jCheckBox5.setSelected(true);
        jCheckBox5.setText("Laboratorios");
        jCheckBox5.setFocusable(false);
        jCheckBox5.setOpaque(false);

        jCheckBox6.setSelected(true);
        jCheckBox6.setText("Imagenologia");
        jCheckBox6.setFocusable(false);
        jCheckBox6.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jCheckBox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jCheckBox5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCheckBox6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(!noValido){
            atencionurgencia.AtencionUrgencia.panelindex.FramEnable(false);
        }        
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(!noValido){
            atencionurgencia.AtencionUrgencia.panelindex.FramEnable(true);
        }
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(!noValido){
            AtencionUrgencia.panelindex.FramEnable(true);
            AtencionUrgencia.panelindex.hc.cerrarPanel();
        }
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       hiloReporte ut = new hiloReporte(this);
       Thread thread = new Thread(ut);
       thread.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
