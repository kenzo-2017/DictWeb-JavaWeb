package com.ljl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ljl.dao.UserDao;
import com.ljl.model.CityMap;
import com.ljl.model.User;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = null;
	
	public UserServlet() {
		super();
		userDao = new UserDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("login".equals(action)) { // 用户登录
			this.login(request, response);
		} else if ("exit".equals(action)) { // 用户退出
			this.exit(request, response);
		} else if ("save".equals(action)) { // 保存用户注册信息
			this.save(request, response); 
		} else if ("getProvince".equals(action)) { // 获取省份信息
			this.getProvince(request, response); 
		} else if ("getCity".equals(action)) { // 获取市县信息
			this.getCity(request, response); 
		} else if ("checkUser".equals(action)) { // 检测用户名是否存在
			this.checkUser(request, response); 
		} else if ("forgetPwd1".equals(action)) { // 找回密码第一步
			this.forgetPwd1(request, response); 
		} else if ("forgetPwd2".equals(action)) { // 找回密码第二步
			this.forgetPwd2(request, response); 
		}
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		User f = new User();
		f.setUsername(request.getParameter("username")); // 获取并设置用户名
		f.setPwd(request.getParameter("pwd")); // 获取并设置密码
		int r = userDao.login(f);
		if (r > 0) { // 当用户登录成功时
			HttpSession session = request.getSession();
			session.setAttribute("userName", f.getUsername()); // 保存用户名
			session.setAttribute("uid", r); // 保存用户ID
			request.setAttribute("returnValue", "登录成功！"); // 保存提示信息
			request.getRequestDispatcher("userMessage.jsp").forward(request, response); // 请求转发
		} else { // 当用户登录不成功时
			request.setAttribute("returnValue", "您输入的用户名或密码错误，请重新输入！");
			request.getRequestDispatcher("userMessage.jsp").forward(request, response); // 重定向页面
		}
	}
	
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void exit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(); // 获取HttpSession对象
		session.invalidate(); // 销毁session
		request.getRequestDispatcher("DiaryServlet?action=listAllDiary").forward(request, response); // 重定向页面
	}
	
	/**
	 * 检测用户名是否被注册
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void checkUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username"); // 获取用户名
		String sql = "SELECT * FROM tb_user WHERE username='" + username + "'";
		String result = userDao.checkUser(sql); // 调用UserDao类的checkUser()方法判断用户是否被注册
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(result); // 输出检测结果
		out.flush();
		out.close();
	}
	
	/**
	 * 保存注册的用户信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("user"); // 获取用户名
		String pwd = request.getParameter("pwd"); // 获取密码
		String email = request.getParameter("email"); // 获取E-mail地址
		String city = request.getParameter("city"); // 获取市县
		String question = request.getParameter("question"); // 获取密码提示问题
		String answer = request.getParameter("answer"); // 获取密码提示问题答案
		String sql = "INSERT INTO tb_user(username,pwd,email,question,answer,city) VALUES " + 
				"('" + username + "','" + pwd + "','" + email + "','" + question + "','" + 
				answer + "','" + city + "')";
		String result = userDao.save(sql); // 保存用户信息
		response.setContentType("text/html"); // 设置响应的类型
		PrintWriter out = response.getWriter();
		out.print(result); // 输出执行结果
		out.flush();
		out.close(); // 关闭输出流对象
	}
	
	/**
	 * 获取省份和直辖市
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getProvince(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result = "";
		Set<String> set = CityMap.model.keySet(); // 获取Map集合中的键，并以Set集合返回
		Iterator<String> it = set.iterator();
		while (it.hasNext()) { // 将获取的省份连接为一个以逗号分隔的字符串
			result = result + it.next() + ",";
		}
		result = result.substring(0, result.length() - 1); // 去掉最后一个逗号
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(result); // 输出获取的省份字符串
		out.flush();
		out.close(); // 关闭输出流对象
	}
	
	/**
	 * 获取市县
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getCity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result = "";
		String selProvince = request.getParameter("parProvince"); // 获取选的省份
		selProvince = new String(selProvince.getBytes("ISO-8859-1"), "GBK");
		String[] arrCity = CityMap.model.get(selProvince); // 获取指定键的值
		for (int i = 0; i < arrCity.length; i++) { // 将获取的市县连接为一个以逗号分隔的字符串
			result = result + arrCity[i] + ",";
		}
		result = result.substring(0, result.length() - 1); // 去掉最后一个逗号
		response.setContentType("text/html"); // 设置响应的内容类型
		PrintWriter out = response.getWriter();
		out.print(result); // 输出获取的市县字符串
		out.flush();
		out.close(); // 关闭输出流对象
	}
	
	/**
	 * 找回密码第一步
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forgetPwd1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username"); // 获取用户名
		String question = userDao.forgetPwd1(username); // 执行找回密码第一步对应的方法获取密码提示问题
		PrintWriter out = response.getWriter();
		if ("".equals(question)) { // 判断密码提示问题是否为空
			out.println("<script>alert('您没有设置密码提示问题，不能找回密码！');history.back();</script>");
		} else if ("您输入的用户名不存在！".equals(question)) {
			out.println("<script>alert('您输入的用户名不存在！');history.back();</script>");
		} else { // 获取密码提示问题成功
			request.setAttribute("question", question); // 保存密码提示问题
			request.setAttribute("username", username); // 保存用户名
			request.getRequestDispatcher("forgetPwd_2.jsp").forward(request, response); // 请求转发
		}
	}
	
	/**
	 * 找回密码第二步
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forgetPwd2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username"); // 获取用户名
		String question = request.getParameter("question"); // 获取密码提示问题
		String answer = request.getParameter("answer"); // 获取提示问题答案
		String pwd = userDao.forgetPwd2(username, question, answer); // 执行找回密码第二部的方法判断提示问题答案是否正确
		PrintWriter out = response.getWriter();
		if ("您输入的密码提示问题答案错误！".equals(pwd)) { // 提示问题答案错误
			out.println("<script>alert('您输入的密码提示问题答案错误！');history.back();</script>");
		} else { // 提示问题答案正确，返回密码
			out.println("<script>alert('您的密码是：\\r\\n" + pwd + 
					"\\r\\n请牢记！');window.location.href='DiaryServlet?action=listAllDiary';</script>");
		}
	}
}
