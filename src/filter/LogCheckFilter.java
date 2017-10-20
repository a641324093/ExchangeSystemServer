package filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogCheckFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("LogCheckFilter 执行");
		HttpSession session = ((HttpServletRequest)request).getSession();
		//在登录Servlet中添加
		String user_id = (String)session.getAttribute("user_id");
		if(user_id==null)
		{
			PrintWriter writer = response.getWriter();
			writer.write("<h3>您还没有登录，请先登录！<h3/>");
			writer.close();
			request.getRequestDispatcher("login.jsp").include(request, response);
		}
		else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
