<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<jsp:useBean class="java.text.SimpleDateFormat" id="dateFormat" scope="application">
	<% dateFormat.applyPattern("dd/MM/yyyy");%>
</jsp:useBean>
<!DOCTYPE html>
<html>
<head lang="en-gb">
	<title>Edu4All</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content="Ales Cirnfus" name="author" />
	<link type="text/css" rel="stylesheet" href="<s:url value="/resources/foundation/stylesheets/foundation.min.css" />" />
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
	<link type="text/css" rel="stylesheet" href="<s:url value="/resources/css/jquery.rating.css" />" />
	<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="<s:url value="/resources/js/whizzywig.js" />"></script>
	<style type="text/css">
		#featuredContent div {display: none;}
		#featuredContent.orbit div {display: block;}
	</style>
</head>
<body>
	<div class="row" style="margin-top: 10px;">
		<div class="five columns">
			<a href="<s:url value="/" />"> <img src="<s:url value="/resources/images/logo.png?v=2" />" alt="Edu4All Logo" /></a>
		</div>
		<div class="seven columns">
			<ul class="nav-bar right">
				<li><a href="<s:url value="/course" />">All Courses</a></li>
				<sec:authorize access="isAnonymous()">
					<li class="has-flyout"><a href="javascript:void(0)">Login</a>
						<a href="#" class="flyout-toggle"><span> </span></a>
						<div class="flyout medium right">
							<div class="row">
								<form method="post" action="<s:url value="/static/j_spring_security_check" />">
									<div class="text-center">
										<label for="username" style="display: none;">Username:</label>
										<input name="j_username" id="username" type="text" placeholder="Username" /> 
										<label for="passwd" style="display: none;">Password:</label> 
										<input name="j_password" id="passwd" type="password" placeholder="Password" /> 
										<input type="submit" value="Login" class="radius button" />
									</div>
								</form>
							</div>
						</div>
					</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="has-flyout"><a href="javascript:void(0)"><sec:authentication property="principal.username" /></a> 
						<a href="#" class="flyout-toggle"><span> </span></a>
						<ul class="flyout small right">
							<li><a href="javascript:void(0)">My Courses</a></li>
							<li><a href="javascript:void(0)">Profile</a></li>
							<li><a href="<s:url value="/logout" />">Logout</a></li>
						</ul>
					</li>
				</sec:authorize>
			</ul>
		</div>
	</div>
	<div class="row"><hr style="padding: 0; margin: 5px 0;" /></div>

	<t:insertAttribute name="pageBody" />

	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
		<div id="loginModal" class="reveal-modal medium alert-box alert" style="position: fixed; text-align: center;">
			<a class="close-reveal-modal">×</a>
			<div style="font-size: x-large; padding-bottom: 15px;">Oops. ${SPRING_SECURITY_LAST_EXCEPTION.message}</div>
		</div>
		<script type="text/javascript">//<![CDATA[
			$(window).load(function() {$("#loginModal").reveal();});
		//]]></script>
		<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session" />
	</c:if>

	<script type="text/javascript" src="<s:url value="/resources/foundation/javascripts/foundation.min.js" />"></script>
	<script type="text/javascript" src="<s:url value="/resources/foundation/javascripts/app.js" />"></script>
	<script type="text/javascript" src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	<script type="text/javascript"src="<s:url value="/resources/js/jquery.rating.js" />"></script>
</body>
</html>