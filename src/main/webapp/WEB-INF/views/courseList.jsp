<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

	<div class="row" style="margin-top: 10px; margin-bottom: 10px;">
		<div class="eight columns" style="font-weight: bold; text-align: center;">Course Category:</div>
		<div class="three columns offset-by-one right" style="font-weight: bold; text-align: center;">Sorted by:</div>
	</div>
	<div class="row" style="margin-bottom: 20px;">
		<div class="medium button split dropdown seven columns">
			<a href="<s:url value="javascript:void(0)" />">
				<c:forEach var="name" items="${categoryMap.keySet()}">
					<c:if test="${categoryMap.get(name) == catId }">
						${name}
					</c:if>
				</c:forEach>
			</a> 
			<span></span>
			<ul>
				<c:forEach var="name" items="${categoryMap.keySet()}">
					<c:if test="${categoryMap.get(name) != catId }">
						<li><a href="<s:url value="/course?cat=${categoryMap.get(name)}&sort=${sortIndex}" />">${name}</a></li>					
						<li class="divider"></li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<div class="two columns">
		<security:authorize access="hasRole('A')">
			<c:if test="${catId != 0 }">
				<a class="button alert" style="padding-right: 19px;" href="<s:url value="/admin/branch/${catId}/course" />">New Course</a>
			</c:if>
		</security:authorize>
		</div>
		<div class="three columns right">
			<div class="medium button split dropdown twelve columns right">
				<a href="<s:url value="javascript:void(0)" />">${sortNames[sortIndex]}</a> 
				<span></span>
				<ul>
					<c:forEach var="name" items="${sortNames}" varStatus="loop" >
						<c:if test="${sortIndex != loop.index }">
							<li><a href="<s:url value="/course?cat=${catId }&sort=${loop.index }" />">${name}</a></li>
							<li class="divider"></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="row">
		<c:forEach items="${courses}" var="course">
			<div class="row" style="margin-bottom: 20px;">
				<div class="five columns">
					<img src="<s:url value="/data/course/${course.id}/image" />" alt="course logo" />
				</div>
				<div class="seven columns">
					<h3>${course.title}</h3>
					<div class="three columns">
						Course Starts:
						<br /><br />
						Duration:
						<br /><br />
						Price:
					</div>
					<div class="three columns">			
						${dateFormat.format(course.startDate)}
						<br /><br />
						${course.length } weeks
						<br /><br />
						&pound;${course.price }
					</div>
					<div class="six columns">
						<div class="medium alert button split dropdown right">
					  		<a href='<s:url value="/course/${course.id}" />'>Course Details</a>
					    	<span></span>
					  		<ul>
					    		<li><a href="<s:url value="/user/course/${course.id}/registration" />">Register</a></li>
								<security:authorize access="hasRole('A')">
									<li class="divider"></li>
					    			<li><a href="<s:url value="/admin/branch/${course.treebranch.id}/course/${course.id}" />">Edit This Course</a></li>
								</security:authorize>						
					  		</ul>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
