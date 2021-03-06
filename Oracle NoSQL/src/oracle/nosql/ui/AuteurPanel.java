/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql.ui;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import oracle.nosql.daos.AEcritDAO;
import oracle.nosql.daos.AEteEcritDAO;
import oracle.nosql.daos.AuteurDAO;
import oracle.nosql.daos.LivreDAO;
import oracle.nosql.entities.AEcrit;
import oracle.nosql.entities.Auteur;

/**
 *
 * @author aimemath
 */
public class AuteurPanel extends javax.swing.JPanel {

    /**
     * Creates new form LivrePanel
     */
    private AuteurDAO adao;
    private LivreDAO ldao;
    private Auteur auteur;
    private AEcritDAO aedao;
    private AEteEcritDAO aeedao;
        
    public AuteurPanel() {
        initComponents();
        
        jButton3.setVisible(false);
        
        adao = new AuteurDAO();
        ldao = new LivreDAO();
        aedao = new AEcritDAO();
        aeedao = new AEteEcritDAO();
        
        jList1.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Auteur) {
                    // Here value will be of the Type 'CD'
                    ((JLabel) renderer).setText(((Auteur) value).getNom() + " " + ((Auteur) value).getPrenom());
                }
                return renderer;
            }
        });
        jList1.addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()){
                JList source = (JList)event.getSource();
                auteur = (Auteur) source.getSelectedValue();
                if(auteur != null) {
                    jTextFieldID.setText(String.valueOf(auteur.getAuteurId()));
                    jTextFieldPrenom.setText(auteur.getPrenom());
                    jTextFieldNom.setText(auteur.getNom());
                    jTextFieldPhone.setText(auteur.getPhone());
                    jTextFieldAdresse.setText(auteur.getAdresse());
                    jButton1.setText("Modifier");
                    jButton3.setVisible(true);
                }
            }
        });
        
        refreshList();
    }
    
    private void refreshList() {
        DefaultListModel model = new DefaultListModel();
        adao.getLast(50).forEach((a) -> {
            model.addElement(a);
        }); 
        jList1.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldNom = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldPrenom = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldAdresse = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jTextFieldPhone = new javax.swing.JTextField();

        jScrollPane1.setViewportView(jList1);

        jLabel1.setText("Liste des auteurs");

        jLabel2.setText("Ajouter un auteur");

        jLabel3.setText("Nom");

        jLabel4.setText("Prénom");

        jLabel5.setText("Adresse");

        jLabel6.setText("Téléphone");

        jButton1.setText("Ajouter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Annuler");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Id");

        jButton3.setText("Supprimer");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldPrenom, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                .addComponent(jTextFieldAdresse)
                                .addComponent(jTextFieldNom)
                                .addComponent(jTextFieldID)
                                .addComponent(jTextFieldPhone)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1))))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldPrenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldAdresse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Auteur a;
        DefaultListModel lm = (DefaultListModel) jList1.getModel();
        
        if (adao.read(Integer.parseInt(jTextFieldID.getText())) != null) {
            a = adao.update(Integer.parseInt(jTextFieldID.getText()), jTextFieldNom.getText(), jTextFieldPrenom.getText(), jTextFieldAdresse.getText(), jTextFieldPhone.getText());
            
            if (auteur != null && !auteur.getNom().equals(jTextFieldNom.getText())) {
                
                for(AEcrit ae : aedao.read(auteur.getNom())) {
                    aedao.delete(ae.getAuteurNom(), ae.getRang());
                    aedao.create(Integer.parseInt(jTextFieldID.getText()), jTextFieldNom.getText(), ae.getLivreTitre(), ae.getRang());
                }
            }
            
            lm.remove(jList1.getSelectedIndex());
        } else { 
            a = adao.create(Integer.parseInt(jTextFieldID.getText()), jTextFieldNom.getText(), jTextFieldPrenom.getText(), jTextFieldAdresse.getText(), jTextFieldPhone.getText());
        }
        
        lm.addElement(a);
        jList1.setModel(lm); 
        clear();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultListModel lm = (DefaultListModel) jList1.getModel();
        
        for(AEcrit ae : aedao.read(auteur.getNom())) {
            //TODO long -> ajouter l'id de l'auteur dans la relation AEcrit
            //ldao.delete(ldao.read(ae.getLivreTitre()).getLivreId());
            ldao.delete(ae.getIdAuteur());
            
            aeedao.delete(ae.getLivreTitre());
            aedao.delete(auteur.getNom(), ae.getRang());
        }
        adao.delete(auteur.getAuteurId());
        
        lm.remove(jList1.getSelectedIndex());
        jList1.setModel(lm);
        clear();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void clear() {
        jList1.clearSelection();
        jTextFieldNom.setText("");
        jTextFieldPrenom.setText("");
        jTextFieldAdresse.setText("");
        jTextFieldPhone.setText("");
        jTextFieldID.setText("");
        jButton1.setText("Ajouter");
        jButton3.setVisible(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldAdresse;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldNom;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldPrenom;
    // End of variables declaration//GEN-END:variables

}
