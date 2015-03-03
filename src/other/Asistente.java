
package other;

/**
 *
 * @author Alvaro Monsalve
 */
public class Asistente extends javax.swing.JDialog {

    public Asistente() {
        initComponents();
        jEditorPane1.setContentType("text/html");
    }
    
    public void getText(String title, String texto){
        String todo = "<H3>"+title+"</H3><hr>";
        jEditorPane1.setText("<html>"+todo+"</html>");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFocusable(false);
        setFocusableWindowState(false);
        setIconImage(null);
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(210, 596));
        setMinimumSize(new java.awt.Dimension(210, 596));
        setModalityType(java.awt.Dialog.ModalityType.DOCUMENT_MODAL);

        jEditorPane1.setContentType("text/html"); // NOI18N
        jScrollPane1.setViewportView(jEditorPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
