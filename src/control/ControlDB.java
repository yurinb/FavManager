/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Util.GetLinkSiteName;
import static control.ConnectDB.connectionConnect;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Group;
import model.Link;

/**
 *
 * @author Yuri
 */
public class ControlDB {

    private static ResultSet resSet = null;
    private static PreparedStatement pStat = null;

    public static void listFavoritos() {
        Connection conn = connectionConnect();

        String sql = "SELECT * FROM siteList";

        try {
            pStat = conn.prepareStatement(sql);
            resSet = pStat.executeQuery();

            while (resSet.next()) {
                System.out.println(resSet.getString(2));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro listFavoritos!");
        }

        ConnectDB.disconnectDB(conn, pStat, resSet);
    }

    public static void addLink(String nome, String url, String grupo) {
        Connection conn = connectionConnect();

        String sql = "INSERT INTO siteList (nome, url, grupo) VALUES (?, ?, ?)";

        try {
            pStat = conn.prepareStatement(sql);
            pStat.setString(1, nome);
            pStat.setString(2, url);
            pStat.setString(3, grupo);

            pStat.execute();
//            System.out.println("link added into DB");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro! addLink");
        }

        ConnectDB.disconnectDB(conn, pStat);
    }

    /**
     *
     * @return ArrayList of Link with all links from DataBase
     */
    public static ArrayList<Link> getLinks() {
        System.out.println("(((((getLinks)))))");
        Connection conn = connectionConnect();
        ArrayList<Link> links = new ArrayList<>();

        String sql = "SELECT * FROM siteList";

        try {
            pStat = conn.prepareStatement(sql);
            resSet = pStat.executeQuery();
            int count = 0;
            while (resSet.next()) {
                int id = resSet.getInt(1);
                String nome = resSet.getString(2);
                String url = resSet.getString(3);
                String grupo = resSet.getString(4);
                Link link = new Link(id, nome, url, grupo);

//                link.setIconName(null);
                try {
                    link.setIconName(GetLinkSiteName.getSiteName(url));
                } catch (Exception e) {
                    link.setIconName(null);
                }

                try {
                    File file = new File("src\\icons\\" + link.getIconName() + ".png");
                    if (file.exists()) {
                        ImageIcon img = new ImageIcon(file.getPath());
                        link.setIcon(img);
                        link.setIconLoading(false);
//                        System.out.println("icone encontrado.");
                    } else {
                        file = new File("src\\icons\\loading.gif");
                        if (file.exists()) {
                            ImageIcon img = new ImageIcon(file.getPath());
                            link.setIcon(img);
                            link.setIconLoading(true);
//                            System.out.println("icone loading...");
                        }
                    }

                } catch (Exception e) {
                }
                count++;
                links.add(link);
            }
            System.out.println("COUNT ============ " + count);
            ConnectDB.disconnectDB(conn, pStat, resSet);
            return links;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro! getLinks");
            ConnectDB.disconnectDB(conn, pStat, resSet);
            return null;
        }

    }

    public static ArrayList<Group> getGroups() {
        Connection conn = connectionConnect();
        ArrayList<Group> groups = new ArrayList<>();

        String sql = "SELECT * FROM groupList";
        try {
            pStat = conn.prepareStatement(sql);
            resSet = pStat.executeQuery();

            while (resSet.next()) {
                int id = resSet.getInt(1);
                String nome = resSet.getString(2);
                boolean secret = resSet.getBoolean(3);
                Group group = new Group(id, nome, secret);
                groups.add(group);
            }
            ConnectDB.disconnectDB(conn, pStat, resSet);
            return groups;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro! getGroups");
            ConnectDB.disconnectDB(conn, pStat, resSet);
            return null;
        }

    }

    public static Group getGroupByID(int ID) {
        Connection conn = connectionConnect();
        String sql = "SELECT * FROM groupList WHERE id = ?;";
        Group group = null;

        try {
            pStat = conn.prepareStatement(sql);
            pStat.setString(1, String.valueOf(group.getId()));
            resSet = pStat.executeQuery();

            while (resSet.next()) {
                int id = resSet.getInt(1);
                String nome = resSet.getString(2);
                boolean secret = resSet.getBoolean(3);
                group = new Group(id, nome, secret);
            }
            ConnectDB.disconnectDB(conn, pStat, resSet);
            return group;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro! getLastAddedGroup");
            ConnectDB.disconnectDB(conn, pStat, resSet);
            return null;
        }
    }

    public static Group getLastAddedGroup() {
        Connection conn = connectionConnect();
        String sql = "SELECT * FROM groupList ORDER BY id DESC LIMIT 1";
        Group group = null;

        try {
            pStat = conn.prepareStatement(sql);
            resSet = pStat.executeQuery();

            while (resSet.next()) {
                int id = resSet.getInt(1);
                String nome = resSet.getString(2);
                boolean secret = resSet.getBoolean(3);
                group = new Group(id, nome, secret);
            }
            ConnectDB.disconnectDB(conn, pStat, resSet);
            return group;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro! getLastAddedGroup");
            ConnectDB.disconnectDB(conn, pStat, resSet);
            return null;
        }

    }

    public static int addGroup(Group group) {
        Connection conn = connectionConnect();

        String sql = "INSERT INTO groupList (nome, secret) VALUES (?, ?)";

        String isSecret = "false";
        if (group.isSecret()) {
            isSecret = "true";
        }
        try {
            System.out.println("executing..");
            pStat = conn.prepareStatement(sql);
            System.out.println("executed.");
            pStat.setString(1, group.getNome());
            pStat.setString(2, isSecret);
            pStat.execute();
//            System.out.println("group added into DB");
            ConnectDB.disconnectDB(conn, pStat);
            return getLastAddedGroup().getId();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro! addGroup");
            return -1;
        }

    }

    public static int removeGroup(Group group) {
        Connection conn = connectionConnect();

        String sql = "DELETE FROM groupList WHERE id = ?;";

        try {
            System.out.println("executing..");
            pStat = conn.prepareStatement(sql);
            System.out.println("executed.");
            pStat.setString(1, String.valueOf(group.getId()));
            pStat.execute();
            System.out.println("group removed from DB");
            ConnectDB.disconnectDB(conn, pStat);
            System.out.println("disconect");
            Group last = getLastAddedGroup();
            if (last == null) {
                return -1;
            } else {
                return last.getId();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro! removeGroup");
            return -1;
        }

    }

    public static int removeLink(Link link) {
        Connection conn = connectionConnect();

        String sql = "DELETE FROM siteList WHERE id = ?;";

        try {
            System.out.println("executing..");
            pStat = conn.prepareStatement(sql);
            System.out.println("executed.");
            String id = String.valueOf(link.getId());
            pStat.setString(1, id);
            pStat.execute();
            System.out.println("link" + id + " removed from DB");
            ConnectDB.disconnectDB(conn, pStat);
            return getLastAddedGroup().getId();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro! removeLink");
            return -1;
        }

    }

}
