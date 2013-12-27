/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionurgencia.evolucion;

import atencionurgencia.AtencionUrgencia;
import entidades.HcuEvolucion;
import java.text.DecimalFormat;
import javax.persistence.EntityManagerFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import jpa.HcuEvolucionJpaController;
import tools.Funciones;

/**
 *
 * @author Alvaro Monsalve
 */
public class newEvo extends javax.swing.JPanel {
    private HcuEvolucionJpaController jpaController=null;
    private HcuEvolucion evolucion;
    public String titleOther = "OTROS";
    
    private Integer glasgow1=0,glasgow2=0,glasgow3=0;
    //Glasgow Lactante entre 0 y 2 años
    private final String GlasgowLact1[]={"Espontánea","Al hablarle","Al dolor","Ninguna"};
    private final String GlasgowLact2[]={"Arrullos, balbuceos","Llanto irritable","Llanto al dolor","Quejidos al dolor","Ninguna"};
    private final String GlasgowLact3[]={"Movimientos espontaneos","Retira al tocar","Retira al dolor","Flexión anormal","Extensión anormal","Ninguna"};
    //Glasgow pre-escolar entre 2 años y 6 años
    private final String GlasgowPre1[]={"Espontánea","A una orden verbal","Al dolor","Ninguna"};
    private final String GlasgowPre2[]={"Palabras apropiadas","Palabras inadecuadas","Llanto o gritos","Quejidos","Ninguna"};
    private final String GlasgowPre3[]={"Obedece órdenes","Localiza el dolor","Flexión y retira el miembro","Flexión anormal","Extensión anormal","Ninguna"};
    //Glasgow escolar entre 6 a 12 años
    private final String GlasgowEsc1[]={"Espontánea","A una orden","Al dolor","Ninguna"};
    private final String GlasgowEsc2[]={"Orientado y conversa","Desorientado","Palabras inadecuadas","Sonidos incomprensibles","Ninguna"};
    private final String GlasgowEsc3[]={"Obedece órdenes","Localiza el dolor","Flexión y retira el miembro","Flexión anormal","Extensión anormal","Ninguna"};
    //Glasgow de 12 en adelante
    private final String GlasgowAdult1[]={"Espontánea","Al estimulo verbal","Al recibir un estimulo doloroso","No responde"};
    private final String GlasgowAdult2[]={"Orientado","Desorientado y conversa","Palabras inapropiadas","Sonidos incomprensibles","No responde"};    
    private final String GlasgowAdult3[]={"Obedece órdenes","Localiza estímulos","Retira ante estímulos","Respuesta en flexión","Respuesta en extensión","No responde"};
    
    private Integer edad=0;//lactante menor de 2 años
    /**
     * Creates new form newEvo
     */
    public newEvo() {
        initComponents();
        initComponent2();
    }
    
    private void initComponent2(){
        if(edad < 2){
            jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(GlasgowLact1));
            jComboBox2.setSelectedIndex(-1);
            jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(GlasgowLact2));
            jComboBox3.setSelectedIndex(-1);
            jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(GlasgowLact3));
            jComboBox4.setSelectedIndex(-1);
        }else if(edad>=2 && edad <6){
            jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(GlasgowPre1));
            jComboBox2.setSelectedIndex(-1);
            jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(GlasgowPre2));
            jComboBox3.setSelectedIndex(-1);
            jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(GlasgowPre3));
            jComboBox4.setSelectedIndex(-1);
        }else if(edad>=6 && edad <12){
            jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(GlasgowEsc1));
            jComboBox2.setSelectedIndex(-1);
            jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(GlasgowEsc2));
            jComboBox3.setSelectedIndex(-1);
            jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(GlasgowEsc3));
            jComboBox4.setSelectedIndex(-1);
        }else{
            jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(GlasgowAdult1));
            jComboBox2.setSelectedIndex(-1);
            jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(GlasgowAdult2));
            jComboBox3.setSelectedIndex(-1);
            jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(GlasgowAdult3));
            jComboBox4.setSelectedIndex(-1);
        }
        jTextField12.setText(null);
    }
    
    private boolean validAll(){
        boolean retorno=true;
//        if(glasgow1==0 && glasgow2==0 && glasgow2==0 && retorno==true){//glasgow
//            JOptionPane.showMessageDialog(this, "El valor de la escala Glasgow es mas bajo que 3");
//            ((JTabbedPane)this.getParent()).setSelectedComponent(this);
//            this.jComboBox2.requestFocus();
//            retorno = false;
//        }
//        if(jComboBox1.getSelectedIndex()<0 && retorno==true){
//            JOptionPane.showMessageDialog(this, "El estado de conciencia no es valido");
//            ((JTabbedPane)this.getParent()).setSelectedComponent(this);
//            jComboBox1.requestFocus();
//        }
        if(!jTextField1.getText().equals("") && retorno==true){//SatO2
            try {   
                Integer.parseInt(jTextField1.getText());
                if(jTextField1.getText().length()>3 || (Integer.parseInt(jTextField1.getText())>100 || Integer.parseInt(jTextField1.getText())<=0)){
                    JOptionPane.showMessageDialog(this, "El valor de la saturacion de Oxigeno no es valido");
                    ((JTabbedPane)this.getParent()).setSelectedComponent(this);
                    jTextField1.requestFocus();
                    retorno = false;
                }
            } catch (NumberFormatException e) {             
                JOptionPane.showMessageDialog(this, "El valor de la saturacion de Oxigeno no es valido\n"+e.getMessage());
                retorno = false;
            }
        }
        if(!jTextField3.getText().equals("") && retorno==true){//FC
            try {
                Integer.parseInt(jTextField3.getText());
                if(jTextField3.getText().length()>3){
                    JOptionPane.showMessageDialog(this, "El valor de la frecuencia cardiaca no es valido");
                    ((JTabbedPane)this.getParent()).setSelectedComponent(this);
                    jTextField3.requestFocus();
                    retorno = false;
                }
            } catch (NumberFormatException e) {//FC
                JOptionPane.showMessageDialog(this, "El valor de la frecuencia cardiaca no es valido.\n"+e.getMessage());
                retorno = false;
            }
        }
        if(!jTextField8.getText().equals("") && retorno==true){//T
            try {
                Float val = Float.parseFloat(jTextField8.getText().replace(",","."));
                if(jTextField8.getText().length()>6 || (val > 42f || val < 34f)){
                    JOptionPane.showMessageDialog(this, "El valor de la Temperatura no es valido");
                    ((JTabbedPane)this.getParent()).setSelectedComponent(this);
                    jTextField8.requestFocus();
                    retorno = false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El valor de la Temperatura  no es valido.\n"+e.getMessage());
                retorno = false;
            }
        }
        if(!jTextField7.getText().equals("") && retorno==true){//TaS
            try {
                Integer.parseInt(jTextField7.getText());
                if(jTextField7.getText().length()>3 || (Integer.parseInt(jTextField7.getText())>300 || Integer.parseInt(jTextField7.getText())<0)){
                    JOptionPane.showMessageDialog(this, "El valor de la tensión arterial sistólica no es valido");
                    ((JTabbedPane)this.getParent()).setSelectedComponent(this);
                    jTextField7.requestFocus();
                    retorno = false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El valor de la tensión arterial sistólica no es valido.\n");
                retorno = false;
            }
        }
        if(!jTextField6.getText().equals("") && retorno==true){//TaD
            try{
                Integer.parseInt(jTextField6.getText());
                if(jTextField6.getText().length()>3 || (Integer.parseInt(jTextField6.getText())>300 || Integer.parseInt(jTextField6.getText())<0)){
                    JOptionPane.showMessageDialog(this, "El valor de la tensión arterial diastólica no es valido");
                    ((JTabbedPane)this.getParent()).setSelectedComponent(this);
                    jTextField6.requestFocus();
                    retorno = false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El valor de la tensión arterial diastólica no es valido");
                retorno = false;
            }
        }
        return retorno;
    }
    
    private void setTAM(){
        if(!jTextField6.getText().equals("") && !jTextField7.getText().equals("")){
            DecimalFormat RedondearADos = new DecimalFormat("###.##");
            int PD = Integer.parseInt(jTextField6.getText());
            int PS = Integer.parseInt(jTextField7.getText());
            double TAM = (((double)PD*2)+(double)PS)/3;
            if(jTextArea2.getText().isEmpty()){
                    jTextArea2.setText("TAM: "+RedondearADos.format(TAM));
            }else{
                jTextArea2.setText("TAM: "+RedondearADos.format(TAM)+"\n"+jTextArea2.getText());
                
            }
            
        }
    }
    
    private void setAporte(Object obj,int tipo){
        if(tipo==0){//SatO2
            Integer val = Integer.parseInt(((javax.swing.JTextField)obj).getText());
            if(val>95){
                jLabel5.setVisible(true);
                jLabel2.setVisible(true);
                jLabel5.setText("VALORES NORMALES");
            }else if(val <= 95 && val > 90){
                jLabel5.setVisible(true);
                jLabel2.setVisible(true);
                jLabel5.setText("TRATAMIENTO INMEDIATO Y MONITORIZACIÓN DE LA RESPUESTA AL MISMO");
            }else if(val <=90 && val >80){
                jLabel5.setVisible(true);
                jLabel2.setVisible(true);
                jLabel5.setText("HIPOXIA SEVERA");
            }else{
                jLabel5.setVisible(true);
                jLabel2.setVisible(true);
                jLabel5.setText("VALORAR INTUBACIÓN Y VENTILACIÓN MECÁNICA");
            }
        }
    }
    
    private String scalaGlasgow(Integer val){
        if(val<9){
            return "Glasgow grave ["+val.toString()+"]";
        }else if(val>=9 && val<=12){
            return "Glasgow moderado ["+val.toString()+"]";
        }else{
            return "Glasgow leve ["+val.toString()+"]";
        }
    }
    
    public void setEvolucion(HcuEvolucion evolucion){
        this.evolucion=evolucion;
        String edad2[]=evolucion.getIdInfoHistoriac().getIdInfoAdmision().getEdad().split(" ");
        edad=Integer.parseInt(edad2[0].toString());
        initComponent2();
        if(evolucion.getConciencia()!=null)  jComboBox1.setSelectedIndex(evolucion.getConciencia());
        if(evolucion.getAperturaOcular()!=null)  jComboBox2.setSelectedIndex(evolucion.getAperturaOcular());
        if(evolucion.getRespuestaVerbal()!=null) jComboBox3.setSelectedIndex(evolucion.getRespuestaVerbal());
        if(evolucion.getRespuestaMotora()!=null) jComboBox4.setSelectedIndex(evolucion.getRespuestaMotora());
        if(evolucion.getSao2()!=null) jTextField1.setText(evolucion.getSao2().toString());
        if(evolucion.getSao2()!=null) this.setAporte(jTextField1,0);
        if(evolucion.getFc()!=null) jTextField3.setText(evolucion.getFc().toString());
        if(evolucion.getTemperatura()!=null) jTextField8.setText(evolucion.getTemperatura().toString());
        if(evolucion.getFr()!=null) jTextField4.setText(evolucion.getFr().toString());
        if(evolucion.getTas()!=null) jTextField7.setText(evolucion.getTas().toString());
        if(evolucion.getTad()!=null) jTextField6.setText(evolucion.getTad().toString());
        if(evolucion.getOtrossignos()!=null) jTextArea2.setText(evolucion.getOtrossignos());
    }

    public boolean saveChanged(EntityManagerFactory factory, HcuEvolucion hcuEvolucion){
        boolean sigue=false;
        if(jpaController==null) jpaController = new HcuEvolucionJpaController(factory);
        HcuEvolucion editEvolucion = hcuEvolucion;
        if(validAll() && editEvolucion.getEstado()!=2){            
            if(jComboBox1.getSelectedIndex()>-1) editEvolucion.setConciencia((short) jComboBox1.getSelectedIndex());
            if(jComboBox2.getSelectedIndex()>-1) editEvolucion.setAperturaOcular((short)jComboBox2.getSelectedIndex());
            if(jComboBox3.getSelectedIndex()>-1) editEvolucion.setRespuestaVerbal((short)jComboBox3.getSelectedIndex());
            if(jComboBox4.getSelectedIndex()>-1) editEvolucion.setRespuestaMotora((short)jComboBox4.getSelectedIndex());
            if(!jTextField1.getText().isEmpty()) editEvolucion.setSao2(Short.parseShort(jTextField1.getText().toString()));
            if(!jTextField3.getText().isEmpty()) editEvolucion.setFc(Short.parseShort(jTextField3.getText().toString()));
            if(!jTextField8.getText().isEmpty()) editEvolucion.setTemperatura(Float.parseFloat(jTextField8.getText().toString().replace(",", ".")));
            if(!jTextField4.getText().isEmpty()) editEvolucion.setFr(Float.parseFloat(jTextField4.getText().toString().replace(",", ".")));
            if(!jTextField7.getText().isEmpty()) editEvolucion.setTas(Short.parseShort(jTextField7.getText().toString()));
            if(!jTextField6.getText().isEmpty()) editEvolucion.setTad(Short.parseShort(jTextField6.getText().toString()));
            if(!jTextArea2.getText().isEmpty()) editEvolucion.setOtrossignos(jTextArea2.getText().toUpperCase());
            editEvolucion.setUsuario(AtencionUrgencia.configdecripcionlogin.getId());
            try {
                if(editEvolucion.getId()==null){
                    jpaController.create(editEvolucion);
                    sigue =true;
                }else{
                    jpaController.edit(editEvolucion);
                    sigue = true;
                }            
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "10130:\n"+ex.getMessage(), newEvo.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            }
         }else if(editEvolucion.getEstado()==2){
            JOptionPane.showMessageDialog(this, "La Evolución fue finalizada y no puede ser modificada");
        }
        return sigue;
    }
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel12 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
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
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jMenuItem1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calculator_add.png"))); // NOI18N
        jMenuItem1.setText("Calcular Tension Arteria Media (TAM)");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setMaximumSize(new java.awt.Dimension(603, 386));
        setMinimumSize(new java.awt.Dimension(603, 386));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(603, 386));

        jPanel12.setMaximumSize(new java.awt.Dimension(603, 386));
        jPanel12.setMinimumSize(new java.awt.Dimension(603, 386));
        jPanel12.setOpaque(false);
        jPanel12.setPreferredSize(new java.awt.Dimension(603, 386));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("SatO2:");
        jLabel1.setToolTipText("Saturacion de Oxigeno");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 102, 255));
        jTextField1.setToolTipText("Saturacion de Oxigeno");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setText("FC:");
        jLabel3.setToolTipText("Frecuencia Cardiaca (Pulso)");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel4.setText("FR:");
        jLabel4.setToolTipText("Frecuencia Respiratoria");

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(0, 102, 255));
        jTextField3.setToolTipText("Frecuencia Cardiaca (Pulso)");
        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField3FocusLost(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(0, 102, 255));
        jTextField4.setToolTipText("Frecuencia Respiratoria");
        jTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField4FocusLost(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel6.setText("TaD:");
        jLabel6.setToolTipText("Tensión Arterial Diastólica");

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(0, 102, 255));
        jTextField6.setToolTipText("Tensión Arterial Diastólica");
        jTextField6.setComponentPopupMenu(jPopupMenu1);
        jTextField6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField6FocusLost(evt);
            }
        });
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6KeyTyped(evt);
            }
        });

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(0, 102, 255));
        jTextField7.setToolTipText("Tensión Arterial Sistólica");
        jTextField7.setComponentPopupMenu(jPopupMenu1);
        jTextField7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField7FocusLost(evt);
            }
        });
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField7KeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("TaS:");
        jLabel7.setToolTipText("Tensión Arterial Sistólica");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel8.setText("T:");
        jLabel8.setToolTipText("Temperatura [34-42]");

        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(0, 102, 255));
        jTextField8.setToolTipText("Temperatura [34-42]");
        jTextField8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField8FocusLost(evt);
            }
        });
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField8KeyTyped(evt);
            }
        });

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "OBSERVACIONES GENERALES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel25.setOpaque(false);

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(3);
        jScrollPane5.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel10.setText("%");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel12.setText("ppm");
        jLabel12.setToolTipText("Pulsaciones por Minuto");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel13.setText("resp/min");
        jLabel13.setToolTipText("Respiraciones por Minuto");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel14.setText("°C");
        jLabel14.setToolTipText("Grados Centigrados");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel17.setText("mmHg");
        jLabel17.setToolTipText("Milímetros de Mercurio");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel9.setText("ESTADO DE CONCIENCIA");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALERTA", "AGITACION PSICOMOTORA", "SOMNOLENCIA", "OBNUBILACION", "ESTUPOR", "COMA", "ESTADO VEGETATIVO PERSISTENTE", "SÍNDROME DE ENCLAUSTRAMIENTO", "MUERTE CEREBRAL" }));
        jComboBox1.setSelectedIndex(-1);
        jComboBox1.setFocusable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GLASGOW", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));
        jPanel1.setOpaque(false);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Espontánea", "A la voz", "Al dolor", "Sin apertura ocular" }));
        jComboBox2.setSelectedIndex(-1);
        jComboBox2.setFocusable(false);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel23.setText("Apertura ocular:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel24.setText("Respuesta verbal:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Orientado", "Confuso", "Palabras", "Sonidos", "Sin respuesta verbal" }));
        jComboBox3.setSelectedIndex(-1);
        jComboBox3.setFocusable(false);
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel25.setText("Respuesta motora:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Obedece órdenes", "Localiza estímulos", "Retira ante estímulos", "Respuesta en flexión", "Respuesta en extensión", "Sin respuesta motora" }));
        jComboBox4.setSelectedIndex(-1);
        jComboBox4.setFocusable(false);
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel22.setText("<html><a href=>ESCALA GLASGOW</html>");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        jTextField12.setEditable(false);
        jTextField12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(0, 102, 255));
        jTextField12.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField12.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel18.setText("mmHg");
        jLabel18.setToolTipText("Milímetros de Mercurio");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "APORTE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));
        jPanel2.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel2.setText("SATURACION DE OXIGENO:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setMaximumSize(new java.awt.Dimension(400, 10));
        jLabel5.setMinimumSize(new java.awt.Dimension(400, 10));
        jLabel5.setPreferredSize(new java.awt.Dimension(400, 10));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jLabel2.setVisible(false);
        jLabel5.setVisible(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)))
                                .addGap(5, 5, 5)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField7)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField6)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel6)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        switch(((JComboBox)evt.getSource()).getSelectedIndex()){
            case -1:
                glasgow1 = 0;              
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 0:
                glasgow1 = 4;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 1:
                glasgow1 = 3;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 2:
                glasgow1 = 2;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 3:
                glasgow1 = 1;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        switch(((JComboBox)evt.getSource()).getSelectedIndex()){
            case -1:
                glasgow2 = 0;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 0:
                glasgow2 = 5;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 1:
                glasgow2 = 4;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 2:
                glasgow2 = 3;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 3:
                glasgow2 = 2;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 4:
                glasgow2 = 1;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        switch(((JComboBox)evt.getSource()).getSelectedIndex()){
            case -1:
                glasgow3 = 0;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 0:
                glasgow3 = 6;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 1:
                glasgow3 = 5;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 2:
                glasgow3 = 4;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 3:
                glasgow3 = 3;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 4:
                glasgow3 = 2;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
            case 5:
                glasgow3 = 1;
                jTextField12.setText(scalaGlasgow(glasgow1+glasgow2+glasgow3));
                break; 
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        if(((javax.swing.JTextField)evt.getSource()).getText().length()==3){
            evt.consume();
        }else{
            if(((javax.swing.JTextField)evt.getSource()).getText().length()>3){
                ((javax.swing.JTextField)evt.getSource()).setText("");
                jLabel5.setText("");
                jLabel5.setVisible(false);
                jLabel2.setVisible(false);
                evt.consume();
            }else{
                char teclaPulsada = evt.getKeyChar();
                if (!Character.isDigit(teclaPulsada)) {
                    evt.consume();
                }
            }
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        try {
            Integer.parseInt(((javax.swing.JTextField)evt.getSource()).getText());
            this.setAporte(evt.getSource(),0);
        } catch (NumberFormatException e){
                ((javax.swing.JTextField)evt.getSource()).setText("");
                jLabel5.setText("");
                jLabel5.setVisible(false);
                jLabel2.setVisible(false);
        }
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusLost
        try {
            Integer.parseInt(((javax.swing.JTextField)evt.getSource()).getText());
        } catch (NumberFormatException e){
                ((javax.swing.JTextField)evt.getSource()).setText("");
        }
    }//GEN-LAST:event_jTextField3FocusLost

    private void jTextField4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusLost
        ((javax.swing.JTextField)evt.getSource()).setText(Funciones.FormatDecimal(((javax.swing.JTextField)evt.getSource()).getText()));
    }//GEN-LAST:event_jTextField4FocusLost

    private void jTextField7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusLost
        try {
            Integer.parseInt(((javax.swing.JTextField)evt.getSource()).getText());
        } catch (NumberFormatException e){
                ((javax.swing.JTextField)evt.getSource()).setText("");
        }
    }//GEN-LAST:event_jTextField7FocusLost

    private void jTextField6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusLost
        try {
            Integer.parseInt(((javax.swing.JTextField)evt.getSource()).getText());
        } catch (NumberFormatException e){
                ((javax.swing.JTextField)evt.getSource()).setText("");
        }
    }//GEN-LAST:event_jTextField6FocusLost

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        if(((javax.swing.JTextField)evt.getSource()).getText().length()==3){
            evt.consume();
        }else{
            if(((javax.swing.JTextField)evt.getSource()).getText().length()>3){
                ((javax.swing.JTextField)evt.getSource()).setText("");
                evt.consume();
            }else{
                char teclaPulsada = evt.getKeyChar();
                if (!Character.isDigit(teclaPulsada)) {
                    evt.consume();
                }
            }
        }
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTextField6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyTyped
        if(((javax.swing.JTextField)evt.getSource()).getText().length()==3){
            evt.consume();
        }else{
            if(((javax.swing.JTextField)evt.getSource()).getText().length()>3){
                ((javax.swing.JTextField)evt.getSource()).setText("");
                evt.consume();
            }else{
                char teclaPulsada = evt.getKeyChar();
                if (!Character.isDigit(teclaPulsada)) {
                    evt.consume();
                }
            }
        }
    }//GEN-LAST:event_jTextField6KeyTyped

    private void jTextField7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyTyped
        if(((javax.swing.JTextField)evt.getSource()).getText().length()==3){
            evt.consume();
        }else{
            if(((javax.swing.JTextField)evt.getSource()).getText().length()>3){
                ((javax.swing.JTextField)evt.getSource()).setText("");
                evt.consume();
            }else{
                char teclaPulsada = evt.getKeyChar();
                if (!Character.isDigit(teclaPulsada)) {
                    evt.consume();
                }
            }
        }
    }//GEN-LAST:event_jTextField7KeyTyped

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
        if(((javax.swing.JTextField)evt.getSource()).getText().length()==6){
            evt.consume();
        }else{
            if(((javax.swing.JTextField)evt.getSource()).getText().length()>6){
                ((javax.swing.JTextField)evt.getSource()).setText("");
                evt.consume();
            }
        }
    }//GEN-LAST:event_jTextField4KeyTyped

    private void jTextField8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField8FocusLost
        ((javax.swing.JTextField)evt.getSource()).setText(Funciones.FormatDecimal(((javax.swing.JTextField)evt.getSource()).getText()));
    }//GEN-LAST:event_jTextField8FocusLost

    private void jTextField8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyTyped
        if(((javax.swing.JTextField)evt.getSource()).getText().length()==6){
            evt.consume();
        }else{
            if(((javax.swing.JTextField)evt.getSource()).getText().length()>6){
                ((javax.swing.JTextField)evt.getSource()).setText("");
                evt.consume();
            }
        }
    }//GEN-LAST:event_jTextField8KeyTyped

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
//        validAll();
        intGlasgow glas = new intGlasgow(null, true);
        glas.setLocationRelativeTo(null);
        glas.setVisible(true);
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        setTAM();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
