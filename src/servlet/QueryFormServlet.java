package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Exception.MyException;
import exchange.db.DBCat;
import exchange.db.DBConnect;
import exchange.db.DBForm;
import exchange.db.DBItem;
import exchange.db.DBUser;

/**
 * 处理表单的查询与删除操作
 * @author lenovo-Myy
 *
 */
public class QueryFormServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public QueryFormServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn;
		DBForm form = null;
		try {
			conn = DBConnect.getConnection();
			String str_form_id = request.getParameter("form_id");
			int form_id = Integer.valueOf(str_form_id);
			form = DBForm.getFormObjById(conn, form_id);
			form.item = DBItem.getItemObjById(conn,form.item_id);
			form.user = DBUser.getUserObjById(conn,form.user_id);
			form.item.cat = DBCat.getCatObjById(conn, form.item.cat_id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		
		if(form!=null)
		{
			HttpSession session = request.getSession();
			//将表单对象传递过去
			session.setAttribute("form",form);
			request.getRequestDispatcher("/log/showForm.jsp").forward(request, response);;
		}
		//若未找到交换单
		else
		{
			PrintWriter writer = response.getWriter();
			writer.append("找不到该交换单！");
			writer.close();
			request.getRequestDispatcher("log/main.jsp").include(request, response);;
		}
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
