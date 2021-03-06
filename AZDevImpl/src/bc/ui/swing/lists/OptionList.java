/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OptionList.java
 *
 * Created on 24/11/2011, 17:55:10
 */
package bc.ui.swing.lists;

import bc.ui.swing.listeners.Listeners;
import bc.ui.swing.listeners.SelectionListener;
import bc.ui.swing.visuals.Visual;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JViewport;

/**
 *
 * @author bennyl
 */
public class OptionList extends javax.swing.JPanel {

    private LinkedHashMap<Object, JRadioButton> items;
    private Object selected;
    private Listeners<SelectionListener> selectionListeners = Listeners.Builder.newInstance(SelectionListener.class); 
    
    /** Creates new form OptionList */
    public OptionList() {
        initComponents();
        JViewport vp = scroll.getViewport();
        vp.setLayout(new BorderLayout());
        vp.removeAll();
        vp.add(jPanel1, BorderLayout.CENTER);
        vp.setOpaque(false);
//        scroll.setBorder(new LineBorder(Color.yellow));
        items = new LinkedHashMap<Object, JRadioButton>();
        selected = null;
    }

    public Listeners<SelectionListener> getSelectionListeners() {
        return selectionListeners;
    }

    public Object getSelected() {
        return selected;
    }

    public void remove(Object item){
        JRadioButton box = items.remove(item);
        buttonGroup1.remove(box);
        jPanel1.remove(box);
    }
    
    public void clear(){
        jPanel1.removeAll();
        while (buttonGroup1.getButtonCount() > 0){
            buttonGroup1.remove(buttonGroup1.getElements().nextElement());
        }
        items.clear();
    }
    
    public void setItems(List<Visual> items){
        for (Visual i : items){
            add(i);
        }
        revalidate();
        repaint();
    }
    
    public void add(final Visual item) {
        
        final JRadioButton radio = new JRadioButton();
        items.put(item, radio);
        buttonGroup1.add(radio);
        //IF IS THE FIRST THEN SET AS SELECTED
        if (items.size() == 1){
            radio.setSelected(true);
            selectionListeners.fire().onSelectionChanged(this, Arrays.asList(item));
        }
        
        radio.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (radio.isSelected()){
                    selected = radio;
                    selectionListeners.fire().onSelectionChanged(OptionList.this, Arrays.asList(item));
                }
            }
        });
        
        radio.setText(item.toString());
        radio.setOpaque(false);
        GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        jPanel1.add(radio, gbc);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        scroll = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        scroll.setBorder(null);
        scroll.setOpaque(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());
        scroll.setViewportView(jPanel1);

        add(scroll, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
    
    public static void main(String[] args){
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        OptionList list = new OptionList();
        jf.setContentPane(list);
//        list.add("test1");
//        list.add("test2");
//        list.add("test3");
        
        list.getSelectionListeners().addListener(new SelectionListener() {

            @Override
            public void onSelectionChanged(Object source, List selectedItems) {
                System.out.println("Selected is " + selectedItems.get(0));
            }
        });
        
        jf.pack();
        jf.setVisible(true);
        
    }
}
