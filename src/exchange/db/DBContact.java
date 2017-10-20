/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author myy
 */
public class DBContact {

    /**
     * 
     * @param conn
     * @param tel
     * @return ���ز����¼��id��δ����Ϊ-1��
     * @throws SQLException 
     */
	public DBContact()
	{
		
	}
	
    public static int addContact(Connection conn, String tel,int user_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "INSERT INTO contact (tel,user_id) VALUES ('" + tel + "',"+user_id+")";
        statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
        //���ز����ļ�¼��������idֵ
        ResultSet rs = statement.getGeneratedKeys();
        int num = -1;
        if (rs.next()) {
            num = rs.getInt(1);
        }
        return num;
    }
    
    /**
     * �õ���ϵ��Ϣ�����û��ǳ�
     * @param conn
     * @param user_id
     * @return
     * @throws SQLException 
     */
    public static String getMsgByUserId(Connection conn,int user_id)throws SQLException
    {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM contact,USER WHERE  user.id=contact.`user_id` AND user.`id`="+user_id;
        ResultSet rs = statement.executeQuery(sql);
        //��������
        String contact="������:";
        while (rs.next()) {
            String name = rs.getString("name");
            String tel = rs.getString("tel");
            contact+=name+"\n  �绰:"+tel;
        }
        rs.close();
        return contact;
    }
    
    public static String getContactByUserId(Connection conn,int user_id)throws SQLException
    {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM contact where `user_id`="+user_id;
        ResultSet rs = statement.executeQuery(sql);
        //��������
        String contact="";
        while (rs.next()) {
            String tel = rs.getString("tel");
            contact+="�绰:"+tel+" ";
        }
        rs.close();
        return contact;
    }
}
