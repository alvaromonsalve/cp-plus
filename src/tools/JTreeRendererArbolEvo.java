/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import entidades.HcuEvolucion;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Administrador
 */
public class JTreeRendererArbolEvo extends DefaultTreeCellRenderer{
    
    private final ImageIcon bullet_grey,bullet_green,folder_open,folder,bullet_yellow;
    
    public JTreeRendererArbolEvo(){
        this.bullet_grey=new ImageIcon(getClass().getResource("/images/bullet_grey.png"));
        this.bullet_green=new ImageIcon(getClass().getResource("/images/bullet_green.png"));
        this.bullet_yellow=new ImageIcon(getClass().getResource("/images/bullet_yellow.png"));
        this.folder_open=new ImageIcon(getClass().getResource("/images/folder_open.png"));
        this.folder=new ImageIcon(getClass().getResource("/images/folder.png"));
    }
    
    @Override
    public Component getTreeCellRendererComponent(
            JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus){
        super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);   
        try {
            if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==2){
                this.setIcon(bullet_green);
            }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==1){
                this.setIcon(bullet_yellow);
            }else{
                this.setIcon(bullet_grey);
            }
        } catch (Exception e) {
             this.setOpenIcon(folder_open);
             this.setClosedIcon(folder);
        }
        return this;
    }
    
}
