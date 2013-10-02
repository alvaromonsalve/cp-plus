/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.regex.Pattern;

/**
 *
 * @author Alvaro Monsalve
 */
public class Numero_a_Letra {
    
    private final String[] UNIDADES = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
    private final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
        "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
        "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
    private final String[] CENTENAS = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
        "setecientos ", "ochocientos ", "novecientos "};
    
    public Numero_a_Letra(){
    }
    
    public String numericToString(String numero){
        String literal = "";
        String decimal = "";
        /** si el numero utiliza (.) en lugar de (,) -> se reemplaza */
        numero = numero.replace(".", ",");
        /** si el numero no tiene parte decimal, se le agrega ,00 */
        if(numero.indexOf(",")==-1){
            numero=numero+",00";
        }
        /** se valida formato de entrada -> 0,00 y 999 999 999,00 */
        if(Pattern.matches("\\d{1,9},\\d{1,2}", numero)){
            /** se divide el numero 0000000,00 -> entero y decimal*/
            String Num[] = numero.split(",");
            /** se da formato al numero decimal*/
            decimal="coma "+ getCentenas(Num[1]);

            if(Integer.parseInt(Num[1])>9){
                decimal="coma "+getDecenas(Num[1]);
            }else if(Integer.parseInt(Num[1])>0){
                decimal = "coma cero "+getUnidades(Num[1]);
            }else{
                decimal="";
            }
            
            /** se convierte el numero a literal */
            /** si el valor es cero */
            if(Integer.parseInt(Num[0])==0){
                literal="cero ";
            /** si el valor es millon */    
            }else if(Integer.parseInt(Num[0])>999999){
                literal=getMillones(Num[0]);
            /** si el valor es miles */
            }else if(Integer.parseInt(Num[0])>999){
                literal=getMiles(Num[0]);
            /** si el valor es centena */
            }else if(Integer.parseInt(Num[0])>99){
                literal=getCentenas(Num[0]);
            /** si el valor es decena */    
            }else if(Integer.parseInt(Num[0])>9){
                literal=getDecenas(Num[0]);
            /** si es unidad */
            }else{
                literal = getUnidades(Num[0]);
            }
            return (literal+decimal).toUpperCase();
        }else{
            return literal = null;
        }
    }
    
    /**
     * convierte numeros a literal de 1 a 9
     * @param numero digito a comvertir
     * @return literal del numero
     */
    private String getUnidades(String numero){
        /** si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9 */
        String num = numero.substring(numero.length()-1);
        return UNIDADES[Integer.parseInt(num)];
    }
    
    /**
     * convierte numeros a literal de 10 a 99
     * @param num digito a comvertir
     * @return literal del numero
     */
    private String getDecenas(String num){
        int n=Integer.parseInt(num);
        /** para casos como -> 01 - 09 */
        if(n<10){
            return getUnidades(num);
        /** para 20...99 */
        }else if(n>19){
            String u=getUnidades(num);
            /** para 20,30,40,50,60,70,80,90 */
            if(u.equals("")){
                return DECENAS[Integer.parseInt(num.substring(0,1))+8];   
            }else{
                return DECENAS[Integer.parseInt(num.substring(0,1))+8]+"y "+u;
            }
        /** numeros entre 11 y 19 */
        }else{
            return DECENAS[n-10];
        }
    }
    
    /**
     * convierte numeros a literal de 100 a 999
     * @param num digito a comvertir
     * @return literal del numero
     */
    private String getCentenas(String num){
        /** es centena */
        if(Integer.parseInt(num)>99){
            /** caso especial */
            if(Integer.parseInt(num) == 100){
                return" cien ";
            }else{
                return CENTENAS[Integer.parseInt(num.substring(0,1))]+getDecenas(num.substring(1));
            }
        /** se quita el 0 antes de comvertir a decenas Ej. 099 */    
        }else{
            return getDecenas(Integer.parseInt(num)+"");
        }
    }
    
    /**
     * convierte numeros a literal de 1000 a 999999
     * @param numero digito a comvertir
     * @return literal del numero
     */
    private String getMiles(String numero){
        /** obtiene las centenas */
        String c = numero.substring(numero.length()-3);
        /** Se obtiene los miles */
        String m=numero.substring(0,numero.length()-3);
        String n="";
        /** se comprueba que miles tenga el valor entero */
        if(Integer.parseInt(m)>0){
            n=getCentenas(m);
            return n+"mil "+getCentenas(c);
        }else{
            return ""+getCentenas(c);
        }
    }
    
    /**
     * convierte numeros a literal de 1000000 a 999999999
     * @param numero digito a comvertir
     * @return literal del numero
     */
    private String getMillones(String numero){
        /** se obtiene los miles */
        String miles = numero.substring(numero.length()-6);
        /** se obtiene los millones */
        String millon = numero.substring(0,numero.length()-6);
        String n="";
        if(millon.length()>1){
            n=getCentenas(millon)+ "millones ";
        }else{
            n=getUnidades(millon)+"millon ";
        }
        return n+getMiles(miles);
    }
}
