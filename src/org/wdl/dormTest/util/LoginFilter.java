package org.wdl.dormTest.util;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.service.UserService;
import org.wdl.dormTest.service.UserServiceImpl;

/**
 * 该过滤器拦截所有以.action的请求
 */
@WebFilter("*.action")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("------------登录拦截器------------");
		
		HttpServletRequest  httpServletRequest = (HttpServletRequest) request;
		HttpSession  session = httpServletRequest.getSession();
		
		httpServletRequest.setCharacterEncoding("utf-8");
		
		//①获取登录后保存在session中的用户信息
		User user  = (User) session.getAttribute("session_user");
		System.out.println("session user:"+user);
		
		UserService  userService = new UserServiceImpl();
		//判断用户是否登录
		if(user != null) {
			//登录，就放行，走处理该请求的方法
			//通过user 判断登录的用户角色是否有对该请求的访问权限
			//通过request中的httpServletRequest.getRequestURI(); 来获取用户发送的请求
			roleJudgment(user,httpServletRequest,response,chain);
			
			//chain.doFilter(request, response);
		}else {
			//② 判断cookie中是否有用户信息，即学号和密码
			//第一步，去遍历所有的cookie，看是否有保存当前学号和密码的cookie
			Cookie cookie = CookieUtil.getCookieByName(httpServletRequest, "cookie_name_pass");
			System.out.println("cookie:"+cookie);
			
			if(cookie != null) {
				//有保存当前项目学号和密码的cookie
				String stuCodePass = URLDecoder.decode(cookie.getValue(),"UTF-8");
				//111#111
				System.out.println("stuCodePass:"+stuCodePass);
				
				String[]  stuCodePass2 =stuCodePass.split("#");
				//判断当前用户的学号和密码是否有效
				User user2 = userService.findByStuCodeAndPass(stuCodePass2[0], stuCodePass2[1],stuCodePass2[2]);
				System.out.println("user2:"+user2);
				
				if(user2 != null) {
					//记住在浏览器的学号和密码有效，放行，自动登录成功将用户信息保存在session中
					
					//通过user 判断登录的用户角色是否有对该请求的访问权限
					//通过request中的httpServletRequest.getRequestURI(); 来获取用
					roleJudgment(user2,httpServletRequest,response,chain);
					
					/*httpServletRequest.getSession().setAttribute("session_user", user2);
					chain.doFilter(request, response);*/
					
				}else {
					//记住在浏览器的学号和密码无效
					request.setAttribute("error", "请先登录！");
					//如未登录，就跳转到登录页面
					request.getRequestDispatcher("/index.jsp").forward(httpServletRequest, response);
				}
			}else {
				request.setAttribute("error", "请先登录！");
				//如未登录，就跳转到登录页面
				request.getRequestDispatcher("/index.jsp").forward(httpServletRequest, response);
			}
		}
	}

	private void roleJudgment(User user, HttpServletRequest httpServletRequest, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//获取用户角色id
		Integer  roleId = user.getRoleId();
		
		MyHttpServletRequestWrapper1 requestWrapper1 = new MyHttpServletRequestWrapper1(
				(HttpServletRequest) httpServletRequest);
		requestWrapper1.setParameterMap(httpServletRequest.getParameterMap());
		
		
		//获取请求地址  /DormTest/dormBuild.action
		String  requestUrI = requestWrapper1.getRequestURI();
		System.out.println("requestUrI:"+requestUrI);
		
		//动态获取项目名/DormTest
		String path = requestWrapper1.getContextPath();
		System.out.println("path:"+path);
		if( (requestUrI.startsWith(path+"/dormBuild.action") || requestUrI.startsWith(path+"/dormManager.action"))
				&& roleId.equals(0)) {
			//当用户发送的是宿舍楼管理模块或者宿舍管理员管理模块的请求时，只有在当前用户角色为超级管理员时才放行
			requestWrapper1.getSession().setAttribute("session_user", user);
			chain.doFilter(requestWrapper1, response);
			
		}else if(requestUrI.startsWith(path+"/student.action") && !roleId.equals(2)) {
			//当用户发送的请求是学生模块的请求时，只有当前用户角色不是学生就放行
			requestWrapper1.getSession().setAttribute("session_user", user);
			chain.doFilter(requestWrapper1, response);
			
		}else if(requestUrI.startsWith(path+"/record.action") ||
				requestUrI.startsWith(path+"/password.action")||
				requestUrI.startsWith(path+"/loginOut.action")||
				requestUrI.startsWith(path+"/index.action")) {
			//当用户发送的请求是考勤、修改密码、退出模块的请求时，不管用户的角色是什么，都放行
			requestWrapper1.getSession().setAttribute("session_user", user);
			chain.doFilter(requestWrapper1, response);
		}else {
			requestWrapper1.getRequestDispatcher("/index.jsp").forward(httpServletRequest, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
