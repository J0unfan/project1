/**
 * 用户注册校验的js文件
 */
function checkForm(){
		// 校验用户名:
		// 获得用户名文本框的值:
		var username = document.getElementById("username").value;
		if(username == null || username == ''){
			document.getElementById("span1").style.color='red';
			document.getElementById("span1").innerHTML = "用户名不能为空";
			return false;
		}
		// 校验密码:
		// 获得密码框的值:
		var password = document.getElementById("password").value;
		if(password == null || password == ''){
			document.getElementById("passwordCheck").innerHTML = "密码不能为空";
			return false;
		}
		// 校验确认密码:
		var repassword = document.getElementById("repassword").value;
		if(repassword != password){
			document.getElementById("repasswordCheck").innerHTML = "两次输入的密码不一致";
			return false;
		}
		// 校验邮箱格式：
		var email = document.getElementById("email").value;
		var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
		if(!reg.test(email)){
			document.getElementById("emailCheck").innerHTML = "邮箱格式不正确";
			return false;
		}
		// 校验姓名
		var name = document.getElementById("name").value;
		if (name == null || name == '') {
			document.getElementById("nameCheck").innerHTML = "姓名不能为空";
			return false;
		}
		// 校验手机号码
		var phone = document.getElementById("phone").value;
		if (!(/^1[3456789]\d{9}$/.test(phone))) {
			document.getElementById("phoneCheck").innerHTML = "手机号码格式不正确";
			return false;
		}
		// 校验地址
		var addr = document.getElementById("addr").value;
		if (addr == null || addr == '') {
			document.getElementById("addrCheck").innerHTML = "地址不能为空";
			return false;
		}
	}
	// 校验验证码是否正确
	function checkCode() {
		var checkcode = document.getElementById("checkcode").value;
		if (checkcode.length != 0) {
			$.ajax({
				url:"${ pageContext.request.contextPath }/user_codeCheck.action",
				dataType: "text",
				type:"post",
				data:{"checkcode": checkcode},
				success:function(data){
					if ("no" == data) {
						document.getElementById("checkcodeCheck").innerHTML = "验证码错误";
						document.getElementById("submit").disabled = true;
						change();
					}else {
						document.getElementById("checkcodeCheck").innerHTML = "";
						document.getElementById("submit").disabled = false;
					}
				},
				error:function(){
					alert("ajax请求出现异常！");
					change();
					document.getElementById("submit").disabled = true;
				}
			});
		}
	}
	
	//ajax校验用户名是否可用
	function checkUsername(){
		// 获得文件框值:
		var username = document.getElementById("username").value;
		//首先判断用户名是否为空
		if (username == null || username.length == 0) {
			document.getElementById("span1").style.color = 'red';
			document.getElementById("span1").innerHTML = "用户名不能为空";
		}else {
			$.ajax({
				url:"${ pageContext.request.contextPath }/user_findByName.action?time="+new Date().getTime()+"&username="+username,
				dataType: "text",
				type:"post",
				success:function(data){
					if ("no" == data) {
						document.getElementById("span1").style.color='green';
						document.getElementById("span1").innerHTML = "用户名可用";
						document.getElementById("submit").disabled = false;
						change();
					}else {
						document.getElementById("span1").style.color='red';
						document.getElementById("span1").innerHTML = "用户名已经存在";
						document.getElementById("submit").disabled = true;
					}
				},
				error:function(){
					alert("ajax请求出现异常！");
					change();
					document.getElementById("submit").disabled = true;
				}
			});
			/* // 1.创建异步交互对象
			var xhr = createXmlHttp();
			// 2.设置监听
			xhr.onreadystatechange = function(){
				if(xhr.readyState == 4){
					if(xhr.status == 200){
						document.getElementById("span1").innerHTML = xhr.responseText;
					}
				}
			}
			// 3.打开连接
			xhr.open("GET","${pageContext.request.contextPath}/user_findByName.action?time="+new Date().getTime()+"&username="+username,true);
			// 4.发送
			xhr.send(null); */
		}
	}
/* 	
	function createXmlHttp(){
		   var xmlHttp;
		   try{ // Firefox, Opera 8.0+, Safari
		        xmlHttp=new XMLHttpRequest();
		    }
		    catch (e){
			   try{// Internet Explorer
			         xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
			      }
			    catch (e){
			      try{
			         xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			      }
			      catch (e){}
			      }
		    }

			return xmlHttp;
		 } */
	
	//更换验证码图片
	function change(){
		var img1 = document.getElementById("checkImg");
		img1.src="${pageContext.request.contextPath}/checkImg.action?n"+new Date().getTime();
	}