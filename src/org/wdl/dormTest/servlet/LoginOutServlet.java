package org.wdl.dormTest.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.dormTest.util.CookieUtil;


/**
 * Servlet implementation class LoginOutServlet
 */
@WebServlet("/loginOut.action")
public class LoginOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginOutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("========退出==========");
		//清除保存在session中的用户信息
		request.getSession().removeAttribute("session_user");
		
		//清除保存在cookie中的用户信息
		//根据名字查找保存当前项目的学号和密码
		Cookie  cookie = CookieUtil.getCookieByName(request,"cookie_name_pass");
		if(cookie != null) {
			//设计cookie有效时间0 ，马上失效
			cookie.setMaxAge(0);
			//设置cookie作用范围
			cookie.setPath(request.getContextPath());
			System.out.println("request.getContextPath():"+request.getContextPath());
			//将cookie响应出去
			response.addCookie(cookie);
		}
		response.sendRedirect("index.jsp");
	}

}
