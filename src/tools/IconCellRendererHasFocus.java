/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

//import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 *
 * @author Alvaro Monsalve
 */
public class IconCellRendererHasFocus extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(value instanceof JLabel){
            JLabel label = (JLabel)value;
            label.setOpaque(true);
            fillColor(table, label, isSelected,hasFocus);
            return label;
        }else if(value instanceof JTable){
            JTable jtable = (JTable)value;
            jtable.setOpaque(true);
            fillColor(table, jtable, isSelected,hasFocus);
            return jtable;
        }else if(value instanceof JCheckBox){
            JCheckBox jCheckBox = (JCheckBox)value;
            jCheckBox.setOpaque(true);
            fillColor(table, jCheckBox, isSelected,hasFocus);
            return jCheckBox;
        }else{
            return super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        }
    }
    
    public void fillColor(JTable t,JLabel l,boolean isSelected,boolean ishasFocus){
        if(isSelected && ishasFocus){
            l.setBackground(t.getSelectionBackground());
            l.setForeground(t.getSelectionForeground());
        }else{
            l.setBackground(t.getBackground());
            l.setForeground(t.getForeground());
        }
    }

    private void fillColor(JTable t, JTable l, boolean isSelected,boolean ishasFocus) {
        if(isSelected && ishasFocus){
            l.setBackground(t.getSelectionBackground());
            l.setForeground(t.getSelectionForeground());
        }else{
            l.setBackground(t.getBackground());
            l.setForeground(t.getForeground());
        }
    }
    
    public void fillColor(JTable t,JCheckBox c,boolean isSelected,boolean ishasFocus){
        if(isSelected && ishasFocus){
            c.setBackground(t.getSelectionBackground());
            c.setForeground(t.getSelectionForeground());
        }else{
            c.setBackground(t.getBackground());
            c.setForeground(t.getForeground());
        }
    }
    
}
