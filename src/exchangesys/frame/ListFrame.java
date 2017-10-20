
package exchangesys.frame;

import exchange.db.DBArea;
import exchange.db.DBCat;
import exchange.db.DBConnect;
import exchange.db.DBForm;
import exchange.db.DBUser;
import exchangesys.panel.ItemPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;

import Exception.MyException;
import utils.FileUtils;
import utils.ResultSetUtils;
import utils.StringUtils;

/**
 *
 * @author myy
 */
public class ListFrame extends javax.swing.JFrame {

    /**
     * Creates new form ListFrame
     */
    private int user_id;
    private int iden = 1;

    public ListFrame(int user_id) {
        try {
            this.user_id = user_id;
            initComponents();
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            init();
        } catch (Exception ex) {
            Logger.getLogger(ListFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() throws Exception {
        Connection conn = DBConnect.getConnection();
        //欢迎信息

        ResultSet rs_user = DBUser.getUserById(conn, user_id);
        String name = "";
        int area_id = -1;
        int iden = 1;
        while (rs_user.next()) {
            name = rs_user.getString("name");
            area_id = Integer.valueOf(rs_user.getString("area_id"));
            iden = rs_user.getInt("user_level");
        }
        rs_user.close();
        String[] area_msg = DBArea.getAreaMsgByAreaID(conn, area_id);
        lab_welcome.setText("欢迎您，" + name + " !");
        //身份以及权限设置
        if (iden == 0) {
            lab_iden.setText("身份：管理员");
            but_submit.setEnabled(false);
            but_submit.setVisible(false);
            but_self.setEnabled(false);
            but_self.setVisible(false);
            this.iden = iden;
        } else {
            lab_iden.setText("身份：普通用户");
        }
        //地区信息
        DBArea.showProvInCB(conn, cb_prov);
        //设置地区信息
        cb_prov.setSelectedItem(area_msg[0]);
        cb_city.setSelectedItem(area_msg[1]);
        cb_area.setSelectedItem(area_msg[2]);
        //种类信息
        DBCat.showCatInConditionCB(conn, cb_cat);
        //显示用户所在区域的交换单
        ResultSet form_rs = DBForm.selectFormByAreaID(conn, area_id);
        showForm(form_rs);
        DBConnect.closeDB(conn);
    }

    private void showForm(ResultSet form_rs) throws SQLException {
        if (form_rs == null) {
            pan_show.removeAll();
            pan_show.updateUI();
            return;
        }
        form_rs.last();
        int len = form_rs.getRow();
        pan_show.removeAll();
        if (len == 0) {
            //JOptionPane.showMessageDialog(this, "无相关符合条件项！");
            pan_show.updateUI();
            return;
        }
        //恢复到开头
        form_rs.beforeFirst();

        int size = jScrollPane1.getWidth() / ItemPanel.WIDTH;
        int w = jScrollPane1.getWidth() - 30;
        int h = (ItemPanel.HEIGHT + 50) * (len / size + 1);
        
        while (form_rs.next()) {
            int form_id = form_rs.getInt("form_id");
            ItemPanel pan = new ItemPanel(form_id, user_id);
            pan_show.add(pan);
        }
        //一行能装下ItemPanel的量
        pan_show.setPreferredSize(new Dimension(w, h));
        pan_show.updateUI();
    }

    private void lockButton() {
        cb_prov.setEnabled(true);
        cb_city.setEnabled(true);
        cb_area.setEnabled(true);

        but_self.setEnabled(false);
        but_seek.setEnabled(false);
        but_submit.setEnabled(false);

    }

    private void clearlockButton() {
        //普通用户
        if (iden == 1) {
            cb_prov.setEnabled(false);
            cb_city.setEnabled(false);
            cb_area.setEnabled(false);
            but_self.setEnabled(true);
            but_seek.setEnabled(true);
            but_submit.setEnabled(true);
        } else//管理员
        {
            cb_prov.setEnabled(false);
            cb_city.setEnabled(false);
            cb_area.setEnabled(false);
            but_self.setEnabled(false);
            but_seek.setEnabled(true);
            but_submit.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cb_cat = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        but_submit = new javax.swing.JButton();
        but_self = new javax.swing.JButton();
        but_seek = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tf_dim_seek = new javax.swing.JTextField();
        lab_welcome = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cb_prov = new javax.swing.JComboBox();
        cb_city = new javax.swing.JComboBox();
        cb_area = new javax.swing.JComboBox();
        but_change_loc = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pan_show = new javax.swing.JPanel();
        lab_iden = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        but_relogin = new javax.swing.JMenuItem();
        mi_exit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("易物宝主界面");
        setPreferredSize(new java.awt.Dimension(1041, 700));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cb_cat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_catItemStateChanged(evt);
            }
        });

        jLabel1.setText("类别：");

        but_submit.setText("发布新交换单");
        but_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_submitActionPerformed(evt);
            }
        });

        but_self.setText("查看我发布的交换单");
        but_self.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_selfActionPerformed(evt);
            }
        });

        but_seek.setText("查询");
        but_seek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_seekActionPerformed(evt);
            }
        });

        jLabel3.setText("模糊查询：");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cb_cat, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_dim_seek, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(but_seek)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(but_submit)
                .addGap(4, 4, 4)
                .addComponent(but_self)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(cb_cat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(but_seek)
                .addComponent(but_submit)
                .addComponent(but_self)
                .addComponent(jLabel3)
                .addComponent(tf_dim_seek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lab_welcome.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lab_welcome.setText("欢迎您，xxx!");

        jLabel2.setText("当前位置：");

        cb_prov.setEnabled(false);
        cb_prov.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_provItemStateChanged(evt);
            }
        });

        cb_city.setEnabled(false);
        cb_city.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_cityItemStateChanged(evt);
            }
        });

        cb_area.setEnabled(false);

        but_change_loc.setText("修改当前位置");
        but_change_loc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_change_locActionPerformed(evt);
            }
        });

        pan_show.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        pan_show.setLayout(new FlowLayout(FlowLayout.LEFT));
        //pan_show.setLayout(new GridLayout(4, 4));
        //pan_show.setLayout(null);

        jScrollPane1.setViewportView(pan_show);
        //int w =jScrollPane1.getWidth();
        //int h=jScrollPane1.getHeight();
        //jScrollPane1.setPreferredSize(new Dimension(w,h));
        //pan_show.setPreferredSize(new Dimension(800,1600));
        //pan_show.setSize(w, h);

        lab_iden.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        lab_iden.setText("身份;");

        jMenu1.setText("操作");

        but_relogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/but_re.png"))); // NOI18N
        but_relogin.setText("注销");
        but_relogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_reloginActionPerformed(evt);
            }
        });
        jMenu1.add(but_relogin);

        mi_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/but_exit.png"))); // NOI18N
        mi_exit.setText("退出");
        mi_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_exitActionPerformed(evt);
            }
        });
        jMenu1.add(mi_exit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lab_welcome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lab_iden)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_prov, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_city, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_area, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(but_change_loc)
                        .addGap(0, 356, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lab_welcome)
                    .addComponent(jLabel2)
                    .addComponent(cb_prov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(but_change_loc)
                    .addComponent(lab_iden))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                .addContainerGap())
        );

        JScrollBar bar = jScrollPane1.getVerticalScrollBar();
        bar.setUnitIncrement(20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void but_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_submitActionPerformed
        FormUpdateFrame fsf = new FormUpdateFrame(FormUpdateFrame.NEWFORM_ID, this.user_id);
        fsf.setVisible(true);
    }//GEN-LAST:event_but_submitActionPerformed

    private void cb_provItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_provItemStateChanged
        try {
            Connection conn = DBConnect.getConnection();
            String prov = cb_prov.getSelectedItem().toString();
            int id = DBArea.getProvIdByName(conn, prov);
            DBArea.showCityInCB(conn, cb_city, id);
            DBConnect.closeDB(conn);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cb_provItemStateChanged

    private void cb_cityItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_cityItemStateChanged
        try {
            Connection conn = DBConnect.getConnection();
//            if (cb_city.getSelectedItem() == null) {
//                cb_area.removeAllItems();
//                cb_area.addItem("无");
//                return;
//            }
            String prov_name = cb_prov.getSelectedItem().toString();
            String city_name = null;
            if (cb_city.getSelectedItem() != null) {
                city_name = cb_city.getSelectedItem().toString();
            }
            int id = DBArea.getCityIdByName(conn, prov_name, city_name);
            DBArea.showAreaInCB(conn, cb_area, id);
            DBConnect.closeDB(conn);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cb_cityItemStateChanged

    private void but_seekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_seekActionPerformed
        try {
            String prov_name = cb_prov.getSelectedItem().toString();
            String city_name = cb_city.getSelectedItem().toString();
            String area_name = cb_area.getSelectedItem().toString();
            String cat_name = cb_cat.getSelectedItem().toString();
            String key = tf_dim_seek.getText();

            Connection conn = DBConnect.getConnection();
            ResultSet cat_rs = null, area_rs = null, dim_rs = null;
            LinkedList list_cat = null, list_dim = null, list_form = null;
            //得到区域ID
            int area_id = DBArea.getAreaIdByName(conn, prov_name, city_name, area_name);
            area_rs = DBForm.selectFormByAreaID(conn, area_id);
            LinkedList list_area = ResultSetUtils.getIDFromResultSet(area_rs, "form_id");
            if (cat_name.equals("无") == false) {
                int cat_id = DBCat.getIdByCatName(conn, cat_name);
                cat_rs = DBForm.selectFormByCatID(conn, cat_id);
                list_cat = ResultSetUtils.getIDFromResultSet(cat_rs, "form_id");
            }
            if (StringUtils.isEmptyOrNull(key) == false) {
                dim_rs = DBForm.selectFormByDimSeek(conn, key);
                list_dim = ResultSetUtils.getIDFromResultSet(dim_rs, "form_id");
            }
            //得到共同的form_id
            list_form = ResultSetUtils.getCommonId(list_area, list_cat);
            list_form = ResultSetUtils.getCommonId(list_form, list_dim);
            ResultSet rs_form = DBForm.getFormByIDs(conn, list_form);
            //显示浏览表单
            showForm(rs_form);
            DBConnect.closeDB(conn);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ListFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }//GEN-LAST:event_but_seekActionPerformed

    private void but_change_locActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_change_locActionPerformed
        if (but_change_loc.getText().equals("修改当前位置")) {
            lockButton();
            but_change_loc.setText("确认");
        } else {
            try {
                String prov_name = cb_prov.getSelectedItem().toString();
                String city_name = cb_city.getSelectedItem().toString();
                String area_name = cb_area.getSelectedItem().toString();
                //更新位置
                Connection conn = DBConnect.getConnection();
                int area_id = DBArea.getAreaIdByName(conn, prov_name, city_name, area_name);
                DBUser.updateLoc(conn, user_id, area_id);
                DBConnect.closeDB(conn);
                clearlockButton();
                but_change_loc.setText("修改当前位置");
                JOptionPane.showMessageDialog(this, "当前位置修改成功!");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ListFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ListFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }//GEN-LAST:event_but_change_locActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        //一获得焦点就重新查询   
        this.but_seekActionPerformed(null);
    }//GEN-LAST:event_formWindowGainedFocus

    private void but_selfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_selfActionPerformed
        try {

            Connection conn = DBConnect.getConnection();
            ResultSet form_rs;
            form_rs = DBForm.selectFormByUserId(conn, user_id);
            showForm(form_rs);
            DBConnect.closeDB(conn);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ListFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }//GEN-LAST:event_but_selfActionPerformed

    private void mi_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mi_exitActionPerformed

    private void but_reloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_reloginActionPerformed
        LoginFrame lf = new LoginFrame();
        lf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_but_reloginActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            FileUtils.clearFile(FormUpdateFrame.IMG_TEMP_PATH);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(ListFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void cb_catItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_catItemStateChanged
        but_seekActionPerformed(null);
    }//GEN-LAST:event_cb_catItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton but_change_loc;
    private javax.swing.JMenuItem but_relogin;
    private javax.swing.JButton but_seek;
    private javax.swing.JButton but_self;
    private javax.swing.JButton but_submit;
    private javax.swing.JComboBox cb_area;
    private javax.swing.JComboBox cb_cat;
    private javax.swing.JComboBox cb_city;
    private javax.swing.JComboBox cb_prov;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lab_iden;
    private javax.swing.JLabel lab_welcome;
    private javax.swing.JMenuItem mi_exit;
    private javax.swing.JPanel pan_show;
    private javax.swing.JTextField tf_dim_seek;
    // End of variables declaration//GEN-END:variables
}
