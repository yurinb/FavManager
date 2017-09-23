/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Util.ExportResource;
import Util.GetBasePatch;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Yuri
 */
public class ConnectDB {

    public static final String dirRaiz = GetBasePatch.getBasePathForClass(ConnectDB.class);
    public static String dirIcons = dirRaiz + "src\\icons\\";
    public static String dirDataBase = dirRaiz + "src\\database\\";

    public void startDB() {
//        get user name
//        String userName = System.getProperty("user.name");
        File file = new File((dirRaiz));
        boolean[] mk = new boolean[3];
        if (mk[0] = file.mkdirs() || file.exists()) {
            System.out.println("diretorio - ok");
        } else {
            JOptionPane.showMessageDialog(null, "ERRO ao criar diretório RAIZ");
            System.exit(0);
        }
        file = new File(dirIcons);
        if (mk[1] = file.mkdirs() || file.exists()) {
            System.out.println("diretorio - ok");
        } else {
            JOptionPane.showMessageDialog(null, "ERRO ao criar diretório ICONS");
            System.exit(0);
        }
        file = new File(dirDataBase);
        if (mk[2] = file.mkdirs() || file.exists()) {
            System.out.println("diretorio - ok");
        } else {
            JOptionPane.showMessageDialog(null, "ERRO ao criar diretório DATABASE");
            System.exit(0);
        }
        try {
            System.out.println("criando os arquivos");
            JOptionPane.showMessageDialog(null, "new database");
            boolean sus = ExportResource.copy(getClass().getResourceAsStream("/Data/TruFavDB.sqlite"), dirDataBase + "TruFavDB");
            boolean sus2 = ExportResource.copy(getClass().getResourceAsStream("/icons/loading.gif"), dirIcons + "loading.gif");
        } catch (Exception ex) {
            System.out.println("DIR NAO CRIADO");
        }
    }

    public static Connection connectionConnect() {

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dirDataBase + "TruFavDB");
            System.out.println("connect - ok*");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "database not found.");
            return null;
        }

    }

    public static void disconnectDB(Connection conn, PreparedStatement pStat) {
        try {
            pStat.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public static void disconnectDB(Connection conn, PreparedStatement pStat, ResultSet resSet) {
        try {
            resSet.close();
            pStat.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

}
