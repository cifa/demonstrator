<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div class="row">
	<h3>Course Registration Completed</h3>
	<p>Congratulations!!! You have successfully registered on ${registration.course.title }.</p>
	<p>Thank you for selecting Edu4All.</p>
	<a href='<s:url value="/user/registration/${registration.uuid }/finish" />' class="radius button">Finish</a>
</div>