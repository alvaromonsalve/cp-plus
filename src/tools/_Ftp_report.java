
package tools;


import atencionurgencia.AtencionUrgencia;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Alvaro Monsalve
 */
public class _Ftp_report implements Runnable{
    FTPClient fTPClient;
    String sFTP ="192.168.1.102";
    String sUser = "userclipa_ftp";
    String sPassword = "Ccqf0owa58eM";
    String pathOrigen;
    String pathDestin;
    String fileName;
    String var="";
    private static final Logger LOGGER = LoggerFactory.getLogger(_Ftp_report.class);
    
    public void downloadFile(String pathOrigen, String fileName) throws SocketException, IOException {  
        LOGGER.trace("Inicio de descarga de reporte "+fileName);
        fTPClient = new FTPClient();
        this.pathOrigen = pathOrigen;
        this.fileName = fileName;
        LOGGER.trace("Conectando con servidor ftp");
        fTPClient.connect(sFTP);
        boolean login = fTPClient.login(sUser, sPassword);
        if (login) {
            new Thread(this).start();
        } else {
            LOGGER.warn("No se pudo conectar con el servidor FTP");
            JOptionPane.showMessageDialog(null, "No se ha podido conectar con el servidor FTP");
        }
    }
   
    @Override
    public void run() {
        try {
            LOGGER.trace("Inicio de Hilo para descarga de reporte "+fileName);
            fTPClient.enterLocalPassiveMode();
            fTPClient.setFileType(FTP.BINARY_FILE_TYPE);
            String remoteFile1 = "/reportes/"+pathOrigen+"/"+fileName;
            File downloadFile1 = new File("C:/Downloads/"+fileName);
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            InputStream inputStream = fTPClient.retrieveFileStream(remoteFile1);
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                outputStream1.write(bytesArray, 0, bytesRead);
            }
            boolean success = fTPClient.completePendingCommand();
            if (success) {
                LOGGER.trace("Archivo descargado satisfactoriamente "+fileName);
            }
            outputStream1.close();
            inputStream.close();
        } catch (Exception ex) {
            LOGGER.error(fileName+": "+ex.getMessage()+"\n"+ex.getCause());
            JOptionPane.showMessageDialog(null, "run Exception: " + ex.getMessage());
        }finally {
            try {
                LOGGER.trace("Cerrando conexion con servidor ftp");
                if (fTPClient.isConnected()) {
                    fTPClient.logout();
                    fTPClient.disconnect();
                }
            } catch (IOException ex) {
                LOGGER.error("ftp_close: "+ex.getMessage()+"\n"+ex.getCause());
                JOptionPane.showMessageDialog(null, "ftp_close - Exception: " + ex.getMessage());
            }
        }
    }
}
