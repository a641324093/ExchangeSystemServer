
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author myy
 */
public class StringUtils {
    
    /**
     * �?测字符串是否为空或null
     * @param str
     * @return 是则返回true
     */
    public static boolean isEmptyOrNull(String str)
    {
        if(str==null)
        {
            return true;
        }
        if(str.trim().equals(""))
        {
            return true;
        }
        return false;
    }
    
    public static boolean isTel(String msg)
    {
        if(msg==null)
        {
            return false;
        }
        Pattern p1 = Pattern.compile("^[0-9]{11}");
                Matcher m1 = p1.matcher(msg);
                if (m1.matches()) {
                    return true;
                } else {
                    return false;
                }
    }
    
    /**
     * �����ַ����ĳ��ȣ��������...����
     * @param str
     * @param len
     * @return 
     */
    public static String fitString(String str,int len)
    {
        if(StringUtils.isEmptyOrNull(str))
        {
            return null;
        }
        int str_len = str.length();
        //�ַ������ȱ�Ҫ��Ķ�
        if(str_len<len)
        {
            len=str_len;
        }
        str=str.substring(0, len);
        //�ַ������ȱ�Ҫ�󳤶ȳ�
        if(str_len>len)
        {
            str = str+"...";
        }
        return str;
    }
}
