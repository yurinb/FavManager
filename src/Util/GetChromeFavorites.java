/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author Yuri
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JOptionPane;
import model.Link;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// parsing code using json.simple  
public class GetChromeFavorites {

    private static String userName = System.getProperty("user.name");
    // path to your file 
    private static String patch1 = "C:\\Users\\" + userName + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\bookmarks";
    private static String patch2 = "C:\\Users\\" + userName + "\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 1\\bookmarks";
    private static String patch3 = "C:\\Users\\" + userName + "\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 2\\bookmarks";
    private static String patch4 = "C:\\Users\\" + userName + "\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 3\\bookmarks";
    private static ArrayList<Link> linksList = new ArrayList<>();
    static int count;

    public static ArrayList<Link> getChromeFav() {
        ArrayList<Link> links = null;
        count = 0;
        // a file reader class to access the file using string file path 
        FileReader reader = null;
        boolean found = false;

        // find bookmarks windows
        try {
            reader = new FileReader(patch1);
            found = true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("PATCH 1 NOT FOUND");
        } // access the file
        if (!found) {
            try {
                reader = new FileReader(patch2);
                found = true;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                System.out.println("PATCH 2 NOT FOUND");
            } // access the file
            if (!found) {
                try {
                    reader = new FileReader(patch3);
                    found = true;
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    System.out.println("PATCH 3 NOT FOUND");
                } // access the file
                if (!found) {
                    try {
                        reader = new FileReader(patch4);
                        found = true;
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        System.out.println("PATCH 4 NOT FOUND");
                    } // access the file
                }
                if (!found) {
                    JOptionPane.showMessageDialog(null, "Bookmarks do chrome não foi encontrado. Tente importar no chrome e tente de novo.");
                }
            }
        }
        JSONObject jsonObject = null;

        try {
            jsonObject = (JSONObject) new JSONParser().parse(reader);
        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String checksum = (String) jsonObject.get("checksum");

        JSONObject root = (JSONObject) jsonObject.get("roots");

        Set<String> set = root.keySet();
        JSONArray childrens = null;
        JSONObject obj = null;
        for (String string : set) {
            try {
                obj = (JSONObject) root.get(string);
            } catch (ClassCastException e) {

            }
            if (obj.containsKey("children")) {
                try {
                    childrens = (JSONArray) obj.get("children");
                    links = returnLinksFromCHROME(childrens);
                } catch (Exception e) {

                }
            }
        }
        // display , how many urls we have found  
        System.out.println("count is " + count);
        return links;
    }

    private static ArrayList<Link> returnLinksFromCHROME(JSONArray childrens) {
        JSONObject temp = null;

        for (int i = 0; i < childrens.size(); i++) {
            // get object using index from childrens array
            temp = (JSONObject) childrens.get(i);
            if (temp.containsKey("children")) {
                returnLinksFromCHROME((JSONArray) temp.get("children"));
            }
            // get name
            String name = (String) temp.get("name");
            String url = (String) temp.get("url");
            if (name != null && url != null) {
                Link link = new Link(name, url, "chrome");
                if (!linksList.contains(link)) {
                    linksList.add(link);
                    count++;
                }
            }
        }
        System.out.println(linksList.size() + "size do links");
        return linksList;
    }
}
