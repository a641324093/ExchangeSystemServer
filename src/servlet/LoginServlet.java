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
import exchange.db.DBAcount;
import exchange.db.DBConnect;


public class LoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
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
		doPost(request, response);
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
		String user_name = request.getParameter("user_name");
		String pw = request.getParameter("password");
		Connection conn=null;
		PrintWriter writer=null;
		try {
			conn = DBConnect.getConnection();
			int id = DBAcount.checkAcount(conn, user_name, pw);
			//验证失败
			if(id==-1)
			{
				writer = response.getWriter();
				writer.write("用户名或密码错误！");
				request.getRequestDispatcher("login.jsp").include(request, response);
			}
			//验证成功
			else
			{
				HttpSession session = request.getSession();
				//以用户Id作为登录标示
				session.setAttribute("user_id",id);
				response.sendRedirect(request.getContextPath()+"/log/main.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				if(conn!=null)
				{
					DBConnect.closeDB(conn);
				}
				if(writer!=null)
				{
					writer.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
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
