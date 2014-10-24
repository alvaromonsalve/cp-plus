/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Alvaro Monsalve
 */
public class MyDate {
    /**
     * dd/MM/yyyy
     */
    public static SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
    /**
     * yyyy-MM-dd HH:mm
     */
    public static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * dd-MM-yyyy
     */
    public static SimpleDateFormat ddMMyyyy2 = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * HH:mm
     */
    public static SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");
    /**
     * dd/MM/yyyy HH:mm
     */
    public static SimpleDateFormat yyyyMMddHHmm2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    
    /**
     * 
     * @param fecha1 java.util.Date
     * @param fecha2 java.util.Date fecha = new java.util.Date(); fecha.getTime();
     * @return 
     */
    public static List<String> getDifDateMinutes(Date fecha1,Time hora, long fecha2){
        Calendar fecha = new GregorianCalendar();
        fecha.setTime(fecha1);
        fecha.set(Calendar.HOUR_OF_DAY, hora.getHours());
        fecha.set(Calendar.MINUTE, hora.getMinutes());
        fecha.set(Calendar.SECOND, hora.getSeconds());
        long diferenciaMils =  fecha2 - fecha.getTime().getTime();
        long horas,minuto,segundo;
        long restohora,restominuto;
        horas=diferenciaMils/3600000;
        restohora=diferenciaMils%3600000;
        minuto=restohora/60000;
        restominuto=restohora%60000;
        segundo=restominuto/1000;
        List<String> lista = new ArrayList<String>();
        lista.add(0,String.valueOf(horas));
        if(minuto<=9){
            lista.add(1,"0"+String.valueOf(minuto));
        }else{
            lista.add(1,String.valueOf(minuto));
        }
        if(segundo<=9){
            lista.add(2,"0"+String.valueOf(segundo));
        }else{
            lista.add(2,String.valueOf(segundo));
        }
        return lista;
    }
    
    
    
}
