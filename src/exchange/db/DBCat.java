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

import javax.swing.JComboBox;

/**
 *
 * @author myy
 */
public class DBCat {
	
	public int cat_id=-1;
	public String name = null;
	
    public DBCat(int cat_id, String name) {
		super();
		this.cat_id = cat_id;
		this.name = name;
	}

    protected static ArrayList<DBCat> packageToObjs(ResultSet rs) {
		ArrayList<DBCat> list_cats = new ArrayList<DBCat>();
		DBCat cat = null;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				int cat_id = rs.getInt("cat.cat_id");
				String name = rs.getString("cat.name");
				cat = new DBCat(cat_id, name);
				list_cats.add(cat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_cats;
	}
    
    public static DBCat getCatObjById(Connection conn,int cat_id) throws SQLException
    {
    	// statement用来执行SQL语句
		Statement statement = conn.createStatement();
		// 要执行的SQL语句
		String sql = "SELECT * FROM cat WHERE cat_id=" + cat_id;
		ResultSet rs = statement.executeQuery(sql);
		DBCat cat = DBCat.packageToObjs(rs).get(0);
		rs.close();
		return cat;
    }
    
    public static ArrayList<DBCat> getAllCatObj(Connection conn) throws SQLException
    {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "select * from cat";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        ArrayList<DBCat> list = DBCat.packageToObjs(rs);
        rs.close();
        return list;
    }
    
	public static void showCatInConditionCB(Connection conn,JComboBox cat) throws SQLException
    {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "select * from cat";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        cat.removeAllItems();
        while (rs.next()) {
            String cat_name = rs.getString("name");
            cat.addItem(cat_name);
        }
        cat.addItem("无");
        cat.setSelectedItem("无");
        rs.close();
    }
    
    public static void showCatInCB(Connection conn,JComboBox cat) throws SQLException
    {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "select * from cat";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        cat.removeAllItems();
        while (rs.next()) {
            String cat_name = rs.getString("name");
            cat.addItem(cat_name);
        }
        rs.close();
    }
    
    /**
     * 
     * @param conn
     * @param cat_name
     * @return cat名 null为无
     * @throws SQLException 
     */
    public static int getIdByCatName(Connection conn,String cat_name) throws SQLException
    {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT cat_id FROM cat WHERE name='"+cat_name+"'";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        while (rs.next()) {
            int cat_id = rs.getInt("cat_id");
            return cat_id;
        }
        rs.close();
        return -1;
    }
}
