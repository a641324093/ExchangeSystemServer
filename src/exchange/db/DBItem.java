/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import utils.FileUtils;

/**
 * 
 * @author myy
 */
public class DBItem {

	public int item_id = -1, cat_id = -1;
	public String name = null, path_img = null, desc = null;
	public DBCat cat = null;
	
	public DBItem(int item_id, int cat_id, String name, String path_img,
			String desc) {
		super();
		this.item_id = item_id;
		this.cat_id = cat_id;
		this.name = name;
		this.path_img = path_img;
		this.desc = desc;
	}

	public static int addItem(Connection conn, String item_name, String item_desc,
			String img_path, int cat_id) throws SQLException {
		// statement����ִ��SQL���
		Statement statement = conn.createStatement();
		// Ҫִ�е�SQL���
		String sql = "INSERT INTO item (`name`,`desc`,img,cat_id) VALUES ('"
				+ item_name + "','" + item_desc + "','" + img_path + "'," + cat_id + ")";
		statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		// ���ز����ļ�¼��������idֵ
		ResultSet rs = statement.getGeneratedKeys();
		int num = -1;
		if (rs.next()) {
			num = rs.getInt(1);
		}
		return num;
	}

	protected static ArrayList<DBItem> packageItems(ResultSet rs) {
		ArrayList<DBItem> list_items = new ArrayList<DBItem>();
		DBItem item = null;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				int item_id = rs.getInt("item.id");
				int cat_id = rs.getInt("item.cat_id");
				String name = rs.getString("item.name");
				String desc = rs.getString("item.desc");
				String path_img = rs.getString("item.img");
				item = new DBItem(item_id, cat_id, name, path_img, desc);
				list_items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_items;
	}

	public static ResultSet getItemById(Connection conn, int id)
			throws SQLException {
		// statement����ִ��SQL���
		Statement statement = conn.createStatement();
		// Ҫִ�е�SQL���
		String sql = "SELECT * FROM item WHERE id=" + id;
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public static DBItem getItemObjById(Connection conn, int id)
			throws SQLException {
		// statement����ִ��SQL���
		Statement statement = conn.createStatement();
		// Ҫִ�е�SQL���
		String sql = "SELECT * FROM item WHERE id=" + id;
		ResultSet rs = statement.executeQuery(sql);
		DBItem item = DBItem.packageItems(rs).get(0);
		rs.close();
		return item;
	}

	/**
	 * ����ItemͬʱҲ���·������е�ͼƬ
	 * 
	 * @param conn
	 * @param name
	 * @param desc
	 * @param img_path
	 * @param item_id
	 * @return 0Ϊִ�д���
	 * @throws SQLException
	 */
	public static int updateItem(Connection conn, String name, String desc,
			String img_path, int item_id, int cat_id) throws SQLException,
			IOException {
		// statement����ִ��SQL���
		Statement statement = conn.createStatement();
		// Ҫִ�е�SQL���
		ResultSet rs = getItemById(conn, cat_id);
		String img = "";
		while (rs.next()) {
			img = rs.getString("img");
		}
		// ͼƬҲ������
		if (img.equals(img_path) == false) {
			// ɾ��ԭͼƬ
			FileUtils.clearFile(img);
		}
		String sql = "UPDATE item SET NAME='" + name + "',`desc`='" + desc
				+ "',img='" + img_path + "',cat_id=" + cat_id + " WHERE id="
				+ item_id;
		int num = statement.executeUpdate(sql);
		return num;
	}

	/**
	 * ɾ��item��¼ʱ����Ҫ���Ĵ���
	 * 
	 * @param item_id
	 * @return
	 */
	public static void delectItem(Connection conn, int item_id)
			throws SQLException, IOException {
		Statement statement = conn.createStatement();
		// Ҫִ�е�SQL���
		String sql = "SELECT	img FROM form,item\n"
				+ "WHERE form.`item_id`=item.`id`\n" + "AND item.`id`="
				+ item_id;
		ResultSet rs = statement.executeQuery(sql);
		String img_path = null;
		while (rs.next()) {
			img_path = rs.getString("img");
		}
		// ɾ���ļ�
		FileUtils.clearFile(img_path);
		// ɾ����¼
		sql = "DELETE FROM item WHERE id=" + item_id + ";";
		statement.executeUpdate(sql);
		statement.close();
		rs.close();

	}
}
