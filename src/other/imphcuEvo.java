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
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import entidades.HcuEvoProcedimiento;
import entidades.HcuEvolucion;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import jpa.HcuEvoProcedimientoJpaController;
import jpa.HcuEvolucionJpaController;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;
import oldConnection.Database;
import tools.Imprimirautorizacionlaboratorio;
import tools.ImprimirautorizacionlaboratorioFinal;


/**
 *
 * @author Alvaro Monsalve
 */
public class imphcuEvo extends javax.swing.JDialog {

    private HcuEvolucion hcuEvolucion = null;
    private boolean noValido;
    private HcuEvoProcedimientoJpaController procedimientoJpa = null;
    private EntityManagerFactory factory;
    private Imprimirautorizacionlaboratorio impautlab;
    private ImprimirautorizacionlaboratorioFinal implabf;
    public int stateevo1;
    private HcuEvolucionJpaController hejc=null;

    public imphcuEvo(java.awt.Frame parent, boolean modal,EntityManagerFactory factory) {
        super(parent, modal);
        initComponents();        
        this.factory = factory;
        this.enabledLink(false);
        jLabel1.setVisible(false);
    }
    
    private void enabledLink(boolean var){
        jLabel3.setEnabled(var);
        jLabel5.setEnabled(var);
        jLabel6.setEnabled(var);
        jLabel7.setEnabled(var);
        jLabel8.setEnabled(var);
        jLabel9.setEnabled(var);
        jLabel10.setEnabled(var);
    }
    
    public void activarLinks(){        
        if (procedimientoJpa == null) {
            procedimientoJpa = new HcuEvoProcedimientoJpaController(factory);
            hejc = new HcuEvolucionJpaController(factory);
        }
        List<HcuEvoProcedimiento> hcuEvoProcedimientos = procedimientoJpa.ListFindInfoProcedimientoEvo(hcuEvolucion);
        HcuEvolucion he = hejc.findHcuEvolucion(hcuEvolucion.getId());
        List<HcuEvoProcedimiento> listInfoProcedimientoHcuLabfilter = new ArrayList<HcuEvoProcedimiento>();
        List<HcuEvoProcedimiento> listInfoProcedimientoHcuRxfilter = new ArrayList<HcuEvoProcedimiento>();
        List<HcuEvoProcedimiento> listInfoProcedimientoHcuOtherfilter = new ArrayList<HcuEvoProcedimiento>();
        for(HcuEvoProcedimiento hep:hcuEvoProcedimientos){
            if(hep.getIdConfigCups().getIdEstructuraCups().getId()==17 || hep.getIdConfigCups().getIdEstructuraCups().getId()==18){
                listInfoProcedimientoHcuLabfilter.add(hep);
            }else if(hep.getIdConfigCups().getId()==15){
                listInfoProcedimientoHcuRxfilter.add(hep);
            }else{
                listInfoProcedimientoHcuOtherfilter.add(hep);
            }
        }
        if (listInfoProcedimientoHcuLabfilter != null & listInfoProcedimientoHcuLabfilter.size() > 0) {
            jLabel3.setEnabled(true);
        }
        if (listInfoProcedimientoHcuRxfilter != null & listInfoProcedimientoHcuRxfilter.size() > 0) {
            jLabel5.setEnabled(true);
        }
        if(he.getEstado()==1 || he.getEstado()==3){
            noValido=true;
        }else if(he.getEstado()!=0){
            noValido=false;
        }else{
            noValido=true;
        }
        if(he.getEstado()==2 || he.getEstado()==1){
            jLabel6.setEnabled(true);
        }else if(he.getEstado()==3 || he.getEstado()==4){
            jLabel7.setEnabled(true);
            if(noValido==false){
                jLabel8.setEnabled(true);
                if(!he.getHcuEvoPosologias().isEmpty()){
                    jLabel9.setEnabled(true);
                }
                if(he.getHcuEvoEgreso().get(0).getIncapacidad()==1){
                    jLabel10.setEnabled(true);
                }
            }
        }
    }

    public void setNoValido(boolean val) {
        this.noValido = val;
    }

    public void setEvolucion(HcuEvolucion hcuEvolucion) {
        this.hcuEvolucion = hcuEvolucion;
    }

    public String setValueValidoInt(boolean var) {
        if (var) {
            return "0";
        } else {
            return "1";
        }
    }

    
    
    public void generarConsecutivos(){
        if (procedimientoJpa == null) {
            procedimientoJpa = new HcuEvoProcedimientoJpaController(factory);
        }
        List<HcuEvoProcedimiento> hcuEvoProcedimientos = procedimientoJpa.ListFindInfoProcedimientoEvo(hcuEvolucion);
        List<HcuEvoProcedimiento> listInfoProcedimientoHcuLabfilter = new ArrayList<HcuEvoProcedimiento>();
        List<HcuEvoProcedimiento> listInfoProcedimientoHcuRxfilter = new ArrayList<HcuEvoProcedimiento>();
        for(HcuEvoProcedimiento hep:hcuEvoProcedimientos){
            if(hep.getIdConfigCups().getIdEstructuraCups().getId()==17 || hep.getIdConfigCups().getIdEstructuraCups().getId()==18){
                listInfoProcedimientoHcuLabfilter.add(hep);
            }else if(hep.getIdConfigCups().getId()==15){
                listInfoProcedimientoHcuRxfilter.add(hep);            
            }
        }
        if (listInfoProcedimientoHcuLabfilter != null & listInfoProcedimientoHcuLabfilter.size() > 0) {
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master;
                if (stateevo1 == 1 || stateevo1 == 3) {
                    master = System.getProperty("user.dir") + "/reportes/solicitudprocedimientolab.jasper";
                    param.put("novalido", setValueValidoInt(noValido));
                } else {
                    master = System.getProperty("user.dir") + "/reportes/solicitudprocedimientolabpost.jasper";
                }
                if (master != null) {
                    db.Conectar();
                    param.put("idevu", hcuEvolucion.getId().toString());
                    param.put("codigo", "R-FA-008");
                    if (noValido == true) {
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS DE LABORATORIO (NO VALIDO)");
                    } else {
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS DE LABORATORIO");
                    }
                    param.put("version", "1.0");
                    param.put("servicio", "URGENCIAS");
                }
                JasperPrint informe = JasperFillManager.fillReport(master, param, db.conexion);
//                
//                
//                
//                
//                
//                Object[] objeto = {"Visualizar", "Imprimir"};
//                int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "LABORATORIO", JOptionPane.YES_NO_CANCEL_OPTION,
//                        JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
//                
//                
//                
//                
//                hiloReporte2 ut = new hiloReporte2(this, informe, n);
//                Thread thread = new Thread(ut);
//                thread.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), imphcuEvo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
            
            
            
            
            
            
        }
        if (listInfoProcedimientoHcuRxfilter != null & listInfoProcedimientoHcuRxfilter.size() > 0) {
            jLabel5.setEnabled(true);
        }
    }


//    public void imprimir2() {
//        jLabel1.setVisible(true);
//        jButton1.setEnabled(false);
//        try {
//            PdfReader reader1 = null, reader2 = null, reader3 = null, reader4 = null, reader5 = null, reader6 = null, reader7 = null, reader8 = null, reader9 = null;
//            File archivoTemporal = File.createTempFile("Evolucion_Urgencia", ".pdf");
//            if (jCheckBox2.isSelected()) {//NOTA egreso
//                ImprimirNotaegreso ie = new ImprimirNotaegreso();
//                Database db = new Database(AtencionUrgencia.props);
//                db.Conectar();
//                ie.setCodigoReport("R-FA-012");
//                ie.setNombrereport("NOTA DE EGRESO");
//                ie.setServicioreport("URGENCIAS");
//                ie.setVersionreport("1.0");
//                ie.setIdevolucion(hcuEvolucion.getId().toString());
//                ie.setConnection(db.conexion);
//                reader2 = ie.ImprimirNotaEgreso();
//                db.DesconectarBasedeDatos();
//                ie.tempFile.deleteOnExit();
//            }
//
//            if (jCheckBox5.isSelected()) {//Laboratorios no valido
//                Database db = new Database(AtencionUrgencia.props);
//                db.Conectar();
//                implabf = new ImprimirautorizacionlaboratorioFinal();
//                implabf.setCodigo("R-FA-008");
//                implabf.setNombrereport("SOLICITUD DE PROCEDIMIENTOS DE LABORATORIO");
//                implabf.setServicio("URGENCIAS");
//                implabf.setVersion("1.0");
//                implabf.setIdevu(hcuEvolucion.getId().toString());
//                implabf.setConnection(db.conexion);
//                reader5 = implabf.ImprimirautolabFinal();
//                db.DesconectarBasedeDatos();
//                implabf.tempFile.deleteOnExit();
//            }
//            if (jCheckBox6.isSelected()) {//IMAGENOLOGIA
//                ImprimirautorizacionrxFinal impautrxf = new ImprimirautorizacionrxFinal();
//                Database db = new Database(AtencionUrgencia.props);
//                db.Conectar();
//                impautrxf.setCodigo("R-FA-009");
//                impautrxf.setNombrereport("SOLICITUD DE PROCEDIMIENTOS DE IMAGENOLOGIA");
//                impautrxf.setServicio("URGENCIAS");
//                impautrxf.setVersion("1.0");
//                impautrxf.setIdevu(hcuEvolucion.getId().toString());
//                impautrxf.setConnection(db.conexion);
//                reader6 = impautrxf.ImprimirautorxFinal();
//                db.DesconectarBasedeDatos();
//                impautrxf.tempFile.deleteOnExit();
//            }
//            if (jCheckBox4.isSelected()) {//NOTA EVO
//                ImprimirEvolucion ie = new ImprimirEvolucion();
//                Database db = new Database(AtencionUrgencia.props);
//                db.Conectar();
//                ie.setCodigoReport("R-FA-010");
//                ie.setNombrereport("NOTA DE EVOLUCION");
//                ie.setServicioreport("URGENCIAS");
//                ie.setVersionreport("1.0");
//                ie.setIdevolucion(hcuEvolucion.getId().toString());
//                ie.setConnection(db.conexion);
//                reader4 = ie.ImprimirEvolucion();
//                db.DesconectarBasedeDatos();
//                ie.tempFile.deleteOnExit();
//            }
//            if (jCheckBox8.isSelected()) {//Epicrisis   
//                ImprimirEpicrisis iep = new ImprimirEpicrisis();
//                Database db = new Database(AtencionUrgencia.props);
//                db.Conectar();
//                iep.setCodigo("R-FA-011");
//                iep.setNombrereport("EPICRISIS");
//                iep.setServicio("URGENCIAS");
//                iep.setVersion("1.0");
//                iep.setDestinohc("destino");
//                iep.setIdhc(hcuEvolucion.getIdInfoHistoriac().getId().toString());
//                iep.setConnection(db.conexion);
//                reader7 = iep.ImprimirEpicrisis();
//                db.DesconectarBasedeDatos();
//                iep.tempFile.deleteOnExit();
//            }
//            if (jCheckBox10.isSelected()) {//incapacidad
//                ImprimirIncapacidad ie = new ImprimirIncapacidad();
//                Database db = new Database(AtencionUrgencia.props);
//                db.Conectar();
//                ie.setCodigoReport("R-FA-010");
//                ie.setNombrereport("INCAPACIDAD");
//                ie.setServicioreport("URGENCIAS");
//                ie.setVersionreport("1.0");
//                ie.setIdevolucion(hcuEvolucion.getId().toString());
//                ie.setConnection(db.conexion);
//                reader1 = ie.ImprimirIncapacidad();
//                db.DesconectarBasedeDatos();
//                ie.tempFile.deleteOnExit();
//            }
//            PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(archivoTemporal));
//            if (jCheckBox2.isSelected()) {
//                if (reader2 != null) {
//                    copy.addDocument(reader2);
//                }
//            }
//            if (jCheckBox5.isSelected()) {
//                if (reader5 != null) {
//                    copy.addDocument(reader5);
//                }
//            }
//            if (jCheckBox6.isSelected()) {
//                if (reader6 != null) {
//                    copy.addDocument(reader6);
//                }
//            }
//            if (jCheckBox4.isSelected()) {
//                if (reader4 != null) {
//                    copy.addDocument(reader4);
//                }
//            }
//            if (jCheckBox8.isSelected()) {
//                if (reader7 != null) {
//                    copy.addDocument(reader7);
//                }
//            }
//            if (jCheckBox10.isSelected()) {
//                if (reader1 != null) {
//                    copy.addDocument(reader1);
//                }
//            }
//            try {
//                copy.close();
//                if (noValido) {
//                    marcaAguaPDF(archivoTemporal);
//                } else {
//                    Desktop.getDesktop().open(archivoTemporal);
//                }
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(null, ex.getMessage(), "Clipa+", JOptionPane.INFORMATION_MESSAGE);
//            }
//            archivoTemporal.deleteOnExit();
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
//        }
//    }

    private void marcaAguaPDF(File archivo_entrada) {
        try {
            File archivoTemporal = File.createTempFile("Evolucion_Urgencia", ".pdf");
            FileOutputStream fop = new FileOutputStream(archivoTemporal);
            PdfReader pdfReader = new PdfReader(archivo_entrada.getPath());
            PdfStamper pdfStamper = new PdfStamper(pdfReader, fop);
            float alto_pagina = 0;
            float ancho_pagina = 0;
            float angulo_marca_agua = 0;
            for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                PdfContentByte content_fondo = pdfStamper.getUnderContent(i);
                if ((pdfReader.getPageRotation(i) == 0) || (pdfReader.getPageRotation(i) == 180)) {
                    alto_pagina = pdfReader.getPageSize(i).getHeight();
                    ancho_pagina = pdfReader.getPageSize(i).getWidth();
                    angulo_marca_agua = 60;
                } else {
                    alto_pagina = pdfReader.getPageSize(i).getWidth();
                    ancho_pagina = pdfReader.getPageSize(i).getHeight();
                    angulo_marca_agua = 30;
                }
                String marca_agua = "NO VALIDO";
                Phrase frase_marca_agua = new Phrase(marca_agua, FontFactory.getFont(BaseFont.HELVETICA, 85, Font.BOLD));
                content_fondo.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
                content_fondo.setColorStroke(new BaseColor(0xD7, 0xD7, 0xD7));
                content_fondo.setColorFill(BaseColor.WHITE);
                ColumnText.showTextAligned(content_fondo, Element.ALIGN_CENTER, frase_marca_agua, ancho_pagina / 2, alto_pagina / 2, angulo_marca_agua);
            }
            pdfStamper.close();
            Desktop.getDesktop().open(archivoTemporal);
            archivoTemporal.deleteOnExit();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private class hiloReporte2 extends Thread{
        JDialog form=null;
        JasperPrint informe;
        int n;
        
        public hiloReporte2(JDialog form,JasperPrint informe, int n){
            this.form =form;
            this.informe=informe;
            this.n=n;
        }
        
        @Override
        public void run(){
            ((imphcuEvo)form).jLabel1.setVisible(true);
            if (n == 0) {
                JasperViewer.viewReport(informe, false);
            } else if(n == 1) {
                try {                    
                    JasperPrintManager.printReport(informe, true);
                } catch (JRException ex) {
                    JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), imphcuEvo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                }
            }
            ((imphcuEvo)form).jLabel1.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(230, 208));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print_32.png"))); // NOI18N
        jLabel49.setText("Impresion de Documentos");
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel49.setMaximumSize(new java.awt.Dimension(190, 32));
        jLabel49.setMinimumSize(new java.awt.Dimension(190, 32));
        jLabel49.setPreferredSize(new java.awt.Dimension(190, 32));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loader.gif"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Seleccione los documentos");

        jLabel3.setText("LABORATORIOS");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel3MouseMoved(evt);
            }
        });
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel3MouseReleased(evt);
            }
        });

        jLabel5.setText("IMAGENOLOGIA");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel5MouseMoved(evt);
            }
        });
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel5MouseReleased(evt);
            }
        });

        jLabel6.setText("NOTA EVOLUCION");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        jLabel7.setText("NOTA EGRESO");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel7MouseMoved(evt);
            }
        });
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel7MouseReleased(evt);
            }
        });

        jLabel8.setText("EPICRISIS");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel8MouseMoved(evt);
            }
        });
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel8MouseReleased(evt);
            }
        });

        jLabel9.setText("RECETA MEDICA");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel9MouseMoved(evt);
            }
        });
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel9MouseReleased(evt);
            }
        });

        jLabel10.setText("INCAPACIDAD");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel10MouseMoved(evt);
            }
        });
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel10MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseMoved
        if(jLabel3.isEnabled()==true){
            jLabel3.setText("<html><a href=''>LABORATORIOS</a></html>");
        }
    }//GEN-LAST:event_jLabel3MouseMoved

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        jLabel3.setText("LABORATORIOS");
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseReleased
        if (jLabel3.isEnabled() == true) {
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master;
                if (stateevo1 == 1 || stateevo1 == 3) {
                    master = System.getProperty("user.dir") + "/reportes/solicitudprocedimientolab.jasper";
                    param.put("novalido", setValueValidoInt(noValido));
                } else {
                    master = System.getProperty("user.dir") + "/reportes/solicitudprocedimientolabpost.jasper";
                }
                if (master != null) {
                    db.Conectar();
                    param.put("idevu", hcuEvolucion.getId().toString());
                    param.put("codigo", "R-FA-008");
                    if (noValido == true) {
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS DE LABORATORIO (NO VALIDO)");
                    } else {
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS DE LABORATORIO");
                    }
                    param.put("version", "1.0");
                    param.put("servicio", "URGENCIAS");
                }
                JasperPrint informe = JasperFillManager.fillReport(master, param, db.conexion);
                Object[] objeto = {"Visualizar", "Imprimir"};
                int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "LABORATORIO", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                hiloReporte2 ut = new hiloReporte2(this, informe, n);
                Thread thread = new Thread(ut);
                thread.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), imphcuEvo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel3MouseReleased

    private void jLabel5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseMoved
        if(jLabel5.isEnabled()==true){
            jLabel5.setText("<html><a href=''>IMAGENOLOGIA</a></html>");
        }
    }//GEN-LAST:event_jLabel5MouseMoved

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        jLabel5.setText("IMAGENOLOGIA");
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseReleased
        if(jLabel5.isEnabled()==true){
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master;
                if (stateevo1 == 1 || stateevo1 == 3) {
                    master = System.getProperty("user.dir") + "/Reportes/solicitudprocedimientoimage.jasper";
                    param.put("novalido", setValueValidoInt(noValido));
                } else {
                    master = System.getProperty("user.dir") + "/Reportes/solicitudprocedimientorxpost.jasper";
                }
                if (master != null) {
                    db.Conectar();
                    param.put("idevu", hcuEvolucion.getId().toString());
                    param.put("codigo", "R-FA-009");
                    if (noValido == true) {
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS DE IMAGENOLOGIA (NO VALIDO)");
                    } else {
                        param.put("NombreReport", "SOLICITUD DE PROCEDIMIENTOS DE IMAGENOLOGIA");
                    }
                    param.put("version", "1.0");
                    param.put("servicio", "URGENCIAS");
                }
                JasperPrint informe = JasperFillManager.fillReport(master, param, db.conexion);
                Object[] objeto = {"Visualizar", "Imprimir"};
                int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "IMAGENOLOGIA", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                hiloReporte2 ut = new hiloReporte2(this, informe, n);
                Thread thread = new Thread(ut);
                thread.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), imphcuEvo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel5MouseReleased

    private void jLabel6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseMoved
        if(jLabel6.isEnabled()==true){
            jLabel6.setText("<html><a href=''>NOTA EVOLUCION</a></html>");
        }
    }//GEN-LAST:event_jLabel6MouseMoved

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        jLabel6.setText("NOTA EVOLUCION");
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseReleased
        if(jLabel6.isEnabled()==true){
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master= System.getProperty("user.dir") + "/Reportes/Evolucion.jasper";                
                if (master != null) {
                    db.Conectar();
                    param.put("IDEVOLUCION", hcuEvolucion.getId().toString());
                    param.put("codigo", "R-FA-009");
                    if (noValido == true) {
                        param.put("NameReport", "NOTA DE EVOLUCION (NO VALIDO) "+tools.MyDate.yyyyMMddHHmm2.format(hcuEvolucion.getFechaEvo()));
                    } else {
                        param.put("NameReport", "NOTA DE EVOLUCION "+tools.MyDate.yyyyMMddHHmm2.format(hcuEvolucion.getFechaEvo()));
                    }
                    param.put("version", "1.0");
                    param.put("servicio", "URGENCIAS");
                }
                JasperPrint informe = JasperFillManager.fillReport(master, param, db.conexion);
                Object[] objeto = {"Visualizar", "Imprimir"};
                int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "NOTA DE EVOLUCION", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                hiloReporte2 ut = new hiloReporte2(this, informe, n);
                Thread thread = new Thread(ut);
                thread.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), imphcuEvo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel6MouseReleased

    private void jLabel7MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseMoved
        if(jLabel7.isEnabled()==true){
            jLabel7.setText("<html><a href=''>NOTA EGRESO</a></html>");
        }
    }//GEN-LAST:event_jLabel7MouseMoved

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        jLabel7.setText("NOTA EGRESO");
    }//GEN-LAST:event_jLabel7MouseExited

    private void jLabel7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseReleased
        if(jLabel7.isEnabled()==true){
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master= System.getProperty("user.dir") + "/Reportes/NotaEgreso.jasper";                
                if (master != null) {
                    db.Conectar();
                    param.put("IDEVOLUCION", hcuEvolucion.getId().toString());
                    param.put("codigo", "R-FA-012");
                    if (noValido == true) {
                        param.put("NameReport", "NOTA DE EGRESO (NO VALIDO) "+tools.MyDate.yyyyMMddHHmm2.format(hcuEvolucion.getFechaEvo()));
                    } else {
                        param.put("NameReport", "NOTA DE EGRESO "+tools.MyDate.yyyyMMddHHmm2.format(hcuEvolucion.getFechaEvo()));
                    }
                    param.put("version", "1.0");
                    param.put("servicio", "URGENCIAS");
                }
                JasperPrint informe = JasperFillManager.fillReport(master, param, db.conexion);
                Object[] objeto = {"Visualizar", "Imprimir"};
                int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "NOTA EGRESO", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                hiloReporte2 ut = new hiloReporte2(this, informe, n);
                Thread thread = new Thread(ut);
                thread.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), imphcuEvo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel7MouseReleased

    private void jLabel8MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseMoved
        if(jLabel8.isEnabled()==true){
            jLabel8.setText("<html><a href=''>EPICRISIS</a></html>");
        }
    }//GEN-LAST:event_jLabel8MouseMoved

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        jLabel8.setText("EPICRISIS");
    }//GEN-LAST:event_jLabel8MouseExited

    private void jLabel8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseReleased
        if(jLabel8.isEnabled()==true){
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master= System.getProperty("user.dir") + "/Reportes/Epicrisis.jasper";                
                if (master != null) {
                    db.Conectar();
                    param.put("idHC", hcuEvolucion.getIdInfoHistoriac().getId().toString());
                    param.put("codigo", "R-FA-011");
                    param.put("NameReport", "EPICRISIS");                    
                    param.put("version", "1.0");
                    param.put("servicio", "URGENCIAS");
                    param.put("DestinoHC",hcuEvolucion.getIdInfoHistoriac().getDestino());
                    if(hcuEvolucion.getIdInfoHistoriac().getCausaExterna().equals("ACCIDENTE DE TRANSITO")){
                        param.put("soat","1");
                    }else{
                        param.put("soat","0");
                    }                    
                }
                JasperPrint informe = JasperFillManager.fillReport(master, param, db.conexion);
                Object[] objeto = {"Visualizar", "Imprimir"};
                int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "EPICRISIS", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                hiloReporte2 ut = new hiloReporte2(this, informe, n);
                Thread thread = new Thread(ut);
                thread.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), imphcuEvo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel8MouseReleased

    private void jLabel9MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseMoved
        if(jLabel9.isEnabled()==true){
            jLabel9.setText("<html><a href=''>RECETA MEDICA</a></html>");
        }
    }//GEN-LAST:event_jLabel9MouseMoved

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        jLabel9.setText("RECETA MEDICA");
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseReleased
        if(jLabel9.isEnabled()==true){
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master= System.getProperty("user.dir") + "/Reportes/Receta.jasper";                
                if (master != null) {
                    db.Conectar();
                    param.put("Idevolucion", hcuEvolucion.getId().toString());
                    param.put("Codigo", "R-FA-013");
                    param.put("NameReport", "PRESCRIPCION MEDICA");                    
                    param.put("Version", "1.0");
                    param.put("Servicio", "URGENCIAS");                  
                }
                JasperPrint informe = JasperFillManager.fillReport(master, param, db.conexion);
                Object[] objeto = {"Visualizar", "Imprimir"};
                int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "PRESCRIPCION MEDICA", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                hiloReporte2 ut = new hiloReporte2(this, informe, n);
                Thread thread = new Thread(ut);
                thread.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), imphcuEvo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel9MouseReleased

    private void jLabel10MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseMoved
        if(jLabel10.isEnabled()==true){
            jLabel10.setText("<html><a href=''>INCAPACIDAD</a></html>");
        }
    }//GEN-LAST:event_jLabel10MouseMoved

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
        jLabel10.setText("INCAPACIDAD");
    }//GEN-LAST:event_jLabel10MouseExited

    private void jLabel10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseReleased
        if(jLabel10.isEnabled()==true){
            Database db = new Database(AtencionUrgencia.props);
            try {
                Map param = new HashMap();
                String master= System.getProperty("user.dir") + "/Reportes/incapacidad.jasper";                
                if (master != null) {
                    db.Conectar();
                    param.put("Idevolucion", hcuEvolucion.getId().toString());
                    param.put("Codigo", "R-FA-012");
                    param.put("NameReport", "INCAPACIDAD");                    
                    param.put("Version", "1.0");
                    param.put("Servicio", "URGENCIAS");                  
                }
                JasperPrint informe = JasperFillManager.fillReport(master, param, db.conexion);
                Object[] objeto = {"Visualizar", "Imprimir"};
                int n = JOptionPane.showOptionDialog(this, "Escoja la opción deseada", "INCAPACIDAD", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
                hiloReporte2 ut = new hiloReporte2(this, informe, n);
                Thread thread = new Thread(ut);
                thread.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "100??:\n" + ex.getMessage(), imphcuEvo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }finally{
                db.DesconectarBasedeDatos();
            }
        }
    }//GEN-LAST:event_jLabel10MouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
