package utils;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileUtils {

    /**
     * 显示出一个路�?,择对话框，得到保存路�?
     *
     * @file_name 设置保存文件的默认名，可以为
     * @return 返回对话框中的路径，无则返回null
     */
    public static String getSavePath(String file_name) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("��·��");
        if (file_name != null) {
            fc.setSelectedFile(new File(file_name));
        }
        fc.setMultiSelectionEnabled(false);
        if (JFileChooser.APPROVE_OPTION == fc.showSaveDialog(null)) {
            String path = fc.getSelectedFile().getAbsolutePath();
            return path;
        }
        return null;
    }
    
    public static String getSavePath(String file_name,FileFilter filter) {
        JFileChooser fc = new JFileChooser();
        //ȡ�������ļ�������
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(filter);
        fc.setDialogTitle("ѡ���ļ�");
        if (file_name != null) {
            fc.setSelectedFile(new File(file_name));
        }
        fc.setMultiSelectionEnabled(false);
        if (JFileChooser.APPROVE_OPTION == fc.showSaveDialog(null)) {
            String path = fc.getSelectedFile().getAbsolutePath();
            return path;
        }
        return null;
    }

    public static String getSaveFloder() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("ѡ���ļ���");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String path = null;
        File f = null;
        try {
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                f = fc.getSelectedFile();
                path = f.getPath();
            }
        } catch (HeadlessException ex) {
            System.out.println("Open File Dialog ERROR!");
            return null;
        }
        return path;
    }

    public static void copyFile(String path_des, String path_src) throws IOException {

        File file_des = new File(path_des);
        if(file_des.exists()==false)
        {
            FileUtils.creatFile(path_des);
        }
        InputStream is = FileUtils.class.getResourceAsStream(path_src);
        if (is == null) {
            File file = new File(path_src);
            is = new FileInputStream(file);
            if (is == null) {
                return;
            }
        }
        FileOutputStream fs;
        int byteread;
        fs = new FileOutputStream(path_des);
        byte[] buffer = new byte[1024];
        while ((byteread = is.read(buffer)) != -1) {
            fs.write(buffer, 0, byteread);
        }
        is.close();
        fs.close();
    }

    /**
     *
     * @param filepath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static boolean clearFile(String filepath) throws FileNotFoundException, IOException {
        File file = new File(filepath);
        if (!file.isDirectory()) {
            if (file.exists()) {
                file.delete();
                return true;
            } else {
                return false;
            }
        }
        else if (file.isDirectory()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File f = new File(filepath + "/" + filelist[i]);
                if (!f.isDirectory()) {
                    f.delete();
                } else if (f.isDirectory()) {
                    clearFile(filepath + "/" + filelist[i]);
                }
            }
        }
        return true;
    }

    /**
     * �����ļ����ᴴ��·���²����ڵ�������ļ��У����ǲ���Ϊ�ļ���
     * @param path
     * @return
     * @throws IOException 
     */
    public static boolean creatFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists())
        {
            return false;
        } else {
            new File(file.getParent()).mkdirs();
            file.createNewFile();
            return true;
        }
    }
    
    /**
     * 在文件中按行写入数组中的数据
     * @param path
     * @param datas
     * @throws IOException 
     */
    public static void writeFile(String path,String [] datas) throws  IOException
    {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
        (new FileOutputStream(new File(path), false), "UTF-8"));
        int num = datas.length;
        for(int i=0;i<num;i++)
        {
            bw.append(datas[i]);
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }
    
    public static String [] readFile(String path, int rows) throws UnsupportedEncodingException, FileNotFoundException, IOException
    {
        BufferedReader bf = null;
        bf = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8")); 
        String datas[]= new String[rows];
        for(int i=0;i<rows;i++)
        {
            datas[i]=bf.readLine();
        }
        bf.close();
        return datas;
    }
            
}
