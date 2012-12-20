<?xml version="1.0" encoding="utf-8" ?>

<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Edu4All</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="Ales Cirnfus" name="author" />
<meta content="en-gb" name="language" />
<link type="text/css" rel="stylesheet"
	href="<s:url value="/resources/foundation/stylesheets/foundation.min.css" />" />
<script type="text/javascript"
	src="<s:url value="/resources/foundation/javascripts/jquery.js" />"></script>
<script type="text/javascript" src="<s:url value="/resources/js/whizzywig.js" />"></script>
<script type="text/javascript">addEvt(window,'load',function() {whizzywig('all')});</script>
</head>
<body>
	<div class="row" style="margin-top: 10px;">
		<div class="five columns">
			<img src="<s:url value="/resources/images/logo.png?v=2" />"
				alt="Edu4All Logo" />
		</div>
		<div class="seven columns">
			<ul class="nav-bar right">
				<li><a href="#">All Courses</a></li>
				<sec:authorize access="isAnonymous()">
				<li class="has-flyout"><a href="#">Login</a> <a href="#"
					class="flyout-toggle"><span> </span></a>
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
					</div></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
				<li class="has-flyout"><a href="#"><sec:authentication property="principal.username" /></a>
				 	<a href="#" class="flyout-toggle"><span> </span></a>
					<ul class="flyout small right">
      					<li><a href="#">My Courses</a></li>
      					<li><a href="#">Profile</a></li>
      					<li><a href="<s:url value="/j_spring_security_logout" />">Logout</a></li>
    				</ul>
				</li>
				</sec:authorize>
			</ul>
		</div>
	</div>
	<div class="row">
		<hr style="padding: 0; margin: 5px 0;" />
	</div>

	<t:insertAttribute name="pageBody" />

	<script type="text/javascript"
		src="<s:url value="/resources/foundation/javascripts/foundation.min.js" />"></script>
	<script type="text/javascript"
		src="<s:url value="/resources/foundation/javascripts/app.js" />"></script>
</body>
</html>