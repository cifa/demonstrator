<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>

	<div class="row">
		<div class="five columns">
			<img src="<s:url value="/data/course/${registration.course.id}/image" />" alt="course logo" />
			<h3>${registration.course.title}</h3>
			<div class="six columns">
				Course Starts:
				<br /><br />
				Duration:
				<br /><br />
				Price:
			</div>
			<div class="six columns">			
				${dateFormat.format(registration.course.startDate)}
				<br /><br />
				${registration.course.length } weeks
				<br /><br />
				&pound;${registration.course.price }
			</div>
		</div>
		<div class="seven columns">
		
			<t:insertAttribute name="formSection" />
			
		</div>
	</div>