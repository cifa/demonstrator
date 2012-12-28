<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
  <div class="row" style="padding-top: 5%;">
	<div class="six columns offset-by-three" style="text-align: center;">
		<h2>Please Log In</h2>
		<form method="post" action="<spring:url value="/static/j_spring_security_check" />">
			<div>
				<label for="uname">Username:</label> <input name="j_username" id="uname" type="text" />
				<label for="password">Password:</label> <input name="j_password" id="password" type="password" />
				<input type="submit" value="Login" class="radius button" />
			</div>
		</form>
	</div>
</div>