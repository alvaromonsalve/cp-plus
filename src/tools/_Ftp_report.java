
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
    
    public void downloadFile(String pathOrigen, String fileName) throws SocketException, IOException {  
        fTPClient = new FTPClient();
        this.pathOrigen = pathOrigen;
        this.fileName = fileName;
        fTPClient.connect(sFTP);
        boolean login = fTPClient.login(sUser, sPassword);
        if (login) {
            new Thread(this).start();
        } else {
            JOptionPane.showMessageDialog(null, "No se ha podido conectar con el servidor FTP");
        }
    }
   
    @Override
    public void run() {
        try {
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
            }
            outputStream1.close();
            inputStream.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "run Exception: " + ex.getMessage());
        }finally {
            try {
                if (fTPClient.isConnected()) {
                    fTPClient.logout();
                    fTPClient.disconnect();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "ftp_close - Exception: " + ex.getMessage());
            }
        }
    }
}
