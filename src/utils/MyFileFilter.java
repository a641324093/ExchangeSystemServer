package utils;

import javax.swing.JFileChooser;

public class MyFileFilter extends javax.swing.filechooser.FileFilter{
  
    private String [] str_ends;
    private String desc;
    public MyFileFilter(String str_ends[])
    {
        this.str_ends=str_ends;
    }
    
    public MyFileFilter(String str_ends[],String desc)
    {
        this.str_ends=str_ends;
        this.desc=desc;
    }
    
    public boolean accept(java.io.File f) {
    if (f.isDirectory())return true;
    if(str_ends==null)
    {
        return false;
    }
    for(int i=0;i<str_ends.length;i++)
    {
        String str = str_ends[i];
        if(f.getName().endsWith(str))
        {
            //�������򷵻�
            return true;
        }
    }
    return false;
  } 
  
  public String getDescription(){
    return desc;
  }
  
//public static void main(String args[]) {
//  MyFileFilter fileFilter=new MyFileFilter ();  //��������������
//  JFileChooser jf=new JFileChooser();
//  jf.setFileFilter(fileFilter);   //��JFileChooser���ù����� 
//  jf.showOpenDialog(null);
//  }
}