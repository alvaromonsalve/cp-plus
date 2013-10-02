/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;


/**
 *
 * @author Alvaro Monsalve
 */
public class CausasExternas {
    
    
    public static String causas(String causa){
        String strCausa;                
        if(causa.equals("01")){                
                 strCausa="ACCIDENTE DE TRABAJO";                
            } else if(causa.equals("02")){                
                strCausa="ACCIDENTE DE TRANSITO";                
            } else if(causa.equals("03")){                
                strCausa="ACCIDENTE RABICO";                 
            } else if(causa.equals("04")){                
                 strCausa="ACCIDENTE OFIDICO";                 
            } else if(causa.equals("05")){                
                strCausa = "OTRO TIPO DE ACCIDENTE";                 
            } else if(causa.equals("06")){                
                strCausa="EVENTO CATASTROFICO";
            } else if(causa.equals("07")){
                strCausa="LESION POR AGRESION";
            } else if(causa.equals("08")){
                strCausa="LESION AUTO-INFLIGIDA";
            } else if(causa.equals("09")){
                strCausa="SOSPECHA DE MALTRATO FISICO";
            } else if(causa.equals("10")){
                strCausa="SOSPECHA DE ABUSO SEXUAL";
            } else if(causa.equals("11")){
                strCausa="SOSPECHA DE VIOLENCIA SEXUAL";
            } else if(causa.equals("12")){
                strCausa="SOSPECHA DE MALTRATO EMOCIONAL";
            } else if(causa.equals("13")){
                 strCausa="ENFERMEDAD GENERAL";
            } else if(causa.equals("14")){
                 strCausa="ENFERMEDAD PROFESIONAL";
            } else{
                 strCausa="OTRA";
            }
        return strCausa;
    }
    
    public static String medioIngreso(String causa){
        String strCausa;                
        if(!causa.equals("0")){                
                 strCausa="Ingresó por sus propios medio";                
            } else {
                 strCausa="No Ingresó por sus propios medios";
            }
        return strCausa;
    }
    
}
