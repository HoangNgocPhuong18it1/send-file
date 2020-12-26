package lib;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Pattern;

public class Lib {

    public static Robot robot;
    private static Rectangle rect;
    private static final Pattern PATTERN_IP = Pattern.compile("\\d{3}(.\\d{1,3}){3}");
    private static BufferedImage mouseCursor ;

    private static final Object LOCK_FOR_INSERTION = new Object();
    private static ImageWriter JPG_WRITER = ImageIO.getImageWritersByFormatName( "jpg" ).next();
    private static JPEGImageWriteParam JPG_PARAM = new JPEGImageWriteParam( null );

    static {
        JPG_PARAM.setCompressionMode( ImageWriteParam.MODE_EXPLICIT );
    }



    public static boolean checkNonLocalIP(String nicDisplayName, String ip){
        if(!nicDisplayName.startsWith("en"))
            return false;
        return PATTERN_IP.matcher(ip).matches();
    }


    public static File[] getListChildOfFile(String path){
        File folder = new File(path);
        if (folder.isDirectory()){
            File[] listFile = folder.listFiles();
            return listFile;
        }else return null;


    }
}
