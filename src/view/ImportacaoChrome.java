/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Util.GetChromeFavorites;
import Util.GetFaviconFromURL;
import control.ControlDB;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.Group;
import model.Link;
import model.Sessao;

/**
 *
 * @author Yuri
 */
public class ImportacaoChrome extends javax.swing.JDialog {

    private DefaultTableModel tableModel;
    private boolean notShowAddedLinks = true;
    private Sessao sessao;

    public ImportacaoChrome(java.awt.Frame parent, boolean modal, Sessao sessao) {
        super(parent, modal);
        initComponents();
        this.sessao = sessao;
        Object[] obj = new Object[3];
        obj[0] = "Nome";
        obj[1] = "URL";
        obj[2] = "Grupo";
        tableModel = new DefaultTableModel(obj, 0) {
            boolean[] canEdit = new boolean[]{true, false, true};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        jTable_Tabela.setModel(tableModel);
        updateLinkList();
    }

    private void updateLinkList() {
        tableModel.setRowCount(0);
        ArrayList<Link> linksDoChrome = GetChromeFavorites.getChromeFav();
        Object[] row = new Object[3];
        int count = 0;
        for (Link link : linksDoChrome) {
            if (!sessao.getLinkList().contains(link) || notShowAddedLinks) {
                row[0] = link.getNome();
                row[1] = link.getUrl();
                row[2] = link.getGrupo();
                tableModel.addRow(row);
                count++;
            }
            boolean contain = false;
            for (int i = 0; i < sessao.getGroups().size(); i++) {
                if (sessao.getGroups().get(i).getNome().equals(link.getGrupo())) {
                    contain = true;
                    break;
                    }
            }
            if (!contain) {
                sessao.addGroup(link.getGrupo());
            }
        }
        jTextField_Count.setText("" + count);
    }
    
    private void importarSelecionados() {
        int[] selectedRows = jTable_Tabela.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            Object nome = jTable_Tabela.getValueAt(selectedRows[i], 0);
            Object url = jTable_Tabela.getValueAt(selectedRows[i], 1);
            Object grupo = jTable_Tabela.getValueAt(selectedRows[i], 2);
            ControlDB.addLink((String) nome, (String) url, (String) grupo);
            Group group = new Group((String) grupo, false);
            if (!sessao.getGroups().contains(group)) {
                sessao.getGroups().add(group);
                ControlDB.addGroup(group);
            }
        }
        sessao.atualizarLinksFromDB();
        sessao.updateBotoesLinks();
        sessao.updateGroupJList();
        updateLinkList();
        GetFaviconFromURL.downloadIcons(sessao);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton_Filtrar = new javax.swing.JButton();
        jButton_SelecionarGrupo = new javax.swing.JButton();
        jButton_SelecionarTodos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Tabela = new javax.swing.JTable();
        jButton_Adicionar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField_Count = new javax.swing.JTextField();
        jCheckBox_notShowRepeat = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(230, 230, 230));

        jButton_Filtrar.setBackground(new java.awt.Color(0, 0, 0));
        jButton_Filtrar.setForeground(new java.awt.Color(204, 204, 204));
        jButton_Filtrar.setText("Filtrar Sites");

        jButton_SelecionarGrupo.setBackground(new java.awt.Color(0, 0, 0));
        jButton_SelecionarGrupo.setForeground(new java.awt.Color(204, 204, 204));
        jButton_SelecionarGrupo.setText("Mudar Grupo");

        jButton_SelecionarTodos.setBackground(new java.awt.Color(0, 0, 0));
        jButton_SelecionarTodos.setForeground(new java.awt.Color(204, 204, 204));
        jButton_SelecionarTodos.setText("Selecionar Todos");
        jButton_SelecionarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SelecionarTodosActionPerformed(evt);
            }
        });

        jTable_Tabela.setAutoCreateRowSorter(true);
        jTable_Tabela.setBackground(new java.awt.Color(0, 0, 0));
        jTable_Tabela.setForeground(new java.awt.Color(0, 255, 153));
        jTable_Tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Link", "URL", "Grupo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_Tabela.setFocusable(false);
        jTable_Tabela.setGridColor(new java.awt.Color(51, 51, 51));
        jTable_Tabela.setSelectionBackground(new java.awt.Color(255, 51, 0));
        jTable_Tabela.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTable_Tabela);

        jButton_Adicionar.setBackground(new java.awt.Color(0, 0, 0));
        jButton_Adicionar.setForeground(new java.awt.Color(204, 204, 204));
        jButton_Adicionar.setText("Importar Selecionados");
        jButton_Adicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AdicionarActionPerformed(evt);
            }
        });

        jLabel1.setBackground(java.awt.Color.black);
        jLabel1.setText("Encontrados:");

        jTextField_Count.setEditable(false);
        jTextField_Count.setBackground(new java.awt.Color(0, 0, 0));
        jTextField_Count.setForeground(new java.awt.Color(255, 51, 0));
        jTextField_Count.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jCheckBox_notShowRepeat.setBackground(new java.awt.Color(230, 230, 230));
        jCheckBox_notShowRepeat.setText("Somente links não adicionados");
        jCheckBox_notShowRepeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_notShowRepeatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_Filtrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_SelecionarGrupo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_SelecionarTodos)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Count, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox_notShowRepeat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Adicionar)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Filtrar)
                    .addComponent(jButton_SelecionarGrupo)
                    .addComponent(jButton_SelecionarTodos)
                    .addComponent(jButton_Adicionar)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_Count, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox_notShowRepeat))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_AdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AdicionarActionPerformed
        // BOTAO IMPORTAR SELECIONADOS
        importarSelecionados();
    }//GEN-LAST:event_jButton_AdicionarActionPerformed

    private void jCheckBox_notShowRepeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_notShowRepeatActionPerformed
        // CHECK links ja adicionados
        if (jCheckBox_notShowRepeat.isSelected()) {
            notShowAddedLinks = false;
        } else {
            notShowAddedLinks = true;
        }
        updateLinkList();
    }//GEN-LAST:event_jCheckBox_notShowRepeatActionPerformed

    private void jButton_SelecionarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SelecionarTodosActionPerformed
        // BOTAO SELECIONAR TODOS
        jTable_Tabela.selectAll();
    }//GEN-LAST:event_jButton_SelecionarTodosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Adicionar;
    private javax.swing.JButton jButton_Filtrar;
    private javax.swing.JButton jButton_SelecionarGrupo;
    private javax.swing.JButton jButton_SelecionarTodos;
    private javax.swing.JCheckBox jCheckBox_notShowRepeat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Tabela;
    private javax.swing.JTextField jTextField_Count;
    // End of variables declaration//GEN-END:variables
}
