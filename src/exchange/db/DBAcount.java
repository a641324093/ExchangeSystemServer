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
 * @author myy �������ݿ��е��˻���
 */
public class DBAcount {

    /**
     * ��¼���
     * @param conn
     * @param acount
     * @param password
     * @return �˻�id�� Ϊ-1Ϊδ�ҵ�
     * @throws SQLException 
     */
    public static int checkAcount(Connection conn, String acount, String password) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "select * from user";
        ResultSet rs = statement.executeQuery(sql);
        //��������
        while (rs.next()) {
            String name = rs.getString("acount");
            if (name.equals(acount)) {
                String pw = rs.getString("password");
                if (pw.equals(password)) {
                    String id = rs.getString("id");
                    return Integer.valueOf(id);
                }
            }
        }
        return -1;
    }

}
