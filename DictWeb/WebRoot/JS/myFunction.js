/**
 * 
 */
// 判断输入的字符串是否大于指定的长度
function myCheckStr(str, digit) {
	var n = 0;
	for (i = 0; i < str.length; i++) {
		var code = str.charCodeAt(i); // 获取字符串中指定字符的Unicode编码
		if (code > 255) {
			n += 2;
		} else {
			n += 1;
		}
	}
	if (n > digit) {
		return true;
	} else {
		return false;
	}
}
// 判断用户名
function myCheckUser(user) {
	if (myCheckStr(user, 20)) {
		return false;
	} else {
		return true;
	}
}
// 判断密码
function myCheckPwd(user) {
	var str = user;
	var expression = /^[A-Za-z]{1}([A-Za-z0-9]|[._]){5,29}$/;
	var objExp = new RegExp(expression); // 创建正则表达式对象
	if (objExp.test(str) == true) { // 通过正则表达式验证
		return true;
	} else{
		return false;
	}
}
// 验证E-mail地址
function myCheckEmail(email) {
	var str = email;
	var expression = /\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
	var objExp = new RegExp(expression); // 创建正则表达式对象
	if (objExp.test(str) == true) { // 创建正则表达式进行验证
		return true;
	} else {
		return false;
	}
}