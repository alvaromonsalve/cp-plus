/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


/**
 *
 * @author Alvaro Monsalve
 */
public class _Ftp implements Runnable{
    FTPClient fTPClient;
    String sFTP ="192.168.1.210";
    String sUser = "admin";
    String sPassword = "admin";
    String pathOrigen;
    String pathDestin;
    String nameFile;
    String evento;
    
    /**
     * 
     * @param pathOrigen debe contener path completo con el nombre del archivo
     * @param pathDestino debe conteenr path sin el nombre del archivo
     * @param nameFile nombre del archivo
     */
    public void subirFile(String pathOrigen,String pathDestino, String nameFile)throws SocketException, IOException {
        evento="subir";
        fTPClient = new FTPClient();
        this.pathDestin=pathDestino;
        this.pathOrigen=pathOrigen;
        this.nameFile=nameFile;
            fTPClient.connect(sFTP);
            boolean login = fTPClient.login(sUser, sPassword);
            if (login){
                new Thread(this).start();
            }else{
                JOptionPane.showMessageDialog(null,"No se ha podido conectar con el servidor FTP");
            }
    }
    
     /**
     * 
     * @param pathOrigen debe contener path completo con el nombre del archivo
     * @param pathDestino debe conteenr path sin el nombre del archivo
     * @param nameFile nombre del archivo
     */
    public void deleteFile(String pathOrigen,String pathDestino, String nameFile)throws SocketException, IOException {
        evento="borrar";
        fTPClient = new FTPClient();
        this.pathDestin=pathDestino;
        this.pathOrigen=pathOrigen;
        this.nameFile=nameFile;
            fTPClient.connect(sFTP);
            boolean login = fTPClient.login(sUser, sPassword);
            if (login){
                new Thread(this).start();
            }else{
                JOptionPane.showMessageDialog(null,"No se ha podido conectar con el servidor FTP");
            }
    }
    
     /**
     * 
     * @param pathDestino debe contener path completo con el nombre del archivo
     */
    public void downloadFile(String pathOrigen)throws SocketException, IOException {
        evento="descargar";
        fTPClient = new FTPClient();
        this.pathOrigen=pathOrigen;
            fTPClient.connect(sFTP);
            boolean login = fTPClient.login(sUser, sPassword);
            if (login){
                new Thread(this).start();
            }else{
                JOptionPane.showMessageDialog(null,"No se ha podido conectar con el servidor FTP");
            }
    }
    
    

    @Override
    public void run() {
        if("subir".equals(evento)){
            try {
                fTPClient.changeWorkingDirectory("Files");
                fTPClient.makeDirectory(pathDestin);
                String s = System.getProperty("file.separator");
                fTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                BufferedInputStream buffIn=null;
                buffIn=new BufferedInputStream(new FileInputStream(pathOrigen));
                fTPClient.enterLocalPassiveMode();
                fTPClient.storeFile(pathDestin+s+nameFile, buffIn);
                buffIn.close(); 
                fTPClient.logout();
                fTPClient.disconnect();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"run IOException: "+ ex.getMessage());
            }
        }else if("borrar".equals(evento)){
            try {
                fTPClient.changeWorkingDirectory("Files");
                String s = System.getProperty("file.separator");
                fTPClient.deleteFile(pathDestin+s+nameFile);
                fTPClient.logout();
                fTPClient.disconnect();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"run IOException: "+ ex.getMessage());
            }
            
        }else if("descargar".equals(evento)){
            try {
                fTPClient.logout();
                fTPClient.disconnect();
                pathOrigen=pathOrigen.replace(System.getProperty("file.separator"), "/");
                String url = "ftp://" + sUser + ":" + sPassword + "@" + sFTP +"/Files/"+ pathOrigen;
                String osName = System.getProperty("os.name");
                 if (osName.startsWith("Windows")) {
                     Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                 }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"run Exception: "+ ex.getMessage());
            }
            
        }

    }
}
