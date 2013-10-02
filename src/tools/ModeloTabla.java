/*
 * ModeloTabla.java
 *
 */

package tools;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.table.*;

/**
 *
 * @author hades
 */
public class ModeloTabla extends AbstractTableModel{
    Vector grilla;
    String [] titulo;
    int numCols;
    public String error;
    public static final int DDMMYYYY=0;
    public static final int MMDDYYYY=1;
    public static final int YYYYMMDD=2;
    
    /**
     * Crea una nueva instancia de ModeloTabla
     */
     
    public ModeloTabla(String [] tit){
        numCols=tit.length;
        titulo=tit;
        String[] registro=new String[numCols];
        for (int i=1;i<=numCols;i++){
            registro[i-1]="";
        }
        grilla=new Vector();
        grilla.addElement(registro);
        fireTableChanged(null);
    }
    public ModeloTabla(ResultSet rs) {
        try{
        ResultSetMetaData metaDatos;
        metaDatos=rs.getMetaData();
        numCols=metaDatos.getColumnCount();
        titulo=new String[numCols];
        for (int i=1;i<=numCols;i++){
           titulo[i-1] =metaDatos.getColumnLabel(i);
            }/*for*/
        grilla = new Vector();
        while (rs.next()){
            String [] registro=new String [numCols];
            for (int i=0;i<numCols;i++){
                registro[i]=rs.getString(i+1);
            }/*for*/
            grilla.addElement(registro);
        }/*while*/
        fireTableChanged(null);
        }/*try*/
        catch (Exception e) {
            error=e.getMessage();
        }
    }

    public String getValueAt(int rowIndex, int columnIndex) {
        return ((String [])grilla.elementAt(rowIndex))[columnIndex];
    }

    public int getColumnCount() {
        return numCols;
    }

    public int getRowCount() {
        return grilla.size();
    }

    public String getColumnName(int column) {    
         return titulo[column];
    }
    
    public static Connection conectarDB(String Driver, String Url,String usuario, String pass) throws Exception{
        Class.forName(Driver).newInstance();
        return DriverManager.getConnection(Url,usuario,pass);
    }
    
    public static String  FechaACadena(GregorianCalendar fecha){
     String dia;
     String mes;
     String anio;
     DecimalFormat d2=new DecimalFormat("00");
     DecimalFormat d4=new DecimalFormat("0000");
     dia=d2.format(fecha.get(GregorianCalendar.DAY_OF_MONTH));
     mes=d2.format(fecha.get(GregorianCalendar.MONTH)+1);
     anio=d4.format(fecha.get(GregorianCalendar.YEAR));
     return dia + "/" + mes + "/" + anio;
    }
    
    public static GregorianCalendar CadenaAFecha(String fecha){
	int anio;
	int mes;
	int dia;
	int barra1;
	int barra2;
	if (fecha == null) throw new java.lang.IllegalArgumentException();
	barra1 = fecha.indexOf('/');
	barra2 = fecha.indexOf('/', barra1+1);
	if ((barra1 > 0) & (barra2 > 0) & (barra2 < fecha.length())) {
	    dia = Integer.parseInt(fecha.substring(0, barra1)) ;
	    mes = Integer.parseInt(fecha.substring(barra1+1, barra2)) ;
	    anio = Integer.parseInt(fecha.substring(barra2+1));	 
	} else {
	    throw new java.lang.IllegalArgumentException();
	}		
	return new GregorianCalendar(anio, mes-1, dia);
    }
    
    public static String esFecha(String fecha){
	int anio;
	int mes;
	int dia;
	int barra1;
	int barra2;
        GregorianCalendar fec;
	if (fecha == null) return "";
	barra1 = fecha.indexOf('/');
	barra2 = fecha.indexOf('/', barra1+1);
	if ((barra1 > 0) & (barra2 > 0) & (barra2 < fecha.length())) {
	    try{
                dia = Integer.parseInt(fecha.substring(0, barra1)) ;
                mes = Integer.parseInt(fecha.substring(barra1+1, barra2))-1 ;
                anio = Integer.parseInt(fecha.substring(barra2+1));
            }
            catch (Exception e){
                return "";
            }
        }
        else {
	    return "";
	}		
	fec= new GregorianCalendar(anio, mes, dia);
        String sdia;
        String smes;
        String sanio;
        DecimalFormat d2=new DecimalFormat("00");
        DecimalFormat d4=new DecimalFormat("0000");
        sdia=d2.format(fec.get(GregorianCalendar.DAY_OF_MONTH));
        smes=d2.format(fec.get(GregorianCalendar.MONTH)+1);
        sanio=d4.format(fec.get(GregorianCalendar.YEAR));
        return sdia + "/" + smes + "/" + sanio;
    }
    
    public static String formatearFecha (String fechadmy,int formato){
        String anio;
	String mes;
	String dia;
	int barra1;
	int barra2;
        String aux=fechadmy;
	if (fechadmy == null) {
            throw new java.lang.IllegalArgumentException();
        }
	barra1 = fechadmy.indexOf('/');
	barra2 = fechadmy.indexOf('/', barra1+1);  
        dia = fechadmy.substring(0, barra1);
        mes = fechadmy.substring(barra1+1, barra2);
        anio = fechadmy.substring(barra2+1);
        switch (formato){
        case DDMMYYYY:aux= dia + "/" + mes + "/" + anio;break;
        case MMDDYYYY:aux= mes + "/" + dia + "/" + anio;break;
        case YYYYMMDD:aux= anio + "/" + mes + "/" + dia;break;
        }
        return aux;
    }

     
}
