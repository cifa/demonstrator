<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>

	<div class="row">
		<div class="five columns">
			<img src="<s:url value="/data/course/${registration.course.id}/image" />" alt="course logo" />
			<h3>${registration.course.title}</h3>
			<div class="six columns" style="padding: 0; margin-bottom: 10px;">Course Starts:</div>
			<div class="six columns" style="padding: 0; margin-bottom: 10px;">${dateFormat.format(registration.course.startDate)}</div>
			<div class="six columns" style="padding: 0; margin-bottom: 10px;">Duration:</div>
			<div class="six columns" style="padding: 0; margin-bottom: 10px;">${registration.course.length } weeks</div>
			<div class="six columns" style="padding: 0; margin-bottom: 10px;">Price:</div>
			<div class="six columns" style="padding: 0; margin-bottom: 10px;">&pound;${registration.course.price }</div>						
		</div>
		<div class="seven columns">
		
			<t:insertAttribute name="formSection" />
			
		</div>
	</div>