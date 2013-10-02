/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Alvaro Monsalve
 */
public class RendererArbol extends DefaultTreeCellRenderer{
    
    public ImageIcon atencion = new ImageIcon(getClass().getResource("/images/Hospital_16x16.png"));
    public ImageIcon triage = new ImageIcon(getClass().getResource("/images/Stethoscope_16x16.png"));
    public ImageIcon hc = new ImageIcon(getClass().getResource("/images/PatientFile_16x16.png"));
    public RendererArbol(){
        
    }
    
    @Override
    public Component getTreeCellRendererComponent(
            JTree tree, Object value, boolean selected, boolean expanded, 
            boolean leaf, int row, boolean hasFocus){
        super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);
        String val = value.toString();
        if (val.equals("Atención")){  
                 setIcon(atencion);
        }
        if (val.equals("Triage")){  
                 setIcon(triage);
        }
        if (val.equals("Historia Clínica")){  
                 setIcon(hc);
        }
        return this;
    }
}
