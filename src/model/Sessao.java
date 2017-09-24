/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Util.GetFaviconFromURL;
import com.sun.javafx.font.FontConstants;
import control.ControlDB;
import java.awt.Color;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import view.TelaPrincipal;

/**
 *
 * @author Yuri
 */
public class Sessao {

    private TelaPrincipal telaPrincipal;
    private List<Link> linkList = new ArrayList<>();
    private List<Link> showingLinks = new ArrayList<>();
    private List<Group> groups;
    private int pagina = 1;
    private int numTotalPaginas = 1;
    private int indexInicial = 0;
    private String showingGroup;

    public Sessao(TelaPrincipal telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        groups = ControlDB.getGroups();
    }

    public void atualizarLinksFromDB() {
        System.out.println("atualizarLinksFromDB()");
        ArrayList<Link> linksFromDB = ControlDB.getLinks();
//        for (int i = 0; i < linkList.size(); i++) {
//            if (!linksFromDB.contains(linkList.get(i))) {
//                linkList.remove(i);
//                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++LINK REMOVED");
//            }
//        }
//        for (int i = 0; i < linksFromDB.size(); i++) {
//            if (!linkList.contains(linksFromDB.get(i))) {
//                linkList.add(linksFromDB.get(i));
////                System.out.println(i + "LINK ADDED");
//            } else {
////                System.out.println(i + "LINK ALRDY HAV");
//            }
//        }
        linkList = linksFromDB;
    }

    public void updateGroupJList() {
        ((DefaultListModel) telaPrincipal.getjList_Groups().getModel()).clear();
        for (int i = 0; i < groups.size(); i++) {
            ((DefaultListModel) telaPrincipal.getjList_Groups().getModel()).addElement(groups.get(i).getNome());
        }

    }

    public void updateBotoesLinks() {
        numTotalPaginas = 1;
        int numLinks = showingLinks.size();
        int numLinksUltimaPag = 0;

        if (numLinks > 9) {
            numLinksUltimaPag = numLinks % 9;
            numTotalPaginas = (int) Math.round(((double) numLinks / 9) + 0.5d);
        } else {
            numLinksUltimaPag = numLinks;
        }

        indexInicial = (pagina - 1) * 9;
        for (int i = 0; i < 9; i++) {
            if (pagina == numTotalPaginas && i >= numLinksUltimaPag) {
                telaPrincipal.getBotoesLink().get(i).setText("");
                telaPrincipal.getBotoesLink().get(i).setContentAreaFilled(false);
                telaPrincipal.getBotoesLink().get(i).setCursor(Cursor.getPredefinedCursor(0));
                telaPrincipal.getBotoesLink().get(i).setIcon(null);
            } else {
                String nome = showingLinks.get(indexInicial + i).getNome();
                telaPrincipal.getBotoesLink().get(i).setText(nome);
                telaPrincipal.getBotoesLink().get(i).setContentAreaFilled(true);
                telaPrincipal.getBotoesLink().get(i).setCursor(Cursor.getPredefinedCursor(12));
                telaPrincipal.getBotoesLink().get(i).setIcon(showingLinks.get(indexInicial + i).getIcon());
            }
            try {
                if (showingLinks.get(indexInicial + i).isSelected()) {
                    telaPrincipal.setSelected(i, true);
                    telaPrincipal.mudarCorLinkBotao(i + 1, Color.RED);
                } else if (!showingLinks.get(indexInicial + i).isSelected()) {
                    telaPrincipal.setSelected(i, false);
                    telaPrincipal.mudarCorLinkBotao(i + 1, Color.WHITE);
                }
            } catch (Exception e) {
            }
        }
        telaPrincipal.getjLabel_CurrentMaxPage().setText("PAGE " + pagina + "/" + numTotalPaginas);
    }

    public void addGroup(String groupName) {
        String novoGrupo = groupName;
        if (novoGrupo == null || novoGrupo == "" || novoGrupo.length() > 15) {
            JOptionPane.showMessageDialog(null, "Nome de grupo não aceito.");
        } else {
            Group group = new Group(novoGrupo, false);
            ControlDB.addGroup(group);
            group.setId(ControlDB.getLastAddedGroup().getId());
            if (!getGroups().contains(group)) {
                getGroups().add(group);
                updateGroupJList();
            }
        }
    }

    public void updateShowingLinks(String grupoName) {
        atualizarLinksFromDB();
        showingLinks.clear();
        int count = 0;
        for (Link pickedLink : linkList) {
            if (pickedLink.getGrupo().equals(grupoName)) {
                showingLinks.add(pickedLink);
                count++;
            }
        }
        showingGroup = grupoName;
        pagina = 1;
        updateBotoesLinks();
    }

    public void nextPage() {
        if (pagina < numTotalPaginas) {
            pagina++;
            updateBotoesLinks();

        }
    }

    public void previousPage() {
        if (pagina > 1) {
            pagina--;
            updateBotoesLinks();
        }
    }

    public List<Link> getShowingLinks() {
        return showingLinks;
    }

    public List<Link> getLinkList() {
        return linkList;
    }

    public int getIndexInicial() {
        return indexInicial;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public String getShowingGroup() {
        return showingGroup;
    }

    public TelaPrincipal getTelaPrincipal() {
        return telaPrincipal;
    }

}
