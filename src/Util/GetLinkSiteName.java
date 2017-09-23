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
public class GetLinkSiteName {

    public static String getSiteName(String url) {
        String siteName = "";
        try {
            int count = 0;
            int indexIn = 0;
            int indexFi = 0;
            String bars = "";
            for (int i = 0; i < url.length(); i++) {
                try {
                    bars = url.substring(i - 2, i);
                } catch (Exception e) {
                }
                if (bars.equals("//") || url.charAt(i) == '.') {
                    int x = 0;
                    count++;
                    if (url.charAt(i) == '.') {
                        x = 1;
                    }
                    if (count == 1) {
                        indexIn = i + x;
                    }
                    if (count >= 2) {
                        indexFi = i;
                        siteName = url.substring(indexIn, indexFi);
                        if (siteName.equalsIgnoreCase("www")) {
                            indexIn = indexFi+1;
                        }else{
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Falha ao pegar nome do site.");
        }
        String c = siteName.substring(0, 1).toUpperCase();
        siteName = c + (siteName.substring(1));
        return siteName;
    }

}
