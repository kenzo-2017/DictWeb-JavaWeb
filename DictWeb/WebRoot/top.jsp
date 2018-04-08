<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
#notClickDiv {
	/* 设置不透明度为35% */
	filter: alpha(Opacity=35);
	opacity: 0.35;
	background: #000000; /* 设置背景为黑色 */
	position: absolute; /* 设置定位方式为绝对位置 */
	display: none; /* 设置该<div>标记不显示 */
	z-index: 9; /* 设置层叠顺序 */
	top: 0px; /* 设置顶边距 */
	left: 0px; /* 设置左边距 */
	margin: 0px; /* 设置外边距 */
	padding: 0px; /* 设置内边距 */
}
</style>
<script type="text/javascript" src="JS/AjaxRequest.js"></script>
<script type="text/javascript">
function myClose(divID) { // 根据传递的参数确定要隐藏的层
	alert('AAA');
	document.getElementById(divID).style.display = 'none';
	// divID.style.display = 'none'; // 设置id为login的层为隐藏状态
	// 设置id为notClickDiv的层为隐藏状态
	document.getElementById("notClickDiv").style.display = 'none';
}
function myOpen(divID) { // 根据传递的参数确定要显示的层
	var notClickDiv = document.getElementById("notClickDiv"); // 获取id为notClickDiv的层
	notClickDiv.style.diaplay = 'block'; // 设置层显示
	document.getElementById("notClickDiv").style.width = document.body.clientWidth;
	document.getElementById("notClickDiv").style.height = document.body.clientHeight;
	// 设置由divID所指定的层显示
	document.getElementById(divID).style.display = 'block';
	// 设置由divID所指定的层的左边距
	document.getElementById(divID).style.left = (document.body.clientWidth - 240) / 2;
	// 设置由divID所指定的层的顶边距
	document.getElementById(divID).style.top = (document.body.clientHeight - 139) / 2;
	return false;
}
function loginSubmit(myForm) {
	if (myForm.username.value == "") { // 验证用户名是否为空
		alert("请输入用户名！");
		myForm.username.focus();
		return false;
	}
	if (myForm.pwd.value == "") { // 验证密码是否为空
		alert("请输入密码！");
		myForm.pwd.focus();
		return false;
	}
	// 将登陆信息连接成字符串，作为发送请求的参数
	var param = "username=" + form.username.value + "&pwd=" + form.pwd.value;
	var loader = new net.AjaxRequest("UserServlet?action=login", deal_login, onerror, 
		"POST", encodeURI(param));
}
function onerror() {
	alert("您的操作有误！");
}
function deal_login() {
	/*********************显示提示信息************************/
	var h = this.req.responseText;
	h = h.replace(/\s/g, "", replacement); // 去掉字符串中的Unicode空白
	alert(h);
	if (h == "登陆成功！") {
		window.location.href = "DiaryServlet?action=listAllDiary";
	} else {
		form.username.value = ""; // 清除用户名文本框
		form.pwd.value = ""; // 清除密码文本框
		form.username.focus(); // 让用户文本框获得焦点
	}
}
</script>
<style>
#top { /* 设置页面头部的DIV样式 */
	background-image: url(images/bg_top.jpg);
	width: 800px;
	height: 176px;
}
#navigation { /* 设置导航条的样式 */
	background-image: url(images/navigator_bg.jpg);
	width: 790px;
	height: 26px;
	padding: 5px 5px 0px 5px;
	margin: 0px;
}
#loginUl { /* 设置登陆所用的<ul>标记的样式  */
	list-style: none;
	margin: 0px;
}
#loginUl li { /* 设置登陆所用的<li>标记的样式 */
	padding: 5px;
}
#loginTitle { /* 设置登陆窗口的标题样式 */
	padding: 15px;
	background-color: #FCFBF0;
	color: #1B7F5D;
	font-size: 14px;
	font-weight: bold;
	margin: 0px;
}
#login {
	position: absolute; /* 设置布局方式 */
	width: 280px; /* 设置宽度 */
	padding: 4px; /* 设置内边距 */
	height: 156px; /* 设置高度 */
	display: none; /* 设置显示方式 */
	z-index: 10; /* 设置层叠顺序 */
	background-color: #546B51; /* 设置背景颜色 */
}
</style>
<link rel="stylesheet" href="CSS/style.css" />
<div id="notClickDiv"></div>
<div id="top"></div>
<div id="navigation">
	<div style="float:left;color:#006700;">
		<c:if test="${!empty sessionScope.userName}">
			<b>&nbsp;》&nbsp;欢迎 ${sessionScope.userName}登陆九宫格日记网！</b>
		</c:if>
		<c:if test="${empty sessionScope.userName}">
			<b>&nbsp;》&nbsp;欢迎光临九宫格日记网！</b>
		</c:if>
	</div>
	<div style="float:right;text-align:right;">
		<a href="DiaryServlet?action=listAllDiary">首页</a>
		<c:if test="${empty sessionScope.userName}">
			&nbsp; | &nbsp;<a href="#" onClick="return myOpen('login')">登陆</a>
			&nbsp; | &nbsp;<a href="#" onClick="regOpen('register')">注册</a>
			&nbsp; | &nbsp;<a href="forgetPwd_1.jsp">找回密码</a>
		</c:if>
		<c:if test="${!empty sessionScope.userName}">
			&nbsp; | &nbsp;<a href="DiaryServlet?action=listMyDiary">我的日记</a>
			&nbsp; | &nbsp;<a href="writeDiary">写九宫格日记</a>
			&nbsp; | &nbsp;<a href="UserServlet?action=exit">退出登陆</a>
		</c:if>
	</div>
</div>
<div id="login">
	<form name="loginForm" method="post" action="" id="loginForm">
		<div id="loginTitle"><b>清爽夏日九宫格日记网——用户登陆</b></div>
		<div id="loginContent" style="background-color: #FFFEF9;margin:0px;">
			<ul id="loginUl">
				<li>
					用户名：
					<input type="text" name="username" style="width:120px" 
						onkeydown="if(event.keyCode==13){this.form.pwd.focus();}" />
				</li>
				<li>
					密&nbsp;&nbsp;码：
					<input type="password" name="pwd" style="width:120px" 
						onkeydown="if(event.keyCode==13){loginSubmit(this.form)}" />
					<a href="forgetPwd_1.jsp">找回密码</a>
				</li>
				<li style="padding-left: 40px;">
					<input name="loginSubmit" type="button" value="登陆" onClick="loginSubmit(this.form)" />
					&nbsp;
					<input name="closeSubmit" type="button" value="关闭" onClick="myClose('login')" /> 
				</li>
			</ul>
		</div>
		<div style="background-color:#FEFEFC;height:10px;"></div>
	</form>
</div>