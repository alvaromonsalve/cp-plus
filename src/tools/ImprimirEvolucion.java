/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;
import com.itextpdf.text.pdf.PdfReader;
import java.io.File;
import java.io.IOException;
import com.mysql.jdbc.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ImprimirEvolucion {
     private String idevolucion, Nombrereport, CodigoReport, Servicioreport, Versionreport;
     private Connection conexion;
     public File tempFile;
     
     public PdfReader ImprimirEvolucion(){
        try{
            Map parametro = new HashMap();
            parametro.put("IDEVOLUCION",""+getIdevolucion());
            parametro.put("NameReport",""+getNombrereport());
            parametro.put("codigo", ""+getCodigoReport());
            parametro.put("servicio",""+getServicioreport());
            parametro.put("version",""+getVersionreport());         
            JasperPrint informe = JasperFillManager.fillReport(System.getProperty("user.dir")+"/Reportes/Evolucion.jasper", parametro, getConnection());
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, informe);
            tempFile = File.createTempFile("Evolucion",".pdf");
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,tempFile);
            exporter.exportReport();
            return new PdfReader(tempFile.getAbsolutePath());
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
    
    public String getIdevolucion() {
        return idevolucion;
    }

    public void setIdevolucion(String idevolucion) {
        this.idevolucion = idevolucion;
    }

    public String getNombrereport() {
        return Nombrereport;
    }

    public void setNombrereport(String Nombrereport) {
        this.Nombrereport = Nombrereport;
    }

    public String getCodigoReport() {
        return CodigoReport;
    }

    public void setCodigoReport(String CodigoReport) {
        this.CodigoReport = CodigoReport;
    }

    public String getServicioreport() {
        return Servicioreport;
    }

    public void setServicioreport(String Servicioreport) {
        this.Servicioreport = Servicioreport;
    }

    public String getVersionreport() {
        return Versionreport;
    }

    public void setVersionreport(String Versionreport) {
        this.Versionreport = Versionreport;
    }
}
