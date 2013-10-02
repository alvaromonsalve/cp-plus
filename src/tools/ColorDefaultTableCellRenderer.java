/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author idlhdeveloper
 */
public class ColorDefaultTableCellRenderer extends DefaultTableCellRenderer{
    
    @Override
     public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column){
        column = 4;                
        setEnabled(table == null || table.isEnabled()); 
        setBackground(Color.white);
        table.setForeground(Color.black);        
        setHorizontalAlignment(2);                
        if(String.valueOf(table.getValueAt(row,column)).equals("PRIORIDAD")){ 
            setBackground(Color.lightGray);              
            table.setForeground(Color.black);    
        }else if(String.valueOf(table.getValueAt(row,column)).equals("MATERNA")){
            setBackground(Color.orange);                       
            table.setForeground(Color.black);
        }else if(String.valueOf(table.getValueAt(row,column)).equals("MENOR 8 AÑOS")){
            setBackground(Color.cyan);                       
            table.setForeground(Color.black);
        }else if(String.valueOf(table.getValueAt(row,column)).equals("MAYOR 65 AÑOS")){
            setBackground(Color.pink);                       
            table.setForeground(Color.black);
        }

    
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);         
        
        return this;
  }

}
