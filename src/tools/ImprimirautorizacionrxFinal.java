/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;
import com.itextpdf.text.pdf.PdfReader;
import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ImprimirautorizacionrxFinal {
    private String idevu, codigo, version, nombrereport, servicio;
    private Connection conexion;
     public File tempFile;
    
    public PdfReader ImprimirautorxFinal(){
        try {
            Map parametro = new HashMap();
            parametro.put("idevu",""+getIdevu());
            parametro.put("codigo",""+getCodigo());
            parametro.put("NombreReport",""+getNombrereport());
            parametro.put("version",""+getVersion());
            parametro.put("servicio",""+getServicio());
            JasperPrint informe = JasperFillManager.fillReport(System.getProperty("user.dir")+"/Reportes/solicitudprocedimientorxpost.jasper", parametro, getConnection());
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, informe);
            tempFile = File.createTempFile("solicitudprocedimientosrx",".pdf");
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,tempFile);
            exporter.exportReport();
            return new PdfReader(tempFile.getAbsolutePath());
//            Desktop.getDesktop().open(tempFile);
//            tempFile.deleteOnExit();
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Error General Lanzando Reporte Descripcion: Ex00026" +e.getMessage());
            return null;
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null,"Error General Lanzando Reporte Descripcion: Ex00026" +e.getMessage());
            return null;
        }
    }
     public void setConnection(Connection conexion){
        this.conexion = conexion;
    }
    private Connection getConnection(){
        return conexion;
    }

    public String getIdevu() {
        return idevu;
    }

    public void setIdevu(String idevu) {
        this.idevu = idevu;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNombrereport() {
        return nombrereport;
    }

    public void setNombrereport(String nombrereport) {
        this.nombrereport = nombrereport;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
