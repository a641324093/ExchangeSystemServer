/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Exception.MyException;
/**
 *
 * @author myy
 */
public class DBConnect {

    // MySQL����ʱ���û���
    public static final String  USER="root";
    // Java����MySQL����ʱ������
    public static final String  PASSWORD="123456";
    //���������� 
    public static final String  DRIVER="com.mysql.jdbc.Driver";
    // URLָ��Ҫ���ʵ����ݿ���scutcs
    public static final String  URL="jdbc:mysql://localhost/exsystem?"
            + "user="+USER+"&password="+PASSWORD+"&useUnicode=true&characterEncoding=GBK&useSSL=false";
    
    
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException, MyException {
            // ������������
            Class.forName(DRIVER);
            // �������ݿ�,�������ӷ�ʽ���ñ���Ҳ�޷���Ч������Ҫ��һ�ֲ��ܽ���������⣬�������Ĳ�ѯ�����Ч�����⣩
             Connection conn = DriverManager.getConnection(URL);
            if(conn==null)
            {
            	throw new MyException("���ݿ�����ʧ��");
            }
            return conn;
    }
    
    public static void closeDB(Connection conn) throws SQLException
    {
        if(conn.isClosed()==false)
        {
            conn.close();
        }
    }
}
