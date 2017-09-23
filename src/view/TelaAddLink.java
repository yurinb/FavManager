/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Util.GetCopyStringContent;
import Util.GetFaviconFromURL;
import Util.GetLinkSiteName;
import control.ControlDB;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.Sessao;

/**
 *
 * @author Yuri
 */
public class TelaAddLink extends javax.swing.JDialog {

    private Sessao sessao;
    private DefaultComboBoxModel comboBoxModel;

    public TelaAddLink(java.awt.Frame parent, boolean modal, Sessao sessao, TelaPrincipal telaPrincipal) {
        super(parent, modal);
        initComponents();
        this.sessao = sessao;
        comboBoxModel = new DefaultComboBoxModel();
        jComboBox_GRUPO.setModel(comboBoxModel);
        for (int i = 0; i < sessao.getGroups().size(); i++) {
            comboBoxModel.addElement(sessao.getGroups().get(i).getNome());
        }
        int index = telaPrincipal.getjList_Groups().getSelectedIndex();
        if (index > 0) {
            jComboBox_GRUPO.setSelectedIndex(index);
        }
        try {
            String copiedSite = GetCopyStringContent.getClipboardContents();
            jTextField_URL.setText(copiedSite);
            jTextField_NOME.setText(GetLinkSiteName.getSiteName(copiedSite));
        } catch (Exception e) {
//            System.out.println("paste fail");
        }
    }

    private void updateList() {
        comboBoxModel.removeAllElements();
        for (int i = 0; i < sessao.getGroups().size(); i++) {
            comboBoxModel.addElement(sessao.getGroups().get(i).getNome());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField_URL = new javax.swing.JTextField();
        jButton_ADD = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jTextField_NOME = new javax.swing.JTextField();
        jComboBox_GRUPO = new javax.swing.JComboBox<>();
        jButton_addGroup = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 102));
        jLabel1.setText("Adicionar Link");

        jButton_ADD.setText("Adicionar");
        jButton_ADD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ADDActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 51, 0));
        jLabel2.setText("*URL:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nome:");

        jComboBox_GRUPO.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FACULDADE", "PESSOAL", "MUSICA", "MATERIAL ESTUDO" }));
        jComboBox_GRUPO.setMaximumSize(new java.awt.Dimension(118, 20));
        jComboBox_GRUPO.setName(""); // NOI18N
        jComboBox_GRUPO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_GRUPOActionPerformed(evt);
            }
        });

        jButton_addGroup.setText("+");
        jButton_addGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addGroupActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 0, 153));
        jButton1.setForeground(new java.awt.Color(204, 204, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Chrome_32px.png"))); // NOI18N
        jButton1.setText("Importar do Chrome");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Colar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Limpar");
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_ADD, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel2)
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton_addGroup)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox_GRUPO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_NOME, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField_URL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_URL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField_NOME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_GRUPO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_addGroup))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_ADD, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ADDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ADDActionPerformed
        //BOTAO ADICIONAR
        ControlDB.addLink(jTextField_NOME.getText(), jTextField_URL.getText(), jComboBox_GRUPO.getItemAt(jComboBox_GRUPO.getSelectedIndex()));
        GetFaviconFromURL.downloadIcons(sessao);
        sessao.updateShowingLinks(sessao.getShowingGroup());
        System.out.println(sessao.getShowingGroup());
        dispose();

    }//GEN-LAST:event_jButton_ADDActionPerformed

    private void jComboBox_GRUPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_GRUPOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_GRUPOActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // BOTAO IMPORTAR CHROME
        new ImportacaoChrome(null, true, sessao).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton_addGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addGroupActionPerformed
        // BOTAO +
        try {
            String novoGrupo = JOptionPane.showInputDialog("Novo grupo:");
            if (novoGrupo != null) {
                sessao.addGroup(novoGrupo);
            }
        } catch (Exception e) {
            JOptionPane.showInputDialog("nome inválido.");
        }
        updateList();
    }//GEN-LAST:event_jButton_addGroupActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // BOTAO LIMPAR
        jTextField_URL.setText("");
        jTextField_NOME.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // BOTAO COLAR
        try {
            String copiedSite = GetCopyStringContent.getClipboardContents();
            jTextField_URL.setText(copiedSite);
            jTextField_NOME.setText(GetLinkSiteName.getSiteName(copiedSite));
        } catch (Exception e) {
//            System.out.println("paste fail");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton_ADD;
    private javax.swing.JButton jButton_addGroup;
    private javax.swing.JComboBox<String> jComboBox_GRUPO;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField_NOME;
    private javax.swing.JTextField jTextField_URL;
    // End of variables declaration//GEN-END:variables
}
