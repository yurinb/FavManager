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
import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Sessao;

public class GetFaviconFromURL {

    public static ImageIcon getIcon(String url) {
        try {
            BufferedImage image = null;
            URL tmpUrl = new URL("https://www.google.com/s2/favicons?domain_url=" + url);
            image = ImageIO.read(tmpUrl);
            ImageIcon img = new ImageIcon(tmpUrl, url);
            return img;
        } catch (IOException e) {
            System.out.println("getIcon ERROR!");
            return null;
        }
    }

    public static void downloadIcons(Sessao sessao) {
        new Thread(new DownloadIcons(sessao)).start();
    }

    public static void save(ImageIcon imgIcon, String site) {
        try {
            if (site == null) {
                System.out.println("nnnnul");
            } else {
                Image image = imgIcon.getImage();
                BufferedImage bImg = toBufferedImage(image);

                File f = new File("src\\icons\\" + site + ".png");
                if (ImageIO.write(bImg, "PNG", f)) {
                    System.out.println("id:" + site);
                    System.out.println("-- saved" + f.getPath());
                }
            }
        } catch (IOException e) {
            System.out.println("NAO SALVOU!");
        }
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

}

class DownloadIcons implements Runnable {

    Sessao sessao;

    public DownloadIcons(Sessao sessao) {
        this.sessao = sessao;
    }

    @Override
    public void run() {
        for (int i = 0; i < sessao.getLinkList().size(); i++) {
            ImageIcon icon = null;
            try {
                icon = sessao.getLinkList().get(i).getIcon();
            } catch (Exception e) {
                   
//                System.out.println("get icon failed.");
            }
            String name = sessao.getLinkList().get(i).getIconName();
            File file = new File("src\\icons\\" + name + ".png");
            if (!file.exists() || sessao.getLinkList().get(i).isIconLoading()) {
                ImageIcon img = null;
                try {
                    img = GetFaviconFromURL.getIcon(sessao.getLinkList().get(i).getUrl());
                } catch (Exception e) {
                    System.out.println("getIcon(getFaviconFromURL) ERROR!");
                }
                try {
                    GetFaviconFromURL.save(img, sessao.getLinkList().get(i).getIconName());
                    String url = sessao.getLinkList().get(i).getUrl();
                    name = GetLinkSiteName.getSiteName(url);
                    sessao.getLinkList().get(i).setIconName(name);
                    sessao.getLinkList().get(i).setIcon(new ImageIcon("src\\icons\\" + name + ".png"));
                    sessao.getLinkList().get(i).setIconLoading(false);
                    sessao.updateBotoesLinks();
                } catch (Exception e) {
                    System.out.println("DownloadIcons - save ERROR!");
                }
            } else {
                System.out.println("DownlaodIcons - ja setado.");
            }
        }
    }

}
