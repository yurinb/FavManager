/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import model.Sessao;
import view.TelaPrincipal;

/**
 *
 * @author Yuri
 */
public class ColoredJList extends JLabel implements ListCellRenderer {

    Sessao sessao;
    TelaPrincipal telaPrincipal;
    JList jlist;

    public ColoredJList(Sessao sessao, TelaPrincipal telaPrincipalm, JList jlist) {
        setOpaque(true);
        this.sessao = sessao;
        this.telaPrincipal = telaPrincipal;
        this.jlist = jlist;
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        // Assumes the stuff in the list has a pretty toString
        setText(value.toString());

        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(204, 204, 204));
        setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N

        // based on the index you set the color.  This produces the every other effect.
        if (sessao.getShowingGroup().equals(value)) {
            setBackground(new Color(201, 0, 0));
            setForeground(new Color(0, 0, 0));
        } else {
            setBackground(Color.BLACK);
        }

        if (isSelected) {
            setBackground(new Color(201, 0, 0));
            setForeground(new Color(0, 0, 0));
        }

        return this;
    }
}
