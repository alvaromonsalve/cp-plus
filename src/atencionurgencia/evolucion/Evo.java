package atencionurgencia.evolucion;

import atencionurgencia.AtencionUrgencia;
import atencionurgencia.ListadoPacientes.Ftriaje;
import atencionurgencia.ingreso.pAnexo3;
import atencionurgencia.ingreso.pTratImagenologia;
import atencionurgencia.ingreso.pTratInterconsulta;
import atencionurgencia.ingreso.pTratLaboratorio;
import atencionurgencia.ingreso.pTratMasProcedimientos;
import atencionurgencia.ingreso.pTratMedic;
import atencionurgencia.ingreso.pTratMedidaGeneral;
import atencionurgencia.ingreso.pTratOtrasInterconsultas;
import atencionurgencia.ingreso.pTratPConsultDiag;
import atencionurgencia.ingreso.pTratQuirurgico;
import entidades.HcuEvolucion;
import entidades.InfoAdmision;
import entidades.InfoAntPersonales;
import entidades.InfoHcExpfisica;
import entidades.InfoHistoriac;
import entidades.InfoPaciente;
import entidades.InfoPruebasComplement;
import entidades.StaticCie10;
import entidades.StaticEspecialidades;
import java.awt.Color;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jpa.InfoAntPersonalesJpaController;
import jpa.StaticCie10JpaController;
import other.*;
import tools.Funciones;
import tools.myStringsFunctions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import jpa.HcuEvolucionJpaController;
import tools.JTreeRendererArbolEvo;
import tools.MyDate;

/**
 *
 * @author Alvaro Monsalve
 */
public class Evo extends javax.swing.JPanel {

    // <editor-fold defaultstate="collapsed" desc="Declaracion de variables">
    FormPersonas personas = null;
    public InfoPaciente infopaciente = null;
    public InfoHistoriac infohistoriac = null;
    public InfoAdmision infoadmision = null;
    private InfoHcExpfisica infoexploracion = null;
    private StaticCie10 staticCie10 = null;
    private StaticCie10JpaController staticcie = null;
    private EntityManagerFactory factory;
    public int idDiag1 = 1, idDiag2 = 1, idDiag3 = 1, idDiag4 = 1, idDiag5 = 1;
    public DefaultTableModel modeloAyudDiag, modDestroyAyudDiag;
    public int finalizar = 0;
    private Boolean edite = false;
    private final Object dato[] = null;
    private List<InfoPruebasComplement> listaPruebas = null;
    private final String s = System.getProperty("file.separator");
    private InfoAntPersonalesJpaController antPersonalesJPA = null;
    private InfoAntPersonales antPersonales;
    public pTratMedic pMedic = null;
    public pTratPConsultDiag pConsultDiag = null;
    public pTratLaboratorio pLaboratorio = null;
    public pTratImagenologia pImagenologia = null;
    public pTratQuirurgico pQuirurgico = null;
    public pTratMasProcedimientos pProcedimientos = null;
    public pTratInterconsulta pInterconsulta0 = null, pInterconsulta1 = null, pInterconsulta2 = null, pInterconsulta3 = null, pInterconsulta4 = null;
    public pTratOtrasInterconsultas pOtrasInterconsultas = null;
    public pTratMedidaGeneral pMedidaGeneral = null;
    public pAnexo3 pAnexo31 = null;
    public Ftriaje ftriaje = null;
    private newEvo evol = null;
    private pSubjetivo subjetivo = null;
    private pObjetivo objetivo = null;
    public pAnalisis analisis = null;
    public pPlan pplan = null;
    private grafic_sVitales gSignos = null;
    private List<HcuEvolucion> hcuEvolucionList = null;
    private HcuEvolucionJpaController hcuEvolucionJpaController = null;
    private DefaultTreeModel modeloTree;
    private DefaultMutableTreeNode EvosHC;
    private HcuEvolucion evoSeleccion = null;
    private datosHCU cU;
    public int tipoEvo;
    public StaticEspecialidades staticEspecialidades = null;

    // </editor-fold>
    /**
     * Creates new form HC
     */
    private HcuEvolucion hcuEvolucion = null;
    private int est = 0;

    public Evo() {
        initComponents();
        factory = Persistence.createEntityManagerFactory("ClipaEJBPU", AtencionUrgencia.props);
        TablaAyudDiag();
        inicio();
        InputMap map2 = jTextArea10.getInputMap(JTextArea.WHEN_FOCUSED);
        map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }

    private void inicio() {
        jpCentro.removeAll();
        jpMotivoC.setBounds(0, 0, 584, 472);
        jpCentro.add(jpMotivoC);
        jpMotivoC.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
        activeCheck(1);
        jButton7.setVisible(false);
    }

    // <editor-fold defaultstate="collapsed" desc="private void activeCheck(int val){">
    private void activeCheck(int val) {
        switch (val) {
            case 1:
                jCheckBox6.setSelected(true);
                jCheckBox10.setSelected(false);
                jCheckBox11.setSelected(false);
                jCheckBox12.setSelected(false);
                jCheckBox4.setSelected(false);
                jCheckBox13.setSelected(false);
                jCheckBox5.setSelected(false);
                jCheckBox14.setSelected(false);
                break;
            case 2:
                jCheckBox6.setSelected(false);
                jCheckBox10.setSelected(true);
                jCheckBox11.setSelected(false);
                jCheckBox12.setSelected(false);
                jCheckBox4.setSelected(false);
                jCheckBox13.setSelected(false);
                jCheckBox5.setSelected(false);
                jCheckBox14.setSelected(false);
                break;
            case 3:
                jCheckBox6.setSelected(false);
                jCheckBox10.setSelected(false);
                jCheckBox11.setSelected(true);
                jCheckBox12.setSelected(false);
                jCheckBox4.setSelected(false);
                jCheckBox13.setSelected(false);
                jCheckBox5.setSelected(false);
                jCheckBox14.setSelected(false);
                break;
            case 4:
                jCheckBox6.setSelected(false);
                jCheckBox10.setSelected(false);
                jCheckBox11.setSelected(false);
                jCheckBox12.setSelected(true);
                jCheckBox4.setSelected(false);
                jCheckBox13.setSelected(false);
                jCheckBox5.setSelected(false);
                jCheckBox14.setSelected(false);
                break;
            case 5:
                jCheckBox6.setSelected(false);
                jCheckBox10.setSelected(false);
                jCheckBox11.setSelected(false);
                jCheckBox12.setSelected(false);
                jCheckBox4.setSelected(true);
                jCheckBox13.setSelected(false);
                jCheckBox5.setSelected(false);
                jCheckBox14.setSelected(false);
                break;
            case 6:
                jCheckBox6.setSelected(false);
                jCheckBox10.setSelected(false);
                jCheckBox11.setSelected(false);
                jCheckBox12.setSelected(false);
                jCheckBox4.setSelected(false);
                jCheckBox14.setSelected(false);
                jCheckBox13.setSelected(true);
                jCheckBox5.setSelected(false);
                break;
            case 7:
                jCheckBox6.setSelected(false);
                jCheckBox10.setSelected(false);
                jCheckBox11.setSelected(false);
                jCheckBox12.setSelected(false);
                jCheckBox4.setSelected(false);
                jCheckBox13.setSelected(false);
                jCheckBox14.setSelected(false);
                jCheckBox5.setSelected(true);
                break;
            case 8:
                jCheckBox6.setSelected(false);
                jCheckBox10.setSelected(false);
                jCheckBox11.setSelected(false);
                jCheckBox12.setSelected(false);
                jCheckBox4.setSelected(false);
                jCheckBox13.setSelected(false);
                jCheckBox5.setSelected(false);
                jCheckBox14.setSelected(true);
                break;
        }
    }
    // </editor-fold>

    public void setSelectionNivelTriage(int var) {
        switch (var) {
            case 0:
                jRadioButton1.setSelected(true);
                break;
            case 1:
                jRadioButton2.setSelected(true);
                break;
            case 2:
                jRadioButton3.setSelected(true);
                break;
            case 3:
                jRadioButton4.setSelected(true);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="private DefaultTableModel getModAyudaDiag(){">
    private DefaultTableModel getModAyudaDiag() {
        try {
            return (new DefaultTableModel(
                    null, new String[]{"imagen", "iamgen2", "archivo", "file", "tipo", "ruta", "bd"}) {
                        Class[] types = new Class[]{
                            javax.swing.JLabel.class,
                            javax.swing.JLabel.class,
                            java.lang.String.class,
                            java.io.File.class,
                            java.lang.String.class,
                            java.lang.String.class,
                            java.lang.String.class
                        };

                        boolean[] canEdit = new boolean[]{
                            false, false, false, false, false, false, false
                        };

                        @Override
                        public Class getColumnClass(int columnIndex) {
                            return types[columnIndex];
                        }

                        @Override
                        public boolean isCellEditable(int rowIndex, int colIndex) {
                            return canEdit[colIndex];
                        }
                    });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10071:\n" + ex.getMessage(), Evo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
    }
    // </editor-fold>

    private void TablaAyudDiag() {
        try {
            modeloAyudDiag = getModAyudaDiag();
            modDestroyAyudDiag = getModAyudaDiag();
            jtbTratamiento4.setModel(modeloAyudDiag);
            jtbTratamiento4.getTableHeader().setReorderingAllowed(false);
            jtbTratamiento4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Funciones.setSizeColumnas(jtbTratamiento4, new int[]{0, 1, 2}, new int[]{20, 20, 330});
            Funciones.setOcultarColumnas(jtbTratamiento4, new int[]{3, 5, 6});
            jtbTratamiento4.setDefaultRenderer(Object.class, new tools.IconCellRenderer());
            jtbTratamiento4.setTableHeader(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10052:\n" + ex.getMessage(), Evo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void viewClinicHistory(InfoHistoriac infoHistoriac) {
        this.infohistoriac = infoHistoriac;
        edite = true;
        setHistoryC();
        setFisicExplorer();
        setHelpDiag();
        setJTreeEvo();
    }

    private void setJTreeEvo() {
        if (hcuEvolucionJpaController == null) {
            hcuEvolucionJpaController = new HcuEvolucionJpaController(factory);
        }
        EvosHC = new DefaultMutableTreeNode("EVOLUCIONES");
        modeloTree = new DefaultTreeModel(EvosHC);
        hcuEvolucionList = hcuEvolucionJpaController.FindHcuEvolucions(infohistoriac);
        jTree1.setModel(modeloTree);
        jTree1.setCellRenderer(new JTreeRendererArbolEvo());
        for (HcuEvolucion hcuEvo : hcuEvolucionList) {
            TreeNode raiz = (TreeNode) jTree1.getModel().getRoot();
            DefaultMutableTreeNode fechaEvo = null;
            DefaultMutableTreeNode Evo = null;
            if (hcuEvo.getEstado() == 3 || hcuEvo.getEstado() == 4) {
//                   jButton1.setVisible(false);
//                   jButton14.setVisible(false);
//                   if(hcuEvo.getEstado()== 4){
                jButton1.setEnabled(false);
                jButton14.setEnabled(false);
                est = 1;
//                   }
            } else {
                jButton1.setVisible(true);
                jButton14.setVisible(true);
                est = 0;
            }
            boolean existeFechaEvo = false;
            for (int i = 0; i < raiz.getChildCount(); i++) {
                if (raiz.getChildAt(i).toString().equals(MyDate.ddMMyyyy.format(hcuEvo.getFechaEvo()))) {
                    existeFechaEvo = true;
                    fechaEvo = (DefaultMutableTreeNode) modeloTree.getChild(EvosHC, i);
                    Evo = new DefaultMutableTreeNode(hcuEvo);
                    fechaEvo.add(Evo);
                    break;
                }
            }
            if (!existeFechaEvo) {
                fechaEvo = new DefaultMutableTreeNode(MyDate.ddMMyyyy.format(hcuEvo.getFechaEvo()));
                modeloTree.insertNodeInto(fechaEvo, EvosHC, 0);
                Evo = new DefaultMutableTreeNode(hcuEvo);
                fechaEvo.add(Evo);
            }
        }
        jTree1.expandRow(0);
    }

    // <editor-fold defaultstate="collapsed" desc="Mostrar datos de Nota de Ingreso">
    public void DatosAntPersonales() {
        if (antPersonalesJPA == null) {
            antPersonalesJPA = new InfoAntPersonalesJpaController(factory);
        }
        antPersonales = antPersonalesJPA.findInfoAntPersonalesIDPac(infopaciente);
        jTextArea11.setText(antPersonales.getAlergias());
        jTextArea11.setCaretPosition(0);
        jTextArea12.setText(antPersonales.getIngresosPrevios());
        jTextArea12.setCaretPosition(0);
        jTextArea22.setText(antPersonales.getTraumatismos());
        jTextArea22.setCaretPosition(0);
        jTextArea23.setText(antPersonales.getTratamientos());
        jTextArea23.setCaretPosition(0);
        jTextArea24.setText(antPersonales.getSituacionBasal());
        jTextArea24.setCaretPosition(0);
        jCheckBox1.setSelected(antPersonales.getHta());
        jCheckBox2.setSelected(antPersonales.getDm());
        jCheckBox3.setSelected(antPersonales.getDislipidemia());
        jCheckBox7.setSelected(antPersonales.getTabaco());
        jCheckBox8.setSelected(antPersonales.getAlcohol());
        jCheckBox9.setSelected(antPersonales.getDroga());
        jTextArea7.setText(antPersonales.getOtrosHabitos());
        jTextArea7.setCaretPosition(0);
        jTextArea5.setText(antPersonales.getDescHdd());
        jTextArea5.setCaretPosition(0);
        jTextArea25.setText(antPersonales.getAntFamiliares());
        jTextArea25.setCaretPosition(0);
    }

    private void setHistoryC() {
        infoadmision = infohistoriac.getIdInfoAdmision();
        infopaciente = infoadmision.getIdDatosPersonales();
        jlbNombrePaciente.setText(infopaciente.getNombre1() + " " + infopaciente.getApellido1() + " [" + infopaciente.getNumDoc() + "]     [" + infohistoriac.getIdInfoAdmision().getIdEntidadAdmision().getNombreEntidad() + "]");
        jTextArea10.setText(infohistoriac.getMotivoConsulta());
        jTextArea10.setCaretPosition(0);
        jComboBox1.setSelectedItem(infohistoriac.getCausaExterna());
        this.setSelectionNivelTriage(infohistoriac.getNivelTriaje());
        jTextArea11.setText(infohistoriac.getAlergias());
        jTextArea11.setCaretPosition(0);
        jTextArea12.setText(infohistoriac.getIngresosPrevios());
        jTextArea12.setCaretPosition(0);
        jTextArea22.setText(infohistoriac.getTraumatismos());
        jTextArea22.setCaretPosition(0);
        jTextArea23.setText(infohistoriac.getTratamientos());
        jTextArea23.setCaretPosition(0);
        jTextArea24.setText(infohistoriac.getSituacionBasal());
        jTextArea24.setCaretPosition(0);
        jCheckBox1.setSelected(infohistoriac.getHta());
        jCheckBox2.setSelected(infohistoriac.getDm());
        jCheckBox3.setSelected(infohistoriac.getDislipidemia());
        jCheckBox7.setSelected(infohistoriac.getTabaco());
        jCheckBox8.setSelected(infohistoriac.getAlcohol());
        jCheckBox9.setSelected(infohistoriac.getDroga());
        jTextArea7.setText(infohistoriac.getOtrosHabitos());
        jTextArea7.setCaretPosition(0);
        jTextArea5.setText(infohistoriac.getDescHdd());
        jTextArea5.setCaretPosition(0);
        jTextArea13.setText(infohistoriac.getEnfermedadActual());
        jTextArea13.setCaretPosition(0);
        jTextArea25.setText(infohistoriac.getAntFamiliar());
        jTextArea25.setCaretPosition(0);
        if (staticcie == null) {
            factory = Persistence.createEntityManagerFactory("ClipaEJBPU", AtencionUrgencia.props);
            staticcie = new StaticCie10JpaController(factory);
        }
        if (infohistoriac.getDiagnostico() != 0 && infohistoriac.getDiagnostico() != 1) {
            staticCie10 = staticcie.findStaticCie10(infohistoriac.getDiagnostico());
            jTextField11.setText("[" + staticCie10.getCodigo() + "] " + staticCie10.getDescripcion());
            jTextField11.setCaretPosition(0);
            jTextArea12.setToolTipText(myStringsFunctions.stringToDIVstring(jTextArea12.getText()));
            idDiag1 = infohistoriac.getDiagnostico();
        }
        if (infohistoriac.getDiagnostico2() != 0 && infohistoriac.getDiagnostico2() != 1) {
            staticCie10 = staticcie.findStaticCie10(infohistoriac.getDiagnostico2());
            jTextField12.setText("[" + staticCie10.getCodigo() + "] " + staticCie10.getDescripcion());
            jTextField12.setCaretPosition(0);
            jTextArea12.setToolTipText(myStringsFunctions.stringToDIVstring(jTextArea12.getText()));
            idDiag2 = infohistoriac.getDiagnostico2();
        }
        if (infohistoriac.getDiagnostico3() != 0 && infohistoriac.getDiagnostico3() != 1) {
            staticCie10 = staticcie.findStaticCie10(infohistoriac.getDiagnostico3());
            jTextField13.setText("[" + staticCie10.getCodigo() + "] " + staticCie10.getDescripcion());
            jTextField13.setCaretPosition(0);
            jTextArea13.setToolTipText(myStringsFunctions.stringToDIVstring(jTextArea13.getText()));
            idDiag3 = infohistoriac.getDiagnostico3();
        }
        if (infohistoriac.getDiagnostico4() != 0 && infohistoriac.getDiagnostico4() != 1) {
            staticCie10 = staticcie.findStaticCie10(infohistoriac.getDiagnostico4());
            jTextField14.setText("[" + staticCie10.getCodigo() + "] " + staticCie10.getDescripcion());
            jTextField14.setCaretPosition(0);
            jTextArea14.setToolTipText(myStringsFunctions.stringToDIVstring(jTextArea14.getText()));
            idDiag4 = infohistoriac.getDiagnostico4();
        }
        if (infohistoriac.getDiagnostico5() != 0 && infohistoriac.getDiagnostico5() != 1) {
            staticCie10 = staticcie.findStaticCie10(infohistoriac.getDiagnostico5());
            jTextField15.setText("[" + staticCie10.getCodigo() + "] " + staticCie10.getDescripcion());
            jTextField15.setCaretPosition(0);
            jTextArea15.setToolTipText(myStringsFunctions.stringToDIVstring(jTextArea15.getText()));
            idDiag5 = infohistoriac.getDiagnostico5();
        }
        jTextArea19.setText(infohistoriac.getHallazgo());
        jpCentro.removeAll();
        cU = new datosHCU(infohistoriac);
        cU.setBounds(0, 0, 584, 445);
        jpCentro.add(cU);
        cU.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
        activeCheck(8);
    }

    private void setFisicExplorer() {
        infoexploracion = infohistoriac.getInfoHcExpfisica();
        jTextField1.setText(infoexploracion.getTa());
        jTextField1.setCaretPosition(0);
        jTextField8.setText(infoexploracion.getT());
        jTextField8.setCaretPosition(0);
        jTextField2.setText(infoexploracion.getTam());
        jTextField2.setCaretPosition(0);
        jTextField7.setText(infoexploracion.getSao2());
        jTextField7.setCaretPosition(0);
        jTextField3.setText(infoexploracion.getFc());
        jTextField3.setCaretPosition(0);
        jTextField6.setText(infoexploracion.getPvc());
        jTextField6.setCaretPosition(0);
        jTextField4.setText(infoexploracion.getFr());
        jTextField4.setCaretPosition(0);
        jTextField5.setText(infoexploracion.getPic());
        jTextField5.setCaretPosition(0);
        jTextField9.setText(infoexploracion.getPeso());
        jTextField9.setCaretPosition(0);
        jTextField10.setText(infoexploracion.getTalla());
        jTextField10.setCaretPosition(0);
        jTextArea2.setText(infoexploracion.getOtros());
        jTextArea2.setCaretPosition(0);
        jTextArea3.setText(infoexploracion.getAspectogeneral());
        jTextArea3.setCaretPosition(0);
        jTextArea4.setText(infoexploracion.getCara());
        jTextArea4.setCaretPosition(0);
        jTextArea6.setText(infoexploracion.getCardio());
        jTextArea6.setCaretPosition(0);
        jTextArea8.setText(infoexploracion.getRespiratorio());
        jTextArea8.setCaretPosition(0);
        jTextArea9.setText(infoexploracion.getGastro());
        jTextArea9.setCaretPosition(0);
        jTextArea14.setText(infoexploracion.getRenal());
        jTextArea14.setCaretPosition(0);
        jTextArea15.setText(infoexploracion.getHemato());
        jTextArea15.setCaretPosition(0);
        jTextArea16.setText(infoexploracion.getEndo());
        jTextArea16.setCaretPosition(0);
        jTextArea17.setText(infoexploracion.getOsteo());
        jTextArea17.setCaretPosition(0);
    }

    private void setHelpDiag() {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/Delete16x16.png"));
        ImageIcon icon2 = new ImageIcon(ClassLoader.getSystemResource("images/download.png"));
        File file = null;
        try {
            file = File.createTempFile("temporal", null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "10065:\n" + ex.getMessage(), Evo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
        listaPruebas = infohistoriac.getInfoPruebasComplements();
        for (int i = 0; i < listaPruebas.size(); i++) {
            modeloAyudDiag.addRow(dato);
            modeloAyudDiag.setValueAt(new JLabel(icon), i, 0);
            modeloAyudDiag.setValueAt(new JLabel(icon2), i, 1);
            modeloAyudDiag.setValueAt(listaPruebas.get(i).getNombre(), i, 2);
            modeloAyudDiag.setValueAt(file, i, 3);
            modeloAyudDiag.setValueAt(listaPruebas.get(i).getTipo(), i, 4);
            modeloAyudDiag.setValueAt(ReturnPathdiagHelpSaveFile(listaPruebas.get(i).getTipo()), i, 5);
            modeloAyudDiag.setValueAt("1", i, 6);
        }
        //descargar archivos
    }
    // </editor-fold>

    public void cerrarPanel() {
        AtencionUrgencia.panelindex.jpContainer.removeAll();
        AtencionUrgencia.panelindex.jpContainer.validate();
        AtencionUrgencia.panelindex.jpContainer.repaint();
    }

    /**
     * @param tipo
     * @return "numero documento/año/mes/id admision/tipo de archivo"
     */
    private String ReturnPathdiagHelpSaveFile(String tipo) {
        Calendar ahoraCal = Calendar.getInstance();
        ahoraCal.setTime(infoadmision.getFechaIngreso());
        String year = String.valueOf(ahoraCal.get(Calendar.YEAR));
        String month = String.valueOf(ahoraCal.get(Calendar.MONTH));
        return infoadmision.getIdDatosPersonales().getNumDoc() + s + year + s + month + s + infoadmision.getId() + s + tipo;
    }

    private Boolean getValidaFirma() {
        boolean f = false;
        try {
            String sFile = this.infohistoriac.getIdConfigdecripcionlogin().getRuta_firma();
            File sFile1 = null;
            if (sFile == null) {
                JOptionPane.showMessageDialog(this, "Errores leyendo archivo de firma del usuario.");
            } else {
                sFile1 = new File(sFile);
                if (sFile1.exists()) {
                    f = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Errores leyendo archivo de firma del usuario.");
                }
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "10116:\n" + e.getMessage(), Evo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
        return f;
    }

    // <editor-fold defaultstate="collapsed" desc="private static void addClosableTab(final JTabbedPane tabbedPane,final JComponent c,final String title,final Icon icon) {">
    private static final Icon CLOSE_TAB_ICON = new ImageIcon(ClassLoader.getSystemResource("images/closeTabButton.png"));
    private static final Icon PAGE_ICON = new ImageIcon(ClassLoader.getSystemResource("images/chart_curve_edit_16x16.png"));
    private static final Icon PAGE_SUBJETIVO = new ImageIcon(ClassLoader.getSystemResource("images/file_document_paper_orange.png"));
    private static final Icon PAGE_SIGNOS = new ImageIcon(ClassLoader.getSystemResource("images/file_document_paper_red_signes.png"));
    private static final Icon PAGE_OBJETIVOS = new ImageIcon(ClassLoader.getSystemResource("images/file_document_paper_blue.png"));
    private static final Icon PAGE_PLAN = new ImageIcon(ClassLoader.getSystemResource("images/file_document_paper_green.png"));
    private static final Icon PAGE_ANALISIS = new ImageIcon(ClassLoader.getSystemResource("images/file_document_paper_analisis.png"));

    /**
     * Creado por Tad Harrison Adds a component to a JTabbedPane with a little
     * "close tab" button on the right side of the tab.
     *
     * @param tabbedPane the JTabbedPane
     * @param c any JComponent
     * @param title the title for the tab
     * @param icon the icon for the tab, if desired
     */
    private static void addClosableTab(final JTabbedPane tabbedPane, final JComponent c, final String title, final Icon icon) {

        // Add the tab to the pane without any label
        tabbedPane.addTab(null, c);
        int pos = tabbedPane.indexOfComponent(c);

        // Create a FlowLayout that will space things 5px apart
        FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);

        // Make a small JPanel with the layout and make it non-opaque
        JPanel pnlTab = new JPanel(f);
        pnlTab.setOpaque(false);

        // Add a JLabel with title and the left-side tab icon
        JLabel lblTitle = new JLabel(title);
        lblTitle.setIcon(icon);

        // Create a JButton for the close tab button
        JButton btnClose = new JButton();
        btnClose.setOpaque(false);

        // Configure icon and rollover icon for button
        btnClose.setRolloverIcon(CLOSE_TAB_ICON);
        btnClose.setRolloverEnabled(true);
        btnClose.setIcon(CLOSE_TAB_ICON);

        // Set border null so the button doesn't make the tab too big
        btnClose.setBorder(null);

        // Make sure the button can't get focus, otherwise it looks funny
        btnClose.setFocusable(false);

        // Put the panel together
        pnlTab.add(lblTitle);
        pnlTab.add(btnClose);

        // Add a thin border to keep the image below the top edge of the tab
        // when the tab is selected
        pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

        // Now assign the component for the tab
        tabbedPane.setTabComponentAt(pos, pnlTab);

        // Add the listener that removes the tab
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // The component parameter must be declared "final" so that it can be
                // referenced in the anonymous listener class like this.
                tabbedPane.remove(c);
            }
        };
        btnClose.addActionListener(listener);

        // Optionally bring the new tab to the front
        tabbedPane.setSelectedComponent(c);

        //-------------------------------------------------------------
        // Bonus: Adding a <Ctrl-W> keystroke binding to close the tab
        //-------------------------------------------------------------
        AbstractAction closeTabAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.remove(c);
            }
        };

        // Create a keystroke
        KeyStroke controlW = KeyStroke.getKeyStroke("control W");

        // Get the appropriate input map using the JComponent constants.
        // This one works well when the component is a container. 
        InputMap inputMap = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Add the key binding for the keystroke to the action name
        inputMap.put(controlW, "closeTab");

        // Now add a single binding for the action name to the anonymous action
        c.getActionMap().put("closeTab", closeTabAction);
    }
    // </editor-fold>

    private void newEvolucion() {
        if (Finalizada() == false) {
            StaticCie10JpaController scjc = new StaticCie10JpaController(factory);
            HcuEvolucionJpaController hejc = new HcuEvolucionJpaController(factory);
            JDdateEvo evo = new JDdateEvo(null, true);
            evo.setLocationRelativeTo(null);
            evo.setVisible(true);
            if (evo.fecha_hora != null) {
                HcuEvolucion evolucion = new HcuEvolucion();
                evolucion.setIdInfoHistoriac(infohistoriac);
                evolucion.setFechaEvo(evo.fecha_hora);
                evolucion.setTipo(tipoEvo);
                evolucion.setIdStaticEspecialidades(staticEspecialidades);
                List<HcuEvolucion> hes = hejc.FindHcuEvolucions(infohistoriac);
                if (hes.isEmpty()) {
                    evolucion.setDx(scjc.findStaticCie10(infohistoriac.getDiagnostico()));
                    evolucion.setDx1(scjc.findStaticCie10(infohistoriac.getDiagnostico2()));
                    evolucion.setDx2(scjc.findStaticCie10(infohistoriac.getDiagnostico3()));
                    evolucion.setDx3(scjc.findStaticCie10(infohistoriac.getDiagnostico4()));
                    evolucion.setDx4(scjc.findStaticCie10(infohistoriac.getDiagnostico5()));
                } else {
                    evolucion.setDx(hes.get(hes.size() - 1).getDx());
                    evolucion.setDx1(hes.get(hes.size() - 1).getDx1());
                    evolucion.setDx2(hes.get(hes.size() - 1).getDx2());
                    evolucion.setDx3(hes.get(hes.size() - 1).getDx3());
                    evolucion.setDx4(hes.get(hes.size() - 1).getDx4());
                }
                evolucion.setEstado(0);
                evoSeleccion = evolucion;
                TreeNode raiz = (TreeNode) jTree1.getModel().getRoot();
                DefaultMutableTreeNode fechaEvo = null;
                DefaultMutableTreeNode Evo = null;
                boolean existeFechaEvo = false;
                boolean existeEvo = false;
                for (int i = 0; i < raiz.getChildCount(); i++) {
                    if (raiz.getChildAt(i).toString().equals(MyDate.ddMMyyyy.format(evolucion.getFechaEvo()))) {
                        existeFechaEvo = true;
                        for (int a = 0; a < raiz.getChildAt(i).getChildCount(); a++) {
                            if (raiz.getChildAt(i).getChildAt(a).toString().equals(evolucion.toString())) {
                                existeEvo = true;
                                break;
                            }
                        }
                        if (!existeEvo) {
                            fechaEvo = (DefaultMutableTreeNode) raiz.getChildAt(i);
                            Evo = new DefaultMutableTreeNode(evolucion);
                            fechaEvo.add(Evo);
                            modeloTree.nodeStructureChanged(raiz);
                            jTree1.setSelectionPath(new TreePath(Evo.getPath()));
                        }
                        break;
                    }
                }
                if (!existeFechaEvo) {
                    fechaEvo = new DefaultMutableTreeNode(MyDate.ddMMyyyy.format(evolucion.getFechaEvo()));
                    modeloTree.insertNodeInto(fechaEvo, EvosHC, 0);
                    Evo = new DefaultMutableTreeNode(evolucion);
                    fechaEvo.add(Evo);
                    jTree1.setSelectionPath(new TreePath(Evo.getPath()));
                } else {
                    if (!existeEvo) {
                        //                        modeloTree.insertNodeInto(fechaEvo, EvosHC, 0);
                        //                        Evo = new DefaultMutableTreeNode(evolucion);
                        //                        fechaEvo.add(Evo); 
                        //                        jTree1.setSelectionPath(new TreePath(Evo.getPath()));
                    }
                }
                this.activarComponentes(evolucion);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Historia Clinica Finalizada");
        }
    }

    private boolean Finalizada() {
        boolean finaliza = false;
        TreeNode raiz = (TreeNode) jTree1.getModel().getRoot();
        DefaultMutableTreeNode Evo = null;
        for (int i = 0; i < raiz.getChildCount(); i++) {
            for (int a = 0; a < raiz.getChildAt(i).getChildCount(); a++) {
                Evo = (DefaultMutableTreeNode) raiz.getChildAt(i).getChildAt(a);
                if (Evo.getUserObject() instanceof HcuEvolucion) {
                    HcuEvolucion evolu = (HcuEvolucion) Evo.getUserObject();
                    if (evolu.getEstado() == 3) {
                        finaliza = true;
                    }
                }
            }
        }
        return finaliza;
    }

    private void notaEgreso() {
        if (Finalizada() == false) {
            StaticCie10JpaController scjc = new StaticCie10JpaController(factory);
            HcuEvolucionJpaController hejc = new HcuEvolucionJpaController(factory);
            JDdateEvo evo = new JDdateEvo(null, true, "Fecha de Egreso");
            evo.setLocationRelativeTo(null);
            evo.setVisible(true);
            if (evo.fecha_hora != null) {
                HcuEvolucion evolucion = new HcuEvolucion();
                evolucion.setIdInfoHistoriac(infohistoriac);
                evolucion.setFechaEvo(evo.fecha_hora);
                evolucion.setTipo(tipoEvo);
                evolucion.setIdStaticEspecialidades(staticEspecialidades);
                List<HcuEvolucion> hes = hejc.FindHcuEvolucions(infohistoriac);
                evolucion.setEstado(3);
                jButton1.setEnabled(false);
                jButton14.setEnabled(false);
                if (hes.isEmpty()) {
                    evolucion.setDx(scjc.findStaticCie10(infohistoriac.getDiagnostico()));
                    evolucion.setDx1(scjc.findStaticCie10(infohistoriac.getDiagnostico2()));
                    evolucion.setDx2(scjc.findStaticCie10(infohistoriac.getDiagnostico3()));
                    evolucion.setDx3(scjc.findStaticCie10(infohistoriac.getDiagnostico4()));
                    evolucion.setDx4(scjc.findStaticCie10(infohistoriac.getDiagnostico5()));
                } else {
                    evolucion.setDx(hes.get(hes.size() - 1).getDx());
                    evolucion.setDx1(hes.get(hes.size() - 1).getDx1());
                    evolucion.setDx2(hes.get(hes.size() - 1).getDx2());
                    evolucion.setDx3(hes.get(hes.size() - 1).getDx3());
                    evolucion.setDx4(hes.get(hes.size() - 1).getDx4());
                }
                evoSeleccion = evolucion;
                TreeNode raiz = (TreeNode) jTree1.getModel().getRoot();
                DefaultMutableTreeNode fechaEvo = null;
                DefaultMutableTreeNode Evo = null;
                boolean existeFechaEvo = false;
                boolean existeEvo = false;
                for (int i = 0; i < raiz.getChildCount(); i++) {
                    if (raiz.getChildAt(i).toString().equals(MyDate.ddMMyyyy.format(evolucion.getFechaEvo()))) {
                        existeFechaEvo = true;
                        for (int a = 0; a < raiz.getChildAt(i).getChildCount(); a++) {
                            if (raiz.getChildAt(i).getChildAt(a).toString().equals(evolucion.toString())) {
                                existeEvo = true;
                                break;
                            }
                        }
                        if (!existeEvo) {
                            fechaEvo = (DefaultMutableTreeNode) raiz.getChildAt(i);
                            Evo = new DefaultMutableTreeNode(evolucion);
                            fechaEvo.add(Evo);
                            modeloTree.nodeStructureChanged(raiz);
                            jTree1.setSelectionPath(new TreePath(Evo.getPath()));
                        }
                        break;
                    }
                }
                if (!existeFechaEvo) {
                    fechaEvo = new DefaultMutableTreeNode(MyDate.ddMMyyyy.format(evolucion.getFechaEvo()));
                    modeloTree.insertNodeInto(fechaEvo, EvosHC, 0);
                    Evo = new DefaultMutableTreeNode(evolucion);
                    fechaEvo.add(Evo);
                    jTree1.setSelectionPath(new TreePath(Evo.getPath()));
                }
                this.activarComponentesEgreso(evolucion);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Historia Clinica Finalizada");
        }
    }

    private void activarComponentes(HcuEvolucion he) {
        evol = new newEvo();
        subjetivo = new pSubjetivo();
        objetivo = new pObjetivo();
        analisis = new pAnalisis();
        pplan = new pPlan(he, factory);
        jTabbedPane6.removeAll();
        subjetivo.setBounds(0, 0, 386, 603);
        addClosableTab(jTabbedPane6, subjetivo, "Nota Subjetiva", PAGE_SUBJETIVO);
        subjetivo.setEvolucion(he);
        evol.setBounds(0, 0, 386, 603);
        addClosableTab(jTabbedPane6, evol, "Signos Vitales", PAGE_SIGNOS);
        evol.setEvolucion(he);
        objetivo.setBounds(0, 0, 386, 603);
        addClosableTab(jTabbedPane6, objetivo, "Nota Objetiva", PAGE_OBJETIVOS);
        objetivo.setEvolucion(he);
        analisis.setBounds(0, 0, 386, 603);
        addClosableTab(jTabbedPane6, analisis, "Analisis", PAGE_ANALISIS);
        analisis.setEvolucion(he);
        pplan.setBounds(0, 0, 386, 603);
        addClosableTab(jTabbedPane6, pplan, "Plan", PAGE_PLAN);
        jLabel65.setText(MyDate.yyyyMMddHHmm2.format(he.getFechaEvo()));
        jTabbedPane6.setSelectedIndex(0);
        jButton8.setVisible(true);
        jButton8.setEnabled(true);
        jButton10.setEnabled(true);
        jButton10.setText("S. Vitales");
        jButton10.setToolTipText("SIGNOS VITALES");
        jButton2.setEnabled(true);
        jButton2.setText("Objetivo");
        jButton2.setToolTipText("NOTAS OBJETIVAS");
        jButton6.setEnabled(true);
        jButton6.setText("Analisis");
        jButton6.setToolTipText("NOTAS DE ANALISIS");
        jButton4.setEnabled(true);
        jButton4.setText("Plan");
        jButton4.setToolTipText("PLAN DE MANEJO");
        jButton12.setEnabled(true);
        jButton13.setEnabled(true);
        jButton5.setEnabled(true);
        jButton14.setEnabled(true);
    }

    private void activarComponentesEgreso(HcuEvolucion he) {
        evol = new newEvo();
        evol.titleOther = "OBSERVACIONES ADICIONALES";
//            subjetivo = new pSubjetivo();
        objetivo = new pObjetivo();
        objetivo.jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SÍNTESIS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 1, 11)));
        analisis = new pAnalisis();
        analisis.jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CONSIDERACIONES ADICIONALES", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 1, 11)));
        pplan = new pPlan(he, factory);
        pplan.jXTaskPane3.setVisible(false);
        pplan.jXTaskPane5.setVisible(true);
        pplan.jXTaskPane4.setVisible(false);
        jTabbedPane6.removeAll();
//            subjetivo.setBounds(0, 0, 386, 603);
//            addClosableTab(jTabbedPane6, subjetivo, "Nota Subjetiva", PAGE_SUBJETIVO);
//            subjetivo.setEvolucion(he);
        evol.setBounds(0, 0, 386, 603);
        addClosableTab(jTabbedPane6, evol, "Estado General", PAGE_SIGNOS);
        evol.setEvolucion(he);
        objetivo.setBounds(0, 0, 386, 603);
        addClosableTab(jTabbedPane6, objetivo, "Síntesis", PAGE_OBJETIVOS);
        objetivo.setEvolucion(he);
        analisis.setBounds(0, 0, 386, 603);
        addClosableTab(jTabbedPane6, analisis, "DX Egreso", PAGE_ANALISIS);
        analisis.setEvolucion(he);
        pplan.setBounds(0, 0, 386, 603);
        addClosableTab(jTabbedPane6, pplan, "Conducta", PAGE_PLAN);
        jLabel65.setText(MyDate.yyyyMMddHHmm2.format(he.getFechaEvo()));
        jTabbedPane6.setSelectedIndex(0);
        jButton8.setVisible(false);
        jButton10.setEnabled(true);
        jButton10.setText("Estado General");
        jButton10.setToolTipText("ESTADO GEENRAL DEL PACIENTE");
        jButton2.setEnabled(true);
        jButton2.setText("Síntesis");
        jButton2.setToolTipText("RESUMEN DE NOTAS DE EVOLUCIÓN");
        jButton6.setEnabled(true);
        jButton6.setText("DX Egreso");
        jButton6.setToolTipText("DIAGNOSTICOS DE EGRESO");
        jButton4.setEnabled(true);
        jButton4.setText("Conducta");
        jButton4.setToolTipText("CONDUCTA Y DESTINO");
        jButton12.setEnabled(true);
        jButton13.setEnabled(true);
        jButton5.setEnabled(true);
    }

    private void saveEvolucion() {
        if (evoSeleccion.getEstado() == 0) {
            evoSeleccion.setEstado(1);
        }
        boolean sigue = evol.saveChanged(factory, evoSeleccion);
        if (evoSeleccion != null && evoSeleccion.getEstado() != 2 && sigue) {
            if (evoSeleccion.getEstado() != 3) {
                subjetivo.saveChanged(factory, evoSeleccion);
            }
            objetivo.saveChanged(factory, evoSeleccion);
            analisis.saveChanged(factory, evoSeleccion);
            evoSeleccion = pplan.saveChanged(factory, evoSeleccion);
            jTree1.repaint();
            jTree1.validate();
//                setJTreeEvo();
        }
    }

    private boolean getCamposObligatoriosVacios() {
        boolean retorno = false;
        List<String> mensaje = new ArrayList<String>();
        String mensajeT = "";
        if (getValidPanels(subjetivo) == false && evoSeleccion.getEstado() != 3) {
            mensaje.add("*Nota Subjetiva*");
            retorno = true;
        }
        if (getValidPanels(evol) == false) {
            if (evol != null) {
                for (String texto : evol.estadoTablasVal()) {
                    mensaje.add(texto);
                }
            }
            retorno = true;
        }
        if (getValidPanels(objetivo) == false) {
            mensaje.add("*Nota Objetiva*");
            retorno = true;
        }
        if (getValidPanels(analisis) == false) {
            if (analisis != null) {
                for (String texto : analisis.estadoTablasVal()) {
                    mensaje.add(texto);
                }
            }
            retorno = true;
        }
        if (getValidPanels(pplan) == false) {
            mensaje.add("*Tratamientos e Indicaciones*");
            retorno = true;
        }

        for (String men : mensaje) {
            mensajeT += men + "\n";
        }

        if (!mensajeT.equals("")) {
            JOptionPane.showMessageDialog(null, "Los siguientes campos no han sido diligenciados: \n" + mensajeT, "Campos Obligatorios", JOptionPane.DEFAULT_OPTION);
        }
        return retorno;
    }

    private boolean getValidPanels(JPanel jp) {
        if (jp != null) {
            if (jp instanceof pSubjetivo) {
                if (((pSubjetivo) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof newEvo) {
                if (((newEvo) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof pObjetivo) {
                if (((pObjetivo) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof pAnalisis) {
                if (((pAnalisis) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof pPlan) {
                if (((pPlan) jp).estadoTablas() == true) {
                    return true;
                }
            }

        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpMotivoC = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea10 = new javax.swing.JTextArea();
        jLabel37 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jpAntPersonales = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea11 = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea12 = new javax.swing.JTextArea();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTextArea22 = new javax.swing.JTextArea();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTextArea23 = new javax.swing.JTextArea();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jLabel39 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        jTextArea24 = new javax.swing.JTextArea();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea25 = new javax.swing.JTextArea();
        jpEnfActual = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea13 = new javax.swing.JTextArea();
        jLabel40 = new javax.swing.JLabel();
        jpExpFisica = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextArea8 = new javax.swing.JTextArea();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextArea9 = new javax.swing.JTextArea();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTextArea14 = new javax.swing.JTextArea();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTextArea15 = new javax.swing.JTextArea();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTextArea16 = new javax.swing.JTextArea();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTextArea17 = new javax.swing.JTextArea();
        jLabel41 = new javax.swing.JLabel();
        jpPruebasDiag = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTextArea19 = new javax.swing.JTextArea();
        jScrollPane27 = new javax.swing.JScrollPane();
        jtbTratamiento4 = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jpDiagMedico = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jpTratamiento = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jLabel46 = new javax.swing.JLabel();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jLabel36 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jXTaskPane3 = new org.jdesktop.swingx.JXTaskPane();
        jLabel48 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jXTaskPane4 = new org.jdesktop.swingx.JXTaskPane();
        jLabel47 = new javax.swing.JLabel();
        jXTaskPane5 = new org.jdesktop.swingx.JXTaskPane();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jlbNombrePaciente = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel30 = new javax.swing.JPanel();
        jpCentro = new javax.swing.JPanel();
        jCheckBox6 = new javax.swing.JCheckBox();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox13 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jLabel33 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jCheckBox14 = new javax.swing.JCheckBox();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton7 = new javax.swing.JButton();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jLabel57 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();

        jpMotivoC.setMaximumSize(new java.awt.Dimension(584, 445));
        jpMotivoC.setMinimumSize(new java.awt.Dimension(584, 445));
        jpMotivoC.setPreferredSize(new java.awt.Dimension(584, 445));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nivel de Triaje", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel26.setOpaque(false);

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton4.setText("CUATRO");
        jRadioButton4.setDoubleBuffered(true);
        jRadioButton4.setEnabled(false);
        jRadioButton4.setHideActionText(true);
        jRadioButton4.setOpaque(false);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton3.setSelected(true);
        jRadioButton3.setText("TRES");
        jRadioButton3.setEnabled(false);
        jRadioButton3.setHideActionText(true);
        jRadioButton3.setOpaque(false);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton2.setText("DOS");
        jRadioButton2.setEnabled(false);
        jRadioButton2.setOpaque(false);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton1.setText("UNO");
        jRadioButton1.setEnabled(false);
        jRadioButton1.setOpaque(false);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addGap(28, 28, 28)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton3)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton4)
                .addGap(23, 23, 23))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea10.setEditable(false);
        jTextArea10.setColumns(20);
        jTextArea10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea10.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea10.setLineWrap(true);
        jTextArea10.setRows(5);
        jTextArea10.setDragEnabled(true);
        jTextArea10.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea10.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea10KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea10KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea10);

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setText("MOTIVO DE CONSULTA");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Origen de la Enfermedad o Accidente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel5.setOpaque(false);

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PACIENTE SANO", "ENFERMEDAD GENERAL O COMÚN", "ENFERMEDAD PROFESIONAL U OCUPACIONAL", "ACCIDENTE DE TRABAJO", "ACCIDENTE NO DE TRABAJO O FUERA DEL TRABAJO" }));
        jComboBox1.setSelectedIndex(-1);
        jComboBox1.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, 0, 294, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 238, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpMotivoCLayout = new javax.swing.GroupLayout(jpMotivoC);
        jpMotivoC.setLayout(jpMotivoCLayout);
        jpMotivoCLayout.setHorizontalGroup(
            jpMotivoCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpMotivoCLayout.setVerticalGroup(
            jpMotivoCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpAntPersonales.setMaximumSize(new java.awt.Dimension(584, 445));
        jpAntPersonales.setMinimumSize(new java.awt.Dimension(584, 445));
        jpAntPersonales.setPreferredSize(new java.awt.Dimension(584, 445));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(584, 445));
        jPanel3.setMinimumSize(new java.awt.Dimension(584, 445));
        jPanel3.setPreferredSize(new java.awt.Dimension(584, 445));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel38.setText("ANTECEDENTES PERSONALES");

        jTabbedPane4.setMaximumSize(new java.awt.Dimension(200, 445));
        jTabbedPane4.setMinimumSize(new java.awt.Dimension(200, 445));
        jTabbedPane4.setPreferredSize(new java.awt.Dimension(200, 445));

        jPanel8.setOpaque(false);

        jScrollPane2.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea11.setEditable(false);
        jTextArea11.setColumns(20);
        jTextArea11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea11.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea11.setLineWrap(true);
        jTextArea11.setRows(2);
        jTextArea11.setText("NINGUNO");
        jTextArea11.setMaximumSize(new java.awt.Dimension(578, 158));
        jTextArea11.setMinimumSize(new java.awt.Dimension(578, 158));
        jTextArea11.setPreferredSize(new java.awt.Dimension(578, 158));
        jTextArea11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea11FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea11FocusLost(evt);
            }
        });
        jTextArea11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea11KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea11);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("Alergias a Medicamentos", jPanel8);

        jPanel9.setOpaque(false);

        jScrollPane8.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane8.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane8.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea12.setEditable(false);
        jTextArea12.setColumns(20);
        jTextArea12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea12.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea12.setLineWrap(true);
        jTextArea12.setRows(2);
        jTextArea12.setText("NINGUNO");
        jTextArea12.setMaximumSize(new java.awt.Dimension(578, 158));
        jTextArea12.setMinimumSize(new java.awt.Dimension(578, 158));
        jTextArea12.setPreferredSize(new java.awt.Dimension(578, 158));
        jTextArea12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea12FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea12FocusLost(evt);
            }
        });
        jTextArea12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea12KeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(jTextArea12);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("Ingresos previos y cirugías", jPanel9);

        jPanel19.setOpaque(false);

        jScrollPane21.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane21.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane21.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea22.setEditable(false);
        jTextArea22.setColumns(20);
        jTextArea22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea22.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea22.setLineWrap(true);
        jTextArea22.setRows(2);
        jTextArea22.setText("NINGUNO");
        jTextArea22.setMaximumSize(new java.awt.Dimension(578, 158));
        jTextArea22.setMinimumSize(new java.awt.Dimension(578, 158));
        jTextArea22.setPreferredSize(new java.awt.Dimension(578, 158));
        jTextArea22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea22FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea22FocusLost(evt);
            }
        });
        jTextArea22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea22KeyPressed(evt);
            }
        });
        jScrollPane21.setViewportView(jTextArea22);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("Traumatismos, accidentes", jPanel19);

        jPanel20.setOpaque(false);

        jScrollPane22.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane22.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane22.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea23.setEditable(false);
        jTextArea23.setColumns(20);
        jTextArea23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea23.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea23.setLineWrap(true);
        jTextArea23.setRows(2);
        jTextArea23.setText("NINGUNO");
        jTextArea23.setMaximumSize(new java.awt.Dimension(578, 158));
        jTextArea23.setMinimumSize(new java.awt.Dimension(578, 158));
        jTextArea23.setPreferredSize(new java.awt.Dimension(578, 158));
        jTextArea23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea23FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea23FocusLost(evt);
            }
        });
        jTextArea23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea23KeyPressed(evt);
            }
        });
        jScrollPane22.setViewportView(jTextArea23);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("Tratamientos habituales", jPanel20);

        jPanel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jPanel4.setOpaque(false);

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox1.setText("HTA");
        jCheckBox1.setEnabled(false);
        jCheckBox1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox1.setOpaque(false);

        jCheckBox2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox2.setText("DM");
        jCheckBox2.setEnabled(false);
        jCheckBox2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox2.setOpaque(false);

        jCheckBox3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox3.setText("DISLIPIDEMIA");
        jCheckBox3.setEnabled(false);
        jCheckBox3.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox3.setOpaque(false);

        jTextArea5.setEditable(false);
        jTextArea5.setColumns(20);
        jTextArea5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea5.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea5.setLineWrap(true);
        jTextArea5.setRows(2);
        jTextArea5.setDoubleBuffered(true);
        jTextArea5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea5KeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(jTextArea5);

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel39.setText("Observacion:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox2)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox3)
                .addContainerGap(344, Short.MAX_VALUE))
            .addComponent(jScrollPane7)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel39)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3))
                .addGap(4, 4, 4)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("HTA, DM, DISLIPIDEMIA", jPanel4);

        jPanel6.setFocusable(false);
        jPanel6.setOpaque(false);

        jCheckBox7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox7.setText("Tabaco");
        jCheckBox7.setEnabled(false);
        jCheckBox7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox7.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox7.setOpaque(false);

        jCheckBox8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox8.setText("Alcohol");
        jCheckBox8.setEnabled(false);
        jCheckBox8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox8.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox8.setOpaque(false);

        jCheckBox9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox9.setText("Drogas de Abuso");
        jCheckBox9.setEnabled(false);
        jCheckBox9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox9.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox9.setOpaque(false);

        jTextArea7.setEditable(false);
        jTextArea7.setColumns(20);
        jTextArea7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea7.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea7.setLineWrap(true);
        jTextArea7.setRows(1);
        jTextArea7.setDoubleBuffered(true);
        jTextArea7.setMaximumSize(new java.awt.Dimension(104, 30));
        jTextArea7.setMinimumSize(new java.awt.Dimension(104, 30));
        jTextArea7.setPreferredSize(new java.awt.Dimension(164, 30));
        jTextArea7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea7KeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(jTextArea7);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel9.setText("Otros:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBox7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox9)))
                .addContainerGap(328, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox7)
                    .addComponent(jCheckBox8)
                    .addComponent(jCheckBox9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Hábitos Tóxicos", jPanel6);

        jPanel21.setOpaque(false);

        jScrollPane23.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane23.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane23.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea24.setEditable(false);
        jTextArea24.setColumns(20);
        jTextArea24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea24.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea24.setLineWrap(true);
        jTextArea24.setRows(2);
        jTextArea24.setText("NINGUNO");
        jTextArea24.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea24.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea24FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea24FocusLost(evt);
            }
        });
        jTextArea24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea24KeyPressed(evt);
            }
        });
        jScrollPane23.setViewportView(jTextArea24);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        );

        jTabbedPane5.addTab("Situación basal (crónicos)", jPanel21);

        jPanel31.setOpaque(false);

        jScrollPane25.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane25.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane25.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea25.setEditable(false);
        jTextArea25.setColumns(20);
        jTextArea25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea25.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea25.setLineWrap(true);
        jTextArea25.setRows(2);
        jTextArea25.setText("NINGUNO");
        jTextArea25.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea25.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea25.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea25FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea25FocusLost(evt);
            }
        });
        jTextArea25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea25KeyPressed(evt);
            }
        });
        jScrollPane25.setViewportView(jTextArea25);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        );

        jTabbedPane5.addTab("Antecedentes Familiares de Interes", jPanel31);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane5)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane5)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpAntPersonalesLayout = new javax.swing.GroupLayout(jpAntPersonales);
        jpAntPersonales.setLayout(jpAntPersonalesLayout);
        jpAntPersonalesLayout.setHorizontalGroup(
            jpAntPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpAntPersonalesLayout.setVerticalGroup(
            jpAntPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpEnfActual.setMaximumSize(new java.awt.Dimension(584, 445));
        jpEnfActual.setMinimumSize(new java.awt.Dimension(584, 445));
        jpEnfActual.setPreferredSize(new java.awt.Dimension(584, 445));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setMaximumSize(new java.awt.Dimension(584, 445));
        jPanel10.setMinimumSize(new java.awt.Dimension(584, 445));
        jPanel10.setPreferredSize(new java.awt.Dimension(584, 445));

        jScrollPane4.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane4.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea13.setEditable(false);
        jTextArea13.setColumns(20);
        jTextArea13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea13.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea13.setLineWrap(true);
        jTextArea13.setRows(5);
        jTextArea13.setMaximumSize(new java.awt.Dimension(164, 60));
        jTextArea13.setMinimumSize(new java.awt.Dimension(164, 60));
        jScrollPane4.setViewportView(jTextArea13);

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setText("ENFERMEDAD ACTUAL");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpEnfActualLayout = new javax.swing.GroupLayout(jpEnfActual);
        jpEnfActual.setLayout(jpEnfActualLayout);
        jpEnfActualLayout.setHorizontalGroup(
            jpEnfActualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpEnfActualLayout.setVerticalGroup(
            jpEnfActualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
        );

        jpExpFisica.setMaximumSize(new java.awt.Dimension(584, 445));
        jpExpFisica.setMinimumSize(new java.awt.Dimension(584, 445));
        jpExpFisica.setPreferredSize(new java.awt.Dimension(584, 445));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setMaximumSize(new java.awt.Dimension(584, 445));
        jPanel11.setMinimumSize(new java.awt.Dimension(584, 445));
        jPanel11.setPreferredSize(new java.awt.Dimension(584, 445));

        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jPanel12.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("TA");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 102, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel2.setText("TAM");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setText("FC");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel4.setText("FR");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(0, 102, 255));

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(0, 102, 255));

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(0, 102, 255));

        jTextField5.setEditable(false);
        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(0, 102, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel5.setText("PIC");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel6.setText("PVC");

        jTextField6.setEditable(false);
        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(0, 102, 255));

        jTextField7.setEditable(false);
        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(0, 102, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("SaO2");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel8.setText("T");

        jTextField8.setEditable(false);
        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(0, 102, 255));

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "OTROS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel25.setOpaque(false);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(3);
        jTextArea2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea2KeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel10.setText("mmHg");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel11.setText("mmHg");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel12.setText("x'");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel13.setText("x'");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel14.setText("°C");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel15.setText("%");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel16.setText("Cm");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel17.setText("mmHg");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel18.setText("PESO");

        jTextField9.setEditable(false);
        jTextField9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(0, 102, 255));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel19.setText("Kg");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel20.setText("TALLA");

        jTextField10.setEditable(false);
        jTextField10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(0, 102, 255));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel21.setText("m");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField9)
                    .addComponent(jTextField4)
                    .addComponent(jTextField3)
                    .addComponent(jTextField2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jTextField6)
                    .addComponent(jTextField7)
                    .addComponent(jTextField8)
                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel16))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel17))
                                .addGap(17, 17, 17)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20)
                                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21)))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel13))))
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("CONSTANTES", jPanel12);

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea3.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea3KeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(jTextArea3);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("ASPECTO GENERAL", jPanel14);

        jTextArea4.setEditable(false);
        jTextArea4.setColumns(20);
        jTextArea4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea4.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea4.setLineWrap(true);
        jTextArea4.setRows(5);
        jTextArea4.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea4FocusLost(evt);
            }
        });
        jTextArea4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea4KeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(jTextArea4);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("NEUROLOGICO ORL Y CARA", jPanel15);

        jTextArea6.setEditable(false);
        jTextArea6.setColumns(20);
        jTextArea6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea6.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea6.setLineWrap(true);
        jTextArea6.setRows(5);
        jTextArea6.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea6FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea6FocusLost(evt);
            }
        });
        jTextArea6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea6KeyPressed(evt);
            }
        });
        jScrollPane11.setViewportView(jTextArea6);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("CARDIOVASCULAR", jPanel16);

        jTextArea8.setEditable(false);
        jTextArea8.setColumns(20);
        jTextArea8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea8.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea8.setLineWrap(true);
        jTextArea8.setRows(5);
        jTextArea8.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea8FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea8FocusLost(evt);
            }
        });
        jTextArea8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea8KeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(jTextArea8);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("RESPIRATORIO", jPanel13);

        jTabbedPane2.setFocusable(false);
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jTextArea9.setEditable(false);
        jTextArea9.setColumns(20);
        jTextArea9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea9.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea9.setLineWrap(true);
        jTextArea9.setRows(5);
        jTextArea9.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea9FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea9FocusLost(evt);
            }
        });
        jTextArea9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea9KeyPressed(evt);
            }
        });
        jScrollPane13.setViewportView(jTextArea9);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("GASTROINTESTINAL", jPanel17);

        jTextArea14.setEditable(false);
        jTextArea14.setColumns(20);
        jTextArea14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea14.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea14.setLineWrap(true);
        jTextArea14.setRows(5);
        jTextArea14.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea14FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea14FocusLost(evt);
            }
        });
        jTextArea14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea14KeyPressed(evt);
            }
        });
        jScrollPane14.setViewportView(jTextArea14);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("GENITOURINARIO", jPanel18);

        jTextArea15.setColumns(20);
        jTextArea15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea15.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea15.setLineWrap(true);
        jTextArea15.setRows(5);
        jTextArea15.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea15FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea15FocusLost(evt);
            }
        });
        jTextArea15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea15KeyPressed(evt);
            }
        });
        jScrollPane15.setViewportView(jTextArea15);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("HEMATOINFECCIOSO", jPanel22);

        jTextArea16.setEditable(false);
        jTextArea16.setColumns(20);
        jTextArea16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea16.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea16.setLineWrap(true);
        jTextArea16.setRows(5);
        jTextArea16.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea16FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea16FocusLost(evt);
            }
        });
        jTextArea16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea16KeyPressed(evt);
            }
        });
        jScrollPane16.setViewportView(jTextArea16);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("ENDOCRINO - METABOLICO", jPanel23);

        jTextArea17.setEditable(false);
        jTextArea17.setColumns(20);
        jTextArea17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea17.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea17.setLineWrap(true);
        jTextArea17.setRows(5);
        jTextArea17.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea17FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea17FocusLost(evt);
            }
        });
        jTextArea17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea17KeyPressed(evt);
            }
        });
        jScrollPane17.setViewportView(jTextArea17);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("OSTEOMUSCULAR Y PIEL", jPanel24);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setText("EXPLORACION FISICA");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpExpFisicaLayout = new javax.swing.GroupLayout(jpExpFisica);
        jpExpFisica.setLayout(jpExpFisicaLayout);
        jpExpFisicaLayout.setHorizontalGroup(
            jpExpFisicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpExpFisicaLayout.setVerticalGroup(
            jpExpFisicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpPruebasDiag.setMaximumSize(new java.awt.Dimension(584, 445));
        jpPruebasDiag.setMinimumSize(new java.awt.Dimension(584, 445));
        jpPruebasDiag.setPreferredSize(new java.awt.Dimension(584, 445));

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setMaximumSize(new java.awt.Dimension(584, 445));
        jPanel27.setMinimumSize(new java.awt.Dimension(584, 445));
        jPanel27.setPreferredSize(new java.awt.Dimension(584, 445));

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HALLAZGOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel32.setOpaque(false);

        jTextArea19.setEditable(false);
        jTextArea19.setColumns(20);
        jTextArea19.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea19.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea19.setLineWrap(true);
        jTextArea19.setRows(5);
        jScrollPane26.setViewportView(jTextArea19);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
        );

        jScrollPane27.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane27.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane27.setOpaque(false);
        jScrollPane27.setPreferredSize(new java.awt.Dimension(200, 200));

        jtbTratamiento4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jtbTratamiento4.setForeground(new java.awt.Color(0, 51, 255));
        jtbTratamiento4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtbTratamiento4.setOpaque(false);
        jtbTratamiento4.setShowHorizontalLines(false);
        jtbTratamiento4.setShowVerticalLines(false);
        jtbTratamiento4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbTratamiento4MouseClicked(evt);
            }
        });
        jScrollPane27.setViewportView(jtbTratamiento4);

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel34.setText("Adjuntar Documentos");

        jTextField16.setEditable(false);
        jTextField16.setBackground(new java.awt.Color(255, 255, 255));
        jTextField16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(0, 102, 255));
        jTextField16.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel42.setText("PRUEBAS COMPLEMENTARIAS");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addGap(0, 22, Short.MAX_VALUE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpPruebasDiagLayout = new javax.swing.GroupLayout(jpPruebasDiag);
        jpPruebasDiag.setLayout(jpPruebasDiagLayout);
        jpPruebasDiagLayout.setHorizontalGroup(
            jpPruebasDiagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpPruebasDiagLayout.setVerticalGroup(
            jpPruebasDiagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpDiagMedico.setMaximumSize(new java.awt.Dimension(584, 445));
        jpDiagMedico.setMinimumSize(new java.awt.Dimension(584, 445));
        jpDiagMedico.setPreferredSize(new java.awt.Dimension(584, 445));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel28.setMaximumSize(new java.awt.Dimension(584, 445));
        jPanel28.setMinimumSize(new java.awt.Dimension(584, 445));
        jPanel28.setPreferredSize(new java.awt.Dimension(584, 445));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel28.setText("DIAGNOSTICO PRINCIPAL");

        jTextField11.setEditable(false);
        jTextField11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField11.setForeground(new java.awt.Color(0, 102, 255));
        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField11KeyReleased(evt);
            }
        });

        jTextField12.setEditable(false);
        jTextField12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(0, 102, 255));
        jTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField12KeyReleased(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel29.setText("DIAGNOSTICO RELACIONADO 1");

        jTextField13.setEditable(false);
        jTextField13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField13.setForeground(new java.awt.Color(0, 102, 255));
        jTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField13KeyReleased(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel30.setText("DIAGNOSTICO RELACIONADO 2");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel31.setText("DIAGNOSTICO RELACIONADO 3");

        jTextField14.setEditable(false);
        jTextField14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(0, 102, 255));
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField14KeyReleased(evt);
            }
        });

        jTextField15.setEditable(false);
        jTextField15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(0, 102, 255));
        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField15KeyReleased(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel32.setText("DIAGNOSTICO RELACIONADO 4");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel43.setText("DIAGNOSTICO MEDICO");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField15)
                    .addComponent(jTextField14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addComponent(jTextField12)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28)))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpDiagMedicoLayout = new javax.swing.GroupLayout(jpDiagMedico);
        jpDiagMedico.setLayout(jpDiagMedicoLayout);
        jpDiagMedicoLayout.setHorizontalGroup(
            jpDiagMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpDiagMedicoLayout.setVerticalGroup(
            jpDiagMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jpTratamiento.setBackground(new java.awt.Color(255, 255, 255));
        jpTratamiento.setMaximumSize(new java.awt.Dimension(584, 445));
        jpTratamiento.setMinimumSize(new java.awt.Dimension(584, 445));
        jpTratamiento.setPreferredSize(new java.awt.Dimension(584, 445));

        jPanel1.setMaximumSize(new java.awt.Dimension(584, 472));
        jPanel1.setMinimumSize(new java.awt.Dimension(584, 472));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(584, 472));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setText("TRTAMIENTO E INDICACIONES");

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setFocusable(false);
        jPanel35.setMaximumSize(new java.awt.Dimension(380, 420));
        jPanel35.setMinimumSize(new java.awt.Dimension(380, 420));
        jPanel35.setOpaque(false);
        jPanel35.setPreferredSize(new java.awt.Dimension(380, 420));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setOpaque(false);

        jXTaskPane1.setTitle("MEDICAMENTOS");
        jXTaskPane1.setAnimated(false);
        jXTaskPane1.setFocusable(false);
        jXTaskPane1.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jXTaskPane1.setOpaque(true);
        jXTaskPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXTaskPane1MouseReleased(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Pills.png"))); // NOI18N
        jLabel46.setText("Medicamentos");
        jLabel46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel46MouseExited(evt);
            }
        });
        jLabel46.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel46MouseMoved(evt);
            }
        });
        jXTaskPane1.getContentPane().add(jLabel46);

        jXTaskPane2.setExpanded(false);
        jXTaskPane2.setTitle("PROCEDIMIENTOS");
        jXTaskPane2.setAnimated(false);
        jXTaskPane2.setFocusable(false);
        jXTaskPane2.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jXTaskPane2.setOpaque(true);
        jXTaskPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXTaskPane2MouseReleased(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bag_icon.png"))); // NOI18N
        jLabel36.setText("<html><p> Monitorización y Tratamientos Diagnósticos</p></html>");
        jLabel36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel36.setDoubleBuffered(true);
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel36MouseExited(evt);
            }
        });
        jLabel36.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel36MouseMoved(evt);
            }
        });
        jXTaskPane2.getContentPane().add(jLabel36);

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/microscope_icon.png"))); // NOI18N
        jLabel45.setText("<html><p>Laboratorio Clinico</p></html>");
        jLabel45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel45.setDoubleBuffered(true);
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel45MouseExited(evt);
            }
        });
        jLabel45.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel45MouseMoved(evt);
            }
        });
        jXTaskPane2.getContentPane().add(jLabel45);

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/XRay.png"))); // NOI18N
        jLabel49.setText("Imagenologia");
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel49.setDoubleBuffered(true);
        jLabel49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel49MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel49MouseExited(evt);
            }
        });
        jLabel49.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel49MouseMoved(evt);
            }
        });
        jXTaskPane2.getContentPane().add(jLabel49);

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Skalpell.png"))); // NOI18N
        jLabel50.setText("Quirúrgicos");
        jLabel50.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel50.setDoubleBuffered(true);
        jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel50MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel50MouseExited(evt);
            }
        });
        jLabel50.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel50MouseMoved(evt);
            }
        });
        jXTaskPane2.getContentPane().add(jLabel50);

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Hospital.png"))); // NOI18N
        jLabel51.setText("Mas Procedimientos");
        jLabel51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel51.setDoubleBuffered(true);
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel51MouseExited(evt);
            }
        });
        jLabel51.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel51MouseMoved(evt);
            }
        });
        jXTaskPane2.getContentPane().add(jLabel51);

        jXTaskPane3.setExpanded(false);
        jXTaskPane3.setTitle("VALORACION ESPECIALISTA");
        jXTaskPane3.setAnimated(false);
        jXTaskPane3.setFocusable(false);
        jXTaskPane3.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jXTaskPane3.setOpaque(true);
        jXTaskPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXTaskPane3MouseReleased(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/surgeon_icon.png"))); // NOI18N
        jLabel48.setText("Cirugia General");
        jLabel48.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel48MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel48MouseExited(evt);
            }
        });
        jLabel48.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel48MouseMoved(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel48);

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Female_icon.png"))); // NOI18N
        jLabel52.setText("Ginecología");
        jLabel52.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel52MouseExited(evt);
            }
        });
        jLabel52.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel52MouseMoved(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel52);

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Head_physician_icon.png"))); // NOI18N
        jLabel53.setText("Medicina Interna");
        jLabel53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel53MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel53MouseExited(evt);
            }
        });
        jLabel53.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel53MouseMoved(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel53);

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plaster_icon.png"))); // NOI18N
        jLabel54.setText("Ortopedia");
        jLabel54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel54MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel54MouseExited(evt);
            }
        });
        jLabel54.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel54MouseMoved(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel54);

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stethoscope-icon.png"))); // NOI18N
        jLabel55.setText("Pediatria");
        jLabel55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel55MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel55MouseExited(evt);
            }
        });
        jLabel55.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel55MouseMoved(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel55);

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Hospital.png"))); // NOI18N
        jLabel56.setText("Otras Especialidades");
        jLabel56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel56MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel56MouseExited(evt);
            }
        });
        jLabel56.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel56MouseMoved(evt);
            }
        });
        jXTaskPane3.getContentPane().add(jLabel56);

        jXTaskPane4.setExpanded(false);
        jXTaskPane4.setTitle("MEDIDAS GENERALES");
        jXTaskPane4.setAnimated(false);
        jXTaskPane4.setFocusable(false);
        jXTaskPane4.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jXTaskPane4.setOpaque(true);
        jXTaskPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXTaskPane4MouseReleased(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        jLabel47.setText("Medidas generales");
        jLabel47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel47MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel47MouseExited(evt);
            }
        });
        jLabel47.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel47MouseMoved(evt);
            }
        });
        jXTaskPane4.getContentPane().add(jLabel47);

        jXTaskPane5.setExpanded(false);
        jXTaskPane5.setTitle("AUTORIZACIONES");
        jXTaskPane5.setAnimated(false);
        jXTaskPane5.setFocusable(false);
        jXTaskPane5.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jXTaskPane5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXTaskPane5MouseReleased(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/product-sales-report-icon.png"))); // NOI18N
        jLabel58.setText("Anexo 3");
        jLabel58.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel58.setEnabled(false);
        jLabel58.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel58MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel58MouseExited(evt);
            }
        });
        jLabel58.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel58MouseMoved(evt);
            }
        });
        jXTaskPane5.getContentPane().add(jLabel58);

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/report-icon.png"))); // NOI18N
        jLabel59.setText("CTC");
        jLabel59.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel59.setEnabled(false);
        jLabel59.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel59MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel59MouseExited(evt);
            }
        });
        jLabel59.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel59MouseMoved(evt);
            }
        });
        jXTaskPane5.getContentPane().add(jLabel59);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPane5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXTaskPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXTaskPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jpTratamientoLayout = new javax.swing.GroupLayout(jpTratamiento);
        jpTratamiento.setLayout(jpTratamientoLayout);
        jpTratamientoLayout.setHorizontalGroup(
            jpTratamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jpTratamientoLayout.setVerticalGroup(
            jpTratamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTratamientoLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/foward.png"))); // NOI18N
        jMenuItem2.setText("Seleccionar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);
        jPopupMenu1.add(jSeparator3);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stop.png"))); // NOI18N
        jMenuItem1.setText("Eliminar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(764, 514));
        setMinimumSize(new java.awt.Dimension(764, 514));
        setPreferredSize(new java.awt.Dimension(764, 514));

        jlbNombrePaciente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlbNombrePaciente.setForeground(new java.awt.Color(0, 102, 255));
        jlbNombrePaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N
        jlbNombrePaciente.setText("NOMBRE");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_22x22.png"))); // NOI18N
        jButton3.setToolTipText("Mas datos del paciente");
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTabbedPane3.setFocusable(false);
        jTabbedPane3.setMaximumSize(new java.awt.Dimension(764, 473));
        jTabbedPane3.setMinimumSize(new java.awt.Dimension(764, 473));
        jTabbedPane3.setPreferredSize(new java.awt.Dimension(764, 473));

        jPanel30.setFocusable(false);
        jPanel30.setOpaque(false);

        jpCentro.setBackground(new java.awt.Color(204, 204, 255));
        jpCentro.setMaximumSize(new java.awt.Dimension(584, 445));
        jpCentro.setMinimumSize(new java.awt.Dimension(584, 445));
        jpCentro.setPreferredSize(new java.awt.Dimension(584, 445));

        javax.swing.GroupLayout jpCentroLayout = new javax.swing.GroupLayout(jpCentro);
        jpCentro.setLayout(jpCentroLayout);
        jpCentroLayout.setHorizontalGroup(
            jpCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );
        jpCentroLayout.setVerticalGroup(
            jpCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jCheckBox6.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox6.setEnabled(false);

        jLabel22.setBackground(new java.awt.Color(204, 204, 204));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png"))); // NOI18N
        jLabel22.setText("<html><p>Motivo de Consulta</p></html>");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel22.setDoubleBuffered(true);
        jLabel22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel22.setMaximumSize(new java.awt.Dimension(145, 40));
        jLabel22.setMinimumSize(new java.awt.Dimension(145, 40));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel22MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel22MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel22MouseReleased(evt);
            }
        });
        jLabel22.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel22MouseMoved(evt);
            }
        });

        jLabel23.setBackground(new java.awt.Color(204, 204, 204));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png"))); // NOI18N
        jLabel23.setText("<html><p align=\"center\">Antecedentes Personales</p></html>");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel23.setDoubleBuffered(true);
        jLabel23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel23.setMaximumSize(new java.awt.Dimension(145, 40));
        jLabel23.setMinimumSize(new java.awt.Dimension(145, 40));
        jLabel23.setPreferredSize(new java.awt.Dimension(145, 40));
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel23MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel23MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel23MouseReleased(evt);
            }
        });
        jLabel23.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel23MouseMoved(evt);
            }
        });

        jCheckBox10.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox10.setEnabled(false);

        jCheckBox11.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox11.setEnabled(false);

        jCheckBox12.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox12.setEnabled(false);

        jCheckBox4.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox4.setEnabled(false);

        jCheckBox13.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox13.setEnabled(false);

        jCheckBox5.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox5.setEnabled(false);

        jLabel33.setBackground(new java.awt.Color(204, 204, 204));
        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png"))); // NOI18N
        jLabel33.setText("<html><p>Ordenes Medicas</p></html>");
        jLabel33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel33.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel33.setMaximumSize(new java.awt.Dimension(145, 40));
        jLabel33.setMinimumSize(new java.awt.Dimension(145, 40));
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel33MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel33MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel33MouseReleased(evt);
            }
        });
        jLabel33.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel33MouseMoved(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(204, 204, 204));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png"))); // NOI18N
        jLabel26.setText("<html><p>Diagnostico Medico</p></html>");
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel26.setMaximumSize(new java.awt.Dimension(145, 40));
        jLabel26.setMinimumSize(new java.awt.Dimension(145, 40));
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel26MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel26MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel26MouseReleased(evt);
            }
        });
        jLabel26.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel26MouseMoved(evt);
            }
        });

        jLabel27.setBackground(new java.awt.Color(204, 204, 204));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png"))); // NOI18N
        jLabel27.setText("<html><p align=\"center\">Pruebas Complementarias</p></html>");
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel27.setDoubleBuffered(true);
        jLabel27.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel27.setMaximumSize(new java.awt.Dimension(145, 40));
        jLabel27.setMinimumSize(new java.awt.Dimension(145, 40));
        jLabel27.setPreferredSize(new java.awt.Dimension(145, 40));
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel27MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel27MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel27MouseReleased(evt);
            }
        });
        jLabel27.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel27MouseMoved(evt);
            }
        });

        jLabel25.setBackground(new java.awt.Color(204, 204, 204));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png"))); // NOI18N
        jLabel25.setText("<html><p>Exploracion Fisica</p></html>");
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel25.setDoubleBuffered(true);
        jLabel25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel25.setMaximumSize(new java.awt.Dimension(145, 40));
        jLabel25.setMinimumSize(new java.awt.Dimension(145, 40));
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel25MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel25MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel25MouseReleased(evt);
            }
        });
        jLabel25.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel25MouseMoved(evt);
            }
        });

        jLabel24.setBackground(new java.awt.Color(204, 204, 204));
        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png"))); // NOI18N
        jLabel24.setText("<html><p>Enfermedad Actual</p></html>");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel24.setDoubleBuffered(true);
        jLabel24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel24.setMaximumSize(new java.awt.Dimension(145, 40));
        jLabel24.setMinimumSize(new java.awt.Dimension(145, 40));
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel24MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel24MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel24MouseReleased(evt);
            }
        });
        jLabel24.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel24MouseMoved(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print_22x22.png"))); // NOI18N
        jButton11.setBorderPainted(false);
        jButton11.setContentAreaFilled(false);
        jButton11.setFocusable(false);
        jButton11.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton11.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton11.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton11.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print_22x22_1.png"))); // NOI18N
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton11MouseExited(evt);
            }
        });
        jButton11.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton11MouseMoved(evt);
            }
        });
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel44.setBackground(new java.awt.Color(204, 204, 204));
        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png"))); // NOI18N
        jLabel44.setText("<html><p align=\"center\">Datos de Nota de Ingreso</p></html>");
        jLabel44.setToolTipText("");
        jLabel44.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel44.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel44.setMaximumSize(new java.awt.Dimension(145, 40));
        jLabel44.setMinimumSize(new java.awt.Dimension(145, 40));
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel44MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel44MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel44MouseReleased(evt);
            }
        });
        jLabel44.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel44MouseMoved(evt);
            }
        });

        jCheckBox14.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox14.setEnabled(false);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox10)
                                    .addComponent(jCheckBox12, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addComponent(jCheckBox13, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jCheckBox5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBox11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBox4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBox6, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpCentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jpCentro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Nota de Ingreso", jPanel30);

        jPanel33.setFocusable(false);
        jPanel33.setOpaque(false);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTree1MouseReleased(evt);
            }
        });
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jTree1);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setDoubleBuffered(true);
        jToolBar1.setOpaque(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/file_document_paper_orange_add.png"))); // NOI18N
        jButton1.setText("Nueva");
        jButton1.setToolTipText("NUEVA NOTA DE EVOLUCIÓN");
        jButton1.setFocusable(false);
        jButton1.setOpaque(false);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
        });
        jButton1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton1MouseMoved(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/23.png"))); // NOI18N
        jButton14.setText("Egreso");
        jButton14.setFocusable(false);
        jButton14.setOpaque(false);
        jButton14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton14MouseExited(evt);
            }
        });
        jButton14.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton14MouseMoved(evt);
            }
        });
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton14);

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/file_document_paper_save.png"))); // NOI18N
        jButton12.setText("Guardar");
        jButton12.setToolTipText("GUARDAR NOTA DE EVOLUCIÓN");
        jButton12.setEnabled(false);
        jButton12.setFocusable(false);
        jButton12.setOpaque(false);
        jButton12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton12MouseExited(evt);
            }
        });
        jButton12.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton12MouseMoved(evt);
            }
        });
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton12);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/file_document_paper_imp.png"))); // NOI18N
        jButton5.setText("Imprimir");
        jButton5.setToolTipText("IMPRIMIR NOTA DE EVOLUCION");
        jButton5.setEnabled(false);
        jButton5.setFocusable(false);
        jButton5.setOpaque(false);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton5MouseExited(evt);
            }
        });
        jButton5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton5MouseMoved(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/13.png"))); // NOI18N
        jButton13.setText("Finalizar");
        jButton13.setToolTipText("FINALIZAR NOTA DE EVOLUCION");
        jButton13.setEnabled(false);
        jButton13.setFocusable(false);
        jButton13.setOpaque(false);
        jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton13MouseExited(evt);
            }
        });
        jButton13.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton13MouseMoved(evt);
            }
        });
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton13);
        jToolBar1.add(jSeparator1);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/file_document_paper_orange.png"))); // NOI18N
        jButton8.setText("Subjetivo");
        jButton8.setToolTipText("SUBJETIVO");
        jButton8.setEnabled(false);
        jButton8.setFocusable(false);
        jButton8.setOpaque(false);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton8MouseExited(evt);
            }
        });
        jButton8.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton8MouseMoved(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton8);

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/file_document_paper_red_signes.png"))); // NOI18N
        jButton10.setText("S. Vitales");
        jButton10.setToolTipText("SIGNOS VITALES");
        jButton10.setEnabled(false);
        jButton10.setFocusable(false);
        jButton10.setOpaque(false);
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton10MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton10MousePressed(evt);
            }
        });
        jButton10.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton10MouseMoved(evt);
            }
        });
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton10);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/file_document_paper_blue.png"))); // NOI18N
        jButton2.setText("Objetivo");
        jButton2.setToolTipText("OBJETIVO");
        jButton2.setEnabled(false);
        jButton2.setFocusable(false);
        jButton2.setOpaque(false);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton2MouseExited(evt);
            }
        });
        jButton2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton2MouseMoved(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/file_document_paper_analisis.png"))); // NOI18N
        jButton6.setText("Analisis");
        jButton6.setToolTipText("ANALISIS");
        jButton6.setEnabled(false);
        jButton6.setFocusable(false);
        jButton6.setOpaque(false);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton6MouseExited(evt);
            }
        });
        jButton6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton6MouseMoved(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/file_document_paper_green.png"))); // NOI18N
        jButton4.setText("Plan");
        jButton4.setToolTipText("PLAN DE MAJEJO");
        jButton4.setEnabled(false);
        jButton4.setFocusable(false);
        jButton4.setOpaque(false);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4MouseExited(evt);
            }
        });
        jButton4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton4MouseMoved(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator2);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/33.png"))); // NOI18N
        jButton7.setText("Graficos");
        jButton7.setEnabled(false);
        jButton7.setFocusable(false);
        jButton7.setOpaque(false);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton7MouseExited(evt);
            }
        });
        jButton7.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton7MouseMoved(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton7);

        jTabbedPane6.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane6.setMaximumSize(new java.awt.Dimension(608, 414));
        jTabbedPane6.setMinimumSize(new java.awt.Dimension(608, 414));
        jTabbedPane6.setPreferredSize(new java.awt.Dimension(608, 414));

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bullet_blue2.png"))); // NOI18N
        jLabel57.setText("Nota de Egreso");
        jLabel57.setMaximumSize(new java.awt.Dimension(89, 11));
        jLabel57.setMinimumSize(new java.awt.Dimension(89, 11));
        jLabel57.setPreferredSize(new java.awt.Dimension(89, 11));

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bullet_green.png"))); // NOI18N
        jLabel60.setText("Evolución Finalizada");

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bullet_grey.png"))); // NOI18N
        jLabel61.setText("Nota Borrador");

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bullet_yellow.png"))); // NOI18N
        jLabel62.setText("Nota sin Finalizar");

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bullet_purple.png"))); // NOI18N
        jLabel63.setText("Nota de Valoracion");

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bullet_orange.png"))); // NOI18N
        jLabel64.setText("Nota de Interconsulta");
        jLabel64.setMaximumSize(new java.awt.Dimension(89, 11));
        jLabel64.setMinimumSize(new java.awt.Dimension(89, 11));
        jLabel64.setPreferredSize(new java.awt.Dimension(89, 11));

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 204));
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel65.setText("Evolucion Activa");
        jLabel65.setMaximumSize(new java.awt.Dimension(89, 11));
        jLabel65.setMinimumSize(new java.awt.Dimension(89, 11));
        jLabel65.setPreferredSize(new java.awt.Dimension(89, 11));

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bullet_blue.png"))); // NOI18N
        jLabel66.setText("Egreso sin Finalizar");
        jLabel66.setMaximumSize(new java.awt.Dimension(89, 11));
        jLabel66.setMinimumSize(new java.awt.Dimension(89, 11));
        jLabel66.setPreferredSize(new java.awt.Dimension(89, 11));

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel65, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jTabbedPane3.addTab("Nota de Evolucion", jPanel33);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editdelete.png"))); // NOI18N
        jButton9.setBorderPainted(false);
        jButton9.setContentAreaFilled(false);
        jButton9.setFocusable(false);
        jButton9.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton9.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton9.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton9.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editdelete1.png"))); // NOI18N
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton9MouseExited(evt);
            }
        });
        jButton9.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton9MouseMoved(evt);
            }
        });
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbNombrePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbNombrePaciente)
                    .addComponent(jButton3)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold desc="eventos de componentes">
    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        jpCentro.removeAll();
        jpMotivoC.setBounds(0, 0, 584, 445);
        jpCentro.add(jpMotivoC);
        jpMotivoC.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
        activeCheck(1);
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        jpCentro.removeAll();
        jpAntPersonales.setBounds(0, 0, 584, 445);
        jpCentro.add(jpAntPersonales);
        jpAntPersonales.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
        activeCheck(2);
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        jpCentro.removeAll();
        jpEnfActual.setBounds(0, 0, 584, 445);
        jpCentro.add(jpEnfActual);
        jpEnfActual.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
        activeCheck(3);
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        jpCentro.removeAll();
        jpExpFisica.setBounds(0, 0, 584, 445);
        jpCentro.add(jpExpFisica);
        jpExpFisica.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
        activeCheck(4);
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        jpCentro.removeAll();
        jpDiagMedico.setBounds(0, 0, 584, 445);
        jpCentro.add(jpDiagMedico);
        jpDiagMedico.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
        activeCheck(6);
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        jpCentro.removeAll();
        jpPruebasDiag.setBounds(0, 0, 584, 445);
        jpCentro.add(jpPruebasDiag);
        jpPruebasDiag.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
        activeCheck(5);
    }//GEN-LAST:event_jLabel27MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (personas == null) {
            personas = new FormPersonas();
            personas.infopaciente = this.infopaciente;
        } else {
            personas.infopaciente = this.infopaciente;
        }
        personas.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        jpCentro.removeAll();
        jpTratamiento.setBounds(0, 0, 584, 445);
        jpCentro.add(jpTratamiento);
        jpTratamiento.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
        activeCheck(7);
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jtbTratamiento4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbTratamiento4MouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            if (jtbTratamiento4.columnAtPoint(evt.getPoint()) == 0) {
            } else if (jtbTratamiento4.columnAtPoint(evt.getPoint()) == 1) {
                //verificar existencia del archivo en la bd o en la clase entidad
                if (((String) jtbTratamiento4.getValueAt(jtbTratamiento4.rowAtPoint(evt.getPoint()), 6))
                        .equals("1")) {
                    String path2 = (String) modeloAyudDiag.getValueAt(jtbTratamiento4.rowAtPoint(evt.getPoint()), 5)
                            + s + (String) modeloAyudDiag.getValueAt(jtbTratamiento4.rowAtPoint(evt.getPoint()), 2);
                    Funciones.fileDownload(path2);
                }
            }
        }
    }//GEN-LAST:event_jtbTratamiento4MouseClicked

    private void jTextField15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyReleased
        if ("".equals(jTextField15.getText())) {
            idDiag5 = 1;
        }
    }//GEN-LAST:event_jTextField15KeyReleased

    private void jTextField14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyReleased
        if ("".equals(jTextField14.getText())) {
            idDiag4 = 1;
        }
    }//GEN-LAST:event_jTextField14KeyReleased

    private void jTextField13KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField13KeyReleased
        if ("".equals(jTextField13.getText())) {
            idDiag3 = 1;
        }
    }//GEN-LAST:event_jTextField13KeyReleased

    private void jTextField12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField12KeyReleased
        if ("".equals(jTextField12.getText())) {
            idDiag2 = 1;
        }
    }//GEN-LAST:event_jTextField12KeyReleased

    private void jTextField11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyReleased
        if ("".equals(jTextField11.getText())) {
            idDiag1 = 1;
        }
    }//GEN-LAST:event_jTextField11KeyReleased

    private void jTextArea10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea10KeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_CONTROL) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea10KeyTyped

    private void jLabel50MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseMoved
        Funciones.setLabelInfo("PROCEDIMIENTOS E INTERVENCIONES QUIRÚRGICAS");
        jLabel50.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel50MouseMoved

    private void jLabel50MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseExited
        Funciones.setLabelInfo();
        jLabel50.setForeground(Color.black);
    }//GEN-LAST:event_jLabel50MouseExited

    private void jLabel49MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseMoved
        Funciones.setLabelInfo("IMAGENOLOGIA");
        jLabel49.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel49MouseMoved

    private void jLabel49MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseExited
        Funciones.setLabelInfo();
        jLabel49.setForeground(Color.black);
    }//GEN-LAST:event_jLabel49MouseExited

    private void jLabel45MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseMoved
        Funciones.setLabelInfo("LABORATORIO CLINICO");
        jLabel45.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel45MouseMoved

    private void jLabel45MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseExited
        Funciones.setLabelInfo();
        jLabel45.setForeground(Color.black);
    }//GEN-LAST:event_jLabel45MouseExited

    private void jLabel36MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseMoved
        Funciones.setLabelInfo("CONSULTA, MONITORIZACION Y PROCEDIMIENTOS DIAGNOSTICOS");
        jLabel36.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel36MouseMoved

    private void jLabel36MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseExited
        Funciones.setLabelInfo();
        jLabel36.setForeground(Color.black);
    }//GEN-LAST:event_jLabel36MouseExited

    private void jLabel51MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseExited
        Funciones.setLabelInfo();
        jLabel51.setForeground(Color.black);
    }//GEN-LAST:event_jLabel51MouseExited

    private void jLabel51MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseMoved
        Funciones.setLabelInfo("TODOS LOS PROCEDIMIENTOS Y SERVICIOS");
        jLabel51.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel51MouseMoved

    private void jLabel46MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseExited
        Funciones.setLabelInfo();
        jLabel46.setForeground(Color.black);
    }//GEN-LAST:event_jLabel46MouseExited

    private void jLabel46MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseMoved
        Funciones.setLabelInfo("LISTADO DE MEDICAMENTOS");
        jLabel46.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel46MouseMoved

    private void jLabel48MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseExited
        Funciones.setLabelInfo();
        jLabel48.setForeground(Color.black);
    }//GEN-LAST:event_jLabel48MouseExited

    private void jLabel48MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseMoved
        Funciones.setLabelInfo("CIRUGIA GENERAL");
        jLabel48.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel48MouseMoved

    private void jLabel52MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseExited
        Funciones.setLabelInfo();
        jLabel52.setForeground(Color.black);
    }//GEN-LAST:event_jLabel52MouseExited

    private void jLabel52MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseMoved
        Funciones.setLabelInfo("GINECOLOGIA");
        jLabel52.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel52MouseMoved

    private void jLabel53MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseExited
        Funciones.setLabelInfo();
        jLabel53.setForeground(Color.black);
    }//GEN-LAST:event_jLabel53MouseExited

    private void jLabel53MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseMoved
        Funciones.setLabelInfo("MEDICINA INTERNA");
        jLabel53.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel53MouseMoved

    private void jLabel54MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseExited
        Funciones.setLabelInfo();
        jLabel54.setForeground(Color.black);
    }//GEN-LAST:event_jLabel54MouseExited

    private void jLabel54MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseMoved
        Funciones.setLabelInfo("ORTOPEDIA");
        jLabel54.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel54MouseMoved

    private void jLabel55MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseExited
        Funciones.setLabelInfo();
        jLabel55.setForeground(Color.black);
    }//GEN-LAST:event_jLabel55MouseExited

    private void jLabel55MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseMoved
        Funciones.setLabelInfo("PEDIATRIA");
        jLabel55.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel55MouseMoved

    private void jLabel56MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseExited
        Funciones.setLabelInfo();
        jLabel56.setForeground(Color.black);
    }//GEN-LAST:event_jLabel56MouseExited

    private void jLabel56MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseMoved
        Funciones.setLabelInfo("OTRAS ESPECIALIDADES");
        jLabel56.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel56MouseMoved

    private void jLabel47MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseExited
        Funciones.setLabelInfo();
        jLabel47.setForeground(Color.black);
    }//GEN-LAST:event_jLabel47MouseExited

    private void jLabel47MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseMoved
        Funciones.setLabelInfo("MEDIDAS GENERALES");
        jLabel47.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel47MouseMoved

    private void jXTaskPane1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTaskPane1MouseReleased
        jXTaskPane2.setExpanded(false);
        jXTaskPane3.setExpanded(false);
        jXTaskPane4.setExpanded(false);
        jXTaskPane5.setExpanded(false);
    }//GEN-LAST:event_jXTaskPane1MouseReleased

    private void jXTaskPane2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTaskPane2MouseReleased
        jXTaskPane1.setExpanded(false);
        jXTaskPane3.setExpanded(false);
        jXTaskPane4.setExpanded(false);
        jXTaskPane5.setExpanded(false);
    }//GEN-LAST:event_jXTaskPane2MouseReleased

    private void jXTaskPane3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTaskPane3MouseReleased
        jXTaskPane2.setExpanded(false);
        jXTaskPane1.setExpanded(false);
        jXTaskPane4.setExpanded(false);
        jXTaskPane5.setExpanded(false);
    }//GEN-LAST:event_jXTaskPane3MouseReleased

    private void jXTaskPane4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTaskPane4MouseReleased
        jXTaskPane2.setExpanded(false);
        jXTaskPane3.setExpanded(false);
        jXTaskPane1.setExpanded(false);
        jXTaskPane5.setExpanded(false);
    }//GEN-LAST:event_jXTaskPane4MouseReleased

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
        if (pMedic == null) {
            pMedic = new pTratMedic();
            pMedic.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pMedic.setBounds(0, 0, 380, 420);
        jPanel35.add(pMedic);
        pMedic.buttonSeven6.setVisible(false);
        pMedic.buttonSeven7.setVisible(false);
        pMedic.buttonSeven8.setVisible(false);
        pMedic.buttonSeven9.setVisible(false);
        pMedic.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel46MouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        if (pConsultDiag == null) {
            pConsultDiag = new pTratPConsultDiag();
            pConsultDiag.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pConsultDiag.setBounds(0, 0, 380, 420);
        jPanel35.add(pConsultDiag);
        pConsultDiag.buttonSeven6.setVisible(false);
        pConsultDiag.buttonSeven7.setVisible(false);
        pConsultDiag.jTextArea25.setEditable(false);
        pConsultDiag.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
        if (pLaboratorio == null) {
            pLaboratorio = new pTratLaboratorio();
            pLaboratorio.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pLaboratorio.setBounds(0, 0, 380, 420);
        jPanel35.add(pLaboratorio);
        pLaboratorio.buttonSeven6.setVisible(false);
        pLaboratorio.buttonSeven7.setVisible(false);
        pLaboratorio.jTextArea25.setEditable(false);
        pLaboratorio.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel45MouseClicked

    private void jLabel49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseClicked
        if (pImagenologia == null) {
            pImagenologia = new pTratImagenologia();
            pImagenologia.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pImagenologia.setBounds(0, 0, 380, 420);
        jPanel35.add(pImagenologia);
        pImagenologia.buttonSeven6.setVisible(false);
        pImagenologia.buttonSeven7.setVisible(false);
        pImagenologia.jTextArea25.setEditable(false);
        pImagenologia.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel49MouseClicked

    private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
        if (pQuirurgico == null) {
            pQuirurgico = new pTratQuirurgico();
            pQuirurgico.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pQuirurgico.setBounds(0, 0, 380, 420);
        jPanel35.add(pQuirurgico);
        pQuirurgico.buttonSeven6.setVisible(false);
        pQuirurgico.buttonSeven7.setVisible(false);
        pQuirurgico.jTextArea25.setEditable(false);
        pQuirurgico.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel50MouseClicked

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        if (pProcedimientos == null) {
            pProcedimientos = new pTratMasProcedimientos(false);
            pProcedimientos.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pProcedimientos.setBounds(0, 0, 380, 420);
        jPanel35.add(pProcedimientos);
        pProcedimientos.buttonSeven6.setVisible(false);
        pProcedimientos.buttonSeven7.setVisible(false);
        pProcedimientos.jTextArea25.setEditable(false);
        pProcedimientos.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel51MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String mensaje = "Guarde sus cambios antes de salir.\n¿Desea salir de este Documento? ";
        int entrada = JOptionPane.showConfirmDialog(null, mensaje, "Cerrar Documento", JOptionPane.YES_NO_OPTION);
        if (entrada == 0) {
            atencionurgencia.AtencionUrgencia.panelindex.jpContainer.removeAll();
            atencionurgencia.AtencionUrgencia.panelindex.jpContainer.validate();
            atencionurgencia.AtencionUrgencia.panelindex.jpContainer.repaint();
            Funciones.setLabelInfo();
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jLabel48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseClicked
        if (pInterconsulta0 == null) {
            pInterconsulta0 = new pTratInterconsulta(0);//cirugia
            pInterconsulta0.showExistente(infohistoriac);
        }
        jPanel35.removeAll();
        pInterconsulta0.setBounds(0, 0, 380, 420);
        jPanel35.add(pInterconsulta0);
        pInterconsulta0.buttonSeven7.setVisible(false);
        pInterconsulta0.buttonSeven8.setVisible(false);
        pInterconsulta0.jTextArea25.setEditable(false);
        pInterconsulta0.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel48MouseClicked

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked
        if (pInterconsulta1 == null) {
            pInterconsulta1 = new pTratInterconsulta(1);//Ginecologia
            pInterconsulta1.showExistente(infohistoriac);
        }
        jPanel35.removeAll();
        pInterconsulta1.setBounds(0, 0, 380, 420);
        jPanel35.add(pInterconsulta1);
        pInterconsulta1.buttonSeven7.setVisible(false);
        pInterconsulta1.buttonSeven8.setVisible(false);
        pInterconsulta1.jTextArea25.setEditable(false);
        pInterconsulta1.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel52MouseClicked

    private void jLabel53MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseClicked
        if (pInterconsulta2 == null) {
            pInterconsulta2 = new pTratInterconsulta(2);//Medicina Interna
            pInterconsulta2.showExistente(infohistoriac);
        }
        jPanel35.removeAll();
        pInterconsulta2.setBounds(0, 0, 380, 420);
        jPanel35.add(pInterconsulta2);
        pInterconsulta2.buttonSeven7.setVisible(false);
        pInterconsulta2.buttonSeven8.setVisible(false);
        pInterconsulta2.jTextArea25.setEditable(false);
        pInterconsulta2.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel53MouseClicked

    private void jLabel54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseClicked
        if (pInterconsulta3 == null) {
            pInterconsulta3 = new pTratInterconsulta(3);//Ortopedia
            pInterconsulta3.showExistente(infohistoriac);
        }
        jPanel35.removeAll();
        pInterconsulta3.setBounds(0, 0, 380, 420);
        jPanel35.add(pInterconsulta3);
        pInterconsulta3.buttonSeven7.setVisible(false);
        pInterconsulta3.buttonSeven8.setVisible(false);
        pInterconsulta3.jTextArea25.setEditable(false);
        pInterconsulta3.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel54MouseClicked

    private void jLabel55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseClicked
        if (pInterconsulta4 == null) {
            pInterconsulta4 = new pTratInterconsulta(4);//Pediatria
            pInterconsulta4.showExistente(infohistoriac);
        }
        jPanel35.removeAll();
        pInterconsulta4.setBounds(0, 0, 380, 420);
        jPanel35.add(pInterconsulta4);
        pInterconsulta4.buttonSeven7.setVisible(false);
        pInterconsulta4.buttonSeven8.setVisible(false);
        pInterconsulta4.jTextArea25.setEditable(false);
        pInterconsulta4.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel55MouseClicked

    private void jLabel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseClicked
        if (pOtrasInterconsultas == null) {
            pOtrasInterconsultas = new pTratOtrasInterconsultas();
            pOtrasInterconsultas.showLista(infohistoriac);
        }
        jPanel35.removeAll();
        pOtrasInterconsultas.setBounds(0, 0, 380, 420);
        jPanel35.add(pOtrasInterconsultas);
        pOtrasInterconsultas.jTextArea25.setEditable(false);
        pOtrasInterconsultas.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel56MouseClicked

    private void jLabel47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseClicked
        if (pMedidaGeneral == null) {
            pMedidaGeneral = new pTratMedidaGeneral();
            pMedidaGeneral.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pMedidaGeneral.setBounds(0, 0, 380, 420);
        jPanel35.add(pMedidaGeneral);
        pMedidaGeneral.jButton1.setVisible(false);
        pMedidaGeneral.jButton2.setVisible(false);
        pMedidaGeneral.jTextArea26.setEditable(false);
        pMedidaGeneral.jTextArea27.setEditable(false);
        pMedidaGeneral.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel47MouseClicked

    private void jLabel22MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseMoved
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
        Funciones.setLabelInfo("MOTIVO DE CONSULTA");
    }//GEN-LAST:event_jLabel22MouseMoved

    private void jLabel23MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseMoved
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
        Funciones.setLabelInfo("ANTECEDENTES PERSONALES");
    }//GEN-LAST:event_jLabel23MouseMoved

    private void jLabel24MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseMoved
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
        Funciones.setLabelInfo("ENFERMEDAD ACTUAL");
    }//GEN-LAST:event_jLabel24MouseMoved

    private void jLabel25MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseMoved
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
        Funciones.setLabelInfo("EXPLORACION FISICA");
    }//GEN-LAST:event_jLabel25MouseMoved

    private void jLabel27MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseMoved
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
        Funciones.setLabelInfo("PRUEBAS COMPLEMENTARIAS");
    }//GEN-LAST:event_jLabel27MouseMoved

    private void jLabel26MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseMoved
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
        Funciones.setLabelInfo("DIAGNOSTICO MEDICO");
    }//GEN-LAST:event_jLabel26MouseMoved

    private void jLabel33MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseMoved
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
        Funciones.setLabelInfo("TRATAMIENTO E INDICACIONES");
    }//GEN-LAST:event_jLabel33MouseMoved

    private void jLabel22MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseExited
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png")));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel22MouseExited

    private void jLabel23MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseExited
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png")));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel23MouseExited

    private void jLabel24MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseExited
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png")));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel24MouseExited

    private void jLabel25MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseExited
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png")));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel25MouseExited

    private void jLabel27MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseExited
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png")));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel27MouseExited

    private void jLabel26MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseExited
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png")));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel26MouseExited

    private void jLabel33MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseExited
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png")));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel33MouseExited

    private void jLabel58MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel58MouseClicked
//        if(pAnexo31==null){
//            pAnexo31 = new pAnexo3();
//            pAnexo31.showLista(infohistoriac);
//        }
//        jPanel35.removeAll();
//        pAnexo31.setBounds(0,0,380,420);
//        jPanel35.add(pAnexo31);
//        pAnexo31.setVisible(true);
//        jPanel35.validate();
//        jPanel35.repaint();
    }//GEN-LAST:event_jLabel58MouseClicked

    private void jLabel58MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel58MouseExited
        Funciones.setLabelInfo();
        jLabel58.setForeground(Color.black);
    }//GEN-LAST:event_jLabel58MouseExited

    private void jLabel58MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel58MouseMoved
        Funciones.setLabelInfo("ANEXO 3");
        jLabel58.setForeground(Color.blue);
    }//GEN-LAST:event_jLabel58MouseMoved

    private void jLabel59MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel59MouseClicked

    }//GEN-LAST:event_jLabel59MouseClicked

    private void jLabel59MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel59MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel59MouseExited

    private void jLabel59MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel59MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel59MouseMoved

    private void jXTaskPane5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTaskPane5MouseReleased
        jXTaskPane1.setExpanded(false);
        jXTaskPane2.setExpanded(false);
        jXTaskPane3.setExpanded(false);
        jXTaskPane4.setExpanded(false);
    }//GEN-LAST:event_jXTaskPane5MouseReleased

    private void jButton9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton9MouseExited

    private void jButton9MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseMoved
        Funciones.setLabelInfo("SALIR DE ATENCION DE URGENCIAS");
    }//GEN-LAST:event_jButton9MouseMoved

    private void jTextArea11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea11FocusLost
        if (jTextArea11.getText().isEmpty()) {
            jTextArea11.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea11FocusLost

    private void jTextArea11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea11FocusGained
        if ("NINGUNO".equals(jTextArea11.getText())) {
            jTextArea11.selectAll();
        }
    }//GEN-LAST:event_jTextArea11FocusGained

    private void jTextArea12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea12FocusGained
        if ("NINGUNO".equals(jTextArea12.getText())) {
            jTextArea12.selectAll();
        }
    }//GEN-LAST:event_jTextArea12FocusGained

    private void jTextArea12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea12FocusLost
        if (jTextArea12.getText().isEmpty()) {
            jTextArea12.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea12FocusLost

    private void jTextArea22FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea22FocusGained
        if ("NINGUNO".equals(jTextArea22.getText())) {
            jTextArea22.selectAll();
        }
    }//GEN-LAST:event_jTextArea22FocusGained

    private void jTextArea22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea22FocusLost
        if (jTextArea22.getText().isEmpty()) {
            jTextArea22.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea22FocusLost

    private void jTextArea23FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea23FocusGained
        if ("NINGUNO".equals(jTextArea23.getText())) {
            jTextArea23.selectAll();
        }
    }//GEN-LAST:event_jTextArea23FocusGained

    private void jTextArea23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea23FocusLost
        if (jTextArea23.getText().isEmpty()) {
            jTextArea23.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea23FocusLost

    private void jTextArea24FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea24FocusGained
        if ("NINGUNO".equals(jTextArea24.getText())) {
            jTextArea24.selectAll();
        }
    }//GEN-LAST:event_jTextArea24FocusGained

    private void jTextArea24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea24FocusLost
        if (jTextArea24.getText().isEmpty()) {
            jTextArea24.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea24FocusLost

    private void jTextArea25FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea25FocusGained
        if ("NINGUNO".equals(jTextArea25.getText())) {
            jTextArea25.selectAll();
        }
    }//GEN-LAST:event_jTextArea25FocusGained

    private void jTextArea25FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea25FocusLost
        if (jTextArea25.getText().isEmpty()) {
            jTextArea25.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea25FocusLost

    private void jTextArea4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea4FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea4.getText())) {
            jTextArea4.selectAll();
        }
    }//GEN-LAST:event_jTextArea4FocusGained

    private void jTextArea4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea4FocusLost
        if (jTextArea4.getText().isEmpty()) {
            jTextArea4.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea4FocusLost

    private void jTextArea6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea6FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea6.getText())) {
            jTextArea6.selectAll();
        }
    }//GEN-LAST:event_jTextArea6FocusGained

    private void jTextArea6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea6FocusLost
        if (jTextArea6.getText().isEmpty()) {
            jTextArea6.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea6FocusLost

    private void jTextArea8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea8FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea8.getText())) {
            jTextArea8.selectAll();
        }
    }//GEN-LAST:event_jTextArea8FocusGained

    private void jTextArea8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea8FocusLost
        if (jTextArea8.getText().isEmpty()) {
            jTextArea8.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea8FocusLost

    private void jTextArea9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea9FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea9.getText())) {
            jTextArea9.selectAll();
        }
    }//GEN-LAST:event_jTextArea9FocusGained

    private void jTextArea9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea9FocusLost
        if (jTextArea9.getText().isEmpty()) {
            jTextArea9.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea9FocusLost

    private void jTextArea14FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea14FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea14.getText())) {
            jTextArea14.selectAll();
        }
    }//GEN-LAST:event_jTextArea14FocusGained

    private void jTextArea14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea14FocusLost
        if (jTextArea14.getText().isEmpty()) {
            jTextArea14.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea14FocusLost

    private void jTextArea15FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea15FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea15.getText())) {
            jTextArea15.selectAll();
        }
    }//GEN-LAST:event_jTextArea15FocusGained

    private void jTextArea15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea15FocusLost
        if (jTextArea15.getText().isEmpty()) {
            jTextArea15.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea15FocusLost

    private void jTextArea16FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea16FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea16.getText())) {
            jTextArea16.selectAll();
        }
    }//GEN-LAST:event_jTextArea16FocusGained

    private void jTextArea16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea16FocusLost
        if (jTextArea16.getText().isEmpty()) {
            jTextArea16.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea16FocusLost

    private void jTextArea17FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea17FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea17.getText())) {
            jTextArea17.selectAll();
        }
    }//GEN-LAST:event_jTextArea17FocusGained

    private void jTextArea17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea17FocusLost
        if (jTextArea17.getText().isEmpty()) {
            jTextArea17.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea17FocusLost

    private void jTextArea2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea2.transferFocusBackward();
            } else {
                jTextArea2.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea2KeyPressed

    private void jTextArea10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea10KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea10.transferFocusBackward();
            } else {
                jTextArea10.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea10KeyPressed

    private void jTextArea11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea11KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea11.transferFocusBackward();
            } else {
                jTextArea11.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea11KeyPressed

    private void jTextArea12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea12KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea12.transferFocusBackward();
            } else {
                jTextArea12.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea12KeyPressed

    private void jTextArea22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea22KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea22.transferFocusBackward();
            } else {
                jTextArea22.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea22KeyPressed

    private void jTextArea23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea23KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea23.transferFocusBackward();
            } else {
                jTextArea23.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea23KeyPressed

    private void jTextArea5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea5KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {
                jTextArea5.transferFocusBackward();
            } else {
                jTextArea5.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea5KeyPressed

    private void jTextArea7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea7KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea7.transferFocusBackward();
            } else {
                jTextArea7.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea7KeyPressed

    private void jTextArea24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea24KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea24.transferFocusBackward();
            } else {
                jTextArea24.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea24KeyPressed

    private void jTextArea25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea25KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea24.transferFocusBackward();
            } else {
                jTextArea24.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea25KeyPressed

    private void jTextArea9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea9KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea9.transferFocusBackward();
            } else {
                jTextArea9.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea9KeyPressed

    private void jTextArea14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea14KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea14.transferFocusBackward();
            } else {
                jTextArea14.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea14KeyPressed

    private void jTextArea15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea15KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea15.transferFocusBackward();
            } else {
                jTextArea15.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea15KeyPressed

    private void jTextArea16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea16KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {
                jTextArea16.transferFocusBackward();
            } else {
                jTextArea16.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea16KeyPressed

    private void jTextArea17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea17KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea17.transferFocusBackward();
            } else {
                jTextArea17.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea17KeyPressed

    private void jTextArea3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea3.transferFocusBackward();
            } else {
                jTextArea3.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea3KeyPressed

    private void jTextArea4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea4KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea4.transferFocusBackward();
            } else {
                jTextArea4.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea4KeyPressed

    private void jTextArea6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea6KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea6.transferFocusBackward();
            } else {
                jTextArea6.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea6KeyPressed

    private void jTextArea8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea8KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea8.transferFocusBackward();
            } else {
                jTextArea8.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea8KeyPressed

    private void jButton11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton11MouseExited

    private void jButton11MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseMoved
        Funciones.setLabelInfo("IMPRIMIR");
    }//GEN-LAST:event_jButton11MouseMoved

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if (getValidaFirma()) {
            impresionesHC imp = new impresionesHC();
            imp.setidHC(this.infohistoriac);
            imp.setdestinoHc("OBSERVACION DE URGENCIAS");
            imp.setLocationRelativeTo(null);
            imp.setNoValido(true);
            imp.setVisible(true);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jLabel33MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MousePressed
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-2.png")));
    }//GEN-LAST:event_jLabel33MousePressed

    private void jLabel33MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseReleased
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
    }//GEN-LAST:event_jLabel33MouseReleased

    private void jLabel26MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MousePressed
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-2.png")));
    }//GEN-LAST:event_jLabel26MousePressed

    private void jLabel27MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MousePressed
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-2.png")));
    }//GEN-LAST:event_jLabel27MousePressed

    private void jLabel25MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MousePressed
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-2.png")));
    }//GEN-LAST:event_jLabel25MousePressed

    private void jLabel24MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MousePressed
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-2.png")));
    }//GEN-LAST:event_jLabel24MousePressed

    private void jLabel23MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MousePressed
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-2.png")));
    }//GEN-LAST:event_jLabel23MousePressed

    private void jLabel22MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MousePressed
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-2.png")));
    }//GEN-LAST:event_jLabel22MousePressed

    private void jLabel26MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseReleased
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
    }//GEN-LAST:event_jLabel26MouseReleased

    private void jLabel22MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseReleased
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
    }//GEN-LAST:event_jLabel22MouseReleased

    private void jLabel23MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseReleased
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
    }//GEN-LAST:event_jLabel23MouseReleased

    private void jLabel24MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseReleased
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
    }//GEN-LAST:event_jLabel24MouseReleased

    private void jLabel25MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseReleased
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
    }//GEN-LAST:event_jLabel25MouseReleased

    private void jLabel27MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseReleased
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
    }//GEN-LAST:event_jLabel27MouseReleased

    private void jButton1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseMoved
        Funciones.setLabelInfo(((JButton) evt.getSource()).getToolTipText());
    }//GEN-LAST:event_jButton1MouseMoved

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        saveEvolucion();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        newEvolucion();
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
        jpCentro.removeAll();
//        cU = new datosHCU(infohistoriac);
        cU.setBounds(0, 0, 584, 445);
        jpCentro.add(cU);
        cU.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
        activeCheck(8);
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jLabel44MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseExited
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1.png")));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel44MouseExited

    private void jLabel44MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MousePressed
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-2.png")));
    }//GEN-LAST:event_jLabel44MousePressed

    private void jLabel44MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseReleased
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
    }//GEN-LAST:event_jLabel44MouseReleased

    private void jLabel44MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseMoved
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/boton1-1.png")));
        Funciones.setLabelInfo("DATOS DE NOTA DE INGRESO");
    }//GEN-LAST:event_jLabel44MouseMoved

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (!subjetivo.isValid()) {
            addClosableTab(jTabbedPane6, subjetivo, "Nota Subjetiva", PAGE_SUBJETIVO);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton8MouseExited

    private void jButton8MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseMoved
        Funciones.setLabelInfo(((JButton) evt.getSource()).getToolTipText());
    }//GEN-LAST:event_jButton8MouseMoved

    private void jButton10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton10MouseExited

    private void jButton10MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseMoved
        Funciones.setLabelInfo(((JButton) evt.getSource()).getToolTipText());
    }//GEN-LAST:event_jButton10MouseMoved

    private void jButton10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MousePressed
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton10MousePressed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (!evol.isValid()) {
            if (jButton10.getText().equals("S. Vitales")) {
                addClosableTab(jTabbedPane6, evol, "Signos Vitales", PAGE_SIGNOS);
            } else {
                addClosableTab(jTabbedPane6, evol, "Estado General", PAGE_SIGNOS);
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (evoSeleccion.getEstado() != 0) {
            imphcuEvo imEvo = new imphcuEvo(null, true);
            imEvo.setLocationRelativeTo(null);
            imEvo.setEvolucion(evoSeleccion);
            System.out.println(evoSeleccion);
            if (evoSeleccion.getEstado() == 1 || evoSeleccion.getEstado() == 3) {
                imEvo.setNoValido(true);
            } else {
                imEvo.setNoValido(false);
            }
            imEvo.activeChec();
            imEvo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No puede imprimir una nota borrador");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton12MouseExited

    private void jButton12MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseMoved
        Funciones.setLabelInfo(((JButton) evt.getSource()).getToolTipText());
    }//GEN-LAST:event_jButton12MouseMoved

    private void jButton13MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseMoved
        Funciones.setLabelInfo(((JButton) evt.getSource()).getToolTipText());
    }//GEN-LAST:event_jButton13MouseMoved

    private void jButton2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseMoved
        Funciones.setLabelInfo(((JButton) evt.getSource()).getToolTipText());
    }//GEN-LAST:event_jButton2MouseMoved

    private void jButton6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseMoved
        Funciones.setLabelInfo(((JButton) evt.getSource()).getToolTipText());
    }//GEN-LAST:event_jButton6MouseMoved

    private void jButton4MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseMoved
        Funciones.setLabelInfo(((JButton) evt.getSource()).getToolTipText());
    }//GEN-LAST:event_jButton4MouseMoved

    private void jButton7MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseMoved
        Funciones.setLabelInfo(((JButton) evt.getSource()).getToolTipText());
    }//GEN-LAST:event_jButton7MouseMoved

    private void jButton5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton5MouseExited

    private void jButton5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseMoved
        Funciones.setLabelInfo(((JButton) evt.getSource()).getToolTipText());
    }//GEN-LAST:event_jButton5MouseMoved

    private void jButton13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton13MouseExited

    private void jButton2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton2MouseExited

    private void jButton6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton6MouseExited

    private void jButton4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton4MouseExited

    private void jButton7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton7MouseExited

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        gSignos = new grafic_sVitales();
        Icon icon = PAGE_ICON;
//        jTabbedPane6.removeAll();
        gSignos.setBounds(0, 0, 386, 603);
        addClosableTab(jTabbedPane6, gSignos, "Graficos", icon);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!objetivo.isValid()) {
            if (jButton2.getText().equals("Objetivo")) {
                addClosableTab(jTabbedPane6, objetivo, "Nota Objetiva", PAGE_OBJETIVOS);
            } else {
                addClosableTab(jTabbedPane6, objetivo, "Síntesis", PAGE_OBJETIVOS);
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (!analisis.isValid()) {
            if (jButton6.getText().equals("")) {
                addClosableTab(jTabbedPane6, analisis, "Analisis", PAGE_ANALISIS);
            } else {
                addClosableTab(jTabbedPane6, analisis, "DX Egreso", PAGE_ANALISIS);
            }
        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (!pplan.isValid()) {
            if (jButton4.getText().equals("Plan")) {
                addClosableTab(jTabbedPane6, pplan, "Plan", PAGE_PLAN);
            } else {
                addClosableTab(jTabbedPane6, pplan, "Conducta", PAGE_PLAN);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        if (getCamposObligatoriosVacios() == false) {
            if (hcuEvolucionJpaController == null) {
                hcuEvolucionJpaController = new HcuEvolucionJpaController(factory);
            }
            HcuEvolucion he = null;
            imphcuEvo imp = new imphcuEvo(null, true);
            try {
                he = evoSeleccion;
                if (he.getEstado() == 1) {
                    String mensaje = "¿Si finaliza la nota de Evolución no podra modificarla posteriormente? ";
                    int entrada = JOptionPane.showConfirmDialog(null, mensaje, "Confirmar finalizacion", JOptionPane.YES_NO_OPTION);
                    if (entrada == 0) {
                        he.setEstado(2);
                        hcuEvolucionJpaController.edit(he);

                        //                    setJTreeEvo(); 
                        imp.setNoValido(false);
                        imp.setEvolucion(he);
                        imp.imprimir();
                        jButton13.setEnabled(false);
                        jButton12.setEnabled(false);
                        if (est == 1) {
                            jButton14.setEnabled(false);
                        }
                    }
                } else if (he.getEstado() == 0) {
                    JOptionPane.showMessageDialog(null, "No se puede finalizar Notas en estado Borrador");
                } else if (he.getEstado() == 3) {
                    System.out.println("1");
                    if (he.getHcuEvoEgreso().size() > 0) {
                        String mensaje = "¿Si finaliza la nota de Egreso no podra modificarla posteriormente? ";
                        int entrada = JOptionPane.showConfirmDialog(null, mensaje, "Confirmar Finalizacion", JOptionPane.YES_NO_OPTION);
                        if (entrada == 0) {
                            he.setEstado(4);
                            hcuEvolucionJpaController.edit(he);
                            //                    setJTreeEvo(); 
                            imp.setNoValido(false);
                            imp.setEvolucion(he);
                            imp.imprimir();
                            jButton13.setEnabled(false);
                            jButton12.setEnabled(false);
                            if (est == 1) {
                                jButton14.setEnabled(false);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe destino del paciente relacionado al egreso");
                    }
                } else if (he.getEstado() > 1) {
                    JOptionPane.showMessageDialog(null, "Esta Nota ya se encuentra finalizada");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No puede finalizar la nota de Evolución sin haber guardado");
            }
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTree1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseReleased
        TreePath selPath = jTree1.getPathForLocation(evt.getX(), evt.getY());
        jTree1.setSelectionPath(selPath);
        if (evt.isPopupTrigger()) {
            if (selPath != null) {
                Object nodo = selPath.getLastPathComponent();
                if (((DefaultMutableTreeNode) nodo).getUserObject() instanceof HcuEvolucion) {
                    HcuEvolucion evolucion = (HcuEvolucion) ((DefaultMutableTreeNode) nodo).getUserObject();
                    if (evolucion.getEstado() != 0 && evolucion.getEstado() != 1) {
                        jMenuItem1.setEnabled(false);
                    } else {
                        jMenuItem1.setEnabled(true);
                    }
                    jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
                    evoSeleccion = evolucion;
                }
            }
        }
    }//GEN-LAST:event_jTree1MouseReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        TreePath selPath = jTree1.getSelectionPath();
        if (selPath != null) {
            Object nodo = selPath.getLastPathComponent();
            if (((DefaultMutableTreeNode) nodo).getUserObject() instanceof HcuEvolucion) {
                HcuEvolucion evolucion = (HcuEvolucion) ((DefaultMutableTreeNode) nodo).getUserObject();
                String mensaje;
                int entrada;
                if (evolucion.getEstado() == 3) {
                    mensaje = "Desea anular la Nota de Egreso " + MyDate.yyyyMMddHHmm.format(evolucion.getFechaEvo());
                    entrada = JOptionPane.showConfirmDialog(null, mensaje, "Anular Nota de Egreso", JOptionPane.YES_NO_OPTION);
                } else {
                    mensaje = "Desea anular la Evolución " + MyDate.yyyyMMddHHmm.format(evolucion.getFechaEvo());
                    entrada = JOptionPane.showConfirmDialog(null, mensaje, "Anular Evolución", JOptionPane.YES_NO_OPTION);
                }
                if (entrada == 0) {
                    if (hcuEvolucionJpaController == null) {
                        hcuEvolucionJpaController = new HcuEvolucionJpaController(factory);
                    }
                    try {
                        if (evolucion.getId() != null) {
                            evolucion.setEstado(0);
                            hcuEvolucionJpaController.edit(evolucion);
                            jTree1.removeSelectionPath(selPath);
                            if (jLabel65.getText().equals(MyDate.yyyyMMddHHmm2.format(evolucion.getFechaEvo()))) {
                                jTabbedPane6.removeAll();
                                jButton8.setVisible(true);
                                jButton8.setEnabled(false);
                                jButton10.setEnabled(false);
                                jButton10.setText("S. Vitales");
                                jButton10.setToolTipText("SIGNOS VITALES");
                                jButton2.setEnabled(false);
                                jButton2.setText("Objetivo");
                                jButton2.setToolTipText("NOTAS OBJETIVAS");
                                jButton6.setEnabled(false);
                                jButton6.setText("Analisis");
                                jButton6.setToolTipText("NOTAS DE ANALISIS");
                                jButton4.setEnabled(false);
                                jButton4.setText("Plan");
                                jButton4.setToolTipText("PLAN DE MANEJO");
                                jButton12.setEnabled(false);
                                jButton13.setEnabled(false);
                                jButton5.setEnabled(false);
                                jButton14.setEnabled(true);
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "10131:\n" + ex.getMessage(), Evo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14MouseExited

    private void jButton14MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14MouseMoved

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        notaEgreso();
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged

    }//GEN-LAST:event_jTree1ValueChanged

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (evoSeleccion.getEstado() == 2) {
            this.activarComponentes(evoSeleccion);
            this.jButton13.setEnabled(false);
            this.jButton12.setEnabled(false);
            if (est == 1) {
                this.jButton14.setEnabled(false);
            }
        } else {
            if (evoSeleccion.getEstado() >= 3) {
                this.activarComponentesEgreso(evoSeleccion);
                this.jButton14.setEnabled(false);
                this.jButton1.setEnabled(false);
                if (evoSeleccion.getEstado() == 4) {
                    this.jButton13.setEnabled(false);
                    this.jButton1.setEnabled(false);
                    this.jButton14.setEnabled(false);
                    this.jButton12.setEnabled(false);
                }
            } else {
                this.activarComponentes(evoSeleccion);
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // </editor-fold>
    // <editor-fold desc="Variables declaration - do not modify"> 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    public javax.swing.JTextArea jTextArea10;
    private javax.swing.JTextArea jTextArea11;
    private javax.swing.JTextArea jTextArea12;
    private javax.swing.JTextArea jTextArea13;
    private javax.swing.JTextArea jTextArea14;
    private javax.swing.JTextArea jTextArea15;
    private javax.swing.JTextArea jTextArea16;
    private javax.swing.JTextArea jTextArea17;
    private javax.swing.JTextArea jTextArea19;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea22;
    private javax.swing.JTextArea jTextArea23;
    private javax.swing.JTextArea jTextArea24;
    private javax.swing.JTextArea jTextArea25;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JTextArea jTextArea9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    public javax.swing.JTextField jTextField11;
    public javax.swing.JTextField jTextField12;
    public javax.swing.JTextField jTextField13;
    public javax.swing.JTextField jTextField14;
    public javax.swing.JTextField jTextField15;
    public javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane3;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane4;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane5;
    public javax.swing.JLabel jlbNombrePaciente;
    private javax.swing.JPanel jpAntPersonales;
    private javax.swing.JPanel jpCentro;
    private javax.swing.JPanel jpDiagMedico;
    private javax.swing.JPanel jpEnfActual;
    private javax.swing.JPanel jpExpFisica;
    private javax.swing.JPanel jpMotivoC;
    private javax.swing.JPanel jpPruebasDiag;
    private javax.swing.JPanel jpTratamiento;
    private javax.swing.JTable jtbTratamiento4;
    // End of variables declaration//GEN-END:variables
// </editor-fold>

}
