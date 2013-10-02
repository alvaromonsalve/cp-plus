/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import cz.dhl.ftp.Ftp;
import cz.dhl.ftp.FtpConnect;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Alvaro Monsalve
 */
public class _FtpDownload implements Runnable{
    String sFTP ="ftp://servidor";
    String sUser = "admin";
    String sPassword = "admin";
    
     /**
     * 
     * @param pathOrigen debe contener path completo con el nombre del archivo
     * @param pathDestino debe contener path completo con el nombre del archivo
     */
    public void downloadFile(String pathOrigen,String pathDestino) throws IOException{
        FtpConnect ftpConnect = FtpConnect.newConnect(sFTP);
        ftpConnect.setUserName(sUser);
        ftpConnect.setPassWord(sPassword);
        Ftp ftp = new Ftp();
        boolean login = ftp.connect(ftpConnect); 
         if (login){
            new Thread(this).start();
        }else{
            JOptionPane.showMessageDialog(null,"No se ha podido conectar con el servidor FTP");
        }
        
    }

    @Override
    public void run() {
        String s = System.getProperty("file.separator");

    }
    
}
