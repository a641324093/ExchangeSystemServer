/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Container;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author myy
 */
public class ImageUtils {

    public static Image resizeImg(ImageIcon img, Container con) {
        int width = con.getWidth();
        int height = con.getHeight();
        //ƥ���С
        Image image = img.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return image;
    }

    /**
     * ͨ��·����ȡͼƬ�����Զ�ȡjar���е��ļ�,��Ҫ���߰�ʵ��
     * @param path
     * @return 
     */
    public static Image getImageInJar(String path) {
        Image image = null;
        InputStream is = (InputStream)ImageUtils.class.getResourceAsStream(path);
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
        }
        return image;
    }
}
