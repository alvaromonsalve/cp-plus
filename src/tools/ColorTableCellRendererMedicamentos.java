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
 * @author Alvaro Monsalve
 */
public class ColorTableCellRendererMedicamentos extends DefaultTableCellRenderer{
    
    @Override
    public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column){
        column = 5;                
        setEnabled(table == null || table.isEnabled());                 
        if(String.valueOf(table.getValueAt(row,column)).equals("EXISTE")){              
            table.setForeground(Color.BLACK);    
        }else if(String.valueOf(table.getValueAt(row,column)).equals("NO EXISTE")){                   
            table.setForeground(Color.GRAY);
        }
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);         
        return this;
    }
    
}
