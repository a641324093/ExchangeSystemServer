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
import java.util.ArrayList;

/**
 *
 * @author myy
 */
public class DBUser {

	public int id=-1,area_id=-1;
	public String name = null;
	private String password = null,acount=null;
	public DBContact contact = null;

	public DBUser(int id, int area_id, String name, 
			String password, String acount) {
		super();
		this.id = id;
		this.area_id = area_id;
		this.name = name;
		this.password = password;
		this.acount = acount;
	}
	
	
	protected static ArrayList<DBUser> packgeUsers(ResultSet rs) throws SQLException
	{
		if(rs.wasNull())
		{
			return null;
		}
		rs.beforeFirst();
		DBUser user = null;
		ArrayList<DBUser> list_users = new ArrayList<DBUser>();
		while(rs.next())
		{
			int id = rs.getInt("id");
	    	String acount = rs.getString("acount");
	    	String pw = rs.getString("password");
	    	String name = rs.getString("name");
	    	int area_id = rs.getInt("area_id");
	    	user = new DBUser(id, area_id, name, pw, acount);
	    	list_users.add(user);
		}
		return list_users;
		
	}
	/**
     *
     * @param conn
     * @param name
     * @param acount	
     * @param password
     * @param area_id
     * @param tel
     * @return ������û���id�ţ�-1Ϊʧ��
     * @throws SQLException
     */
    public static int addUser(Connection conn, String name, String acount, String password, int area_id, String tel) throws SQLException {

        Statement statement = conn.createStatement();
        String sql = "INSERT INTO USER(name,acount,password,area_id) "
                + "VALUES ('" + name + "','" + acount + "','" + password + "','" + area_id + "')";
        //�����֮��Ĳ������ܷ��ز����¼��id
        statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        int num = -1;
        if (rs.next()) {
            num = rs.getInt(1);
        }
        //�������ϵ��ʽ
        if (num != -1) {
            DBContact.addContact(conn, tel, num);
        }
        return num;
    }

    
    public static ResultSet getUserById(Connection conn, int id) throws SQLException {
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM USER WHERE id=" + id;
        ResultSet rs = statement.executeQuery(sql);
       
        return rs;
    }
    
    public static DBUser getUserObjById(Connection conn, int id) throws SQLException {
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM USER WHERE id=" + id;
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next())
        {
        	int col =rs.getRow();
        }
        //ֻ����һλ
        ArrayList<DBUser> list = DBUser.packgeUsers(rs);
        if(list.size()<=0)
        {
        	int a = 1;
        }
        DBUser user = list.get(0);
        rs.close();
        return user ;
    }
    
    /**
     * �Ƿ�Ϊ�������ķ�����
     * @param conn
     * @param user_id
     * @param form_id
     * @return trueΪ��
     * @throws SQLException
     */
    public static boolean isOwnerOfForm(Connection conn, int user_id, int form_id) throws SQLException {
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM USER,form WHERE  user_id=id AND form_id=" + form_id;
        ResultSet rs = statement.executeQuery(sql);
        //��������
        while (rs.next()) {
            int user_id1 = rs.getInt("user_id");
            if (user_id == user_id1) {
                rs.close();
                return true;
            }
        }
        rs.close();
        return false;
    }

    public static boolean isOwnerOfMessage(Connection conn, int user_id, int msg_id) throws SQLException {
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM message WHERE msg_id=" + msg_id;
        ResultSet rs = statement.executeQuery(sql);
        //��������
        while (rs.next()) {
            int user_id1 = rs.getInt("user_id");
            if (user_id == user_id1) {
                rs.close();
                return true;
            }
        }
        rs.close();
        return false;
    }

    public static boolean isMasterOfForm(Connection conn, int user_id) throws SQLException {
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM USER WHERE user.`user_level`=0;";
        ResultSet rs = statement.executeQuery(sql);
        //��������
        while (rs.next()) {
            int user_id1 = rs.getInt("id");
            if (user_id == user_id1) {
                rs.close();
                return true;
            }
        }
        rs.close();
        return false;
    }

    /**
     * �û��Ƿ��Ѿ��ڸñ������
     *
     * @param conn
     * @param user_id
     * @param form_id
     * @return
     * @throws SQLException
     */
    public static boolean hasMsgedInForm(Connection conn, int user_id, int form_id) throws SQLException {
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM message WHERE form_id=" + form_id + " AND user_id=" + user_id + ";";
        ResultSet rs = statement.executeQuery(sql);
        //��������
        rs.last();
        int row = rs.getRow();
        rs.close();
        if (row == 0) {
            return false;
        }
        return true;
    }

    public static int updateLoc(Connection conn, int user_id, int area_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "UPDATE `user` SET area_id=" + area_id + " WHERE id=" + user_id + ";";
        int num = statement.executeUpdate(sql);
        if (num == 0) {
            num = -1;
        }
        return num;
    }

    /**
     * ���˻��Ƿ��Ѿ�ע��
     *
     * @param conn
     * @param acount
     * @return
     * @throws SQLException
     */
    public static boolean hasResgiter(Connection conn, String acount) throws SQLException {
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM USER ";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String user_acount = rs.getString("acount");
            if (user_acount.equals(acount)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean nameHasResgiter(Connection conn, String str_name) throws SQLException {
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM USER ";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString("name");
            if (name.equals(str_name)) {
                return true;
            }
        }
        return false;
    }
}
