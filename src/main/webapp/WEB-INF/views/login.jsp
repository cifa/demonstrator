<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
  <div class="row" style="padding-top: 5%;">
	<div class="six columns offset-by-three" style="text-align: center;">
		<h2>Please Log In</h2>
		<form method="post" action="<spring:url value="/static/j_spring_security_check" />">
			<div>
				<label for="username">Username:</label> <input name="j_username" id="username" type="text" />
				<label for="passwd">Password:</label> <input name="j_password" id="passwd" type="password" />
				<input type="submit" value="Login" class="radius button" />
			</div>
		</form>
	</div>
</div>