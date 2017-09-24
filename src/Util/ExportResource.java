/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Yuri
 */
public class ExportResource {

    // CHAMA EXEMPLO: String fullPath = ExportResource("/myresource.ext");
    static public String ExportRes(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        System.out.println("@#");
        try {
            stream = ExportResource.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if (stream == null) {
                System.out.println("#");
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }
            System.out.println("#");

            int readBytes;
            System.out.println("#");
            byte[] buffer = new byte[4096];
            jarFolder = new File(ExportResource.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            System.out.println("#");
            while ((readBytes = stream.read(buffer)) > 0) {
                System.out.println("!");
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            System.out.println("#@@@@@@@@@@@@@@@@@@@@@");
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        System.out.println("EXPORTADO");
        return jarFolder + resourceName;
    }

    /**
     * Copy a file from source to destination.
     *
     * @param source the source
     *
     * Testing it (icon.png is an image inside the package image of the
     * application):
     * copy(getClass().getResourceAsStream("/image/icon.png"),getBasePathForClass(Main.class)+"icon.png");
     *
     * @param destination the destination
     * @return True if succeeded , False if not
     */
    public static boolean copy(InputStream source, String destination) {
        boolean succeess = true;

        System.out.println("Copying ->" + source + "\n\tto ->" + destination);
//        JOptionPane.showMessageDialog(null, "Copying ->" + source + "\n\tto ->" + destination);
        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            System.out.println("COPY FAILEDD");
            succeess = false;
        }

        return succeess;

    }
}
