/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Util.ColoredJList;
import Util.GetFaviconFromURL;
import control.ConnectDB;
import control.ControlDB;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.Sessao;

/**
 *
 * @author Yuri
 */
public class TelaPrincipal extends javax.swing.JFrame {

    private Connection conn;
    private List<JButton> botoesLink = new ArrayList<>();
    private Sessao sessao;
    private ImageIcon chromeIcon = new ImageIcon(TelaPrincipal.class.getResource("/resources/icons8_Chrome_32px.png"));
    private DefaultListModel listModel = new DefaultListModel();
    private boolean modoSelecaoOn;
    private Boolean[] selected = new Boolean[10];

    public TelaPrincipal() {
        initComponents();
        init();
        for (int i = 0; i < sessao.getGroups().size(); i++) {
            listModel.addElement(sessao.getGroups().get(i).getNome());
        }
        jList_Groups.setModel(listModel);
        jList_Groups.setCellRenderer(new ColoredJList(sessao, this, this.jList_Groups));

        //muda links conforme clica nos grupos:
//        jList_Groups.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                sessao.setShowingLinks(jList_Groups.getModel().getElementAt(jList_Groups.getSelectedIndex()));
//            }
//        });
    }

    private void init() {
        this.conn = ConnectDB.connectionConnect();
        for (int i = 0; i < 1; i++) {
            if (conn == null) {
                try {
                    new ConnectDB().startDB();
                    this.conn = ConnectDB.connectionConnect();
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "FALHA AO CONECTAR COM BANCO DE DADOS");
                }
            } else {
                break;
            }
        }
        botoesLink.add(jButton_LINK1);
        botoesLink.add(jButton_LINK2);
        botoesLink.add(jButton_LINK3);
        botoesLink.add(jButton_LINK4);
        botoesLink.add(jButton_LINK5);
        botoesLink.add(jButton_LINK6);
        botoesLink.add(jButton_LINK7);
        botoesLink.add(jButton_LINK8);
        botoesLink.add(jButton_LINK9);
        this.sessao = new Sessao(this);
        sessao.updateShowingLinks("home");
        GetFaviconFromURL.downloadIcons(sessao);
        for (int i = 0; i < 10; i++) {
            selected[i] = new Boolean(false);
        }
        linkButtonsEffect();
    }

    private void linkButtonsEffect() {
        //LINKS BOTOES
        Color cor = Color.decode("0xC90000");
        //Color cor = Color.WHITE;
        jButton_LINK1.addMouseListener(new MouseListenerButtons(jButton_LINK1, cor, this, 0));
        jButton_LINK2.addMouseListener(new MouseListenerButtons(jButton_LINK2, cor, this, 1));
        jButton_LINK3.addMouseListener(new MouseListenerButtons(jButton_LINK3, cor, this, 2));
        jButton_LINK4.addMouseListener(new MouseListenerButtons(jButton_LINK4, cor, this, 3));
        jButton_LINK5.addMouseListener(new MouseListenerButtons(jButton_LINK5, cor, this, 4));
        jButton_LINK6.addMouseListener(new MouseListenerButtons(jButton_LINK6, cor, this, 5));
        jButton_LINK7.addMouseListener(new MouseListenerButtons(jButton_LINK7, cor, this, 6));
        jButton_LINK8.addMouseListener(new MouseListenerButtons(jButton_LINK8, cor, this, 7));
        jButton_LINK9.addMouseListener(new MouseListenerButtons(jButton_LINK9, cor, this, 8));
        // <<  >>
        URL proximoIcon2 = getClass().getResource("/resources/icons8_More_Than_2_24px_b.png");
        jLabel_PROXIMO.addMouseListener(new MouseListenerJLabel(proximoIcon2, jLabel_PROXIMO));
        URL voltarIcon2 = getClass().getResource("/resources/icons8_More_Than_2_24px_b2.png");
        jLabel_VOLTAR.addMouseListener(new MouseListenerJLabel(voltarIcon2, jLabel_VOLTAR));
    }

    private void linkButtonsAction(int botaoIndex) {
        if (modoSelecaoOn) {
            try {
                if (sessao.getShowingLinks().get(sessao.getIndexInicial() + (botaoIndex - 1)).isSelected()) {
                    selected[botaoIndex - 1] = false;
                    sessao.getShowingLinks().get(sessao.getIndexInicial() + (botaoIndex - 1)).setIsSelected(false);
                } else {
                    selected[botaoIndex - 1] = true;
                    sessao.getShowingLinks().get(sessao.getIndexInicial() + (botaoIndex - 1)).setIsSelected(true);
                }
                sessao.updateBotoesLinks();

            } catch (Exception e) {
                System.out.println("BOTAO DESABILITADO");
            }
        } else {
            try {
                String urlx = null;
                try {
                    urlx = sessao.getShowingLinks().get((sessao.getIndexInicial() + (botaoIndex - 1))).getUrl();
                } catch (Exception e) {
                    System.out.println("BOTAO DESABILITADO");
                    return;
                }
                URL url = null;
                if (urlx.substring(0, 5).equals("https") || urlx.substring(0, 5).equals("http")) {
                    url = new URL(urlx);
                } else {
                    url = new URL("https://" + urlx);
                }
                try {
                    URI uri = url.toURI();
                    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                        try {
                            desktop.browse(uri);
                        } catch (IOException ex) {
                            System.out.println("deu pau no terceiro try");
                        }
                    }
                } catch (URISyntaxException ex) {
                    System.out.println("deu pau no segundo try");
                }
                sessao.updateBotoesLinks();
            } catch (MalformedURLException ex) {
                System.out.println("ba nem inicio");
            }
        }
    }

    public void mudarCorLinkBotao(int index, Color color) {
        if (index == 1) {
            jButton_LINK1.setForeground(color);
        }
        if (index == 2) {
            jButton_LINK2.setForeground(color);
        }
        if (index == 3) {
            jButton_LINK3.setForeground(color);
        }
        if (index == 4) {
            jButton_LINK4.setForeground(color);
        }
        if (index == 5) {
            jButton_LINK5.setForeground(color);
        }
        if (index == 6) {
            jButton_LINK6.setForeground(color);
        }
        if (index == 7) {
            jButton_LINK7.setForeground(color);
        }
        if (index == 8) {
            jButton_LINK8.setForeground(color);
        }
        if (index == 9) {
            jButton_LINK9.setForeground(color);
        }
    }

    private void excluir() {
        int x = jList_Groups.getSelectedIndex();
        int s = 1;
        if (x == 0) {
            s = 0;
        }
        if (x >= 0 && !modoSelecaoOn) {
            ControlDB.removeGroup(sessao.getGroups().get(x));
            sessao.getGroups().remove(x);
            sessao.updateGroupJList();
            if (s == 0) {
                sessao.updateShowingLinks("home");
            } else {
                jList_Groups.setSelectedIndex(Math.abs(x - s));
                sessao.updateShowingLinks(jList_Groups.getModel().getElementAt(Math.abs(x - s)));
            }
        } else if (modoSelecaoOn) {
            JOptionPane.showMessageDialog(null, sessao.getShowingLinks().size());
            for (int i = 0; i < sessao.getShowingLinks().size(); i++) {
                if (sessao.getShowingLinks().get(i).isSelected()) {
                    ControlDB.removeLink(sessao.getShowingLinks().get(i));
                    JOptionPane.showMessageDialog(null, "Removido link" + i);
                }
                System.out.println(i + " # " + sessao.getShowingLinks().get(i).isSelected());
            }
            sessao.updateShowingLinks(sessao.getShowingGroup());
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um grupo ou link(modo de seleção).");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel32 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton_LINK1 = new javax.swing.JButton();
        jButton_LINK2 = new javax.swing.JButton();
        jButton_LINK3 = new javax.swing.JButton();
        jButton_LINK4 = new javax.swing.JButton();
        jButton_LINK5 = new javax.swing.JButton();
        jButton_LINK6 = new javax.swing.JButton();
        jButton_LINK7 = new javax.swing.JButton();
        jButton_LINK8 = new javax.swing.JButton();
        jButton_LINK9 = new javax.swing.JButton();
        jLabel_PAGES = new javax.swing.JLabel();
        jLabel_PROXIMO = new javax.swing.JLabel();
        jLabel_VOLTAR = new javax.swing.JLabel();
        jLabel_CurrentMaxPage = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList_Groups = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Exclamation_Mark_16px.png"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tru FavManager 1.0");
        setName("Tru FavManager 1.0"); // NOI18N
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Logo.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/title.png"))); // NOI18N

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_YouTube_2_32px.png"))); // NOI18N

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Firefox_32px.png"))); // NOI18N

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Chrome_32px.png"))); // NOI18N

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Internet_Explorer_32px.png"))); // NOI18N

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Automation_32px.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Fire_Station_32px.png"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(51, 51, 51)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addGap(36, 36, 36)
                .addComponent(jLabel27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(10, 10, 10)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel26)
                        .addComponent(jLabel25)
                        .addComponent(jLabel24)
                        .addComponent(jLabel23)
                        .addComponent(jLabel13)
                        .addComponent(jLabel27)))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Save_as_32px.png"))); // NOI18N

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Add_Folder_32px.png"))); // NOI18N

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_YouTube_32px.png"))); // NOI18N

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Grid_2_32px.png"))); // NOI18N

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Red_Hat_32px.png"))); // NOI18N

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Exit_Sign_32px.png"))); // NOI18N

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Delete_32px.png"))); // NOI18N
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Add_to_Favorites_32px.png"))); // NOI18N
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Minus_Math__16px.png"))); // NOI18N

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Minus_Math__16px.png"))); // NOI18N

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Minus_Math__16px.png"))); // NOI18N

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Minus_Math__16px.png"))); // NOI18N

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Minus_Math__16px.png"))); // NOI18N

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Minus_Math__16px.png"))); // NOI18N

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Minus_Math__16px.png"))); // NOI18N

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Search_32px.png"))); // NOI18N
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel37)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(jLabel38)
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jLabel33)
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38)
                    .addComponent(jLabel35)
                    .addComponent(jLabel31)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jButton_LINK1.setBackground(new java.awt.Color(0, 0, 0));
        jButton_LINK1.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButton_LINK1.setForeground(new java.awt.Color(204, 204, 204));
        jButton_LINK1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Chrome_32px.png"))); // NOI18N
        jButton_LINK1.setText("Facebook");
        jButton_LINK1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jButton_LINK1.setBorderPainted(false);
        jButton_LINK1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_LINK1.setFocusable(false);
        jButton_LINK1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton_LINK1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton_LINK1.setOpaque(false);
        jButton_LINK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LINK1ActionPerformed(evt);
            }
        });

        jButton_LINK2.setBackground(new java.awt.Color(0, 0, 0));
        jButton_LINK2.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButton_LINK2.setForeground(new java.awt.Color(204, 204, 204));
        jButton_LINK2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Chrome_32px.png"))); // NOI18N
        jButton_LINK2.setText("HOTMAIL");
        jButton_LINK2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jButton_LINK2.setBorderPainted(false);
        jButton_LINK2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_LINK2.setFocusable(false);
        jButton_LINK2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton_LINK2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton_LINK2.setOpaque(false);
        jButton_LINK2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LINK2ActionPerformed(evt);
            }
        });

        jButton_LINK3.setBackground(new java.awt.Color(0, 0, 0));
        jButton_LINK3.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButton_LINK3.setForeground(new java.awt.Color(204, 204, 204));
        jButton_LINK3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Chrome_32px.png"))); // NOI18N
        jButton_LINK3.setText("Whatsapp WEB");
        jButton_LINK3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jButton_LINK3.setBorderPainted(false);
        jButton_LINK3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_LINK3.setFocusable(false);
        jButton_LINK3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton_LINK3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton_LINK3.setOpaque(false);
        jButton_LINK3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LINK3ActionPerformed(evt);
            }
        });

        jButton_LINK4.setBackground(new java.awt.Color(0, 0, 0));
        jButton_LINK4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButton_LINK4.setForeground(new java.awt.Color(204, 204, 204));
        jButton_LINK4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Chrome_32px.png"))); // NOI18N
        jButton_LINK4.setText("DropBox");
        jButton_LINK4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jButton_LINK4.setBorderPainted(false);
        jButton_LINK4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_LINK4.setFocusable(false);
        jButton_LINK4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton_LINK4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton_LINK4.setOpaque(false);
        jButton_LINK4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LINK4ActionPerformed(evt);
            }
        });

        jButton_LINK5.setBackground(new java.awt.Color(0, 0, 0));
        jButton_LINK5.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButton_LINK5.setForeground(new java.awt.Color(204, 204, 204));
        jButton_LINK5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Chrome_32px.png"))); // NOI18N
        jButton_LINK5.setText("MUSSUM");
        jButton_LINK5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jButton_LINK5.setBorderPainted(false);
        jButton_LINK5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_LINK5.setFocusable(false);
        jButton_LINK5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton_LINK5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton_LINK5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LINK5ActionPerformed(evt);
            }
        });

        jButton_LINK6.setBackground(new java.awt.Color(0, 0, 0));
        jButton_LINK6.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButton_LINK6.setForeground(new java.awt.Color(204, 204, 204));
        jButton_LINK6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Chrome_32px.png"))); // NOI18N
        jButton_LINK6.setText("PORTAL Aluno");
        jButton_LINK6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jButton_LINK6.setBorderPainted(false);
        jButton_LINK6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_LINK6.setFocusable(false);
        jButton_LINK6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton_LINK6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton_LINK6.setOpaque(false);
        jButton_LINK6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LINK6ActionPerformed(evt);
            }
        });

        jButton_LINK7.setBackground(new java.awt.Color(0, 0, 0));
        jButton_LINK7.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButton_LINK7.setForeground(new java.awt.Color(204, 204, 204));
        jButton_LINK7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Chrome_32px.png"))); // NOI18N
        jButton_LINK7.setText("NOTICIAS");
        jButton_LINK7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jButton_LINK7.setBorderPainted(false);
        jButton_LINK7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_LINK7.setFocusable(false);
        jButton_LINK7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton_LINK7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton_LINK7.setOpaque(false);
        jButton_LINK7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LINK7ActionPerformed(evt);
            }
        });

        jButton_LINK8.setBackground(new java.awt.Color(0, 0, 0));
        jButton_LINK8.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButton_LINK8.setForeground(new java.awt.Color(204, 204, 204));
        jButton_LINK8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Chrome_32px.png"))); // NOI18N
        jButton_LINK8.setText("URI Online Edge");
        jButton_LINK8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jButton_LINK8.setBorderPainted(false);
        jButton_LINK8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_LINK8.setFocusable(false);
        jButton_LINK8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton_LINK8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton_LINK8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LINK8ActionPerformed(evt);
            }
        });

        jButton_LINK9.setBackground(new java.awt.Color(0, 0, 0));
        jButton_LINK9.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButton_LINK9.setForeground(new java.awt.Color(204, 204, 204));
        jButton_LINK9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Chrome_32px.png"))); // NOI18N
        jButton_LINK9.setText("Game Of THRONES");
        jButton_LINK9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jButton_LINK9.setBorderPainted(false);
        jButton_LINK9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_LINK9.setFocusable(false);
        jButton_LINK9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton_LINK9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton_LINK9.setOpaque(false);
        jButton_LINK9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LINK9ActionPerformed(evt);
            }
        });

        jLabel_PAGES.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel_PAGES.setForeground(new java.awt.Color(204, 204, 204));
        jLabel_PAGES.setText("_________________________________");

        jLabel_PROXIMO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_More_Than_2_24px.png"))); // NOI18N
        jLabel_PROXIMO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_PROXIMOMouseClicked(evt);
            }
        });

        jLabel_VOLTAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_More_Than_2_24px_2.png"))); // NOI18N
        jLabel_VOLTAR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_VOLTARMouseClicked(evt);
            }
        });

        jLabel_CurrentMaxPage.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel_CurrentMaxPage.setForeground(new java.awt.Color(204, 204, 204));
        jLabel_CurrentMaxPage.setText("PAGE 1/1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton_LINK1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_LINK2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_LINK3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton_LINK4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_LINK5, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_LINK6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton_LINK7, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_LINK8, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_LINK9, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel_CurrentMaxPage, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_PAGES, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel_VOLTAR)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_PROXIMO)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_LINK1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_LINK2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_LINK3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_LINK4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_LINK5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_LINK6, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_LINK7, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_LINK8, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_LINK9, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_PROXIMO, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel_VOLTAR)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel_PAGES)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel_CurrentMaxPage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(204, 204, 204));
        jButton1.setText("Novo Grupo");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jList_Groups.setBackground(new java.awt.Color(0, 0, 0));
        jList_Groups.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jList_Groups.setForeground(new java.awt.Color(204, 204, 204));
        jList_Groups.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "SENAC", "Mat. Estudo", "Musicas", "Aleatorios", "Pessoal" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_Groups.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList_Groups.setFocusable(false);
        jList_Groups.setSelectionBackground(new java.awt.Color(201, 0, 0));
        jList_Groups.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(jList_Groups);

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setForeground(new java.awt.Color(204, 204, 204));
        jButton2.setText("Modo Seleção");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setForeground(new java.awt.Color(204, 204, 204));
        jButton3.setText("<--  Move link(s) para group ^");
        jButton3.setEnabled(false);
        jButton3.setFocusable(false);

        jButton4.setBackground(new java.awt.Color(0, 0, 0));
        jButton4.setForeground(new java.awt.Color(204, 204, 204));
        jButton4.setText("<< Mostrar links >>");
        jButton4.setFocusable(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        jLabel3.setForeground(new java.awt.Color(0, 128, 128));
        jLabel3.setText("Copyright © Yuri Bento - 2017");

        jLabel4.setForeground(new java.awt.Color(0, 128, 128));
        jLabel4.setText("yurinbento@gmail.com");

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_GitHub_24px.png"))); // NOI18N

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Facebook_24px.png"))); // NOI18N

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons8_Help_24px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addGap(3, 3, 3))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel40))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel30)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel39)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_LINK9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LINK9ActionPerformed
        // TODO add your handling code here:
        linkButtonsAction(9);
    }//GEN-LAST:event_jButton_LINK9ActionPerformed

    private void jButton_LINK8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LINK8ActionPerformed
        // TODO add your handling code here:
        linkButtonsAction(8);
    }//GEN-LAST:event_jButton_LINK8ActionPerformed

    private void jButton_LINK7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LINK7ActionPerformed
        linkButtonsAction(7);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_LINK7ActionPerformed

    private void jButton_LINK6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LINK6ActionPerformed
        linkButtonsAction(6);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_LINK6ActionPerformed

    private void jButton_LINK5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LINK5ActionPerformed
        // TODO add your handling code here:
        linkButtonsAction(5);
    }//GEN-LAST:event_jButton_LINK5ActionPerformed

    private void jButton_LINK4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LINK4ActionPerformed
        // TODO add your handling code here:
        linkButtonsAction(4);
    }//GEN-LAST:event_jButton_LINK4ActionPerformed

    private void jButton_LINK3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LINK3ActionPerformed
        // TODO add your handling code here:
        linkButtonsAction(3);
    }//GEN-LAST:event_jButton_LINK3ActionPerformed

    private void jButton_LINK2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LINK2ActionPerformed
        // TODO add your handling code here:
        linkButtonsAction(2);
    }//GEN-LAST:event_jButton_LINK2ActionPerformed

    private void jButton_LINK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LINK1ActionPerformed
        // TODO add your handling code here:
        linkButtonsAction(1);
    }//GEN-LAST:event_jButton_LINK1ActionPerformed

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        // BOTAO ADD FAVORITO
        new TelaAddLink(this, true, sessao, this).setVisible(true);
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // BOTAO SEARCH
        ControlDB.listFavoritos();

    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel_PROXIMOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_PROXIMOMouseClicked
        // BOTAO NEXT PAGE
        sessao.nextPage();

    }//GEN-LAST:event_jLabel_PROXIMOMouseClicked

    private void jLabel_VOLTARMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_VOLTARMouseClicked
        // BOTAO PREVIOUS PAGE
        sessao.previousPage();

    }//GEN-LAST:event_jLabel_VOLTARMouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // BOTAO HOME (HOT)
        sessao.updateShowingLinks("home");
        sessao.updateGroupJList();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // BOTAO MOSTRAR LINKS
        int x = jList_Groups.getSelectedIndex();
        if (x >= 0) {
            sessao.updateShowingLinks(jList_Groups.getModel().getElementAt(x));
            sessao.updateGroupJList();
            jList_Groups.setSelectedIndex(x);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um grupo.");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        // BOTAO X EXCLUIR
        excluir();
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // BOTAO NOVO GRUPO
        try {
            String novoGrupo = JOptionPane.showInputDialog("Novo grupo:");
            if (novoGrupo != null) {
                sessao.addGroup(novoGrupo);
            }
        } catch (Exception e) {
            JOptionPane.showInputDialog("nome inválido.");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // BOTAO MODO SELECAO
        if (modoSelecaoOn) {
            modoSelecaoOn = false;
            jButton2.setBackground(Color.BLACK);
            for (int i = 0; i < sessao.getShowingLinks().size(); i++) {
                if (sessao.getShowingLinks().get(i).isSelected()) {
                    sessao.getShowingLinks().get(i).setIsSelected(false);
                }
            }
            sessao.updateBotoesLinks();
        } else {
            modoSelecaoOn = true;
            jButton2.setBackground(new Color(201, 0, 0));
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    public List<JButton> getBotoesLink() {
        return botoesLink;
    }

    public JLabel getjLabel_CurrentMaxPage() {
        return jLabel_CurrentMaxPage;
    }

    public ImageIcon getChromeIcon() {
        return chromeIcon;
    }

    public JList<String> getjList_Groups() {
        return jList_Groups;
    }

    public void setSelected(int i, Boolean selected) {
        this.selected[i] = selected;
    }

    public Boolean[] isSelected() {
        return selected;
    }

    public static void main(String args[]) {
        try {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
            * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
             */
            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton_LINK1;
    private javax.swing.JButton jButton_LINK2;
    private javax.swing.JButton jButton_LINK3;
    private javax.swing.JButton jButton_LINK4;
    private javax.swing.JButton jButton_LINK5;
    private javax.swing.JButton jButton_LINK6;
    private javax.swing.JButton jButton_LINK7;
    private javax.swing.JButton jButton_LINK8;
    private javax.swing.JButton jButton_LINK9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JLabel jLabel_CurrentMaxPage;
    private javax.swing.JLabel jLabel_PAGES;
    private javax.swing.JLabel jLabel_PROXIMO;
    private javax.swing.JLabel jLabel_VOLTAR;
    private javax.swing.JList<String> jList_Groups;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}

class MouseListenerJLabel implements MouseListener {

    JLabel label;
    Icon icon;
    ImageIcon icon2;

    public MouseListenerJLabel(URL icon2, JLabel label) {
        this.label = label;
        this.icon = label.getIcon();
        this.icon2 = new ImageIcon(icon2);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.label.setIcon(icon);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.label.setIcon(icon2);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.label.setIcon(icon2);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.label.setIcon(icon);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.label.setIcon(icon2);
    }

};

class MouseListenerButtons implements MouseListener {

    private final JButton buton;
    private Color colorIn;
    private Color colorChange;
    private boolean mouseOver;
    private TelaPrincipal telaPrincipal;
    private final int num;

    public MouseListenerButtons(JButton buton, Color color2, TelaPrincipal telaPrincipal, int num) {
        this.buton = buton;
        this.colorIn = buton.getBackground();
        this.colorChange = color2;
        this.telaPrincipal = telaPrincipal;
        this.num = num;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (telaPrincipal.isSelected()[num]) {

        } else {
            this.buton.setBackground(colorIn);
            this.buton.setForeground(Color.WHITE);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mouseOver) {
            if (telaPrincipal.isSelected()[num]) {
            } else {
                this.buton.setBackground(colorChange);
                this.buton.setForeground(Color.BLACK);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (telaPrincipal.isSelected()[num]) {

        } else {
            this.mouseOver = true;
            this.buton.setBackground(colorChange);
            this.buton.setForeground(Color.BLACK);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (telaPrincipal.isSelected()[num]) {

        } else {
            this.mouseOver = false;
            this.buton.setBackground(colorIn);
            this.buton.setForeground(Color.WHITE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (telaPrincipal.isSelected()[num]) {

        } else {
            this.buton.setBackground(colorChange);
            this.buton.setForeground(Color.BLACK);
        }
    }

};
