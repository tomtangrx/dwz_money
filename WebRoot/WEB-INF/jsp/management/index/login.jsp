<%@ page pageEncoding="UTF-8"%>
<html>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="/styles/management/js/jquery-1.4.4.min.js"
	type="text/javascript"></script>
<style type="text/css">
body {
	margin: 0px;
	background-color: #d6e3f7;
}

.span4 {
	position: absolute;
	top: 50%; 
	left: 50%;
	width: 450px;
	height: 300px; 
	margin: -150px 0 0 -225px;
}

input.login-field {
	font-size: 16px;
	height: 30px;
	width: 250px;
	margin-left: 10px;
	padding-left: 7px;
	/* To start the typing of the text a bit to the right */
	float: right;
	background: white;
	border: solid 1px transparent;
	margin-bottom: 5px;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
}

.login-field-caption {
	line-height: 200%
}

.login-button {
	text-align: center;
	line-height: 200%;
	float: right;
	width: 150px;
	height: 30px;
	margin-top: 5px;
	margin-right: 3px;
	color: white;
	font-size: 14px;
	background-color: #55ba49;
	border: solid 1px transparent;
	font-weight: bold;
	letter-spacing: 1px;
	cursor: pointer;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
}

.login-button2 {
	text-align: center;
	line-height: 200%;
	float: right;
	width: 80px;
	height: 30px;
	margin-top: 1px;
	margin-right: 3px;
	color: white;
	font-size: 14px;
	background-color: #6EB0DC;
	border: solid 1px transparent;
	font-weight: bold;
	letter-spacing: 1px; 
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
}

.login-button:hover {
	background-color: #86e379;
}
</style>
<script type="text/javascript">
	
</script>
<<<<<<< HEAD
	</head>
	<body>
		<div  >
			<div class="span4"  style="height:300px;width:400px;border:2px #BAD1D7 solid;">
				<h3>
					demo系统展示
				</h3>
				<p>
				<form method="post" action="/passport!login.do">
					<c:if test="${!empty message}">
						<p style="color: red">
							${message}
						</p>
					</c:if>
					<p>
						<label>
							用户名(默认test)
						</label>
						<input type="text" name="app_username" size="20"
							class="login_input" value="admin" />
					</p>
					<p>
						<label>
							密码(默认为1)
						</label>
						<input type="password" name="app_password" size="20"
							class="login_input" value="111111" />
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" value="登陆" />
					</div>
				</form> 
			</div>
=======
</head>
<body>
	<div>
		<div class="span4">

			<form method="post" action="/passport!login.do">
				<c:if test="${!empty message}">
					<p style="color: red">${message}</p>
				</c:if>
				<table>
					<tr>
						<td colspan="3" style="text-align:center"><h3
								style="text-align:center">demo系统展示</h3></td>
					</tr>
					<tr>
						<td class="login-button2">用户名</td>
						<td><input type="text" name="app_username" size="20"
							class="login-field" value="test" /></td>
						<td>(默认test)</td>
					</tr>
					<tr>
						<td class="login-button2">密码</td>
						<td><input type="password" name="app_password" size="20"
							class="login-field" value="1" /></td>
						<td>(默认为1)</td>
					</tr>
					<tr>
						<td colspan="3"><input class="login-button" type="submit"
							value="登陆" /></td>
					</tr>
				</table>
			</form>
>>>>>>> f0c60d3cab48c428d4b93168ccc323484fe3833d
		</div>
	</div>


<<<<<<< HEAD
		
		<!--  footer> 
		 <hr>
		<p >
=======

	<!-- <p ><hr>
		<footer> 
>>>>>>> f0c60d3cab48c428d4b93168ccc323484fe3833d
			©2012 人杰技术网版权所有 沪ICP备12002847号
			<script language="javascript" type="text/javascript"
				src="http://js.users.51.la/6717157.js"></script>
		<noscript>
			<a href="http://www.51.la/?6717157" target="_blank"><img
					alt="&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;"
					src="http://img.users.51.la/6717157.asp" style="border: none" /> </a>
		</noscript>
		</p>
<<<<<<< HEAD
		</footer-->
		</div>
		<script src="/bootstrap/js/jquery.min.js"></script>
		<script src="/bootstrap/js/bootstrap.min.js"></script>
=======
		</footer> -->
	</div>
	<script src="/bootstrap/js/jquery.min.js"></script>
>>>>>>> f0c60d3cab48c428d4b93168ccc323484fe3833d
</html>