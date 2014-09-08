package other;

import tools.ImprimirEpicrisis;
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
import entidades.HcuEvoProcedimiento;
import entidades.HcuEvolucion;
import java.awt.Desktop;
import java.awt.Dialog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import jpa.HcuEvoProcedimientoJpaController;
import oldConnection.Database;
import tools.ImprimirEvolucion;
import tools.ImprimirNotaegreso;
import tools.ImprimirIncapacidad;
import tools.Imprimirautorizacionlaboratorio;
import tools.ImprimirautorizacionlaboratorioFinal;
import tools.Imprimirautorizacionrx;
import tools.ImprimirautorizacionrxFinal;

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

    public imphcuEvo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jLabel1.setVisible(false);
    }

    public void activeChec() {
        jCheckBox9.setEnabled(false);
        if (procedimientoJpa == null) {
            factory = Persistence.createEntityManagerFactory("ClipaEJBPU", AtencionUrgencia.props);
            procedimientoJpa = new HcuEvoProcedimientoJpaController(factory);
        }
        //LAB
        List<HcuEvoProcedimiento> hcuEvoProcedimientos = procedimientoJpa.ListFindInfoProcedimientoEvo(hcuEvolucion, 17);
        if (hcuEvoProcedimientos.isEmpty()) {
            jCheckBox5.setEnabled(false);
        } else {
            jCheckBox5.setEnabled(true);
        }
        //IMAGENOLOGIA
        hcuEvoProcedimientos = procedimientoJpa.ListFindInfoProcedimientoEvo(hcuEvolucion, 15);
        if (hcuEvoProcedimientos.isEmpty()) {
            jCheckBox6.setEnabled(false);
        } else {
            jCheckBox6.setEnabled(true);
        }
        //Nota EGRESO
        if (hcuEvolucion.getEstado() != 4) {
            jCheckBox2.setEnabled(false);
            jCheckBox10.setEnabled(false);
            jCheckBox8.setEnabled(false);
        } else {
            if (hcuEvolucion.getEstado() == 4) {
                jCheckBox2.setEnabled(true);
                jCheckBox4.setEnabled(false);
                jCheckBox8.setEnabled(true);
                if(hcuEvolucion.getHcuEvoEgreso().isEmpty() == false & 
                        hcuEvolucion.getHcuEvoEgreso().get(hcuEvolucion.getHcuEvoEgreso().size()-1).getIncapacidad()==1 ){
                    jCheckBox10.setEnabled(true);
                }
            }
        }
        if (hcuEvolucion.getEstado() == 3) {
            jCheckBox2.setEnabled(true);
            jCheckBox4.setEnabled(false);
        }
        //nota de evolucion
        if (hcuEvolucion.getEstado() == 2) {//evo finalizada
            jCheckBox2.setEnabled(false);
        } else {
            jCheckBox2.setEnabled(true);
        }
        if (hcuEvolucion.getEstado() == 1) {
            jCheckBox2.setEnabled(false);
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

    private class hiloReporte extends Thread {
        Dialog form = null;
        
        public hiloReporte(Dialog form) {
            this.form = form;
        }

        @Override
        public void run() {
            ((imphcuEvo) form).jLabel1.setVisible(true);
            ((imphcuEvo) form).jButton1.setEnabled(false);
            try {
                PdfReader reader1 = null, reader2 = null, reader3 = null, reader4 = null, reader5 = null, reader6 = null, reader7 = null, reader8 = null, reader9 = null;
                File archivoTemporal = File.createTempFile("Evolucion_Urgencia", ".pdf");
                if (jCheckBox2.isSelected()) {//NOTA egreso
                    ImprimirNotaegreso ie = new ImprimirNotaegreso();
                    Database db = new Database(AtencionUrgencia.props);
                    db.Conectar();
                    ie.setCodigoReport("R-FA-012");
                    ie.setNombrereport("NOTA DE EGRESO");
                    ie.setServicioreport("URGENCIAS");
                    ie.setVersionreport("1.0");
                    ie.setIdevolucion(hcuEvolucion.getId().toString());
                    ie.setConnection(db.conexion);
                    reader2 = ie.ImprimirNotaEgreso();
                    db.DesconectarBasedeDatos();
                    ie.tempFile.deleteOnExit();
                }
                if (jCheckBox5.isSelected()) {                    
                    Database db = new Database(AtencionUrgencia.props);
                    db.Conectar();
                    if (hcuEvolucion.getEstado() == 1 || hcuEvolucion.getEstado() == 3) {
                        impautlab = new Imprimirautorizacionlaboratorio();
                        impautlab.setCodigo("R-FA-008");
                        impautlab.setNombrereport("SOLICITUD DE PROCEDIMIENTOS DE LABORATORIO");
                        impautlab.setNovalido(setValueValidoInt(noValido));
                        impautlab.setServicio("URGENCIAS");
                        impautlab.setVersion("1.0");
                        impautlab.setIdevu(hcuEvolucion.getId().toString());
                        impautlab.setConnection(db.conexion);
                        reader5 = impautlab.Imprimirautolab();
                        impautlab.tempFile.deleteOnExit();
                    }else{
                        implabf = new ImprimirautorizacionlaboratorioFinal();
                        implabf.setCodigo("R-FA-008");
                        implabf.setNombrereport("SOLICITUD DE PROCEDIMIENTOS DE LABORATORIO");
                        implabf.setServicio("URGENCIAS");
                        implabf.setVersion("1.0");
                        implabf.setIdevu(hcuEvolucion.getId().toString());
                        implabf.setConnection(db.conexion);
                        reader5 = implabf.ImprimirautolabFinal();                        
                        implabf.tempFile.deleteOnExit();
                    }   
                    db.DesconectarBasedeDatos();
                }
                if (jCheckBox6.isSelected()) {//IMAGENOLOGIA
                    Database db = new Database(AtencionUrgencia.props);
                    db.Conectar();
                    if (hcuEvolucion.getEstado() == 1 || hcuEvolucion.getEstado() == 3) {
                        Imprimirautorizacionrx impautrx = new Imprimirautorizacionrx();
                        impautrx.setCodigo("R-FA-009");
                        impautrx.setNombrereport("SOLICITUD DE PROCEDIMIENTOS DE IMAGENOLOGIA");
                        impautrx.setNovalido(setValueValidoInt(noValido));
                        impautrx.setServicio("URGENCIAS");
                        impautrx.setVersion("1.0");
                        impautrx.setIdevu(hcuEvolucion.getId().toString());
                        impautrx.setConnection(db.conexion);
                        reader6 = impautrx.Imprimirautorx();                        
                        impautrx.tempFile.deleteOnExit();
                    }else{
                        ImprimirautorizacionrxFinal impautrxf = new ImprimirautorizacionrxFinal();
                        impautrxf.setCodigo("R-FA-009");
                        impautrxf.setNombrereport("SOLICITUD DE PROCEDIMIENTOS DE IMAGENOLOGIA");
                        impautrxf.setServicio("URGENCIAS");
                        impautrxf.setVersion("1.0");
                        impautrxf.setIdevu(hcuEvolucion.getId().toString());
                        impautrxf.setConnection(db.conexion);
                        reader6 = impautrxf.ImprimirautorxFinal();
                        impautrxf.tempFile.deleteOnExit();
                    }
                    db.DesconectarBasedeDatos();
                }
                if (jCheckBox4.isSelected()) {//NOTA EVO
                    ImprimirEvolucion ie = new ImprimirEvolucion();
                    Database db = new Database(AtencionUrgencia.props);
                    db.Conectar();
                    ie.setCodigoReport("R-FA-010");
                    ie.setNombrereport("NOTA DE EVOLUCION");
                    ie.setServicioreport("URGENCIAS");
                    ie.setVersionreport("1.0");
                    ie.setIdevolucion(hcuEvolucion.getId().toString());
                    ie.setConnection(db.conexion);
                    reader4 = ie.ImprimirEvolucion();
                    db.DesconectarBasedeDatos();
                    ie.tempFile.deleteOnExit();
                }
                if (jCheckBox8.isSelected()) {//Epicrisis   
                    ImprimirEpicrisis iep = new ImprimirEpicrisis();
                    Database db = new Database(AtencionUrgencia.props);
                    db.Conectar();
                    iep.setCodigo("R-FA-011");
                    iep.setNombrereport("EPICRISIS");
                    iep.setServicio("URGENCIAS");
                    iep.setVersion("1.0");
                    iep.setDestinohc("destino");
                    iep.setIdhc(hcuEvolucion.getIdInfoHistoriac().getId().toString());
                    iep.setConnection(db.conexion);
                    reader7 = iep.ImprimirEpicrisis();
                    db.DesconectarBasedeDatos();
                    iep.tempFile.deleteOnExit();
                }
                if (jCheckBox10.isSelected()) {//incapacidad
                    ImprimirIncapacidad iep = new ImprimirIncapacidad();
                    Database db = new Database(AtencionUrgencia.props);
                    db.Conectar();
                    iep.setCodigoReport("R-FA-012");
                    iep.setNombrereport("INCAPACIDAD");
                    iep.setServicioreport("URGENCIAS");
                    iep.setVersionreport("1.0");
                    iep.setIdevolucion(hcuEvolucion.getId().toString());
                    iep.setConnection(db.conexion);
                    reader1 = iep.ImprimirIncapacidad();
                    db.DesconectarBasedeDatos();
                    iep.tempFile.deleteOnExit();
                }
                
                PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(archivoTemporal));
                if (jCheckBox2.isSelected()) {
                    if (reader2 != null) {
                        copy.addDocument(reader2);
                    }
                }
                if (jCheckBox5.isSelected()) {
                    if (reader5 != null) {
                        copy.addDocument(reader5);
                    }
                }
                if (jCheckBox6.isSelected()) {
                    if (reader6 != null) {
                        copy.addDocument(reader6);
                    }
                }
                if (jCheckBox4.isSelected()) {
                    if (reader4 != null) {
                        copy.addDocument(reader4);
                    }
                }
                if (jCheckBox8.isSelected()) {
                    if (reader7 != null) {
                        copy.addDocument(reader7);
                    }
                }
                if (jCheckBox10.isSelected()) {
                    if (reader1 != null) {
                        copy.addDocument(reader1);
                    }
                }
                try {
                    copy.close();
                    if (noValido) {
                        marcaAguaPDF(archivoTemporal);
                    } else {
                        Desktop.getDesktop().open(archivoTemporal);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "10136:\n" + ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                }
                archivoTemporal.deleteOnExit();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10135:\n" + ex.getMessage(), impresionesHC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }
            ((imphcuEvo) form).jLabel1.setVisible(false);
            ((imphcuEvo) form).jButton1.setEnabled(true);
            ((imphcuEvo) form).dispose();
        }
    }

    public void imprimir() {
        if (procedimientoJpa == null) {
            factory = Persistence.createEntityManagerFactory("ClipaEJBPU", AtencionUrgencia.props);
            procedimientoJpa = new HcuEvoProcedimientoJpaController(factory);
        }
        if (hcuEvolucion.getEstado() == 2 || hcuEvolucion.getEstado() == 1) {
            //LAB
            List<HcuEvoProcedimiento> hcuEvoProcedimientos = procedimientoJpa.ListFindInfoProcedimientoEvo(hcuEvolucion, 17);
            if (!hcuEvoProcedimientos.isEmpty()) {
                jCheckBox5.setSelected(true);
            }
            //IMAGENOLOGIA
            hcuEvoProcedimientos = procedimientoJpa.ListFindInfoProcedimientoEvo(hcuEvolucion, 15);
            if (!hcuEvoProcedimientos.isEmpty()) {
                jCheckBox6.setSelected(true);
            }
            jCheckBox4.setSelected(true);
        } else if (hcuEvolucion.getEstado() == 4 || hcuEvolucion.getEstado() == 3) {
            jCheckBox4.setSelected(false);
            jCheckBox5.setSelected(false);
            jCheckBox6.setSelected(false);
            jCheckBox8.setSelected(false);
            jCheckBox2.setSelected(true);
            jCheckBox9.setSelected(false);
        }
        hiloReporte ut = new hiloReporte(this);
        Thread thread = new Thread(ut);
        thread.start();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print_32.png"))); // NOI18N
        jLabel49.setText("Impresion de Documentos");
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel49.setMaximumSize(new java.awt.Dimension(190, 32));
        jLabel49.setMinimumSize(new java.awt.Dimension(190, 32));
        jLabel49.setPreferredSize(new java.awt.Dimension(190, 32));

        jButton1.setText("Aceptar");
        jButton1.setFocusable(false);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.setFocusable(false);
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBox4.setText("Nota Evolucion");
        jCheckBox4.setFocusable(false);
        jCheckBox4.setOpaque(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loader.gif"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Seleccione los documentos");

        jCheckBox5.setText("Laboratorios");
        jCheckBox5.setFocusable(false);
        jCheckBox5.setOpaque(false);

        jCheckBox6.setText("Imagenologia");
        jCheckBox6.setFocusable(false);
        jCheckBox6.setOpaque(false);

        jCheckBox2.setText("Nota Egreso");
        jCheckBox2.setFocusable(false);
        jCheckBox2.setOpaque(false);

        jCheckBox8.setText("Epicrisis");
        jCheckBox8.setFocusable(false);
        jCheckBox8.setOpaque(false);

        jCheckBox9.setText("Receta Medicamentos");
        jCheckBox9.setFocusable(false);
        jCheckBox9.setOpaque(false);

        jCheckBox10.setText("Incapacidad");
        jCheckBox10.setFocusable(false);
        jCheckBox10.setOpaque(false);

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jCheckBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jCheckBox6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jCheckBox4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jCheckBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jCheckBox8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jCheckBox9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCheckBox10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addGap(25, 25, 25)
                        .addComponent(jCheckBox5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

            hiloReporte ut = new hiloReporte(this);
            Thread thread = new Thread(ut);
            thread.start();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!noValido) {
            AtencionUrgencia.panelindex.FramEnable(true);
            AtencionUrgencia.panelindex.hc.cerrarPanel();
        }
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
