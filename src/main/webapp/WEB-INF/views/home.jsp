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
				<li class="has-flyout"><a href="#">Login</a> <a href="#"
					class="flyout-toggle"><span> </span></a>
					<div class="flyout medium right">
						<div class="row">
							<form action="post">
								<div class="text-center">
									<input name="username" type="text" placeholder="Username" /> <input
										name="passwd" type="password" placeholder="Password" /> <input
										type="submit" value="Login" class="radius button" />
								</div>
							</form>
						</div>
					</div></li>
			</ul>
		</div>
	</div>
	<div class="row">
		<hr style="padding: 0; margin: 5px 0;" />
	</div>
	<div class="row">
		<div id="featuredContent">
			<div>
				<div id="tweet0" class="seven columns offset-by-one">
					<p style="font-size: large; color: #2ba6cb;"></p>
					<p class="right"></p>
				</div>
				<div class="four columns right">
					<img src="<s:url value="/resources/images/twitter1.png" />"
						alt="twitter" />
				</div>
			</div>
			<div>
				<div id="tweet1" class="seven columns offset-by-one">
					<p style="font-size: large; color: #2ba6cb;"></p>
					<p class="right"></p>
				</div>
				<div class="four columns right">
					<img src="<s:url value="/resources/images/twitter2.png" />"
						alt="twitter" />
				</div>
			</div>
			<div>
				<div id="tweet2" class="seven columns offset-by-one">
					<p style="font-size: large; color: #2ba6cb;"></p>
					<p class="right"></p>
				</div>
				<div class="four columns right">
					<img src="<s:url value="/resources/images/twitter3.png" />"
						alt="twitter" />
				</div>
			</div>
			<div>
				<div id="tweet3" class="seven columns offset-by-one">
					<p style="font-size: large; color: #2ba6cb;"></p>
					<p class="right"></p>
				</div>
				<div class="four columns right">
					<img src="<s:url value="/resources/images/twitter4.png" />"
						alt="twitter" />
				</div>
			</div>
			<div>
				<div id="tweet4" class="seven columns offset-by-one">
					<p style="font-size: large; color: #2ba6cb;"></p>
					<p class="right"></p>
				</div>
				<div class="four columns right">
					<img src="<s:url value="/resources/images/twitter5.png" />"
						alt="twitter" />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<hr style="padding: 0; margin: 0;" />
	</div>

	<script type='text/javascript'>
		$(window).load(function() {
			$('#featuredContent').orbit({
				fluid : '16x3',
				advanceSpeed : 10000
			});
			self.setInterval(function() {
				updateTweets()
			}, 50000);
		});

		function updateTweets() {
			$.getJSON('tweet', function(tweets) {
				for (i = 0; i < tweets.length; i++) {
					$('#tweet' + i).children().each(function(index) {
						switch (index) {
						case 0:
							$(this).html(tweets[i].content);
							break;
						case 1:
							$(this).html(tweets[i].author);
							break;
						}
					});
				}
			});
		}

		updateTweets();
	</script>


	<script type="text/javascript"
		src="<s:url value="/resources/foundation/javascripts/foundation.min.js" />"></script>
	<script type="text/javascript"
		src="<s:url value="/resources/foundation/javascripts/app.js" />"></script>
</body>
</html>