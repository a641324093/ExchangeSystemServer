package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Exception.MyException;
import exchange.db.DBConnect;
import exchange.db.DBForm;
import exchangesys.frame.ItemShowFrame;

public class OperactionFormServlet extends HttpServlet {

	private Connection conn=null;
	
	public OperactionFormServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
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
//		System.out.println("表单操作doGet Servlet");
		//doGet只有删除操作
		String action = request.getParameter("action");
		if(action.equals("delete")==true)
		{
			try {
				if(conn==null||conn.isClosed())
				{
					conn = DBConnect.getConnection();
				}
				HttpSession session = request.getSession();
				DBForm form = (DBForm)session.getAttribute("form");
				if(form==null)
				{
					//错误
					return;
				}
				DBForm.delFormById(conn, form.form_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				if(conn!=null)
				{
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			//返回主页面
			PrintWriter writer = response.getWriter();
			String script = "<script type='text/javascript'>alert('删除交换单成功!');</script>";
			writer.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			writer.println("<HTML>");
			writer.println("  <BODY>");
			writer.println(script);
			writer.println("  </BODY>");
			writer.println("</HTML>");
			//转到表单查询界面
			request.getRequestDispatcher("/log/main.jsp").include(request, response);
			writer.close();
//			request.getRequestDispatcher("log/main.jsp").forward(request, response);
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
		//doPost只有添加Form
		String title = request.getParameter("title");
		if(title==null)
		{
			//出错
			return;
		}
		String item_name = request.getParameter("item_name");
		String str_cat = request.getParameter("cat");
		String item_desc = request.getParameter("item_desc");
		String form_desc = request.getParameter("form_desc");
		HttpSession session = request.getSession();
		
		int user_id = (Integer)session.getAttribute("user_id");
		int cat_id = Integer.valueOf(str_cat);
		SimpleDateFormat sdf = new SimpleDateFormat(ItemShowFrame.DATE_FORMAT);
		String sub_time = sdf.format(new Date(System.currentTimeMillis()));
		
		int form_id = -1;
		try {
			
			if(conn==null||conn.isClosed())
			{
				conn = DBConnect.getConnection();
			}
			//已经调用了添加物品的语句
			form_id = DBForm.addForm(conn, title, item_name, form_desc, item_desc, 
					user_id, cat_id,null, sub_time);
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			if(conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		PrintWriter writer = response.getWriter();
		String script = "<script type='text/javascript'>alert('删除交换单成功!');</script>";
		writer.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		writer.println("<HTML>");
		writer.println("  <BODY>");
		writer.println(script);
		writer.println("  </BODY>");
		writer.println("</HTML>");
		
		//转到表单查询界面
		request.getRequestDispatcher("QuaryFormServlet?form_id="+form_id).include(request, response);
		writer.close();
		
		
		
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
