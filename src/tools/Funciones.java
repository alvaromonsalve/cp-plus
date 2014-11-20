/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;



import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Alvaro Monsalve
 */
public class Funciones {
    
    
    public ImageIcon CreateImageIcon(String path){
       URL imgURL = getClass().getResource(path);
       if (imgURL != null) {
        return new ImageIcon(imgURL);
        } else {
            JOptionPane.showMessageDialog(null,"No se pudo encontrar el archivo "+ path);
            return null;
        }
        
    }

    static final int BUFFER_SIZE = 2048;  
    static final byte[] buffer = new byte[BUFFER_SIZE];
    
    public static String getFileExtension(File file){
        int posPoint = file.getName().lastIndexOf(".");
        if(0<posPoint && posPoint <= file.getName().length() - 2){
            return file.getName().substring(posPoint + 1);
        }
        return "";
    }
    
    /**
     * 
     * @param pathOrigen debe contener path completo con el nombre del archivo
     * @param pathDestino debe conteenr path sin el nombre del archivo
     * @param name nombre del archivo
     */
    public static void fileUpload(String pathOrigen, String pathDestino, String name){
        try {
            new _Ftp().subirFile(pathOrigen, pathDestino,name);
        } catch (SocketException ex) {
            JOptionPane.showMessageDialog(null, "10060:\n"+ex.getMessage(), Funciones.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "10061:\n"+ex.getMessage(), Funciones.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10062:\n"+ex.getMessage(), Funciones.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    

    
        /**
     * 
     * @param pathOrigen debe contener path completo con el nombre del archivo
     * @param pathDestino debe conteenr path sin el nombre del archivo
     * @param name nombre del archivo
     */
    public static void fileDelete(String pathOrigen, String pathDestino, String name){
        try {
            new _Ftp().deleteFile(pathOrigen, pathDestino, name);
        } catch (SocketException ex) {
            JOptionPane.showMessageDialog(null,"fileUpload SocketException: "+ ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"fileUpload IOException: "+ ex.getMessage());
        }
    }
    
     /**
     * 
     * @param pathDestino debe conteenr path sin el nombre del archivo
     */
    public static void fileDownload(String pathDestino){
        try {
            new _Ftp().downloadFile(pathDestino);
        } catch (SocketException ex) {
            JOptionPane.showMessageDialog(null,"fileUpload SocketException: "+ ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"fileUpload IOException: "+ ex.getMessage());
        }
    }
    

    public static void reportDownload(String pathDestino, String fileName){
        try {
            new _Ftp_report().downloadFile(pathDestino,fileName);
        } catch (SocketException ex) {
            JOptionPane.showMessageDialog(null,"fileUpload SocketException: "+ ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"fileUpload IOException: "+ ex.getMessage());
        }
    }
    
    public static void setOcultarColumnas(JTable tbl, int columna[]){
            for(int i = 0;i<columna.length;i++){
                tbl.getColumnModel().getColumn(columna[i]).setMaxWidth(0);
                tbl.getColumnModel().getColumn(columna[i]).setMinWidth(0);
                tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMaxWidth(0);
                tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMinWidth(0);
           }
        }
    
    public static void setSizeColumnas(JTable tbl,int columna[], int sizeColumn[]){
        for(int i=0;i<columna.length;i++){
            tbl.getColumnModel().getColumn(columna[i]).setMinWidth(sizeColumn[i]);
            tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMaxWidth(sizeColumn[i]);
        }
    }
    
    public static void setColorTableHeader(JTable tbl,int columna[]){
        for(int i=0;i<columna.length;i++){
            tbl.getColumnModel().getColumn(columna[i]).setHeaderRenderer(new tools.colorRenderTableHeader(Color.white, Color.BLACK));
        }
    }
    
        
    public static void setLabelInfo(String s){
       atencionurgencia.AtencionUrgencia.panelindex.jlInfo.setText(s);
    }
    
    public static void setLabelInfo(){
        atencionurgencia.AtencionUrgencia.panelindex.jlInfo.setText("");
    }
       
    public static String FormatDecimal(String numero){

        numero = numero.replace(".", ",");
        if(numero.indexOf(",")==-1){
            numero=numero+",00";
        }
        if(Pattern.matches("\\d{1,9},\\d{1,2}", numero)){
            String Num[] = numero.split(",");
            for(int i=0;i<Num[0].length();i++){
                if(!Character.isDigit(Num[0].charAt(i))){
                    return "";
                }
            }
            for(int i=0;i<Num[1].length();i++){
                if(!Character.isDigit(Num[1].charAt(i))){
                    return "";
                }
            }
            if(Num[1].length()==1){
                Num[1]=Num[1]+"0";
                numero = Num[0]+","+Num[1];                
            }
            if (Float.parseFloat(numero.replace(",", ".")) >= Float.parseFloat("1000.00")) {
                numero = "";
            }
        }else{
            return "";
        }
        return numero;
    }
}
