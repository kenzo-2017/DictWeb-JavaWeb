package com.ljl.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateImg extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// 禁止缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// 指定生成的响应是图片
		response.setContentType("image/jpeg");
		int width = 600; // 图片的宽度
		int height = 600; // 图片的高度
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics(); // 获取Graphics类的对象
		HttpSession session = request.getSession(true);
		String template = session.getAttribute("template").toString();
		String weather = session.getAttribute("weather").toString();
		// 获取图片的完整路径
		weather = request.getSession().getServletContext().getRealPath("images/" + weather + ".png");
		// 获取背景图片
		String[] content = (String[]) session.getAttribute("diary");
		File bgImgFile;
		if ("默认".equals(template)) {
			bgImgFile = new File(request.getSession().getServletContext().getRealPath("images/bg_00.jpg"));
			Image src = ImageIO.read(bgImgFile);
			g.drawImage(src, 0, 0, width, height, null);
			outWord(g, content, weather, 0, 0);
		} else if ("女孩".equals(template)) {
			bgImgFile = new File(request.getSession().getServletContext().getRealPath("images/bg_01.jpg"));
			Image src = ImageIO.read(bgImgFile);
			g.drawImage(src, 0, 0, width, height, null);
			outWord(g, content, weather, 25, 110);
		} else {
			bgImgFile = new File(request.getSession().getServletContext().getRealPath("images/bg_02.jpg"));
			Image src = ImageIO.read(bgImgFile);
			g.drawImage(src, 0, 0, width, height, null);
			outWord(g, content, weather, 30, 5);
		}
		// 将生存的日记图片保存到Session中
		ImageIO.write(image, "PNG", response.getOutputStream());
		session.setAttribute("diaryImg", image);
	}
	
	public void outWord(Graphics g, String[] content, String weather, int offsetX, int offSetY) {
		Font mFont = new Font("微软雅黑", Font.PLAIN, 26); // 通过Font构造字体
		g.setFont(mFont); // 设置字体
		g.setColor(new Color(0, 0, 0)); // 设置颜色为黑色
		int contentLen = 0;
		int x = 0; // 文字的横坐标
		int y = 0; // 文字的纵坐标
		for (int i = 0; i < content.length; i++) {
			contentLen = content[i].length(); // 获取内容的长度
			x = 45 + (i % 3) * 170 + offsetX;
			y = 130 + (i / 3) * 140 + offSetY;
			if (content[i].equals("weathervalue")) {
				File bgImgFile = new File(weather);
				mFont = new Font("微软雅黑", Font.PLAIN, 14); // 通过Font构造字体
				g.setFont(mFont); // 设置字体
				Date date = new Date();
				String newTimeString = new SimpleDateFormat("yyyy年M月d日 E").format(date);
				g.drawString(newTimeString, x - 12, y - 60);
				Image src;
				try {
					src = ImageIO.read(bgImgFile);
					g.drawImage(src, x + 10, y - 40, 80, 80, null); // 绘制天气图片
				} catch (IOException e) {
					e.printStackTrace();
				}
				continue;
			}
			if (contentLen < 5) {
				switch (contentLen % 5) {
				case 1:
					mFont = new Font("微软雅黑", Font.PLAIN, 40); // 通过Font构造字体
					g.setFont(mFont); // 设置字体
					g.drawString(content[i], x + 40, y);
					break;
				case 2:
					mFont = new Font("微软雅黑", Font.PLAIN, 36); // 通过Font构造字体
					g.setFont(mFont); // 设置字体
					g.drawString(content[i], x + 25, y);
					break;
				case 3:
					mFont = new Font("微软雅黑", Font.PLAIN, 30); // 通过Font构造字体
					g.setFont(mFont); // 设置字体
					g.drawString(content[i], x + 20, y);
					break;
				case 4:
					mFont = new Font("微软雅黑", Font.PLAIN, 28); // 通过Font构造字体
					g.setFont(mFont); // 设置字体
					g.drawString(content[i], x + 10, y);
					break;
				}
			} else {
				mFont = new Font("微软雅黑", Font.PLAIN, 22); // 通过Font构造字体
				g.setFont(mFont); // 设置字体
				if (Math.ceil(contentLen) / 5.0 == 1) {
					g.drawString(content[i], x, y);
				} else if (Math.ceil(contentLen) / 5.0 == 2) {
					// 分两行写
					g.drawString(content[i].substring(0, 5), x, y - 20);
					g.drawString(content[i].substring(5), x, y + 10);
				} else if (Math.ceil(contentLen) / 5.0 == 3) {
					// 分三行写
					g.drawString(content[i].substring(0, 5), x, y - 30);
					g.drawString(content[i].substring(5, 10), x, y);
					g.drawString(content[i].substring(10), x, y + 30);
				}
			}
		}
		g.dispose();
	}
}
